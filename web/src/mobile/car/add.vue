<template>
  <div>
    <mu-appbar title="添加新车辆">
      <mu-icon-button icon='reply' slot="right" @click="gorReply" />
    </mu-appbar>
    <mu-text-field label="车辆牌号" v-model="number" :errorColor="numberErrorColor" :errorText="numberErrorText" @input="checkNumber" fullWidth labelFloat icon="directions_car" maxLength="7"/><br/>
    <mu-text-field label="品牌型号" v-model="brand" fullWidth labelFloat maxLength="30" icon="extension"/><br/>
    <mu-text-field label="车辆备注" v-model="remark" fullWidth labelFloat maxLength="100" multiLine :rows="3" :rowsMax="6" icon="comment"/><br/>
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
      number: '鲁A',
      brand: '',
      remark: '',
      message: '',
      numberErrorText: '',
      numberErrorColor: ''
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
      this.number = '鲁A'
      this.numberErrorText = ''
      this.numberErrorColor = ''
      this.brand = ''
      this.remark = ''
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
          API.CheckNumberForAdd,
          { params: { number: value } }
        ).then((response) => {
          if (response.body === 'OK') {
            this.numberErrorText = 'OK'
            this.numberErrorColor = 'green'
          } else {
            this.numberErrorText = response.body
            this.numberErrorColor = 'red'
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
          number: this.number,
          brand: this.brand,
          remark: this.remark
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
