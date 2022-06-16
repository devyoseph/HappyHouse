<template>
  <b-container>
    <div id="register" style="margin: 0 auto; width: 400px">
      <b-form-input
        class="mb-2"
        type="text"
        v-model="userid"
        placeholder="아이디를 입력하세요"
      />
      <b-form-input
        class="mb-4"
        type="text"
        v-model="username"
        placeholder="닉네임을 입력하세요"
      />
      <v-btn color="green" black right fab @click.prevent="registerMember">
        <v-icon>mdi-check-outline</v-icon>
      </v-btn>
    </div>
  </b-container>
</template>

<script>
import jwt_decode from "jwt-decode";
import http from "@/api/http";

export default {
  name: "RegisterView",
  props: {
    platform_id: {
      type: String,
    },
    email: {
      type: String,
    },
    platform: {
      type: String,
    },
  },
  data() {
    return {
      userid: "",
      username: "",
    };
  },
  methods: {
    registerMember() {
      if (!this.userid || !this.username) {
        return;
      }
      let params = {
        platform_id: this.platform_id,
        platform: this.platform,
        email: this.email,
        userid: this.userid,
        username: this.username,
      };
      http
        .post(`/user/`, params)
        .then((data) => {
          sessionStorage.setItem("access-token", data.data);
          console.log("access_token", jwt_decode(data.data));
          this.$router.push({ name: "home" });
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>
