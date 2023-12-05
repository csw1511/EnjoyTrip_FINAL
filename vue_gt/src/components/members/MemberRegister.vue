<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { regist, idCheck } from "@/api/member";

const router = useRouter();

const userInfo = ref({
  userName: "",
  userId: "",
  userPassword: "",
  userNickname: "",
  userEmailId: "",
  userEmailDomain: "",
});

const idCheckMessage = ref("");

const checkIdDuplicate = () => {
  if (userInfo.value.userId.length >= 4) {
    // console.log(userInfo.value.userId)
    idCheck(
      userInfo.value.userId,
      ({ data }) => {
        // console.log(data);
        if (data === 1) {
          idCheckMessage.value = "이미 존재하는 ID입니다.";
          document.querySelector("#id-view").setAttribute("class", "mb-3 fw-bold text-danger");
        } else {
          idCheckMessage.value = "사용 가능한 ID입니다.";
          document.querySelector("#id-view").setAttribute("class", "mb-3 fw-bold text-primary");
        }
      },
      (error) => {
        console.log(error);
      }
    );
  } else {
    idCheckMessage.value = "ID를 4자리 이상 설정해 주십시오.";
    document.querySelector("#id-view").setAttribute("class", "mb-3 fw-bold text-danger");
  }
};

const passwordDiv = ref("");
const passwordCheckMessage = ref("");

const checkPassword = () => {
  if (passwordDiv.value.length >= 4 && userInfo.value.userPassword.length >= 4) {
    if (passwordDiv.value === userInfo.value.userPassword) {
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

const memberRegist = async () => {
  
  if (!userInfo.value.userName) {
    alert("이름 입력!!");
    return;
  } else if (!userInfo.value.userId) {
    alert("아이디 입력!!");
    return;
    // } else if (!userId) {
    //   alert("아이디 중복 확인!!");
    //   return;
  } else if (!userInfo.value.userNickname) {
    alert("닉네임 입력!!");
    return;
  } else if (!userInfo.value.userPassword) {
    alert("비밀번호 입력!!");
    return;
  } else if (passwordDiv.value != userInfo.value.userPassword) {
    alert("비밀번호 확인!!");
    return;
  } else if (!userInfo.value.userEmailId) {
    alert("이메일 아이디 입력!!");
    return;
  } else if (!userInfo.value.userEmailDomain) {
    alert("이메일 도메인 입력!!");
    return;
  } else {
    console.log(userInfo.value);
    await regist(userInfo);
    router.push({ name: "user-login" });
  }
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <!-- <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">회원가입</mark>
        </h2>
      </div> -->
      <div class="col-lg-10 text-start joinform">
        <div class="avatar">
            <img src="https://www.markuptag.com/images/user-icon.jpg" alt="Avatar">
        </div>
        <h2>회원가입</h2>
        <form>
          <div class="mb-3">
            <label for="username" class="form-label">이름 : </label>
            <input type="text" class="form-control" id="userName" v-model="userInfo.userName" placeholder="이름..." />
          </div>
          <div class="mb-3">
            <label for="userid" class="form-label">아이디 : </label>
            <input
              type="text"
              class="form-control"
              v-model="userInfo.userId"
              id="userId"
              @focusout="checkIdDuplicate"
              placeholder="아이디..."
            />
          </div>
          <div class="mb-3" id="id-view">
            {{ idCheckMessage }}
          </div>
          <div class="mb-3">
            <label for="usernickname" class="form-label">닉네임 : </label>
            <input
              type="text"
              class="form-control"
              v-model="userInfo.userNickname"
              id="userNickname"
              placeholder="닉네임..."
            />
          </div>
          <div class="mb-3">
            <label for="userpwd" class="form-label">비밀번호 : </label>
            <input
              type="password"
              class="form-control"
              v-model="userInfo.userPassword"
              id="userPassword"
              @focusout="checkPassword"
              placeholder="비밀번호..."
            />
          </div>
          <div class="mb-3">
            <label for="pwdcheck" class="form-label">비밀번호확인 : </label>
            <input
              type="password"
              class="form-control"
              v-model="passwordDiv"
              @focusout="checkPassword"
              id="pwdcheck"
              placeholder="비밀번호확인..."
            />
          </div>
          <div class="mb-3" id="password-view">
            {{ passwordCheckMessage }}
          </div>
          <div class="mb-3">
            <label for="emailid" class="form-label">이메일 : </label>
            <div class="input-group">
              <input
                type="text"
                class="form-control"
                v-model="userInfo.userEmailId"
                id="userEmailId"
                placeholder="이메일아이디"
              />
              <span class="input-group-text">@</span>
              <select
                class="form-select"
                v-model="userInfo.userEmailDomain"
                id="userEmailDomain"
                aria-label="이메일 도메인 선택"
              >
                <option selected>선택</option>
                <option value="ssafy.com">ssafy.com</option>
                <option value="google.com">google.com</option>
                <option value="naver.com">naver.com</option>
                <option value="kakao.com">kakao.com</option>
              </select>
            </div>
          </div>
          <div class="col-auto text-center">
            <button type="button" class="btn btn-outline-primary mb-3 btn-join" @click="memberRegist">회원가입</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>

.joinform {
  background: #ffffff;
    width: 450px;
    color: #4caf50;
    top: 50%;
    left: 50%;
    position: absolute;
    transform: translate(-50%,-50%);
    box-sizing: border-box;
    padding: 40px 30px;
    margin-top: 200px;
    border-radius: 20px;
    box-shadow: 0px 10px 29px 0px #e0e0e0;
}
/* .joinform p{
    margin: 0;
    padding: 0;
    font-weight: bold;
} */
.joinform h2 {
    font-size: 30px;
    margin: 30px 0px;
    text-transform: uppercase;
    font-weight: normal;
    text-align: center;
}
/* .joinform input{
    width: 100%;
    margin-bottom: 30px;

} */
.avatar {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    overflow: hidden;
    margin: 0 auto;
    position: absolute;
    top: -55px;
    left: 0px;
    right: 0px;
    border: 6px solid #e6e6e6;
}
.avatar img {
    width: 100%;
    height: auto;
}

.btn-join{
  width: 100%;
  margin-top: 10px;
    margin-bottom: 30px;
    background: #4CAF50;
    color: #fff;
    font-size: 20px;
    padding: 7px 15px;
    border-radius: 20px;
    transition: 0.4s;
    border: none;
}
.btn-join:hover{
  cursor: pointer;
    background: #1f5822;
}


</style>
