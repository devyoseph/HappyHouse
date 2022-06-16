<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mb-1">
      <b-col cols="12" style="text-align: left">
        <b-form>
          <b-form-group
            id="subject-group"
            label="제목:"
            label-for="subject"
            description="제목을 입력하세요."
          >
            <b-form-input
              placeholder="제목 입력..."
              v-model="article.subject"
            ></b-form-input>
          </b-form-group>

          <b-form-group id="content-group" label="내용:" label-for="content">
            <b-form-textarea
              id="content"
              placeholder="내용 입력..."
              rows="10"
              max-rows="15"
              v-model="article.content"
            ></b-form-textarea>
          </b-form-group>
        </b-form>
      </b-col>
    </b-row>

    <b-row>
      <b-col cols="1"></b-col>

      <b-col cols="1">
        <v-btn color="green" dark top right fab @click.prevent="doRegister">
          <v-icon>mdi-plus</v-icon>
        </v-btn>
      </b-col>

      <b-col cols="7"></b-col>

      <b-col cols="2">
        <v-btn
          color="rgba(0, 0, 0,0.7)"
          dark
          top
          right
          fab
          outline
          @click.prevent="goList"
        >
          <v-icon>mdi-arrow-u-left-top-bold</v-icon>
        </v-btn>
      </b-col>

      <b-col cols="1"></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "BoardInputItem",
  data() {
    return {
      article: {
        subject: "",
        content: "",
      },
    };
  },
  computed: {},

  methods: {
    ...mapActions(["getArticleRegister"]),
    goList() {
      this.$router.push({ name: "board" });
    },
    doRegister() {
      if (!this.article.subject) {
        alert("제목을 입력하세요");
      } else if (!this.article.content) {
        alert("내용을 입력하세요");
      } else this.getArticleRegister(this.article);
    },
  },
};
</script>

<style></style>
