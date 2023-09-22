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
            <span key="comment-basic-reply-to" @click="alterCommentShow">
                <CommentOutlined />
                <span style="padding-left: 6px; cursor: auto">
                  {{blogList.commentNum}}
                </span>
            </span>
        </template>
        <template #author><a>{{blogList.authorName}}</a></template>
        <template #avatar>
            <a-avatar :src="'http://localhost:8080/picture/avatars/' + blogList.avatar" alt="Han Solo" />
        </template>
        <template #content>
            <div :innerHTML="html"></div>
        </template>
        <template #datetime>
            <a-tooltip :title="dayjs().format('YYYY-MM-DD HH:mm:ss')">
                <span>{{ dayjs().to(dayjs(blogList.publishTime)) }} update</span>
            </a-tooltip>
        </template>
    </a-comment>
    <!-- 评论展示-->
    <a-list
            v-if="comments.length && commentShow"
            :data-source="comments"
            :header="`共${comments.length}条评论`"
            item-layout="horizontal"
            :style="{paddingLeft:'40px'}"
    >
        <template #renderItem="{ item }">
            <a-list-item>
                <a-comment
                        :author="item.author"
                        :avatar="item.avatar"
                        :content="item.content"
                        :datetime="item.datetime"
                />
            </a-list-item>
        </template>
    </a-list>

    <!-- 发表评论 -->
    <a-comment v-if="commentShow" :style="{paddingLeft:'40px'}">
        <template #avatar>
            <a-avatar :src="'http://localhost:8080/picture/avatars/' + avatarName" alt="Han Solo" />
        </template>
        <template #content>
            <a-form-item>
                <a-textarea v-model:value="content" :rows="3" />
            </a-form-item>
            <a-form-item>
                <a-button html-type="submit" :loading="submitting" type="primary" @click="handleSubmitComment">
                    评论
                </a-button>
            </a-form-item>
        </template>
    </a-comment>
</template>

<script lang="ts">
    // 解决兼容性报错：Getting a value from the `props` in root scope of `setup()` will cause the value to lose reactivity  vue/no-setup-props-destructure
    /* eslint-disable */
    // eslint-disable-next-line vue/no-setup-props-destructure
    import dayjs from 'dayjs';
    import { LikeFilled, LikeOutlined, DislikeFilled, DislikeOutlined } from '@ant-design/icons-vue';
    import { defineComponent, ref } from 'vue';
    import relativeTime from 'dayjs/plugin/relativeTime';
    import { CommentOutlined } from '@ant-design/icons-vue';
    import axios from "axios";
    import store from '@/store';
    import {message} from "ant-design-vue";
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
             * 评论
             * */
            // 发表的评论内容
            const content = ref('');

            // 发表的评论
            const comment ={
                commentatorId: store.state.user.id,
                blogId: props.blogList.id,
                content: content.value,
            };

            // 展示的评论
            const comments = ref([{

            }]);

            const commentShow = ref(false);


            const alterCommentShow = () => {
                getComment();
                commentShow.value = commentShow.value ? false : true;
            };

            const submitting = ref(false);

            // 展示评论
            const getComment = () => {
                comments.value = [];
                axios.get("/comment/list/" + props.blogList.id).then((response) => {
                    const data = response.data;
                    console.log("评论内容:" + data.content);
                    if (data.success){
                        for(let item of data.content){
                            comments.value.push({
                                author: item.commentatorName,
                                avatar: 'http://localhost:8080/picture/avatars/' + item.avatar,
                                content: item.content,
                                datetime: dayjs().to(dayjs(item.commentTime)),
                            })
                        }
                    }else {
                        message.error(data.error)
                    }
                })
            };

            // 发表评论
            const handleSubmitComment = () => {
                // 判空
                if (!content.value) {
                    return;
                }
                submitting.value = true;
                comment.content = content.value;
                axios.post("/comment/save", comment).then((response) =>{
                    if (response.data.success){
                        message.success("评论成功");
                    }else {
                        message.error(response.data.message);
                    }
                });
                // 清空评论框
                content.value = '';
                setTimeout(() => {
                    getComment();
                    props.blogList.commentNum ++;
                    submitting.value = false;
                }, 600)
            };

            /**
             * 点赞点踩
             * */
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
                CommentOutlined,
                avatarName,

                comments,
                submitting,
                handleSubmitComment,
                alterCommentShow,
                commentShow,
                content,
            };
        },
    });
</script>

<style scoped>

</style>