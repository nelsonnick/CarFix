<template>
  <div>
    <mu-appbar title="欢迎使用汽车保养管理系统"/>
    <mu-flexbox>
      <mu-flexbox-item >
        <mu-text-field hintText="登录名" icon="assignment_ind" v-model="name" fullWidth/><br/>
        <mu-text-field hintText="密码" icon="blur_on" v-model="pass" type="password" fullWidth/><br/>
      </mu-flexbox-item>
    </mu-flexbox>
    <mu-raised-button label="登录" fullWidth @click="goLogin" primary/>
    <mu-popup position="bottom" :overlay="false" popupClass="popup-bottom" :open="bottomPopup">
      <mu-icon :value="icon" :size="36" :color="color"/>&nbsp;{{ message }}
    </mu-popup>
  </div>
</template>
<script>
  import * as API from './api.js'
  export default {
    name: 'login',
    data () {
      return {
        open: false,
        bottomPopup: false,
        message: '',
        icon: '',
        color: '',
        name: '',
        pass: ''
      }
    },
    methods: {
      goLogin () {
        if (this.name.toString() === '') {
          this.openPopup('登录名不能为空!', 'report_problem', 'orange')
        } else if (this.pass.toString() === '') {
          this.openPopup('密码不能为空!', 'report_problem', 'orange')
        } else {
          this.$http.get(
            API.Login,
            { params: {
              name: this.name,
              pass: this.pass
            } },
            { headers: { 'X-Requested-With': 'XMLHttpRequest' } }
          ).then((response) => {
            if (response.body === 'OK') {
              window.location.href = '/car'
            } else {
              this.openPopup('服务器内部错误!', 'error', 'red')
            }
          }, (response) => {
            this.openPopup('服务器内部错误!', 'error', 'red')
          })
        }
      },
      openPopup (message, icon, color) {
        this.message = message
        this.icon = icon
        this.color = color
        this.bottomPopup = true
        setTimeout(() => { this.bottomPopup = false }, 2000)
      }
    }
  }
</script>

