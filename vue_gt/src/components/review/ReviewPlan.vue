<script setup>
import { ref, onMounted } from "vue";
import { listSido, listGugun, listAttractions } from "@/api/map";
import { useRoute, useRouter } from "vue-router";

import VKakaoMap from "@/components/common/VKakaoMap.vue";
import VSelect from "@/components/common/VSelect.vue";
import ReviewPlanList from "./items/ReviewPlanList.vue";
import ReviewSelectedList from "./items/ReviewSelectedList.vue";
import { storeToRefs } from "pinia";
import { useAttrlistStore } from "@/stores/attrlist";
const attrlistStore = useAttrlistStore();
const { attractions, selectedListPlan, findByContentIdList } = storeToRefs(attrlistStore);

// const serviceKey = import.meta.env.VITE_OPEN_API_SERVICE_KEY;
// const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;
const route = useRoute();
const router = useRouter();

const sidoList = ref([]);
const gugunList = ref([{ text: "구군선택", value: "" }]);

//내가 좌측리스트에서 선택한 관광지가 담기는 변수. 맵 이동시에 사용된다
const selectAttraction = ref({});


// let title

const param = ref({
  sido: "",
  gugun: "",
  type: "",
});

const title = ref("");
onMounted(() => {
  console.log(route.params.title)
  if (route.params.title != undefined) {
    console.log("입력받은값", route.params.title)
    // title = route.params.title;
    // console.log(title)
    
    title.value = route.params.title;
    console.log(title.value)
  }
  setTimeout(() => {
    getAttractions();
    getSidoList();
  }, 100);
});

const getSidoList = () => {
  listSido(
    ({ data }) => {
      let options = [];
      options.push({ text: "시도선택", value: "" });
      data.forEach((sido) => {
        options.push({ text: sido.sidoName, value: sido.sidoCode });
      });
      sidoList.value = options;
    },
    (err) => {
      console.log(err);
    }
  );
};

const onChangeSido = (val) => {
  param.value.sido = val;
  listGugun(
    { sido: val },
    ({ data }) => {
      console.log(data);
      let options = [];
      options.push({ text: "구군선택", value: "" });
      data.forEach((gugun) => {
        options.push({ text: gugun.gugunName, value: gugun.gugunCode });
      });
      gugunList.value = options;
    },
    (err) => {
      console.log(err);
    }
  );
};

const onChangeGugun = (val) => {
  param.value.gugun = val;
  console.log("최종위치", param.value);
  getAttractions();
};

const attrType = ref("");
const onChangeAttrType = () => {
  param.value.type = attrType.value;
  console.log("최종위치 및 관광지타입", param.value);
  getAttractions();
};

const getAttractions = () => {
  listAttractions(
    param.value,
    ({ data }) => {
      for (let i = 0; i < data.length; i++) {
        data[i].isUsed = false;
        data[i].memo = "";
      }
      // console.log(data);
      attractions.value = data;
    },
    (err) => {
      console.log(err);
    }
  );
};

//맵 좌표이동용 함수. 선택한 관광지로 맵을 이동시킨다
const viewAttractions = (attraction) => {
  selectAttraction.value = attraction;
  console.log("selectAttraction가먼데", selectAttraction);
};

// const selectedKey = ref("");

const selectedPlanAdd = (findedList) => {
  for (let i = 0; i < findedList.length; i++) {
    findedList[i].isUsed = true;
    selectedListPlan.value.push(findedList[i]);
  }
  console.log(selectedListPlan.value);
};
</script>

<template>
  <div class="row">
    <div class="col-3">
      <ReviewPlanList @viewAttractions="viewAttractions" :iftitle="title"></ReviewPlanList>
    </div>
    <div class="container text-center col-6 mt-3">
      <div class="alert alert-success" role="alert">테마별 여행지 검색</div>

      <div class="row mb-2">
        <div class="col d-flex flex-row-reverse">
          <VSelect :selectOption="sidoList" @onKeySelect="onChangeSido" />
        </div>
        <div class="col"><VSelect :selectOption="gugunList" @onKeySelect="onChangeGugun" /></div>
      </div>
      <select id="search-content-id" class="form-select me-2" v-model="attrType" @change="onChangeAttrType">
        <option value="" selected>관광지 유형</option>
        <option value="12">관광지</option>
        <option value="14">문화시설</option>
        <option value="15">축제공연행사</option>
        <!-- <option value="25">여행코스</option> -->
        <option value="28">레포츠</option>
        <option value="32">숙박</option>
        <option value="38">쇼핑</option>
        <option value="39">음식점</option>
      </select>
      <VKakaoMap :selectAttraction="selectAttraction" />
      <div class="text-center" v-for="(findedList, index) in findByContentIdList" :key="index">
        <span v-for="(fList, indexJ) in findedList" :key="indexJ"> {{ fList.title }} &nbsp;&nbsp;</span>
        <span><button @click="selectedPlanAdd(findedList)">계획에 추가하기</button></span>
      </div>
    </div>
    <div class="col-3">
      <ReviewSelectedList></ReviewSelectedList>
    </div>
  </div>
</template>

<style>
mark.purple {
  background: linear-gradient(to top, #c354ff 20%, transparent 30%);
}

/* ========== */
.alert-success {
  background-color: #28a745;
  color: #fff;
}

/* Style for the map container */
.container.text-center {
  margin-top: 3rem;
}

/* Style for the select dropdowns */
.row.mb-2 .col {
  margin-bottom: 1rem;
}

/* Style for the search-content-id select */
#search-content-id {
  width: 100%;
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  line-height: 1.5;
  color: #495057;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid #ced4da;
  border-radius: 0.25rem;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

/* Style for the button to add to the plan */
button {
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

/* Style for the table */
.table {
  width: 100%;
  margin-bottom: 1rem;
  color: #212529;
}

.table th,
.table td {
  padding: 0.75rem;
  vertical-align: top;
  border-top: 1px solid #dee2e6;
}

.table thead th {
  vertical-align: bottom;
  border-bottom: 2px solid #dee2e6;
}

/* Style for the rows in the table */
.table tbody tr:hover {
  background-color: rgba(0, 123, 255, 0.1);
}

/* Style for the images in the table */
.table img {
  height: 100px;
  width: 100px;
}
/* ========== */

.text-center {
  text-align: center;
}

.text-center span {
  display: inline-block;
  margin-right: 10px; /* Adjust the margin as needed */
}


</style>
