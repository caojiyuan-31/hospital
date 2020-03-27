<template>
  <div>
    <div>
      <el-card class="departmentcard" style="height: 200px;">
        <img :src="department.url" class="avatar">
        <div class="introduce">
          <p class="title">{{department.name}}</p>
          <p>介绍：{{department.description}}</p>
        </div>
      </el-card>
      <el-card class="departmentcard">
        <div class="departmentintroduce">科室医生</div>
        <div class="departmentintroducet">

  <div class = "selects">
    <el-select v-model="doctor.level" placeholder="请选择级别" @change="getDoctor()">
    <el-option
      v-for="item in level"
      :key="item.name"
      :label="item.name"
      :value="item.name"
      >
    </el-option>
  </el-select>
  </div>

    <div class="newsContain">
      <div class="temp">
<!--        @click="personDetail(item.id)-->
        <div class="newsItem" v-for="item in doctorList" :key="item.id">
                <div class="picContain" ontouchstart="this.classList.toggle('hover');">
                  <meta name="referrer" content="no-referrer"/>
                  <img :src=item.url height="75" width="75">
              </div>
          <div @click="getDoctorDetail(item.id)">
            <p style="white-space: pre-wrap;">姓名：{{item.name}}          科室：{{item.departmentName}}          学历：{{item.schoolName}}       级别：{{item.level}}</p>
            <p style="margin-top:25px">擅长技能： {{item.skill}}</p>
          </div>
        </div>
      </div>

      <div class = "selects">
        <el-pagination
        background
        @size-change="doctorSizeChange"
        @current-change="doctorCurrentChange"
        :current-page="doctorPage.pageNo"
        :page-sizes="[10, 20, 100]"
        :page-size="doctorPage.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="doctorTotal">
      </el-pagination>
        </div>

    </div>

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
      department: {},
      doctorList: [],
      level: [
        { name: '普通' }, { name: '主任' }, { name: '副主任' },
      ],
      doctor: {
        level: '',
        departmentId: '',
      },
      doctorPage: {
        pageNo: 1,
        pageSize: 10,
      },
      doctorTotal: 0,
    };
  },
  mounted() {
    this.getDepartmentDetail();
  },
  computed: {

  },
  methods: {
    getDepartmentDetail() {
      const id = localStorage.getItem('departmentId');
      fetch
        .getDepartmentInfo(id)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.department = res.data.data;
              this.doctor.departmentId = id;
              this.getDoctor();
            }
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },

    doctorSizeChange(val) {
      this.doctorPage.pageSize = val;
      this.getDoctor();
    },
    doctorCurrentChange(val) {
      this.doctorPage.pageNo = val;
      this.getDoctor();
    },

    getDoctorDetail(id) {
      localStorage.setItem('doctorId', id);
      this.$router.push({ name: 'doctorInfo' });
    },

    getDoctor() {
      fetch.getDoctor(this.doctorPage.pageNo, this.doctorPage.pageSize, this.doctor.departmentId, this.doctor.level)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.doctorList = res.data.data.list;
              this.doctorTotal = res.data.data.totalCount;
            }
          }
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

  .picContain {
    margin-right: 10px;
    perspective: 1000px;
  }

  .newsItem {
    display: flex;
    justify-content: flex-start;
    width: 1200px;
    margin: auto;
    height: 114px;
    text-align: left;
    color: #5a5a5a;
    font-weight: 500;
    padding-top: 15px;
    border-bottom: 1px solid #ededed;
  }

</style>
