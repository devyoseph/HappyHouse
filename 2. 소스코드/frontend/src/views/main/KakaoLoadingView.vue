<template>
  <div>
    <p>Kakao Login Access Pages</p>
  </div>
</template>

<script>
import http from "@/api/http";

export default {
  mounted() {
    let code = document.location.href.split("?")[1].split("=")[1];
    http
      .post(`/kakao/?${code}`)
      .then((data) => {
        const obj = JSON.parse(data.request.response);
        let message = obj.message;
        console.log(message);
        if (message === "LOGIN") {
          //회원가입 화면으로 이동
          sessionStorage.setItem("access-token", obj.access_token);
          console.log(obj.access_token);
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
