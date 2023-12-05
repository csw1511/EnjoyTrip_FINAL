<!-- 
<script>
import draggable from "vuedraggable";
let id = 1;
export default {
  name: "simple",
  display: "Simple",
  order: 0,
  components: {
    draggable
  },
  data() {
    return {
      enabled: true,
      list: [
        { name: "John", id: 0 },
        { name: "Joao", id: 1 },
        { name: "Jean", id: 2 }
      ],
      dragging: false
    };
  },
  computed: {
    draggingInfo() {
      return this.dragging ? "under drag" : "";
    }
  },
  methods: {
    add: function() {
      this.list.push({ name: "Juan " + id, id: id++ });
    },
    replace: function() {
      this.list = [{ name: "Edgard", id: id++ }];
    },
    checkMove: function(e) {
      window.console.log("Future index: " + e.draggedContext.futureIndex);
    }
  }
};
</script> -->

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { savePlanDetail, savePlan } from "@/api/map";
import { storeToRefs } from "pinia";
import { jwtDecode } from "jwt-decode";
import { useAttrlistStore } from "@/stores/attrlist";
import { useMemberStore } from "@/stores/member"

const attrlistStore = useAttrlistStore();
const memberStore = useMemberStore();
const { isLogin } = storeToRefs(memberStore);
const { attractions, selectedListPlan } = storeToRefs(attrlistStore);

const router = useRouter();

const deleteAttrInPlan = (selected) => {
  console.log("삭제할", selected.isUsed);
  selected.isUsed = false;
};

onMounted(() => {
  isMember();
});

// 로그인 여부 판단하기
function isMember() {
  console.log("현재 로그인 한 여부를 판단하자!!! (미출력은 로그인안한 경우)")
  let token = sessionStorage.getItem("accessToken");
  console.log(token)
  if (token != null) {
    // 로그인한 상태(여기서부터 로그인여부를 판단해야함.)
    let decodeToken = jwtDecode(token);
    plan.value.userId = decodeToken.userId;
    console.log("현재 로그인한 회원의 아이디 : ", plan.value.userId);
  } else {
    alert("로그인 후 이용해 주십시오")
    router.push("/")
  }
}

const writePlan = () => {
  console.log(isLogin.value);
  if (isLogin) {
    savePlan(
      plan.value,
      () => {
        savePlanDetail(selectedListPlan);
      },
      (err) => {
        console.log(err);
      }
    );
  }
};

const plan = ref({
  userId: "",
  title: "",
  startDate: "",
  endDate: "",
  content: "",
});
</script>

<template>
  <div>
    <!-- Button trigger modal -->

    <!-- Modal -->
    <div
      class="modal fade"
      id="writePlan"
      data-bs-backdrop="static"
      data-bs-keyboard="false"
      tabindex="-1"
      aria-labelledby="writePlanLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="writePlanLabel">Modal title</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label for="title" class="col-form-label">계획 제목</label>
              <input type="text" class="form-control" id="title" v-model="plan.title" />
            </div>
            <div class="mb-3">
              <label for="start-date" class="col-form-label">출발일</label>
              <input type="date" class="form-control" id="start-date" v-model="plan.startDate" />
              <label for="end-date" class="col-form-label">도착일</label>
              <input type="date" class="form-control" id="end-date" v-model="plan.endDate" />
            </div>
            <div class="mb-3">
              <label for="content" class="col-form-label">계획 메모 내용</label>
              <textarea class="form-control" id="content" v-model="plan.content"></textarea>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            <button type="button" class="btn btn-primary" @click="writePlan">계획 작성하기</button>
          </div>
        </div>
      </div>
    </div>
    

    <table class="table table-hover">
      <thead>
        <tr class="text-center">
          <th scope="col">관광지명</th>
          <th scope="col">관광타입</th>
          <th scope="col">위치</th>
          <th scope="col">이미지</th>
          <th scope="col">삭제</th>
        </tr>
      </thead>
      <!-- <draggable tag="tbody" v-model="selectedListPlan"> -->
        <tbody>
        <template v-for="selected in selectedListPlan" :key="selected.contentId">
          <tr v-if="selected.isUsed">
            <!-- <td>{{ selected }}</td> -->

            <td>{{ selected.title }}</td>
            <td>{{ selected.contentTypeName }}</td>
            <td>{{ selected.addr1 }}</td>
            <td>
              <img :src="selected.firstImage" alt="" style="height: 100px; width: 100px" />
            </td>
            <td>
              <button @click="deleteAttrInPlan(selected)">X</button>
            </td>
          </tr>

          <input type="text" v-model="selected.memo" />
        </template>
      </tbody>
      <!-- </draggable> -->
    </table>
  </div>

  <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#writePlan">계획 작성</button>
  
</template>

<style scoped></style>
