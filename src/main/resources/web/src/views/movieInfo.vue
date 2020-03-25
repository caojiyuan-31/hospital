<template>
  <div>
    <el-card v-if="isShow" class="moviecard">
      <img src="http://www.pqdong.com/wp-content/uploads/2020/03/tip-img.png">
      <p>啊哦，电影已经下线了</p>
    </el-card>
    <div v-if="!isShow">
      <el-card class="moviecard" style="height: 200px;">
        <img :src="movie.cover" class="avatar">
        <div class="introduce">
          <p class="title">{{movie.name}}</p>
          <p>标签：{{movie.tags}}</p>
          <p>主演：{{movie.actors}}</p>
          <p>评分：{{movie.score}}<span>|</span>投票数：{{movie.votes}}</p>
        </div>
        <el-button class="moviebtn" @click="viewMovie(movie.officialSite)">观看电影</el-button>
      </el-card>
      <el-card class="moviecard">
        <div class="movieintroduce">电影简介</div>
        <div class="movieintroducet">
          <p>导演：{{movie.directors}}</p>
          <p>上映时间：{{movie.releaseDate}}<span>|</span>地区：{{movie.regions}}<span>|</span>语言：{{movie.languages}}</p>
          <p>片长：{{movie.mins}} 分钟</p>
        </div>
      </el-card>
      <el-card class="moviecard">
        <div class="movieintroduce">电影内容</div>
        <div class="moviecontent">
          <p>{{movie.storyline}}</p>
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
      movie: {},
      isShow: false,
    };
  },
  mounted() {
    this.getMovieDetail();
  },
  computed: {
    isLogin() {
      return !!localStorage.getItem('token');
    },
  },
  methods: {
    getMovieDetail() {
      const movieId = localStorage.getItem('movieId');
      fetch
        .getMovieInfo(movieId)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.data === null) {
              this.isShow = true;
            }
            this.movie = res.data.data;
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    viewMovie(url) {
      window.open(url);
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

  div .moviecard {
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

  .movieintroducet {
    /*margin-left: 0px;*/
    margin: 15px auto 15px auto;
    height: 110px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
  }

  .movieintroducet p {
    margin: 5px;
  }

  .introduce p {
    margin: 8px;
  }

  p span {
    margin: 14px;
  }

  .movieintroduce {
    border-left: 5px solid #888;
    text-align: left;
    padding-left: 8px;
  }

  .moviecontent {
    height: 100px;
    margin: 15px auto 15px auto;
    text-align: left;
  }


  .moviebtn {
    float: right;
    margin-bottom: 20px;
  }
</style>
