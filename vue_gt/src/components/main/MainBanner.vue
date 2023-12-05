<script setup>
import {ref, onMounted} from "vue"
import MainBannerCard from './items/MainBannerCard.vue';
import {getTopCourse, getTopPlace} from "@/api/main";
const topTravel = ref({});
const topPlace = ref({});

onMounted(() => {
  getTravel();
  getPlace();
})

const getTravel = () =>{
  console.log("좋아요를 많이 받은 3개의 후기글을 가져오는 api 호출!!!");
  
  getTopCourse(
    ({ data }) => {
      console.log("getTopCourse-호출 결과 : ", data);
      topTravel.value = data;
      console.log("topTravel-호출 결과 : ", topTravel.value);
    },
    (error) => {
      console.log(error);
    }
  );

}

const getPlace = () => {
  console.log("가장 많은 후기가 있는 핫플레이스 3곳을 가져오는 api 호출!!!");
  
  getTopPlace(
    ({ data }) => {
      console.log("getTopCourse-호출 결과 : ", data);
      topPlace.value = data;
      console.log("topPlace-호출 결과 : ", topPlace);
    },
    (error) => {
      console.log(error);
    }
  );
}
</script>

<template>
  <link rel="stylesheet" href="@/assets/css/index.css" />
  <div class='card-container'>
    <div>
      <h3>어여러 Pick! 여행코스</h3>
      <MainBannerCard v-for="travel in topTravel" :key="travel.pladnId" :tavel="travel"></MainBannerCard>
    </div>
    <div class='card-container'>
      <h3>고민하는 당신, 어여가!</h3>
      <!-- 여행지 종류 4개 정도 -->
      <!-- <MainBannerCard v-for="place in topPlace" :key="place.contentId" :tavel="place"></MainBannerCard> -->
      <div class='card-container'>
      <h3>테마별 여행지</h3>
      <div class="card" style="width: 18rem;">
        <img src="..." class="card-img-top" alt="...">
        <div class="card-body">
          <h5 class="card-title">여행지 종류 입력</h5>
          <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
          <a href="#" class="btn btn-primary">Go somewhere</a>
        </div>
      </div>
      <div class="card" style="width: 18rem;">
        <img src="..." class="card-img-top" alt="...">
        <div class="card-body">
          <h5 class="card-title">여행지 종류 입력</h5>
          <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
          <a href="#" class="btn btn-primary">Go somewhere</a>
        </div>
      </div>
      <div class="card" style="width: 18rem;">
        <img src="..." class="card-img-top" alt="...">
        <div class="card-body">
          <h5 class="card-title">여행지 종류 입력</h5>
          <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
          <a href="#" class="btn btn-primary">Go somewhere</a>
        </div>
      </div>
      <div class="card" style="width: 18rem;">
        <img src="..." class="card-img-top" alt="...">
        <div class="card-body">
          <h5 class="card-title">여행지 종류 입력</h5>
          <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
          <a href="#" class="btn btn-primary">Go somewhere</a>
        </div>
      </div>
    </div>
    </div>
  </div>
</template>

<style scoped>
.card{
    display: inline-block;
    margin-inline-start: 10px;
}
</style>