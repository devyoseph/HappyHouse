import http from "@/api/http";
import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

const houseStore = {
  namespaced: true,
  state: {
    sidos: [{ value: null, text: "선택하세요" }],
    guguns: [{ value: null, text: "선택하세요" }],
    dodongs: [{ value: null, text: "선택하세요" }],
    houses: [{ dongName: null, text: "선택하세요" }],
    markers: [],
    house: null,
  },
  getters: {},
  mutations: {
    CLEAR_SIDO_LIST: (state) => {
      state.sidos = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_GUGUN_LIST: (state) => {
      state.guguns = [{ value: null, text: "선택하세요" }];
    },
    SET_SIDO_LIST: (state, sidos) => {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST: (state, guguns) => {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DODONG_LIST: (state, dodongs) => {
      dodongs.forEach((dodong) => {
        state.dodongs.push({ value: dodong.dongCode, text: dodong.dongName });
      });
    },
    SET_HOUSE_LIST: (state, houses) => {
      //   console.log(houses);
      state.houses = houses;
      houses.forEach((house) => {
        state.markers.push({ lat: house.lat, lng: house.lng });
      });
    },
  },
  actions: {
    //db에서 시도코드 가져오는 코드
    getSido({ commit }) {
      console.log("겟시도 확인");
      http
        .get(`map/sido`)
        .then(({ data }) => {
          console.log("시도 가져오기 성공");
          console.log(commit, data);
          commit("SET_SIDO_LIST", data);
        })
        .catch((error) => {
          console.log("시도 가져오기실패");
          console.log(error);
        });
    },

    //시도코드를 바탕으로 구군코드 가져오는 코드
    getGugun({ commit }, sidoCode) {
      const params = {
        sido: sidoCode,
      };
      http
        .get(`map/gugun`, { params: params })
        .then(({ data }) => {
          commit("SET_GUGUN_LIST", data);
          console.log(data);
        })
        .catch((error) => {
          console.log(error);
        });
    },

    //구군코드를 바탕으로 도동코드를 가져오는 코드

    getDodong({ commit }, gugunCode) {
      const params = {
        gugun: gugunCode,
      };

      http
        .get(`/map/dong`, { params: params })
        .then(({ data }) => {
          commit("SET_DODONG_LIST", data);
          console.log(data);
        })
        .catch((error) => {
          console.log(error);
        });
    },

    getHouseList({ commit }, dodongCode) {
      const params = {
        dong: dodongCode,
      };
      console.log(params);
      http
        .get(`/map/apt`, { params: params })
        .then(({ data }) => {
          commit("SET_HOUSE_LIST", data);
          console.log(data);
        })
        .catch((error) => {
          console.log(error);
        });
    },

    detailHouse({ commit }, house) {
      commit("SET_DETAIL_HOUSE", house);
    },
  },
};

export default houseStore;
