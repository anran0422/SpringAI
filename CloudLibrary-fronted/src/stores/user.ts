import { defineStore } from 'pinia'
import { ref } from 'vue'

/**
 * 状态管理
 */
export const useLoginUserStore = defineStore('loginUser', () => {
  // 定义状态初始值
  const loginUser = ref<any>({
    userName: '未登录',
  })

  // 定义状态的逻辑
  async function fetchLoginUser() {
    // todo 由于后端还没提供接口，暂时注释
    // const res = await getCurrentUser();
    // if (res.data.code === 0 && res.data.data) {
    //   loginUser.value = res.data.data;
    // }
    setTimeout(() => {
      loginUser.value = { userName: '测试用户', id: 1 }
    }, 3000)
  }

  // 定义状态更改方法
  function setLoginUser(newLoginUser: any) {
    loginUser.value = newLoginUser
  }

  return { loginUser, setLoginUser, fetchLoginUser }
})
