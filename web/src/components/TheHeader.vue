<template>
    <a-layout-header :style="{ position: 'fixed', zIndex: 1}" id="components-layout-demo-fixed">
        <div class="logo" />
        <a-menu
                theme="dark"
                mode="horizontal"
                :style="{ lineHeight: '64px' }"
        >
            <a-menu-item key="1"><router-link to="/">首页</router-link></a-menu-item>
            <a-menu-item key="2" v-if="user.id"><router-link to="/myself">我的博客</router-link></a-menu-item>
            <a-menu-item key="3" v-if="user.id"><router-link to="/publish">发布博客</router-link></a-menu-item>
            <a-menu-item key="4" v-if="!user.id" :style="{marginLeft:'auto'}" @click="popSignIn">登录</a-menu-item>
            <a-menu-item key="5" v-if="!user.id" @click="popSignUp">注册</a-menu-item>
            <a-menu-item key="6" v-if="user.id" :style="{marginLeft:'auto'}">
                <a-dropdown>
                    <a class="ant-dropdown-link" @click.prevent>您好：{{user.name}}<DownOutlined/></a>
                    <template #overlay>
                        <a-menu>
                            <a-menu-item>
                                <router-link to="">
                                    <a-button type="text">修改信息</a-button>
                                </router-link>
                            </a-menu-item>
                            <a-menu-item>
                                <a-popconfirm
                                        title="确认退出登录?"
                                        ok-text="是"
                                        cancel-text="否"
                                        @confirm="logout()"
                                >
                                    <router-link to="">
                                        <a-button type="text">退出登录</a-button>
                                    </router-link>
                                </a-popconfirm>
                            </a-menu-item>
                        </a-menu>
                    </template>
                </a-dropdown>
            </a-menu-item>
        </a-menu>
    </a-layout-header>
    <a-modal v-model:open="signInVisible" title="登录" @ok="handleSignInOk" ok-text="登录" cancel-text="取消">
        <a-form :label-col="{ span: 3 }" :wrapper-col="{ span: 21 }">
            <a-form-item label="用户名">
                <a-input v-model:value="signInUser.username" placeholder="请输入用户名"/>
            </a-form-item>
            <a-form-item label="密码">
                <a-input v-model:value="signInUser.password" type="password" placeholder="请输入密码"/>
            </a-form-item>
        </a-form>
    </a-modal>

    <!-- 后续修改:antd组件中添加密码验证 -->
    <!-- visible被open替代掉了 -->
    <a-modal
            v-model:open="signUpVisible"
            title="注册"
            @ok="handleSignUpOk"
            ok-text="注册"
            cancel-text="取消"
            :ok-button-props="checked ? { disabled: false } : { disabled: true }"

    >
        <a-form
                ref="formRef"
                :label-col="{ span: 4 }"
                :wrapper-col="{ span: 20 }"
                :model="formState"
                :rules="rules"
                @finish="handleFinish"
                @validate="handleValidate"
                @finishFailed="handleFinishFailed"
        >
            <a-form-item label="用户名">
                <!-- :model和v-model:value的区别：:model是v-bind:model的缩写，单项绑定，并不是v-model -->
                <a-input v-model:value="signUpUser.username" placeholder="请输入用户名"/>
            </a-form-item>
            <a-form-item label="昵称">
                <a-input v-model:value="signUpUser.name" placeholder="请输入昵称"/>
            </a-form-item>
            <a-form-item has-feedback label="密码" name="password">
                <a-input v-model:value="formState.password" placeholder="请输入密码" type="password" autocomplete="off"/>
            </a-form-item>
            <a-form-item has-feedback label="确认密码" name="checkPass">
                <a-input v-model:value="formState.checkPass" placeholder="请再次输入密码" type="password" autocomplete="off"/>
            </a-form-item>
        </a-form>
    </a-modal>

</template>

<script lang="ts">
    import { defineComponent, ref, reactive, computed } from 'vue';
    import { DownOutlined } from '@ant-design/icons-vue';
    import axios from "axios";
    import { message } from "ant-design-vue";
    import type { Rule } from 'ant-design-vue/es/form';
    import type { FormInstance } from 'ant-design-vue';
    import store from '@/store'
    import {useRouter} from "vue-router";
    declare let hexMd5: any;
    declare let KEY: any;

    export default defineComponent ({
        name: "TheHeader",
        setup(){
            /**
             * 密码校验
             */
            let checked = ref(false);
            interface FormState {
                password: string;
                checkPass: string;
            }
            const formRef = ref<FormInstance>();
            const formState = reactive<FormState>({
                password: '',
                checkPass: '',
            });

            const validatePass = async (_rule: Rule, value: string) => {
                if (value === '') {
                    checked.value = false;
                    return Promise.reject('请输入密码');
                } else {
                    if (formState.checkPass !== '') {
                        checked.value = false;
                        // 修复报错：加？
                        formRef.value?.validateFields('checkPass');
                    }
                    return Promise.resolve();
                }
            };

            const validatePass2 = async (_rule: Rule, value: string) => {
                if (value === '') {
                    checked.value = false;
                    return Promise.reject('请再次输入密码');
                } else if (value !== formState.password) {
                    checked.value = false;
                    return Promise.reject("两次密码输入不一致!");
                } else {
                    checked.value = true;
                    return Promise.resolve();
                }
            };

            const rules: Record<string, Rule[]> = {
                password: [{
                    validator: validatePass,
                    trigger: 'change',
                }],
                checkPass: [{
                    validator: validatePass2,
                    trigger: 'change',
                }]
            };
            const handleFinish = (values: FormState) => {
                console.log("注册成功" + values, formState);
            };
            const handleFinishFailed = (errors: any) => {
                console.log("注册失败：" + errors);
            };
            const handleValidate = (...args: any[]) => {
                console.log("校验结果：" + args);
            };

            /**
             * 登录
             */
            // 实时监听user的变化
            const user = computed(() => store.state.user);

            const signInVisible = ref(false);

            const signInUser = reactive({
                username: 'test2',
                password: '111222333'
            });

            const popSignIn = () => {
                signInVisible.value = true;
            };

            const handleSignInOk = () => {
                console.log("开始登录");
                // 前端密码第一次加密
                signInUser.password = hexMd5(signInUser.password + KEY);
                axios.post("/user/login", signInUser).then((response) => {
                    const data = response.data;
                    if (data.success) {
                        message.success("登录成功！");
                        signInVisible.value = false;
                        // 存入vuex中的state里
                        store.commit("setUser", data.content);
                    } else {
                        message.error(data.message);
                    }
                })
            };

            /**
             * 注册
             */
            /* ref只能用于将基本类型数据转换成响应式数据，而reactive可以将任意对象转换成响应式数据 */
            const signUpVisible = ref(false);
            const signUpUser = reactive({
                username: '',
                name: '',
                password: ''
            });
            const confirmPassword = ref('');

            const popSignUp = () => {
                signUpVisible.value = true;
            };
            const handleSignUpOk = () => {
                signUpUser.password = formState.password;
                // 前端密码第一次加密
                signUpUser.password = hexMd5(signUpUser.password + KEY);
                axios.post("/user/signup", signUpUser).then((response) => {
                    if(response.data.success){
                        // 自动登录
                        signInUser.username = response.data.content.username;
                        signInUser.password = signUpUser.password;
                        axios.post("/user/login", signInUser).then((res) => {
                            console.log(res);
                            if(response.data.success){
                                store.commit("setUser", res.data.content);
                                message.success('注册并自动登录成功');
                                signUpVisible.value = false;
                            } else {
                                message.error(res.data.message);
                            }
                        })
                    } else {
                        message.error(response.data.message);
                    }
                })
            };

            /**
             * 退出登录
             * */
            const { push } = useRouter();
            const logout = () => {
                console.log("开始退出登录");
                axios.get('/user/logout/' + user.value.token).then((response) => {
                    const data = response.data;
                    if (data.success) {
                        message.success("退出登录成功！");
                        // 将前端缓存及vuex里的user清空
                        store.commit("setUser", {});
                        // 跳转到首页
                        push('/');
                    } else {
                        message.error(data.message);
                    }
                });
            };
            return{
                popSignIn,
                popSignUp,
                signInVisible,
                signUpVisible,
                handleSignInOk,
                handleSignUpOk,
                signInUser,
                signUpUser,
                confirmPassword,

                rules,
                formState,
                formRef,
                handleFinish,
                handleFinishFailed,
                handleValidate,
                logout,
                checked,
                user,

                DownOutlined,
            }
        }
    })
</script>

<style scoped>

    #components-layout-demo-fixed{
        width:100%;
    }

    #components-layout-demo-fixed .logo {
        width: 120px;
        height: 31px;
        background: rgba(255, 255, 255, 0.2);
        margin: 16px 24px 16px 0;
        float: left;
    }
</style>