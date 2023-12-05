<script setup>
import { ref, watch, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useAttrlistStore } from "@/stores/attrlist";
const attrlistStore = useAttrlistStore();
const { attractions } = storeToRefs(attrlistStore);

var map;
const positions = ref([]);
const markers = ref([]);

const props = defineProps({ selectAttraction: Object });

// const selectedAttrPlan = (contentId) => {
//   return async function () {
//     console.log("addbutton 눌림");
//     findByContentIdFunc(contentId);
//     for (let i = 0; i < attractions.value.length; i++) {
//       if (attractions.value[i].contentId == contentId) {
//         let tmpAttr = JSON.stringify(attractions.value[i]);
//         tmpAttr = JSON.parse(tmpAttr);
//         tmpAttr.isUsed = true;
//         selectedListPlan.value.push(tmpAttr);
//       }
//     }
//     console.log(selectedListPlan.value);
//   };
// };

// const findByContentIdFunc = (contentId) => {
//   findByContentId(
//     { contentId: contentId },
//     ({ data }) => {
//       // console.log(data);
//       findByContentIdList.value = data;
//       // console.log(JSON.stringify(findByContentIdList.value));
//       let nowKey = 0;
//       let totalArray = [];
//       let nowKeyArray = [];
//       for (let i = 0; i < findByContentIdList.value.length; i++) {
//         if (findByContentIdList.value[i].planId != nowKey) {
//           nowKey = findByContentIdList.value[i].planId;
//           totalArray.push(nowKeyArray);
//           nowKeyArray = [];
//         }
//         nowKeyArray.push(findByContentIdList.value[i]);
//       }
//       totalArray.push(nowKeyArray);
//       findByContentIdList.value = totalArray.slice(1);
//       console.log(findByContentIdList.value);
//     },
//     (err) => {
//       console.log(err);
//     }
//   );
// };

watch(
  () => props.selectAttraction.value,
  () => {
    // 이동할 위도 경도 위치를 생성합니다
    var moveLatLon = new kakao.maps.LatLng(props.selectAttraction.latitude, props.selectAttraction.longitude);

    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.panTo(moveLatLon);
  },
  { deep: true }
);
onMounted(() => {
  console.log(props.selectAttraction);
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    }&libraries=services,clusterer`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
});

watch(
  () => attractions.value,
  () => {
    positions.value = [];
    attractions.value.forEach((attraction) => {
      let obj = {};
      obj.latlng = new kakao.maps.LatLng(attraction.latitude, attraction.longitude);
      obj.title = attraction.title;
      obj.contentId = attraction.contentId;
      obj.firstImage = attraction.firstImage;

      positions.value.push(obj);
    });
    console.log("마커로드됨!");
    loadMarkers();
  },
  { deep: true }
);

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  console.log(container);
  console.log(options);
  map = new kakao.maps.Map(container, options);

  // loadMarkers();
};

const loadMarkers = () => {
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers();

  // 마커 이미지를 생성합니다
  //   const imgSrc = require("@/assets/map/markerStar.png");
  // 마커 이미지의 이미지 크기 입니다
  //   const imgSize = new kakao.maps.Size(24, 35);
  //   const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

  // 마커를 생성합니다
  markers.value = [];
  positions.value.forEach((position) => {
    // console.log("포지션이뭔데", position);
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: position.latlng, // 마커를 표시할 위치
      title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      // image: markerImage, // 마커의 이미지
    });
    markers.value.push(marker);

    var iwContent = `<div class="mks" style=" padding:20px; height:100%; width:300px;" value=${position.contentId} >
      <div>
        <img src=${position.firstImage} style="max-width:150px; max-height: 300px; margin: 0 auto; display: flex;">
        <hr>
      </div>
      <p class="markP" style=" margin: 0;"  >${position.title}</p>
      <br>
      <!--
      <div class="btnBox">
        <button style="height:30px;" id="hotplace" data-no=${position.title}>핫플레이스 등록</button>
        <div></div>
        <button style="height:30px;" data-no=${position.contentId} id="addButton">계획에 추가</button>
      </div>
      -->
    </div>`;

    var iwPosition = new kakao.maps.LatLng(33.450701, 126.570667);
    var iwRemoveable = true;

    var infowindow = new kakao.maps.InfoWindow({
      position: iwPosition,
      content: iwContent,
      removable: iwRemoveable,
    });

    kakao.maps.event.addListener(marker, "click", function () {
      // infowindow.close();
      console.log("click Event");
      infowindow.open(map, marker);
      // 인포윈도우 창이 열리고 버튼들에게 이벤트 주입.
      // var hotPlaces = document.querySelectorAll("#hotplace");
      // var addButtons = document.querySelectorAll("#addButton");
      // hotPlaces.forEach((hotPlace) => {
      //   hotPlace.addEventListener("click", hotplaceFunc);
      // });

      // addButtons.forEach((addButton) => {
      //   console.log(addButton);
      //   addButton.addEventListener("click", selectedAttrPlan(addButton.dataset.no));
      // });

      // emit("selected", position);
    });
    markers.value.push(marker);
  });

  // 4. 지도를 이동시켜주기
  // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);
};

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};

// const closeOverlay = () => {
//   console.log("오버레이닫기");
//   overlay.setMap(null);
// };
</script>

<template>
  <div id="map"></div>
</template>

<style>
#map {
  width: 100%;
  height: 700px;
}

.wrap {
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 288px;
  height: 132px;
  margin-left: -144px;
  text-align: left;
  overflow: hidden;
  font-size: 12px;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  line-height: 1.5;
}
.wrap * {
  padding: 0;
  margin: 0;
}
.wrap .info {
  width: 286px;
  height: 120px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}
.wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.info .title {
  padding: 5px 0 0 10px;
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 18px;
  font-weight: bold;
}
.info .close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}
.info .close:hover {
  cursor: pointer;
}
.info .body {
  position: relative;
  overflow: hidden;
}
.info .desc {
  position: relative;
  margin: 13px 0 0 90px;
  height: 75px;
}
.desc .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.desc .jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}
.info .img {
  position: absolute;
  top: 6px;
  left: 5px;
  width: 73px;
  height: 71px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}
.info:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.info .link {
  color: #5085bb;
}
</style>
