<template>
  <div id="spaceDetailPage">
    <!-- 空间信息 -->
    <a-flex justify="space-between">
      <h2>{{ space.spaceName }}（私有空间）</h2>
      <a-space size="middle">
        <a-button type="primary" :href="`/add_picture?spaceId=${id}`" target="_blank">
          + 创建图片
        </a-button>
        <!--批量编辑图片-->
        <a-button :icon="h(EditOutlined)" @click="doEditBatch">批量编辑</a-button>

        <a-tooltip
          :title="`占用空间 ${formatSize(space.totalSize)} / ${formatSize(space.maxSize)}`"
        >
          <a-progress
            type="circle"
            :percent="((space.totalSize * 100) / space.maxSize).toFixed(1)"
            :size="42"
          />
        </a-tooltip>
      </a-space>
    </a-flex>
    <div style="margin-bottom: 16px"></div>

    <!-- 搜索表单 -->
    <PictureSearchForm :onSearch="onSearch" />
    <!-- 按颜色搜索 -->
    <a-form-item label="按颜色搜索" style="margin-top: 16px">
      <color-picker format="hex" @pureColorChange="onColorChange" />
    </a-form-item>

    <div style="margin-bottom: 16px"></div>
    <!-- 图片列表 -->
    <PictureList :dataList="dataList" :loading="loading" :showOP="true" :onReload="fetchData" />
    <a-pagination
      style="text-align: center"
      v-model:current="searchParams.current"
      v-model:pageSize="searchParams.pageSize"
      :total="total"
      :show-total="() => `图片总数 ${total} / ${space.maxCount}`"
      @change="onPageChange"
    />

    <!-- 批量编辑图片弹框 -->
    <EditPictureBatchModal
    ref="editPictureBatchModalRef"
    :spaceId="id"
    :pictureList="dataList"
    :onSuccess="onEditPictureBatchSuccess"
    />

  </div>

</template>

<script setup lang="ts">
import { listPictureVoByPageUsingPost, searchPictureByColorUsingPost } from '@/api/pictureController'
import { message } from 'ant-design-vue'
import { h, onMounted, ref } from 'vue'
import { getSpaceVoByIdUsingGet } from '@/api/spaceController'
import { formatSize } from '@/utils'
import PictureList from '@/components/PictureList.vue'
import PictureSearchForm from '@/components/PictureSearchForm.vue'
import EditPictureBatchModal from '@/components/modal/EditPictureBatchModal.vue'
import { EditOutlined } from '@ant-design/icons-vue'
import { ColorPicker } from 'vue3-colorpicker'
import "vue3-colorpicker/style.css"

const props = defineProps<{
  id: string | number
}>()
const space = ref<API.SpaceVO>({})

// 获取空间详情
const fetchSpaceDetail = async () => {
  try {
    const res = await getSpaceVoByIdUsingGet({
      id: props.id,
    })
    if (res.data.code === 0 && res.data.data) {
      space.value = res.data.data
    } else {
      message.error('获取空间详情失败，' + res.data.message)
    }
  } catch (e: any) {
    message.error('获取空间详情失败：' + e.message)
  }
}

onMounted(() => {
  fetchSpaceDetail()
})

// 数据
const dataList = ref([])
const total = ref(0)
const loading = ref(true)

// 搜索条件
const searchParams = ref<API.PictureQueryRequest>({
  current: 1,
  pageSize: 12,
  sortField: 'createTime',
  sortOrder: 'descend',
})

// 分页参数
const onPageChange = (page, pageSize) => {
  searchParams.value.current = page
  searchParams.value.pageSize = pageSize
  fetchData()
}

// 搜索
const onSearch = (newSearchParams: API.PictureQueryRequest) => {
  searchParams.value = {
    ...searchParams.value,
    ...newSearchParams,
    current: 1,
  }
  fetchData()
}

// 获取图片列表
const fetchData = async () => {
  loading.value = true
  // 转换搜索参数
  const params = {
    spaceId: props.id,
    ...searchParams.value,
  }
  const res = await listPictureVoByPageUsingPost(params)
  if (res.data.code === 0 && res.data.data) {
    dataList.value = res.data.data.records ?? []
    total.value = res.data.data.total ?? 0
  } else {
    message.error('获取数据失败，' + res.data.message)
  }
  loading.value = false
}

// 页面加载时请求一次
onMounted(() => {
  fetchData()
})

/**
 * 批量编辑图片弹框
 */
// 批量编辑图片弹窗引用
const editPictureBatchModalRef = ref()
// 批量编辑图片成功后 重新获取数据
const onEditPictureBatchSuccess = () => {
  fetchData()
}
// 点击批量编辑按钮
const doEditBatch = () => {
  if(editPictureBatchModalRef.value) {
    editPictureBatchModalRef.value.openModal()
  }
}

/**
 * 按颜色搜索图片
 */
const onColorChange = async (color: string) => {
  loading.value = true
  const res = await searchPictureByColorUsingPost({
    picColor: color,
    spaceId: props.id,
  })
  if (res.data.code === 0 && res.data.data) {
    const data = res.data.data ?? [];
    dataList.value = data;
    total.value = data.length;
  } else {
    message.error('获取数据失败，' + res.data.message)
  }
  loading.value = false
}

</script>


<style scoped>
</style>
