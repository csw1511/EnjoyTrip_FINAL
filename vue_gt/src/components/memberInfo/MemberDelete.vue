<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { userDelete } from "@/api/member";
import { jwtDecode } from "jwt-decode";

const router = useRouter();

const userInfo = ref({
  userId: "",
  userPasswordOld: "",
  userPasswordNew: "",
});

onMounted(() => {
  getUserInfo();
});

const getUserInfo = () => {
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);
  userInfo.value.userId = decodeToken.userId;
  console.log(decodeToken.userId);
};

const deleteUser = () => {
  console.log(userInfo.value);
  userDelete(
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
          <mark class="orange">회원 탈퇴</mark>
        </h2>
      </div>
      <div class="col-lg-10">
        <div class="card mt-3 m-auto" style="max-width: 700px">
          <div class="row g-0">
            <div class="col-md-12">
              <div class="card-body text-start">
                <form>
                  <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                      <label class="form-label">비밀번호 확인</label>
                      <input type="text" class="form-control" v-model="userInfo.userPasswordOld" />
                    </li>
                  </ul>
                  <button type="button" class="btn btn-outline-danger mt-2" @click="deleteUser">회원탈퇴</button>
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
