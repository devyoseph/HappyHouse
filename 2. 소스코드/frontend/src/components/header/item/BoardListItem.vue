<template>
  <tr>
    <td class="text-left">
      <h6>{{ articleno }}</h6>
    </td>
    <th class="text-left">
      <router-link
        style="color: rgba(0, 0, 0, 0.7) !important"
        :to="{ name: 'boardDetail', params: { articleno: articleno } }"
        ><h6 @click="increaseVisit()" style="font-weight: bolder !important">
          {{ subject }}
        </h6></router-link
      >
    </th>
    <td class="text-left" style="color: rgba(0, 0, 0, 0.7) !important">
      {{ username }}
    </td>
    <td class="text-left" style="color: rgba(0, 0, 0, 0.7) !important">
      {{ visit_count }}
    </td>
  </tr>
</template>

<script>
import moment from "moment";
import { mapActions } from "vuex";

export default {
  name: "BoardListItem",
  props: {
    articleno: Number,
    username: String,
    subject: String,
    create_date: String,
    visit_count: String,
  },
  filters: {
    dateFormat(regtime) {
      return moment(new Date(regtime)).format("YY.MM.DD");
    },
  },
  methods: {
    ...mapActions(["updateVisitCount"]),
    increaseVisit() {
      console.log(this.articleno, "방문수증가");
      this.updateVisitCount(this.articleno);
    },
  },
};
</script>

<style></style>
