<template>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
        <a-descriptions title="个人信息" :style="{ paddingTop: '25px' }" bordered :column="4">
            <template #extra>
                <a-button type="primary" v-if="!isEdit" @click="editInfo">编辑</a-button>
                <div v-else>
                    <a-button type="primary" danger @click="updateInfo" style="margin-right: 20px">保存</a-button>
                    <a-button type="primary" @click="cancelEdit">取消</a-button>
                </div>
            </template>
            <a-descriptions-item label="用户昵称：" :span="2">
                <div v-if="isEdit">
                    <a-input type="text" :style="{width:'200px'}" v-model:value="user.name" />
                </div>
                <div v-else>
                    <span>{{user.name}}</span>
                </div>
            </a-descriptions-item>
            <a-descriptions-item label="状态：" :span="2">
                <div v-if="user.status">
                    <a-badge status="processing" text="在线" />
                </div>
                <div v-else>
                    <a-badge status="default" text="离线"/>
                </div>
            </a-descriptions-item>
            <a-descriptions-item label="性别：" :span="2">
                <a-radio-group v-model:value="user.gender" name="radioGroup" v-if="isEdit">
                    <a-radio value="male">男</a-radio>
                    <a-radio value="female">女</a-radio>
                </a-radio-group>
                <span v-else>{{user.gender === 'male' ? '男' : '女'}}</span>
            </a-descriptions-item>
            <a-descriptions-item label="年龄：" :span="2">
                <div v-if="isEdit">
                    <a-input-number id="userAge" v-model:value="user.age" :min="1" :max="100" />
                </div>
                <span v-else>{{user.age}}</span>
            </a-descriptions-item>
            <a-descriptions-item label="头像：" :span="4">
                <a-avatar :size="64" :src="'http://localhost:8080/picture/avatars/' + avatarName" alt="Han Solo"/>
                <a-button type="primary" v-if="isEdit" @click="showModal" :style="{marginLeft: '30px'}">修改头像</a-button>
            </a-descriptions-item>
            <a-descriptions-item label="加入的部落：" :span="4">{{user.circles}}</a-descriptions-item>
            <a-descriptions-item label="自我介绍：" :span="4">
                <div v-if="isEdit">
                    <a-textarea :rows="4" placeholder="介绍一下自己吧~" :maxlength="1000" v-model:value="user.selfIntro"/>
                </div>
                <div v-else>
                    {{user.selfIntro}}
                </div>
            </a-descriptions-item>
            <a-descriptions-item label="个性标签" :span="4">
                <div v-if="isEdit">
                    <template v-for="(tag, index) in tags" :key="tag">
                        <a-tooltip v-if="tag.length > 10" :title="tag">
                            <!-- 只展示前十个 -->
                            <a-tag :closable="index !== 0" @close="handleClose(tag)">
                                {{ `${tag.slice(0, 10)}...` }}
                            </a-tag>
                        </a-tooltip>
                        <a-tag v-else :closable="index !== 0" @close="handleClose(tag)">
                            {{ tag }}
                        </a-tag>
                    </template>
                    <!-- 失去焦点或回车时提交表单 -->
                    <a-input
                            v-if="inputVisible"
                            ref="inputRef"
                            v-model:value="inputValue"
                            type="text"
                            size="small"
                            :style="{ width: '78px' }"
                            @blur="handleInputConfirm"
                            @keyup.enter="handleInputConfirm"
                    />
                    <a-tag v-else style="background: #fff; border-style: dashed" @click="showInput">
                        <plus-outlined />
                        自定义标签
                    </a-tag>
                </div>
                <div v-else>
                    <span v-for="(tag, index) in tags" :key="tag">
                        <a-tag :color="color[index % 7]">{{tag}}</a-tag>
                    </span>
                </div>
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
    import { ref, computed, defineComponent, onMounted, reactive, nextTick, toRefs} from 'vue';
    import store from '@/store';
    import axios from 'axios';
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
             * 显示用户信息
             * */
            const getUserInfo = () => {
                axios.get("/user/info/" + user.value.id).then((response) => {
                    const userInfo = response.data.content;
                    console.log(userInfo);
                    store.commit("setUser", userInfo);
                })
            };

            /**
             * 更改用户信息
             * */
            const isEdit = ref();
            isEdit.value = false;

            const editInfo = () => {
                isEdit.value = isEdit.value ? false : true;
            };

            const updateInfo = () => {
                isEdit.value = isEdit.value ? false : true;
            };

            const cancelEdit = () => {
                isEdit.value = isEdit.value ? false : true;
            };

            /**
             * 个人标签
             */
            const inputRef = ref();
            const tags = ref([]);
            console.log(store.state.user.tags);
            const color = ['pink','red','orange','green','cyan','blue','purple'];

            // 解析数据库中的标签json
            if (store.state.user.tags){
                tags.value = store.state.user.tags.split(',');
                console.log(tags.value);
                console.log(typeof (tags.value));
            }else {
                tags.value = []
            }

            const state = reactive({
                tags: tags.value,
                inputVisible: false,
                inputValue: '',
            });

            // 删除标签
            const handleClose = removedTag => {
                const tags = state.tags.filter(tag => tag !== removedTag);
                console.log(tags);
                state.tags = tags;
            };

            // 展示标签修改框
            const showInput = () => {
                state.inputVisible = true;
                // 将里面的回调函数延迟下次DOM更新循环结束之后执行，在修改数据之后立即使用这个方法，获取更新后的DOM
                nextTick(() => {
                    // 获取输入框的焦点
                    inputRef.value.focus();
                });
            };

            const handleInputConfirm = () => {
                const inputValue = state.inputValue;
                let tags = state.tags;
                console.log("tags的类型：" + typeof (Object.values(tags)));
                // 判断添加的标签是否为空或与旧的重复
                if (inputValue && Object.values(tags).indexOf(inputValue) === -1) {
                    tags = [...tags, inputValue];
                }
                console.log(tags);
                // 还原状态
                Object.assign(state, {
                    tags,
                    inputVisible: false,
                    inputValue: '',
                });
            };


            /**
            * 显示头像
            * */
            const avatarName = ref();
            avatarName.value = store.state.user.avatar;


            /**
             * 头像上传
             */
            const fileList = ref([]);
            const loading = ref(false);
            const imageUrl = ref('');

            // 获取图片base64编码
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
                message.success("头像修改成功！");
            };

            const confirm = () => {
                Modal.confirm({
                    okText: '确认',
                    cancelText: '取消',
                });
            };

            onMounted(() =>{
                getUserInfo();
            });

            return{
                user,

                fileList,
                loading,
                imageUrl,
                handleChange,
                beforeUpload,
                headers: {
                    userId: store.state.user.id,
                },

                getUserInfo,
                editInfo,
                updateInfo,
                cancelEdit,
                isEdit,

                visible,
                showModal,
                alterAvatar,
                confirm,
                avatarName,

                ...toRefs(state),
                handleClose,
                showInput,
                handleInputConfirm,
                inputRef,
                color
            }
        }
    })

</script>

<style scoped>

</style>