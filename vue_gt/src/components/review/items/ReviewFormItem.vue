<script setup>
import { ref, watch, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { jwtDecode } from "jwt-decode";
import { updatePlanArticleNo } from "@/api/map";
import { registArticle, modifyArticle, detailArticle } from "@/api/board";

const router = useRouter();
const route = useRoute();
const props = defineProps({ type: String });
const isUseId = ref(false);
const userIdVal = ref("");

const planId = route.params.planid;

const updateparam = ref({ articleNo: "", planId: "" });

onMounted(() => {
  console.log(planId);
  console.log("게시글 입력 화면");
  console.log(props.type);
  let tmp = sessionStorage.getItem("accessToken");
  if (tmp != null) {
    userIdVal.value = jwtDecode(tmp).userId;
  }
});

const article = ref({
  articleNo: route.params.articleno,
  subject: "",
  content: "",
  userId: userIdVal,
  // hit: 0,
  // registerTime: "",
  categoryName: "여행후기게시판",
  // fileInfos: {saveFolder:"", originalFile:"", saveFile:""}
});

if (props.type === "modify") {
  let { articleno } = route.params;
  console.log(articleno + "번글 얻어와서 수정할거야");
  // API 호출
  isUseId.value = true;
  console.log(article.value.articleNo);
  onMounted(() => {
    detailArticle(
      article.value.articleNo,
      ({ data }) => {
        console.log(data);
        article.value.subject = data.subject;
        article.value.content = data.content;
      },
      (error) => {
        console.log(error);
      }
    );
  });
}

const src = ref([]);
const formData = new FormData();
// // 등록 버튼 눌렀을때로 보내기
var fileList = [];
const selectImage = (event) => {
  console.log(event);

  let files = event.target.files;
  console.log(files);
  // let newList = [];
  for (let i = 0; i < files.length; i++) {
    formData.append("images", files[i]);

    fileList.push(URL.createObjectURL(files[i]));
  }
  src.value = fileList;
};
const handleRemove = (index) => {
  console.log("hrer");
  console.log(index);
  fileList.splice(index);
  console.log(fileList);
  // 삭제 안됨
  console.log(formData[index].get("images"));
};

function writeArticle() {
  console.log("글등록하자!!", article.value);
  formData.append("article", JSON.stringify(article.value));
  for (let value of formData.values()) {
    console.log(value);
  }
  // API 호출
  registArticle(
    formData,
    ({ data }) => {
      console.log(data.articleNo);
      console.log(planId);
      updateparam.value.articleNo = data.articleNo;
      updateparam.value.planId = planId;

      updatePlanArticleNo(updateparam.value);
      // updatePlanArticleNo({ why: "why" });
    },
    (error) => {
      console.log(error);
    }
  );
  console.log("이제 움직이러가자");
  moveList();
}
function updateArticle() {}
function moveList() {
  router.push({ name: "reviewarticle-list" });
}
</script>

<template>
  <form @submit.prevent="onSubmit" enctype="multipart/form-data">
    <!-- <div class="mb-3">
      <label for="userid" class="form-label">작성자 ID : </label>
      <input type="text" class="form-control" v-model="article.userId" :disabled="true" placeholder="작성자ID..." />
    </div> -->
    <div class="mb-3">
      <label for="subject" class="form-label">제목 : </label>
      <input type="text" class="form-control" v-model="article.subject" placeholder="제목..." />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <textarea class="form-control" v-model="article.content" rows="10"></textarea>
      <div class="custom-file">
        <p>\파일 업로드:</p>
        <input multiple @change="selectImage" ref="files" type="file" />
      </div>

      <!-- 업로드된 리스트 -->
      <div class="file-upload-list">
        <div class="file-upload-list__item" v-for="(item, index) in src" :key="index">
          <div class="file-upload-list__item__data">
            <img class="file-upload-list__item__data-thumbnail" :src="item" />
            <div class="file-upload-list__item__data-name">
              {{ item.name }}
            </div>
          </div>
          <div class="file-upload-list__item__btn-remove" @click="handleRemove(index)">삭제</div>
        </div>
      </div>

      <!-- <div >
        <img class="upload-image" v-for="(item, index) in src" :key="index" :src="item" />

      </div> -->
    </div>
    <div class="col-auto text-center">
      <button type="submit" class="btn btn-outline-primary mb-3" v-if="type === 'regist'" @click="writeArticle">
        글작성
      </button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else @click="writeArticle">글수정</button>
      <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="moveList">목록으로이동...</button>
    </div>
  </form>
</template>

<style scoped></style>
