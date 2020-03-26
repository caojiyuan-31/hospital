<template>
  <div>
    <table v-if="!isEdit" class="container">
      <tr>
        <td>姓名：</td>
        <td>{{user.name}}</td>
      </tr>
      <tr>
        <td>身份证号：</td>
        <td>{{user.identity}}</td>
      </tr>
      <tr>
        <td>手机号：</td>
        <td>{{user.phone}}</td>
      </tr>
      <tr>
        <td></td>
      </tr>
    </table>
    <el-form :model="user" status-icon :rules="rules2" ref="putSelfInfo" label-width="100px" class="formWrap"
             v-if="isEdit">
        <el-form-item label="姓名" prop="name" style="text-align: right">
        <el-input v-model="user.name" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="身份证号" prop="identity" style="text-align: right">
        <el-input v-model="user.identity" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="phone" style="text-align: right">
        <el-input v-model="user.phone" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button class='editor' @click="backToView">返回</el-button>
        <el-button @click="submitInfo('putSelfInfo')">提交</el-button>
      </el-form-item>
    </el-form>
    <div>
      <el-button v-if="!isEdit" class="edit" @click="changeEdit" >编辑</el-button>
      <el-button v-if="!isEdit" class="edit" @click="toIndex" >返回</el-button>
    </div>
  </div>
</template>

<script>/* eslint-disable indent */

import fetch from '../api/fetch';

export default {
  data() {
    const checkName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入姓名'));
      } else if (!/^([\u4E00-\u9FFF]|\w){2,11}$/.test(value)) {
        return callback(new Error('请输入正确的姓名'));
      }
      callback();
    };
    const checkIdentity = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入身份证号'));
      } else if (!/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/.test(value)) {
        return callback(new Error('请输入正确的身份证号'));
      }
      callback();
    };
    const checkPhone = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入手机号'));
      } else if (!/^(\+86)?1[3456789]\d{9}$/.test(value)) {
        return callback(new Error('请输入正确的手机号'));
      }
      callback();
    };
    return {
      user: {
        name: '',
        url: '',
        phone: '',
        identity: '',
      },
      refresh: 0,
      isEdit: false,
      rules2: {
        name: [{ validator: checkName, trigger: 'blur' }],
        identity: [{ validator: checkIdentity, trigger: 'blur' }],
        phone: [{ validator: checkPhone, trigger: 'blur' }],
      },
    };
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
    changeEdit() {
      this.isEdit = !this.isEdit;
    },
    backToView() {
      this.isEdit = !this.isEdit;
    },
    toIndex() {
      this.$router.push({ name: 'index' });
    },
    getSelfInfo() {
      fetch
        .getSelfInfo()
        .then((res) => {
          if (res.data.code === '00000') {
            this.user.name = res.data.data.name;
            this.user.identity = res.data.data.identity;
            this.user.phone = res.data.data.phone;
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

    submitInfo(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          fetch
            .putSelfInfo(this.user)
            .then((res) => {
              if (res.data.code === '00000') {
                this.backToView();
                this.$message({
                  message: '保存成功',
                  type: 'success',
                });
              } else {
                this.$message({
                  message: res.data.msg,
                  type: 'error',
                });
              }
            })
            .catch((e) => {
              this.$message({
                message: e,
                type: 'error',
              });
            });
        } else {
          console.log('error submit!!');
        }
      });
    },
  },
};
</script>


<style>
  table {
    width: 600px;
    font-size: 16px
  }

  table tr td {
    padding: 11.2px;
    text-align: left;
  }

  .formWrap {
    width: 500px;
    font-size: 18px;
  }

  .edit {
    /*position: inherit;*/
    /*left: 300px;*/
    /*margin: 10px auto auto 10px;*/
  }

  .editt {
    margin: 0px auto auto 0px;
  }

  .mutli dd,.singleli dd {
    float: left;
    margin-right: 6px;
    vertical-align: middle;
    padding: 3px 15px 1px 20px;
    cursor: pointer;
    color: #006fbc;
    display: inline-block;
  }

  .mutli input[type="checkbox"], .singleli input[type="checkbox"] {
    display: inline-block;
    width: auto;
    vertical-align: middle;
    padding: 3px 15px;
    cursor: pointer;
    color: #006fbc;
    margin-left: -18px;
  }

</style>

