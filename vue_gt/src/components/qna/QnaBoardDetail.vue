<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailQnaArticle, deleteQnaArticle } from "@/api/qnaboard";
import Comment from "../comments/CommentView.vue";

const route = useRoute();
const router = useRouter();

// const articleno = ref(route.params.articleno);
const { articleno } = route.params;

const article = ref({});

onMounted(() => {
  getArticle();
});

const getArticle = () => {
  // const { articleno } = route.params;
  console.log(articleno + "번글 얻으러 가자!!!");
  // API 호출
  detailQnaArticle(
    articleno,
    ({ data }) => {
      console.log(data);
      article.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

function moveList() {
  router.push({ name: "qna-list" });
}

function moveModify() {
  router.push({ name: "qna-modify", params: { articleno } });
}

function onDeleteArticle() {
  // const { articleno } = route.params;
  console.log(articleno + "번글 삭제하러 가자!!!");
  // API 호출
  deleteQnaArticle(
    articleno,
    () => {

    },
    (error) => {
      console.log(error);
    }
  );
  location.href="/qna/qnalist"

}
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글보기</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <div class="row my-2">
          <h2 class="text-secondary px-5">{{ article.articleNo }}. {{ article.subject }}</h2>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img
                class="avatar me-2 float-md-start bg-light p-2"
                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
              />
              <p>
                <span class="fw-bold">{{ article.userName }}</span> <br />
                <span class="text-secondary fw-light"> {{ article.registerTime }} 조회 : {{ article.hit }} </span>
              </p>
            </div>
          </div>
          <div class="col-md-4 align-self-center text-end">댓글 : 17</div>
          <div class="divider mb-3"></div>
          <div class="text-secondary">
            {{ article.content }}
          </div>
          <div class="divider mt-3 mb-3"></div>
          <div class="d-flex justify-content-end">
            <button type="button" class="btn btn-outline-primary mb-3" @click="moveList">글목록</button>
            <button type="button" class="btn btn-outline-success mb-3 ms-1" @click="moveModify">글수정</button>
            <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="onDeleteArticle">글삭제</button>
          </div>
        </div>
      </div>
    </div>
    <Comment :article-no="articleno"></Comment>
  </div>
</template>

<style scoped></style>
