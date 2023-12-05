<template>
  <div class="container">
    <p class="category-name">게시판 >>
      <router-link class="category-router" :to="{ name: 'board-hotplace', params: { categoryname: categoryname  } } " @click="replace">{{ categoryname }}</router-link>
    </p>
    <div class="div-wrap">
      <div class="col-lg-10 div-cards">
        <!-- <div class="row align-self-center mb-2">
          <div class="col-md-5 offset-5">
            <form class="d-flex">
              <VSelect :selectOption="selectOption" @onKeySelect="changeKey" />
              <div class="input-group input-group-sm">
                <input type="text" class="form-control" v-model="param.word" placeholder="검색어..." />
                <button class="btn btn-dark" type="button" @click="getArticleList">검색</button>
              </div>
            </form>
          </div>
        </div> -->
        <HotplaceListItem class="card" @close-modal="getPlaceList" @get-feed='getCountFeed' v-for="place in places" :key="place.contentId" :place="place" :place-count="placeCount"></HotplaceListItem>
      </div>
    </div>
    
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter, onBeforeRouteLeave } from "vue-router";
import { listPlace} from "@/api/hotplace";
import HotplaceListItem from "./item/HotplaceListItem.vue";
// import { useMemberStore } from "@/stores/member";

const router = useRouter();
const route = useRoute();

const categoryname = ref(route.params.categoryname);
// 현재 로그인한 회원의 아이디 
// const memberStroe = useMemberStore();
// const userIdVal = memberStroe.userInfo.userId;

const places = ref([]);
const placeCount = ref("");
const getCountFeed = function(count){
  console.log("값을알려주라 : ", count);
  placeCount.value = count;
  console.log("가져온 값 : ", placeCount.value);

}

onMounted(() => {
  getPlaceList();
});

const getPlaceList = () => {
  console.log("서버에서 핫플레이스 얻어오자!!!");
  // API 호출
  listPlace(
    ({ data }) => {
      // console.log("조상 : ", data);
      places.value = data.places;
      placeCount.value=places.value.length;
      // console.log("조상 : ", places.value);
    },
    (error) => {
      console.log(error);
    }
  );
};

function replace() {
  // 페이지 새로고침
  router.reload();
}

</script>

<style scoped>
.div-wrap{
  display: flex;
  justify-content: center;
  align-items: center;
}
.div-cards{
  /* display: flex;
  width: 50%;
  justify-content: space-between; */
  /* flex-basis: 30%;
  justify-content:row;
  gap:16px; */

  display: grid;
        grid-template-columns: 1fr 1fr;
        grid-template-rows: 1fr 1fr;
        grid-gap: 10px;

}
.card{
  background-color: black;

}
.category-name{
  text-align: left;
  color: #6D6D6D;
  margin-bottom: 30px;
}
.category-router{
  text-decoration: none;
  color: #6D6D6D;
}
.category-router:hover{
  text-decoration: none;
  color: #1DB954;
}
</style>