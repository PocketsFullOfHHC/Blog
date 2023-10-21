<template>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
        <a-breadcrumb :style="{ margin: '16px 0' }">
            <a-breadcrumb-item>部落</a-breadcrumb-item>
        </a-breadcrumb>
        <a-layout style="padding: 24px 0; background: #fff">
            <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
                <a-button type="primary" @click="showCreateCircleModal">创建部落</a-button>
                <a-modal
                        v-model:open="createModalVisible"
                        title="创建部落"
                        ok-text="创建"
                        cancel-text="取消"
                        @ok="createCircle"
                >
                    <a-form-item label="部落名" name="circleName">
                        <a-input v-model:value="createdCircle.circleName" />
                    </a-form-item>
                    <a-form-item label="部落介绍" name="circleIntro">
                        <a-input v-model:value="createdCircle.intro" />
                    </a-form-item>
                </a-modal>
                <a-divider></a-divider>
                <a-modal
                        v-model:open="alterCircleInfoVisible"
                        title="修改部落信息"
                        ok-text="修改"
                        cancel-text="取消"
                        @ok="alterCircleInfo()"
                >
                    <a-form-item label="部落名" name="circleName">
                        <a-input v-model:value="circleName" />
                    </a-form-item>
                    <a-form-item label="部落介绍" name="circleIntro">
                        <a-input v-model:value="circleIntro" />
                    </a-form-item>
                </a-modal>
                <h4>我创建的部落</h4>
                <div style="padding: 20px 55px">
                    <a-row :gutter="60">
                        <a-col :span="8" v-for="( circle ) in createdCircleList" :key="circle.id" style="margin-bottom: 20px">
                            <a-card hoverable style="width: 320px">
                                <template #actions>
                                    <home-outlined key="home" @click="toCircleHomePage(circle.id)"/>
                                    <edit-outlined @click="showCircleInfo(circle.id, circle.circleName, circle.intro)"/>
                                    <a-popconfirm title="确认删除部落？" ok-text="是" cancel-text="否"  @confirm="deleteCircle(circle.id)">
                                        <delete-outlined key="import"/>
                                    </a-popconfirm>
                                </template>
                                <div style="text-align: center; font-size: 20px">{{circle.circleName}}</div>
                                <div style="font-size: 15px; color: rgba(0, 0, 0, 0.45); margin-top: 10px">部落介绍：{{circle.intro}}</div>
                            </a-card>
                        </a-col>
                    </a-row>
                </div>
                <a-divider></a-divider>
                <h4>所有部落</h4>
                <div style="padding: 20px 55px">
                    <a-row :gutter="60">
                        <a-col :span="8" v-for="( circle ) in circleList" :key="circle.id" style="margin-bottom: 20px">
                            <a-card hoverable style="width: 320px">
                                <template #actions>
                                    <home-outlined key="home" @click="toCircleHomePage(circle.id)"/>
                                    <a-popconfirm title="确认加入部落？" ok-text="是" cancel-text="否">
                                        <import-outlined key="import"/>
                                    </a-popconfirm>
                                </template>
                                <div style="text-align: center; font-size: 20px">{{circle.circleName}}</div>
                                <div style="font-size: 15px; color: rgba(0, 0, 0, 0.45); margin-top: 10px">部落介绍：{{circle.intro}}</div>
                            </a-card>
                        </a-col>
                    </a-row>
                </div>
            </a-layout-content>
        </a-layout>
    </a-layout-content>
</template>

<script>
    import { defineComponent, ref, onMounted } from 'vue';
    import axios from 'axios'
    import store from '@/store'
    import { HomeOutlined, DeleteOutlined, MessageOutlined, ImportOutlined, TeamOutlined, EditOutlined} from '@ant-design/icons-vue';
    import  { message }  from "ant-design-vue";
    import  {useRouter}  from "vue-router";
    export default defineComponent({
        name: "AdminCircle",
        components: {
            MessageOutlined,
            DeleteOutlined,
            HomeOutlined,
            ImportOutlined,
            TeamOutlined,
            EditOutlined,
        },
        setup() {
            /**
             * 获取全部部落
             * */
            let circleList = ref();
            const getCircleList = () => {
                axios.get("/circle/list").then((response) => {
                    const data = response.data;
                    if (data){
                        circleList.value = data.content ? data.content :[];
                        console.log(circleList.value);
                    }
                })
            };

            /**
             * 获取我创建的全部部落
             * */
            let createdCircleList = ref();

            const getCreatedCircleList = () => {
                console.log("createCircleList", createdCircleList.value);
                axios.get("/circle/myCreatedCircle/" + store.state.user.id).then((response) => {
                    const data = response.data;
                    if (data){
                        createdCircleList.value = data.content;
                    }
                })
            };

            /**
             * 创建部落
             */
            const createdCircle = ref();
            createdCircle.value = {
                managerId: store.state.user.id,
                circleName: '',
                intro: '',
            };

            // 显示对话框
            const createModalVisible = ref(false);
            const showCreateCircleModal = () => {
                createModalVisible.value = true;
            };

            // 创建新部落
            const createCircle = () => {
                axios.post('/circle/create', createdCircle.value).then((response) => {
                    const data = response.data;
                    if (data.success){
                        message.success("部落创建成功！")
                        createdCircle.value.circleIntro = '';
                        createdCircle.value.circleName = '';
                        location.reload();
                        createModalVisible.value = false;
                    }else {
                        message.error(data.message);
                        createModalVisible.value = false;
                    }
                })
            };

            /**
             * 删除部落
             */
            const deleteCircle = (circleId) => {
                axios.get('/circle/delete/' + circleId).then((response) => {
                    const data = response.data;
                    if (data.success){
                        getCircleList();
                        getCreatedCircleList();
                        message.success("删除部落成功！")
                    }else {
                        message.error(data.message);
                    }
                })
            };

            /**
             * 修改部落信息
             */
            const alterCircleInfoVisible = ref(false);
            const circleId = ref(0);
            const circleName = ref("");
            const circleIntro = ref("");

            // 显示原始信息
            const showCircleInfo = (id, name, intro) => {
                circleId.value = id;
                circleName.value = name;
                circleIntro.value = intro;
                alterCircleInfoVisible.value = true;
            };

            // 修改部落信息
            const alterCircleInfo = () => {
                const updateCircleReq = {
                    id: circleId.value,
                    circleName: circleName.value,
                    intro: circleIntro.value,
                };
                axios.post('/circle/update', updateCircleReq).then((response) => {
                    const data = response.data;
                    if (data.success) {
                        location.reload();
                        message.success("修改部落信息成功！")
                        alterCircleInfoVisible.value = false;
                    }
                })
            };

            /**
             * 进入部落主页
             */
            const router = useRouter();
            const toCircleHomePage = (circleId) => {
                router.push ({
                    path:"/circleHomePage",
                    query:{
                        circleId: circleId
                    }
                })
            };

            onMounted(()=>{
                getCircleList();
                getCreatedCircleList();
            });

            return {
                circleList,
                createdCircle,

                showCreateCircleModal,
                createCircle,
                createModalVisible,
                deleteCircle,
                createdCircleList,
                showCircleInfo,
                alterCircleInfoVisible,
                alterCircleInfo,

                circleId,
                circleName,
                circleIntro,

                toCircleHomePage,
            };
        },
    });
</script>