<template>
  <b-container>
    <div class="text-center p-5 hover-zoom" @click="moveHome()">
      <h1>HAPPY HOUSE</h1>
    </div>
    <b-navbar
      class="shadow rounded"
      toggleable="lg"
      type="dark"
      style="background-color: rgba(052, 168, 083)"
    >
      <router-link to="/home/main" class="mx-3"
        ><b-navbar-brand>Main</b-navbar-brand>
      </router-link>
      <router-link to="/home/board"
        ><b-navbar-brand>Board</b-navbar-brand>
      </router-link>
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav class="ml-auto">
          <b-nav-item-dropdown text="MY PAGE " right>
            <b-dropdown-item href="#">
              <router-link to="/home/search">QnA</router-link></b-dropdown-item
            >
            <b-dropdown-item href="#"
              ><router-link to="/home/mylike"
                >찜목록</router-link
              ></b-dropdown-item
            >
            <b-dropdown-item href="#">
              <router-link to="/home/mymenu"
                >My Menu</router-link
              ></b-dropdown-item
            >
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </b-container>
</template>

<script>
export default {
  data() {
    return {
      upHere: false,
      navColor: "color: rgba(0, 0, 0, 0.7)",
    };
  },
  methods: {
    moveHome() {
      this.$router.push({ name: "home" });
    },
  },
};
</script>

<style scoped></style>
