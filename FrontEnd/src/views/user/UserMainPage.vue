<template>
  <div>
    <AppHeader />
    <div class="d-flex align-start flex-wrap justify-space-around bodySize bg-light-blue-lighten-5">
      <v-list class="bg-light-blue-lighten-5" v-for="item in listOfTickets" :key="item.id">
        <!-- <v-card
          minWidth="30dvw"
          minHeight="20dvh"
          :title="item.ticketOwner.contactInfo?.firstName"
          :subtitle="item.dateOfIncident"
          :text="item.ticketStatus"
        >
        </v-card> -->
        <ticket-in-list :item=item></ticket-in-list>
      </v-list>
    </div>
    <AppFooter />
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive } from 'vue'
import AppHeader from '@/components/AppHeader.vue'
import AppFooter from '@/components/AppFooter.vue'
import TicketInList from '@/components/TicketInList.vue'
import router from '@/router'
import { type CardInGeneral } from '@/stores/modelInterfaces'
import { getTokenFromCookie } from '@/helpers/service'
import { authStore } from '@/stores/store'
import { SERVER_URL } from '@/helpers/envVars'

const personStore = authStore()

const token = getTokenFromCookie()
const listOfTickets: CardInGeneral[] = reactive([])
const getListOfTickets = async () => {
  try {
    const response = await fetch(`${SERVER_URL}user/tickets`, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      }
    })
    if (response.status === 401 || response.status === 403) {
      personStore.changeIsLoggedInToFalse()
      router.push('/login')
    }
    const data = await response.json()
    data.forEach((ticket: CardInGeneral) => {
      listOfTickets.push(ticket)
    })
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
