<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { listArticle } from "@/api/board";
import { useMemberStore } from "@/stores/member";

import VSelect from "@/components/common/VSelect.vue";
import BoardListItem from "@/components/board/item/BoardListItem.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";
const router = useRouter();
const route = useRoute();

console.log("다시 로드 게시판")
const categoryname = ref(route.params.categoryname);
console.log("이동방향 : ", categoryname.value);
const memberStroe = useMemberStore();
const userIdVal = memberStroe.userInfo.userId;

const selectOption = ref([
  { text: "검색조건", value: "" },
  { text: "글번호", value: "article_no" },
  { text: "제목", value: "subject" },
  { text: "작성자아이디", value: "user_id" },
]);

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: "",
  word: "",
  categoryname: categoryname.value,
});


onMounted(() => {
  getArticleList();
});

const changeKey = (val) => {
  console.log("BoarList에서 선택한 조건 : " + val);
  param.value.key = val;
};

const getArticleList = () => {
  console.log("조상에서 확인 : ", userIdVal.value);
  console.log("서버에서 글목록 얻어오자!!!", param.value);
  // API 호출
  listArticle(
    param.value,
    ({ data }) => {
      console.log(data);
      articles.value = data.articles;
      currentPage.value = data.currentPage;
      totalPage.value = data.totalPageCount;
    },
    (error) => {
      console.log(error);
    }
  );
};

function replace() {
  router.go(0);
}

const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!");
  currentPage.value = val;
  param.value.pgno = val;
  getArticleList();
};

const moveWrite = () => {
  router.push({ name: "article-write", params: categoryname.value });
};
</script>

<template>
  <div class="container">
    <p class="category-name">게시판 >>
      <router-link class="category-router" :to="{ name: 'board', params: { categoryname: categoryname  } } " @click="replace">{{ categoryname }}</router-link>
    </p>
    <div class="row justify-content-center">
      <!-- <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글목록</mark>
        </h2>
      </div> -->
      <div class="col-lg-10">
        <div class="row align-self-center mb-2">
          <div class="col-md-2 text-start">
            <button type="button" class="btn btn-outline-primary btn-sm" id="btn-write" @click="moveWrite" v-if="userIdVal != ''">
              글쓰기
            </button>
          </div>
          <div class="col-md-5 offset-5">
            <form class="d-flex">
              <VSelect :selectOption="selectOption" @onKeySelect="changeKey" />
              <div class="input-group input-group-sm">
                <input type="text" class="form-control" v-model="param.word" placeholder="검색어..." />
                <button class="btn btn-dark" type="button" @click="getArticleList">검색</button>
              </div>
            </form>
          </div>
        </div>
        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col">글번호</th>
              <th scope="col">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">조회수</th>
              <th scope="col">작성일</th>
            </tr>
          </thead>
          <tbody>
            <BoardListItem @re-get-article="getArticleList" v-for="article in articles" :key="article.articleNo" :article="article" ></BoardListItem>
          </tbody>
        </table>
      </div>
      
      <PageNavigation :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></PageNavigation>
    </div>
  </div>
</template>

<style scoped>
th{
  background-color: #1DB954;
  color: white;
}
.category-name{
  text-align: left;
  color: #6D6D6D;
  margin-bottom: 30px;
}
.category-router{
  text-decoration: none;
  color: #6D6D6D;
}
.category-router:hover{
  text-decoration: none;
  color: #1DB954;
}


#btn-write{
  width: 90px;
  height: 35px;
  background-color: white;
  border-color: #d9d9d9;
  border-style: solid;
  border-radius: 10px;
  color: #1DB954;
  font-weight: 20px;
  box-shadow: 1px 1px 1px #d9d9d9;
  float: left;
}
#btn-write:hover{
  background-color: #1DB954;
  color: white;
}
</style>
