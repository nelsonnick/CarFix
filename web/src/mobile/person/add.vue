<template>
  <div>
    <mu-appbar title="添加新人员">
      <mu-icon-button icon='reply' slot="right" @click="gorReply" />
    </mu-appbar>
    <mu-text-field label="人员姓名" v-model="name" :errorColor="nameErrorColor" :errorText="nameErrorText" @input="checkName" fullWidth labelFloat icon="person"/><br/>
    <mu-text-field label="联系电话" v-model="phone" :errorColor="phoneErrorColor" :errorText="phoneErrorText" @input="checkPhone" fullWidth labelFloat icon="phone" maxLength="11"/><br/>
    <mu-dialog :open="Saving" title="正在保存" >
      <mu-circular-progress :size="60" :strokeWidth="5"/>请稍后
    </mu-dialog>
    <mu-flexbox>
      <mu-flexbox-item class="flex-demo">
        <mu-float-button icon="save" @click="goSave" backgroundColor="green" />
      </mu-flexbox-item>
      <mu-flexbox-item class="flex-demo">
        <mu-float-button icon="cached" @click="goReset" backgroundColor="orange"/>
      </mu-flexbox-item>
    </mu-flexbox>
    <mu-popup position="bottom" :overlay="false" popupClass="popup-bottom" :open="bottomPopup">
      <mu-icon :value="icon" :size="36" :color="color"/>&nbsp;{{ message }}
    </mu-popup>
  </div>
</template>

<script>
import * as API from './api.js'
export default {
  name: 'Add',
  data () {
    return {
      bottomPopup: false,
      Saving: false,
      icon: '',
      color: '',
      name: '',
      phone: '',
      message: '',
      nameErrorText: '',
      nameErrorColor: '',
      phoneErrorText: '',
      phoneErrorColor: ''
    }
  },
  methods: {
    gorReply () {
      this.$router.push({ path: '/list' })
    },
    openPopup (message, icon, color) {
      this.message = message
      this.icon = icon
      this.color = color
      this.bottomPopup = true
      setTimeout(() => { this.bottomPopup = false }, 1500)
    },
    goReset () {
      this.name = ''
      this.nameErrorText = ''
      this.nameErrorColor = ''
      this.phone = ''
      this.phoneErrorText = ''
      this.phoneErrorColor = ''
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
          API.CheckPhoneForAdd,
          { params: { phone: value } }
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
    },
    goSave () {
      this.Saving = true
      this.$http.get(
        API.Save,
        { params: {
          name: this.name,
          phone: this.phone
        } }
      ).then((response) => {
        this.Saving = false
        if (response.body === 'OK') {
          this.openPopup('保存成功！', 'check_circle', 'green')
          setTimeout(() => { this.$router.push({ path: '/list' }) }, 1000)
        } else {
          this.openPopup(response.body, 'report_problem', 'orange')
        }
        this.Saving = false
      }, (response) => {
        this.Saving = false
        this.openPopup('服务器内部错误!', 'error', 'red')
      })
    }
  }
}
</script>
<style lang="css">
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
  .flex-demo {
    height: 70px;
    text-align: center;
    line-height: 32px;
  }
</style>
