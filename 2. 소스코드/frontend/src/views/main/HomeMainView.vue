<template>
  <div>
    <b-container>
      <house-search-bar></house-search-bar>

      <div class="row">
        <b-container class="col-6">
          <div
            id="map"
            style="height: 120%; border-radius: 0.5rem !important"
          ></div>
        </b-container>

        <div class="col-6">
          <b-container
            class="shadow"
            style="height: 120%; border-radius: 0.5rem !important"
          >
            <h3 class="m-5 text-center" style="color: rgba(0, 0, 0, 0.7)">
              "{{ houses[0].dongName }}" 검색 결과
            </h3>
            <house-list-item
              v-for="(house, index) in houses"
              :key="index"
              :house="house"
            />
          </b-container>
        </div>
      </div>
    </b-container>
  </div>
</template>

<script>
import HouseSearchBar from "@/components/header/item/HouseSearchBar.vue";
import HouseListItem from "@/components/header/item/HouseListItem.vue";
import { mapState } from "vuex";
const houseStore = "houseStore";
export default {
  name: "HomeMainVuew",
  data() {
    return {
      map: Object,
      positions: [],
      Markers: [],
    };
  },
  components: {
    HouseSearchBar,
    HouseListItem,
  },
  mounted() {
    window.kakao && window.kakao.maps
      ? this.initMap()
      : this.addKakaoMapScript();
  },
  methods: {
    addKakaoMapScript() {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=7f153b5ac2be01b9c1e04a6357f01e06";
      document.head.appendChild(script);
    },

    initMap() {
      var container = document.getElementById("map"); //지도를 담을 영역의 DOM 레퍼런스
      var options = {
        //지도를 생성할 때 필요한 기본 옵션
        center: new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
        level: 5, //지도의 레벨(확대, 축소 정도)
      };

      this.map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
      console.log(this.map);
    },

    addMarker(positions) {
      var imageSrc =
        "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

      var imageSize = new kakao.maps.Size(24, 35);
      var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

      for (var j = 0; j < positions.length; j++) {
        // 마커를 생성합니다
        let marker = new kakao.maps.Marker({
          //map: this.map, // 마커를 표시할 지도
          position: positions[j].latlng, // 마커를 표시할 위치
          image: markerImage, // 마커 이미지
        });
        this.Markers.push(marker);
        this.Markers[j].setMap(this.map);
        //marker.setMap(this.map);
        console.log(marker);
      }
      let nowlat = this.houses[0].lat;
      let nowlng = this.houses[0].lng;
      var moveLatLon = new kakao.maps.LatLng(nowlat, nowlng);
      this.map.setCenter(moveLatLon);
      // 지도 중심을 이동 시킵니다
    },
  },
  watch: {
    houses: function () {
      for (let j = 0; j < this.Markers.length; j++) {
        this.Markers[j].setMap(null);
      }
      this.Markers.length = 0;
      this.positions.length = 0;
      for (let house of this.houses) {
        let tmp = new kakao.maps.LatLng(house.lat, house.lng);
        this.positions.push({
          latlng: tmp,
        });
      }
      this.addMarker(this.positions);
      //maker.push(marker);
    },
  },
  computed: {
    //시도구군어쩌고가 바뀌면 houses 가 바뀌기 때문에
    ...mapState(houseStore, ["houses"]),
  },
};
</script>
