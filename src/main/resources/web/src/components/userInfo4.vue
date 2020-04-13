<template>
  <div>
    <el-dialog
  :modal=false
  title="回复评论"
  :visible.sync="dialogVisible"
  width="30%"
  :before-close="handleClose">
  <el-input v-model="reply.text" placeholder="请输入内容">
     <template slot="prepend">@ {{reply.toName}}</template>
  </el-input>
  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false; reply.text=''">取 消</el-button>
    <el-button type="primary" @click="replyCliclD">确 定</el-button>
  </span>
</el-dialog>

    <el-table
    :data="replyList"
    border
    style="width: 100%">
    <el-table-column
      fixed
      prop="createdTime"
      label="时间"
      width="250">
    </el-table-column>
    <el-table-column
      prop="fromName"
      label="发送用户"
      width="120">
    </el-table-column>
    <el-table-column
      prop="text"
      label="内容"
      width="400">
    </el-table-column>
    <el-table-column
      fixed="right"
      label="操作"
      width="220">
      <template slot-scope="scope">
        <el-button @click="toClick(scope.row)" type="text" size="small">查看页面</el-button>
        <el-button @click="replyClick(scope.row)" type="text" size="small">回复评论</el-button>
        <el-button @click="delReply(scope.row)" type="text" size="small" v-if="isShow">删除评论</el-button>
      </template>
    </el-table-column>
  </el-table>

    <div class = "selects">
        <el-pagination
        background
        @size-change="replySizeChange"
        @current-change="replyCurrentChange"
        :current-page="replyPage.pageNo"
        :page-sizes="[10, 20, 100]"
        :page-size="replyPage.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="replyTotal">
      </el-pagination>
        </div>

  </div>
</template>

<script>/* eslint-disable indent */

import fetch from '../api/fetch';

export default {
  data() {
    return {
      isShow: false,
      dialogVisible: false,
      reply: {
        parentId: 0,
        toName: '',
        text: '',
      },
      refresh: 0,
      replyList: [],
      replyPage: {
        pageNo: 1,
        pageSize: 10,
        toName: '',
      },
      replyTotal: 0,
    };
  },
  mounted() {
    this.getSelfInfo();
    if (localStorage.getItem('roles') == 2) {
      this.isShow = true;
    }
  },
  watch: {
    refresh() {
      location.reload();
    },
  },
  methods: {

    addReply(reply) {
      fetch
        .addReply(reply)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.$message({
                message: '回复成功',
                type: 'success',
              });
               this.reply.text = '';
            } else {
              this.$message({
                message: res.data.msg,
                type: 'warning',
              });
               this.reply.text = '';
            }
          }
        })
      // eslint-disable-next-line no-unused-vars
        .catch((e) => {
          this.$message({
            message: '回复失败',
            type: 'error',
          });
           this.reply.text = '';
        });
    },

    replyClick(row) {
      this.dialogVisible = true;
      this.reply.parentId = row.id;
      this.reply.toName = row.fromName;
    },

    replyCliclD() {
      this.dialogVisible = false;
      this.addReply(this.reply);
    },

    toClick(row) {
        fetch
        .getDoctorId(row.id)
        .then((res) => {
          if (res.data.code === '00000') {
            this.getDoctorDetail(res.data.data);
          } else {
            this.$message({
              type: 'warning',
              message: res.data.msg,
            });
          }
        })
        .catch((err) => {
          this.$message({
            type: 'error',
            message: err,
          });
        });
    },

    getSelfInfo() {
      fetch
        .getSelfInfo()
        .then((res) => {
          if (res.data.code === '00000') {
            this.replyPage.toName = res.data.data.username;
            this.getReply();
          } else {
            this.$message({
              type: 'warning',
              message: res.data.msg,
            });
          }
        })
        .catch((err) => {
          this.$message({
            type: 'error',
            message: err,
          });
        });
    },

    delReply(row) {
      fetch
        .delReply(row.id)
        .then((res) => {
          if (res.data.code === '00000') {
            this.getReply();
          } else {
            this.$message({
              type: 'warning',
              message: res.data.msg,
            });
          }
        })
        .catch((err) => {
          this.$message({
            type: 'error',
            message: err,
          });
        });
    },

    getReply() {
      fetch.getReply(this.replyPage.pageNo, this.replyPage.pageSize, this.replyPage.toName)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.replyList = res.data.data.list;
              this.replyTotal = res.data.data.totalCount;
            }
          }
        });
    },

    replySizeChange(val) {
      this.replyPage.pageSize = val;
      this.getReply();
    },
    replyCurrentChange(val) {
      this.replyPage.pageNo = val;
      this.getReply();
    },

    getDoctorDetail(id) {
      localStorage.setItem('doctorId', id);
      this.$router.push({ name: 'doctorInfo' });
    },

    handleClose(done) {
      this.$confirm('确认关闭？')
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },

  },
};
</script>


<style>

</style>

