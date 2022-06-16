<template>
  <div class="my-5">
    <b-container fluid="md">
      <h2 class="p-5"></h2>
      <b-row>
        <b-col cols="1"></b-col>
        <b-col cols="3" class="rounded ml-5" align-v="center">
          <div>
            <span class="mx-2 text-dark"> 아이디 </span
            ><span class="mx-2 px-1 py-1 text-dark">
              {{ userInfo.userid }}
            </span>
          </div>
          <div class="my-4">
            <span class="mx-2 text-dark"> 닉네임 </span
            ><span class="mx-2 px-1 py-1 text-dark">
              {{ userInfo.username }}</span
            >
          </div>
          <div class="my-4">
            <span class="mx-2 text-dark"> 가입일 </span
            ><span class="mx-2 px-1 py-1 text-dark">
              {{ userInfo.joindate }}</span
            >
          </div>
        </b-col>

        <b-col cols="2">
          <center><div class="updown"></div></center>
        </b-col>
        <b-col cols="3" class="rounded ml-5" align-v="center">
          <div>
            <span class="mx-2 text-dark"> Team Leader </span
            ><span class="mx-2 px-1 py-1 text-dark"> @ joseph Yang </span>
          </div>
          <div class="my-4">
            <span class="mx-2 text-dark"> Team member </span
            ><span class="mx-2 px-1 py-1 text-dark"> @ yesol Shin</span>
          </div>
          <div class="my-4">
            <span class="mx-2 text-dark"> Agency</span
            ><span class="mx-2 px-1 py-1 text-dark"> ssafy</span>
          </div>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<style scoped>
.container-my {
  border: 1px;
  background-color: gainsboro;
  padding: 10px;
}
</style>

<script>
import { mapState } from "vuex";
export default {
  name: "MymenuView",
  computed: {
    ...mapState(["userInfo"]),
  },
};
</script>

<style scope>
.updown {
  border: 0.5px solid #929292;
  width: 0.1px;
  height: 120px;
}
</style>
출처: https://coding-restaurant.tistory.com/377 [코딩맛집:티스토리]
