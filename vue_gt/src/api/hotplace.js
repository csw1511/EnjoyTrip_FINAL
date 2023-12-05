import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "/board/hotplace";

function registHotplace(hotplaceContent, sucess, fail) {
    console.log("registHotplace : ", hotplaceContent);
    local.post(`${url}`, hotplaceContent).then(sucess).catch(fail);
}

function listPlace(success, fail) {
    console.log("listPlace 호출 - listPlace");
    local.get(`${url}`).then(success).catch(fail);
}
function getHotplace(contentId, success, fail) {
    console.log("getHotplace 호출 : ", contentId);
    local.get(`${url}/detail/${contentId}`).then(success).catch(fail);
}
function updateHit(articleno, success, fail) {
    console.log("updatehit : ", articleno)
    local.get(`${url}/${articleno}`).then(success).catch(fail);
}

function checkFavorite(articleno, userId, success, fail) {
    console.log("checkFavorite : ", articleno, userId)
    local.get(`${url}/${articleno}/${userId}`).then(success).catch(fail);
}

function addFavorite(articleno, userId, success, fail) {
    console.log("checkFavorite : ", articleno, userId)
    local.post(`${url}/${articleno}/${userId}`).then(success).catch(fail);
}

function deleteFavorite(articleno, userId, success, fail) {
    console.log("checkFavorite : ", articleno, userId)
    local.delete(`${url}/${articleno}/${userId}`).then(success).catch(fail);
}



export { listPlace, getHotplace, updateHit, checkFavorite, addFavorite, deleteFavorite, registHotplace};

