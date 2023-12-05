const GODATA_API_KEY =
  "YUwVSect1PKQbI8R1ktNxw%2FVwulaCt7e4YIdnCuacTHcHclqKyoS%2FBM%2BpdM%2FeoTF70MjC%2B8RmdamplZvTm56tw%3D%3D";
const KAKAOMAP_API_KEY = "8a213fa191bd8e74afa7b141d7982242";



let areaUrl = `https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=${GODATA_API_KEY}&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json`;

var mapContainer = document.getElementById("map"), // 지도를 표시할 div
  mapOption = {
    center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
    level: 3, // 지도의 확대 레벨
  };

var map = new kakao.maps.Map(document.getElementById("map"), mapOption); //지도 생성 및 객체 리턴

var geocoder = new kakao.maps.services.Geocoder();

geocoder.addressSearch("서울특별시 광진구 능동로 120", (result, status) => {
  if (status === kakao.maps.services.Status.OK) {
    var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
    // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
    map.setCenter(coords);
  } else console.log(status);
});

fetch(areaUrl, { method: "GET" })
  .then((response) => response.json())
  .then((data) => makeOption(data));

const makeOption = (data) => {
  let areas = data.response.body.items.item;
  // console.log(areas);
  let sel = document.getElementById("search-area");
  areas.forEach((area) => {
    let opt = document.createElement("option");
    opt.setAttribute("value", area.code);
    opt.appendChild(document.createTextNode(area.name));

    sel.appendChild(opt);
  });
};

var mHtml = $("html");
var page = 1;

document.getElementById("button-addon2").addEventListener("click", (e) => {
  const keywordInfo = document.getElementById("keyword").value;

  if (!keywordInfo) return;

  let baseUrl = `https://apis.data.go.kr/B551011/KorService1/`;
  let queryString = `serviceKey=${GODATA_API_KEY}&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;

  let service = ``;

  service = `searchKeyword1`;
  queryString += `&keyword=${keywordInfo}`;

  let searchUrl = baseUrl + service + "?" + queryString;

  fetch(searchUrl)
    .then((response) => response.json())
    .then((data) => {
      listUp(data);
    });

  page = 2;
  var posTop = (page - 1) * $(window).height();
  mHtml.animate({ scrollTop: posTop });
});

document.getElementById("btn-search").addEventListener("click", () => {

	
	location.href = "/EnjoyTrip_Backend/enjoyTrip?action=test";
  let baseUrl = `https://apis.data.go.kr/B551011/KorService1/`;
  // let searchUrl = `https://apis.data.go.kr/B551011/KorService1/searchKeyword1?serviceKey=${serviceKey}&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;
  // let searchUrl = `https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=${serviceKey}&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;
 
  let queryString = `serviceKey=${GODATA_API_KEY}&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;
  let areaCode = document.getElementById("search-area").value;
  let contentTypeId = document.getElementById("search-content-id").value;
  let keyword = document.getElementById("search-keyword").value;

  if (parseInt(areaCode)) queryString += `&areaCode=${areaCode}`;
  if (parseInt(contentTypeId)) queryString += `&contentTypeId=${contentTypeId}`;
  // if (!keyword) {
  //   alert("검색어 입력 필수!!!");
  //   return;
  // } else searchUrl += `&keyword=${keyword}`;
  let service = ``;
  if (keyword) {
    service = `searchKeyword1`;
    queryString += `&keyword=${keyword}`;
  } else {
    service = `areaBasedList1`;
  }
  let searchUrl = baseUrl + service + "?" + queryString;

  fetch(searchUrl)
    .then((response) => response.json())
    .then((data) => listUp(data));
});

let markers = [];
let markerInfos = [];

const listUp = (data) => {
  console.log(data);
  let trips = data.response.body.items.item;
  let tripList = ``;
  eraseMarker();
  positions = [];

  document.querySelector("#place-list").setAttribute("style", "display: ;");

  if (!trips) return;

  trips.forEach((area) => {
    tripList += `<tr onclick="moveCenter(${area.mapy}, ${area.mapx});">
    <td><img src="${area.firstimage}" width="200px"></td>
    <td>${area.title}</td>
    <td>${area.addr1} ${area.addr2}</td>
    <td>${area.mapy}</td>
    <td>${area.mapx}</td>
  </tr>`;

    let markerInfo = {
      title: area.title,
      latlng: new kakao.maps.LatLng(area.mapy, area.mapx),
      address: area.addr1,
      mlevel: area.mlevel,
    };
    positions.push(markerInfo);
  });
  document.getElementById("trip-list").innerHTML = tripList;

  displayMarker();
};

const eraseMarker = () => {
  for (let index = 0; index < markers.length; index++) {
    markers[index].setMap(null);
    markerInfos[index].close();
  }
};

const displayMarker = () => {
  for (var i = 0; i < positions.length; i++) {
    // 마커를 생성합니다
    markers[i] = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: positions[i].latlng, // 마커를 표시할 위치
      title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
      // image: markerImage, // 마커 이미지
    });

    markerInfos[i] = new kakao.maps.InfoWindow({
      content: `
      <div class="list-group-item px-3 list-group-item-action active" aria-current="true">
        <div class="d-flex justify-content-between">
          <h6 class="mb-1">${positions[i].title}</h6>
          <small>${positions[i].mlevel} </small>
        </div>
        <p class="mb-4">${positions[i].address}</p>
        <small>.</small>
      </div>
      `,
      // content: `<div style="text-align:center;padding:6px 6px;">${positions[i].title}</div>`,
    });
    markerInfos[i].open(map, markers[i]);
  }

  // 첫번째 검색 정보를 이용하여 지도 중심을 이동 시킵니다
  map.setCenter(positions[0].latlng);
};

function moveCenter(lat, lng) {
  map.setCenter(new kakao.maps.LatLng(lat, lng));
}

{
  /* <a href="#" class="list-group-item list-group-item-action active" aria-current="true">
    <div class="d-flex w-100 justify-content-between">
      <h5 class="mb-1">List group item heading</h5>
      <small>3 days ago</small>
    </div>
    <p class="mb-1">Some placeholder content in a paragraph.</p>
    <small>And some small print.</small>
  </a> */
}

// window.addEventListener(
//   "wheel",
//   (e) => {
//     e.preventDefault();
//   },
//   { passive: false }
// );

// mHtml.animate({ scrollTop: 0 }, 10);

// $(window).on("wheel", (e) => {
//   if (mHtml.is(":animated")) return;
//   if (e.originalEvent.deltaY > 0) {
//     if (page == 1 || page == 4) return;
//     page++;
//   } else if (e.originalEvent.deltaY < 0) {
//     if (page == 1) return;
//     page--;
//   }
//   var posTop = (page - 1) * $(window).height();
//   mHtml.animate({ scrollTop: posTop });
// });
