<script setup>
import { ref } from "vue";
import { RouterLink } from "vue-router";
import router from "../../router";

const title = ref("");

const findTitle = () => {
  console.log(title)
  router.push({ name: 'travels', params: { title: title.value } })
}

</script>

<template>
 <div class="search-mode">
    <input type="text" placeholder="어디로 떠날까요?" v-model="title" @keyup.enter="findTitle">


    <!-- <div class="icon">
      <div class="search">
        <i class="fas fa-search"></i>
      </div>

      <div class="plus-option">
        <i class="fas fa-keyboard"></i>
        <i class="fas fa-microphone"></i>
      </div> -->
    <!--</div>-->
</div>

  <!-- <div class="search-mode">
    <input type="text" placeholder="검색어를 입력해주세요.">
</div>
<div class="button">
     <button>검색</button>
</div> -->
    <!-- <input class="search-text" type="text" placeholder="가고 싶은 여행지를 입력하세요.">
    <button>
      <img src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png">
    </button> -->
    
    

    <!-- <div class="icon">
      <div class="search">
        <i class="fas fa-search"></i>
      </div>

      <div class="plus-option">
        <i class="fas fa-keyboard"></i>
        <i class="fas fa-microphone"></i>
      </div> -->
    <!--</div>-->

  <!-- <div class='search-div'>
    
  </div> -->
  <!-- <div id="search" class="search-div">
     <div id="search-box" class="input-group input-group-lg mb-3"> -->
      <!-- <div id="search-box" class="input-group input-group-lg search-box">
        <input id="searchInput"
        type="text"
        class="form-control"
        placeholder="{{닉네임}}님 어디로 떠날까요"
        aria-describedby="button-addon2">
        <img src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png"
        class="btn bg-white text-black"
        id="button-addon2"
        > -->



      <!-- <input
        id="searchInput"
        type="text"
        class="form-control"
        placeholder="{{닉네임}}님 어디로 떠날까요"
        aria-describedby="button-addon2"
      /> -->
      <!-- <button
        class="btn bg-white text-black"
        type="button"
        id="button-addon2"
      >
        Search
      </button>
      <a class="search-btn" href="#">
        <i class="fas fa-search"></i>
      </a> -->
    <!--</div>-->
  <!--</div> -->
  <!-- <div class='container-search'>

  </div> -->
  <!-- <div class="search">
  <input type="text" placeholder="검색어 입력">
  <img src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png">
</div> -->

</template>

<style scoped>
div {
     display:inline-block;
}
/* .search-div{
  text-align: center;
  background: red;
  margin-top: 107px;
  height: 130px; 
  position: relative;
  padding-top: 30px;
} */
/* .search-box{
  width: 300px;
  width: 500px;
  position: absolute;
  top: 50%;
  left: 50%;
  margin: -50px 0 0 -50px;
}
input {
  width: 100%;
  border: 1px solid #bbb;
  border-radius: 8px;
  padding: 10px 12px;
  font-size: 14px;
}
img {
  position : absolute;
  width: 17px;
  top: 10px;
  right: 12px;
  margin: 0;
} */
/* .container-search{
  margin-top: 107px;
}
.search {
  position: relative;
  width: 300px;
}

input {
  width: 100%;
  border: 1px solid #bbb;
  border-radius: 8px;
  padding: 10px 12px;
  font-size: 14px;
}

img {
  position : absolute;
  width: 17px;
  top: 10px;
  right: 12px;
  margin: 0;
} */
.search-mode {
  width: 42%;
  height: 110px;
  margin-top: 107px;
  /* background: red; */
  margin-left: 28%;
  margin-bottom: 3%;

  /* width: 42%; */
  /* height: 110px; */
  /* margin-top: 107px; */
  /* background: red; */
  /* margin-left: 28%; */
  /* margin-bottom: 3%; */
  
  /* width: 100%; */
  /* height: 40px; */
  /* text-align: center;
  height: 130px; 
  position: relative;
  padding-top: 30px;

  width: 30%;
  height: 40px;
  margin: 10px auto; */
}

input {

  background-color: #ffffff;
  border: 1px solid #C9C9C9;
  border-radius: 60px;
  width: 100%; 
  height: 60%;
  font-size: 20px;
  box-shadow: 1px 1px 1px #d9d9d9;
  margin-top: 20px;
  outline: 2px solid #d9d9d9;
  padding-left: 100px;
}
button{
  /* width: 10%;
  height: 100%;
  border: 0px;
  background: #d9d9d9;
  outline: none;
  float: right; */
  
  /* position: absolute;
  top: 0;
  right: 5px; */
}

/* .icon {
  
  display: flex;
  justify-content: space-between;
  position: absolute;
  top: 5px;
  right: 10px;
  left: 10px;
  
  
} */

.search {
  color: #808080;
}

.plus-option i {
  display: inline-block;
  margin-left: 5px;
}


.plus-option i:nth-child(2){
  color: #4F86EC; 
}
</style>