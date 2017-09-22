<template>
  <div>
    <mu-appbar title="">
      <mu-icon-button icon='menu' slot="left" @click="openMenu"/>
      <mu-text-field icon="search" class="appbar-search-field" hintText="请输入关键词" @input="goQuery" :value="keyword"/>
      <mu-icon-button icon='add' slot="right" @click="goAdd"/>
    </mu-appbar>
    <mu-list>
      <mu-list-item v-for="object in list" :value="object.id" :title="object.number" :describeText="object.brand" @click="goSheet(object.id, object.number)">
      </mu-list-item>
    </mu-list>
    <mu-flexbox>
      <mu-flexbox-item class="flex-demo">
        <mu-flat-button v-if="before" label="上一页" icon="navigate_before" @click="pageBefore" />
      </mu-flexbox-item>
      <mu-flexbox-item class="flex-demo">
        <div v-if="chip">
          {{pageCurrent}}/{{pageTotal}}
        </div>
      </mu-flexbox-item>
      <mu-flexbox-item class="flex-demo">
        <mu-flat-button v-if="next" label="下一页" labelPosition="before" icon="navigate_next" @click="pageNext" />
      </mu-flexbox-item>
    </mu-flexbox>
    <br/>
    <menuList :open="open" v-on:closeMenu="closeMenu" :menu="menu"></menuList>
    <mu-popup position="bottom" :overlay="false" popupClass="popup-bottom" :open="bottomPopup">
      <mu-icon :value="icon" :size="36" :color="color"/>&nbsp;{{ message }}
    </mu-popup>
    <mu-bottom-sheet :open="bottomSheet" @close="goClose">
      <mu-sub-header>{{number}}</mu-sub-header>
      <mu-raised-button class="raised-button" label="修改" @click="goEdit" icon="edit" backgroundColor="blue" fullWidth/>
      <mu-raised-button class="raised-button" label="维修" @click="goFix" icon="build" backgroundColor="green" fullWidth/>
      <mu-raised-button class="raised-button" label="取消" @click="goClose" icon="undo" backgroundColor="grey" fullWidth/>
    </mu-bottom-sheet>
  </div>
</template>

<script>
  import * as API from './api.js'
  import MenuList from '../menu/menuList'
  export default {
    name: 'list',
    components: {
      'menuList': MenuList
    },
    data () {
      return {
        before: false,
        next: false,
        chip: false,
        open: false,
        bottomSheet: false,
        menu: [],
        icon: '',
        color: '',
        message: '',
        id: '',
        number: '',
        keyword: '',
        list: [],
        pageTotal: '',
        pageSize: '',
        pageCurrent: ''
      }
    },
    created: function () {
      this.keyword = this.$store.state.keyword
      this.pageCurrent = this.$store.state.pageCurrent
      this.pageSize = this.$store.state.pageSize
      this.getLists()
    },
    methods: {
      openMenu () {
        this.open = true
      },
      closeMenu () {
        this.open = false
      },
      openPopup (message, icon, color) {
        this.message = message
        this.icon = icon
        this.color = color
        this.bottomPopup = true
        setTimeout(() => { this.bottomPopup = false }, 1500)
      },
      goSheet (id, number) {
        this.id = id
        this.number = number
        this.bottomSheet = true
      },
      goClose () {
        this.id = ''
        this.number = ''
        this.bottomSheet = false
      },
      getLists () {
        this.$http.get(
          API.Query,
          { params: {
            keyword: this.$store.state.keyword,
            pageCurrent: this.$store.state.pageCurrent,
            pageSize: this.$store.state.pageSize
          } },
          { headers: { 'X-Requested-With': 'XMLHttpRequest' }, emulateJSON: true }
        ).then((response) => {
          this.$http.get(
            API.Total,
            { params: {
              keyword: this.$store.state.keyword,
              pageSize: this.$store.state.pageSize
            } },
            { headers: { 'X-Requested-With': 'XMLHttpRequest' } }
          ).then((res) => {
            this.list = response.body
            this.$store.state.pageCurrent.toString() === '1' ? this.before = false : this.before = true
            this.pageTotal = res.body
            this.pageTotal === '0' ? this.chip = false : this.chip = true
            this.pageTotal === '1' || this.pageTotal === '0' || this.pageTotal.toString() === this.$store.state.pageCurrent.toString() ? this.next = false : this.next = true
          }, (response) => {
            this.openPopup('服务器内部错误!', 'error', 'red')
          })
        }, (response) => {
          this.openPopup('服务器内部错误!', 'error', 'red')
        })
      },
      goQuery (keyword) {
        this.keyword = keyword
        this.pageCurrent = '1'
        this.$store.commit('save', {
          keyword: this.keyword,
          pageCurrent: this.pageCurrent,
          pageSize: this.pageSize
        })
        this.getLists()
        this.before = false
      },
      goEdit () {
        this.$router.push({ path: '/edit/' + this.id })
        this.$store.commit('save', {
          keyword: this.keyword,
          pageCurrent: this.pageCurrent,
          pageSize: this.pageSize
        })
      },
      goFix () {
        this.$router.push({ path: '/detail/' + this.id })
        this.$store.commit('save', {
          keyword: this.keyword,
          pageCurrent: this.pageCurrent,
          pageSize: this.pageSize
        })
      },
      pageBefore () {
        this.pageCurrent--
        this.$store.commit('save', {
          keyword: this.keyword,
          pageCurrent: this.pageCurrent,
          pageSize: this.pageSize
        })
        this.pageCurrent.toString() === '1' ? this.before = false : this.before = true
        this.next = true
        this.getLists()
      },
      pageNext () {
        this.pageCurrent++
        this.$store.commit('save', {
          keyword: this.keyword,
          pageCurrent: this.pageCurrent,
          pageSize: this.pageSize
        })
        this.pageCurrent.toString() === this.pageTotal ? this.next = false : this.next = true
        this.before = true
        this.getLists()
      },
      goAdd () {
        this.$router.push({ path: '/add' })
      }
    }
  }
</script>
<style lang="less">
  .appbar-search-field{
    color: #FFF;
    margin-bottom: 0;
    &.focus-state {
      color: #FFF;
    }
    .mu-text-field-hint {
      color: fade(#FFF, 54%);
    }
    .mu-text-field-input {
      color: #FFF;
    }
    .mu-text-field-focus-line {
      background-color: #FFF;
    }
  }
  .flex-demo {
    height: 70px;
    text-align: center;
    line-height: 32px;
  }
  .raised-button {
    margin: 3px 1px 3px;
  }
</style>
