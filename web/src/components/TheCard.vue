<template>
    <a-comment id="comment">
        <template #actions>
            <span key="comment-basic-like">
                <a-tooltip title="点赞">
                    <template v-if="action === 'liked'">
                        <LikeFilled @click="like" />
                    </template>
                    <template v-else>
                        <LikeOutlined @click="like" />
                    </template>
                </a-tooltip>
                <span style="padding-left: 8px; cursor: auto">
                    {{ blogList.voteNum }}
                </span>
            </span>
            <span key="comment-basic-dislike">
                <a-tooltip title="点踩">
                    <template v-if="action === 'disliked'">
                        <DislikeFilled @click="dislike" />
                    </template>
                    <template v-else>
                        <DislikeOutlined @click="dislike" />
                    </template>
                </a-tooltip>
                <span style="padding-left: 8px; cursor: auto">
                    {{ blogList.opposeNum }}
                </span>
            </span>
            <span key="comment-basic-reply-to" @click="alterCommentShow">
                <CommentOutlined />
                <span style="padding-left: 6px; cursor: auto">
                  {{blogList.commentNum}}
                </span>
            </span>
            <span key="collect">
                <a-tooltip title="收藏">
                    <template v-if="isCollect === 1">
                        <StarFilled @click="collectBlog"/>
                    </template>
                    <template v-else>
                        <StarOutlined @click="collectBlog"/>
                    </template>
                </a-tooltip>
            </span>
        </template>
        <!-- 点赞展示 -->
        <a-list
                v-if="likesName.length > 1"
                :data-source="likesName"
                item-layout="horizontal"
        >
            <template #renderItem="{ index, item }">
                <a style="pointer-events:none" v-if="item.likeName !== undefined && index === likesName.length - 1">{{item.likeName}}。</a>
                <a style="pointer-events:none" v-else-if="item.likeName !== undefined && index !== likesName.length - 1">{{item.likeName}}，</a>
                <a style="pointer-events:none" v-else><LikeTwoTone/>{{item.likeName}}点赞者：</a>
            </template>
        </a-list>
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
    </a-comment>
    <!-- 评论展示-->
    <a-list
            v-if="comments.length && commentShow"
            :data-source="comments"
            :header="`共${comments.length}条评论`"
            item-layout="horizontal"
            :style="{paddingLeft:'45px'}"
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
    <a-comment v-if="commentShow" :style="{paddingLeft:'50px'}">
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
    import { LikeFilled, LikeOutlined, DislikeFilled, DislikeOutlined, LikeTwoTone, StarOutlined } from '@ant-design/icons-vue';
    import { defineComponent, ref, onMounted } from 'vue';
    import relativeTime from 'dayjs/plugin/relativeTime';
    import { CommentOutlined } from '@ant-design/icons-vue';
    import axios from "axios";
    import store from '@/store';
    import {message} from "ant-design-vue";
    import {useRouter}  from "vue-router";
    dayjs.extend(relativeTime);
    export default defineComponent({
        name: "TheCard",
        components:{
            LikeFilled,
            LikeOutlined,
            DislikeFilled,
            DislikeOutlined,
            LikeTwoTone,
            StarOutlined,
            CommentOutlined,
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
                        message.error(data.message);
                    }
                })
            };

            // 发表评论
            const handleSubmitComment = () => {
                // 判空
                if (!content.value) {
                    message.warn("请勿提交空内容");
                    return;
                }
                // 判断是否登录
                if(!comment.commentatorId){
                    message.warn("请登录后再评论博客")
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
            // 点赞点踩请求参数
            const likeReq = {
                commentatorId: store.state.user.id,
                blogId: props.blogList.id,
                isLike: true
            };

            // 点赞点踩状态
            const action = ref<string>('none');

            const like = () => {
                // 判断是否登录
                if(!likeReq.commentatorId){
                    message.warn("请登录后再点赞")
                    return;
                }
                likeReq.isLike = true;
                axios.post("/likes/save", likeReq).then((response) => {
                    const data = response.data;
                    if(data.success){
                        message.success("点赞成功");
                        action.value = 'liked';
                        props.blogList.voteNum ++;
                        blogsLike();
                    } else {
                        message.error(data.message);
                    }
                });
            };

            const dislike = () => {
                if(!likeReq.commentatorId){
                    message.warn("请登录后再点踩")
                    return;
                }
                likeReq.isLike = false;
                axios.post("/likes/save", likeReq).then((response) => {
                    const data = response.data;
                    if(data.success){
                        message.success("点踩成功");
                        action.value = 'disliked';
                        props.blogList.opposeNum ++;
                    } else {
                        message.error(data.message);
                    }
                });
            };

            // 登录者是否点赞
            const isMyLike = () => {
                if(!store.state.user.id){
                    return;
                }
                for (let item of store.state.likes){
                    if (item.blogId === props.blogList.id && item.isLike){
                        action.value = 'liked';
                        break;
                    }else if (item.blogId === props.blogList.id && !item.isLike){
                        action.value = 'disliked';
                        break;
                    }else {
                        action.value = 'none';
                    }
                }
            };

            // 显示该博客有谁点过赞
            const likesName = ref([{

            }]);
            const blogsLike = () => {
                axios.get("/likes/listByBlog/" + props.blogList.id).then((response) => {
                    likesName.value = [{}];
                    const data = response.data;
                    if (data.success){
                        if(data.content.length !== 0){
                            for(let item of data.content){
                                likesName.value.push({
                                    likeName:item.name,
                                })
                            }
                            console.log("点赞名单：",likesName.value);
                        }
                    }else {
                        message.error(data.message);
                    }

                })
            };

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
             * 收藏博客
             */
            const isCollect = ref(0);

            // 确定收藏状态
            const isCollected = () =>{
                axios.get('/collect/collectBlogList/'+ store.state.user.id).then((response) => {
                    const data = response.data;
                    console.log("用户收藏名单：", data);
                    for (let item of data.content){
                        if (item.id === props.blogList.id){
                            isCollect.value = 1;
                            break;
                        }else {
                            isCollect.value = 0;
                        }
                    }
                })
            };

            // 收藏
            const collectBlog = () => {
                const collectSaveReq = {
                    collectorId: store.state.user.id,
                    blogId: props.blogList.id
                };
                axios.post('/collect/save', collectSaveReq).then((response) =>{
                    const data = response.data;
                    isCollect.value = 1;
                    if (data.success){
                        message.success("收藏成功！")
                    }else {
                        message.error(data.message);
                    }
                })
            };

            onMounted(() => {
                isMyLike();
                blogsLike();
                isCollected();
            });

            return {
                action,
                like,
                dislike,
                likesName,
                blogsLike,

                dayjs,
                html,
                avatarName,

                comments,
                submitting,
                handleSubmitComment,
                alterCommentShow,
                commentShow,
                content,

                toUserInfo,

                collectBlog,
                isCollect,
            };
        },
    });
</script>

<style scoped>

</style>