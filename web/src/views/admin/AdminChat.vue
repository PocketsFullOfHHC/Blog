<template>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
        <a-breadcrumb :style="{ margin: '16px 0' }">
            <a-breadcrumb-item>聊天室</a-breadcrumb-item>
        </a-breadcrumb>
        <div class="bg-info panel panel-default" style="height: 500px">
            <!-- 聊天对象信息 -->
            <div class="panel-heading">
                <a-avatar :src="'http://localhost:8080/picture/avatars/' + friendAvatar" alt="头像"></a-avatar>
                <span style="margin-left: 10px">{{ friendName }}</span>
            </div>
            <!-- 聊天内容区 -->
            <div id="chatWindow" style="height: 300px; overflow-y:scroll">
                <div v-for="( message ) in messageList" :key="message.id" style="margin-left: 30px; margin-right: 30px; margin-top: 30px">
                    <div v-if="message.senderId === userId">
                        <div style="text-align: right">
                            <span class="sentence bg-success" style="margin-right: 10px">{{ message.content }}</span>
                            <a-avatar :src="'http://localhost:8080/picture/avatars/' + userAvatar" alt="头像"/>
                        </div>
                    </div>
                    <div v-else>
                        <div style="text-align: left">
                            <a-avatar :src="'http://localhost:8080/picture/avatars/' + friendAvatar" alt="头像"/>
                            <span class="sentence bg-primary" style="margin-left: 10px">{{ message.content }}</span>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 输入框 -->
            <div style="margin-top: 50px">
                <a-input-group compact>
                    <a-input v-model:value="content" style="width: calc(100% - 130px)" placeholder="请输入..."/>
                    <a-button type="primary" @click="sendMessage" >发送</a-button>
                    <a-button type="primary" danger @click="clearContent" style="float: right">清空</a-button>
                </a-input-group>
            </div>
        </div>
    </a-layout-content>
</template>

<script>
    import { ref, onMounted, onBeforeUnmount, getCurrentInstance } from 'vue';
    import store from '@/store';
    import axios from 'axios';
    import dayjs from 'dayjs';
    import { message } from "ant-design-vue";
    export default {
        setup(){
            // 基础数据
            let { proxy } = getCurrentInstance();
            let friendName = ref();
            friendName.value = proxy.$route.query.friendName;
            let friendAvatar = ref();
            friendAvatar.value = proxy.$route.query.friendAvatar;
            const userName = ref(store.state.user.name);
            const userId = ref(store.state.user.id);
            const userAvatar = ref(store.state.user.avatar);

            // websocket相关数据
            let ws;
            const url = process.env.VUE_APP_WS_SERVER + '/ws/chat/';
            let messageList = ref([]);
            let content = ref();

            /**
             * 加入聊天室
             * */
            const add = ()=> {
                console.log("add函数被执行");
                // 创建WebSocket连接
                ws = new WebSocket(url + store.state.user.id + '/' + proxy.$route.query.friendId);
                console.log("webSocket的地址为：", url + store.state.user.id + '/' + proxy.$route.query.friendId);
                // 建立连接成功回调
                ws.onopen = () =>{
                    console.log("websocket服务器连接成功");
                };
                // 客户端收到服务器端消息时回调
                // 这里是收到的服务端发回的信息：message为事件对象，POJO类websocket.java里面sendText的参数：chatId以及message
                ws.onmessage = (message) => {
                    const messageInfo = {
                        senderId: '',
                        receiverId: '',
                        content: '',
                        time: '',
                    };
                    messageInfo.senderId = message.data.split(':')[0];
                    messageInfo.receiverId = message.data.split(':')[1];
                    messageInfo.content = message.data.split(':')[2];
                    messageInfo.time = dayjs().format('YYYY-MM-DD HH:mm:ss');
                    messageList.value.push(messageInfo);
                    console.log("发送的消息：",messageInfo);
                    console.log("新的消息记录：",messageList);
                };
            };

            /**
             * 获取历史聊天记录
             * */
            const getMessageList = () => {
                axios.get('/message/getMessage/' + store.state.user.id + '/' + proxy.$route.query.friendId).then((response)=>{
                    const data = response.data;
                    if (data.success){
                        messageList.value = data.content;
                    }
                })
            };

            /**
             * 发消息
             * */
            const sendMessage = ()=> {
                if(content.value === "" || content.value === "undefined"){
                    message.warn("发送内容不可为空");
                    return;
                }
                ws.send(content.value);
                const messageItem = {
                    senderId: store.state.user.id,
                    receiverId: proxy.$route.query.friendId,
                    content: content.value,
                };
                let chatWindow = document.getElementById("chatWindow");
                axios.post('/message/save', messageItem).then((response)=>{
                    const data = response.data;
                    if (data.success){
                        message.success("消息发送成功！");
                    }
                    chatWindow.scrollTop = chatWindow.scrollHeight
                });
                content.value = '';
            };

            const clearContent = () => {
                content.value = '';
            };

            /**
             * 关闭连接
             * */
            const close = ()=> {
                ws.close();
            };

            onMounted(() => {
                add();
                getMessageList();
                setTimeout(() => {
                    let chatWindow = document.getElementById("chatWindow");
                    chatWindow.scrollTop = chatWindow.scrollHeight
                }, 100);
            });

            onBeforeUnmount(() => {
                console.log("websocket连接即将关闭");
                ws.close();
            });

            return {
                url,
                ws,
                messageList,
                content,
                clearContent,
                add,
                sendMessage,
                close,

                friendName,
                friendAvatar,
                userId,
                userName,
                userAvatar,
            }
        }
    }
</script>

<style scoped>
    .sentence{
        border-radius: 5px;
        padding-left: 5px;
        padding-right: 5px;
    }
</style>