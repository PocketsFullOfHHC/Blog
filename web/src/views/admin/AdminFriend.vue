<template>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
        <a-breadcrumb :style="{ margin: '16px 0' }">
            <a-breadcrumb-item>好友列表</a-breadcrumb-item>
        </a-breadcrumb>
        <a-layout style="padding: 24px 0; background: #fff">
            <div style="padding: 20px 60px">
                <a-row :gutter="80">
                    <a-col :span="8" v-for="(item) in followList" :key="item.id" style="margin-bottom: 20px">
                        <a-card hoverable style="width: 320px">
                            <template #actions>
                                <home-outlined key="home" @click="toUserInfo(item.id)"/>
                                <message-outlined key="message"/>
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

            onMounted(()=>{
                getFollowList();
            });

            return {
                followList,
                toUserInfo,
                unFollow,
            };
        },
    });
</script>