<template>
  <div>

    <div  class = "selects1234">
      <el-input placeholder="请输入用户名" v-model="userPage.name">
        <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button>
      </el-input>
    </div>

    <el-dialog
      title="创建医生信息"
      :modal=false
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
       <p class="introduce12">上传医生图片</p>
      <el-upload
        class="avatar-uploader"
        action="http://localhost:8080/file/uploadFile"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :with-credentials='true'
        :before-upload="beforeAvatarUpload">
        <img v-if="doctor.url" :src="doctor.url" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <el-input
        type="text"
        placeholder="请输入医生名"
        v-model="doctor.name"
        style="width:100%; margin : 10px 0px 10px 0px;">
      </el-input>
      <el-select v-model="doctor.departmentId" placeholder="请选择科室" @change="selectDepartment"
      style="width:100%;">
        <el-option
          v-for="item in departmentList"
          :key="item.id"
          :label="item.name"
          :value="item.id"
          >
        </el-option>
      </el-select>
      <el-input
        type="textarea"
        :rows="2"
        placeholder="请输入学历介绍"
        v-model="doctor.schoolName"
        style="width:100%; margin : 10px 0px 10px 0px;">
      </el-input>
      <el-select v-model="doctor.level" placeholder="请选择级别" style="width:100%;">
        <el-option
          v-for="item in level"
          :key="item.name"
          :label="item.name"
          :value="item.name"
          >
        </el-option>
      </el-select>
      <el-input
        type="text"
        placeholder="请输入am挂号限制"
        v-model="doctor.am"
        style="width:100%; margin : 10px 0px 10px 0px;">
      </el-input>
      <el-input
        type="text"
        placeholder="请输入pm挂号限制"
        v-model="doctor.pm"
        style="width:100%; margin : 10px 0px 10px 0px;">
      </el-input>
      <el-input
        type="textarea"
        :rows="2"
        placeholder="请输入技能"
        v-model="doctor.skill"
        style="width:100%; margin : 10px 0px 10px 0px;">
      </el-input>
      <el-input
        type="textarea"
        :rows="2"
        placeholder="请输入描述"
        v-model="doctor.description"
        style="width:100%; margin : 10px 0px 10px 0px;">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="clickClose">取 消</el-button>
        <el-button type="primary" @click="addDoctor">确 定</el-button>
      </span>
    </el-dialog>

    <el-table
    :data="userList"
    border
    style="width: 100%">
    <el-table-column
      fixed
      prop="id"
      label="id"
      width="80">
    </el-table-column>
    <el-table-column
      prop="username"
      label="用户名"
      width="80">
    </el-table-column>
    <el-table-column
      prop="name"
      label="用户姓名"
      width="80">
    </el-table-column>
    <el-table-column
      prop="phone"
      label="电话号码"
      width="140">
    </el-table-column>
    <el-table-column
      prop="email"
      label="邮箱"
      width="180">
    </el-table-column>
    <el-table-column
      prop="identity"
      label="身份证号"
      width="200">
    </el-table-column>
    <el-table-column
      fixed="right"
      label="操作"
      width="150">
      <template slot-scope="scope">
        <el-button @click="addRow(scope.row)" size="small" type="primary">升级为医生用户</el-button>
      </template>
    </el-table-column>
  </el-table>

    <div class = "selects">
        <el-pagination
        background
        @size-change="userSizeChange"
        @current-change="userCurrentChange"
        :current-page="userPage.pageNo"
        :page-sizes="[5, 10]"
        :page-size="userPage.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="userTotal">
      </el-pagination>
    </div>

  </div>
</template>

<script>/* eslint-disable indent */

import fetch from '../api/fetch';

export default {
  data() {
    return {
      dialogVisible: false,
      refresh: 0,
      userList: [],
      departmentList: [],
      level: [
        { name: '普通' }, { name: '主任' }, { name: '副主任' },
      ],
      doctor: {
        userId: '',
        name: '',
        departmentId: '',
        departmentName: '',
        schoolName: '',
        level: '',
        url: null,
        am: '',
        pm: '',
        skill: '',
        description: '',
      },
      userPage: {
        pageNo: 1,
        pageSize: 5,
        name: null,
      },
      userTotal: 0,
    };
  },

  mounted() {
    this.getDepartment();
    this.getUserList();
  },

  watch: {
    refresh() {
      location.reload();
    },
  },

  methods: {
    selectDepartment(id) {
      let obj = null;
      obj = this.departmentList.find(item => item.id === id);
      this.doctor.departmentName = obj.name;
    },

    getDepartment() {
      fetch.getDepartment(1, 20)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.departmentList = res.data.data.list;
            }
          }
        });
    },

    handleAvatarSuccess(res, file) {
      console.log(res.data);
      this.doctor.url = res.data;
    },

    beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
    },

    addRow(row) {
      this.doctor.userId = row.id;
      this.dialogVisible = true;
    },

    addDoctor() {
      fetch
        .addDoctor(this.doctor)
        .then((res) => {
          if (res.data.code === '00000') {
            this.$message({
                message: '升级成功',
                type: 'success',
              });
            this.getDoctor();
            this.clickClose();
          } else {
            this.$message({
              type: 'warning',
              message: res.data.msg,
            });
            this.clickClose();
          }
        })
        .catch((err) => {
          this.$message({
            type: '升级失败',
            message: err,
          });
          this.clickClose();
        });
    },

    getUserList() {
      fetch.getUserList(this.userPage.pageNo, this.userPage.pageSize, this.userPage.name)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.userList = res.data.data.list;
              this.userTotal = res.data.data.totalCount;
            }
          }
        });
    },

    clickClose() {
      this.doctor.userId = null;
      this.doctor.name = null;
      this.doctor.departmentId = null;
      this.doctor.departmentName = null;
      this.doctor.schoolName = null;
      this.doctor.level = null;
      this.doctor.url = null;
      this.doctor.am = null;
      this.doctor.pm = null;
      this.doctor.skill = null;
      this.doctor.description = null;
      this.dialogVisible = false;
    },

    handleClose(done) {
        this.$confirm('确认关闭？')
          .then((_) => {
            this.clickClose();
            done();
          })
          .catch((_) => {});
    },

    userSizeChange(val) {
      this.userPage.pageSize = val;
      this.getUserList();
    },
    userCurrentChange(val) {
      this.userPage.pageNo = val;
      this.getUserList();
    },

  },
};
</script>


<style>
  .introduce123 {
    margin-left: 20px;
    height: 60px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
  }

  .selects1234 {
    padding: 10px 20px 10px 0px;
    float:left;
  }

  .introduce12 {
    margin-left: 0px;
    height: 30px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
  }

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>

