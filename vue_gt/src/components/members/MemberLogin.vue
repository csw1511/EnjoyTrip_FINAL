<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { useMenuStore } from "@/stores/menu";

const router = useRouter();

const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;
const { changeMenuState } = useMenuStore();

const loginUser = ref({
  userId: "",
  userPassword: "",
});

const login = async () => {
  console.log("login ing!!!! !!!");
  await userLogin(loginUser.value);
  let token = sessionStorage.getItem("accessToken");
  console.log("111. ", token);
  console.log("isLogin: ", isLogin);
  if (isLogin) {
    console.log("로그인 성공아닌가???");
    getUserInfo(token);
    changeMenuState();
  }
  router.push("/");
};
</script>

<template>
  <div class="container ">
    <div class="row justify-content-center ">
      <!-- <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">로그인</mark>
        </h2>
      </div> -->
      <div class="col-lg-10 loginform">
        <div class="avatar">
            <img src="https://www.markuptag.com/images/user-icon.jpg" alt="Avatar">
        </div>
        <h2>로그인</h2>
        <form>
          <!-- <div class="form-check mb-3 float-end">
            <input class="form-check-input" type="checkbox" />
            <label class="form-check-label" for="saveid"> 아이디저장 </label>
          </div> -->
          
          <div class="mb-3 text-start">
            <label for="userid" class="form-label">아이디 : </label>
            <input
              type="text"
              class="form-control"
              v-model="loginUser.userId"
              placeholder="아이디..."
            />
          </div>
          <div class="mb-3 text-start">
            <label for="userpwd" class="form-label">비밀번호 : </label>
            <input
              type="password"
              class="form-control"
              v-model="loginUser.userPassword"
              @keyup.enter="login"
              placeholder="비밀번호..."
            />
          </div>
          <div class='checkbox-login'>
            <input class="form-check-input" type="checkbox" />
            <label class="form-check-label" for="saveid"> 아이디저장 </label>
          </div>
          <div class="col-auto text-center">
            <button type="button" class="btn btn-outline-primary mb-3 btn-login" @click="login">
              로그인
            </button>

            
          </div>
          <router-link :to="{name:'user-join'}" class='router-join'>
              <p clas='p-join'>아직 회원이 아니신가요?</p>
              <!-- <button type="button" class="btn btn-outline-success ms-1 mb-3 btn-join" >
                회원가입
              </button> -->
            </router-link>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container{
    margin: 0;
    padding: 0;
    padding-top: 50px;
    font-family: arial;
}

.loginform{
    background: #ffffff;
    width: 450px;
    color: #4caf50;
    top: 50%;
    left: 50%;
    position: absolute;
    transform: translate(-50%,-50%);
    box-sizing: border-box;
    padding: 40px 30px;
    margin-top: 100px;
    border-radius: 20px;
    box-shadow: 0px 10px 29px 0px #e0e0e0;
}

.loginform p{
    margin: 0;
    padding: 0;
    font-weight: bold;
}
.loginform h2 {
    font-size: 30px;
    margin: 30px 0px;
    text-transform: uppercase;
    font-weight: normal;
    text-align: center;
}
.loginform input{
    width: 100%;
    margin-bottom: 30px;
}

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
.loginform input[type="text"], input[type="password"]
{
    border: none;
    border-bottom: 1px solid #1e5220;
    background: transparent;
    outline: none;
    height: 40px;
    color: #333;
    font-size: 16px;
}
.loginform input[type="checkbox"]{
    width: 4%;
    margin-right: 10px;
    
}
.btn-login{
  width: 100%;
    margin-bottom: 30px;
  background: #4CAF50;
    color: #fff;
    font-size: 20px;
    padding: 7px 15px;
    border-radius: 20px;
    transition: 0.4s;
    border: none;
}
.btn-login:hover{
  cursor: pointer;
    background: #1f5822;
}

.router-join {
    text-decoration: none;
    font-size: 15px;
    line-height: 20px;
    color: #1e5220;
    text-align: center;
}
.loginform a:hover {
    color: #4caf50;
}
/*-- Responsive CSS --*/
@media(max-width: 576px) {
.loginform {
    width: 90%;
}
.p-join {
    display: block;
    text-align: center;
    float: inherit !important;
    margin: 10px 0px;
}
}

</style>