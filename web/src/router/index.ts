import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import AdminMyself from '../views/admin/AdminMyself.vue';
import AdminPublish from '../views/admin/AdminPublish.vue';
import AdminFind from '../views/admin/AdminFind.vue';
import AdminUserInfo from '../views/admin/AdminUserInfo.vue';
import AdminUserHomePage from '../views/admin/AdminUserHomePage.vue';
import AdminFriend from '../views/admin/AdminFriend.vue';
import AdminFollow from '../views/admin/AdminFollow.vue';
import AdminBlogEdit from '../views/admin/AdminBlogEdit.vue';
import AdminChat from '../views/admin/AdminChat.vue';
import AdminCollect from '../views/admin/AdminCollect.vue';
import AdminCircle from '../views/admin/AdminCircle.vue';
import AdminCircleHomePage from '../views/admin/AdminCircleHomePage.vue';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/edit',
    name: 'edit',
    component: AdminBlogEdit
  },
  {
    path: '/follow',
    name: 'follow',
    component: AdminFollow
  },
  {
    path: '/myself',
    name: 'myself',
    component: AdminMyself
  },
  {
    path: '/friend',
    name: 'friend',
    component: AdminFriend
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
    path: '/collect',
    name: 'collect',
    component: AdminCollect
  },
  {
    path: '/circle',
    name: 'circle',
    component: AdminCircle
  },
  {
    path: '/circleHomePage',
    name: 'circleHomePage',
    component: AdminCircleHomePage
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
    path: '/chat',
    name: 'chat',
    component: AdminChat
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
