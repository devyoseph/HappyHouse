<template>
  <b-row class="mt-3 mb-0 ml-1">
    <b-col cols="1" class="pa-1"></b-col>
    <b-col cols="2" class="pa-1 mt-2">
      <label
        ><strong style="color: darkslateblue">{{
          userInfo.userid
        }}</strong></label
      >
    </b-col>
    <b-col class="pl-0 pt-1 mt-1 text-left" cols="6">
      <b-input
        v-model="reply_content"
        size="sm"
        type="text"
        placeholder="댓글을 입력하세요."
      />
    </b-col>
    <b-col class="pa-1 text-left mt-1">
      <btn @click="register">
        <v-icon style="color: rgba(052, 168, 083)">mdi-send</v-icon>
      </btn>
    </b-col>
  </b-row>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";
const replyStore = "replyStore";

export default {
  name: "ReplyBar",
  data() {
    return {
      reply_content: "",
    };
  },
  computed: {
    ...mapState(["userInfo"]),
  },
  props: ["articleno"],
  methods: {
    ...mapGetters(["getReplies"]),
    ...mapActions(replyStore, ["registerReply", "updateReplies"]),
    register() {
      if (!this.reply_content) {
        return;
      }
      let params = {
        articleno: this.articleno,
        userid: this.userInfo.userid,
        username: this.userInfo.username,
        content: this.reply_content,
      };
      this.registerReply(params);
      this.reply_content = "";
    },
  },
  created() {},
};
</script>

<style>
b-container {
  border: 1px;
  background-color: bisque;
}
</style>
