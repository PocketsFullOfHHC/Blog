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
                <a-menu-item key="5">option5</a-menu-item>
                <a-menu-item key="6">option6</a-menu-item>
                <a-menu-item key="7">option7</a-menu-item>
                <a-menu-item key="8">option8</a-menu-item>
            </a-sub-menu>
        </a-menu>
    </a-layout-sider>
</template>

<script lang="ts">
    import { ref, onMounted } from 'vue';
    import axios from 'axios'
    import store from '@/store'
    export default {
        name: "TheSider",
        setup(){
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
            onMounted(() => {
                getFollowList();
            });
            return{
                getFollowList,
                followList,
            }
        }
    }
</script>

<style scoped>

</style>