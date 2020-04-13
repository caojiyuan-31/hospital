const localhost = 'http://localhost:8080';

export default {
  getUserList() {
    return `${localhost}/user/list`;
  },
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
  addDepartment() {
    return `${localhost}/department/save`;
  },
  delDepartment() {
    return `${localhost}/department/delete`;
  },
  updateDepartment() {
    return `${localhost}/department/update`;
  },
  getDoctor() {
    return `${localhost}/doctor/list`;
  },
  addDoctor() {
    return `${localhost}/doctor/save`;
  },
  updateDoctor() {
    return `${localhost}/doctor/update`;
  },
  delDoctor() {
    return `${localhost}/doctor/delete`;
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
  delReply() {
    return `${localhost}/reply/delete`;
  },
  getRegister() {
    return `${localhost}/register/list`;
  },
  getRegisterNum() {
    return `${localhost}/register/num`;
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
  addAnnouncement() {
    return `${localhost}/announcement/save`;
  },
  updateAnnouncement() {
    return `${localhost}/announcement/update`;
  },
  delAnnouncement() {
    return `${localhost}/announcement/delete`;
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
  forecastCategory() {
    return `${localhost}/data/forecastCategory`;
  },
};
