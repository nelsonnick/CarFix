<template>
  <div>
    <mu-appbar title="车辆详情">
      <mu-icon-button icon='reply' slot="right" @click="goReply"/>
    </mu-appbar>
    <mu-text-field label="车辆牌号" v-model="number" :errorColor="numberErrorColor" :errorText="numberErrorText" @input="checkNumber" fullWidth labelFloat icon="directions_car" maxLength="7"/><br/>
    <mu-text-field label="品牌型号" v-model="brand" fullWidth labelFloat icon="title" maxLength="30" icon="extension"/><br/>
    <mu-text-field label="车辆备注" v-model="remark" fullWidth labelFloat icon="title" maxLength="100" multiLine :rows="3" :rowsMax="6" icon="comment"/><br/>
    <mu-flexbox>
      <mu-flexbox-item class="flex-demo">
        <mu-float-button icon="edit" @click="goEdit" backgroundColor="blue"/>
      </mu-flexbox-item>
      <mu-flexbox-item class="flex-demo">
        <mu-float-button icon="cached" @click="goReset" backgroundColor="orange"/>
      </mu-flexbox-item>
    </mu-flexbox>
    <mu-dialog :open="Reading" title="正在读取" >
      <mu-circular-progress :size="60" :strokeWidth="5"/>请稍后
    </mu-dialog>
    <mu-dialog :open="Saving" title="正在保存" >
      <mu-circular-progress :size="60" :strokeWidth="5"/>请稍后
    </mu-dialog>
    <mu-popup position="bottom" :overlay="false" popupClass="popup-bottom" :open="bottomPopup">
      <mu-icon :value="icon" :size="36" :color="color"/>&nbsp;{{ message }}
    </mu-popup>
  </div>
</template>

<script>
import * as API from './api.js'
export default {
  name: 'Edit',
  data () {
    return {
      Saving: false,
      Reading: true,
      bottomPopup: false,
      icon: '',
      color: '',
      message: '',
      object: [],
      number: '',
      brand: '',
      remark: '',
      numberErrorText: '',
      numberErrorColor: ''
    }
  },
  created () {
    this.fetchData(this.$route.params.id)
  },
  watch: {
    // 如果路由有变化，会再次执行该方法
    '$route': 'fetchData'
  },
  methods: {
    goReply () {
      this.$router.push({ path: '/list' })
    },
    goReset () {
      this.Reading = true
      this.fetchData(this.$route.params.id)
    },
    openPopup (message, icon, color) {
      this.message = message
      this.icon = icon
      this.color = color
      this.bottomPopup = true
      setTimeout(() => { this.bottomPopup = false }, 1500)
    },
    goSave () {
      this.Saving = true
      this.$http.get(
        API.Edit,
        { params: {
          id: this.$route.params.id,
          number: this.number,
          brand: this.brand,
          remark: this.remark
        } },
        { headers: { 'X-Requested-With': 'XMLHttpRequest' } }
      ).then((response) => {
        this.Saving = false
        if (response.body === 'OK') {
          this.openPopup('修改成功！', 'check_circle', 'green')
          setTimeout(() => { this.$router.push({ path: '/list' }) }, 1000)
        } else {
          this.openPopup(response.body, 'report_problem', 'orange')
        }
      }, (response) => {
        this.Saving = false
        this.openPopup('服务器内部错误!', 'error', 'red')
      })
    },
    fetchData (id) {
      this.$http.get(
        API.Get,
        { params: { id: id } },
        { headers: { 'X-Requested-With': 'XMLHttpRequest' } }
      ).then((response) => {
        this.object = response.body
        this.number = this.object.number
        this.brand = this.object.brand
        this.remark = this.object.remark
        this.Reading = false
      }, (response) => {
        this.openPopup('服务器内部错误!', 'error', 'red')
      })
    },
    checkNumber (value) {
      if (value === null || value === undefined || value === '') {
        this.numberErrorText = '车辆牌号为必填项!'
        this.numberErrorColor = 'orange'
      } else if (value.length > 7) {
        this.numberErrorText = '车辆牌号不能超过7个字符'
        this.numberErrorColor = 'orange'
      } else if (value.length < 7) {
        this.numberErrorText = '车辆牌号应为7个字符'
        this.numberErrorColor = 'orange'
      } else {
        this.$http.get(
          API.CheckNumberForEdit,
          { params: { id: this.$route.params.id, number: value } },
          { headers: { 'X-Requested-With': 'XMLHttpRequest' } }
        ).then((response) => {
          if (response.body === 'OK') {
            this.nameErrorText = 'OK'
            this.nameErrorColor = 'green'
          } else {
            this.nameErrorText = response.body
            this.nameErrorColor = 'red'
          }
        }, (response) => {
          this.openPopup('服务器内部错误!', 'error', 'red')
        })
      }
    }
  }
}
</script>
<style lang="css">
  .flex-demo {
    height: 70px;
    text-align: center;
    line-height: 32px;
  }
  .popup-bottom {
    width: 100%;
    opacity: .8;
    height: 48px;
    line-height: 48px;
    display: flex;
    align-items: center;
    justify-content: center;
    max-width: 300px;
  }
</style>
