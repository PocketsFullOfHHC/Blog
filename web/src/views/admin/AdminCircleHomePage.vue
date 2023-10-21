<template>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
        <a-breadcrumb :style="{ margin: '16px 0' }">
            <a-breadcrumb-item>部落</a-breadcrumb-item>
        </a-breadcrumb>
        <a-layout style="padding: 24px 0; background: #fff">
            <the-sider></the-sider>
            <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
                <a-button type="primary" style="margin: 10px 0" @click="popModal">发言</a-button>
                <a-modal
                        v-model:open="modalVisible"
                        title="发言框"
                        ok-text="发言"
                        cancel-text="取消"
                        @ok="saveCircleBlog"
                >
                    <a-textarea
                            v-model:value="blogContent"
                            placeholder="把想说的话写在这里吧"
                            :auto-size="{ minRows: 3, maxRows: 6 }"
                            allow-clear
                    />
                </a-modal>
                <div v-for="( item ) in blogList" :key="item.id" >
                    <TheCircleCard :blogList="item" :ifShowDeleteButton="managerId === store.state.user.id"></TheCircleCard>
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

<script>
    import TheCircleCard from "@/components/TheCircleCard.vue";
    import TheSider from "@/components/TheSider";
    import { defineComponent, ref, onMounted } from 'vue';
    import { StarOutlined, LikeOutlined, MessageOutlined } from '@ant-design/icons-vue';
    import { message } from 'ant-design-vue'
    import axios from 'axios'
    import store from '@/store'
    import { useRouter }  from "vue-router";
    export default defineComponent({
        name: "CircleHomePage",
        components: {
            TheCircleCard,
            TheSider,
            StarOutlined,
            LikeOutlined,
            MessageOutlined,
        },
        setup() {
            const router = useRouter();
            // 获取路由传参的参数
            let circleId = router.currentRoute.value.query.circleId;
            let managerId = router.currentRoute.value.query.managerId;

            /**
             * 分页获取部落博客
             * */
            let blogList = ref();
            const getAllBlog = (pageNum) => {
                axios.get("/circleBlog/circleBlogListByPage/"+ circleId + '/' + pageNum + "/5").then(
                    (response) =>{
                        blogList.value = response.data.content ? response.data.content :[];
                    },
                    (error) => {
                        message.error(error)
                    }
                )
            };

            // 分页
            const pagination = ref();
            const current = ref(1);
            const blogNum = ref();
            const getBlogNum = () => {
                axios.get("/circleBlog/circleBlogListNum/" + circleId).then(
                    (response) => {
                        blogNum.value = response.data.content;
                    }
                )
            };

            /**
             * 发布部落博客
             * */
            const blogContent = ref('');
            const modalVisible = ref(false);
            const popModal = () => {
                modalVisible.value = true;
            };
            const saveCircleBlog = () => {
                const circleBlog = {
                    authorId: store.state.user.id,
                    circleId: circleId,
                    content: blogContent.value
                };
                axios.post("/circleBlog/saveCircleBlog", circleBlog).then((response) => {
                    const data = response.data;
                    if (data.success){
                        message.success("发言成功！");
                        blogContent.value = '';
                        modalVisible.value = false;
                        location.reload();
                    }else {
                        message.error(data.message);
                    }
                })
            };

            onMounted(()=>{
                getAllBlog(1);
                getBlogNum();
                console.log("部落管理者：", managerId);
                console.log("登录者：", store.state.user.id);
                console.log("是否相同：", store.state.user.id === managerId);
            });

            return {
                pagination,
                current,
                blogNum,
                blogList,
                getAllBlog,

                popModal,
                modalVisible,
                blogContent,
                saveCircleBlog,

                managerId,
                store,
            };
        },
    });
</script>

<style scoped>

</style>