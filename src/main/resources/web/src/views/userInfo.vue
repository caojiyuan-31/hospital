<template>
  <div>
    <div class="wrapper">
      <el-tabs type="border-card" tabPosition="left" v-model="activeName"
               style="width:1000px;height: 100vh;margin: 14px auto auto auto;position: sticky">

        <el-tab-pane
          v-for="(item) in editableTabs"
          :key="item.name"
          :label="item.title"
          :name="item.name"
          v-if="item.isShow"
        >
          <component :is=item.content></component>
        </el-tab-pane>

      </el-tabs>
    </div>
  </div>
</template>

<script>/* eslint-disable indent */

import UserInfo from '../components/userInfo';
import UserInfo2 from '../components/userInfo2';
import UserInfo3 from '../components/userInfo3';
import UserInfo4 from '../components/userInfo4';
import UserInfo5 from '../components/userInfo5';
import UserInfo6 from '../components/userInfo6';

export default {
  data() {
    return {
      activeName: '',
      editableTabs: [{
        title: '个人信息',
        name: '1',
        content: 'UserInfo',
        isShow: true,
      }, {
        title: '修改邮箱',
        name: '2',
        content: 'UserInfo2',
        isShow: true,
      }, {
        title: '修改密码',
        name: '3',
        content: 'UserInfo3',
        isShow: true,
      }, {
        title: '消息中心',
        name: '4',
        content: 'UserInfo4',
        isShow: true,
      }, {
        title: '挂号中心',
        name: '5',
        content: 'UserInfo5',
        isShow: false,
      }, {
        title: '挂号中心',
        name: '6',
        content: 'UserInfo6',
        isShow: false,
      }],
      activeIndex2: '1',
      btnText: '取消',
      refresh: 0,
    };
  },
  mounted() {
    this.activeName = this.editableTabs[0].name;
    this.refresh = this.$route.params.refresh !== undefined ? this.$route.params.refresh : 0;
    if (localStorage.getItem('roles') == 1) {
        this.editableTabs[4].isShow = true;
    }

    if (localStorage.getItem('roles') == 2) {
        this.editableTabs[5].isShow = true;
    }

    if (localStorage.getItem('roles') == 3) {
        this.editableTabs[2].isShow = false;
    }
  },
  watch: {
    refresh() {
      location.reload();
    },
  },
  components: {
    UserInfo,
    UserInfo2,
    UserInfo3,
    UserInfo4,
    UserInfo5,
    UserInfo6,
  },
  methods: {

  },
};
</script>


<style>
  html * {
    padding: 0;
    margin: 0;
  }

  * {
    box-sizing: border-box;
  }

  .box-card {
    width: 1000px;
    margin: 14px auto auto auto;
  }

  .img {
    border-radius: 50%;
    width: 70px;
    height: 70px;
  }

  .el-card .username {
    float: left;
    margin-left: 14px;
    font-size: 21px;
  }

  .avatar-uploader {
    float: left;
  }

  .avatar-uploader .el-upload {
    border-radius: 50%;
    width: 70px;
    height: 70px;
    margin-bottom: 14px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409eff;
  }

  .avatar {
    width: 5rem;
    height: 5rem;
    display: block;
  }
</style>
