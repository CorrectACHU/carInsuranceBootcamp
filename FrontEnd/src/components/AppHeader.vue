<template>
  <header>
    <v-toolbar class="toolbarSize" color="blue">
      <v-btn 
      class=""
      variant="text"
      @click="logout">
        LogOut
      </v-btn>
    </v-toolbar>
  </header>
</template>

<script setup lang="ts">
import router from '@/router';


const logout = () => {
  document.cookie = 'token'+'=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
  router.push('/login')
}

</script>

<style scoped>
.toolbarSize {
  height: 10vh;
}
</style>
