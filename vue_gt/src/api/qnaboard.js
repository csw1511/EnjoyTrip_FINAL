import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "/qna";

function listQnaArticle(param, success, fail) {
    local.get(`${url}`, { params: param }).then(success).catch(fail);
}

function detailQnaArticle(articleno, success, fail) {
    local.get(`${url}/${articleno}`).then(success).catch(fail);
}

function registQnaArticle(article, success, fail) {
    console.log("boardjs article", article);
    local.post(`${url}`, JSON.stringify(article)).then(success).catch(fail);
}

function getModifyQnaArticle(articleno, success, fail) {
    local.get(`${url}/modify/${articleno}`).then(success).catch(fail);
}

function modifyQnaArticle(article, success, fail) {
    local.put(`${url}`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteQnaArticle(articleno, success, fail) {
    local.delete(`${url}/${articleno}`).then(success).catch(fail);
}

export { listQnaArticle, detailQnaArticle, registQnaArticle, getModifyQnaArticle, modifyQnaArticle, deleteQnaArticle };
