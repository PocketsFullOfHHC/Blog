<template>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
        <a-breadcrumb :style="{ margin: '16px 0' }">
            <a-breadcrumb-item>好友列表</a-breadcrumb-item>
        </a-breadcrumb>
        <a-layout style="padding: 24px 0; background: #fff">
            <div style="padding: 10px 60px">
                <a-button type="primary" @click="showSearchFriendModal()">搜索好友</a-button>
                <a-divider></a-divider>
                <a-modal
                        v-model:open="searchFriendModalVisible"
                        title="搜索好友"
                        @ok="hideSearchFriendModal"
                        :footer="null"
                >
                    <a-button @click="searchWay = '1'" style="margin: 10px 0 20px 0">按昵称搜索</a-button>
                    <a-button @click="searchWay = '2'" style="margin: 10px 0 20px 20px">按用户标签搜索</a-button>
                    <a-form name="getUser">
                        <a-form-item
                                label="用户名"
                                name="username"
                                v-if="searchWay === '1'"
                        >
                            <a-input v-model:value="searchUsername" />
                        </a-form-item>
                        <a-form-item
                                label="用户标签"
                                name="password"
                                v-if="searchWay === '2'"
                        >
                            <a-input v-model:value="searchUserTags" />
                        </a-form-item>
                        <a-form-item v-if="searchWay === '2' || searchWay === '1'" style="text-align: right;">
                            <a-button type="primary" html-type="submit" @click="searchUser">搜索</a-button>
                        </a-form-item>
                        <div v-if="userInfoBySearch.length !== 0" >
                            <a-card hoverable style="width: 476px; margin-bottom: 20px" v-for="(item) in userInfoBySearch" :key="item.id" >
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
                                </template>
                                <a-card-meta :title="item.name" :description="item.selfIntro ? item.selfIntro : '这个人很懒，没有留下一句话'">
                                    <template #avatar>
                                        <a-avatar :src="'http://localhost:8080/picture/avatars/' + item.avatar"/>
                                    </template>
                                </a-card-meta>
                            </a-card>
                        </div>
                        <div v-else>
                            <a-empty/>
                        </div>
                    </a-form>
                </a-modal>
                <a-row :gutter="80">
                    <a-col :span="8" v-for="(item) in followList" :key="item.id" style="margin-bottom: 20px">
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
                                <a-popconfirm title="确认删除好友？" ok-text="是" cancel-text="否"  @confirm="unfollow(item.id)">
                                    <delete-outlined key="delete"/>
                                </a-popconfirm>
                            </template>
                            <a-card-meta :title="item.name" :description="item.selfIntro ? item.selfIntro : '这个人很懒，没有留下一句话'">
                                <template #avatar>
                                    <a-avatar :src="'http://localhost:8080/picture/avatars/' + item.avatar" />
                                </template>
                            </a-card-meta>
                        </a-card>
                    </a-col>
                </a-row>
            </div>
        </a-layout>
    </a-layout-content>
</template>

<script>
    import {defineComponent, ref, onMounted} from 'vue';
    import axios from 'axios'
    import store from '@/store'
    import { HomeOutlined, DeleteOutlined, MessageOutlined} from '@ant-design/icons-vue';
    import  {useRouter}  from "vue-router";
    import  { message }  from "ant-design-vue";

    export default defineComponent({
        name: "AdminFriend",
        components: {
            MessageOutlined,
            DeleteOutlined,
            HomeOutlined,
        },
        setup() {
            /**
             * 获取关注好友
             * */
            let followList = ref();
            const getFollowList = () => {
                axios.get("/follow/followList/" + store.state.user.id).then((response) => {
                    const data = response.data;
                    if (data){
                        followList.value = data.content ? data.content :[];
                    }
                })
            };

            /**
             * 进入好友主页
             */
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
             * 删除好友
             */
            const unFollow = (userId) => {
                axios.get(`/follow/unFollow/${store.state.user.id}/${userId}`).then((response) => {
                    if (response.data.success){
                        message.success("删除好友成功！");
                        getFollowList();
                    }else {
                        message.error(response.data.message);
                    }
                })
            };

            /**
             * 搜索好友
             */
            const searchFriendModalVisible = ref(false);
            const showSearchFriendModal = () => {
                searchFriendModalVisible.value = true;
            };
            const hideSearchFriendModal = () => {
                searchFriendModalVisible.value = false;
            };

            // 搜索形式
            const searchWay = ref('0');
            const searchUsername = ref("");
            const searchUserTags = ref("");
            const userInfoBySearch = ref([]);
            console.log("搜索内容初始化为：", userInfoBySearch.value.length);

            const searchUser = () => {
                if (searchWay.value === '1'){
                    if(searchUsername.value === ""){
                        message.warn("请勿输入空内容");
                        return;
                    }
                    axios.get("/user/getUserInfoByName/" + searchUsername.value).then((response) => {
                        if (response.data){
                            userInfoBySearch.value = response.data.content;
                            console.log("查找结果1：", userInfoBySearch.value);
                            message.success("查找完毕")
                        }
                    })
                }else if (searchWay.value === '2'){
                    if(searchUserTags.value === ""){
                        message.warn("请勿输入空内容");
                        return;
                    }
                    axios.get("/user/getUserListByTags/" + searchUserTags.value).then((response) => {
                        if (response.data){
                            userInfoBySearch.value = response.data.content;
                            console.log("查找结果2：", userInfoBySearch.value);
                            message.success("查找完毕")
                        }
                    })
                }
                searchWay.value = '0';
            };

            onMounted(()=>{
                getFollowList();
            });

            return {
                followList,
                toUserInfo,
                unFollow,

                showSearchFriendModal,
                searchFriendModalVisible,
                searchUsername,
                searchUserTags,
                searchWay,
                hideSearchFriendModal,
                searchUser,
                userInfoBySearch,
            };
        },
    });
</script>