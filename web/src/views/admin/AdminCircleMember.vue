<template>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
        <a-breadcrumb :style="{ margin: '16px 0' }">
            <a-breadcrumb-item>部落成员列表</a-breadcrumb-item>
        </a-breadcrumb>
        <a-layout style="padding: 24px 0; background: #fff">
            <div style="padding: 20px 60px">
                <a-row :gutter="80">
                    <a-col :span="8" v-for="(item, index) in memberList" :key="item.id" style="margin-bottom: 20px">
                        <a-card hoverable :title="'成员' + (index + 1)"  style="width: 320px">
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
                                <a-popconfirm title="确认删除好友？" ok-text="是" cancel-text="否"  @confirm="deleteMemberFromCircle(item.id)">
                                    <delete-outlined key="delete"/>
                                </a-popconfirm>
                            </template>
                            <a-card-meta :title="item.name">
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
    import { MessageOutlined } from '@ant-design/icons-vue';
    import  {useRouter}  from "vue-router";
    import  { message }  from "ant-design-vue";

    export default defineComponent({
        name: "adminCircleMember",
        components: {
            MessageOutlined,
        },
        setup() {
            const router = useRouter();
            let circleId = router.currentRoute.value.query.circleId;

            /**
             * 进入好友主页
             */
            const toUserInfo = (userId) => {
                router.push ({
                    path:"/userHomePage",
                    query:{
                        authorId: userId
                    }
                })
            };

            /**
             * 获取部落的全部成员
             * */
            const memberList = ref({});
            const getCircleMember = () => {
                axios.get('/circle/circleMember/' + circleId).then((response) => {
                    const data = response.data;
                    if (data.success){
                        memberList.value = data.content;
                    }else {
                        message.error(data.message);
                    }
                })
            };

            /**
             * 删除部落成员
             * */
            const deleteMemberFromCircle = (memberId) => {
                axios.get('/circle/deleteMemberFromCircle/' + memberId + '/' + circleId).then((response) => {
                    const data = response.data;
                    if (data.success){
                        getCircleMember();
                        message.success("删除成员成功！")
                    }else {
                        message.error(data.message);
                    }
                })
            };

            onMounted(()=>{
                getCircleMember();
            });

            return {
                toUserInfo,
                memberList,
                deleteMemberFromCircle,
            };
        },
    });
</script>