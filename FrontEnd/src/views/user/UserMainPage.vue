<template>
  <div>
    <AppHeader />
    <div class="d-flex align-start flex-wrap justify-space-around bodySize bg-light-blue-lighten-5">
      <v-list
        class="mt-8 mb-8 bg-light-blue-lighten-5"
        v-for="item in tickets.tickets"
        :key="item.id"
      >
        <ticket-in-list :item="item"></ticket-in-list>
      </v-list>
    </div>
    <AppFooter />
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import AppHeader from '@/components/AppHeader.vue'
import AppFooter from '@/components/AppFooter.vue'
import TicketInList from '@/components/TicketInList.vue'
import router from '@/router'
import { getTokenFromCookie } from '@/helpers/service'
import { authStore } from '@/stores/store'
import { SERVER_URL } from '@/helpers/envVars'
import { ticketListStore } from '@/stores/store'

const tickets = ticketListStore()
const personStore = authStore()

const token = getTokenFromCookie()
async function getListOfTickets() {
  try {
    const response = await fetch(`${SERVER_URL}user/tickets`, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      }
    })
    if (response.status === 401 || response.status === 403) {
      personStore.setIsLoggedIn(false)
      router.push({ name: 'login' })
    }
    const data = await response.json()
    tickets.setTickets(data)
  } catch (error) {
    throw new Error('Something went wrong')
  }
}
onMounted(() => {
  getListOfTickets()
})
</script>

<style scoped>
.bodySize {
  min-height: 80dvh;
}
</style>
