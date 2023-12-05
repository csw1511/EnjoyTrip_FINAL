<script setup>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import {updateComment, deleteComment} from "@/api/comment";

const props = defineProps({ comment: Object});
const emit = defineEmits(['getComment']);

const route = useRoute();
const router = useRouter();
const isState = ref(true);

const memberStroe = useMemberStore();
const userIdVal = memberStroe.userInfo.userId;

const comment = ref({
  userId:props.comment.userId,
  comment:props.comment.comment,
  commentId:"",
  articleNo:"",
});


function onDeleteComment() {
  console.log(props.comment.commentId + "번 댓글 삭제하러 가자!!!");
  
  // API 호출
  deleteComment(props.comment.commentId,
  () => {
    // 새로 댓글리스트를 업데이트 하기 위해서 emit 호출하기
  emit('getComment');
  },
  (error) => {
      console.log(error)
    }
  );

  
}

function clickUpdate(){
  // isState 값 바꾸기 
  // console.log("clickUpdate 전  : "+isState.value)
  isState.value = !isState.value;
  // console.log("clickUpdate 후  : "+isState.value)
}

function onUpdateComment() {
  // 입력한 값으로 수정하기dqq
  console.log("here : ",comment.value);
  comment.value.commentId = props.comment.commentId;
  console.log("here cid : ",comment.value);
  
  let commentid = comment.value.commentId;
  console.log("commentid : ", commentid);

  // API 호출
  updateComment(comment.value,
    ({ data }) => {
      console.log(data);
      // isState 값 바꾸기
      isState.value = !isState.value;
      // 새로 댓글리스트를 업데이트 하기 위해서 emit 호출하기
      emit('getComment');

    },
    (error) => {
      console.log(error)
    }
  )


  // router.push({ name: "article-view", params: {articleno : props.comment.articleNo}});
}
</script>

<template>
  <div class="comment">
    <div class="comment-author">{{ props.comment.userNickname }}({{ props.comment.registerTime }})</div>
    <div v-if="isState">
      <div class="comment-body" >{{ props.comment.comment }}</div>
      <button type="button" class="btn btn-outline-success mb-3 ms-1" @click="clickUpdate" v-if="userIdVal === comment.userId ">댓글수정</button>
    </div>
    <div v-else>
      <form class="comment-form">
        <textarea v-model="comment.comment"></textarea>
        <button type="button" class="btn btn-outline-primary mb-3" @click="onUpdateComment" v-if="userIdVal === comment.userId">댓글 수정</button>
      </form>
    </diV>
    
    <button type="button" class="btn btn-outline-danger mb-3" @click="onDeleteComment" v-if="userIdVal === comment.userId">댓글 삭제</button>
  </div>
</template>

<style scoped>

</style>
