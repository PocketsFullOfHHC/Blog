<template>
    <a-layout-sider width="250" style="background: #fff" padding="200px">
        <a-menu mode="inline" style="height: 100%">
            <a-sub-menu key="sub1">
                <a-menu-item v-for="( friend ) in followList" :key="friend.id">
                    <router-link :to="{
                          path:'/chat',
                          query:{
                            friendId: friend.id,
                            friendName: friend.name,
                            friendAvatar: friend.avatar,
                          }
                    }">
                        <a-avatar :src="'http://localhost:8080/picture/avatars/' + friend.avatar" alt="头像"/>
                        <span style="margin-left: 30px">
                            {{friend.name}}
                        </span>
                    </router-link>
                </a-menu-item>
                <template #title>
                    <span>
                      我的朋友
                    </span>
                </template>
            </a-sub-menu>
            <a-sub-menu key="sub2">
                <template #title>
                    <span>
                      我的部落
                    </span>
                </template>
                <a-menu-item v-for="( circle ) in joinedCircleList" :key="circle.id" @click="toCircleHomePage(circle.id, circle.managerId)">
                    <span style="margin-left: 30px">
                        {{circle.circleName}}
                    </span>
                </a-menu-item>
            </a-sub-menu>
        </a-menu>
    </a-layout-sider>
</template>

<script lang="ts">
    import { ref, onMounted } from 'vue';
    import axios from 'axios'
    import store from '@/store'
    import { useRouter } from "vue-router";
    export default {
        name: "TheSider",
        setup(){
            const router = useRouter();

            /**
             * 获取好友列表
             * */
            const followList = ref();
            const getFollowList = () => {
                axios.get("/follow/followList/" + store.state.user.id).then((response) => {
                    const data = response.data;
                    if (data){
                        followList.value = data.content ? data.content :[];
                        console.log("聊天对象：", followList)
                    }
                })
            };

            /**
             * 获取部落列表
             * */
            const joinedCircleList = ref();
            const getJoinedCircleList = () => {
                axios.get("/circle/myJoinedCircle/" + store.state.user.id).then((response) => {
                    const data = response.data;
                    if (data){
                        joinedCircleList.value = data.content ? data.content :[];
                    }
                })
            };

            /**
             * 进入部落主页
             * */
            const toCircleHomePage = (circleId:any, managerId:any) => {
                router.push({
                    path:"/circleHomePage",
                    query:{
                        circleId: circleId,
                        managerId: managerId
                    }
                });
            };

            onMounted(() => {
                getJoinedCircleList();
                getFollowList();
            });

            return{
                followList,
                joinedCircleList,
                toCircleHomePage,
            }
        }
    }
</script>

<style scoped>

</style>