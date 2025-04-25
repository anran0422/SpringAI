<template>
  <div id="globalHeader">
    <a-row :wrap="false">
      <a-col flex="200px">
        <router-link to="/">
          <div class="title-bar">
            <img class="logo" src="../assets/cloud.png" alt="logo" />
            <div class="title">云图库</div>
          </div>
        </router-link>
      </a-col>
      <a-col flex="auto">
        <a-menu
          v-model:selectedkeys="current"
          mode="horizontal"
          :items="items"
          @click="doMenuClick"
        />
      </a-col>

      <a-col flex="200px">
        <div v-if="loginUserStore.loginUser.id">
          <a-dropdown>
            <ASpace>
              <a-avatar :src="loginUserStore.loginUser.userAvatar" />
              {{ loginUserStore.loginUser.userName ?? '无名' }}
            </ASpace>
            <template #overlay>
              <a-menu>
                <a-menu-item @click="doLogout">
                  <LogoutOutlined />
                  退出登录
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </div>

      </a-col>
    </a-row>
  </div>
</template>

<script lang="ts" setup>
import { computed, h, ref } from 'vue'
import { LogoutOutlined, HomeOutlined} from '@ant-design/icons-vue'
import { MenuProps, message } from 'ant-design-vue'
import { useRouter } from 'vue-router'
import { useLoginUserStore } from '../stores/user'
import { logoutUsingPost } from '@/api/userController'

const loginUserStore = useLoginUserStore()

// 菜单列表
const originItems = [
  {
    key: '/',
    icon: () => h(HomeOutlined),
    label: '主页',
    title: '主页',
  },
  {
    key: '/add_picture',
    label: '创建图片',
    title: '创建图片',
  },
  {
    key: '/admin/userManage',
    label: '用户管理',
    title: '用户管理',
  },
  {
    key: '/admin/pictureManage',
    label: '图片管理',
    title: '图片管理',
  },

  {
    key: '/admin/spaceManage',
    label: '空间管理',
    title: '空间管理',
  },
  {
    key: 'others',
    label: h('a', { href: 'https://github.com/anran0422', target: '_blank' }, '关于我'),
    title: '关于我',
  },
]

// 过滤菜单项
const filterMenus = (menus = [] as MenuProps['items']) => {
  return menus?.filter((menu) => {
    if (menu.key.startsWith('/admin')) {
      const loginUser = loginUserStore.loginUser
      if (!loginUser || loginUser.userRole !== "admin") {
        return false
      }
    }
    return true
  })
}

// 展示在菜单的路由数组
const items = computed<MenuProps['items']>(() => filterMenus(originItems))



const router = useRouter()
const current = ref<string[]>([])
/**
 * 更新菜单高亮
 */
router.afterEach((to, from, next) => {
  current.value = [to.path]
})

/**
 * 菜单点击事件
 * @param key
 */
const doMenuClick = ({ key }: { key: string }) => {
  router.push({
    path: key
  })
}

/**
 * 退出登录
 */
const doLogout = async () => {
  const res = await logoutUsingPost()
  // 退出登录 将全局状态清空
  if (res.data.code === 0 || res.data.data) {
    loginUserStore.setLoginUser({
      userName: '未登录'
    })
    message.success('退出登录成功')
    await router.push('/user/login')
  } else {
    message.error('退出登录失败' + res.data.message)
  }
}

</script>

<style scoped>
#globalHeader .title-bar {
  display: flex;
  align-items: center;
}

#globalHeader .title {
  color: black;
  font-size: 18px;
  margin-left: 16px;
}

#globalHeader .logo {
  height: 48px;
}
</style>
