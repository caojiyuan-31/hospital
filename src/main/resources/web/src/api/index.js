const host = 'http://movie.pqdong.com:10015';
const localhost = 'http://localhost:8080';

export default {
  userRegister() {
    return `${localhost}/user/save`;
  },
  userLogin() {
    return `${localhost}/login`;
  },
  getSelfInfo() {
    return `${localhost}/user/getSelfInfo`;
  },
  getMovie() {
    return `${host}/movie/list`;
  },
  getDepartment() {
    return `${localhost}/department/list`;
  },
  getDoctor() {
    return `${localhost}/doctor/list`;
  },
  getAnnouncement() {
    return `${localhost}/announcement/list`;
  },
  getMovieInfo() {
    return `${host}/movie/info`;
  },
  getPerson() {
    return `${host}/person/list`;
  },
  getMovieTag() {
    return `${host}/movie/tag`;
  },
  sendCheck() {
    return `${localhost}/user/sendCheckMail`;
  },
  logout() {
    return `${localhost}/logout`;
  },
  // 提交用户信息
  putSelfInfo() {
    return `${localhost}/user/updateSelf`;
  },
  // 提交用户信息
  putSelfOther() {
    return `${localhost}/user/updateOther`;
  },
  // 修改用户手机号码
  changePhone() {
    return `${host}/user/phone`;
  },
  // 修改用户密码
  changePass() {
    return `${host}/user/password`;
  },
  // 修改用户邮箱
  changeEmail() {
    return `${host}/user/email`;
  },
  // 获取站内信
  getMessage() {
    return `${host}/message/user/get`;
  },
};
