<template>
  <div>
    <div>
      <el-form :inline="true" :model="param">
        <el-col :span="6">
          <el-form-item label="姓名">
            <el-input v-model="param.name" placeholder="筛选姓名" clearable prefix-icon="el-icon-search"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="部门">
            <el-input v-model="param.department" placeholder="筛选部门" clearable prefix-icon="el-icon-search"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="日期">
            <el-date-picker v-model="param.data" align="left" placeholder="筛选入职日期" type="date" value-format="yyyy-MM-dd"
                            :picker-options="pickerOptions">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item>
            <el-button type="primary" @click="findUser">查询</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </div>
    <div style="display: flex;margin-bottom: 10px ;float: right">
      <el-button type="primary" @click="toAdd">添加</el-button>
      <el-button type="danger" @click="doDelete()" :disabled="tableSelect.length===0||tableSelect.length>1">删除
      </el-button>
    </div>

    <el-table ref="userTable" :data="tableData" :stripe="true" :border="true" @row-click="rowClick"
              @selection-change="tableSelect=$event" style="width: 100%">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column prop="account" label="账号" width="120"></el-table-column>
      <el-table-column prop="name" label="姓名" width="100"></el-table-column>
      <el-table-column prop="createDate" label="入职时间" width="100"></el-table-column>
      <el-table-column prop="department" label="部门" width="100"></el-table-column>
      <el-table-column prop="mail" label="邮箱" width="190"></el-table-column>
      <el-table-column prop="address" label="家庭住址"></el-table-column>
      <el-table-column prop="status" label="启用状态" width="100" align="center">
        <template #default="{row}">
          <el-tag type="danger" v-if="row.status === 0" size="small">禁用</el-tag>
          <el-tag v-if="row.status === 1" size="small">启用</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="modifyDate" label="修改时间" width="100" align="center"></el-table-column>
      <el-table-column prop="modifyName" label="修改人姓名" width="100"></el-table-column>
      <el-table-column label="操作" width="100">
        <template slot-scope="scope">
          <el-button @click="jinUser(scope.row)" type="text" size="small">禁用</el-button>
          <el-button @click="openUser(scope.row)" type="text" size="small">启用</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background :page-sizes="[8, 16, 30]" @current-change="pageChange($event)"
                   :page-size=this.param.size @size-change="sizeChange($event)"
                   layout="total,prev,pager,next,sizes" :total="total" small>
    </el-pagination>

    <!--=========添加员工-->
    <el-dialog title="添加员工" :visible.sync="isAdding" width="30%" :close-on-click-modal="false" center>
      <el-form :model="user" status-icon :rules="rules" ref="user" label-width="100px" class="user">
        <el-form-item label="姓名" prop="name">
          <el-input v-model.trim="user.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model.trim="user.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pass">
          <el-input type="password" v-model.trim="user.pass" ></el-input>
        </el-form-item>
        <el-form-item label="部门" prop="depart">
          <el-select v-model="departmentSelect" clearable value-key="id" @change="selectDepart" placeholder="请选择部门">
            <el-option v-for="item in departmentList" :key="item.id"
                :label="item.department" :value="item">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="resetForm('user')">重置</el-button>
        <el-button type="primary" @click="submitForm()">确认</el-button>
      </span>
    </el-dialog>

  </div>


</template>

<script>
import check from "@/utils/check";
export default {
  name: "UserInformation",
  data() {
    let checkAccount = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('用户名不能为空'));
      } else {
        callback();
      }
    };
    // let idCardNo = async (rule, value, callback) => {
    //   let city = {
    //     11: "北京",
    //     12: "天津",
    //     13: "河北",
    //     14: "山西",
    //     15: "内蒙古",
    //     21: "辽宁",
    //     22: "吉林",
    //     23: "黑龙江 ",
    //     31: "上海",
    //     32: "江苏",
    //     33: "浙江",
    //     34: "安徽",
    //     35: "福建",
    //     36: "江西",
    //     37: "山东",
    //     41: "河南",
    //     42: "湖北 ",
    //     43: "湖南",
    //     44: "广东",
    //     45: "广西",
    //     46: "海南",
    //     50: "重庆",
    //     51: "四川",
    //     52: "贵州",
    //     53: "云南",
    //     54: "西藏 ",
    //     61: "陕西",
    //     62: "甘肃",
    //     63: "青海",
    //     64: "宁夏",
    //     65: "新疆",
    //     71: "台湾",
    //     81: "香港",
    //     82: "澳门",
    //     91: "国外 "
    //   };
    //   let tip = ""
    //   let pass = true
    //   if (!value || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(value)) {
    //     tip = "身份证号格式错误"
    //     pass = false;
    //   } else if (!city[value.substr(0, 2)]) {
    //     // tip = "地址编码错误"
    //     tip = "身份证输入错误"
    //     pass = false
    //   } else {
    //     // 18位身份证需要验证最后一位校验位
    //     if (value.length === 18) {
    //       value = value.split('')
    //       // ∑(ai×Wi)(mod 11)
    //       // 加权因子
    //       let factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]
    //       // 校验位
    //       let parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2]
    //       let sum = 0
    //       let ai = 0
    //       let wi = 0
    //       for (let i = 0; i < 17; i++) {
    //         ai = value[i]
    //         wi = factor[i]
    //         sum += ai * wi
    //       }
    //       if (parity[sum % 11] !== value[17]) {
    //         // tip = "校验位错误"
    //         tip = "身份证输入错误"
    //         pass = false
    //       }
    //     }
    //   }
    //   if (!pass) {
    //     callback(new Error(tip))
    //   } else {
    //     callback()
    //   }
    // };
    // let validatePass = (rule, value, callback) => {
    //   if (value === '') {
    //     callback(new Error('请输入密码'));
    //   } else if (value.length < 6) {
    //     callback(new Error('密码不得少于6位'));
    //   } else {
    //     if (this.user.checkPass !== '') {
    //       this.$refs.ruleForm.validateField('checkPass');
    //     }
    //     callback();
    //   }
    // };
    return {
      userInfo: [],
      tableData: [],
      tableSelect: {},
      total: 0,
      isAdding: false,
      departmentList:[],
      param: {
        account: '',
        name: '',
        department: '',
        date: '',
        page: 1,
        size: 8,
      },
      departmentSelect: {}, //选取当前值的所有字段都在
      user: {
        account:'',
        departmentId:'',
        name: '',
        id: '',
        phone: '',
        pass: '',
        isDel: 0,
        version: '',
        modifyName: '',
        status: 1,
      },
      rules: {
        pass: [
          {validator: check.validatePass, trigger: 'blur'}
        ],
        phone: [
          {validator: check.checkPhone, trigger: 'blur'}
        ],
        name: [
          {validator: checkAccount, trigger: 'blur'}
        ],
        depart:[]
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
    };
  },
  created() {

  },
  mounted() {
    this.findUser();
    this.userInfo = JSON.parse(sessionStorage.getItem("userInfo"))
    this.user.modifyName = this.userInfo.name
  },
  methods: {
    findUser() {
      this.$axios.get('/api/user/findUser', {params: this.param}).then(res => {
        console.log(res)
        this.tableData = res.data.data.list;
        this.total = res.data.data.total;
      }).catch(e => console.log(e));
    },
    pageChange(page) {
      this.param.page = page;
      this.findUser()
    },
    sizeChange(size) {
      this.param.size = size;
      this.findUser()
    },
    toAdd() {
      this.isAdding = true;
      this.user.createUserName = this.userInfo.name
      this.user.createUserId = this.userInfo.id
      this.getDepartment()
    },
    doDelete() {
      this.$confirm('将要删除该员工, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let userInfo = JSON.parse(sessionStorage.getItem('userInfo'))
        // let ids = this.tableSelect.map(item => item.id);
        // console.log(this.tableSelect[0].id)
        // console.log(userInfo.id)
        if (this.tableSelect[0].id !== userInfo.id) {
          // return this.$axios.delete(`/api/user/delete?ids=${ids}`).then(res => {  //物理删除
          //修改删除标记(逻辑删除用)-----
          this.user.id = this.tableSelect[0].id
          this.user.isDel = 1
          this.user.version = this.tableSelect[0].version
          this.user.modifyName = userInfo.name
          //逻辑删除结束 --------------
          return this.$axios.post(`/api/user/upUser`, this.user).then(res => {  //逻辑删除
            if (res.data.status === 1) {  //删除成功
              this.$notify({
                title: '提示',
                message: '删除成功',
                type: 'success'
              });
              this.findUser();
            } else {
              this.$notify({
                title: '提示',
                message: res.data.message,
                type: 'error'
              });
            }
          }).catch(e => e);
        } else {
          this.$notify({
            title: '提示',
            message: '不能删除当前账号账户',
            type: 'error'
          });
        }
      })
    },
    getDepartment() {
      this.$axios.get('/api/company/findDepart').then(res => {
        this.departmentList = res.data.data
        console.log(this.departmentList)
      })
    },
    rowClick(row) {
      this.$refs.userTable.toggleRowSelection(row)
    },
    jinUser(row) {
      this.user.id = row.id
      this.user.version = row.version
      this.user.status = 0
      if (this.user.id !== this.userInfo.id) {
        console.log(this.user)
        this.$axios.post('/api/user/upUser', this.user).then(res => {
          if (res.data.status === 1) {
            this.$notify({
              title: '提示',
              message: '禁用成功',
              type: 'success'
            });
            this.findUser()
          } else {
            this.$notify({
              title: '提示',
              message: res.data.message,
              type: 'error'
            });
          }
        })
      } else {
        this.$notify({
          title: 'w(ﾟДﾟ)w',
          message: "不能禁用当前账户 ( ఠൠఠ )ﾉ",
          type: 'warning'
        })
      }
    },
    openUser(row) {
      this.user.id = row.id
      this.user.version = row.version
      this.user.status = 1
      if (this.user.id !== this.userInfo.id) {
        this.$axios.post('/api/user/upUser', this.user).then(res => {
          if (res.data.status === 1) {
            this.$notify({
              title: '提示',
              message: '启用成功',
              type: 'success'
            });
            this.findUser()
          } else {
            this.$notify({
              title: '提示',
              message: res.data.message,
              type: 'error'
            });
          }
        })
      } else {
        this.$notify({
          title: '(≧∇≦)ﾉ',
          message: '不能在开启啦 (≧∀≦)ゞ',
          type: 'warning'
        });
      }
    },

    submitForm() {
      let num = this.generateAccount()
      num += this.departmentSelect.code + ""
      //随机生成账号加入部门编号结尾
      this.user.account = num
      this.user.departmentId = this.departmentSelect.id
      this.user.count = this.departmentSelect.count
      console.log(this.user)
      this.$axios.post('/api/user/saveUser', this.user).then(res => {
        if (res.data.status === 1) {
            this.$notify({
              title: '提示',
              message: '添加成功!',
              type: 'success'
            })
          this.isAdding = false;
          this.findUser()
          //添加成功后从后台立刻查出该账号的id
          this.param.account = num
        } else {
          this.$notify({
            title: '提示',
            message: res.data.message,
            type: 'error'
          })
        }
      })
    },
    resetForm(user) {
      this.$refs[user].resetFields();
    },
    selectData() {
      this.findUser();
    },
    /**  20220517xx
     * 当前年月日生成账号前缀 在加两位随机数
     * @returns {string}
     */
    generateAccount(){
      const date = new Date()
      const year = date.getFullYear()
      const month = date.getMonth()+1
      const day = date.getDate()
      const lastNum = Math.floor(Math.random()*90 +9)
      let num = year + ""
      if (month < 10)  num += "0";
      num += month + ""
      if (day <10 ) num += "0"
      num += day + ""
      if (lastNum <10) num += "0"
      num += lastNum + ""
      return(num)
      // console.log(num)
    },
    selectDepart(item){
      this.user.departmentCode = item.code
    },
  }
}
</script>

<style scoped>

</style>
