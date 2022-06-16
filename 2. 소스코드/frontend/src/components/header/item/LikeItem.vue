<template>
  <v-form class="d-flex justify-content-center">
    <v-btn
      @click="plusMyScore"
      class="ma-2 mr-5"
      text
      icon
      x-large
      color="blue lighten-2"
    >
      <v-icon class="mr-3">mdi-thumb-up</v-icon>
      {{ scores.plus }}
    </v-btn>
    <v-btn
      @click="minusMyScore"
      class="ma-2 ml-3"
      text
      icon
      x-large
      color="red lighten-2"
      >{{ scores.minus }} <v-icon class="ml-5">mdi-thumb-down</v-icon>
    </v-btn>
  </v-form>
</template>

<script>
import { mapGetters, mapActions, mapState } from "vuex";
const likeStore = "likeStore";

export default {
  name: "LikeItem",
  props: {
    articleno: {
      type: String,
    },
  },
  computed: {
    ...mapState(likeStore, ["scores", "score"]),
    ...mapState(["userInfo"]),
    ...mapGetters(likeStore, ["GetMyScore", "GetAllScores"]),
  },
  methods: {
    ...mapActions(likeStore, ["getMyScore", "getAllScores", "addScore"]),
    plusMyScore() {
      let params = this.makeParams(1);
      this.addScore(params);
    },
    minusMyScore() {
      let params = this.makeParams(-1);
      this.addScore(params);
    },
    makeParams(now_score) {
      let params = {
        articleno: this.articleno,
        userid: this.userInfo.userid,
        username: this.userInfo.username,
        score: now_score,
      };
      return params;
    },
  },
  created() {
    console.log("좋아요 로드");
    this.getAllScores(this.articleno);
    this.getMyScore(this.makeParams(0));
  },
};
</script>

<style>
.checked {
  color: red;
  font-weight: bolder;
}
</style>
