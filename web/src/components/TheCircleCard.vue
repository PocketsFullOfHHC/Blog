<template>
    <a-comment id="comment">
        <template #author><a>{{blogList.authorName}}</a></template>
        <template #avatar>
            <a-avatar @click="toUserInfo()" :src="'http://localhost:8080/picture/avatars/' + blogList.avatar" alt="Han Solo" />
        </template>
        <template #content>
            <div :innerHTML="html"></div>
        </template>
        <template #datetime>
            <a-tooltip :title="dayjs().format('YYYY-MM-DD HH:mm:ss')">
                <span>{{ dayjs().to(dayjs(blogList.publishTime)) }} update</span>
            </a-tooltip>
        </template>
        <a-popconfirm title="确定要删除这篇博客？" @confirm="deleteCircleBlog()" ok-text="是" cancel-text="否">
            <a-button size="small" danger v-if="ifShowDeleteButton" type="primary">删除</a-button>
        </a-popconfirm>
    </a-comment>
    <a-divider></a-divider>
</template>

<script lang="ts">
    // 解决兼容性报错：Getting a value from the `props` in root scope of `setup()` will cause the value to lose reactivity  vue/no-setup-props-destructure
    /* eslint-disable */
    // eslint-disable-next-line vue/no-setup-props-destructure
    import dayjs from 'dayjs';
    import { defineComponent, ref } from 'vue';
    import relativeTime from 'dayjs/plugin/relativeTime';
    import axios from "axios";
    import store from '@/store';
    import {message} from "ant-design-vue";
    import {useRouter}  from "vue-router";
    dayjs.extend(relativeTime);
    export default defineComponent({
        name: "TheCircleCard",
        props: ['blogList', 'ifShowDeleteButton'],
        // setup中使用props
        setup(props) {

            /**
             * 显示头像
             * */
            const avatarName = ref();
            avatarName.value = store.state.user.avatar;

            /**
             * 展示博客内容
             * */
            const html = ref();
            html.value = props.blogList.content;

            const curDate = dayjs(props.blogList.publishTime).format();
            console.log(curDate);

            /**
             * 点击头像进入主页
             */
            const router = useRouter();
            const toUserInfo = () => {
                router.push ({
                    path:"/userHomePage",
                    query:{
                        authorId: props.blogList.authorId
                    }
                })
            };

            /**
             * 删除博客
             * */
            const deleteCircleBlog = () => {
                axios.get('/circleBlog/deleteCircleBlog/' + props.blogList.id).then((response) => {
                    const data = response.data;
                    if (data.success){
                        message.success("删除成功！")
                        location.reload();
                    }else {
                        message.error(data.message);
                    }
                })
            };

            return {
                dayjs,
                html,
                avatarName,

                toUserInfo,
                deleteCircleBlog,
            };
        },
    });
</script>

<style scoped>

</style>