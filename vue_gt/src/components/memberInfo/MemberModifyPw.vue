<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { userModifyPw } from "@/api/member";
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

const modifyUserPassword = () => {

  if (!userInfo.value.userPasswordNew) {
    alert("비밀번호 입력!!");
    return;
  } else if (passwordDiv.value != userInfo.value.userPasswordNew) {
    alert("비밀번호 확인!!");
    return;
  } else {
    console.log(userInfo.value);
    userModifyPw(
      userInfo.value,
      (response) => {
        console.log("response", response);
      },
      async (error) => {
        console.error(error);
      }
    );
  }


  

  router.push({ name: "user-mypage" });
};

const passwordDiv = ref("");
const passwordCheckMessage = ref("");

const checkPassword = () => {
  if (passwordDiv.value.length >= 4 && userInfo.value.userPasswordNew.length >= 4) {
    if (passwordDiv.value === userInfo.value.userPasswordNew) {
      passwordCheckMessage.value = "비밀번호가 일치합니다.";
      document.querySelector("#password-view").setAttribute("class", "mb-3 fw-bold text-primary");
    } else {
      passwordCheckMessage.value = "비밀번호가 일치하지 않습니다. 다시 입력해 주십시오.";
      document.querySelector("#password-view").setAttribute("class", "mb-3 fw-bold text-danger");
    }
  } else {
    passwordCheckMessage.value = "비밀번호를 4자리 이상 설정해 주십시오.";
    document.querySelector("#password-view").setAttribute("class", "mb-3 fw-bold text-danger");
  }
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">비밀번호 변경</mark>
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
                      <label class="form-label">현재 비밀번호</label>
                      <input type="password" class="form-control" v-model="userInfo.userPasswordOld" />
                    </li>
                    <li class="list-group-item">
                      <label class="form-label">변경할 비밀번호</label>
                      <input type="password" class="form-control" v-model="userInfo.userPasswordNew" @focusout="checkPassword"/>
                    </li>
                    <li class="list-group-item">
                      <label class="form-label">변경할 비밀번호 확인</label>
                      <input type="password" class="form-control" v-model="passwordDiv" @focusout="checkPassword"/>
                    </li>
                    <li class="list-group-item" id="password-view">
                      {{ passwordCheckMessage }}
                    </li>
                  </ul>
                  <button type="button" class="btn btn-outline-primary mt-2" @click="modifyUserPassword">수정</button>
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
