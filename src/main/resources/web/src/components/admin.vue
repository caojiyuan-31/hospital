<template>
  <div>

    <el-dialog
      title="新增公告"
      :modal=false
      :visible.sync="dialogVisible1"
      width="30%"
      :before-close="handleClose">
      <el-input
        type="text"
        placeholder="请输入标题"
        v-model="announcement.title"
        style="width:100%; margin : 10px 0px 10px 0px;">
      </el-input>
      <el-input
        type="textarea"
        :rows="2"
        placeholder="请输入内容"
        v-model="announcement.text"
        style="width:100%; margin : 10px 0px 10px 0px;">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="clickClose">取 消</el-button>
        <el-button type="primary" @click="addAnnouncement">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="修改公告"
      :modal=false
      :visible.sync="dialogVisible2"
      width="30%"
      :before-close="handleClose">
      <el-input
        type="text"
        placeholder="请输入标题"
        v-model="announcement.title"
        style="width:100%; margin : 10px 0px 10px 0px;">
      </el-input>
      <el-input
        type="textarea"
        :rows="2"
        placeholder="请输入内容"
        v-model="announcement.text"
        style="width:100%; margin : 10px 0px 10px 0px;">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="clickClose">取 消</el-button>
        <el-button type="primary" @click="updateAnnouncement">确 定</el-button>
      </span>
    </el-dialog>

    <div class="introduce123">
      <el-button type="success" @click="dialogVisible1 = true">新增公告</el-button>
    </div>

    <el-table
    :data="announcementList"
    border
    style="width: 100%">
    <el-table-column
      fixed
      prop="id"
      label="id"
      width="80">
    </el-table-column>
    <el-table-column
      fixed
      prop="createdTime"
      label="创建时间"
      width="220">
    </el-table-column>
    <el-table-column
      prop="title"
      label="公告标题"
      width="120">
    </el-table-column>
    <el-table-column
      prop="text"
      label="公告内容"
      width="450">
    </el-table-column>
    <el-table-column
      fixed="right"
      label="操作"
      width="150">
      <template slot-scope="scope">
        <el-button @click="updateRow(scope.row)" size="small" type="primary">修改</el-button>
        <el-button @click="deleteRow(scope.row)" size="small" type="danger">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

    <div class = "selects">
        <el-pagination
        background
        @size-change="announcementSizeChange"
        @current-change="announcementCurrentChange"
        :current-page="announcementPage.pageNo"
        :page-sizes="[5, 10, 20, 100]"
        :page-size="announcementPage.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="announcementTotal">
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
      announcementList: [],
      announcementPage: {
        pageNo: 1,
        pageSize: 5,
      },
      announcement: {
        id: '',
        title: '',
        text: '',
      },
      announcementTotal: 0,
    };
  },

  mounted() {
    this.getAnnouncement();
  },

  watch: {
    refresh() {
      location.reload();
    },
  },

  methods: {
    updateRow(row) {
      this.announcement.id = row.id;
      this.announcement.title = row.title;
      this.announcement.text = row.text;
      this.dialogVisible2 = true;
    },

    delAnnouncement(id) {
      fetch
        .delAnnouncement(id)
        .then((res) => {
          if (res.data.code === '00000') {
            this.$message({
                message: '删除成功',
                type: 'success',
              });
            this.getAnnouncement();
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

    addAnnouncement() {
      fetch
        .addAnnouncement(this.announcement)
        .then((res) => {
          if (res.data.code === '00000') {
            this.$message({
                message: '新增成功',
                type: 'success',
              });
            this.getAnnouncement();
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

    updateAnnouncement() {
      fetch
        .updateAnnouncement(this.announcement)
        .then((res) => {
          if (res.data.code === '00000') {
            this.$message({
                message: '更新成功',
                type: 'success',
              });
            this.getAnnouncement();
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

    getAnnouncement() {
      fetch.getAnnouncement(this.announcementPage.pageNo, this.announcementPage.pageSize)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.announcementList = res.data.data.list;
              this.announcementTotal = res.data.data.totalCount;
            }
          }
        });
    },

    announcementSizeChange(val) {
      this.announcementPage.pageSize = val;
      this.getAnnouncement();
    },
    announcementCurrentChange(val) {
      this.announcementPage.pageNo = val;
      this.getAnnouncement();
    },

    clickClose() {
      this.announcement.title = '';
      this.announcement.text = '';
      this.announcement.id = '';
      this.dialogVisible1 = false;
      this.dialogVisible2 = false;
    },

    handleClose(done) {
        this.$confirm('确认关闭？')
          .then((_) => {
            this.announcement.title = '';
            this.announcement.text = '';
            this.announcement.id = '';
            done();
          })
          .catch((_) => {});
    },

    deleteRow(row) {
        this.$confirm('此操作将删除该公告, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          this.delAnnouncement(row.id);
        }).catch(() => {
        });
    },

  },
};
</script>


<style>
.introduce123 {
    margin-left: 0px;
    height: 60px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
  }
</style>

