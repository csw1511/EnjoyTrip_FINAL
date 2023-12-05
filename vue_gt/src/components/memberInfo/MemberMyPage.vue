<script setup>
import { ref, onMounted } from "vue";
import MemberWrittenBoard from "./items/MemberWrittenBoard.vue";
import { jwtDecode } from "jwt-decode";
import { findById, logout, userDelete } from "@/api/member";
import { findByUserId } from "@/api/map";
import { registHotplace } from "@/api/hotplace";
import { httpStatusCode } from "@/util/http-status";
import { useMenuStore } from "@/stores/menu";
import router from "../../router";
const menuStore = useMenuStore();
const { changeMenuState } = menuStore;

const userInfo = ref({
  userId: "",
  userName: "",
  userNickname: "",
  userEmail: "",
});

const hotplaceContent = ref({
  userId: "",
  title: "",
  contentId: "",
  contentTypeName: "",
  registTime: "",
  addr1: "",
  memo: "",
});

onMounted(() => {
  getUserInfo();
  getFindedPlanUserId();
});

const getUserInfo = () => {
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);
  console.log(decodeToken.userId);
  hotplaceContent.value.userId = decodeToken.userId;

  findById(
    decodeToken.userId,
    (response) => {
      console.log(response.data.userInfo);
      if (response.status === httpStatusCode.OK) {
        userInfo.value = response.data.userInfo;
        console.log("3. getUserInfo data >> ", response.data);
      } else {
        console.log("유저 정보 없음!!!!");
      }
    },
    async (error) => {
      console.error(error);
    }
  );
};

const findedPlanUserId = ref([]);

const getFindedPlanUserId = () => {
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);
  console.log(decodeToken.userId);

  findByUserId(
    { userId: decodeToken.userId },
    ({ data }) => {
      console.log(data);

      let nowKey = 0;
      let totalArray = [];
      let nowKeyArray = [];

      for (let i = 0; i < data.length; i++) {
        if (data[i].planId != nowKey) {
          nowKey = data[i].planId;
          totalArray.push(nowKeyArray);
          nowKeyArray = [];
        }
        nowKeyArray.push(data[i]);
      }
      totalArray.push(nowKeyArray);
      findedPlanUserId.value = totalArray.slice(1);
      console.log(findedPlanUserId.value);
    },
    (err) => {
      console.log(err);
    }
  );
};

const modifyUserInfo = () => {
  console.log(userInfo.value.userId, "회원정보수정하러가자");
  router.push({ name: "user-modify", params: { userid: userInfo.value.userId } });
};

const modifyUserPassword = () => {
  console.log(userInfo.value.userId, "비밀번호변경하러가자");
  router.push({ name: "user-modifyPw" });
};

const deleteUser = () => {
  console.log(userInfo.value.userId, "사용자탈퇴및삭제하자");
  userDelete(userInfo.value.userId);
  logout(userInfo.value.userId);
  changeMenuState();
  console.log("페이지이동왜안됨");
  router.push("/");
};

const getContentId = (findedPlan) => {
  console.log("정보 : ", findedPlan);
  hotplaceContent.value.title = findedPlan.title;
  hotplaceContent.value.contentId = findedPlan.contentId;
  hotplaceContent.value.contentTypeName = findedPlan.contentTypeName;
  hotplaceContent.value.registTime = findedPlan.registTime;
  hotplaceContent.value.addr1 = findedPlan.addr1;
  // hotplaceContent.value.contentId = contentId;
  // console.log(hotplaceContent.value.contentId, "를 핫플레이스로 등록하자");
};

const registplace = () => {
  console.log("핫플레이스 호출 API : ", hotplaceContent.value),
    registHotplace(
      JSON.stringify(hotplaceContent.value),
      () => {},
      (error) => {
        console.log(error);
      }
    );
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">내정보</mark>
        </h2>
      </div>
      <div class="col-lg-10">
        <div class="card mt-3 m-auto" style="max-width: 700px">
          <div class="row g-0">
            <div class="col-md-4">
              <img src="https://source.unsplash.com/random/250x250/?food" class="img-fluid rounded-start" alt="..." />
            </div>
            <div class="col-md-8">
              <div class="card-body text-start">
                <ul class="list-group list-group-flush">
                  <li class="list-group-item">아이디 | {{ userInfo.userId }}</li>
                  <li class="list-group-item">이름 | {{ userInfo.userName }}</li>
                  <li class="list-group-item">닉네임 | {{ userInfo.userNickname }}</li>
                  <li class="list-group-item">이메일 | {{ userInfo.userEmail }}</li>
                </ul>
                <button type="button" class="btn btn-outline-primary mt-2" @click="modifyUserInfo">수정</button>
                <button type="button" class="btn btn-outline-success mt-2" @click="modifyUserPassword">
                  비밀번호 변경
                </button>
                <button type="button" class="btn btn-outline-danger mt-2" @click="deleteUser">회원탈퇴</button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">내 여행 계획</mark>
        </h2>
      </div>

      <div class="col-lg-10">
        <div class="card mt-3 m-auto" style="max-width: 700px">
          <div class="row g-0">
            <div class="col-md-12">
              <div class="card-body text-start">
                <ul class="list-group list-group-flush">
                  <!-- <router-link :to="{name:'travel'}" @click=""></router-link> -->
                  <li class="list-group-item" v-for="(findedList, index) in findedPlanUserId" :key="index">
                    <span
                      class="course-item"
                      v-for="(findedPlan, indexJ) in findedList"
                      key="indexJ"
                      @click="getContentId(findedPlan)"
                      data-bs-toggle="modal"
                      data-bs-target="#registHp"
                    >
                      {{ findedPlan.title }} &nbsp;
                    </span>
                    <!-- <MemberWrittenHotplace :finded-plan='findePlan' :id='#registHotplace' ></MemberWrittenHotplace> -->
                  </li>
                  <!-- <li class="list-group-item">SSAFY</li> -->
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- Modal -->
  <div
    class="modal fade"
    id="registHp"
    data-bs-backdrop="static"
    data-bs-keyboard="false"
    tabindex="-1"
    aria-labelledby="registPlace"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="registPlace">어여가!</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="mb-3">
            <label for="title" class="col-form-label">제목</label>
            <input type="text" class="form-control" id="title" v-model="hotplaceContent.title" readonly />
          </div>
          <div class="mb-3">
            <label for="contentTypeName" class="col-form-label">관광지유형</label>
            <input
              type="text"
              class="form-control"
              id="contentTypeName"
              v-model="hotplaceContent.contentTypeName"
              readonly
            />
          </div>
          <div class="mb-3">
            <label for="registTime" class="col-form-label">등록일</label>
            <input type="date" class="form-control" id="registTime" v-model="hotplaceContent.registTime" />
          </div>
          <div class="mb-3">
            <label for="addr1" class="col-form-label">주소</label>
            <input type="text" class="form-control" id="addr1" v-model="hotplaceContent.addr1" readonly />
          </div>
          <div class="mb-3">
            <label for="memo" class="col-form-label">메모</label>
            <input type="text" class="form-control" id="memo" v-model="hotplaceContent.memo" />
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
          <button type="button" class="btn btn-primary" @click="registplace" data-bs-dismiss="modal">
            핫플레이스 등록하기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.course-item {
  border-style: solid;
  border-width: 1px;
  border-color: #d9d9d9d9;
  margin: 2%;
  padding: 2%;
}
</style>
