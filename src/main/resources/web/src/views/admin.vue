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

import Admin from '../components/admin';
import Admin2 from '../components/admin2';
import Admin3 from '../components/admin3';
import Admin4 from '../components/admin4';

export default {
  data() {
    return {
      activeName: '',
      editableTabs: [{
        title: '公告管理',
        name: '1',
        content: 'Admin',
        isShow: true,
      }, {
        title: '科室管理',
        name: '2',
        content: 'Admin2',
        isShow: true,
      }, {
        title: '医生管理',
        name: '3',
        content: 'Admin3',
        isShow: true,
      }, {
        title: '用户管理',
        name: '4',
        content: 'Admin4',
        isShow: true,
      }],
      activeIndex2: '1',
      btnText: '取消',
      refresh: 0,
    };
  },
  mounted() {
    this.activeName = this.editableTabs[0].name;
    this.refresh = this.$route.params.refresh !== undefined ? this.$route.params.refresh : 0;
  },
  watch: {
    refresh() {
      location.reload();
    },
  },
  components: {
    Admin,
    Admin2,
    Admin3,
    Admin4,
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
