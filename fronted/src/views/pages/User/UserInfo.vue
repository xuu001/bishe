<template>
  <div>
    <el-row :gutter="20" style="margin-top:10px;">
      <el-col :span="8">
        <div class="grid-content bg-purple">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>个人信息</span>
            </div>
            <div class="name-role">
              <span class="sender">Admin - {{ userInfo.name }}</span>
            </div>
            <div class="register-info">
          <span class="register-info">
            入职时间：<li class="fa fa-clock-o"></li>
             {{ userInfo.createDate }}
          </span>
            </div>
            <el-divider></el-divider>
            <div class="personal-relation">
              <div class="relation-item">手机号:
                <div style="float: right; padding-right:20px;">{{ userInfo.phone }}</div>
              </div>
            </div>
            <div class="personal-relation">
              <div class="relation-item">所属部门:
                <div style="float: right; padding-right:20px;">{{ userInfo.department }}</div>
              </div>
            </div>
            <div class="personal-relation">
              <div class="relation-item">首页链接:
                <div style="float: right; padding-right:20px;">http://eepc.hhang.xyz</div>
              </div>
            </div>
          </el-card>
        </div>
      </el-col>
      <el-col :span="16">
        <div class="grid-content bg-purple">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>基本资料</span>
            </div>
            <div>
              <el-form label-width="80px" v-model="userInfo" size="small" label-position="right">
                <el-form-item label="员工姓名" prop="name">
                  <el-input auto-complete="off" v-model="userInfo.name"></el-input>
                </el-form-item>
                <el-form-item label="年龄" prop="age">
                  <el-input auto-complete="off" v-model="userInfo.age"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="gender">
                  <el-input auto-complete="off" v-model="userInfo.gender"></el-input>
                </el-form-item>
                <el-form-item label="手机号" prop="phone">
                  <el-input auto-complete="off" v-model="userInfo.phone"></el-input>
                </el-form-item>
                <el-form-item label="首页链接" prop="homeUrl">
                  <el-input maxlength="18" v-model="userInfo.homeUrl"></el-input>
                </el-form-item>
                <el-form-item label="家庭地址" prop="homeUrl">
                  <el-input maxlength="18" v-model="userInfo.address"></el-input>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button size="mini" type="primary">提交</el-button>
                <el-button size="mini" type="warning">关闭</el-button>
              </div>
            </div>
          </el-card>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "UserInfo",
  data() {
    return {
      userInfo: {
        homeUrl: 'http://eepc.hhang.xyz',
        gender:''
      },

    }

  },
  create() {

  },
  mounted() {
    this.userInfo = JSON.parse(sessionStorage.getItem("userInfo"))
    console.log(this.userInfo)
    this.userInfo.createDate = this.renderTime(this.userInfo.createDate)
  },
  methods: {
    renderTime(date) {
      let dates = new Date(date).toJSON();
      return new Date(+new Date(dates) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
    }
  }

}
</script>

<style lang="scss" scoped>
/* 卡片样式*/
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.box-card {
  width: 100%;
}

/* 文本样式区*/
.name-role {
  font-size: 16px;
  padding: 5px;
  text-align: center;
}

.sender {
  text-align: center;
}

.register-info {
  text-align: center;
  padding-top: 10px;
}

.personal-relation {
  font-size: 16px;
  padding: 0px 5px 15px;
  margin-right: 1px;
  width: 100%
}

.relation-item {
  text-align: left;

}

.dialog-footer {
  padding-top: 10px;
  padding-left: 10%;
}

/*布局样式区*/
.el-row {
  margin-bottom: 20px;

  &:last-child {
    margin-bottom: 0;
  }
}

.el-col {
  border-radius: 4px;
}

.bg-purple-dark {
  background: #99a9bf;
}

.bg-purple {
  background: #d3dce6;
}

.bg-purple-light {
  background: #e5e9f2;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>
