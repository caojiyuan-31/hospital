<template>

  <div>
    <el-dialog
      title="智能分诊结果"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">

     <el-table
    :data="categoryList"
    border
    style="width: 100%">
    <el-table-column
      fixed
      prop="category"
      label="科室名"
      width="100">
    </el-table-column>
    <el-table-column
      label="推荐星级"
      width="200">
      <template slot-scope="scope">
        <el-rate
          v-model="scope.row.probility"
          disabled
          show-score
          :colors="colors"
          text-color="#ff9900"
          score-template="{value}">
        </el-rate>
      </template>
    </el-table-column>
    <el-table-column
      fixed="right"
      label="操作"
      width="135">
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="scopeClick(scope.row)">选择该科室挂号</el-button>
      </template>
    </el-table-column>
  </el-table>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>

    <div class="indexContain">
      <div class="cardBox">
        <el-carousel trigger="click" height="400px" style="position: sticky;">
          <el-carousel-item v-for="(item, key) in crouselImg" :key="key">
            <img :src="item.img" class="boxImg">
          </el-carousel-item>
        </el-carousel>
      </div>
    </div>

    <div class="division"><h3 id="department">选择挂号科室</h3>
      <h3 style="color: #888;font-weight: 400">--- DEPARTMENTS ---</h3></div>

    <div class="zhineng">
      <el-input placeholder="请输入症状描述，通过智能分诊帮助您选择挂号科室" v-model="input" >
        <el-button slot="append" icon="el-icon-search" @click="forecastCategory"></el-button>
      </el-input>
    </div>

    <div class="cardContain">
      <div class="wrapper-card" :style="'height:'+ h * 333 +'px;'">
        <div class="card" v-for="item in departmentList" :key="item.id">
          <meta name="referrer" content="no-referrer"/>
          <img :src="item.url" class="image" @click="getDepartmentDetail(item.id)">
          <div>
            <p style="white-space: pre-wrap;">{{item.name}}    </p>
          </div>
        </div>
      </div>
    </div>


    <div class="division">
      <h3 id="doctor">选择挂号医生</h3>
      <h3 style="color: #888;font-weight: 400">--- DOCTORS ---</h3>
    </div>


  <div class = "selects">
    <el-select v-model="doctor.departmentId" placeholder="请选择科室" @change="getDoctor()">
    <el-option
      v-for="item in departmentList"
      :key="item.id"
      :label="item.name"
      :value="item.id"
      >
    </el-option>
  </el-select>

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
          <div  @click="getDoctorDetail(item.id)">
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


    <div class="division">
      <h3 id="announcement">近期公告</h3>
      <h3 style="color: #888;font-weight: 400">--- ANNOUNCEMENTS ---</h3>
    </div>
    <div class="newsContain">
      <div class="temp">
<!--        @click="personDetail(item.id)-->
        <div class="newsItem" v-for="item in announcementList" :key="item.id">
          <div>
            <p style="white-space: pre-wrap;">标题：{{item.title}}          时间：{{item.createdTime}}          发布人：{{item.createdUser}}</p>
            <p style="margin-top:25px">内容： {{item.text}}</p>
          </div>
        </div>
      </div>

    <div class = "selects">
        <el-pagination
        background
        @size-change="announcementSizeChange"
        @current-change="announcementCurrentChange"
        :current-page="announcementPage.pageNo"
        :page-sizes="[5, 10, 20]"
        :page-size="announcementPage.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="announcementTotal">
      </el-pagination>
        </div>

    </div>

    <div class="division"><h3>关于我们</h3>
      <h3 style="color: #888;font-weight: 400">--- ABOUT ---</h3></div>
    <div class="aboutus">
      <div id="aboutusInfo">
        <p style="font-size: 200%">基于文本分类算法的智能分诊系统</p>
        <p style="font-size: 200%">为您推荐最符合病情的挂号科室，降低科室转诊率</p>
      </div>
    </div>
    <div class="division"><h3>联系我们</h3>
      <h3 style="color: #888;font-weight: 400">--- CONTACT ---</h3></div>
    <div class="footer">
      <a href="https://github.com/caojiyuan-31"><img src="../assets/github.png"><span>https://github.com/caojiyuan-31</span></a>
    </div>
  </div>
</template>

<script>
import fetch from '../api/fetch';
// eslint-disable-next-line import/extensions,import/no-unresolved
import store from '../store/store';
// eslint-disable-next-line import/extensions,import/no-unresolved

export default {
  data() {
    return {
      value2: 2.3,
      colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
      categoryList: [],
      dialogVisible: false,
      input: '',
      h: 3,
      crouselImg: [
        { img: 'http://localhost:8080/upload/lb1.jpg' },
        { img: 'http://localhost:8080/upload/lb2.jpg' },
        { img: 'http://localhost:8080/upload/lb3.jpg' },
        { img: 'http://localhost:8080/upload/lb4.jpg' },
      ],
      activeIndex2: '1',
      departmentList: [],
      doctorList: [],
      announcementList: [],
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
      announcementPage: {
        pageNo: 1,
        pageSize: 5,
      },
      announcementTotal: 0,
    };
  },

  mounted() {
    window.addEventListener('scroll', this.handler);
    this.getDepartment();
    this.getDoctor();
    this.getAnnouncement();
  },
  methods: {
    scopeClick(row) {
      this.departmentList.forEach((item) => {
        if (item.name == row.category) {
          console.log(item.id);
          this.getDepartmentDetail(item.id);
        }
      });
    },

    forecastCategory() {
      fetch
        .forecastCategory(this.input)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.$message({
                message: '智能分诊成功',
                type: 'success',
              });
              localStorage.setItem('text', this.input);
              this.categoryList = res.data.data;
              this.dialogVisible = true;
            } else {
              this.$message({
                message: res.data.msg,
                type: 'warning',
              });
            }
          }
        })
      // eslint-disable-next-line no-unused-vars
        .catch((e) => {
          this.$message({
            message: '智能分诊失败',
            type: 'error',
          });
        });
    },

    handler() {
      const info = document.getElementById('aboutusInfo') || null;
      const card = document.getElementsByClassName('temp')[0] || null;
      if (info === null || card === null) {

      } else if (document.documentElement.scrollTop > 1000) {
        card.classList.add('animated');
        card.classList.add('bounceInLeft');
        info.classList.add('animated');
        info.classList.add('bounceInLeft');
      } else {
        info.classList.remove('animated');
        info.classList.remove('bounceInLeft');
        card.classList.remove('animated');
        card.classList.remove('bounceInLeft');
      }
    },


    getDepartmentDetail(id) {
      localStorage.setItem('departmentId', id);
      this.$router.push({ name: 'departmentInfo' });
    },

    getDepartment() {
      fetch.getDepartment(1, 20)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.departmentList = res.data.data.list;
              // eslint-disable-next-line radix
              this.h = parseInt((this.departmentList.length + 3) / 4);
            }
          }
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

    announcementSizeChange(val) {
      this.announcementPage.pageSize = val;
      this.getAnnouncement();
    },
    announcementCurrentChange(val) {
      this.announcementPage.pageNo = val;
      this.getAnnouncement();
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
  @import "../assets/Animate/animate.min.css";

  * {
    box-sizing: border-box;
  }

  body {
    background: #ededed;
    padding: 0;
    margin: 0;
  }

  .myMenu {
    position: sticky;
    top: 0;
    z-index: 100;
  }

  .indexContain {
    width: 100%;
    height: 100%;
    border: 1px solid #ededed;
    background: #fff;
  }

  .cardContain {
    width: 100%;
    height: 100%;
    background: #fff;
  }

  .newsContain {
    padding-top: 1px;
    width: 100%;
    height: 100%;
    background: #fff;
  }

  .picContain {
    margin-right: 10px;
    perspective: 1000px;
  }

  .picContain:hover .flipper, .picContain.hover .flipper {
    transform: rotateY(180deg);
  }

  .picContain, .itemPic, .back {
    width: 80px;
    height: 80px;
  }

  .flipper {
    transition: 0.6s;
    transform-style: preserve-3d;
    position: relative;
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

  .itemPic, .back {
    position: absolute;
    top: 0;
    left: 0;
    backface-visibility: hidden;
    background: #cc0000;
    text-align: center;
    color: white;
    font-weight: 500;
    line-height: 80px;
    white-space: nowrap;
  }

  .itemPic {
    z-index: 2;
  }

  .back {
    transform: rotateY(180deg);
  }

  .footer {
    width: 100%;
    height: 100px;
    background: black;
    padding-top: 20px
  }

  .footer a {
    color: white;
    text-decoration: none;
  }

  .aboutus {
    width: 100%;
    height: 500px;
    background: url("http://localhost:8080/upload/yx.jpg") no-repeat;
    background-size: 100% 100%;
    filter: grayscale(70%);
    opacity: 0.7;
    color: white;
    font-weight: 600;
    padding-top: 60px;
  }

  .aboutus p {
    margin-top: 30px;
    font-size: 18px;
  }

  #aboutusInfo {
    margin-top: 80px;
    animation-delay: 1s
  }

  .cardBox {
    position: relative;
    width: 1200px;
    margin: 20px auto 30px auto;
    box-shadow: 0 10px 15px #888;
    border-radius: 6px;
  }

  .wrapper-card {
    width: 1200px;
    height: 1000px;
    margin: 30px auto auto auto;
    padding-top: 30px;
  }

  .wrapper-card .card {
    color: #07111B;
    font-size: 16px;
    width: 230px;
    height: 243px;
    float: left;
    margin: 30px;
    border-radius: 6px;
  }

  .wrapper-card .card:hover {
    transform: translateY(-5px);
    transition: 3ms;
    box-shadow: 5px 5px 10px #888;
  }

  .wrapper-card .image {
    border-radius: 6px 6px 0 0;
    width: 100%;
    height: 100%;
    margin-bottom: 20px;
    border-radius: 6px;
  }

  .boxImg {
    width: 100%;
    height: 100%;
    border-radius: 6px;
  }

  .division {
    width: 100%;
    margin: 30px auto;
    text-align: center;
    padding-left: 10px;
    color: #5a5a5a;
  }

  .footer img {
    width: 25px;
    height: 25px;
    margin-right: 10px
  }

  .footer span {
    margin-right: 20px;
  }

  .recommandInfo p {
    margin-bottom: 6px;
  }

  .el-progress__text {
    font-size: 16px !important;
    text-align: center !important;
  }

  .el-carousel-item {
    display: flex;
    justify-content: space-around;
  }

  .el-carousel {
    width: 1200px;
    margin: 0 auto;
  }

  .mytable {
    width: 100%;
    height: 700px;
  }

  .progress2 {
    width: 182px;
    border: 0;
  }

  .selects{
    padding: 0px 0px 30px 0px;
  }

  .zhineng{
    margin: 15px 20% 0px 20%;
    width: 60%;
    text-align:center;
  }
</style>
