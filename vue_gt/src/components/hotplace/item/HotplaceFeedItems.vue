<template>
    <div class="feed-wrap" >
        <div class='m-head'>
            <div class='feed-left'>
                <img class="feed-profile" src="@/assets/img/profile.png" >
            </div>
        <div class='feed-right'>
            <div class="place-id">
                    {{ item.userId }}
            </div>
            <div class='place-info'>
                {{ item.title }}, {{ item.addr1}}{{ item.addr2}}
                </div>
            </div>
        </div>
        
        <div class='main-image'>
            <img v-if="item.firstImage != ''" :src=item.firstImage  alt="" class='mainimage'>
            <img v-else src="@/assets/img/emptygreen.jpg"  alt="" class='mainimage'>
        </div>
        <div class='place-description'>
            <div class='place-favorite'>
                <!-- 좋아요 기능-->
                <img v-if='isFavorite' src='@/assets/img/icon_like.png' @click="isClickFavorite" class='feed-like'/>
                <img v-else src='@/assets/img/icon_nolike.png' @click="isClickFavorite" class='feed-like'/>
                <!-- <img @click="isClickFavorite" :src="isFavorite? 'src/assets/img/like.svg' : 'src/assets/img/nolike.svg'"/> -->
                <span v-if='favoriteCount > 0'>좋아요{{ favoriteCount}}개</span>
            </div>
            <div class='place-id'>
                {{ item.userId }}
            </div>
            <div class='place-memo'>
                {{item.memo }}
            </div>
            <div class='place-time'>
                {{ item.registTime }}
            </div>
        </div>
    </div>
</template>

<script setup>
import {ref, onMounted} from "vue"
import { updateHit, checkFavorite, addFavorite, deleteFavorite} from "@/api/hotplace";
import { useRoute, useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { jwtDecode } from "jwt-decode";

const router = useRouter();
const route = useRoute();
const emit = defineEmits(['closeModal'])

const memberStroe = useMemberStore();
// const userIdVal = memberStroe.userInfo.userId;
const userIdVal = ref("");


const props = defineProps({ item: Object});
const articleno = ref(props.item.articleNo);
const views = ref(props.item.hit);

// 좋아요 클릭 여부
const isFavorite = ref(false);
// 좋아요 수
const favoriteCount = ref(props.item.favoriteCount);
// 현재 좋아요 상태
const isOriginalFavorite = ref(false);

  
onMounted(() => {
    console.log("자손 : ", props.item);
    isMember() 
    getFavoriteState();
});

// 로그인 여부 판단하기
function isMember() {
  console.log("현재 로그인 한 여부를 판단하자!!! (미출력은 로그인안한 경우)")
  let token = sessionStorage.getItem("accessToken");
  if (token != null) {
      // 로그인한 상태(여기서부터 로그인여부를 판단해야함.)
      let decodeToken = jwtDecode(token);
      userIdVal.value = decodeToken.userId;
      console.log("현재 로그인한 회원의 아이디 : ", userIdVal.value);
  } else {
  alert("로그인 후 이용해 주십시오")
  router.push("/")
  }
}


const getFavoriteState = () => {
    if (userIdVal.value != "") {
        // 현재 로그인한 사용자의 좋아요 체크 여부 확인 API 호출
        console.log(props.item.articleNo,"번글의 좋아요 여부를 확인하는 API 호출!!!")
          checkFavorite(
          articleno.value, userIdVal.value,
              ({ data }) => {
              isOriginalFavorite.value = data;          // 원본 상태 저장할
              //결과값이 1이면 ture로 전환하기
              if (data === 1) {
                  console.log("현재 상태 : ", isFavorite.value);
                  isFavorite.value = !isFavorite.value;
                  console.log("변화 후 상태 : ", isFavorite.value);
             }
          },
          (error) => {
            console.log(error);
          }
        );
    }
};


// 좋아요 클릭상태 변화
function isClickFavorite() {
    if (userIdVal != "") {
        // 좋아요 여부 바꾸기
        console.log("현재 좋아요 상태 : ",favoriteCount.value);
        // 좋아요 상태 변환
        isFavorite.value = !isFavorite.value;
        // 좋아요 상태에 따른 좋아요 개수
        if (isFavorite.value != false) {
            // 상태 변환 결과가 좋아요 true -> 개수 증가
            favoriteCount.value = favoriteCount.value + 1;
        } else {
            // 상태 변환 결과가 좋아요 false -> 개수 감소
            favoriteCount.value = favoriteCount.value - 1;
        }
        console.log("카운트 : ",  favoriteCount.value);
        updateFavorite();
    } else {
        console.log("로그인 안해서 상태변화 불가");
        alert("해당 서비스는 로그인한 회원만 이용 가능합니다.")
    }
}

function updateFavorite() {
    if (userIdVal.value != "") {
        console.log(articleno.value, "번 글  모달다을때: ", isFavorite.value)
        // 로그인한 경우에만 db에 좋아요 결과 반영
        if (isOriginalFavorite.value == true && isOriginalFavorite.value != isFavorite.value) {
            // 좋아요 취소한 경우 -> 좋아요 삭제
            console.log("좋아요를 삭제하는 API 호출!!!")
            deleteFavorite(articleno.value, userIdVal.value)
        } else if (isOriginalFavorite.value == false && isOriginalFavorite.value != isFavorite.value) {
            // 좋아요 누른 경우 -> 좋아요 추가
            console.log("좋아요를 추가하는 API 호출!!!")
            addFavorite(articleno.value, userIdVal.value)
        }
    }

    console.log("emit하러가자")
    // 다시 getList 하라는 emit
    // emit('closeModal');
}

</script>

<style scoped>
.feed-wrap{
    margin-bottom: 10%;
}
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
    width: 100%;
    height: 100%;
    border-style: solid;
    border-width: 0.5px;
    border-color: rgb(211, 211, 211);
    display: inline-block;
    text-align: center;
    margin-left: 20px;
    margin-right: 20px;
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
    background-color: red;
    
}
.right-body{
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
/* .main-image{
    width: 80%;
    height: 80%;
} */
.mainimage{
    width: 100%;
    height: 100%;
    
}
</style>

