import { localAxios, fileAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance
const fileLocal = fileAxios();  // file 업로드 관련 axios instance

const url = "/board";

function listArticle(param, success, fail) {
    console.log(param);
    local.get(`${url}`, {params : param}).then(success).catch(fail);
}

function detailArticle(articleno, success, fail) {
    console.log(articleno);
    local.get(`${url}/${articleno}`).then(success).catch(fail);
}

function registArticle(formData, success, fail) {
    // //파라메터로 formdata
    // for (let key of formData.keys()) {
    //     console.log(key);
    // }

    // // FormData의 value 확인
    // for (let value of formData.values()) {
    //     console.log(value);
    // }
    console.log("board.js에서 registArticle가 호출됨!!!");

    fileLocal.post(`${url}`, formData).then(success).catch(fail);
}
function getImg(saveFolder, originalFile, saveFile, success, fail) {
    console.log("api getImg ");
    local.get(`${url}/getImg/${saveFolder}/${originalFile}/${saveFile}`).then(success).catch(fail);
}


function getModifyArticle(articleno, success, fail) {
    local.get(`${url}/modify/${articleno}`).then(success).catch(fail);
}


function updateFavorite(articleno, favoritecount, success, fail) {
    console.log("api 호출 좋아요기능");
    console.log(articleno, ", ", favoritecount)
    local.get(`${url}/${articleno}/${favoritecount}`, favoritecount).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
    console.log(JSON.stringify(article))
    local.put(`${url}`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
    console.log(articleno);
    local.delete(`${url}/${articleno}`).then(success).catch(fail);
}

function listPlace(param, success, fail) {
    console.log(param);
    local.get(`${url}`, {params : param}).then(success).catch(fail);
}

export { listArticle, detailArticle, registArticle, getModifyArticle, modifyArticle, deleteArticle, updateFavorite, listPlace, getImg };
