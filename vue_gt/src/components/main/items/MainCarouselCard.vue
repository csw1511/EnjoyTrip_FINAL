<template>
  <div class="carousel-item active">
    <!-- <router-link :to="{ name: 'article-view', params: { planId: rplace.planId } }" class="article-title link-dark"> -->
    <img v-if="props.rplace.firstImage != ''" :src="props.rplace.firstImage" class="d-block w-100" alt="..." />
    <img v-else src="@/assets/img/emptygreen.jpg" class="d-block w-100" alt="..." />
    <!-- </router-link> -->

    <div class="carousel-caption d-none d-md-block">
      <div class="carousel-caption d-none d-md-block">
        <h5>{{ props.rplace.userId }}님의 코스가 궁금하다면?</h5>
        <p>이미지를 클릭해 {{ props.rplace.userId }}님의 여행 코스를 확인하세요.</p>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({ rplace: Object });
console.log(props.rplace);
</script>

<style coped></style>
