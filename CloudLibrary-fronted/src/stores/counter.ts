import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useCounterStore = defineStore('counter', () => {
  // 定义状态
  const count = ref(0)
  const doubleCount = computed(() => count.value * 2)
  // 计算状态的逻辑
  function increment() {
    count.value++
  }

  return { count, doubleCount, increment }
})
