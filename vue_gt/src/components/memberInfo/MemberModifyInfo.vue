<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { findById, userModify } from "@/api/member";
import { jwtDecode } from "jwt-decode";
import { httpStatusCode } from "@/util/http-status";

const route = useRoute();
const router = useRouter();
const { userid } = route.params;

const userInfo = ref({
  userName: "",
  userNickname: "",
  userEmail: "",
});
onMounted(() => {
  getUserInfo();
});

const getUserInfo = () => {
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);
  console.log(decodeToken.userId);

  findById(
    userid,
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

const modifyUserInfo = () => {
  console.log(userInfo.value);
  userModify(
    userInfo.value,
    (response) => {
      console.log("response", response);
    },
    async (error) => {
      console.error(error);
    }
  );

  router.push({ name: "user-mypage" });
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">내정보수정</mark>
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
                <form>
                  <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                      <label class="form-label">이름</label>
                      <input type="text" class="form-control" v-model="userInfo.userName" />
                    </li>
                    <li class="list-group-item">
                      <label class="form-label">닉네임</label>
                      <input type="text" class="form-control" v-model="userInfo.userNickname" />
                    </li>
                    <li class="list-group-item">
                      <label class="form-label">이메일</label>
                      <input type="text" class="form-control" v-model="userInfo.userEmail" />
                    </li>
                  </ul>
                  <button type="button" class="btn btn-outline-primary mt-2" @click="modifyUserInfo">수정</button>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
