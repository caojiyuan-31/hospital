<template>
  <header>
    <div class="contain">
      <div>
        <span @click="redirect(1)" class="tab">首页</span>
        <span @click="department" class="tab">科室信息</span>
        <span @click="doctor" class="tab">医生信息</span>
        <span @click="announcement" class="tab">公告信息</span>
        <span @click="redirect(2)" class="tab" v-if="!isShow">个人中心</span>
        <span @click="redirect(5)" class="tab" v-if="!isShow2">管理中心</span>

      </div>
      <div>
<!--        <span @click="redirect(3)" class="tab" v-show="!isShow">-->
<!--          <i class="el-icon-message" style="margin-right:0.3rem" @click="redirect(3)">-->
<!--            </i>消息中心<span class="icon" v-show="count > 0" ref="icon">{{ count }}</span>-->
<!--          </span>-->
        <span v-if="isShow">
          <span class="tab" @click="redirect(4)">登录</span>
          <span class="tab"  @click="toregister">注册</span>
        </span>
        <span v-if="!isShow" class="tab" @click="logout()" >退出登录</span>
      </div>
    </div>
  </header>
</template>

<script>/* eslint-disable standard/object-curly-even-spacing */

import fetch from '../../api/fetch';
import store from '../../store/store';
import * as types from '../../store/types';

export default {
  data() {
    return {
      index: 0,
      count: 0,
      amount: 0,
      content: '',
      companyList: [],
      msg: '',
      isShow: true,
      isShow2: true,
      newTotal: 0,
      oldTotal: 0,
      username: 0,
      first: true,
    };
  },
  created() {
  },
  watch: {
    amount() {
      location.reload();
    },
  },
  mounted() {
    if (store.state.enter_status !== null) {
      this.isShow = false;
    }
    if (localStorage.getItem('roles') == 3) {
      this.isShow2 = false;
    }
    this.getSelfInfo();
    window.setInterval(() => {
      setTimeout(this.getReply(), 0);
      if (this.oldTotal !== this.newTotal) {
        this.$message({
          type: 'success',
          message: '您有新的回复',
        });
        this.oldTotal = this.newTotal;
      }
    }, 10000);
  },
  methods: {
    doctor() {
      this.redirect(1);
      document.querySelector('#doctor').scrollIntoView(true);
    },
    department() {
      this.redirect(1);
      document.querySelector('#department').scrollIntoView(true);
    },
    announcement() {
      this.redirect(1);
      document.querySelector('#announcement').scrollIntoView(true);
    },
    redirect(num) {
      if (num === 1) {
        this.$router.push({ name: 'index' });
      } else if (num === 2) {
        this.$router.push({ name: 'userInfo' });
      } else if (num === 3) {
        this.$router.push({ name: 'infoCenter' });
      } else if (num === 4) {
        this.$router.push({ name: 'login' });
      } else if (num === 5) {
        this.$router.push({ name: 'admin' });
      }
    },
    toregister() {
      this.$router.push({ name: 'register' });
    },
    logout() {
      fetch
        .logout()
        .then((res) => {
          if (res.status === 200) {
            this.$message({
              message: res.data.msg,
              type: 'success',
            });
            store.commit(types.LOGOUT);
            this.$router.push({ name: 'login' });
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getSelfInfo() {
      fetch
        .getSelfInfo()
        .then((res) => {
          if (res.data.code === '00000') {
            this.username = res.data.data.username;
            this.getReply();
          } else {
            this.$message({
              type: 'success',
              message: '登录后才能使用挂号和智能分诊功能',
            });
          }
        });
    },

    getReply() {
      fetch.getReply(1, 20, this.username)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.newTotal = res.data.data.totalCount;
              if (this.first) {
                this.oldTotal = res.data.data.totalCount;
                this.first = false;
              }
            }
          }
        });
    },
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
  header {
    width: 100%;
    height: 60px;
    background: rgba(0,0,0,0.8);
  }
  .contain {
    display: flex;
    justify-content: space-between;
    width: 1000px;
    margin: auto;
    line-height: 60px;
    font-weight: 500;
  }
  .contain .tab{
    color: white;
    font-size: 16px;
    margin: 10px;
    padding: 6px;
    border-radius: 4px;
  }
  .icon {
    position: relative;
    background: red;
    font-size: 10px;
    border-radius: 50%;
    left: 0;
    top: -8px;
    padding: 0 5px;
    color: #fff;
  }

  .requireinput {
    width: 35%;
    height: 40px;
    border-radius: 4px;
    border: 1px solid #dcdfe6;
    outline: 0;
    background: #fff;
    padding: 0 15px;
    margin: auto 11.2px 14px auto;
  }

  .requireselect {
    width: 35%;
    height: 40px;
    border-radius: 4px;
    border: 1px solid #dcdfe6;
    outline: 0;
    background: #fff;
    padding: 0 15px;
    margin: auto 11.2px 14px auto;
  }

  .require {
    width: 80%;
  }

  .addbtn {
    position: relative;
    top: 40px;
    left: 280px;
  }

  .delete {
    color: #dcdfe6;
    position: relative;
    left: -10px;
  }

  .delete:hover {
    color: red;
  }

</style>
