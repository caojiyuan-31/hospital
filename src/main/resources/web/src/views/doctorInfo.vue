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
        <div class="departmentintroduce">相关评论（点击回复）</div>
        <div class="departmentintroducet">
            <el-tree :data="data"
                     default-expand-all
                     :expand-on-click-node = "false"
                     :props="defaultProps"
                     @node-click="handleNodeClick">
            </el-tree>

            <el-button type="text" @click="dialogVisible = true">新增评论</el-button>


        </div>
      </el-card>
    </div>

    <el-dialog
  title="新增评论"
  :visible.sync="dialogVisible"
  width="30%"
  :before-close="handleClose">
  <el-input v-model="input" placeholder="请输入内容">
     <template slot="prepend">@ {{doctor.name}}</template>
  </el-input>
  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false; input=''">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false; addReply()">确 定</el-button>
  </span>
</el-dialog>

<el-dialog
  title="回复评论"
  :visible.sync="dialogVisible2"
  width="30%"
  :before-close="handleClose">
  <el-input v-model="input" placeholder="请输入内容">
     <template slot="prepend">@ {{replyName}}</template>
  </el-input>
  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible2 = false; input=''">取 消</el-button>
    <el-button type="primary" @click="dialogVisible2 = false; addReply2()">确 定</el-button>
  </span>
</el-dialog>

  </div>


</template>


<script>
import fetch from '../api/fetch';

export default {
  data() {
    return {
      input: '',
      dialogVisible: false,
      dialogVisible2: false,
      replyName: '',
      replyId: '',
      doctor: {},
      data: [],
      defaultProps: {
        children: 'children',
        label: 'label',
        id: 'id',
        value: 'value',
      },
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
              this.getReplyTree();
            }
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },

    getReplyTree() {
      fetch
        .getReplyTree(this.doctor.name)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.data = res.data.data;
            }
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },

    addReply() {
      const reply = {
        parentId: 0,
        toName: this.doctor.name,
        text: this.input,
      };
      fetch
        .addReply(reply)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.$message({
                message: '评论成功',
                type: 'success',
              });
              this.getReplyTree();
              this.input = '';
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
            message: '评论失败',
            type: 'error',
          });
        });
    },

    addReply2() {
      const reply = {
        parentId: this.replyId,
        toName: this.replyName,
        text: this.input,
      };
      fetch
        .addReply(reply)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.$message({
                message: '回复成功',
                type: 'success',
              });
              this.getReplyTree();
              this.input = '';
            } else {
              this.$message({
                message: res.data.msg,
                type: 'warning',
              });
              this.input = '';
            }
          }
        })
      // eslint-disable-next-line no-unused-vars
        .catch((e) => {
          this.$message({
            message: '回复失败',
            type: 'error',
          });
          this.input = '';
        });
    },

    handleNodeClick(data) {
      this.dialogVisible2 = true;
      this.replyName = data.value.fromName;
      this.replyId = data.id;
      console.log(data);
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

  .el-tree__empty-text{
    padding-left: 100px;
    width: 300px;
  }

</style>
