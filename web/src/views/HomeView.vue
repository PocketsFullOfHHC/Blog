<template>
  <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
    <a-breadcrumb :style="{ margin: '16px 0' }">
      <a-breadcrumb-item>Home</a-breadcrumb-item>
    </a-breadcrumb>
    <a-layout style="padding: 24px 0; background: #fff">
      <the-sider></the-sider>
      <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
        Content
      </a-layout-content>
    </a-layout>
  </a-layout-content>
</template>

<script>
  import { defineComponent} from 'vue';
  import TheSider from '@/components/TheSider.vue';
  export default defineComponent({
    name:'homeView',
    components: {
      TheSider,
    }
  })
</script>

<style scoped>

</style>