import http from "@/api/http";
import Vue from "vue";
import Vuex from "vuex";
import router from "@/router";

Vue.use(Vuex);

import replyStore from "@/store/modules/replyStore.js";
import houseStore from "@/store/modules/houseStore.js";
import likeStore from "@/store/modules/likeStore.js";

export default new Vuex.Store({
  modules: {
    replyStore,
    houseStore,
    likeStore,
  },
  state: {
    userInfo: [""],
    articles: [""], //게시글 목록
    article: {},
    register: true,
    pageno: 0,
  },
  getters: {
    GetArticle: function (state) {
      return state.article;
    },
    GetUserInfo: function (state) {
      return state.userInfo;
    },
    GetPageNo: function (state) {
      return state.pageno;
    },
  },
  mutations: {
    SET_USER_INFO(state, userInfo) {
      state.userInfo = userInfo;
    },
    SET_ARTICLE_LIST(state, articles) {
      state.articles = articles;
      console.log(state.articles);
    },
    SET_DETAIL_ARTICLE(state, article) {
      state.article = article;
      console.log(state.article);
    },
    SET_DELETE_ARTICLE(state, article) {
      console.log(state, article);
    },
    SET_ARTICLE_REGISTER(state, article) {
      console.log("아래 아티클");
      state.articles.push(article);
      console.log(state.article);
      console.log(state, article);
    },
    SET_PAGE(state, pageno) {
      state.pageno = pageno;
      console.log("pageno를 수정", state.pageno);
    },
    SET_VISIT_COUNT(state, articleno) {
      state.article.visit_count++;
      console.log("pageno를 수정", state, articleno);
    },
  },
  actions: {
    getUserInfo({ commit }, access_token) {
      console.log("액세스 토큰: ", access_token);
      http
        .get(`/user/` + access_token)
        .then(({ data }) => {
          console.log("자료", data.userInfo.email);
          commit("SET_USER_INFO", data.userInfo);
          console.log("유저정보", data.userInfo);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getArticle({ commit }, pageno) {
      http
        .get(`/board/` + pageno)
        .then(({ data }) => {
          commit("SET_ARTICLE_LIST", data);
          commit("SET_PAGE", pageno);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getDetailArticle({ commit }, articleno) {
      http
        .get(`/board/detail/` + articleno)
        .then(({ data }) => {
          console.log(data);
          commit("SET_DETAIL_ARTICLE", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getDeleteArticle({ commit }, params) {
      console.log("아티클넘버");
      console.log(params);
      http
        .delete(`/board/` + params)
        .then(({ data }) => {
          console.log(data);
          commit("SET_DELETE_ARTICLE", data);
        })
        .then(() => {
          console.log("이동하자");
          router.push({ name: "board" });
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getModifyArticle({ commit }, article) {
      let tmp = {
        articleno: article.articleno,
        content: article.content,
        subject: article.subject,
      };
      // commit("SET_DETAIL_ARTICLE", article.getters.GetArticle);
      http
        .put(`/board/`, JSON.stringify(tmp))
        .then(({ data }) => {
          console.log(data);
        })
        .then(() => {
          console.log("이동하자");
          commit("SET_DETAIL_ARTICLE", article);
          router.push({ name: "board" });
        })
        .catch((error) => {
          console.log(error);
        });
    },

    getArticleRegister({ commit }, params) {
      let tmp = {
        userid: this.state.userInfo.userid,
        username: this.state.userInfo.username,
        content: params.content,
        subject: params.subject,
      };

      console.log(commit, tmp);
      http
        .post(`/board/`, JSON.stringify(tmp))
        .then(({ data }) => {
          console.log(data);
          console.log("포스트성공");
          // console.log(commit);
          // commit("SET_ARTICLE_REGISTER", JSON.stringify(tmp));
        })
        .then(() => {
          console.log("이동하자");
          router.push({ name: "board" });
        })
        .catch((error) => {
          console.log("에러 ", error);
        });
    },

    searchByContent({ commit }, params) {
      console.log("검색 시작", params);
      http
        .post(`/board/search`, JSON.stringify(params))
        .then(({ data }) => {
          console.log("검색 완료 후 데이터", data);
          commit("SET_ARTICLE_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    updateVisitCount({ commit }, articleno) {
      http
        .put(`/board/visitcount/` + articleno)
        .then(({ data }) => {
          commit("SET_VISIT_COUNT", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
});
