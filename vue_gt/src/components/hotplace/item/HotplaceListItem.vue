<template>
    <div class='card' data-bs-toggle="modal" :data-bs-target="'#exampleModal' + place.contentId" @click='clickModal'>
        <div class="main-image">
            <img v-if="place.firstImage != ''" :src=place.firstImage  alt="" class='mainimage'>
            <img v-else src="@/assets/img/emptygreen.jpg"  alt="" class='mainimage'>
        </div>
        <!-- <div class='div-hit'>
            후기 : {{placeCount}}
        </div> -->
        <div class='div-title'>
            {{ place.title}}
        </div>            
        <!-- <img @click="productStore.updateFavorite(product.name)"
        :src="product.isFavorite? 'src/assets/like.svg' : 'src/assets/nolike.svg'"/> -->
    </div>

    <!-- Modal -->
<div class="modal fade" :id="'exampleModal' + place.contentId" tabindex="-1" :aria-labelledby="'modalLabel' + place.contentId" aria-hidden="true" data-id='place' @click='clickBackground'>
  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-lg">
    <div class="modal-content mcontent">
      <div class="modal-header">
        <h1 class="modal-title fs-5" :id="'exampleModal' + place.contentId">{{place.title }}
            <span >
                <img class="mviews" src="@/assets/img/views.png">
            </span>
            <!-- 후기 개수{{placeCount}} -->
        </h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <!-- 핫플레이스 위치 -->
        <div class='left-body'>
            <VKakaoMap :selectAttraction="place"></VKakaoMap>
            왼쪽 카카오맵 지도
            <div class="accordion accordion-flush" id="accordionFlushExample">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="flush-headingOne">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseOne" aria-expanded="false" aria-controls="flush-collapseOne">
                        핫플레이스 설명
                    </button>
                    </h2>
                    <div id="flush-collapseOne" class="accordion-collapse collapse" aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
                    <div class="accordion-body">{{ place.overview }}</div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 사용자 핫플레이스 피드 내역-->
        <div class='right-body'>
            <HotplaceFeedItem @get-feed-length="getCountFeed" v-for="feed in feeds" :key="feeds.articleNo" :feed="feed"></HotplaceFeedItem>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
</template>

<script setup>
import {ref, onMounted} from "vue"
import {getHotplace, updateHit, checkFavorite, addFavorite, deleteFavorite} from "@/api/hotplace";
import { useRoute, useRouter } from "vue-router";
import HotplaceFeedItem from "./HotplaceFeedItem.vue";
import VKakaoMap from "../../common/VKakaoMap.vue";

const router = useRouter();
const route = useRoute();
const emit = defineEmits(['closeModal', 'getFeed'])

const props = defineProps({ place: Object, placeCount: Number });

const articleno = ref(props.place.articleNo);
const feeds = ref([]);

const pickedPlace = ref({});

onMounted(async () => {
    // console.log("item : ", props.place);
    getHotplaceFeed();
    pickedPlace.value = props.place;
    console.log("되나", pickedPlace)
});
const getCountFeed = function(count){
    emit('getFeed', count)
    console.log("후기 개수 : ", count);
}

const getHotplaceFeed = () => {
    console.log(props.place.contentId, "에 해당하는 피드를 가져오는 API 호출!!!!");
    getHotplace(
        props.place.contentId,
        ({ data }) => {
            console.log("부모 : ", data);
            feeds.value = data;
            console.log("부모2 :", feeds.value.length);
        },
        (error) => {
            console.log(error);
        }
    );
}

function clickModal() {
    // console.log("클릭한 모달의 게시글 조회수 증가하러 가자!!!", articleno.value);
    // // console.log(props.place.articleNo);
    // // views.value = views.value + 1;
    // console.log(views.value);
    // // 조회수 증가 API 호출
    // updateHit(articleno.value);
}

function clickBackground() {
    // if (props.userId != "") {
    //     console.log(articleno.value, "번 글  모달다을때: ", isFavorite.value)
    //     // 로그인한 경우에만 db에 좋아요 결과 반영
    //     if (isOriginalFavorite.value == true && isOriginalFavorite.value != isFavorite.value) {
    //         // 좋아요 취소한 경우 -> 좋아요 삭제
    //         console.log("좋아요를 삭제하는 API 호출!!!")
    //         deleteFavorite(articleno.value, props.userId)
    //     } else if (isOriginalFavorite.value == false && isOriginalFavorite.value != isFavorite.value) {
    //         // 좋아요 누른 경우 -> 좋아요 추가
    //         console.log("좋아요를 추가하는 API 호출!!!")
    //         addFavorite(articleno.value, props.userId)
    //     }
    // }

    // console.log("emit하러가자")
    // // 다시 getList 하라는 emit
    // emit('closeModal');
}

</script>

<style scoped>
.m-head{
    display: flex;
    height: 5%;
}
.feed-like{
    width: 5%;
    height: 5%;
}
.place-info{
    font-size: smaller;
}
.place-description{
    text-align: left;
    
}
.place-id{
    font-weight: bolder;
}
.place-favorite{
    font-size: smaller;
}
.feed-profile{
    width: 60%;
    height: 60%;
}
div.feed-left{
    flex-basis: 18%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    
}
div.feed-right{
    flex-basis: 82%;
    text-align: left;
    
    
}
.place-time{
    font-size: small;
}

.card{
    box-shadow: 0 1px 1px rgba(0,0,0,0.12),
    0 2px 2px rgba(0,0,0,0.12),
    0 4px 4px rgba(0,0,0,0.12),
    0 8px 8px rgba(0,0,0,0.12),
    0 16px 16px rgba(0,0,0,0.12);
    
    /* width: 100%;
    height: 100%; */
    /* height: 30%;
    width: 100%;
    height: 100%;
    border-style: solid;
    border-width: 0.5px;
    border-color: rgb(211, 211, 211);
    /* display: inline-block; */
    /* text-align: center;
    margin-left: 20px;
    margin-right: 20px; */ 
}
.mviews{
    width: 4%;
    height: 4%;
}
img{
    width: 7%;
    height: 7%;
}

#imgpath{
    width: 100%;
    height: 100%px;
    object-fit: cover;
}
.div-hit{
    float: left;
}
.div-title{
    float: right;
}
.full{
 background-image: url("https://www.flaticon.com/kr/free-icons/-");
}
.empty{
    background-image: url("https://www.flaticon.com/kr/free-icons/-");
}
.mcontent{
    justify-content: space-around
}
.left-body{
    float: left;
    width: 49%;
    height: 90%;
    /* background-color: red; */
    
}
.right-body{
    display: flex;
    float: right;
    width: 49%;
    height: 90%;
    background-color: white;
    border-color: #d9d9d9;
    border-width: 1px;
    border-style: solid;
    box-shadow: 0 1px 1px rgba(0,0,0,0.12),
    0 2px 2px rgba(0,0,0,0.12),
    0 4px 4px rgba(0,0,0,0.12),
    0 8px 8px rgba(0,0,0,0.12),
    0 16px 16px rgba(0,0,0,0.12);

}
.main-image{
    width: 100%;
    height: 100%;
}
.mainimage{
    width: 100%;
    height: 100%;
    
}
</style>

