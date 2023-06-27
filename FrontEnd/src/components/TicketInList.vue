<template>
  <div>
    <v-card
      class="card"
      minWidth="30dvw"
      minHeight="20dvh"
      title="Your ticket from date:"
      :subtitle="item.dateOfIncident"
      :text="item.ticketStatus"
      @click="moveToTicket(item.id)"
    >
      <v-btn
        class="deleteButton"
        v-if="item.ticketStatus === 'NEW'"
        v-on:click.stop
        @click="deleteTicket(item.id)"
      >
        DELETE
      </v-btn>
    </v-card>
  </div>
</template>

<script setup lang="ts">
import { SERVER_URL } from '@/helpers/envVars'
import { getTokenFromCookie } from '@/helpers/service'
import router from '@/router'
import { ticketListStore } from '@/stores/store'

defineProps(['item'])

const tickets = ticketListStore()
const token = getTokenFromCookie()

const moveToTicket = (id: number) => {
  router.push({ name: 'ticketDetails', params: { id: id } })
}

const deleteTicket = async (id: number) => {
  try {
    const response = await fetch(`${SERVER_URL}user/ticket/${id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      }
    })
    if (response.ok) {
      tickets.deleteTicket(id)
    }
  } catch (error) {
    alert(error)
    throw new Error("Ticket wasn't deleted!")
  }
}
</script>

<style scoped>
.card:hover {
  background-color: rgb(78, 129, 238);
  color: white;
}

.deleteButton {
  background-color: red;
  color: white;
}

.deleteButton:hover {
  border-style: solid;
  border-color: black;
  border-width: thin;
}
</style>
