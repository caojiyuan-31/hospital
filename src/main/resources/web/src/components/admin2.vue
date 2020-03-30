<template>
  <div>

    <!-- <el-dialog
      title="新增公告"
      :modal=false
      :visible.sync="dialogVisible1"
      width="30%"
      :before-close="handleClose">
      <p class="introduce12">上传科室图片</p>
      <el-upload
        class="avatar-uploader"
        action="http://localhost:8080/file/uploadFile"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :with-credentials='true'
        :before-upload="beforeAvatarUpload">
        <img v-if="department.url" :src="department.url" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <el-input
        type="text"
        placeholder="请输入科室名"
        v-model="department.name"
        style="width:100%; margin : 10px 0px 10px 0px;">
      </el-input>
      <el-input
        type="textarea"
        :rows="2"
        placeholder="请输入科室介绍"
        v-model="department.description"
        style="width:100%; margin : 10px 0px 10px 0px;">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="clickClose">取 消</el-button>
        <el-button type="primary" @click="addDepartment">确 定</el-button>
      </span>
    </el-dialog> -->

    <el-dialog
      title="修改科室信息"
      :modal=false
      :visible.sync="dialogVisible2"
      width="30%"
      :before-close="handleClose">
       <p class="introduce12">上传科室图片</p>
      <el-upload
        class="avatar-uploader"
        action="http://localhost:8080/file/uploadFile"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :with-credentials='true'
        :before-upload="beforeAvatarUpload">
        <img v-if="department.url" :src="department.url" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <!-- <el-input
        type="text"
        placeholder="请输入科室名"
        v-model="department.name"
        style="width:100%; margin : 10px 0px 10px 0px;">
      </el-input> -->
      <el-input
        type="textarea"
        :rows="2"
        placeholder="请输入科室介绍"
        v-model="department.description"
        style="width:100%; margin : 10px 0px 10px 0px;">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="clickClose">取 消</el-button>
        <el-button type="primary" @click="updateDepartment">确 定</el-button>
      </span>
    </el-dialog>

    <!-- <div class="introduce123">
      <el-button type="success" round @click="dialogVisible1 = true">新增科室</el-button>
    </div> -->

    <el-table
    :data="departmentList"
    border
    style="width: 100%">
    <el-table-column
      fixed
      prop="id"
      label="id"
      width="80">
    </el-table-column>
    <el-table-column
      label="科室图片"
      width="120">
      <template slot-scope="scope">
        <div class="demo-image__preview">
          <el-image
            style="width: 100px; height: 80px"
            :src=scope.row.url>
          </el-image>
        </div>
      </template>
    </el-table-column>
    <el-table-column
      prop="name"
      label="科室名"
      width="120">
    </el-table-column>
    <el-table-column
      prop="description"
      label="科室介绍"
      width="600">
    </el-table-column>
    <el-table-column
      fixed="right"
      label="操作"
      width="80">
      <template slot-scope="scope">
        <el-button @click="updateRow(scope.row)" size="small" type="primary">修改</el-button>
        <!-- <el-button @click="deleteRow(scope.row)" size="small" type="danger">删除</el-button> -->
      </template>
    </el-table-column>
  </el-table>

    <div class = "selects">
        <el-pagination
        background
        @size-change="departmentSizeChange"
        @current-change="departmentCurrentChange"
        :current-page="departmentPage.pageNo"
        :page-sizes="[5, 10]"
        :page-size="departmentPage.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="departmentTotal">
      </el-pagination>
    </div>

  </div>
</template>

<script>/* eslint-disable indent */

import fetch from '../api/fetch';

export default {
  data() {
    return {
      dialogVisible1: false,
      dialogVisible2: false,
      refresh: 0,
      departmentList: [],
      department: {
        id: '',
        name: '',
        url: null,
        description: '',
      },
      departmentPage: {
        pageNo: 1,
        pageSize: 5,
      },
      departmentTotal: 0,
    };
  },

  mounted() {
    this.getDepartment();
  },

  watch: {
    refresh() {
      location.reload();
    },
  },

  methods: {
    handleAvatarSuccess(res, file) {
      console.log(res.data);
      this.department.url = res.data;
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

    updateRow(row) {
      this.department.id = row.id;
      this.department.name = row.name;
      this.department.url = row.url;
      this.department.description = row.description;
      this.dialogVisible2 = true;
    },

    delDepartment(id) {
      fetch
        .delDepartment(id)
        .then((res) => {
          if (res.data.code === '00000') {
            this.$message({
                message: '删除成功',
                type: 'success',
              });
            this.getDepartment();
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
            type: '删除失败',
            message: err,
          });
          this.clickClose();
        });
    },

    addDepartment() {
      fetch
        .addDepartment(this.department)
        .then((res) => {
          if (res.data.code === '00000') {
            this.$message({
                message: '新增成功',
                type: 'success',
              });
            this.getDepartment();
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
            type: '新增失败',
            message: err,
          });
          this.clickClose();
        });
    },

    updateDepartment() {
      fetch
        .updateDepartment(this.department)
        .then((res) => {
          if (res.data.code === '00000') {
            this.$message({
                message: '更新成功',
                type: 'success',
              });
            this.getDepartment();
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
            type: '更新失败',
            message: err,
          });
          this.clickClose();
        });
    },

    getDepartment() {
      fetch.getDepartment(this.departmentPage.pageNo, this.departmentPage.pageSize)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.departmentList = res.data.data.list;
              this.departmentTotal = res.data.data.totalCount;
            }
          }
        });
    },

    clickClose() {
      this.department.id = '';
      this.department.name = '';
      this.department.url = null;
      this.department.description = '';
      this.dialogVisible1 = false;
      this.dialogVisible2 = false;
    },

    handleClose(done) {
        this.$confirm('确认关闭？')
          .then((_) => {
            this.department.id = '';
            this.department.name = '';
            this.department.url = null;
            this.department.description = '';
            done();
          })
          .catch((_) => {});
    },

    deleteRow(row) {
        this.$confirm('此操作将删除该科室, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          this.delDepartment(row.id);
        }).catch(() => {
        });
    },

    departmentSizeChange(val) {
      this.departmentPage.pageSize = val;
      this.getDepartment();
    },
    departmentCurrentChange(val) {
      this.departmentPage.pageNo = val;
      this.getDepartment();
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

