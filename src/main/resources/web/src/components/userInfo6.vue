<template>
  <div>
    <el-date-picker
      @change="getRegister"
      v-model="registerPage.date"
      type="date"
      placeholder="选择日期"
      value-format="yyyy-MM-dd"
      style="width:30%; margin : 10px 10px 10px 10px;">
    </el-date-picker>
    <el-select v-model="registerPage.scope" placeholder="选择时间段"  @change="getRegister"
    style="width:30%; margin : 10px 10px 10px 10px;">
      <el-option
        v-for="item in options1"
        :key="item.value"
        :label="item.label"
        :value="item.value">
      </el-option>
    </el-select>
    <el-select v-model="registerPage.status" placeholder="选择状态"  @change="getRegister"
    style="width:30%; margin : 10px 10px 10px 10px;">
      <el-option
        v-for="item in options2"
        :key="item.value"
        :label="item.label"
        :value="item.value">
      </el-option>
    </el-select>

    <el-table
    :data="registerList"
    border
    style="width: 100%">
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
      width="220">
      <template slot-scope="scope">
        <span style="margin-left: 10px">{{ scope.row.scope | formatScope }}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="userName"
      label="挂号用户"
      width="80">
    </el-table-column>
    <el-table-column
      label="状态"
      width="150">
      <template slot-scope="scope">
        <span style="margin-left: 10px">{{ scope.row.status | formatStata }}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="text"
      label="症状描述"
      width="120">
    </el-table-column>
    <el-table-column
      fixed="right"
      label="操作"
      width="220">
      <template slot-scope="scope">
        <el-button @click="open(scope.row, 2)" type="success" round>已看诊</el-button>
        <el-button @click="open(scope.row, 3)" type="info" round>未看诊</el-button>
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
      options1: [{
        value: 0,
        label: '上午（9：00-12：00）',
      }, {
        value: 1,
        label: '下午（14：00-18：00）',
      }],
      options2: [{
        value: 0,
        label: '已挂号',
      }, {
        value: 1,
        label: '已取消',
      }, {
        value: 2,
        label: '已看诊',
      }, {
        value: 3,
        label: '未看诊',
      }],
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
    };
  },
  filters: {
    formatStata(status) {
      const statusMap = {
        0: '已挂号',
        1: '已取消',
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
  },
  watch: {
    refresh() {
      location.reload();
    },
  },
  methods: {
    toClick(row) {

    },

    getSelfInfo() {
      fetch
        .getSelfInfo()
        .then((res) => {
          if (res.data.code === '00000') {
            this.getDoctorInfoOfUserId(res.data.data.id);
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

    getDoctorInfoOfUserId(id) {
      fetch
        .getDoctorInfoOfUserId(id)
        .then((res) => {
          if (res.data.code === '00000') {
            this.registerPage.doctorId = res.data.data.id;
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

    getRegister() {
      fetch.getRegister(this.registerPage.pageNo, this.registerPage.pageSize, this.registerPage.userId,
            this.registerPage.doctorId, this.registerPage.date, this.registerPage.scope, this.registerPage.status)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.registerList = res.data.data.list;
              this.registerTotal = res.data.data.totalCount;
            }
          }
        });
    },

    open(row, status) {
        this.$confirm('此操作将更改挂号状态, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          this.updateRegister(row.id, status);
        }).catch(() => {
        });
    },

    updateRegister(registerId, registerStatus) {
      const register = {
          id: registerId,
          status: registerStatus,
      };
      fetch
        .updateRegister(register)
        .then((res) => {
          if (res.data.code === '00000') {
            this.$message({
                message: '更改成功',
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
            type: '更改失败',
            message: err,
          });
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

  },
};
</script>

<style>
</style>

