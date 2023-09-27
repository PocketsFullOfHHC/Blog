<template>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
        <a-descriptions title="用户主页" :style="{ paddingTop: '25px' }" bordered :column="4">
            <template #extra>
                <a-button type="primary" v-if="!followStatus" @click="follow" shape="round">关注</a-button>
                <a-button type="primary" v-if="followStatus" @click="unFollow" danger shape="round">取消关注</a-button>
            </template>
            <a-descriptions-item label="用户昵称：" :span="1">
                <span>{{user.name}}</span>
            </a-descriptions-item>
            <a-descriptions-item label="性别：" :span="1">
                <span>{{user.gender}}</span>
            </a-descriptions-item>
            <a-descriptions-item label="年龄：" :span="1">
                <span>{{user.age}}</span>
            </a-descriptions-item>
            <a-descriptions-item label="状态：" :span="1">
                <div v-if="user.status">
                    <a-badge status="processing" text="在线" />
                </div>
                <div v-else>
                    <a-badge status="default" text="离线"/>
                </div>
            </a-descriptions-item>

            <a-descriptions-item label="所在兴趣圈：" :span="4">{{user.circles}}</a-descriptions-item>

            <a-descriptions-item label="自我介绍：" :span="3">
                <div>
                    {{user.selfIntro}}
                </div>
            </a-descriptions-item>
            <a-descriptions-item label="头像：" :span="1">
                <a-avatar :size="64" :src="'http://localhost:8080/picture/avatars/' + user.avatar" alt="Han Solo"/>
            </a-descriptions-item>

            <a-descriptions-item label="个性标签：" :span="4">
                <div>
                    <span v-for="(tag, index) in tags" :key="tag">
                        <a-tag :color="color[index % 7]">{{tag}}</a-tag>
                    </span>
                </div>
            </a-descriptions-item>
            <a-descriptions-item label="点赞内容：" :span="4">
                <div>
                    <!--{{userLike}}}-->
                </div>
            </a-descriptions-item>
        </a-descriptions>
    </a-layout-content>
</template>

<script>
    import { UserOutlined } from '@ant-design/icons-vue';
    import { PlusOutlined, LoadingOutlined } from '@ant-design/icons-vue';
    import { message } from 'ant-design-vue';
    import {ref, onMounted} from 'vue';
    import store from '@/store'
    import axios from 'axios'
    import  {useRouter}  from "vue-router";


    export default {
        name: "AdminInfo",
        components: {
            UserOutlined,
            LoadingOutlined,
            PlusOutlined,
        },
        setup(){
            const router = useRouter();
            // 获取路由传参的参数
            let authorId = router.currentRoute.value.query.authorId;

            /**
             * 获取用户点赞信息
             * */
            const userLike = ref([]);
            const getUserLikes = () => {
                axios.get("/likes/list/" + authorId).then((response) => {
                    const data = response.data.content;
                    userLike.value = data;
                })
            };


            /**
             * 获取发博客用户信息
             */
            const user = ref({});
            const tags = ref([]);
            const color = ['pink','red','orange','green','cyan','blue','purple'];

            const getUserInfo = () => {
                axios.get("/user/info/" + authorId).then((response) => {
                    const data = response.data.content;
                    user.value = data;
                    console.log("主页数据：", user.value);
                    if(user.value.gender){
                        if(user.value.gender === "male"){
                            user.value.gender = "男";
                        } else {
                            user.value.gender = "女";
                        }
                    }
                    if (user.value.tags){
                        tags.value = user.value.tags.split(',');
                        console.log("主页标签：", tags.value);
                        console.log(typeof (tags.value));
                    }else {
                        tags.value = []
                    }
                })
            };

            /**
             * 关注
             */
            const followStatus = ref(false);

            // 查看是否关注
            const getIfFollowed = () =>{
                axios.get(`/follow/isFollowed/${store.state.user.id}/${authorId}`).then((response) => {
                    if (response.data.success){
                        const data = response.data.content;
                        console.log("是否关注：", data);
                        if(data === false){
                            followStatus.value = false;
                        } else {
                            followStatus.value = true;
                        }
                    }else {
                        console.log("出错");
                    }
                })
            };

            // 关注
            const follow = () => {
                if(store.state.user.id === authorId){
                    message.error("自己不能关注自己");
                    return;
                }
                axios.get(`/follow/follow/${store.state.user.id}/${authorId}`).then((response) => {
                    if (response.data.success){
                        const data = response.data.content;
                        console.log("关注数据：", data);
                        followStatus.value = true;
                        message.success("关注成功！");
                    }else {
                        message.error(response.data.message)
                    }
                })
            };

            // 取关
            const unFollow = () => {
                axios.get(`/follow/unFollow/${store.state.user.id}/${authorId}`).then((response) => {
                    if (response.data.success){
                        followStatus.value = false;
                        message.success("取消关注成功！");
                    }else {
                        message.error(response.data.message);
                    }
                })
            };

            onMounted(() =>{
                getUserInfo();
                getIfFollowed();
                getUserLikes();
            });

            return {
                user,
                color,
                tags,

                follow,
                unFollow,
                followStatus,

                userLike,
            };
        }
    }
</script>

<style scoped>

</style>