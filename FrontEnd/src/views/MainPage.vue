<template>
    <div>

    </div>
</template>

<script setup lang="ts">
import { authStore } from '@/stores/store'
import router from '@/router'
import { getTokenFromCookie, redirectByPersonRole } from '@/helpers/service'
import { getRoleFromCookie } from '@/helpers/service'

const personStore = authStore()

if (getTokenFromCookie() === '') {
  router.push('/login')
} else {
  personStore.isLoggedIn = true
  const role = (personStore.role = getRoleFromCookie())
  console.log(role)

  redirectByPersonRole(role)
}
</script>

<style scoped>

</style>