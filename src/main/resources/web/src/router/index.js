import Vue from 'vue';
import Router from 'vue-router';
import store from '../store/store';
import * as types from '../store/types';
// eslint-disable-next-line import/extensions
import index from '../views/index.vue';
// eslint-disable-next-line import/extensions
import CommonPage from '../components/common/commonPage.vue';

Vue.use(Router);
// eslint-disable-next-line global-require,import/no-dynamic-require
const login = resolve => require(['../views/login'], resolve);
// eslint-disable-next-line global-require,import/no-dynamic-require
const register = resolve => require(['../views/register'], resolve);
// eslint-disable-next-line global-require,import/no-dynamic-require
const password = resolve => require(['../views/password'], resolve);
// eslint-disable-next-line global-require,import/no-dynamic-require
const userInfo = resolve => require(['../views/userInfo.vue'], resolve);
// eslint-disable-next-line global-require,import/no-dynamic-require
const departmentInfo = resolve => require(['../views/departmentInfo.vue'], resolve);
// eslint-disable-next-line global-require,import/no-dynamic-require
const doctorInfo = resolve => require(['../views/doctorInfo.vue'], resolve);


const router = new Router({
  routes: [
    {
      path: '/',
      component: CommonPage,
      children: [
        {
          path: '/',
          name: 'index',
          component: index,
        },
      ],
    },
    {
      path: '/departmentInfo',
      component: CommonPage,
      children: [
        {
          path: '/',
          name: 'departmentInfo',
          component: departmentInfo,
        },
      ],
    },
    {
      path: '/doctorInfo',
      component: CommonPage,
      children: [
        {
          path: '/',
          name: 'doctorInfo',
          component: doctorInfo,
        },
      ],
    },
    {
      path: '/login',
      name: 'login',
      component: login,
    },
    {
      path: '/register',
      name: 'register',
      component: register,
    },
    {
      path: '/password',
      name: 'password',
      component: password,
    },
    {
      path: '/userInfo',
      component: CommonPage,
      children: [
        {
          path: '/',
          name: 'userInfo',
          component: userInfo,
        },
      ],
    },
  ],
});

export default router;

// 同步 localstorage 信息到 store
store.commit(types.SYNC);

