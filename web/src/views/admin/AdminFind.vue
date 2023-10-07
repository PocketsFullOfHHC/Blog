<template>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
        <a-breadcrumb :style="{ margin: '16px 0' }">
            <a-breadcrumb-item>Home</a-breadcrumb-item>
        </a-breadcrumb>
        <a-layout style="padding: 24px 0; background: #fff">
            <the-sider></the-sider>
            <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
                <div v-for="(item,index) in blogList" :key="index" >
                    <TheCard :blogList="item"></TheCard>
                </div>
            </a-layout-content>
        </a-layout>
    </a-layout-content>
</template>

<script lang="ts">
    import TheCard from "@/components/TheCard.vue";
    import TheSider from '@/components/TheSider.vue';
    import {defineComponent, onMounted, ref} from 'vue';
    import axios from "axios";
    import { message } from "ant-design-vue";
    import store from "@/store";
    export default defineComponent({
        name: "AdminFind",
        components: {
            TheCard,
            TheSider,
        },
        setup(){
            let blogList = ref();
            const getAllBlog = () => {
                axios.get("/blog/AllList").then(
                    (response) =>{
                        blogList.value = response.data.content ? response.data.content :[];
                        console.log(response);
                        console.log(blogList.value);
                    },
                    (error) => {
                        message.error(error);
                    }
                )
            };

            /**
             * 将个人的点赞历史保存到vuex中，后续个人主要需要用
             * */
            const getMyLikes = () => {
                if (store.state.user.id){
                    axios.get("/likes/list/" + store.state.user.id).then((response) => {
                        const data = response.data;
                        store.commit("setLikes", data.content);
                    })
                }
            };

            onMounted(()=>{
                getAllBlog();
                getMyLikes();
            });
            return{
                blogList,
            }
        },
    });
</script>

<style scoped>

</style>