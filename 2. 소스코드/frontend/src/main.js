import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import { BootstrapVue, BootstrapVueIcons } from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import VueSession from "vue-session";
import vuetify from "./plugins/vuetify";
import Vuetify from "vuetify/lib";
Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);
Vue.config.productionTip = false;
Vue.use(Vuetify);
import jQuery from "jquery";
global.$ = jQuery;
//

export default new Vuetify({
  icons: {
    iconfont: "mdi",
  },
});

Vue.config.productionTip = false;

//카카오 API JS KEY
window.Kakao.init("0060df94c7ad80480f5baf1c4ced440e");

//Session 사용
var sessionOptions = {
  persist: true,
};
Vue.use(VueSession, sessionOptions);

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");
