<template>
  <v-app>
    <board-header></board-header>
    <router-view></router-view>
  </v-app>
</template>

<script>
import BoardHeader from "@/components/header/BoardHeader.vue";

export default {
  name: "BoardView",
  components: {
    BoardHeader,
  },
};
</script>
