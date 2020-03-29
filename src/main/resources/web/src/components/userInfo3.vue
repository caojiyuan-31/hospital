<template>
  <div>
    <table class="container">
      <tr>
        <td>邮箱：</td>
        <td>{{user.email}}</td>
      </tr>
      <tr>
        <td></td>
      </tr>
    </table>
    <el-form :model="user" status-icon :rules="rules2" ref="updatePassOfEmail" class="formWrap">
       <el-form-item prop="password">
          <el-input type="password" v-model="user.password" auto-complete="off" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item prop="checkPass">
          <el-input type="password" v-model="user.checkPass" auto-complete="off" placeholder="确认密码"></el-input>
        </el-form-item>
        <el-form-item prop="check">
          <el-input v-model.number="user.check" style="width: 380px;"
                    placeholder="验证码"></el-input>
          <el-button :disabled="isAble" @click="sendCode">{{this.msg}}</el-button>
        </el-form-item>
      <el-form-item>
        <el-button  type="primary" class="registerBtn" @click="submitOther('updatePassOfEmail')">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>/* eslint-disable indent */

import fetch from '../api/fetch';

export default {
  data() {
    const checkCheck = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入验证码'));
      }
      callback();
    };
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.user.checkPass !== '') {
          this.$refs.updatePassOfEmail.validateField('checkPass');
        }
        callback();
      }
    };
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.user.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      user: {
        password: '',
        checkPass: '',
        email: '',
        check: '',
      },
      msg: '发送验证码',
      count: '',
      timer: null,
      isAble: false,
      show: true,
      confirmCode: '',
      refresh: 0,
      rules2: {
        check: [{ validator: checkCheck, trigger: 'blur' }],
        password: [{ validator: validatePass, trigger: 'blur' }],
        checkPass: [{ validator: validatePass2, trigger: 'blur' }],
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
    sendCode() {
      const TIME_COUNT = 60;
      fetch
        .sendCheck()
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.$message({
                message: '发送成功',
                type: 'success',
              });
            } else {
              this.$message({
                message: res.data.msg,
                type: 'warning',
              });
            }
          }
        })
        .catch((e) => {
          console.log(e);
        });
      if (!this.timer) {
        this.count = TIME_COUNT;
        this.show = false;
        this.isAble = true;
        this.timer = setInterval(() => {
          if (this.count > 0 && this.count <= TIME_COUNT) {
            // eslint-disable-next-line no-plusplus
            this.count--;
            this.msg = `${this.count}s后发送`;
            if (this.count === 0) {
              this.isAble = false;
              this.msg = '发送验证码';
            }
          } else {
            this.isAble = false;
            this.show = true;
            clearInterval(this.timer);
            this.timer = null;
          }
        }, 1000);
      }
    },

    getSelfInfo() {
      fetch
        .getSelfInfo()
        .then((res) => {
          if (res.data.code === '00000') {
            this.user.email = res.data.data.email;
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

    submitOther(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const user = {
            email: this.user.email,
            password: this.user.password,
          };
          fetch
            .updatePassOfEmail(user, this.user.check)
            .then((res) => {
              if (res.data.code === '00000') {
                this.$message({
                  message: '修改成功',
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

