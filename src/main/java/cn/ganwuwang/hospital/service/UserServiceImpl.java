package cn.ganwuwang.hospital.service;

import cn.ganwuwang.hospital.dao.RoleDao;
import cn.ganwuwang.hospital.dao.UserDao;
import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.pojo.LoadUser;
import cn.ganwuwang.hospital.domain.pojo.Role;
import cn.ganwuwang.hospital.domain.pojo.User;
import cn.ganwuwang.hospital.domain.pojo.UserRole;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.PageQuery;
import cn.ganwuwang.hospital.domain.query.Sort;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleServiceImpl userRoleService;

    public List<User> queryPageList(Page page, List<Sort> sort, User user) throws GlobalException {

        List<User> result = null;
        PageQuery query = new PageQuery(user);
        query.setPage(page);
        if(sort != null){
            query.setSort(sort);
        }

        try{
            result = userDao.queryList(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

        return result;
    }

    public Integer queryTotal(User user) throws GlobalException {

        Integer result = 0;
        PageQuery query = new PageQuery(user);

        try{
            result = userDao.queryTotal(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

        return result;
    }

    public User queryObject(Long id) throws GlobalException {

        User result = null;
        try{
            result = userDao.queryObject(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }
        return result;
    }

    public Boolean checkSelf(Long id) throws GlobalException {

        User result = null;
        try{
            result = userDao.queryObject(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }
        if(result.getPhone() == null || CheckUtils.isEmptyBatch(result.getIdentity(), result.getName())){
            return false;
        }
        return true;
    }

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void save(User user) throws GlobalException {

        user.setDeleteFlag(false);
        user.setCreatedUser("SYSTEM");
        encryptPassword(user);
        try{
            userDao.save(user);
            UserRole userRole = new UserRole();
            userRole.setRoleId(new Long(1));
            userRole.setUserId(user.getId());
            userRole.setCreatedUser("SYSTEM");
            userRoleService.save(userRole);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void update(User user) throws GlobalException {

        user.setModifiedUser(CheckUtils.getAuthentication().getName());
        encryptPassword(user);
        try{
            userDao.update(user);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void deleteBatch(Long[] ids) throws GlobalException {

        try{
            userDao.deleteBatch(ids);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void delete(Long id) throws GlobalException {

        try{
            userDao.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        LoadUser loadUser = new LoadUser();
        User user = userDao.queryObjectByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("账号不存在");
        }
        loadUser.setUser(user);
        List<Role> roles = roleDao.getListByUid(user.getId());
        if(roles == null){
            throw new UsernameNotFoundException("加载权限失败");
        }
        loadUser.setRoles(roles);

        return loadUser;
    }

    private void encryptPassword(User user){
        String password = user.getPassword();
        if(password == null || "".equals(password)){
            return;
        }
        password = new BCryptPasswordEncoder().encode(password);
        user.setPassword(password);
    }
}
