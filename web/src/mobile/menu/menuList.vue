<template>
  <div class="Menu">
    <mu-drawer :open="open" @close="closeMenu">
      <mu-appbar :title="menuTitle" @click.native="closeMenu">
        <mu-icon-button icon='close' slot="right"/>
      </mu-appbar>
      <mu-list>
        <mu-list-item title="快捷录入" @click.native="goQuick">
          <mu-icon slot="left" value="add_box" color="red300"/>
        </mu-list-item>
        <mu-list-item title="车辆信息" @click.native="goCar">
          <mu-icon slot="left" value="directions_car" color="purple300"/>
        </mu-list-item>
        <mu-list-item title="维修记录" @click.native="goFix">
          <mu-icon slot="left" value="build" color="cyan300"/>
        </mu-list-item>
        <mu-list-item title="客户信息" @click.native="goPerson">
          <mu-icon slot="left" value="account_box" color="green300"/>
        </mu-list-item>
        <mu-list-item title="关系设定" @click.native="goOwn">
          <mu-icon slot="left" value="attachment" color="yellow300"/>
        </mu-list-item>
      </mu-list>
    </mu-drawer>
  </div>
</template>
<script>
  export default {
    name: 'Menu',
    props: ['open', 'menu'],
    data () {
      return {
        menuTitle: '汽车保养'
      }
    },
    methods: {
      openMenu () {
        this.open = true
      },
      closeMenu () {
        this.open = false
        this.$emit('closeMenu')
      },
      goQuick () {
        window.location.href = '/quick'
      },
      goCar () {
        window.location.href = '/car'
      },
      goFix () {
        window.location.href = '/fix'
      },
      goPerson () {
        window.location.href = '/person'
      },
      goOwn () {
        window.location.href = '/own'
      }
    }
  }
</script>
