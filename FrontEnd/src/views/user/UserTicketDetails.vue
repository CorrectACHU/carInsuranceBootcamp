<template>
  <AppHeader />
  <main class="bg-light-blue-lighten-5 py-5">
    <TicketDetails :ticket="ticket" />
  </main>
  <AppFooter />
</template>

<script setup lang="ts">
import AppHeader from '@/components/AppHeader.vue'
import AppFooter from '@/components/AppFooter.vue'
import TicketDetails from '@/components/TicketDetails.vue'
import { type TicketDetailsInterface } from '@/models/TicketDetails'
import { onMounted, ref } from 'vue'
import { SERVER_URL } from '@/helpers/envVars'
import { getTokenFromCookie } from '@/helpers/service'
import { authStore } from '@/stores/store'
import router from '@/router'
import { useRoute } from 'vue-router'

const route = useRoute()
const personStore = authStore()
const token = getTokenFromCookie()
const ticketId = route.params.id
const ticket = ref<TicketDetailsInterface>()

async function getTicketDetails() {
  try {
    const response = await fetch(`${SERVER_URL}user/ticket/${ticketId}`, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      }
    })
    if (response.status === 401 || response.status === 403) {
      personStore.setIsLoggedIn(false)
      router.push({ name: 'login' })
    }
    if (response.ok) {
      const ticketResponse = await response.json()
      ticket.value = ticketResponse
    }
  } catch (error) {
    throw new Error('Error')
  }
}

onMounted(() => {
  getTicketDetails()
})
</script>

<style></style>
