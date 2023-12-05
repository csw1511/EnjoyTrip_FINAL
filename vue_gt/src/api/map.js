import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listSido(success, fail) {
  local.get(`/map/sido`).then(success).catch(fail);
}

function listGugun(param, success, fail) {
  console.log(param);
  local.get(`/map/gugun`, { params: param }).then(success).catch(fail);
}

function listAttractions(param, success, fail) {
  console.log(param);
  local.get(`/map/attractions`, { params: param }).then(success).catch(fail);
}

function searchAttraction(param, success, fail) {
  console.log(param);
  local.get(`/map/searchbytitle`, { params: param }).then(success).catch(fail);
}

function savePlanDetail(param, success, fail) {
  // console.log(param.value)
  // console.log(JSON.stringify(param.value))
  local.post(`/map/reviewsavedetail`, JSON.stringify(param.value)).then(success).catch(fail);
}

function savePlan(param, success, fail) {
  local.post(`/map/reviewsave`, param).then(success).catch(fail);
}

function findByContentId(param, success, fail) {
  console.log(param);
  local.get(`/map/findbycontentid`, { params: param }).then(success).catch(fail);
}

function findByUserId(param, success, fail) {
  console.log(param);
  local.get(`/map/findbyuserid`, { params: param }).then(success).catch(fail);
}

function planList(success, fail) {
  local.get(`/map/planlist`).then(success).catch(fail);
}

function planListByPlanId(planid, success, fail) {
  console.log(planid);
  local.get(`/map/planlistbyplanid/${planid}`).then(success).catch(fail);
}

function plansByPlanId(planid, success, fail) {
  console.log(planid);
  local.get(`/map/travelreviewboard/view/${planid}`).then(success).catch(fail);
}

function updatePlanArticleNo(param, success, fail) {
  console.log(param);
  local.post(`/map/updateplanarticleno`, param).then(success).catch(fail);
}

export {
  listSido,
  listGugun,
  listAttractions,
  searchAttraction,
  savePlanDetail,
  savePlan,
  findByContentId,
  findByUserId,
  planList,
  plansByPlanId,
  planListByPlanId,
  updatePlanArticleNo,
};
