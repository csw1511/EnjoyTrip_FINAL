<template>
  <div class="comments-container">
    <p>댓글</p>
    <CommentDetail  @get-comment="getComment" v-for="comment in comments" :key="comment.comment" :comment="comment"></CommentDetail>
    <form @submit.prevent="onSubmit" v-if="userIdVal!=''" class="comment-form">
      <textarea v-model="comment.comment" placeholder="댓글을 입력하세요..."></textarea>
      <button type="submit" class="btn btn-outline-primary mb-3" @click="writeComment">댓글 작성</button>
    </form>
  </div>
</template>

<script setup>
import CommentDetail from "./CommentDetail.vue";
import { useRoute, useRouter } from "vue-router";
import { addComment, getCommentList } from "@/api/comment";
import { onMounted, ref } from "vue";
import { useMemberStore } from "@/stores/member";

const emit = defineEmits(['getCommentCount'])
const props = defineProps({ articleNo: Number });

const route = useRoute();
const router = useRouter();

const memberStroe = useMemberStore();
const userIdVal = memberStroe.userInfo.userId;

const comment = ref({
  userId:userIdVal,
  comment:"",
  articleNo: props.articleNo,
  commentCount:"",
})

const comments = ref({});
const commetCount = ref("");

onMounted(() => {
  getComment();
});
const getCommentsCount = () => {
  console.log("dd : ", commetCount.value);
  emit('getCommentCount(commetCount.value)')
}

const getComment = () => {
  console.log("dffdfd : ", userIdVal);
  console.log(props.articleNo+"번 글의 댓글을 얻어오자");
  getCommentList(
    props.articleNo,
    ({ data }) => {
      console.log("api 호출 뒤 댓글 얻어옴 아래에는data 출력");
      console.log(data);
      comments.value = data;
      commetCount.value = comments.value.length;
      getCommentsCount();
    },
    (error) => {
      console.log(error);
    }
  );
};

function writeComment() {
  console.log("댓글등록하자!!", props.articleNo);
  console.log("내용 : ",comment);
  // API 호출
  addComment(
    props.articleNo,
    comment
    ,
    ({ data }) => {
      console.log(data);
      comments.value = data;
      // 댓글 작성 란 초기화
      comment.value.comment = "";
      getComment();
    },
    (error) => {
      console.log(error);
    }
    );
    
  // 경로지정다시하기
  // 이렇게 하면, articleNo 페이지로 이동
  // router.push({name:"article-view", params: { articleno: props.articleNo }});
  // router.push({ name: "article-view", params: {articleno : props.articleNo}});
  // getComment();
}
</script>



<style scoped>
body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
  background-color: #f4f4f4;
}

.comments-container {
  width: 60%;
  margin: 50px auto;
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.comment {
  margin-bottom: 20px;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.comment-author {
  font-weight: bold;
  color: #333;
}

.comment-body {
  margin-top: 10px;
  color: #555;
}

.comment-form {
  margin-top: 20px;
}

.comment-form textarea {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  resize: none;
}

.comments-container {
    margin-top: 20px;
  }

  .comments-container p {
    font-size: 1.5rem;
    font-weight: bold;
    color: #6c757d; 
  }

  .comment-form {
    margin-top: 10px;
  }

  .comment-form textarea {
    width: 100%;
    padding: 8px;
    margin-bottom: 10px;
    resize: vertical;
  }

  .comment-form button {
    padding: 8px 16px;
    color: #007bff; 
    border: 1px solid #007bff;
    background-color: white;
    border-radius: 4px;
    cursor: pointer;
  }

  .comment-detail {
    margin-top: 10px;
    padding: 10px;
    border: 1px solid #dee2e6; 
    border-radius: 4px;
  }
</style> 
