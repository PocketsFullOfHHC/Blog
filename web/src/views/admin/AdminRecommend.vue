<template>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
        <a-breadcrumb :style="{ margin: '16px 0' }">
            <a-breadcrumb-item>推荐</a-breadcrumb-item>
        </a-breadcrumb>
        <a-layout style="padding: 24px 0; background: #fff">
            <the-sider></the-sider>
            <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
                <a-card title="为你推荐好友：">
                    <a-row :gutter="150">
                        <a-col :span="11" v-for="( item ) in recommendList" :key="item.id" style="margin: 20px 20px">
                            <a-card hoverable style="width: 320px">
                                <template #actions>
                                    <home-outlined key="home" @click="toUserInfo(item.id)"/>
                                    <router-link :to="{
                                          path:'/chat',
                                          query:{
                                            friendId: item.id,
                                            friendName: item.name,
                                            friendAvatar: item.avatar,
                                          }
                                    }">
                                        <message-outlined key="message"/>
                                    </router-link>
                                    <heart-outlined key="follow" @click="followUser(item.id)"/>
                                </template>
                                <a-card-meta :title="item.name" :description="item.selfIntro ? item.selfIntro : '这个人很懒，没有留下一句话'">
                                    <template #avatar>
                                        <a-avatar :src="'http://localhost:8080/picture/avatars/' + item.avatar"/>
                                    </template>
                                </a-card-meta>
                            </a-card>
                        </a-col>
                    </a-row>
                </a-card>
                <a-divider></a-divider>
                <a-card title="为你推荐部落：">
                    <a-row :gutter="150">
                        <a-col :span="11" v-for="( item ) in recommendCircleList" :key="item.id" style="margin: 20px 20px">
                            <a-card hoverable style="width: 320px">
                                <template #actions>
                                    <home-outlined key="home" @click="toCircleSquare(item.id, item.managerId)"/>
                                    <a-popconfirm title="确认加入部落？" ok-text="是" cancel-text="否"  @confirm="partInCircle(item.id, item.managerId)">
                                        <import-outlined key="import"/>
                                    </a-popconfirm>
                                </template>
                                <div style="text-align: center; font-size: 20px">{{item.circleName}}</div>
                                <div style="font-size: 15px; color: rgba(0, 0, 0, 0.45); margin-top: 10px">部落介绍：{{item.intro}}</div>
                            </a-card>
                        </a-col>
                    </a-row>
                </a-card>
            </a-layout-content>
        </a-layout>
    </a-layout-content>
</template>

<script>
    import TheSider from '@/components/TheSider.vue';
    import { defineComponent, onMounted, ref } from 'vue';
    import axios from "axios";
    import { message } from "ant-design-vue";
    import { useRouter }  from "vue-router";
    import { MessageOutlined, HomeOutlined, HeartOutlined, ImportOutlined } from '@ant-design/icons-vue';
    import store from "@/store";
    export default defineComponent({
        name: "AdminRecommend",
        components: {
            TheSider,
            MessageOutlined,
            HomeOutlined,
            HeartOutlined,
            ImportOutlined
        },
        setup(){
            /**
             * 好友推荐
             * */
            let recommendList = ref();
            const getFollowList = () => {
                axios.get("/recommend/clusterUser/" + store.state.user.username).then((response) => {
                    const data = response.data;
                    if (data){
                        recommendList.value = data.content ? data.content :[];
                    }
                })
            };

            /**
             * 前往推荐好友主页
             * */
            const router = useRouter();
            const toUserInfo = (userId) => {
                router.push ({
                    path:"/userHomePage",
                    query:{
                        authorId: userId
                    }
                })
            };

            /**
             * 关注好友
             * */
            const followUser = (userId) => {
                axios.get(`/follow/follow/${store.state.user.id}/${userId}`).then((response) => {
                    if (response.data.success){
                        message.success("关注成功！");
                    }else {
                        message.error(response.data.message)
                    }
                })
            };

            /**
             * 部落推荐
             * */
            let recommendCircleList = ref();
            const getCircleList = () => {
                axios.get("/recommend/clusterCircle/").then((response) => {
                    const data = response.data;
                    if (data){
                        recommendCircleList.value = data.content ? data.content :[];
                    }
                })
            };

            /**
             * 前往部落主页
             * */
            const toCircleSquare = (circleId, managerId) => {
                router.push ({
                    path:"/circleHomePage",
                    query:{
                        circleId: circleId,
                        managerId: managerId,
                    }
                })
            };

            /**
             * 加入部落
             * */
            const partInCircle = (circleId, managerId) => {
                axios.get('/circle/joinCircle/' + store.state.user.id + '/' + circleId + '/' + managerId).then((response) => {
                    const data = response.data;
                    if (data.success){
                        message.success("加入部落成功");
                    }else {
                        message.error(data.message)
                    }
                })
            };

            onMounted(()=>{
                getFollowList();
                getCircleList();
            });

            return {
                recommendList,
                toUserInfo,
                followUser,
                recommendCircleList,
                partInCircle,
                toCircleSquare,
            }
        },
    });
</script>

<style scoped>

</style>