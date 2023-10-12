<template>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
        <a-breadcrumb :style="{ margin: '16px 0' }">
            <a-breadcrumb-item>我的收藏</a-breadcrumb-item>
        </a-breadcrumb>
        <a-layout style="padding: 24px 0; background: #fff">
            <the-sider></the-sider>
            <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
                <div v-for="item in blogList" :key="item.id" >
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
    import store from '@/store'
    import { message } from "ant-design-vue";
    export default defineComponent({
        name: "AdminFollow",
        components: {
            TheCard,
            TheSider,
        },
        setup(){
            /**
             * 分页展示我的博客
             * */
            let blogList = ref();
            const getCollectBlog = () => {
                axios.get("/collect/collectBlogList/" + store.state.user.id).then(
                    (response) =>{
                        blogList.value = response.data.content ? response.data.content :[];
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
                getCollectBlog();
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