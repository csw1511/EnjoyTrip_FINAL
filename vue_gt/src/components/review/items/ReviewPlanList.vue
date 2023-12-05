<script setup>
import { ref, onMounted } from "vue";
import ReviewPlanListItem from "./ReviewPlanListItem.vue";
import { searchAttraction,findByContentId } from "@/api/map";
import { storeToRefs } from "pinia";
import { useAttrlistStore } from "@/stores/attrlist";
const attrlistStore = useAttrlistStore();
const { attractions, selectedListPlan, findByContentIdList } = storeToRefs(attrlistStore);

const searchAttr = ref();

const props = defineProps({
  iftitle : String
})

searchAttr.value = props.iftitle;
console.log(searchAttr.value);

const emit = defineEmits(["viewAttractions"]);

const searchByTitle = () => {
  searchAttraction(
    { title: searchAttr.value },
    ({ data }) => {
      console.log(data);
      attractions.value = data;
    },
    (err) => {
      console.log(err);
    }
  );
};

const viewAttractions = (attr) => {
  emit("viewAttractions", attr);
};

const selectedAttrPlan = (contentId) => {
    console.log("addbutton 눌림");
    findByContentIdFunc(contentId);
    for (let i = 0; i < attractions.value.length; i++) {
      if (attractions.value[i].contentId == contentId) {
        let tmpAttr = JSON.stringify(attractions.value[i]);
        tmpAttr = JSON.parse(tmpAttr);
        tmpAttr.isUsed = true;
        selectedListPlan.value.push(tmpAttr);
      }
    }
    console.log(selectedListPlan.value);
};

const findByContentIdFunc = (contentId) => {
  findByContentId(
    { contentId: contentId },
    ({ data }) => {
      // console.log(data);
      findByContentIdList.value = data;
      // console.log(JSON.stringify(findByContentIdList.value));
      let nowKey = 0;
      let totalArray = [];
      let nowKeyArray = [];
      for (let i = 0; i < findByContentIdList.value.length; i++) {
        if (findByContentIdList.value[i].planId != nowKey) {
          nowKey = findByContentIdList.value[i].planId;
          totalArray.push(nowKeyArray);
          nowKeyArray = [];
        }
        nowKeyArray.push(findByContentIdList.value[i]);
      }
      totalArray.push(nowKeyArray);
      findByContentIdList.value = totalArray.slice(1);
      console.log(findByContentIdList.value);
    },
    (err) => {
      console.log(err);
    }
  );
};


</script>

<template>
  <div>
    검색창 : <input type="text" v-model="searchAttr" @keyup.enter="searchByTitle" />
    <button @click="searchByTitle">검색</button>

    <table class="table table-hover">
      <thead>
        <tr class="text-center">
          <th scope="col">관광지명</th>
          <th scope="col">관광타입</th>
          <th scope="col">위치</th>
          <th scope="col">이미지</th>
          <th scope="col">추가</th>
        </tr>
      </thead>
      <tbody>
        <tr class="text-center" v-for="attr in attractions" :key="attr.contentId" @click="viewAttractions(attr)">
          <td>{{ attr.title }}</td>
          <td>{{ attr.contentTypeName }}</td>
          <td>{{ attr.addr1 }}</td>
          <td>

            <!-- <img :src="attr.firstImage" alt="" style="height: 100px; width: 100px" /> -->
            <img v-if="attr.firstImage != ''" :src="attr.firstImage" alt="" style="height: 100px; width: 100px" />
            <img v-else src="@/assets/img/emptywhite.jpg" alt="" style="height: 100px; width: 100px" />
          </td>
          <td><button @click="selectedAttrPlan(attr.contentId)">추가</button></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
/* Style for the container */
div {
    margin: 20px;
  }

  /* Style for the search input and button */
  input[type="text"] {
    padding: 5px;
    margin-right: 5px;
  }

  button {
    padding: 5px 10px;
    cursor: pointer;
  }

  /* Style for the table */
  table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
  }

  th, td {
    border: 1px solid #ddd;
    padding: 8px;
    text-align: center;
  }

  th {
    background-color: #f2f2f2;
  }

  /* Style for the images */
  img {
    max-width: 100%;
    max-height: 100%;
  }

  /* Style for the "추가" button */
  button {
    padding: 5px 10px;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }

  /* Hover effect for the table rows */
  tr:hover {
    background-color: #f5f5f5;
  }
</style>
