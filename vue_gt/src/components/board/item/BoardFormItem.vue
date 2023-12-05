<script setup>
import { ref, watch, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { jwtDecode } from "jwt-decode";
import { registArticle, modifyArticle, detailArticle } from "@/api/board";

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });
const emit = defineEmits(["reGetArticle"]);

const isUseId = ref(false);

const userIdVal = ref("");

const files = ref([]);

onMounted(() => {
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
  categoryName: route.params.categoryname,
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
const subjectErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => article.value.subject,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = "제목을 확인해 주세요!!!";
    } else subjectErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => article.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = "내용을 확인해 주세요!!!";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

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
  // 작성할 글 정보와 업로드할 파일을 formdata 에 담기

  // 1.파일 정보
  // let files = event.target.files;
  // console.log(files);
  // for(let i=0; i<files.length; i++) {
  //     formData.append('images', files[i]);
  // }
  // 2.글 정보
  formData.append("article", JSON.stringify(article.value));

  // for (let key of formData.keys()) {
  //   console.log(key);
  // }

  // FormData의 value 확인
  for (let value of formData.values()) {
    console.log(value);
  }

  // API 호출
  registArticle(
    formData,
    ({ data }) => {
      console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );
  console.log("이제 움직이러가자");

  emit("reGetArticle");
  moveList();
}

function updateArticle() {
  console.log(article.value.articleNo + "번글 수정하자!!", article.value);

  // API 호출
  modifyArticle(
    article.value,
    ({ data }) => {
      console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );
  router.push({ name: "article-view", params: route.params.articleno });
}

function moveList() {
  router.push({ name: "article-list" });
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
      <button type="submit" class="btn btn-outline-success mb-3" v-else @click="updateArticle">글수정</button>
      <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="moveList">목록으로이동...</button>
    </div>
  </form>
</template>

<style scoped>
p {
  margin-top: 20px;
}
</style>

<style lang="scss">
// .container {
//   min-height: 300px;
//   width: 500px;
//   margin: 0 auto;
// }
// .file-upload {
//   display: flex;
//   justify-content: center;
//   align-items: center;
//   width: 100%;
//   height: 100%;
//   border: transparent;
//   border-radius: 20px;
//   cursor: pointer;
//   &.dragged {
//     border: 1px dashed powderblue;
//     opacity: .6;
//   }
//   &-container {
//     height: 300px;
//     padding: 20px;
//     margin: 0 auto;
//     box-shadow: 0 0.625rem 1.25rem #0000001a;
//     border-radius: 20px;
//   }
//   &-input {
//     display: none;
//   }
//   &-list {
//     margin-top: 10px;
//     width: 100%;
//     &__item {
//       padding: 10px;
//       display: flex;
//       justify-content: space-between;
//       align-items: center;
//       &__data {
//         display: flex;
//         align-items: center;
//         &-thumbnail {
//           margin-right: 10px;
//           border-radius: 20px;
//           width: 120px;
//           height: 120px;
//         }
//       }
//       &__btn-remove {
//         cursor: pointer;
//         border: 1px solid powderblue;
//         display: flex;
//         justify-content: center;
//         align-items: center;
//         padding: 5px;
//         border-radius: 6px;
//       }
//     }
//   }
// }
</style>
