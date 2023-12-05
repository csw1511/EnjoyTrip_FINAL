import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "board/comment";

function addComment(articleNo, comment, success, fail) {
  console.log("commentjs comment", articleNo);
  // console.log("commentjs comment", comment.value.userId);
  // console.log("commentjs comment", comment.value.comment);
  // comment.value.articleNo = articleNo;
    
  console.log("commentjs comment", JSON.stringify(comment.value));
  local.post(`${url}/${articleNo}`, JSON.stringify(comment.value)).then(success).catch(fail);
}

function getCommentList(articleNo, success, fail) {
  console.log("vue API", articleNo)
  local.get(`${url}/${articleNo}`, { articleNo: articleNo }).then(success).catch(fail);
}

function updateComment(comment, success, fail) {
  console.log("commentjs comment :", comment);
  
  // console.log("commentjs comment", JSON.stringify(comment.value));
  local.put(`${url}`,comment).then(success).catch(fail);
}

function deleteComment(commentid, success, fail) {
  // console.log("vue deleteComment", articleno);
  console.log("vue deleteComment", commentid);
  local.delete(`${url}/${commentid}`).then(success).catch(fail);
}


// function addComment(articleno, comment, success, fail) {
//   console.log(articleno);

//   console.log("commentjs comment", comment.value.writer);
//   console.log("commentjs comment", comment.value.comment);
//   comment.value.articleNo = articleno;
    
//   console.log("commentjs comment", JSON.stringify(comment.value));
//   local.post(`${url}/${articleno}`, JSON.stringify(comment.value)).then(success).catch(fail);
// }

// function getCommentList(articleno, success, fail) {
//   console.log("vue API", articleno)
//   local.get(`${url}/${articleno}`, { articleno: articleno }).then(success).catch(fail);
// }

// function deleteQnaArticle(articleno, commentno, success, fail) {
//   local.delete(`${url}/${articleno}/${commentno}`).then(success).catch(fail);
// }

export { addComment, getCommentList, updateComment, deleteComment };
