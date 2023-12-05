<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import VKakaoMap from "../common/VKakaoMap.vue";
import { planListByPlanId, plansByPlanId } from "@/api/map";
import { storeToRefs } from "pinia";
import { useAttrlistStore } from "@/stores/attrlist";

import { detailArticle, getModifyArticle, deleteArticle, getImg } from "@/api/board";
import Comment from "@/components/comments/CommentView.vue";
import BoardCarousel from "@/components/board/item/BoardCarousel.vue";
import { useMemberStore } from "@/stores/member";

const attrlistStore = useAttrlistStore();
const { attractions, selectedListPlan, findByContentIdList } = storeToRefs(attrlistStore);

const route = useRoute();
const router = useRouter();

const { planid } = route.params;
const planData = ref({});
const startAttr = ref({});

//후기게시글
const article = ref({});
const memberStroe = useMemberStore();
const userIdVal = memberStroe.userInfo.userId;

onMounted(() => {
  setTimeout(() => {
    console.log("플랜아이디", planid);
    planListByPlanId(planid, ({ data }) => {
      console.log("선택한 계획의 내용", data);
      planData.value = data;
      // planData.value.articleNo = 12;
      console.log(planData.value);

      if (planData.value.articleNo != 0) {
        getArticle();
      }
    });

    plansByPlanId(planid, ({ data }) => {
      console.log("선택한 계획의 여행지들", data);

      startAttr.value = data[0];
      attractions.value = data;
      // console.log(startAttr)
    });
  }, 100);

  const getArticle = () => {
    console.log("getArticle : ");
    // const { articleno } = route.params;
    console.log("게시글 상세 보기 API 호출 : ", planData.value.articleNo + "번글 얻으러 가자!!!");
    // API 호출

    detailArticle(
      planData.value.articleNo,
      ({ data }) => {
        console.log(data);
        article.value = data;
      },
      (error) => {
        console.log(error);
      }
    );
  };
});

const moveWrite = () => {
  // BoardModify.vue로 이동
  // let planid = planData.value.planId;
  // router.push({ name: "article-modify", params: { articleno } });
  router.push({ name: "reviewarticle-write", params: { planid } });
};

function moveList() {
  router.push({ name: "reviewarticle-list" });
}

function moveModify() {
  let articleno = planData.value.articleNo;
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
  let articleno = planData.value.articleNo;
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

<template>
  <!-- <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10 text-start">
        <div class="row my-2">
          <h2 class="text-secondary px-5">{{ planData.planId }}. {{ planData.title }}</h2>
        </div>
      </div>
      
      <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <p>
                <span class="fw-bold">{{ planData.userNickname }}</span> <br />
                <span class="text-secondary fw-light"> 시작일 : {{ planData.startDate }} 종료일 : {{ planData.endDate }} </span>
              </p>
            </div>
          </div>
      </div>
      <div class="text-secondary">
        {{ planData.content }}
      </div>
    </div>
  </div> -->

  <div class="plan-wrap">
    <div class="plan-map">
      <VKakaoMap :selectAttraction="startAttr" />
    </div>
    <div class="plan-course">
      <div>
        <div>
          <h3>{{ planData.title }}</h3>
        </div>
        <div>{{ planData.userNickname }}</div>
        <div>{{ planData.startDate }} ~ {{ planData.endDate }}</div>
        <div>{{ planData.content }}</div>
        <!-- <div>{{ planData }}</div> -->
      </div>
      <tr v-for="attr in attractions" :key="attr.planOrder" class="text-center">
        <td>{{ attr.planOrder }}</td>
        <td>{{ attr.title }}</td>
        <td>{{ attr.contentTypeName }}</td>
        <td>{{ attr.addr1 }}</td>
        <td>
          <img :src="attr.firstImage" alt="" style="height: 100px; width: 100px" />
        </td>
      </tr>
    </div>
  </div>

  <button
    type="button"
    class="btn btn-outline-primary btn-sm"
    id="btn-write"
    @click="moveWrite"
    v-if="planData.articleNo === 0"
  >
    글쓰기
  </button>
  <!-- 후기게시글 -->
  <div class="container" v-if="planData.articleNo != 0">
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
    <!-- <p class="comment-p">댓글</p> -->
    <hr class="justify-content-center" />
    <Comment @get-comment-count="getCommentsCount" :article-no="planData.articleNo"></Comment>
  </div>
</template>

<style scoped>
.plan-wrap {
  display: flex;
}
.plan-map {
  flex-basis: 45%;
}
.plan-course {
  flex-basis: 45%;
}

.plan-course {
    margin: 20px;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #f8f9fa; /* Light gray color */
  }

  /* Style for the h3 inside plan-course */
  .plan-course h3 {
    color: #007bff; /* Blue color */
  }

  /* Style for the inner divs inside plan-course */
  .plan-course > div {
    margin-bottom: 10px;
  }

  /* Style for the table rows */
  .plan-course tr {
    background-color: #fff; /* White color */
  }

  /* Style for the table cells */
  .plan-course td {
    border: 1px solid #ddd;
    padding: 8px;
    text-align: center;
  }

  /* Style for the images */
  .plan-course img {
    max-width: 100%;
    max-height: 100%;
  }

  /* Style for the text-center class */
  .text-center {
    text-align: center;
  }

  /* Style for the container */
  .container {
    margin: 20px auto;
  }

  /* Style for the header */
  h2 {
    color: #6c757d; /* Gray color */
    padding-left: 5px;
  }

  /* Style for the avatar image */
  .avatar {
    max-width: 50px;
    max-height: 50px;
    border-radius: 50%;
  }

  /* Style for the user info section */
  .clearfix {
    overflow: auto;
  }

  /* Style for the paragraph containing user info */
  p {
    margin-bottom: 0;
  }

  /* Style for the divider */
  .divider {
    border: 1px solid #dee2e6; /* Light gray color */
  }

  /* Style for the carousel container */
  .carousel-container {
    margin-top: 20px;
  }

  /* Style for the carousel controls */
  .carousel-control-prev,
  .carousel-control-next {
    width: 5%;
  }

  /* Style for the content section */
  .text-secondary {
    color: #6c757d; /* Gray color */
  }

  /* Style for the buttons */
  .btn {
    padding: 8px 16px;
    margin-bottom: 10px;
  }

  .btn-success {
    background-color: #28a745; /* Green color */
    color: white;
  }

  .btn-outline-danger {
    color: #dc3545; /* Red color */
    border: 1px solid #dc3545;
  }

  /* Style for the comment section */
  .comment-p {
    font-size: 1.5rem;
    font-weight: bold;
    color: #6c757d; /* Gray color */
    margin-top: 20px;
  }

  /* Style for the horizontal rule */
  hr {
    border-color: #6c757d; /* Gray color */
  }
</style>
