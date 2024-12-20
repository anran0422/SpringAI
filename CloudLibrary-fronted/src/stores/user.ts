import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getLoginUserUsingGet } from '@/api/userController'

/**
 * 状态管理
 */
export const useLoginUserStore = defineStore('loginUser', () => {
  // 定义状态初始值
  const loginUser = ref<API.LoginUserVO>({
    userName: '未登录',
  })

  // 定义状态的逻辑
  async function fetchLoginUser() {
    const res = await getLoginUserUsingGet();
    if (res.data.code === 0 && res.data.data) {
      loginUser.value = res.data.data;
    }
  }

  // 定义状态更改方法
  function setLoginUser(newLoginUser: API.LoginUserVO) {
    loginUser.value = newLoginUser
  }

  return { loginUser, setLoginUser, fetchLoginUser }
})
