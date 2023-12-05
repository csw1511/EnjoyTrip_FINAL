<script setup>

</script>

<template>
  <!-- main banner-->
  <div id="carouselExampleCaptions" class="carousel slide carousel-container" data-bs-ride="carousel">
    <div class="carousel-indicators">
      <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
      <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1" aria-label="Slide 2"></button>
      <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2" aria-label="Slide 3"></button>
    </div>
    <div class="carousel-inner">
      <div class="carousel-item active">
        <img src="@/assets/img/tbanner.jpg" class="d-block w-100" alt="...">
        <div class="carousel-caption d-none d-md-block">
          <h5>여행은 가고 싶지만, 
            계획은 하기 싫은 당신을 위한</h5>
        </div>
      </div>
      <div class="carousel-item">
        <img src="@/assets/img/jeju.jpg" class="d-block w-100" alt="...">
        <div class="carousel-caption d-none d-md-block">
          <h5>채형원님의 후기가 궁금하다면?</h5>
          <p>이미지를 클릭해 형원님의 여행 코스를 둘러보세요.</p>
        </div>
      </div>
      <div class="carousel-item">
        <img src="@/assets/img/busan.jpg" class="d-block w-100" alt="...">
        <div class="carousel-caption d-none d-md-block">
            <h5>임창균님의 후기가 궁금하다면?</h5>
            <p>이미지를 클릭해 창균님의 여행 코스를 둘러보세요.</p>
        </div>
      </div>
      <div class="carousel-item">
        <img src="@/assets/img/seoul.jpg" class="d-block w-100" alt="...">
        <div class="carousel-caption d-none d-md-block">
            <h5>최산님의 후기가 궁금하다면?</h5>
            <p>이미지를 클릭해 산님의 여행 코스를 둘러보세요.</p>
        </div>
      </div>
      <div class="carousel-item">
        <img src="@/assets/img/gang.jpg" class="d-block w-100" alt="...">
        <div class="carousel-caption d-none d-md-block">
            <h5>최산님의 후기가 궁금하다면?</h5>
            <p>이미지를 클릭해 산님의 여행 코스를 둘러보세요.</p>
        </div>
      </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
      <span class="visually-hidden">Next</span>
    </button>
  </div>
</template>

<style scoped>
.arousel-container {      /* 전체 케러셀 */
            /* width: 100%; */
            /* float: left; */
            /* position: relative; */
            /* left: 50%; */
            /* object-fit: cover; */
        }

.carousel-inner {
            width: auto;
            height: 600px; /* 이미지 높이 변경 */
        }

.carousel-item {
            /* width: auto; */
            height: 100%;
        }

.d-block {
            display: block;
            /* width: auto; */
            height: 100%;
        }

</style>