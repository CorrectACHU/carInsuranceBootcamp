<template>
  <div>
    <v-card
      class="card"
      minWidth="30dvw"
      minHeight="20dvh"
      title="Your ticket from date:"
      :subtitle="item.dateOfIncident"
      v-bind:text="item.ticketStatus"
      @click="moveToTicket(item.id)"
    >
    </v-card>
  </div>
</template>

<script setup lang="ts">
import router from '@/router'

const props = defineProps(['item'])

const moveToTicket = (id: number) => {
  router.push(`ticket/${id}`)
}
</script>

<style scoped>
.card:hover {
  background-color:rgb(78, 129, 238);
  color: white;
}
</style>
