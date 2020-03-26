import api from './index';
import axios from '../http';

const headers = {
  'Content-Type': 'application/json',
  // 'Content-Type': 'application/x-www-form-urlencoded',
};

export default {
  getPerson() {
    return axios.get(api.getPerson(), { params: { size: 9 } }, { headers });
  },
  getMovie() {
    return axios.get(api.getMovie(), { params: { size: 12 } }, { headers });
  },
  getMovieInfo(id) {
    return axios.get(api.getMovieInfo(), { params: { movieId: id } }, { headers });
  },
  userRegister(user, check) {
    return axios.post(api.userRegister(), { user, check }, { headers });
  },

  movieTags() {
    return axios.get(api.getMovieTag(), { headers });
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

  sendCheck(to) {
    return axios.post(api.sendCheck(), to, { headers });
  },

  logout() {
    return axios.post(api.logout());
  },

  putSelfInfo(userInfo) {
    return axios.post(api.putSelfInfo(), userInfo, { headers });
  },

  putSelfOther(user, check) {
    return axios.post(api.putSelfOther(), { user, check }, { headers });
  },

  changePhone(phone) {
    return axios.put(api.changePhone(), JSON.stringify(phone), { headers });
  },
  changePass(password) {
    return axios.put(api.changePass(), JSON.stringify(password), { headers });
  },
  changeEmail(email) {
    return axios.put(api.changeEmail(), JSON.stringify(email), { headers });
  },
  getMessage() {
    return axios.get(api.getMessage(), { headers });
  },
};
