<script setup>
import { useMenuStore } from "@/stores/menu";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { jwtDecode } from "jwt-decode";

const menuStore = useMenuStore();
const memberStore = useMemberStore();

// 반응형을 유지하면서 스토어에서 속성을 추출하려면, storeToRefs()를 사용
// https://pinia.vuejs.kr/core-concepts/
const { menuList } = storeToRefs(menuStore);
const { changeMenuState } = menuStore;

const { isLogin } = storeToRefs(memberStore);
const { userLogout, getUserInfo } = memberStore;

const logout = () => {
  console.log("로그아웃!!!!");
  console.log("isLogin: ", isLogin);
  let token = sessionStorage.getItem("accessToken");
  getUserInfo(token);
  userLogout(jwtDecode(token).userId);
  changeMenuState();
};


// const mypage = () => {};
</script>

<template>
  <!-- The Heading bard-->
  <nav class="navbar navbar-expand-lg navbar-light bg-white fixed-top">
    <div class="container">
      <!-- <input type="hidden" id="login-check" name="login" value="false" /> -->
      <router-link :to="{name:'main'}">
        <img src="@/assets/img/logo.png" alt="" />
      </router-link>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <!-- 로그인 전 -->
        <ul id="before-login" class="navbar-nav mb-2 me-2 mb-lg-0 ms-auto">
          <li class="nav-item">
            <router-link :to="{ name: 'travel' }" class="nav-link">여행지검색</router-link>
            <!-- <a class="nav-link" aria-current="page" href="#">여행지검색</a>-->
          </li>
          <li class="nav-item">
            <router-link :to="{ name: 'travelreviewboard' }" class="nav-link">여행 계획 공유</router-link>
          </li>
          <li class="nav-item">
            <router-link :to="{ name: 'board-hotplace', params: { categoryname: '핫플레이스' } }" class="nav-link"
              >핫플레이스</router-link
            >
          </li>
          <li class="nav-item dropdown">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              id="navbarDropdown"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              게시판
            </a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
              <li class="dropdown-item">
                <router-link class="router-category-name" :to="{ name: 'board', params: { categoryname: '공지사항' } }"
                  >공지사항</router-link
                >
              </li>
              <li><hr class="dropdown-divider" /></li>
              <li class="dropdown-item">
                <router-link
                  class="router-category-name"
                  :to="{ name: 'board', params: { categoryname: '질문게시판' } }"
                  >질문게시판</router-link
                >
              </li>
              <li><hr class="dropdown-divider" /></li>
              <li class="dropdown-item">
                <router-link
                  class="router-category-name"
                  :to="{ name: 'board', params: { categoryname: '자유게시판' } }"
                  >자유게시판</router-link
                >
              </li>
            </ul>
          </li>
          <template v-for="menu in menuList" :key="menu.routeName">
            <template v-if="menu.show">
              <template v-if="menu.routeName === 'user-logout'">
                <li class="nav-item">
                  <router-link to="/" @click.prevent="logout" class="nav-link">{{ menu.name }}</router-link>
                </li>
              </template>
              <template v-else-if="menu.routeName === 'user-mypage'">
                <li class="nav-item">
                  <router-link :to="{ name: menu.routeName }" class="nav-link">{{ menu.name }}</router-link>
                </li>
              </template>
              <template v-else>
                <li class="nav-item">
                  <router-link :to="{ name: menu.routeName }" class="nav-link">{{ menu.name }}</router-link>
                </li>
              </template>
            </template>
          </template>
          <!-- <li class="nav-item">
              <router-link :to="{name:'user-login'}" class="nav-link">로그인</router-link>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">회원가입</a>
            </li> -->
        </ul>

        <!-- 로그인 후
        <ul id="after-login" class="navbar-nav mb-2 me-2 mb-lg-0 ms-auto" style="display: none">
          <li class="nav-item">
            <a class="nav-link" aria-current="page" href="#">여행지검색</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">여행 계획</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Hot 플레이스</a>
          </li>
          <li class="nav-item dropdown">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              id="navbarDropdown"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              게시판
            </a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
              <li><a class="dropdown-item" href="#">공지사항</a></li>
              <li><hr class="dropdown-divider" /></li>
              <li><a class="dropdown-item" href="#">질문게시판</a></li>
              <li><hr class="dropdown-divider" /></li>
              <li><a class="dropdown-item" href="#">자유게시판</a></li>
            </ul>
          </li>
          <li class="nav-item dropdown">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              id="navbarDropdown"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              {{ 닉네임님 }} 반갑습니다.
            </a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
              <li><a class="dropdown-item" href="#">마이페이지</a></li>
            </ul>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">로그아웃</a>
          </li>
        </ul> -->

        <!-- 관리자 -->
        <ul id="after-login" class="navbar-nav mb-2 me-2 mb-lg-0 ms-auto" style="display: none">
          <li class="nav-item">
            <a class="nav-link" aria-current="page" href="#">여행지검색</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Hot 플레이스</a>
          </li>
          <li class="nav-item dropdown">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              id="navbarDropdown"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              게시판
            </a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
              <li><a class="dropdown-item" href="#">공지사항</a></li>
              <li><hr class="dropdown-divider" /></li>
              <li><a class="dropdown-item" href="#">질문게시판</a></li>
              <li><hr class="dropdown-divider" /></li>
              <li><a class="dropdown-item" href="#">자유게시판</a></li>
            </ul>
          </li>
          <li class="nav-item dropdown">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              id="navbarDropdown"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              {{ 관리자 }}님 반갑습니다.
            </a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
              <li><a class="dropdown-item" href="#">마이페이지</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<style scoped>
img {
  width: 40%;
  margin-top: 5%;
  margin-left: -20%;
}
.router-category-name {
  text-decoration: none;
  color: #6d6d6d;
}
</style>
