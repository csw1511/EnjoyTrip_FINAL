<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter, } from "vue-router";
import { getImg } from "@/api/board";

const route = useRoute();
const router = useRouter();

// const articleno = ref(route.params.articleno);


const props = defineProps({ item: Object });



onMounted(() => {
  console.log("잘넘어옴 : ", props.item);
  getImage();
});

const path = ref("");


function getImage() {
  // 이미지를 불러오는 API 호출!!!
  console.log(props.item.saveFolder, props.item.originalFile, props.item.saveFile);
  getImg(
    props.item.saveFolder,
    props.item.originalFile,
    props.item.saveFile,
    ({ data }) => {
      // 이미지 불러오는 주소
      path.value = "http://localhost/board/getImg/" + props.item.saveFolder + "/" + props.item.originalFile + "/" + props.item.saveFile;
      console.log("ddd : ",path.value);
    },
    (error) => {
      console.log(error);
    }
  )
  
  
}

</script>

<template>
  <!-- 게시글의 이미지 출력-->
    <!-- main banner-->
    <img :src=path class="d-block w-100" alt="...">
    
</template>

<style scoped>
img {
            display: block;
            /* width: auto; */
            height: 50%;
        }
</style>
