<template>
  <div v-if="props.feedbackArticle" id="feedback_article">
    <p>제목 : {{ props.feedbackArticle.title }}</p>
    <p>작성자 : {{ props.feedbackArticle.nickname }}</p>
    <p>시작 시간 : {{ props.feedbackArticle.startTime }}</p>
    <p>종료 시간 : {{ props.feedbackArticle.endTime }}</p>
    <button
      v-if="member.role === 'HELPER'"
      @click="acceptMeeting(props.feedbackArticle)"
      id="accept-button"
    >
      수락
    </button>
    <font-awesome-icon
      v-if="member.role === 'APPLICANT'"
      icon="fa-solid fa-xmark"
      @click="deleteFeedbackArticle"
      id="delete-article"
    />

    <FeedbackCreateModal v-if="createModal" />
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";
import { apiInstance } from "@/api/index";
import { useMember } from "@/store/Member";
import { ref } from "vue";
import FeedbackCreateModal from "./FeedbackCreateModal.vue";
import Swal from "sweetalert2";

const memberStore = useMember();
const { accessToken, member } = memberStore;
const api = apiInstance();
const props = defineProps(["feedbackArticle", "index"]);
const emit = defineEmits(["updateProps"]);
const meetingSaveReq = ref({
  feedbackBoardId: undefined,
  title: undefined,
  applicantId: undefined,
  startTime: undefined,
  endTime: undefined,
});

const deleteFeedbackArticle = () => {
  Swal.fire({
    title: "삭제 하시겠습니까?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Yes",
  }).then((result) => {
    if (result.isConfirmed) {
      api
        .delete(
          `/api/feedback-boards/${props.feedbackArticle.feedbackBoardId}`,
          {
            headers: {
              "access-token": accessToken,
            },
          }
        )
        .then(() => {
          emit("updateProps", { index: props.index });
          Swal.fire("삭제되었습니다", "success");
        })
        .catch((err) => {
          err;
          console.log(err);
        });
    }
  });
};

const acceptMeeting = (feedbackArticle) => {
  meetingSaveReq.value.feedbackBoardId = feedbackArticle.feedbackBoardId;
  meetingSaveReq.value.title = feedbackArticle.title;
  meetingSaveReq.value.applicantId = feedbackArticle.writerId;
  meetingSaveReq.value.startTime = feedbackArticle.startTime;
  meetingSaveReq.value.endTime = feedbackArticle.endTime;

  api
    .post(`/api/meetings`, meetingSaveReq.value, {
      headers: {
        "access-token": accessToken,
      },
    })
    .then(() => {
      alert("상담 수락 완료");
      emit("updateProps", { index: props.index });
    })
    .catch((err) => {
      console.log(err);
    });
};
</script>

<style lang="scss" scoped>
#accept-button {
  float: right;
  margin-right: 3%;
  position: relative;
  top: -50px;
}

#feedback_article {
  /* feedbackReqDetail_Volunteer_component */

  box-sizing: border-box;

  /* Auto layout */

  flex-direction: column;
  justify-content: center;
  align-items: flex-end;
  padding: 10px;

  margin-left: 4%;
  margin-right: 2%;
  margin-bottom: 5%;
  /* Black/200 */
  border-right: 2px solid #f1f1f1;
  border-bottom: 2px solid #f1f1f1;
  border-left: 1px solid #f1f1f1;
  border-top: 1px solid #f1f1f1;

  filter: drop-shadow(0px 4px 4px rgba(0, 0, 0, 0.25));
  border-radius: 16px;

  /* Inside auto layout */

  flex: none;
  order: 0;
  flex-grow: 0;

  // background color
  background-color: var(--black1);
}

#delete-article {
  float: right;
  margin-right: 2%;
  position: relative;
  top: -145px;
}
</style>
