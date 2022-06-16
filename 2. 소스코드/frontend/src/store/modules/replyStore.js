import http from "@/api/http";

const replyStore = {
  namespaced: true,
  state: {
    replies: [""],
    reply: [""],
  },
  getters: {
    getReplies(state) {
      return state.replies;
    },
  },
  mutations: {
    SET_REPLIES: (state, replies) => {
      state.replies = replies;
    },
    SET_REPLY: (state, replie) => {
      state.replie = replie;
    },
  },
  actions: {
    updateReplies({ commit }, articleno) {
      console.log("업데이트", articleno);
      http
        .get(`board/reply/${articleno}`)
        .then((response) => {
          commit("SET_REPLIES", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    registerReply({ commit, dispatch }, params) {
      console.log("등록하기 전", params, JSON.stringify(params));
      http
        .post(`board/reply/register`, JSON.stringify(params))
        .then((response) => {
          console.log("등록성공!!", typeof params.articleno);
          commit("SET_REPLY", response.data);
          dispatch("updateReplies", params.articleno);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    deleteReply({ commit, dispatch }, reply) {
      console.log("삭제 전", reply.articleno, typeof reply.articleno);
      http
        .delete(`board/reply/` + reply.id)
        .then((response) => {
          commit("SET_REPLIES", response.data);
          dispatch("updateReplies", reply.articleno);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};

export default replyStore;
