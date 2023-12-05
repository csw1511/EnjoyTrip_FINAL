import { createRouter, createWebHistory } from "vue-router";
import TheMainView from "../views/TheMainView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: TheMainView,
    },
    {
      path: "/board/:categoryname",
      name: "board",
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import("@/views/TheBoardView.vue"),
      redirect: { name: "article-list" },
      children: [
        {
          path: "list",
          name: "article-list",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "view/:articleno",
          name: "article-view",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "article-write",
          component: () => import("@/components/board/BoardWrite.vue"),
        },
        {
          path: "modify/:articleno",
          name: "article-modify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
        {
          path: "hotplace",
          name: "board-hotplace",
          component: () => import("@/components/hotplace/HotplaceList.vue"),
        },
      ],
    },
    {
      path: "/qna",
      name: "qna",
      component: () => import("../views/TheQnaBoardView.vue"),
      redirect: { name: "qna-list" },
      children: [
        {
          path: "qnalist",
          name: "qna-list",
          component: () => import("@/components/qna/QnaBoardList.vue"),
        },
        {
          path: "qnaview/:articleno",
          name: "qna-view",
          component: () => import("@/components/qna/QnaBoardDetail.vue"),
        },
        {
          path: "qnawrite",
          name: "qna-write",
          component: () => import("@/components/qna/QnaBoardWrite.vue"),
        },
        {
          path: "qnamodify/:articleno",
          name: "qna-modify",
          component: () => import("@/components/qna/QnaBoardModify.vue"),
        },
      ],
    },
    {
      path: "/member",
      name: "member",
      component: () => import("../views/TheMemberView.vue"),
      children: [
        {
          path: "login",
          name: "user-login",
          component: () => import("@/components/members/MemberLogin.vue"),
        },
        {
          path: "join",
          name: "user-join",
          component: () => import("@/components/members/MemberRegister.vue"),
        },
        {
          path: "mypage",
          name: "user-mypage",
          component: () => import("@/components/memberinfo/MemberMyPage.vue"),
        },
        {
          path: "modify/:userid",
          name: "user-modify",
          component: () => import("@/components/memberinfo/MemberModifyInfo.vue"),
        },
        {
          path: "modifyPassword",
          name: "user-modifyPw",
          component: () => import("@/components/memberinfo/MemberModifyPw.vue"),
        },
        {
          path: "delete",
          name: "user-delete",
          component: () => import("@/components/memberinfo/MemberDelete.vue"),
        },
      ],
    },
    {
      path: "/travel",
      name: "travel",
      component: () => import("@/views/TheTravelView.vue"),
    },
    {
      path: "/travel/:title",
      name: "travels",
      component: () => import("@/views/TheTravelView.vue"),
    },
    // {
    //   path: "/travelreviewboard",
    //   name: "travelreviewboard",
    //   component: () => import("@/views/TheReviewView.vue"),
    // },
    {
      path: "/board/travelreviewboard",
      name: "travelreviewboard",
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import("@/views/TheReviewView.vue"),
      redirect: { name: "reviewarticle-list" },
      children: [
        {
          path: "list",
          name: "reviewarticle-list",
          component: () => import("@/components/review/ReviewList.vue"),
        },
        {
          path: "view/:planid",
          name: "reviewarticle-view",
          component: () => import("@/components/review/ReviewDetail.vue"),
        },
        {
          path: "write/:planid",
          name: "reviewarticle-write",
          component: () => import("@/components/review/ReviewWrite.vue"),
        },
        {
          path: "modify/:articleno",
          name: "reviewarticle-modify",
          component: () => import("@/components/review/ReviewModify.vue"),
        },
      ],
    },
  ],
});

export default router;
