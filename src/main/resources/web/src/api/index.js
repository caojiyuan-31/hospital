const host = 'http://movie.pqdong.com:10015';
const localhost = 'http://localhost:8080';

export default {
  userRegister() {
    return `${host}/user/register`;
  },
  userLogin() {
    return `${localhost}/login`;
  },
  getUserInfo() {
    return `${host}/user/userInfo`;
  },
  getMovie() {
    return `${host}/movie/list`;
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
  sendCode() {
    return `${host}/user/code`;
  },
  logout() {
    return `${localhost}/logout`;
  },
  // 提交用户信息
  putUserInfo() {
    return `${host}/user/userInfo`;
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
