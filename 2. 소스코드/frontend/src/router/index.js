import Vue from "vue";
import VueRouter from "vue-router";
import LoginView from "@/views/LoginView.vue";
import HomeView from "@/views/HomeView.vue";
import HomeMainView from "@/views/main/HomeMainView.vue";
import BoardView from "@/views/main/BoardView.vue";
import SearchView from "@/views/main/SearchView.vue";
import MymenuView from "@/views/main/MymenuView.vue";
import MyLikeView from "@/views/main/MyLikeView.vue";

import BoardList from "@/views/main/board/BoardList.vue";
import BoardRegister from "@/views/main/board/BoardRegister.vue";
import BoardDetail from "@/views/main/board/BoardDetail.vue";
import BoardModify from "@/views/main/board/BoardModify.vue";

import RegisterView from "@/views/main/login/RegisterView.vue";
import NaverLoadingView from "@/views/main/login/NaverLoadingView.vue";
import NaverLogin from "@/views/main/login/NaverLogin.vue";
import { isAuth } from "@/api/isAuth.js";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "login",
    component: LoginView,
    redirect: "/naver",
    children: [
      {
        path: "naver",
        name: "naver",
        component: NaverLogin,
      },
      {
        path: "naverloading",
        name: "naverloading",
        component: NaverLoadingView,
      },
      {
        path: "register",
        name: "register",
        component: RegisterView,
        props: true,
      },
    ],
  },
  {
    path: "/home",
    name: "home",
    component: HomeView,
    redirect: "/home/main",
    meta: { authRequired: true },
    children: [
      {
        path: "main",
        name: "main",
        component: HomeMainView,
      },
      {
        path: "board",
        name: "board",
        component: BoardView,
        redirect: "board/list",
        children: [
          {
            path: "list",
            name: "boardlist",
            component: BoardList,
          },
          {
            path: "register",
            name: "boardregister",
            component: BoardRegister,
          },
          {
            path: "detail/:articleno",
            name: "boardDetail",
            component: BoardDetail,
            props: true,
          },
          {
            path: "modify/:articleno",
            name: "boardModify",
            component: BoardModify,
          },
        ],
      },
      {
        path: "search",
        name: "search",
        component: SearchView,
        meta: { authRequired: true },
      },
      {
        path: "mymenu",
        name: "mymenu",
        component: MymenuView,
        meta: { authRequired: true },
      },
      {
        path: "mylike",
        name: "mylike",
        component: MyLikeView,
        meta: { authRequired: true },
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach(function (to, from, next) {
  console.log("라우팅 대기 메서드");

  if (
    to.matched.some(function (routeInfo) {
      return routeInfo.meta.authRequired;
    })
  ) {
    //true
    console.log("인증 필요 페이지 진입");
    if (
      isAuth(
        sessionStorage.getItem("access_token"),
        (response) => {
          console.log("인증 성공!!", response);
          return true;
        },
        (error) => {
          console.log("인증 실패", error);
          return false;
          // eslint-disable-next-line
        }
      )
    ) {
      //토큰인증 완료: 이 부분을 써주지 않으면 next()가 실행안됨
      console.log("토큰 인증 완료??");
      next();
    } else {
      console.log("토큰 인증 실패~");
    }
  } else {
    //false
    console.log("인증 불필요 페이지");
    next(); // 페이지 전환
  }
});
export default router;
