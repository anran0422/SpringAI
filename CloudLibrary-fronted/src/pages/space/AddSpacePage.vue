<template>
  <div id="addSpacePage">
    <h2 style="margin-bottom: 16px">
      {{ route.query?.id ? '修改' : '创建' }} {{ SPACE_TYPE_MAP[spaceType] }}
    </h2>

    <!-- 显示 -->
    <a-form layout="vertical" :model="formData" @finish="handleSubmit">
      <a-form-item label="空间名称" name="spaceName">
        <a-input v-model:value="formData.spaceName" placeholder="请输入空间名称" allow-clear />
      </a-form-item>
      <a-form-item label="空间级别" name="spaceLevel">
        <a-select
          v-model:value="formData.spaceLevel"
          :options="SPACE_LEVEL_OPTIONS"
          placeholder="请输入空间级别"
          style="min-width: 180px"
          allow-clear
        />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit" style="width: 100%" :loading="loading">
          提交
        </a-button>
      </a-form-item>
      <a-card title="空间级别介绍">
        <a-typography-paragraph>
          * 目前仅支持开通普通版，如需升级空间，请联系
          <a href="https://www.baidu.com" target="_blank">安然</a>。
        </a-typography-paragraph>
        <a-typography-paragraph v-for="spaceLevel in spaceLevelList">
          {{ spaceLevel.text }}： 大小 {{ formatSize(spaceLevel.maxSize) }}， 数量
          {{ spaceLevel.maxCount }}
        </a-typography-paragraph>
      </a-card>
    </a-form>
  </div>

</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { computed, onMounted, reactive, ref } from 'vue'
import { SPACE_LEVEL_ENUM, SPACE_LEVEL_OPTIONS, SPACE_TYPE_ENUM, SPACE_TYPE_MAP } from '@/constants/space'
import {
  addSpaceUsingPost,
  editSpaceUsingPost,
  getSpaceVoByIdUsingGet,
  listSpaceLevelUsingGet
} from '@/api/spaceController'
import { message } from 'ant-design-vue'
import { formatSize } from '../../utils'


const formData = reactive<API.SpaceAddRequest | API.SpaceEditRequest>({
  spaceName: '',
  // 默认为普通级别
  spaceLevel: SPACE_LEVEL_ENUM.COMMON,
})

const loading = ref(false)
const router = useRouter()
const route = useRoute()

// 空间类别
const spaceType = computed(() => {
  if (route.query?.type) {
    return Number(route.query.type)
  }
  return SPACE_TYPE_ENUM.PRIVATE
})
/**
 * 提交表单
 */
const handleSubmit = async (values: any) => {
  const spaceId = oldSpace.value?.id
  loading.value = true
  let res
  // 更新
  if(spaceId) {
    res = await editSpaceUsingPost({
      id:spaceId,
      ...formData
    })
  } else {
    // 创建
    res = await addSpaceUsingPost({
      ...formData,
      spaceType: spaceType.value
    })
  }
  // 接收响应
  if (res.data.code === 0 && res.data.data) {
    message.success('操作成功')
    let path = `/space/${spaceId ?? res.data.data}`
    router.push({
      path,
    })
  } else {
    message.error('操作失败,' + res.data.message)
  }
  loading.value = false
}

/**
 * 获取空间级别
 */
const spaceLevelList = ref<API.SpaceLevel>()
const fetchSpaceLevel = async() => {
  const res = await listSpaceLevelUsingGet()
  if(res.data.code === 0 && res.data.data) {
    spaceLevelList.value = res.data.data;
  } else {
    message.error("加载空间级别失败:" + res.data.message);
  }
}

onMounted(() => {
  fetchSpaceLevel()
})


/**
 * 编辑空间逻辑
 */
const oldSpace = ref<API.Space>()

const getOldSpace = async() => {

  const id = route.query?.id
  if(id) {
    const res = await getSpaceVoByIdUsingGet({
      id:id,
    })
    if(res.data.code === 0 && res.data.data) {
      const data = res.data.data
      oldSpace.value = data
      formData.spaceName = data.spaceName
      formData.spaceLevel = data.spaceLevel
    }
  }
}

onMounted(() => {
  getOldSpace()
})


</script>

<style scoped>
#addSpacePage {
  max-width: 720px;
  margin: 0 auto;
}


</style>
