<script setup>
import BoardFormItem from "../board/item/BoardFormItem.vue";
import QnaBoardFormItem from "./item/QnaBoardFormItem.vue";
</script>

<template>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                    <mark class="sky">qna글쓰기</mark>
                </h2>
            </div>
            <div class="col-lg-10 text-start">
                <QnaBoardFormItem type="regist" />
            </div>
        </div>
    </div>
</template>

<style scoped></style>
