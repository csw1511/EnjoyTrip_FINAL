<script setup>
import { ref, onMounted } from "vue";
import VKakaoMap from "../../common/VKakaoMap.vue";
import {planList} from "@/api/map"
import { storeToRefs } from "pinia";
import { useAttrlistStore } from "@/stores/attrlist"
const attrlistStore = useAttrlistStore();
const { attractions, selectedListPlan, findByContentIdList } = storeToRefs(attrlistStore);

onMounted(() => {
  planList(
    ({ data }) => {
      console.log("계획", data);
    }
  );
});
</script>

<template>
  <div>
    hello
    <!-- <VKakaoMap/> -->
  </div>
</template>

<style scoped>

</style>