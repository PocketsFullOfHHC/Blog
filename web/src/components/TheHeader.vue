<template>
    <a-layout-header :style="{ position: 'fixed', zIndex: 1, width: '100%' }" id="components-layout-demo-fixed">
        <div class="logo" />
        <a-menu
                theme="dark"
                mode="horizontal"
                :style="{ lineHeight: '64px' }"
        >
            <a-menu-item key="1">nav 1</a-menu-item>
            <a-menu-item key="2">nav 2</a-menu-item>
            <a-menu-item key="3">nav 3</a-menu-item>
            <a-menu-item key="4" :style="{marginLeft:'auto'}" @click="popSignIn">登录</a-menu-item>
            <a-menu-item key="5" @click="popSignUp">注册</a-menu-item>
        </a-menu>
    </a-layout-header>
    <a-modal v-model:open="signInVisible" title="登录" @ok="handleSignInOk">
        <a-form :label-col="{ span: 3 }" :wrapper-col="{ span: 21 }">
            <a-form-item label="用户名">
                <a-input placeholder="请输入用户名"/>
            </a-form-item>
            <a-form-item label="密码">
                <a-input type="password" placeholder="请输入密码"/>
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
    import {defineComponent, ref, reactive} from 'vue';
    import axios from "axios";
    import { message } from "ant-design-vue";
    import type { Rule } from 'ant-design-vue/es/form';
    import type { FormInstance } from 'ant-design-vue';
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
                        // 修复报错：加!
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
                        message.success('注册成功');
                        signUpVisible.value = false;
                    } else {
                        message.error(response.data.message);
                    }

                })
            };

            /**
             * 登录
             */
            const signInVisible = ref(false);

            const signInUser = reactive({
                username: '',
                password: ''
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
                    } else {
                        message.error(data.message);
                    }
                })
            };

            return{
                popSignIn,
                popSignUp,
                signInVisible,
                signUpVisible,
                handleSignInOk,
                handleSignUpOk,
                signUpUser,
                confirmPassword,

                rules,
                formState,
                formRef,
                handleFinish,
                handleFinishFailed,
                handleValidate,
                checked,
            }
        }
    })
</script>

<style scoped>

    #components-layout-demo-fixed .logo {
        width: 120px;
        height: 31px;
        background: rgba(255, 255, 255, 0.2);
        margin: 16px 24px 16px 0;
        float: left;
    }
</style>