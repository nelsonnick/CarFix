import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import VueResource from 'vue-resource'

import list from './list.vue'
import add from './add.vue'

import MuseUI from 'muse-ui'
import 'muse-ui/dist/muse-ui.css'
import 'material-design-icons/iconfont/material-icons.css'

Vue.config.productionTip = false
Vue.use(VueRouter)
Vue.use(Vuex)
Vue.use(VueResource)
Vue.use(MuseUI)

const routes = [
  { path: '/list', component: list },
  { path: '/add', component: add },
  { path: '/', redirect: '/list' }
]

const router = new VueRouter({
  routes // （缩写）相当于 routes: routes
})

const store = new Vuex.Store({
  state: {
    keyword: '',
    pageCurrent: '1',
    pageSize: '8'
  },
  mutations: {
    save (state, page) {
      state.keyword = page.keyword
      state.pageCurrent = page.pageCurrent
      state.pageSize = page.pageSize
    }
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router: router,
  store: store,
  template: '<router-view></router-view>'
})
