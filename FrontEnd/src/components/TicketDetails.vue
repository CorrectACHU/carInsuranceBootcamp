<template>
  <div>
    <v-card class="d-flex mx-auto" min-height="60dvh" max-width="60dvw" :text="ticketTitle">
      <v-divider></v-divider>
      <v-text>sadasd</v-text>
    </v-card>
  </div>
</template>

<script setup lang="ts">
import { SERVER_URL } from '@/helpers/envVars'
import { onMounted } from 'vue'
import { ref } from 'vue'

const ticketTitle = ref('')
ticketTitle.value = `llll`

async function getTicketDetails() {
  const response = await fetch(`${SERVER_URL}user/ticket`)
}

onMounted(() => {})
</script>

<style scoped></style>
