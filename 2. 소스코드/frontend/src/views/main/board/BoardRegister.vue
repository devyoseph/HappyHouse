<template>
  <div>
    <h2 class="text-center">글쓰기</h2>
    <board-input-item></board-input-item>
  </div>
</template>

<script>
import BoardInputItem from "@/components/header/item/BoardInputItem.vue";

export default {
  name: "BoardModify",
  components: {
    BoardInputItem,
  },
};
</script>

<style></style>
