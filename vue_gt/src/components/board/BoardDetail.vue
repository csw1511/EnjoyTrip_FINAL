<template>
  <div class="container">
    <div class="row justify-content-center">
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
                <span class="fw-bold">{{ article.userNickname }}</span> <br />
                <span class="text-secondary fw-light"> {{ article.registerTime }} 조회 : {{ article.hit }} </span>
              </p>
            </div>
          </div>
          <div class="col-md-4 align-self-center text-end">댓글 : {{ thisArticleCommentCount }}</div>
          <div class="divider mb-3"></div>
          <!-- 게시글의 이미지 출력-->
          <div id="carouselExampleCaptions" class="carousel slide carousel-container" data-bs-ride="carousel">
            <div class="carousel-indicators">
              <button
                type="button"
                data-bs-target="#carouselExampleCaptions"
                data-bs-slide-to="0"
                class="active"
                aria-current="true"
                aria-label="Slide 1"
              ></button>
              <button
                type="button"
                data-bs-target="#carouselExampleCaptions"
                data-bs-slide-to="1"
                aria-label="Slide 2"
              ></button>
              <button
                type="button"
                data-bs-target="#carouselExampleCaptions"
                data-bs-slide-to="2"
                aria-label="Slide 3"
              ></button>
            </div>
            <div class="carousel-inner">
              <!-- <div class="carousel-inner" v-for="(item, index) in article.fileInfos" :key="index"> -->
              <div
                :class="index === 0 ? 'carousel-item' : 'carousel-item active'"
                v-for="(item, index) in article.fileInfos"
                :key="index"
              >
                <BoardCarousel :item="item"></BoardCarousel>
              </div>
            </div>
            <button
              class="carousel-control-prev"
              type="button"
              data-bs-target="#carouselExampleCaptions"
              data-bs-slide="prev"
            >
              <span class="carousel-control-prev-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Previous</span>
            </button>
            <button
              class="carousel-control-next"
              type="button"
              data-bs-target="#carouselExampleCaptions"
              data-bs-slide="next"
            >
              <span class="carousel-control-next-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Next</span>
            </button>
          </div>
          <div class="text-secondary">
            {{ article.content }}
          </div>
          <div class="divider mt-3 mb-3"></div>
          <div>
            <div>
              <button type="button" class="btn mb-3" id="btn-list" @click="moveList">글목록</button>
            </div>
            <div class="d-flex justify-content-end">
              <button
                type="button"
                class="btn btn-success mb-3 ms-1"
                id="btn-modify"
                @click="moveModify"
                v-if="userIdVal === article.userId"
              >
                글수정
              </button>
              <button
                type="button"
                class="btn btn-outline-danger mb-3 ms-1"
                id="btn-delete"
                @click="onDeleteArticle"
                v-if="userIdVal === article.userId"
              >
                글삭제
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 댓글란 -->
    <p class="comment-p">댓글</p>
    <hr class="justify-content-center" />
    <Comment @get-comment-count="getCommentsCount" :article-no="articleno"></Comment>
  </div>
</template>
<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, getModifyArticle, deleteArticle, getImg } from "@/api/board";
import Comment from "@/components/comments/CommentView.vue";
import BoardCarousel from "@/components/board/item/BoardCarousel.vue";
import { useMemberStore } from "@/stores/member";

const route = useRoute();
const router = useRouter();

const { articleno } = route.params;

const article = ref({});
const memberStroe = useMemberStore();
const userIdVal = memberStroe.userInfo.userId;

onMounted(() => {
  getArticle();
});

// 로그인 여부 판단하기

const getArticle = () => {
  console.log("getArticle : ");
  // const { articleno } = route.params;
  console.log("게시글 상세 보기 API 호출 : ", articleno + "번글 얻으러 가자!!!");
  // API 호출
  detailArticle(
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
  router.push({ name: "article-list" });
}

function moveModify() {
  // API 호출
  getModifyArticle(
    articleno,
    ({ data }) => {
      console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );

  // BoardModify.vue로 이동
  router.push({ name: "article-modify", params: { articleno } });
}

function onDeleteArticle() {
  // const { articleno } = route.params;
  console.log(articleno + "번글 삭제하러 가자!!!");
  // API 호출
  deleteArticle(articleno);
  router.push({ name: "article-list" });
}

const thisArticleCommentCount = ref(0);

const getCommentsCount = function (count) {
  console.log("emit ghcnfd");
  console.log(count);
  thisArticleCommentCount.value = count;
};
</script>

<style scoped>
.comment-p {
  text-align: center;
}
#btn-list {
  width: 100px;
  background-color: white;
  border-color: #d9d9d9;
  border-radius: 10px;
  color: #1db954;
  font-weight: 20px;
  box-shadow: 1px 1px 1px #d9d9d9;
  float: left;
}
#btn-list:hover {
  background-color: #1db954;
  color: white;
}
#btn-modify {
  width: 100px;
  background-color: #1db954;
  border-color: #d9d9d9;
  border-radius: 10px;
  color: white;
  font-weight: 20px;
  box-shadow: 1px 1px 1px #d9d9d9;
}
#btn-modify:hover {
  background-color: white;
  color: #1db954;
}
#btn-delete {
  width: 100px;
  background-color: #cc0000;
  border-color: #d9d9d9;
  border-radius: 10px;
  color: white;
  font-weight: 20px;
  box-shadow: 1px 1px 1px #d9d9d9;
}
#btn-delete:hover {
  background-color: white;
  color: #cc0000;
  font-weight: 20px;
}

.carousel-container {
  width: 60%;
  height: 30%;
}
.carousel-inner {
  width: auto;
  height: 40%; /* 이미지 높이 변경 */
}

.carousel-item {
  /* width: auto; */
  height: 40%;
}
</style>
