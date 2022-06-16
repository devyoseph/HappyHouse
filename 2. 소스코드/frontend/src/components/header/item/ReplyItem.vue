<template>
  <b-row class="mt-0 mb-0 ml-1">
    <b-col cols="1"></b-col>
    <b-col cols="2" class="pa-1">
      <label
        ><strong style="color: rgba(0, 0, 0, 0.8)">{{
          reply.userid
        }}</strong></label
      >
    </b-col>
    <b-col class="pa-1 text-left" cols="6">
      {{ reply.content }}
    </b-col>
    <b-col cols="1" class="pa-1 text-left">
      <btn @click.prevent="deleteThisReply">
        <v-icon dark style="color: rgba(223, 115, 115)"
          >mdi-backspace-reverse</v-icon
        >
      </btn>
    </b-col>
  </b-row>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";
const replyStore = "replyStore";

export default {
  name: "ReplyItem",
  computed: {
    ...mapState(["userInfo"]),
    ...mapState(replyStore, ["replies"]),
  },
  props: {
    reply: Object,
  },
  methods: {
    ...mapGetters(["GetUserInfo"]),
    ...mapActions(replyStore, ["updateReplies", "deleteReply"]),
    ...mapGetters(replyStore, ["getReplies"]),
    deleteThisReply() {
      confirm("정말로 삭제?", this.deleteReply(this.reply));
    },
  },
  created() {},
};
</script>

<style>
.b-col {
  padding-top: 0;
  padding-bottom: 0;
}
</style>
