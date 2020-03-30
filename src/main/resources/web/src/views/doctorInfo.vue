<template>


  <div>
    <div>
      <el-card class="departmentcard" style="height: 200px;">
        <img :src="doctor.url" class="avatar">
        <div class="introduce">
          <p class="title">{{doctor.name}}</p>
          <p>介绍：{{doctor.description}}</p>
          <p>技能：{{doctor.skill}}</p>
          <el-button type="primary" plain @click="dialogVisible3 = true">点击挂号</el-button>
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
  title="选择挂号时间"
  :visible.sync="dialogVisible3"
  width="20%"
  :before-close="handleClose">
  <el-date-picker
      v-model="date"
      type="date"
      :picker-options="pickerOptions"
      placeholder="选择日期"
      value-format="yyyy-MM-dd"
      style="width:100%; margin : 10px 0px 10px 0px;">
  </el-date-picker>

  <el-select v-model="scope" placeholder="请选择时间段" style="width:100%; margin : 10px 0px 10px 0px;">
    <el-option
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.value">
    </el-option>
  </el-select>

  <el-input
    type="textarea"
    :rows="2"
    placeholder="请输入病症描述"
    v-model="text"
    style="width:100%; margin : 10px 0px 10px 0px;">
  </el-input>

  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible3 = false; date = '', scope = 0">取 消</el-button>
    <el-button type="primary" @click="dialogVisible3 = false; addRegister()">确 定</el-button>
  </span>
</el-dialog>

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
      pickerOptions: {
        disabledDate: time => this.dealDisabledDate(time),
      },
      input: '',
      dialogVisible: false,
      dialogVisible2: false,
      dialogVisible3: false,
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
      scope: 0,
      date: '',
      text: '',
      options: [{
        value: 0,
        label: '上午（9：00-12：00）',
      }, {
        value: 1,
        label: '下午（14：00-18：00）',
      }],
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
      this.text = localStorage.getItem('text');
      fetch
        .getDoctorInfo(id)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.doctor = res.data.data;
              this.getInfoOfDoctorId();
            }
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },

    getInfoOfDoctorId() {
      const id = localStorage.getItem('doctorId');
      fetch
        .getInfoOfDoctorId(id)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              console.log(res.data.data);
              this.doctor.name = res.data.data;
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
          this.input = '';
          this.date = '';
          this.scope = 0;
          done();
        })
        .catch((_) => {});
    },

    dealDisabledDate(time) {
      const times = Date.now();
      return time.getTime() <= times;
    },

    addRegister() {
      const register = {
        date: this.date,
        scope: this.scope,
        text: this.text,
        doctorId: this.doctor.id,
      };
      fetch
        .addRegister(register)
        .then((res) => {
          if (res.status === 200) {
            if (res.data.code === '00000') {
              this.$message({
                message: '挂号成功',
                type: 'success',
              });
              this.date = '';
              this.scope = 0;
            } else {
              this.$message({
                message: res.data.msg,
                type: 'warning',
              });
              this.date = '';
              this.scope = 0;
            }
          }
        })
      // eslint-disable-next-line no-unused-vars
        .catch((e) => {
          this.$message({
            message: '挂号失败',
            type: 'error',
          });
          this.date = '';
          this.scope = 0;
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

  .el-tree__empty-text{
    padding-left: 100px;
    width: 300px;
  }

</style>
