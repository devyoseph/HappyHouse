import http from "@/api/http";
import Vue from "vue";
import Vuex from "vuex";
Vue.use(Vuex);
const likeStore = {
  namespaced: true,
  state: {
    scores: [""],
    myscore: [],
    res: [],
  },
  getters: {
    GetMyScore: function (state) {
      return state.myscore;
    },
    GetAllScores: function (state) {
      return state.scores;
    },
  },
  mutations: {
    SET_MY_SCORE(state, score) {
      state.myscore = score;
    },
    SET_ALL_SCORES(state, scores) {
      state.scores = scores;
    },
    SET_RES(state, res) {
      state.res = res;
    },
  },
  actions: {
    getMyScore({ commit }, params) {
      console.log("나의 좋아요 조회", params);
      http
        .get(`/board/like/my`, JSON.stringify(params))
        .then(({ data }) => {
          commit("SET_ALL_SCORES", data.score);
          console.log("나의 좋아요 결과 :", data.score);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getAllScores({ commit }, articleno) {
      console.log("전체 좋아요 조회", articleno);
      http
        .get(`/board/like/${articleno}`)
        .then(({ data }) => {
          commit("SET_ALL_SCORES", data);
          console.log("전체 좋아요 데이터: ", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    addScore({ commit, dispatch }, params) {
      http
        .post(`/board/like/my`, JSON.stringify(params))
        .then(({ data }) => {
          commit("SET_MY_SCORE", data);
          dispatch("getAllScores", params.articleno);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // deleteMyScore({ commit, dispatch }, params) {
    //   http
    //     .delete(`/board/my`, JSON.stringify(params))
    //     .then(({ data }) => {
    //       dispatch("getMyScore", params);
    //       console.log(data);
    //     })
    //     .catch((error) => {
    //       console.log(error);
    //     });
    // },
  },
};

export default likeStore;
