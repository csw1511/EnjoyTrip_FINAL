<script setup>
import { RouterView } from "vue-router";
import TheHeadingNavBar from "./components/layout/TheHeadingNavBar.vue";
import TheSearchBar from "./components/layout/TheSearchBar.vue";
import TheFooter from "./components/layout/TheFooter.vue";

</script>

<template>
    <TheHeadingNavBar></TheHeadingNavBar>
    <TheSearchBar></TheSearchBar>
    <div class="body d-flex flex-column min-vh-100">
        <router-view></router-view>
    </div>
    <TheFooter></TheFooter>
</template>

<style scoped>
/* .body {
    padding-top: 150px;
} */
</style>
