<template>
  <div>
    <mu-appbar title="人员详情">
      <mu-icon-button icon='reply' slot="right" @click="goReply"/>
    </mu-appbar>
    <mu-text-field label="人员姓名" v-model="name" :errorColor="nameErrorColor" :errorText="nameErrorText" @input="checkName" fullWidth labelFloat icon="person"/><br/>
    <mu-text-field label="联系电话" v-model="phone" :errorColor="phoneErrorColor" :errorText="phoneErrorText" @input="checkPhone" fullWidth labelFloat icon="phone" maxLength="11"/><br/>
    <mu-text-field label="openId" v-model="openId" fullWidth labelFloat icon="chat"/><br/>
    <mu-flexbox>
      <mu-flexbox-item class="flex-demo">
        <mu-float-button icon="edit" @click="goSave" backgroundColor="blue"/>
      </mu-flexbox-item>
      <mu-flexbox-item class="flex-demo">
        <mu-float-button icon="call" @click="goCall" backgroundColor="red"/>
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
      name: '',
      phone: '',
      openId: '',
      nameErrorText: '',
      nameErrorColor: '',
      phoneErrorText: '',
      phoneErrorColor: ''
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
    goCall () {
      this.phone.toString() === '' ? this.openPopup('无联系电话!', 'report_problem', 'orange') : window.location.href = 'tel:' + this.phone
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
          name: this.name,
          phone: this.phone,
          openId: this.openId
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
        this.name = this.object.name
        this.phone = this.object.phone
        this.openId = this.object.openId
        this.Reading = false
      }, (response) => {
        this.openPopup('服务器内部错误!', 'error', 'red')
      })
    },
    checkName (value) {
      if (value === null || value === undefined || value === '') {
        this.nameErrorText = '人员姓名为必填项!'
        this.nameErrorColor = 'orange'
      } else {
        this.nameErrorText = 'OK'
        this.nameErrorColor = 'green'
      }
    },
    checkPhone (value) {
      if (value === null || value === undefined || value === '') {
        this.phoneErrorText = '手机号码为必填项!'
        this.phoneErrorColor = 'orange'
      } else if (!value.match(/^1(3|4|5|7|8)\d{9}$/)) {
        this.phoneErrorText = '手机号码格式错误!'
        this.phoneErrorColor = 'orange'
      } else {
        this.$http.get(
          API.CheckPhoneForEdit,
          { params: { id: this.$route.params.id, phone: value } },
          { headers: { 'X-Requested-With': 'XMLHttpRequest' } }
        ).then((response) => {
          if (response.body === 'OK') {
            this.phoneErrorText = 'OK'
            this.phoneErrorColor = 'green'
          } else {
            this.phoneErrorText = response.body
            this.phoneErrorColor = 'red'
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
