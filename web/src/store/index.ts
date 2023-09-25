import { createStore } from 'vuex'

declare let SessionStorage: any;
const USER = "USER";
const LIKES = "LIKES";

export default createStore({
  state: {
    user: SessionStorage.get(USER) || {},
    likes: SessionStorage.get(LIKES) || {}
  },
  getters: {
  },
  mutations: {
    setUser (state, user) {
      console.log("store user：", user);
      state.user = user;
      // 登录成功后将登录信息放入缓存中
      SessionStorage.set(USER, user);
    },
    setLikes (state, likes) {
      console.log("store likes：", likes);
      state.likes = likes;
      SessionStorage.set(LIKES, likes);
    }
  },
  actions: {
  },
  modules: {
  }
})