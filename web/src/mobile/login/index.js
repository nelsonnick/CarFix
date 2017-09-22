import Vue from 'vue'
import login from './login.vue'
import VueResource from 'vue-resource'

import MuseUI from 'muse-ui'
import 'muse-ui/dist/muse-ui.css'
import 'material-design-icons/iconfont/material-icons.css'

Vue.config.productionTip = false
Vue.use(VueResource)
Vue.use(MuseUI)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  template: '<login/>',
  components: { login }
})
