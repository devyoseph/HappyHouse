<template>
  <b-container>
    <b-row>
      <b-col cols="12" class="text-center">
        <h3 class="font-weight-bold text-center">글수정</h3>
        <span style="color: blue; font-weight: bolder">{{
          article.username
        }}</span>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="1"></b-col>
      <b-col cols="10" class="text-center">
        <b-input
          v-model="article.subject"
          placeholder="제목을 입력하세요."
          size="lg"
        ></b-input>
      </b-col>
      <b-col cols="1"></b-col>
    </b-row>

    <b-row class="md-5">
      <b-col cols="1"></b-col>
      <b-col cols="10"
        ><b-form-textarea size="sm" v-model="article.content"> </b-form-textarea
      ></b-col>
      <b-col cols="1"></b-col>
    </b-row>

    <b-row>
      <b-col cols="7"></b-col>
      <b-col cols="4" class="text-right">
        <v-btn
          color="green"
          dark
          right
          fab
          outline
          @click.prevent="ModifyArticle"
        >
          <v-icon>mdi-check-outline</v-icon>
        </v-btn>
      </b-col>
    </b-row>

    <like-item :articleno="articleno"></like-item>
    <reply-list :articleno="articleno"></reply-list>
  </b-container>
</template>

<script>
import { mapState, mapActions, mapMutations, mapGetters } from "vuex";
export default {
  name: "BoardModify",
  data() {
    return {
      aftermodifyarticle: {
        username: "",
        subject: "",
        content: "",
      },
    };
  },
  components: {},
  computed: {
    ...mapState(["article", "articleno"]),
    ...mapGetters(["GetArticle"]),
  },
  created() {
    this.SET_DETAIL_ARTICLE();
    this.getDetailArticle(this.$route.params.articleno);
  },
  mounted() {
    this.aftermodifyarticle = this.GetArticle;
    console.log(this.aftermodifyarticle);
  },
  methods: {
    ...mapActions(["getDetailArticle", "getModifyArticle"]),
    ...mapMutations(["SET_DETAIL_ARTICLE"]),
    ModifyArticle() {
      if (!this.article.subject) {
        alert("제목을 입력하세요");
      } else if (!this.article.content) {
        alert("내용을 입력하세요");
      } else if (!this.article.username || !this.article.userid) {
        alert("로그인을 해주세요");
      } else {
        this.getModifyArticle(this.article);
      }
    },
    goList() {
      this.$router.push({ name: "board" });
    },
  },
};
</script>
