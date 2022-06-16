<template>
  <v-app>
    <v-container>
      <div class="mt-4 md-5 text-center"><br /></div>
      <h1 class="text-center" style="font-size: 5em">HAPPY HOUSE</h1>
      <div id="naver_id_login" style="display: none"></div>
      <div><br /></div>
      <div><br /></div>
      <div><br /></div>
      <div><br /></div>
      <div><br /></div>
      <div><br /></div>
      <router-view class="mt-5 text-center"></router-view>
    </v-container>
  </v-app>
</template>

<script>
import jQuery from "jquery";

let $ = jQuery;
$(document).on("click", "#naverLogin", function () {
  var btnNaverLogin = document.getElementById("naver_id_login").firstChild;
  btnNaverLogin.click();
});

export default {
  name: "LoginView",
  mounted() {
    var naver_id_login = new window.naver_id_login(
      "tgSH9dEWosClKiFjV9j1",
      // eslint-disable-next-line
      "http://localhost:8080/naverloading"
    );
    const state = naver_id_login.getUniqState();
    naver_id_login.setButton("white", 1, 40); // 버튼 설정
    naver_id_login.setState(state);
    // naver_id_login.setPopup(); // popup 설정을 위한 코드
    naver_id_login.init_naver_id_login();
  },
};
</script>

<style>
* {
  padding: 2px;
}
img {
  width: 15%;
  height: 5%;
}
</style>
