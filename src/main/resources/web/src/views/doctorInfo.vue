<template>
  <div>
    <div>
      <el-card class="departmentcard" style="height: 200px;">
        <img :src="doctor.url" class="avatar">
        <div class="introduce">
          <p class="title">{{doctor.name}}</p>
          <p>介绍：{{doctor.description}}</p>
        </div>
      </el-card>
      <el-card class="departmentcard">
        <div class="departmentintroduce">相关回复</div>
        <div class="departmentintroducet">
            <p>test</p>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import fetch from '../api/fetch';

export default {
  data() {
    return {
      doctor: {},
    };
  },
  mounted() {
    this.getDoctorDetail();
  },
  computed: {

  },
  methods: {
    getDoctorDetail() {
      const id = localStorage.getItem('doctorId');
      fetch
        .getDoctorInfo(id)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.doctor = res.data.data;
            }
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },

  },
};
</script>


<style>
  * {
    box-sizing: border-box;
  }

  body {
    margin: 0;
    padding: 0;
  }

  div .departmentcard {
    width: 1000px;
    margin: 20px auto auto auto;
  }

  .avatar {
    float: left;
    width: 126px;
    height: 140px;
  }

  .title {
    font-size: 21px;
  }

  .introduce {
    margin-left: 140px;
    height: 110px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
  }

  .departmentintroducet {
    /*margin-left: 0px;*/
    margin: 15px auto 15px auto;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
  }

  .departmentintroducet p {
    margin: 5px;
  }

  .introduce p {
    margin: 8px;
  }

  p span {
    margin: 14px;
  }

  .departmentintroduce {
    border-left: 5px solid #888;
    text-align: left;
    padding-left: 8px;
  }

</style>
