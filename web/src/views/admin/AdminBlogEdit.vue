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
    <a-button type="primary" shape="round" size="large" :style="{'float': 'right', 'margin': '15px'}" @click="editBlog()">修改</a-button>
    </a-layout-content>
</template>

<script>
    import '@wangeditor/editor/dist/css/style.css';
    import { onBeforeUnmount, ref, shallowRef, getCurrentInstance } from 'vue';
    import { Editor, Toolbar } from '@wangeditor/editor-for-vue';
    import { message } from 'ant-design-vue';
    import axios from 'axios';
    import { useRouter } from 'vue-router';

    export default {
        name: "AdminBlogEdit",
        components: {
            Editor,
            Toolbar
        },
        setup(){
            // 编辑器实例，必须用 shallowRef
            const editorRef = shallowRef();

            // 内容 HTML
            const valueHtml = ref('');
            let { proxy } = getCurrentInstance();
            valueHtml.value = proxy.$route.query.content;

            // 博客id
            const blogId = proxy.$route.query.blogId;

            const toolbarConfig = {
                // 排除不需要的菜单组
                excludeKeys: [
                    'group-more-style',
                    'fullScreen',
                ],
            };

            const editorConfig = {
                placeholder: '请输入内容...',
            };

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

            /**
             * 修改博客
             * */
            const editBlog = () => {
                const blog = {
                    blogId : blogId,
                    content: editorRef.value.getHtml(),
                };
                axios.post("/blog/edit", blog).then((response) => {
                    const data = response.data;
                    if(data.success){
                        message.success("保存成功！")
                    }else {
                        message.error(data.message);
                    }
                });
                push('/myself');
            };

            return {
                editorRef,
                valueHtml,
                mode: 'simple',
                toolbarConfig,
                editorConfig,

                handleCreated,
                editBlog,
            };
        },
    }
</script>

<style scoped>

</style>