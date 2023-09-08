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
    <a-modal v-model:visible="signInVisible" title="登录" @ok="handleSignInOk">
        <a-form :label-col="{ span: 3 }" :wrapper-col="{ span: 21 }">
            <a-form-item label="用户名">
                <a-input placeholder="请输入用户名"/>
            </a-form-item>
            <a-form-item label="密码">
                <a-input type="password" placeholder="请输入密码"/>
            </a-form-item>
        </a-form>
    </a-modal>

    <a-modal v-model:visible="signUpVisible" title="注册" @ok="handleSignUpOk">
        <a-form :label-col="{ span: 4 }" :wrapper-col="{ span: 20 }">
            <a-form-item label="用户名">
                <!-- :model和v-model:value的区别：:model是v-bind:model的缩写，单项绑定，并不是v-model -->
                <a-input placeholder="请输入用户名"/>
            </a-form-item>
            <a-form-item label="昵称">
                <a-input placeholder="请输入昵称"/>
            </a-form-item>
            <a-form-item label="密码">
                <a-input placeholder="请输入密码" type="password"/>
            </a-form-item>
            <a-form-item label="确认密码">
                <a-input placeholder="请再次输入密码" type="password"/>
            </a-form-item>
        </a-form>
    </a-modal>

</template>

<script lang="ts">
    import {defineComponent, ref} from 'vue';
    export default defineComponent ({
        name: "TheHeader",
        setup(){

            /**
             * 登录
             */
            const signInVisible = ref(false);

            const popSignIn = () => {
                signInVisible.value = true;
            };

            const handleSignInOk = () => {
                signInVisible.value = false;
            };

            /**
             * 注册
             */
            const signUpVisible = ref(false);
            const signUpUser = ref({
                username: '',
                name: '',
                password: ''
            });
            const confirmPassword = ref();

            const popSignUp = () => {
                signUpVisible.value = true;
            };
            const handleSignUpOk = () => {
                signInVisible.value = false;
            };
            return{
                popSignIn,
                popSignUp,
                signInVisible,
                signUpVisible,
                handleSignInOk,
                handleSignUpOk,
                signUpUser,
                confirmPassword
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