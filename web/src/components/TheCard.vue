<template>
    <a-comment id="comment">
        <template #actions>
            <span key="comment-basic-like">
                <a-tooltip title="Like">
                    <template v-if="action === 'liked'">
                        <LikeFilled @click="like" />
                    </template>
                    <template v-else>
                        <LikeOutlined @click="like" />
                    </template>
                </a-tooltip>
                <span style="padding-left: 8px; cursor: auto">
                    {{ likes }}
                </span>
            </span>
            <span key="comment-basic-dislike">
                <a-tooltip title="Dislike">
                    <template v-if="action === 'disliked'">
                        <DislikeFilled @click="dislike" />
                    </template>
                    <template v-else>
                        <DislikeOutlined @click="dislike" />
                    </template>
                </a-tooltip>
                <span style="padding-left: 8px; cursor: auto">
                    {{ dislikes }}
                </span>
            </span>
            <span key="comment-basic-reply-to">
                Reply to
            </span>
        </template>
        <template #author><a>{{blogList.authorName}}</a></template>
        <template #avatar>
            <a-avatar src="https://www.antdv.com/assets/logo.1ef800a8.svg" alt="Han Solo" />
        </template>
        <template #content>
            <a-image
                    :width="200"
                    src="https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png"
                    style="padding: 10px 0 10px 0"
            />
            <div :innerHTML="html"></div>
        </template>
        <template #datetime>
            <a-tooltip :title="dayjs().format('YYYY-MM-DD HH:mm:ss')">
                <span>{{ dayjs().to(dayjs(blogList.publishTime)) }}</span>
            </a-tooltip>
        </template>
    </a-comment>
</template>

<script lang="ts">
    // 解决兼容性报错：Getting a value from the `props` in root scope of `setup()` will cause the value to lose reactivity  vue/no-setup-props-destructure
    /* eslint-disable */
    // eslint-disable-next-line vue/no-setup-props-destructure
    import dayjs from 'dayjs';
    import { LikeFilled, LikeOutlined, DislikeFilled, DislikeOutlined } from '@ant-design/icons-vue';
    import { defineComponent, ref, toRef } from 'vue';
    import relativeTime from 'dayjs/plugin/relativeTime';
    dayjs.extend(relativeTime);
    export default defineComponent({
        name: "TheCard",
        components:{
            LikeFilled,
            LikeOutlined,
            DislikeFilled,
            DislikeOutlined,
        },
        props: ['blogList'],
        // setup中使用props
        setup(props) {

            /**
             * 展示博客内容
             * */
            const html = ref();
            html.value = props.blogList.content;

            const curDate = dayjs(props.blogList.publishTime).format();
            console.log(curDate);

            const likes = ref<number>(0);
            const dislikes = ref<number>(0);
            const action = ref<string>();

            const like = () => {
                likes.value = 1;
                dislikes.value = 0;
                action.value = 'liked';
            };

            const dislike = () => {
                likes.value = 0;
                dislikes.value = 1;
                action.value = 'disliked';
            };

            return {
                likes,
                dislikes,
                action,
                like,
                dislike,
                dayjs,
                html,
            };
        },
    });
</script>

<style scoped>
    #comment{
        width:800px;
        margin: 0 70px 0 70px;
        float: left;
    }
</style>