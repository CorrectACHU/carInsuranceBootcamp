<template>
  <div>
    <!-- <v-container class=""> -->
    <v-card class="d-flex w-100 mx-auto" min-height="60dvh" max-width="60dvw">
      <v-card-title>Title Of Insurance</v-card-title>
      <v-card-text >
        <p class="text-center">Title Of Insurance</p>
        <p>saasdf</p>
      </v-card-text>
    </v-card>
    <!-- </v-container> -->
  </div>
</template>

<script setup lang="ts">
import { SERVER_URL } from '@/helpers/envVars'
import { onMounted } from 'vue'
import { ref } from 'vue'

const ticketTitle = ref('')

async function getTicketDetails() {
  const response = await fetch(`${SERVER_URL}user/ticket`)
}

onMounted(() => {})
</script>

<style scoped></style>
