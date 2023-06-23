<template>
  <div>
    <AppHeader />
    <div class="d-flex align-start flex-wrap justify-space-around bodySize bg-light-blue-lighten-5">
      <v-list class="bg-light-blue-lighten-5" v-for="item in listOfTickets" :key="item.id">
        <v-card
          minWidth=30dvw
          minHeight=20dvh
          :title=item.ticketOwner.contactInfo?.firstName
          :subtitle=item.dateOfIncident
          :text=item.ticketStatus
          >
        </v-card>
      </v-list>
    </div>
    <AppFooter />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue'
import AppHeader from '@/components/AppHeader.vue'
import AppFooter from '@/components/AppFooter.vue'
import router from '@/router'
import { type CardInGeneral } from '@/stores/modelInterfaces'

const getToken = () => {
  const cookies = document.cookie
  const cookiesArr = cookies.split(';')
  const token = cookiesArr.find((cookie) => cookie.includes('token'))
  if (token) {
    return token.replace('token=', '')
  }
  return 'null'
}

const token = getToken()
const listOfTickets: CardInGeneral[] = reactive([])
const getListOfTickets = async () => {
  try {
    const response = await fetch('http://localhost:8080/api/v1/user/tickets', {
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      }
    })
    if (response.status === 401) {
      const message = 'Your token was expired'
      router.push('/login')
    }
    const data = await response.json()
    data.forEach((ticket: CardInGeneral) => {
      listOfTickets.push(ticket)
    })
  } catch (error) {
    throw new Error('ssss')
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
