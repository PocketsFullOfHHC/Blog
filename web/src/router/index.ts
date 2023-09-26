import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import AdminMyself from '../views/admin/AdminMyself.vue';
import AdminPublish from '../views/admin/AdminPublish.vue';
import AdminFind from '../views/admin/AdminFind.vue';
import AdminUserInfo from '../views/admin/AdminUserInfo.vue'
import AdminUserHomePage from '../views/admin/AdminUserHomePage.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/myself',
    name: 'myself',
    component: AdminMyself
  },
  {
    path: '/find',
    name: 'find',
    component: AdminFind
  },
  {
    path: '/publish',
    name: 'publish',
    component: AdminPublish
  },
  {
    path: '/info',
    name: 'info',
    component: AdminUserInfo
  },
  {
    path: '/userHomePage',
    name: 'userHomePage',
    component: AdminUserHomePage
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

export default router
