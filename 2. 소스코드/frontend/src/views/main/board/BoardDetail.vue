<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mb-3"></b-row>
    <b-row>
      <b-col cols="12" class="text-center">
        <h3 class="font-weight-bold">{{ article.subject }}</h3>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="1"></b-col>
      <b-col cols="4"
        >작성자 |
        <span style="color: rgba(0, 0, 0, 0.8)"
          ><strong>{{ article.username }}</strong></span
        >
      </b-col>
      <b-col cols="4"></b-col>
      <b-col cols="2" class="text-right"
        >방문 | {{ article.visit_count }}</b-col
      >
      <b-col cols="1"></b-col>
    </b-row>
    <b-row>
      <b-col cols="1"></b-col>
      <b-col cols="10">
        <div class="rounded-lg pa-10 border">{{ article.content }}</div>
      </b-col>
      <b-col cols="1"></b-col>
    </b-row>
    <b-row v-if="article.userid == userInfo.userid">
      <b-col cols="1"></b-col>
      <b-col cols="1">
        <router-link
          :to="{
            name: 'boardModify',
            params: { articleno: article.articleno },
          }"
        >
          <v-btn color="yellow" white top right fab>
            <v-icon>mdi-pencil-remove-outline</v-icon>
          </v-btn>
        </router-link>
      </b-col>
      <b-col cols="1"
        ><v-btn
          color="rgba(066,133,244)"
          right
          fab
          dark
          outline
          @click.prevent="goList"
        >
          <v-icon>mdi-form-select</v-icon>
        </v-btn></b-col
      >
      <b-col cols="6"></b-col>
      <b-col cols="1" class="text-right">
        <v-btn color="red" black right fab @click.prevent="deleteArticle">
          <v-icon>mdi-delete-off</v-icon>
        </v-btn>
      </b-col>
      <b-col cols="1"></b-col>
    </b-row>

    <like-item :articleno="articleno"></like-item>
    <reply-list :articleno="articleno"></reply-list>
  </b-container>
</template>

<script>
const likeStore = "likeStore";
import { mapState, mapActions, mapMutations } from "vuex";
import ReplyList from "@/components/header/item/ReplyList.vue";
import LikeItem from "@/components/header/item/LikeItem.vue";

export default {
  name: "BoardDetail",
  data() {
    return {};
  },
  props: ["articleno"],
  components: {
    ReplyList,
    LikeItem,
  },
  computed: {
    ...mapState(["article", "userInfo"]),
  },
  created() {
    this.SET_DETAIL_ARTICLE();
    this.getDetailArticle(this.$route.params.articleno);
    this.setArticleNum(this.$route.params.articleno);
  },
  methods: {
    ...mapActions(["getDetailArticle", "getDeleteArticle"]),
    ...mapActions(likeStore, ["setArticleNum"]),
    ...mapMutations(["SET_DETAIL_ARTICLE"]),
    deleteArticle() {
      if (confirm("정말로 삭제?")) {
        this.getDeleteArticle(this.$route.params.articleno);
      }
    },
    goList() {
      this.$router.push({ name: "board" });
    },
  },
  goList() {
    this.$router.push({ name: "board" });
    this.getArticle(0);
  },
};
</script>
