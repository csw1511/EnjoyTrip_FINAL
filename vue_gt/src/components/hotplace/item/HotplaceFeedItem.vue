<template>
   <div class='feed-main'>
        <HotplaceFeedItems v-for="item in feed" :key="item.articleNo" :item="item"></HotplaceFeedItems>
    </div> 
</template>

<script setup>
import {ref, onMounted} from "vue"
import { useRoute, useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import HotplaceFeedItems from "./HotplaceFeedItems.vue";

const router = useRouter();
const route = useRoute();

// const memberStroe = useMemberStore();
// const userIdVal = memberStroe.userInfo.userId;
// const userIdVal = ref("");


const props = defineProps({ feed: Object });
const emit = defineEmits(['getFeedLength'])
console.log("ddd : ", props.feed.length);
onMounted(() => {
    // console.log(props.feed.length);
    getLength();
})

const getLength = () => {
    emit('getFeedLength', props.feed.length);
}
</script>

<style scoped></style>

