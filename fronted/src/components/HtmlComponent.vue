<template>
  <div>
      <mu-circular-progress :size= "40"  v-if = "loading" />
      <p v-html= "html" ></p>
  </div>

</template>

<script>
export default {
  name: "HtmlComponent",
  props: {
    url: {
      required:  true
    }
  },
  data () {
    return  {
      loading:  false ,
      html:  ''
    }
  },
  watch: {
    url (value) {
      this .load(value)
    }
  },
  mounted () {
    this .load( this .url)
  },
  methods: {
    load (url) {
      if  (url && url.length > 0) {
        // 加载中
        this .loading =  true
        let param = {
          accept:  'text/html, text/plain'
        }
        this .$http.get(url, param).then((response) => {
          this .loading =  false
          // 处理HTML显示
          this .html = response.data
        }). catch (() => {
          this .loading =  false
          this .html =  '加载失败'
        })
      }
    }
  }
}
</script>

<style scoped>

</style>