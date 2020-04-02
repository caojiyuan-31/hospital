<template>
  <div>
    <div class="introduce123">
      <el-button type="warning" round @click="open">取消当前挂号</el-button>
    </div>

    <div class="introduce2" v-if="isShow">
      <p>您当前为排队挂号的第{{registerOldNum}}人</p>
    </div>

    <el-table
    :data="registerList"
    border
    style="width: 100%">
    <el-table-column
      prop="id"
      label="号码"
      width="80">
    </el-table-column>
    <el-table-column
      fixed
      label="日期"
      width="200">
      <template slot-scope="scope">
        <i class="el-icon-time"></i>
        <span style="margin-left: 10px">{{ scope.row.date }}</span>
      </template>
    </el-table-column>
    <el-table-column
      fixed
      label="时间段"
      width="250">
      <template slot-scope="scope">
        <span style="margin-left: 10px">{{ scope.row.scope | formatScope }}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="doctorName"
      label="门诊医生"
      width="120">
    </el-table-column>
    <el-table-column
      label="状态"
      width="150">
      <template slot-scope="scope">
        <span style="margin-left: 10px">{{ scope.row.status | formatStata }}</span>
      </template>
    </el-table-column>
    <el-table-column
      fixed="right"
      label="操作"
      width="160">
      <template slot-scope="scope">
        <el-button @click="toClick(scope.row)" type="text" size="small">查看医生页面</el-button>
      </template>
    </el-table-column>
  </el-table>

    <div class = "selects">
        <el-pagination
        background
        @size-change="registerSizeChange"
        @current-change="registerCurrentChange"
        :current-page="registerPage.pageNo"
        :page-sizes="[10, 20, 100]"
        :page-size="registerPage.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="registerTotal">
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
      refresh: 0,
      registerList: [],
      registerPage: {
        pageNo: 1,
        pageSize: 10,
        userId: '',
        doctorId: '',
        date: '',
        scope: '',
        status: '',
      },
      registerTotal: 0,
      registerOldNum: 0,
      registerNewNum: 0,
      first: true,
    };
  },
  filters: {
    formatStata(status) {
      const statusMap = {
        0: '已挂号',
        1: '已取消挂号',
        2: '已看诊',
        3: '未看诊',
      };
      return statusMap[status];
    },
    formatScope(scope) {
      const statusMap = {
        0: '上午（9：00-12：00）',
        1: '下午（14：00-18：00）',
      };
      return statusMap[scope];
    },
  },
  mounted() {
    this.getSelfInfo();
    window.setInterval(() => {
      setTimeout(this.getRegisterNum(), 0);
      if (this.registerOldNum !== this.registerNewNum) {
        this.$message({
          type: 'success',
          message: '您的挂号排队位置有变动',
        });
        this.registerOldNum = this.registerNewNum;
      }
    }, 10000);
  },
  watch: {
    refresh() {
      location.reload();
    },
  },
  methods: {
    toClick(row) {
      this.getDoctorDetail(row.doctorId);
    },

    getSelfInfo() {
      fetch
        .getSelfInfo()
        .then((res) => {
          if (res.data.code === '00000') {
            this.registerPage.userId = res.data.data.id;
            this.getRegister();
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

    cancelRegister() {
      fetch
        .cancelRegister()
        .then((res) => {
          if (res.data.code === '00000') {
            this.$message({
                message: '取消成功',
                type: 'success',
              });
            this.getRegister();
          } else {
            this.$message({
              type: 'warning',
              message: res.data.msg,
            });
          }
        })
        .catch((err) => {
          this.$message({
            type: '取消失败',
            message: err,
          });
        });
    },

    getRegister() {
      fetch.getRegister(this.registerPage.pageNo, this.registerPage.pageSize, this.registerPage.userId)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.registerList = res.data.data.list;
              this.registerTotal = res.data.data.totalCount;
            }
          }
        });
    },

    getRegisterNum() {
      fetch
        .getRegisterNum()
        .then((res) => {
          if (res.data.code === '00000') {
            this.registerNewNum = res.data.data;
            if (this.first) {
                this.registerOldNum = res.data.data;
                this.first = false;
            }
            this.isShow = true;
          } else {
            this.isShow = false;
          }
        })
        .catch((err) => {
          this.isShow = false;
        });
    },


    registerSizeChange(val) {
      this.registerPage.pageSize = val;
      this.getRegister();
    },
    registerCurrentChange(val) {
      this.registerPage.pageNo = val;
      this.getRegister();
    },

    getDoctorDetail(id) {
      localStorage.setItem('doctorId', id);
      this.$router.push({ name: 'doctorInfo' });
    },

    open() {
        this.$confirm('此操作将取消现有挂号, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          this.cancelRegister();
        }).catch(() => {
        });
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

  .introduce2 {
    margin-left: 20px;
    height: 30px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
  }
</style>

