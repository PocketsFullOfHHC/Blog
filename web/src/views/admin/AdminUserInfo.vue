<template>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
        <a-descriptions title="个人信息" :style="{ paddingTop: '25px' }" bordered :column="4">
            <template #extra>
                <a-button type="primary" v-if="!isEdit">编辑</a-button>
                <div v-else>
                    <a-button type="primary" danger  style="margin-right: 20px">保存</a-button>
                    <a-button type="primary">取消</a-button>
                </div>
            </template>
            <a-descriptions-item label="用户昵称：" :span="2"><input type="text" value="你好" v-show="isEdit.value">口袋饱饱</a-descriptions-item>
            <a-descriptions-item label="状态：" :span="2">
                <div v-if="true">
                    <a-badge status="processing" text="在线" />
                </div>
                <div v-else>
                    <a-badge status="default" text="离线"/>
                </div>
            </a-descriptions-item>
            <a-descriptions-item label="性别：" :span="2">男</a-descriptions-item>
            <a-descriptions-item label="年龄：" :span="2">23</a-descriptions-item>
            <a-descriptions-item label="头像：" :span="4">
                <a-avatar :size="64" :src="'http://localhost:8080/picture/avatars/' + avatarName" alt="Han Solo"/>
                <a-button type="primary" @click="showModal" :style="{marginLeft: '30px'}">修改头像</a-button>
            </a-descriptions-item>
            <a-descriptions-item label="加入的部落：" :span="4">2333333</a-descriptions-item>
            <a-descriptions-item label="自我介绍：" :span="4">我是谁呢我是谁呢我是谁呢我是谁呢我是谁呢我是谁呢我是谁呢我是谁呢我是谁呢我是谁呢</a-descriptions-item>
            <a-descriptions-item label="个性标签" :span="4">
                <a-tag style="background: #fff; border-style: dashed">
                    <plus-outlined />
                    自定义标签
                </a-tag>
            </a-descriptions-item>
        </a-descriptions>
        <a-modal
                v-model:open="visible"
                title="修改头像"
                ok-text="确认"
                cancel-text="取消"
                @ok="alterAvatar"
        >
            <div style="text-align: center">
                <a-upload
                        v-model:file-list="fileList"
                        name="avatar"
                        list-type="picture-card"
                        class="avatar-uploader"
                        :show-upload-list="false"
                        :action="' http://localhost:8080/user/upload/avatar'"
                        :before-upload="beforeUpload"
                        :headers="headers"
                        @change="handleChange"
                >
                    <a-avatar v-if="imageUrl" :size="120" :src="imageUrl" alt="Han Solo"/>
                    <div v-else>
                        <loading-outlined v-if="loading"></loading-outlined>
                        <plus-outlined v-else></plus-outlined>
                    </div>
                </a-upload>
            </div>
        </a-modal>
    </a-layout-content>
</template>

<script>
    import { ref, computed, defineComponent,} from 'vue';
    import store from '@/store';
    import { PlusOutlined, LoadingOutlined } from '@ant-design/icons-vue';
    import { message, Modal } from 'ant-design-vue';


    export default defineComponent({
        name: "AdminUserInfo",
        components: {
            LoadingOutlined,
            PlusOutlined,
        },
        setup(){
            const user = computed(() => store.state.user);

            /**
            * 显示头像
            * */
            const avatarName = ref();
            avatarName.value = store.state.user.avatar;

            const isEdit = ref();
            isEdit.value = false;


            /**
             * 头像上传
             */
            const fileList = ref([]);
            const loading = ref(false);
            const imageUrl = ref('');

            function getBase64(img, callback) {
                const reader = new FileReader();
                reader.addEventListener('load', () => callback(reader.result));
                reader.readAsDataURL(img);
            }

            const handleChange = info => {
                if (info.file.status === 'uploading') {
                    loading.value = true;
                    return;
                }
                if (info.file.status === 'done') {
                    getBase64(info.file.originFileObj, base64Url => {
                        imageUrl.value = base64Url;
                        loading.value = false;
                    });
                }
                if (info.file.status === 'error') {
                    loading.value = false;
                    message.error('上传错误');
                }
            };

            const beforeUpload = file => {
                console.log(file);
                const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
                if (!isJpgOrPng) {
                    message.error('您只能上传jpeg或png格式的图片');
                }
                const isLt2M = file.size / 1024 / 1024 < 2;
                if (!isLt2M) {
                    message.error('头像图片必须小于2M');
                }
                return isJpgOrPng && isLt2M;
            };

            /**
             * 头像上传对话框
             */

            const visible = ref(false);

            const showModal = () => {
                visible.value = true;
            };

            const alterAvatar = () => {
                visible.value = false;
            };

            const confirm = () => {
                Modal.confirm({
                    okText: '确认',
                    cancelText: '取消',
                });
            };



            return{
                fileList,
                loading,
                imageUrl,
                handleChange,
                beforeUpload,
                headers: {
                    userId: store.state.user.id,
                },
                isEdit,

                visible,
                showModal,
                alterAvatar,
                confirm,
                avatarName,
            }
        }
    })

</script>

<style scoped>

</style>