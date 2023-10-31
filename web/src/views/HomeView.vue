<template>
  <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
    <a-breadcrumb :style="{ margin: '16px 0' }">
      <a-breadcrumb-item>主页</a-breadcrumb-item>
    </a-breadcrumb>
    <a-layout style="padding: 24px 0; background: #fff">
      <the-sider></the-sider>
      <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
        欢迎
        <a-divider></a-divider>
        <div>
          <a-row>
            <a-col :span="24">
              <a-card>
                <a-row>
                  <a-col :span="8">
                    <a-statistic title="总博客量" :value="statistic.blogNum">
                      <template #suffix>
                        <like-outlined />
                      </template>
                    </a-statistic>
                  </a-col>
                  <a-col :span="8">
                    <a-statistic title="总点赞量" :value="statistic.voteNum">
                      <template #suffix>
                        <like-outlined />
                      </template>
                    </a-statistic>
                  </a-col>
                  <a-col :span="8">
                    <a-statistic title="总评论量" :value="statistic.commentNum">
                      <template #suffix>
                        <UserOutlined />
                      </template>
                    </a-statistic>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
          </a-row>
          <br>
          <a-row :gutter="16">
            <a-col :span="12">
              <a-card>
                <a-row>
                  <a-col :span="12">
                    <a-statistic title="今日点赞" :value="statistic.todayVoteNum">
                      <template #suffix>
                        <like-outlined />
                      </template>
                    </a-statistic>
                  </a-col>
                  <a-col :span="12">
                    <a-statistic title="今日评论" :value="statistic.todayCommentNum" style="margin-right: 50px">
                      <template #suffix>
                        <UserOutlined />
                      </template>
                    </a-statistic>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
            <a-col :span="12">
              <a-card>
                <a-row>
                  <a-col :span="12">
                    <a-statistic
                            title="预计今日点赞"
                            :value="statistic.todayVoteIncrease"
                            :value-style="{ color: '#0000ff' }"
                    >
                      <template #suffix>
                        <UserOutlined />
                      </template>
                    </a-statistic>
                  </a-col>
                  <a-col :span="12">
                    <a-statistic
                            title="预计今日点赞增长"
                            :value="statistic.todayVoteIncreaseRateAbs"
                            :precision="2"
                            suffix="%"
                            class="demo-class"
                            :value-style="statistic.todayVoteIncreaseRate < 0 ? { color: '#3f8600' } : { color: '#cf1322' }"
                    >
                      <template #prefix>
                        <arrow-down-outlined v-if="statistic.todayVoteIncreaseRate < 0"/>
                        <arrow-up-outlined v-if="statistic.todayVoteIncreaseRate >= 0"/>
                      </template>
                    </a-statistic>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
          </a-row>
        </div>
      </a-layout-content>
    </a-layout>
  </a-layout-content>
</template>

<script lang="ts">
  import { defineComponent, onMounted, ref } from 'vue';
  import TheSider from '@/components/TheSider.vue';
  import axios from 'axios';
  export default defineComponent({
    name:'homeVote',
    components: {
      TheSider,
    },
    setup(){
      const statistic = ref();
      statistic.value = {};
      const getStatistic = () => {
        // 接口返回的是数组：[昨天的数据，今天的数据]
        axios.get('/blog-snapshot/get-statistic').then((response) => {
          const data = response.data;
          if (data.success) {
            const statisticResp = data.content;
            console.log("返回的数据", statisticResp);
            statistic.value.voteNum = statisticResp[1].voteNum;
            statistic.value.commentNum = statisticResp[1].commentNum;
            statistic.value.todayVoteNum = statisticResp[1].voteIncrease;
            statistic.value.todayCommentNum = statisticResp[1].commentIncrease;
            axios.get('/blog/BlogNum').then((response) => {
              const data = response.data;
              if(data.success){
                statistic.value.blogNum = data.content;
              }
            });

            // 按分钟计算当前时间点，占一天的百分比
            const now = new Date();
            const nowRate = (now.getHours() * 60 + now.getMinutes()) / (60 * 24);
            console.log(nowRate);
            statistic.value.todayVoteIncrease = parseInt(String(statisticResp[1].voteIncrease / nowRate));
            // todayVoteIncreaseRate：今日预计增长率
            statistic.value.todayVoteIncreaseRate = (statistic.value.todayVoteIncrease - statisticResp[0].voteIncrease) / statisticResp[0].voteIncrease * 100;
            statistic.value.todayVoteIncreaseRateAbs = Math.abs(statistic.value.todayVoteIncreaseRate);
          }
        });
      };
      onMounted(() =>{
        getStatistic();
      });
      return{
        statistic,
      }
    },
  })
</script>

<style scoped>

</style>