<template>
  <b-container class="bv-example-row mt-3">
    <reply-item v-for="(reply, index) in replies" :key="index" :reply="reply" />
    <reply-bar :articleno="articleno"></reply-bar>
  </b-container>
</template>

<script>
import { mapActions, mapState } from "vuex";
const replyStore = "replyStore";
import ReplyItem from "@/components/header/item/ReplyItem.vue";
import ReplyBar from "@/components/header/item/ReplyBar.vue";

export default {
  name: "ReplyList",
  components: {
    ReplyItem,
    ReplyBar,
  },
  props: ["articleno"],
  computed: {
    ...mapState(replyStore, ["replies"]),
  },
  methods: {
    ...mapActions(replyStore, ["updateReplies"]),
  },
  created() {
    //댓글 초기화
    this.updateReplies(this.articleno);
  },
};
</script>

<style></style>
