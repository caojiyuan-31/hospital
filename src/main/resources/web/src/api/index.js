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
  getInfoOfDoctorId() {
    return `${localhost}/user/infoOfDoctorId`;
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
  getDoctorInfoOfUserId() {
    return `${localhost}/doctor/infoOfUserId`;
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
  getRegister() {
    return `${localhost}/register/list`;
  },
  cancelRegister() {
    return `${localhost}/register/cancel`;
  },
  updateRegister() {
    return `${localhost}/register/update`;
  },
  addRegister() {
    return `${localhost}/register/save`;
  },
  getAnnouncement() {
    return `${localhost}/announcement/list`;
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
};
