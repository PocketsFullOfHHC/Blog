<template>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
        <a-breadcrumb :style="{ margin: '16px 0' }">
            <a-breadcrumb-item>Home</a-breadcrumb-item>
        </a-breadcrumb>
        <div :style="{border: '1px solid #ccc'}">
            <a-layout style="padding: 5px 0; background: #fff">
                <a-layout-content :style="{ padding: '0 100px' }">
                    <Toolbar
                            style="border-bottom: 1px solid #ccc"
                            :editor="editorRef"
                            :defaultConfig="toolbarConfig"
                            :mode="mode"
                    />
                    <Editor
                            style="height: 500px; overflow-y: hidden;"
                            v-model="valueHtml"
                            :defaultConfig="editorConfig"
                            :mode="mode"
                            @onCreated="handleCreated"
                    />
                </a-layout-content>
            </a-layout>
        </div>
    <a-button type="primary" shape="round" size="large" :style="{'float': 'right', 'margin': '15px'}" @click="publishBlog()">发帖</a-button>
    </a-layout-content>
</template>

<script>
    import '@wangeditor/editor/dist/css/style.css';
    import { onBeforeUnmount, ref, shallowRef, onMounted } from 'vue';
    import { Editor, Toolbar } from '@wangeditor/editor-for-vue';
    import { message } from 'ant-design-vue';
    import axios from 'axios';
    import store from '@/store';
    import { useRouter } from 'vue-router';

    export default {
        name: "AdminPublish",
        components: {
            Editor,
            Toolbar
        },
        setup(){
            // 编辑器实例，必须用 shallowRef
            const editorRef = shallowRef();

            // 内容 HTML
            const valueHtml = ref('');

            // 模拟 ajax 异步获取内容
            onMounted(() => {
                setTimeout(() => {
                    valueHtml.value = '<p>记录下你想说的话吧</p>';
                    console.log(editorRef.value.getHtml());
                }, 1500);
            });

            const toolbarConfig = {
                // 排除不需要的菜单组
                excludeKeys: [
                    'group-more-style',
                    'fullScreen',
                ],
            };
            const editorConfig = { placeholder: '请输入内容...' };

            // 组件销毁时，也及时销毁编辑器
            onBeforeUnmount(() => {
                const editor = editorRef.value;
                if (editor == null) return;
                editor.destroy();
            });

            const handleCreated = (editor) => {
                editorRef.value = editor // 记录 editor 实例，重要！
            };

            const { push } = useRouter();

            const publishBlog = () => {
                const blog = {
                    authorId: store.state.user.id,
                    content: editorRef.value.getHtml(),
                };
                axios.post("/blog/publish", blog).then((response) => {
                    const data = response.data;
                    if(data.success){
                        message.success("发帖成功！");
                        // 发博客成功后跳转到我的博客界面
                        setTimeout(() => {
                            push('/myself');
                        }, 300);
                    }else {
                        message.error(data.message);
                    }
                })
            };

            return {
                editorRef,
                valueHtml,
                mode: 'simple',
                toolbarConfig,
                editorConfig,

                handleCreated,
                publishBlog,
            };
        },
    }
</script>

<style scoped>

</style>