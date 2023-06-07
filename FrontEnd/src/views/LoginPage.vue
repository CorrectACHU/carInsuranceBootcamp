<template>
  <div>
    <AppHeader />
    <main>
      <v-sheet className="bg-light-blue-lighten-5 sheetSize">
        <LoginForm />
      </v-sheet>
    </main>
    <AppFooter />
  </div>
</template>

<script setup lang="ts">
import AppHeader from '@/components/HeaderComponent.vue'
import AppFooter from '@/components/FooterComponent.vue'
import LoginForm from '@/components/LoginForm.vue'
</script>

<style scoped>
.sheetSize {
  height: 80vh;
  padding-top: 20vh;
}
</style>
