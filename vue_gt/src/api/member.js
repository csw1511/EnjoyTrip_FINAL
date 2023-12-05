import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function userConfirm(param, success, fail) {
  console.log("param", param);
  await local.post(`/member/login`, param).then(success).catch(fail);
  console.log("userConfirm ok");
}

async function findById(userid, success, fail) {
  local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await local.get(`/member/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`/member/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await local.get(`/member/logout/${userid}`).then(success).catch(fail);
}

async function regist(user, success, fail) {
  user = user.value;
  console.log("user정보", user);
  const paramUser = {
    userName: user.userName,
    userId: user.userId,
    userPassword: user.userPassword,
    userNickname: user.userNickname,
    userEmail: user.userEmailId + "@" + user.userEmailDomain,
  };
  console.log("넘겨줄 user정보", paramUser);

  await local.post(`/member/join`, paramUser).then(success).catch(fail);
}

async function idCheck(userId, success, fail) {
  await local.get(`/member/join/${userId}`).then(success).catch(fail);
}

async function userModify(user, success, fail) {
  console.log(user);

  await local.post(`/member/modify`, user).then(success).catch(fail);
}

async function userModifyPw(params, success, fail) {
  console.log(params);

  await local.post(`/member/modifyPw`, params).then(success).catch(fail);
}

async function userDelete(params, success, fail) {
  console.log("in API", params);
  await local.delete(`/member/${params}`).then(success).catch(fail);
}

export { userConfirm, findById, tokenRegeneration, logout, regist, idCheck, userModify, userModifyPw, userDelete };
