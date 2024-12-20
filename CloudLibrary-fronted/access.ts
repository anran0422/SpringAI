import router from './src/router'
import { useLoginUserStore } from './src/stores/user'
import { message } from 'ant-design-vue'

// 是否为首次获取登录用户，首次登录才校验权限
let firstFetchLoginUser = true;

/**
 * 全局权限校验
 */
router.beforeEach(async (to, from, next) => {
  const loginUserStore = useLoginUserStore()
  let loginUser = loginUserStore.loginUser
  // 确保页面刷新 首次加载时 等后端返回用户信息再校验权限
  if(firstFetchLoginUser) {
    await loginUserStore.fetchLoginUser()
    loginUser = loginUserStore.loginUser
    firstFetchLoginUser = false
  }
  const toUrl = to.fullPath
  if(toUrl.startsWith("/admin")) {
    if(!loginUser || loginUser.userRole !== "admin") {
      message.error("没有权限")
      next(`/user/login?redirect=${to.fullPath}`)
      return
    }
  }
  next()
})
