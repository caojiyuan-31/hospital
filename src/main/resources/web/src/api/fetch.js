import api from './index';
import axios from '../http';

const headers = {
  'Content-Type': 'application/json;charset=UTF-8',
};

export default {
  getUserList(pageNo, pageSize, name) {
    return axios.get(api.getUserList(), { params: { pageNo, pageSize, name } });
  },
  getDepartment(pageNo, pageSize) {
    return axios.get(api.getDepartment(), { params: { pageNo, pageSize } });
  },
  getDepartmentInfo(id) {
    return axios.get(api.getDepartmentInfo(), { params: { id } });
  },
  addDepartment(department) {
    return axios.post(api.addDepartment(), department, { headers });
  },
  delDepartment(id) {
    return axios.post(api.delDepartment(), id, { headers });
  },
  updateDepartment(department) {
    return axios.post(api.updateDepartment(), department, { headers });
  },
  getDoctor(pageNo, pageSize, departmentId, level) {
    return axios.get(api.getDoctor(), { params: { pageNo, pageSize, departmentId, level } });
  },
  addDoctor(doctor) {
    return axios.post(api.addDoctor(), doctor, { headers });
  },
  updateDoctor(doctor) {
    return axios.post(api.updateDoctor(), doctor, { headers });
  },
  delDoctor(id) {
    return axios.post(api.delDoctor(), id, { headers });
  },
  getDoctorInfo(id) {
    return axios.get(api.getDoctorInfo(), { params: { id } });
  },
  getDoctorInfoOfUserId(id) {
    return axios.get(api.getDoctorInfoOfUserId(), { params: { id } });
  },
  getDoctorId(id) {
    return axios.get(api.getDoctorId(), { params: { id } });
  },
  getReply(pageNo, pageSize, toName) {
    return axios.get(api.getReply(), { params: { pageNo, pageSize, toName } });
  },
  getReplyTree(toName) {
    return axios.get(api.getReplyTree(), { params: { toName } });
  },
  addReply(reply) {
    return axios.post(api.addReply(), reply, { headers });
  },
  getRegister(pageNo, pageSize, userId, doctorId, date, scope, status) {
    return axios.get(api.getRegister(), { params: { pageNo, pageSize, userId, doctorId, date, scope, status } });
  },
  getRegisterNum() {
    return axios.get(api.getRegisterNum());
  },
  cancelRegister() {
    return axios.post(api.cancelRegister());
  },
  updateRegister(register) {
    return axios.post(api.updateRegister(), register, { headers });
  },
  addRegister(register) {
    return axios.post(api.addRegister(), register, { headers });
  },
  getAnnouncement(pageNo, pageSize) {
    return axios.get(api.getAnnouncement(), { params: { pageNo, pageSize } });
  },
  addAnnouncement(announcement) {
    return axios.post(api.addAnnouncement(), announcement, { headers });
  },
  updateAnnouncement(announcement) {
    return axios.post(api.updateAnnouncement(), announcement, { headers });
  },
  delAnnouncement(id) {
    return axios.post(api.delAnnouncement(), id, { headers });
  },
  userRegister(user, check) {
    return axios.post(api.userRegister(), { user, check }, { headers });
  },

  userLogin(info) {
    const formData = new FormData();
    formData.append('username', info.username);
    formData.append('password', info.password);
    return axios.post(api.userLogin(), formData);
  },
  getSelfInfo() {
    return axios.get(api.getSelfInfo());
  },
  getInfoOfDoctorId(id) {
    return axios.get(api.getInfoOfDoctorId(), { params: { id } });
  },
  sendCheck(to) {
    return axios.post(api.sendCheck(), to, { headers });
  },
  logout() {
    return axios.post(api.logout());
  },
  putSelfInfo(userInfo) {
    return axios.post(api.putSelfInfo(), userInfo, { headers });
  },
  updateSelfEmail(user, check) {
    return axios.post(api.updateSelfEmail(), { user, check }, { headers });
  },
  updatePassOfEmail(user, check) {
    return axios.post(api.updatePassOfEmail(), { user, check }, { headers });
  },
  forecastCategory(text) {
    return axios.post(api.forecastCategory(), text, { headers });
  },
};
