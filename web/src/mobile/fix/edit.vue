<template>
  <div>
    <mu-appbar title="维修记录">
      <mu-icon-button icon='reply' slot="right" @click="goReply"/>
    </mu-appbar>
    <mu-text-field label="车辆牌号" v-model="number" fullWidth labelFloat icon="directions_car" disabled/>
    <br/>
    <mu-text-field label="品牌型号" v-model="brand" fullWidth labelFloat icon="extension" disabled/>
    <br/>
    <mu-text-field label="当前里程" v-model="mileage" fullWidth labelFloat icon="motorcycle" type="number"/>
    <br/>
    <mu-text-field label="备注说明" v-model="remark" fullWidth labelFloat icon="comment" multiLine :rows="2" :rowsMax="3"/>
    <br/>
    <mu-flexbox justify="space-around" wrap="wrap" align="center">
      <mu-flat-button :label="list.name" @click="list.open=true;dis=true" :icon="list.icon" v-for="list in lists" labelClass="btn" backgroundColor="#FFFFFF" color="deepPurple500" :disabled="dis"/>
    </mu-flexbox>
    <mu-flexbox>
      <mu-raised-button :label="moneys" icon="attach_money" disabled fullWidth/>
    </mu-flexbox>
    <mu-flexbox>
      <mu-flexbox-item class="flex-demo">
        <mu-float-button icon="edit" @click="goSave" backgroundColor="blue"/>
      </mu-flexbox-item>
      <mu-flexbox-item class="flex-demo">
        <mu-float-button icon="delete" @click="Del=true" backgroundColor="red"/>
      </mu-flexbox-item>
      <mu-flexbox-item class="flex-demo">
        <mu-float-button icon="cached" @click="goReset" backgroundColor="orange"/>
      </mu-flexbox-item>
    </mu-flexbox>
    <mu-dialog :open="Reading" title="正在读取">
      <mu-circular-progress :size="60" :strokeWidth="5"/>
      请稍后
    </mu-dialog>
    <mu-dialog :open="Saving" title="正在保存">
      <mu-circular-progress :size="60" :strokeWidth="5"/>
      请稍后
    </mu-dialog>
    <mu-dialog :open="Del" title="确定删除该维修记录?" @close="Del=false">
      <mu-flat-button label="取消" @click="Del=false" />
      <mu-flat-button label="确定" @click="goDel" secondary/>
    </mu-dialog>
    <mu-popup position="bottom" :overlay="false" popupClass="popup-bottom" :open="bottomPopup">
      <mu-icon :value="icon" :size="36" :color="color"/>&nbsp;{{ message }}
    </mu-popup>
    <mu-drawer right :open="list.open" docked="false" v-for="list in lists">
      <mu-appbar :title="list.name">
        <mu-icon-button :icon="list.icon" slot="right"/>
      </mu-appbar>
      <mu-text-field label="金额" v-model="list.money" fullWidth labelFloat icon="attach_money" type="number" @input="if (list.money.toString() !== '') { list.icon = 'check_box' } else { list.icon = 'check_box_outline_blank' }"/>
      <br/>
      <mu-text-field label="详情" v-model="list.detail" fullWidth labelFloat icon="details" multiLine :rows="3" :rowsMax="6"/>
      <br/>
      <mu-flexbox>
        <mu-flexbox-item class="flex-demo">
          <mu-raised-button label="保存" @click="list.open=false;dis=false" fullWidth primary/>
        </mu-flexbox-item>
        <mu-flexbox-item class="flex-demo">
          <mu-raised-button label="清空" @click="list.open=false;dis=false;list.money='';list.detail='';list.icon = 'check_box_outline_blank'" fullWidth secondary/>
        </mu-flexbox-item>
      </mu-flexbox>
    </mu-drawer>
  </div>
</template>

<script>
import * as API from './api.js'
export default {
  name: 'Detail',
  data () {
    return {
      Saving: false,
      Reading: true,
      bottomPopup: false,
      dis: false,
      Del: false,
      icon: '',
      color: '',
      message: '',
      object: [],
      number: '',
      brand: '',
      money: '',
      car_id: '',
      time: '',
      time_next: '',
      remark: '',
      detail: '',
      lists: []
    }
  },
  created () {
    this.fetchData(this.$route.params.id)
  },
  computed: {
    moneys: function () {
      var total = 0
      for (var list in this.lists) {
        var one = 0
        if (this.lists[list].money.toString() !== '') {
          one = parseFloat(this.lists[list].money)
        }
        total = total + one
      }
      this.money = total
      return '总金额：' + total + '元'
    }
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
          mileage: this.mileage,
          money: this.money,
          remark: this.remark,
          detail: JSON.stringify(this.lists)
        } },
        { headers: { 'X-Requested-With': 'XMLHttpRequest' } }
      ).then((response) => {
        this.Saving = false
        if (response.body === 'OK') {
          this.openPopup('保存成功！', 'check_circle', 'green')
          setTimeout(() => { this.$router.push({ path: '/list' }) }, 1000)
        } else {
          this.openPopup(response.body, 'report_problem', 'orange')
        }
      }, (response) => {
        this.Saving = false
        this.openPopup('服务器内部错误!', 'error', 'red')
      })
    },
    goDel () {
      this.Saving = true
      this.$http.get(
        API.Del,
        { params: {
          id: this.$route.params.id
        } },
        { headers: { 'X-Requested-With': 'XMLHttpRequest' } }
      ).then((response) => {
        this.Saving = false
        if (response.body === 'OK') {
          this.openPopup('删除成功！', 'check_circle', 'green')
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
        API.GetFix,
        { params: { id: id } },
        { headers: { 'X-Requested-With': 'XMLHttpRequest' } }
      ).then((res) => {
        this.mileage = res.body.mileage
        this.money = res.body.money
        this.remark = res.body.remark
        this.time = res.body.time
        this.time_next = res.body.time_next
        this.lists = JSON.parse(res.body.detail)
        this.car_id = res.body.car_id
        this.$http.get(
          API.GetCar,
          { params: { id: this.car_id } },
          { headers: { 'X-Requested-With': 'XMLHttpRequest' } }
        ).then((response) => {
          this.object = response.body
          this.number = this.object.number
          this.brand = this.object.brand
          this.Reading = false
        }, (response) => {
          this.openPopup('服务器内部错误!', 'error', 'red')
        })
      }, (res) => {
        this.openPopup('服务器内部错误!', 'error', 'red')
      })
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

  .btn {
    width: 100px;
    height: 32px;
    text-align: center;
    line-height: 32px;
    margin: 15px 1px;
  }
</style>
