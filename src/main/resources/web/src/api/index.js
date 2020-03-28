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
  getDepartmentInfo() {
    return `${localhost}/department/info`;
  },
  getDoctor() {
    return `${localhost}/doctor/list`;
  },
  getDoctorInfo() {
    return `${localhost}/doctor/info`;
  },
  getDoctorId() {
    return `${localhost}/reply/doctorId`;
  },
  getReply() {
    return `${localhost}/reply/list`;
  },
  getReplyTree() {
    return `${localhost}/reply/tree`;
  },
  addReply() {
    return `${localhost}/reply/save`;
  },
  getAnnouncement() {
    return `${localhost}/announcement/list`;
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
  putSelfInfo() {
    return `${localhost}/user/updateSelf`;
  },
  updateSelfEmail() {
    return `${localhost}/user/updateSelfEmail`;
  },
  updatePassOfEmail() {
    return `${localhost}/user/updatePassOfEmail`;
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
