import { ref } from "vue";
import { defineStore } from "pinia";

export const useAttrlistStore = defineStore("attrlistStore", () => {
  const attractions = ref([]);
  const selectedListPlan = ref([]);
  const findByContentIdList = ref([]);
  return {
    attractions,
    selectedListPlan,
    findByContentIdList
  };
});
