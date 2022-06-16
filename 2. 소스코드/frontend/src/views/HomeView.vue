<template>
  <div class="home">
    <header-nav></header-nav>
    <router-view></router-view>
  </div>
</template>

<script>
// @ is an alias to /src
import HeaderNav from "@/components/header/HeaderNav.vue";
import { mapGetters } from "vuex";

export default {
  name: "HomeView",
  components: {
    HeaderNav,
  },
  methods: {
    ...mapGetters(["GetUserInfo"]),
  },
  created() {
    console.log("홈페이지???", this.GetUserInfo());
  },
};
</script>
