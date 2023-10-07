<template>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
        <a-breadcrumb :style="{ margin: '16px 0' }">
            <a-breadcrumb-item>Home</a-breadcrumb-item>
        </a-breadcrumb>
        <a-layout style="padding: 24px 0; background: #fff">
            <the-sider></the-sider>
            <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
                <div v-for="item in blogList" :key="item.id" >
                    <TheCard :blogList="item"></TheCard>
                    <div :style="{marginTop : '20px', marginLeft: '10px'}">
                        <router-link :to="{
                          path:'/edit',
                          query:{
                            blogId: item.id,
                            content:item.content,
                          }
                        }">
                            <a-button size="small" type="primary" style="margin-left: 30px">编辑</a-button>
                        </router-link>
                        <a-popconfirm title="确定要删除这篇帖子？" ok-text="是" cancel-text="否">
                            <a-button size="small" danger type="primary" style="margin-left: 10px">删除</a-button>
                        </a-popconfirm>
                        <a-divider></a-divider>
                    </div>
                </div>
                <a-pagination
                        v-model:current="current"
                        :total="blogNum ? blogNum : 1"
                        :defaultPageSize="5"
                        :style="{marginLeft:'auto', textAlign: 'right'}"
                        @change="getAllBlog(current)"/>
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
        name: "AdminMyself",
        components: {
            TheCard,
            TheSider,
        },
        setup(){
            /**
             * 分页展示我的博客
             * */
            const pagination = ref();
            const current = ref(1);
            let blogList = ref();
            const getAllBlog = (pageNum:number) => {
                axios.get("/blog/myList/" + store.state.user.id + "/" + pageNum + "/5").then(
                    (response) =>{
                        blogList.value = response.data.content ? response.data.content :[];
                        console.log(pageNum);
                        console.log(blogList.value);
                    },
                    (error) => {
                        message.error(error);
                    }
                )
            };

            const blogNum = ref();
            const getBlogNum = () => {
                axios.get("/blog/myBlogNum/" + store.state.user.id).then(
                    (response) => {
                        blogNum.value = response.data.content;
                        console.log(blogNum.value);
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
                getAllBlog(1);
                getBlogNum();
                getMyLikes();

            });
            return{
                pagination,
                blogList,
                current,
                getAllBlog,
                blogNum,
            }
        },
    });
</script>

<style scoped>

</style>