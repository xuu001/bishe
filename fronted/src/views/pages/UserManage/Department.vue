<template>
  <el-container style="height: 610px">
    <el-aside width="300px" style="background: white">
      <el-input style="margin-top: 25px" placeholder="输入员工名进行过滤" v-model="filterText" size="small">
      </el-input>
      <el-tree class="filter-tree" :data="list" :props="defaultProps" default-expand-all
               :filter-node-method="filterNode" ref="tree" v-loading="loading"
               empty-text="加载中..." element-loading-spinner="el-icon-loading"
               element-loading-background="rgba(0, 0, 0, 0.3)">
      </el-tree>
    </el-aside>

    <el-main style="height: 620px">
      <el-row :gutter="16">
        <el-col :span="3" :offset="21">
          <el-button type="primary" @click="toAdd()" size="small">新增</el-button>
        </el-col>
      </el-row>
      <el-table :data="departmentList" style="width: 100%">
        <el-table-column prop="department" label="部门">
        </el-table-column>
        <el-table-column prop="code" label="编号">
        </el-table-column>
        <el-table-column prop="count" label="人数" sortable>
        </el-table-column>
        <el-table-column prop="createDate" label="创建时间">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button class="el-icon-edit-outline" type="text" size="small">编辑</el-button>
            <el-button class="el-icon-delete" @click="del(scope.row)" type="text" size="small">删除</el-button>
          </template>
        </el-table-column>

      </el-table>
    </el-main>
    <!-- 新增部门的弹窗 -->
    <el-dialog title="新增部门" :visible.sync="isAdding" width="30%" :close-on-click-modal="false" center>
      <el-form :model="depart" status-icon :rules="rules" ref="depart" label-width="100px" class="user">
        <el-form-item label="部门名称" prop="department">
          <el-input v-model.trim="depart.department" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="编号" prop="code">
          <el-input v-model="depart.code" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="resetForm('depart')">重置</el-button>
        <el-button type="primary" @click="add('depart')">确认</el-button>
      </span>
    </el-dialog>
  </el-container>
</template>

<script>
export default {
  name: "Department",
  data() {
    let checkDepart = (rule, value, callback) => {
      let reg = /^[^\s]*$/
      if (!reg.test(value)) {
        return callback(new Error('不能含有空格'))
      } else {
        callback()
      }
    }
    let checkCode = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入部门编码'))
      } else if (!Number(value)) {
        callback(new Error('请输入数字值'))
      } else {
        callback()
      }
    }
    return {
      loading: true,
      filterText: '',
      departmentList: {},
      param: {
        page: 1,
        size: 999,
      },
      isAdding: false,
      depart: {
        department: '',
        code: '',
        createDate: '',
        createUserName: '',
        createUserId: '',
      },
      child: [],
      data: [],
      list: [],
      data1: [
        {label: '111', children: [{label: '1q1q1'}, {label: '202020'}]},
        {label: '2222'}
      ],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      rules: {
        department: [{required: true, message: '请输入部门名称', triangle: 'blur',},
          {min: 3, max: 8, message: '请输入2-8个字符的部门名称', triangle: 'blur'},
          {validator: checkDepart, triangle: 'blur'}],
        code: [{validator: checkCode, triangle: 'blue'},
          {required: true},
          {min:2,max:2,message:'请输入两位部门编号'}]
      }
    };
  },
  mounted() {
    this.getUser()
    this.getDepartment()
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    getDepartment() {   // 得到部门
      this.$axios.get('/api/company/findDepart').then(res => {
        this.departmentList = res.data.data
        this.data = res.data.data.map(item => ({label: item.department, id: item.id}))
        //该控件会优先渲染父，子还未传入 后续data已经有子，但是已经不渲染了 需要加个延迟将数据传 list 控件得到的list为全值
        setTimeout(() => {
          this.list = this.data
          this.loading = false
        }, 2000)
        console.log(this.data)
      })
    },

    getUser() {
      this.$axios.get('/api/user/findUser', {params: this.param}).then(res => {
        this.child = res.data.data.list.map(item => ({label: item.name, id: item.id, departmentId: item.departmentId}))
        this.data.forEach(list => {
          list.children = []
          this.child.forEach(item => {
            if (item.departmentId === list.id) {
              list.children.push(item)
            }
          })
        })
      }).catch(e => console.log(e));
    },
    toAdd() {
      this.isAdding = true
      let userInfo = JSON.parse(sessionStorage.getItem('userInfo'))
      this.depart.createUserName = userInfo.name
      this.depart.createUserId = userInfo.id
    },
    add(depart) {
      this.$refs[depart].validate(valid => {
        if (valid) {
          //将部门列表处理成['xx部','bb部','cc部']的形式
          const list = this.departmentList.map(item => (item.department))
          const codeList = this.departmentList.map(item => (item.code))
          //用includes方法检查数组中是否存在指定元素  返回true  false 判断新增部门是否存在
          const equal = list.includes(this.depart.department)
          const coddEqual = codeList.includes(this.depart.code)
          // console.log(!equal) //先判断部门是否存在 在判断编号是否存在 这些都唯一!!
          if (!equal) {
            if (!coddEqual) {
              this.$axios.post('/api/company/add', this.depart).then(res => {
                console.log(res)
                if (res.data.status === 1) {
                  this.$notify({
                    title: 'q(≧▽≦q)',
                    message: `成功新增` + this.depart.department,
                    type: 'success'
                  })
                  this.isAdding = false
                  this.getDepartment()
                  this.getUser()
                  this.resetForm('depart')
                } else {
                  this.$notify({
                    title: '╥﹏╥..',
                    message: this.depart.department + '新增失败',
                    type: 'error'
                  })
                  this.resetForm('depart')
                }
              })
            } else {
              this.$notify({
                title: 'w(ﾟДﾟ)w',
                message: '部门编号：' + this.depart.code + '已存在',
                type: 'warning'
              })
              this.resetForm('depart')
            }
          } else {
            this.$notify({
              title: 'w(ﾟДﾟ)w',
              message: this.depart.department + '已存在',
              type: 'warning'
            })
          }
        } else {
          return false
        }
      })
    },
    del(row) {
      console.log(row)
      if (row.count === 0) {
        this.$confirm('确定删除该部门吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const id = row.id
          this.$axios.delete(`/api/company/delete?ids=${id}`).then(res => {
            if (res.data.status === 1) {
              this.$notify({
                title: 'ヾ(≧▽≦*)o',
                message: '已经删除' + row.department,
                type: 'success'
              });
            }
            this.getDepartment()
            this.getUser()
          })
        })
      } else {
        this.$notify({
          title: 'w(ﾟДﾟ)w',
          message: row.department + '还有员工，不能删除',
          type: 'warning'
        });
      }
    },
    update() {

    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    resetForm(depart) {
      this.$refs[depart].resetFields()
    }
  }

}
</script>

<style scoped>

</style>
