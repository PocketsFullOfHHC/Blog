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
          <a-divider></a-divider>
          <a-row>
            <a-col :span="24">
              <div id="main" style="width: 100%;height:300px;"></div>
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
  declare let echarts: any;
  export default defineComponent({
    name:'homeView',
    components: {
      TheSider,
    },
    setup(){

      /**
       * 展示统计数据
       * */
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

      /**
       * 展示趋势图
       * */
      const testEcharts = () => {
        // 基于准备好的dom，初始化echarts实例
        const myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        const option = {
          title: {
            text: 'ECharts 入门示例'
          },
          tooltip: {},
          legend: {
            data:['销量']
          },
          xAxis: {
            data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
          },
          yAxis: {},
          series: [{
            name: '销量',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20]
          }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
      };

      onMounted(() =>{
        getStatistic();
        testEcharts();
      });

      return{
        statistic,
      }
    },
  })
</script>

<style scoped>

</style>