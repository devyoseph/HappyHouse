<template>
  <div>
    <b-container class="bv-example-row mt-3">
      <b-row class="mb-4">
        <h3 style="color: rgba(0, 0, 0, 0.7)" class="font-weight-bold ml-6">
          자유게시판
        </h3>
      </b-row>

      <v-simple-table>
        <template v-slot:default>
          <thead>
            <tr>
              <th width="20%" class="text-left">
                <h6>번호</h6>
              </th>
              <th width="45%" class="text-left">
                <h6>내용</h6>
              </th>
              <th width="15%" class="text-left">
                <h6>작성자</h6>
              </th>
              <th width="10%" class="text-left"></th>
            </tr>
          </thead>
          <tbody>
            <board-list-item
              v-for="article in articles"
              :key="article.articleno"
              v-bind="article"
            />
          </tbody>
        </template>
      </v-simple-table>
      <b-container>
        <b-row classalign-v="center" class="ml-2">
          <b-col cols="3"></b-col>
          <b-col cols="2">
            <btn color="rgba(066,133,244)" @click.prevent="lastPage">
              <v-icon>mdi-chevron-double-left</v-icon>
            </btn>
          </b-col>
          <b-col cols="2" class="text-center">{{ pageno + 1 }}</b-col>
          <b-col cols="2" class="text-right">
            <btn color="rgba(066,133,244)" @click.prevent="nextPage">
              <v-icon>mdi-chevron-double-right</v-icon>
            </btn>
          </b-col>
          <b-col cols="3"></b-col>
        </b-row>
        <b-row classalign-v="center">
          <b-col cols="1">
            <router-link
              :to="{
                name: 'boardregister',
              }"
            >
              <v-btn class="mt-5" fab dark large color="rgba(251,188,005)">
                <v-icon dark> mdi-pencil </v-icon>
              </v-btn>
            </router-link>
          </b-col>
          <b-col cols="1"></b-col>
          <b-col cols="1"></b-col>
          <b-col cols="7" style="margin: 0">
            <b-input
              class="mt-5"
              align-self="center"
              type="text"
              placeholder="검색어 입력"
              style="margin: 0"
              v-model="search_content"
            ></b-input>
          </b-col>
          <b-col cols="1">
            <v-btn
              color="rgba(234,067,053)"
              dark
              top
              right
              outline
              @click.prevent="searchContent"
              class="mt-5"
            >
              <v-icon>mdi-magnify</v-icon>
            </v-btn>
          </b-col>
        </b-row>
      </b-container>
    </b-container>
  </div>
</template>

<script>
import moment from "moment";
import BoardListItem from "@/components/header/item/BoardListItem";
import { mapState, mapActions, mapMutations, mapGetters } from "vuex";
export default {
  name: "BoardList",
  components: {
    BoardListItem,
  },
  data() {
    return {
      search_content: "",
    };
  },
  computed: {
    ...mapState(["articles", "userInfo", "pageno"]),
    ...mapGetters(["GetPageNo"]),
  },
  created() {
    this.SET_PAGE(0);
    this.getArticle(0);
    this.SET_ARTICLE_LIST();
  },
  filters: {
    dateFormat(regtime) {
      return moment(new Date(regtime)).format("YY.MM.DD");
    },
  },
  methods: {
    ...mapActions(["getArticle", "searchByContent"]),
    ...mapMutations(["SET_ARTICLE_LIST", "SET_PAGE"]),
    // async confirm() {
    //   await this.getUserInfo(sessionStorage.getItem("access_token"));
    //   await this.getArticle();
    // },
    nextPage() {
      this.moveContent(this.pageno + 1);
    },
    lastPage() {
      this.moveContent(this.pageno - 1);
    },
    searchContent() {
      let content = this.search_content.trim();
      if (!content) {
        alert("검색어를 입력해주세요");
        return;
      }
      let params = {
        type: "content",
        content: content,
        pageno: this.GetPageNo,
      };
      this.searchByContent(params);
    },
    moveContent(pageno) {
      if (pageno < 0) {
        alert("처음 페이지입니다.");
        return;
      }

      this.getArticle(pageno);
    },
  },
};
</script>

<style scoped></style>
