<template>
  <div class="header">
    <div class="back_icon">
      <i class="fa-solid fa-arrow-left" @click="goback()"></i>
    </div>
    <div class="logo__div">
      <router-link to="/">
        <span>
          <img src="@/assets/img/logotext.png" id="logotext" />
        </span>
      </router-link>
    </div>
    <div></div>
  </div>
</template>

<script setup>
import router from "@/router";
function goback() {
  console.log("ok?");
  router.go(-1);
}
</script>

<style scoped>
@media screen and (min-width: 412px) {
  .header {
    width: 412px;
  }
}
.header {
  max-width: 412px;

  background-color: var(--maincolor2);
  border-radius: 0px 0px 24px 24px;

  display: flex;
  text-align: center;
  height: 56px;
  position: fixed;
  width: 100%;
  z-index: 10;
  justify-content: space-between;
  align-items: center;
}
.logo__div {
  position: relative;
  right: 3vh;
  font-size: x-large;
  bottom: 1vh;
  width: fit-content;
}
.back_icon {
  padding-inline: 3%;
  z-index: 100;
}

#logo {
  width: 12%;
  height: auto;
  position: relative;
  right: 0.6vh;
  top: 1vh;
}

#logotext {
  width: 50%;
  height: auto;
  position: relative;
  left: 0.8vh;
  top: 1.5vh;
}

#text {
  color: black;
}
</style>
