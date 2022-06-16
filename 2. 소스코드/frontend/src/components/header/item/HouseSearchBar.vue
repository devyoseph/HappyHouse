<template>
  <b-row class="mt-4 mb-4 text-center">
    <b-col class="sm-3">
      <b-form-select v-model="sidoCode" :options="sidos" @change="gugunList">
        <!--change : 셀렉트 값 변경될 때-->
      </b-form-select>
    </b-col>
    <b-col class="sm-3">
      <b-form-select
        v-model="gugunCode"
        :options="guguns"
        @change="dodongList"
      ></b-form-select>
    </b-col>
    <b-col class="sm-3">
      <b-form-select
        v-model="dodongCode"
        :options="dodongs"
        @change="searchApt"
      ></b-form-select>
    </b-col>
  </b-row>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
const houseStore = "houseStore";
export default {
  name: "HomeMainView",
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dodongCode: null,
    };
  },
  computed: {
    //고를때마다 상태 바뀌므로
    ...mapState(houseStore, ["sidos", "guguns", "houses", "dodongs"]),
  },
  created() {
    //처음에 시도리스트를 클리어해야 선택
    this.CLEAR_SIDO_LIST();
    //시도 가져오기
    this.getSido();
    this.getHouseList("1120011100");
  },
  methods: {
    ...mapActions(houseStore, [
      "getSido",
      "getGugun",
      "getDodong",
      "getHouseList",
    ]),
    ...mapMutations(houseStore, ["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST"]),

    gugunList() {
      //시도코드 변경되면 -> 구군리스트 가져와야함
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      this.dodongCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    dodongList() {
      //구군코드 변경되면 -> 도동리스트 가져와야함
      if (this.gugunCode) this.getDodong(this.gugunCode);
    },
    searchApt() {
      if (this.dodongCode) this.getHouseList(this.dodongCode);
    },
  },
};
</script>

<style></style>
