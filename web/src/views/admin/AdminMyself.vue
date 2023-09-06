<template>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
        <a-breadcrumb :style="{ margin: '16px 0' }">
            <a-breadcrumb-item>Home</a-breadcrumb-item>
        </a-breadcrumb>
        <a-layout style="padding: 24px 0; background: #fff">
            <the-sider></the-sider>
            <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
                <the-card></the-card>
            </a-layout-content>
        </a-layout>
    </a-layout-content>
</template>

<script lang="ts">
    import TheCard from "@/components/TheCard.vue";
    import TheSider from '@/components/TheSider.vue';
    import {defineComponent} from 'vue';
    export default defineComponent({
        name: "AdminMyself",
        components: {
            TheCard,
            TheSider,
        },
    });
</script>

<style scoped>

</style>