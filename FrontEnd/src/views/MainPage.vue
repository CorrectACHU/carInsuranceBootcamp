<template>
  <div></div>
</template>

<script setup lang="ts">
import { authStore } from '@/stores/store'
import router from '@/router'
import { getTokenFromCookie, redirectByPersonRole } from '@/helpers/service'
import { getRoleFromCookie } from '@/helpers/service'

const personStore = authStore()

if (getTokenFromCookie() === '') {
  router.push({ name: 'login' })
} else {
  personStore.setIsLoggedIn(true)
  const role = (personStore.role = getRoleFromCookie())
  redirectByPersonRole(role)
}
</script>

<style scoped></style>
