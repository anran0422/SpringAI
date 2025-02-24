<template>
  <div>
    <a-row :gutter="[16, 16]">
      <!-- 图片展示区 -->
      <a-col :sm="24" :md="16" :xl="18">
        <a-card title="图片预览">
          <a-image
            style="max-height: 600px; object-fit: contain"
            :src="picture.url"
          />
        </a-card>
      </a-col>
      <!-- 图片信息区 -->
      <a-col :sm="24" :md="8" :xl="6">
        <a-card title="图片信息">
          <a-descriptions :column="1">
            <a-descriptions-item label="作者">
              <a-space>
                <a-avatar :size="24" :src="picture.user?.userAvatar" />
                <div>{{ picture.user?.userName }}</div>
              </a-space>
            </a-descriptions-item>
            <a-descriptions-item label="名称">
              {{ picture.name ?? '未命名' }}
            </a-descriptions-item>
            <a-descriptions-item label="简介">
              {{ picture.introduction ?? '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="分类">
              {{ picture.category ?? '默认' }}
            </a-descriptions-item>
            <a-descriptions-item label="标签">
              <a-tag v-for="tag in picture.tags" :key="tag">
                {{ tag }}
              </a-tag>
            </a-descriptions-item>
            <a-descriptions-item label="格式">
              {{ picture.picFormat ?? '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="宽度">
              {{ picture.picWidth ?? '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="高度">
              {{ picture.picHeight ?? '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="宽高比">
              {{ picture.picScale ?? '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="大小">
              {{ formatSize(picture.picSize) }}
            </a-descriptions-item>
            <a-descriptions-item label="主色调">
              <a-space>
                {{ picture.picColor ?? '-' }}
                <div
                  v-if="picture.picColor"
                  :style="{
        backgroundColor: toHexColor(picture.picColor),
        width: '16px',
        height: '16px',
      }"
                />
              </a-space>
            </a-descriptions-item>

            <!-- 操作按钮-->
            <a-space wrap>
              <a-button type="primary" @click="doDownload">
                下载
                <template #icon>
                  <DownloadOutlined />
                </template>
              </a-button>
              <a-button :icon="h(ShareAltOutlined)" type="primary" ghost @click="doShare" >
                分享
              </a-button>
              <!-- 分享图片组件 -->
              <ShareModal ref="shareModalRef" :link="shareLink"  />
            </a-space>
            <a-space wrap>
              <a-button v-if="canEdit" :icon="h(EditOutlined)" type="default" @click="doEdit">
                编辑
              </a-button>
              <a-button v-if="canEdit" :icon="h(DeleteOutlined)" danger @click="doDelete">
                删除
              </a-button>
            </a-space>
          </a-descriptions>

        </a-card>
      </a-col>
    </a-row>

  </div>

</template>

<script setup lang="ts">
import { computed, h, onMounted, ref } from 'vue'
import { deletePictureUsingPost, getPictureVoByIdUsingGet } from '@/api/pictureController'
import { message } from 'ant-design-vue'
import { downloadImage, formatSize, toHexColor } from '../../utils'
import { DownloadOutlined,DeleteOutlined,EditOutlined,ShareAltOutlined } from '@ant-design/icons-vue'
import { useLoginUserStore } from '@/stores/user'
import router from '@/router'
import ShareModal from '@/components/modal/ShareModal.vue'

const props = defineProps<{
  id: string | number
}>()
const picture = ref<API.PictureVO>({})

// 获取图片详情
const fetchPictureDetail = async () => {
  try {
    const res = await getPictureVoByIdUsingGet({
      id: props.id,
    })
    if (res.data.code === 0 && res.data.data) {
      picture.value = res.data.data
    } else {
      message.error('获取图片详情失败，' + res.data.message)
    }
  } catch (e: any) {
    message.error('获取图片详情失败：' + e.message)
  }
}

const loginUserStore = useLoginUserStore()
// 是否具有编辑权限
const canEdit = computed(() => {
  const loginUser = loginUserStore.loginUser;
  // 未登录不可编辑
  if (!loginUser.id) {
    return false
  }
  // 仅本人或管理员可编辑
  const user = picture.value.user || {}
  return loginUser.id === user.id || loginUser.userRole === 'admin'
})

// 编辑
const doEdit = () => {
  router.push({
    path: '/add_picture',
    query: {
      id:picture.value.id,
      spaceId: picture.value.spaceId
    }
  })
}
// 删除
const doDelete = async () => {
  const id = picture.value.id
  if (!id) {
    return
  }
  const res = await deletePictureUsingPost({ id })
  if (res.data.code === 0) {
    message.success('删除成功')
    router.push('/')
  } else {
    message.error('删除失败')
  }
}

// 处理下载
const doDownload = () => {
  downloadImage(picture.value.url)
}

// -------- 分享操作 --------
const shareModalRef = ref()
const shareLink = ref<string>()
const doShare = () => {
  shareLink.value = `${window.location.protocol}//${window.location.host}/picture/${picture.value.id}`
  if(shareModalRef.value) {
    shareModalRef.value.openModal()
  }
}

onMounted(() => {
  fetchPictureDetail()
})


</script>


<style scoped>

</style>
