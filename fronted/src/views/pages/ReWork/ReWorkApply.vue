<template>
  <div>
    <el-card>
      <el-form label-position="right" :inline="true" :model="reWorkApply" :rules="rules" ref="reWorkApply"
               style="height: 400px">
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="是否隔离结束" prop="quarantine">
              <el-radio-group v-model="reWorkApply.quarantine">
                <el-radio v-for="(item, index) in quarantine" :key="index" :label="item.value" border>
                  {{ item.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="复工前所在地" prop="returnPlace">
             <el-input v-model="reWorkApply.returnPlace"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="抗原结果" prop="nucleicAcid">
              <el-radio-group v-model="reWorkApply.nucleicAcid">
                <el-radio v-for="(item, index) in nucleicAcid" :key="index" :label="item.value" border>
                  {{ item.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="13">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="reWorkApply.remarks"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="抗原结果证明" prop="file">
              <el-upload ref="files" action="#" list-type="picture-card" :auto-upload="false" :limit="1" :on-remove="handleRemove"
                         :file-list="file" :before-upload="beforeAvatarUpload" :on-change="upFile">
                <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过1024kb</div>
                <i slot="default" class="el-icon-plus"></i>
                <div slot="file" slot-scope="{file}">
                  <img class="el-upload-list__item-thumbnail" :src="file.url" alt="">
                  <span class="el-upload-list__item-actions">
                      <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
                        <i class="el-icon-zoom-in"></i>
                       </span>
                       <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleRemove(file)">
                         <i class="el-icon-delete"></i>
                       </span>
                  </span>
                </div>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>


        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
        <el-row>
          <el-form-item>
            <el-button type="primary" @click="onSubmit('reWorkApply')">提交</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "ReWorkApply",
  data() {
    return {
      file: [],
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '明天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() + 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周后',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() + 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
      reWorkApply: {
        returnPlace:'',
        quarantine: '',
        nucleicAcid: '',
        remarks:'',
      },
      rules: {},
      quarantine: [{
        "label": "否",
        "value": 0
      }, {
        "label": "是",
        "value": 1
      }],
      nucleicAcid: [{
        "label": "阴性",
        "value": 0
      }, {
        "label": "阳性",
        "value": 1
      }],
    }
  },
  mounted() {
    let userInfo = this.userInfo = JSON.parse(sessionStorage.getItem("userInfo"))
    this.reWorkApply.accountId = userInfo.id
    this.reWorkApply.createUserName = userInfo.name

  },
  methods: {
    onSubmit(apply) {
      this.$refs[apply].validate(val =>{
        if (val){
          let formData = new FormData()
          formData.append('file',this.file[0].raw)
          for (let key of Object.keys(this.reWorkApply)){
            formData.append(key,this.reWorkApply[key])
          }
          this.$axios.post('/api/reWork/apply',formData,{
            headers:{'Content-Type': 'multipart/form-data;charset=utf-8'}
          }).then(res =>{
            if (res.data.status ===1){
              this.$notify({
                title: 'ヾ(≧▽≦*)o',
                message: '申请提交成功',
                type: 'success'
              })
              this.$refs[apply].resetFields();
            }else {
              this.$notify({
                title: '提示',
                message: res.data.message,
                type: 'error'
              });
            }
          })
        }else return false
      })


    },
    handleRemove(file) {
      this.$confirm(`确定移除 ${file.name}？`).then(() => {
        this.$refs.files.handleRemove(file)
      })

    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png'
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG && !isPNG) {
        this.$message.error('上传头像图片只能是 JPG/PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    upFile(file, fileList) {
      console.log(fileList);
      this.file.splice(0, this.file.length - 1);
      let url = window.URL.createObjectURL(new Blob([file.raw]));
      this.file = [{
        url: url,
        raw: file.raw,
        name: file.name,
      }]
    },
  }

}
</script>

<style scoped>

</style>
