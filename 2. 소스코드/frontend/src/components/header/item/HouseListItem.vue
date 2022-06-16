<template>
  <b-row
    style="weight: 100%"
    @click="selectHouse"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
    :class="{ 'mouse-over-bgcolor': isColor }"
  >
    <b-col sm="4" class="text-center">
      <p class="font-weight-bold" style="color: rgba(0, 0, 0, 0.7)">
        {{ house.aptName }}
      </p>
    </b-col>
    <b-col sm="4" class="text-center" style="color: rgba(0, 0, 0, 0.7)">
      {{ house.sidoName }} {{ house.gugunName }} {{ house.dongName }}
    </b-col>
    <b-col sm="4" class="text-center" style="color: rgba(0, 0, 0, 0.7)">
      {{ house.recentPrice }} (만)원
    </b-col>
  </b-row>
</template>

<script>
import { mapActions } from "vuex";

const houseStore = "houseStore";

export default {
  name: "HouseListItem",
  data() {
    return {
      isColor: false,
    };
  },
  props: {
    house: Object,
  },
  methods: {
    ...mapActions(houseStore, ["detailHouse"]),
    selectHouse() {
      this.detailHouse(this.house);
    },
    colorChange(flag) {
      this.isColor = flag;
    },
  },
  created: {},
};
</script>

<style scoped>
.apt {
  width: 50px;
}
.mouse-over-bgcolor {
  background-color: lightblue;
}
</style>
