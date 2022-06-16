<template>
  <b-container>
    <div></div>
  </b-container>
</template>

<script>
import { mapState, mapActions, mapGetters } from "vuex";
export default {
  name: "BoardHeader",
  methods: {
    ...mapActions(["getArticle"]),
    goList() {
      this.$router.push({ name: "board" });
      this.getArticle(0);
    },
  },
  computed: {
    ...mapState(["articles"]),
    ...mapGetters(["GetPageNo"]),
  },
};
</script>
