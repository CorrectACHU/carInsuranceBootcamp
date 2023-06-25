<template>
  <header>
    <v-toolbar class="toolbarSize" color="blue">
      <img src="/static/images/logo1.png" alt="" @click="toMainPage" />
      <v-btn class="btnLogout" v-if="personStore.isLoggedIn" variant="text" @click="logout">
        logOut
      </v-btn>
    </v-toolbar>
  </header>
</template>

<script setup lang="ts">
import { cleanCookie } from '@/helpers/service'
import router from '@/router'
import { authStore } from '@/stores/store'
const personStore = authStore()

const logout = () => {
  personStore.changeIsLoggedInToFalse()
  cleanCookie()
  router.push('/login')
}
const toMainPage = () => {
  router.push('/')
}
</script>

<style scoped>
.toolbarSize {
  height: 10vh;
}
img {
  min-width: 40dvw;
  max-height: 14dvw;
  margin-top: 4dvh;
}

img:hover {
  background-color: rgb(255, 109, 65);
  cursor: pointer;
}

.btnLogout {
  color: rgb(151, 55, 101);
  background-color: azure;
  margin-left: 45dvw;
  margin-top: 3dvh;
}

.btnLogout:hover {
  background-color: rgb(255, 109, 65);
  color: white;
}
</style>
