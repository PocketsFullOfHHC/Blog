<template>
  <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
    <a-breadcrumb :style="{ margin: '16px 0' }">
      <a-breadcrumb-item>主页</a-breadcrumb-item>
    </a-breadcrumb>
    <a-layout style="padding: 24px 0; background: #fff">
      <the-sider></the-sider>
      <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">

        欢迎

      </a-layout-content>
    </a-layout>
  </a-layout-content>
</template>

<script lang="ts">
  import { defineComponent, onMounted, ref } from 'vue';
  import TheSider from '@/components/TheSider.vue';
  // import axios from 'axios';
  export default defineComponent({
    name:'homeView',
    components: {
      TheSider,
    },
    setup(){
      const blogs = ref();
      onMounted(() =>{
        // axios.get("/blog/list").then((response) =>{
        //   const data = response.data;
        //   blogs.value = data.content;
        //   console.log(response);
        // });
      });
      return{
        blogs
      }
    },
  })
</script>

<style scoped>

</style>