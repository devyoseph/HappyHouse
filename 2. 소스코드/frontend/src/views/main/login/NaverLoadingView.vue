<template>
  <div>
    <b-icon
      icon="star-fill"
      animation="fade"
      color="green"
      font-scale="4"
      x-large
    ></b-icon>
  </div>
</template>

<script>
import http from "@/api/http";
import { mapMutations } from "vuex";

export default {
  name: "NaverLoadingView",
  methods: {
    ...mapMutations(["SET_USER_INFO"]),
  },
  created() {
    let naver_id_login = new window.naver_id_login(
      "tgSH9dEWosClKiFjV9j1",
      "http://localhost:8080/naver",
    );
    http
      .post(`/naver/`, naver_id_login.getAccessToken())
      .then((data) => {
        const obj = JSON.parse(data.request.response);
        let message = obj.message;
        if (message === "LOGIN") {
          //회원가입 화면으로 이동
          sessionStorage.setItem("access_token", obj.access_token);

          //로그인시 최초 정보 저장
          let tmp = {
            platform: obj.userInfo.platform,
            email: obj.userInfo.email,
            userid: obj.userInfo.userid,
            username: obj.userInfo.username,
            joindate: obj.userInfo.joindate.slice(0, 10),
          };

          this.SET_USER_INFO(tmp);
          this.$router.push({ name: "home" });
        } else if (message === "REGISTER") {
          this.$router.push({
            name: "register",
            params: {
              platform_id: obj.userInfo.platform_id,
              platform: obj.userInfo.platform,
              email: obj.userInfo.email,
            },
          });
        } else {
          console.log("회원가입 실패");
        }
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>
