<template>
  <v-card
    v-if="ticket"
    class="d-flex mx-auto flex-column rounded-shaped pa-5"
    min-height="60dvh"
    max-width="60dvw"
  >
    <v-card-title class="d-flex text-h5 justify-space-between align-center">
      <span>{{ dateFormat(ticket.dateOfIncident, 'fullDateWithWeekday') }}</span>
      <v-chip class="ma-2" size="x-large" :color="getTicketStatus(ticket).color">
        {{ getTicketStatus(ticket).status }}
      </v-chip>
    </v-card-title>
    <v-divider :thickness="10" class="border-opacity-50" color="info"></v-divider>
    <v-table>
      <thead>
        <tr>
          <th class="text-center">Ticket Owner</th>
          <th class="text-center">Insurance Manager</th>
          <th class="text-center">Estimator Company</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="text-center">
            {{ ticket.ticketOwner.contactInfo.firstName }}
            {{ ticket.ticketOwner.contactInfo.lastName }}
          </td>
          <td class="text-center">
            <p v-if="ticket.currentManager?.contactInfo">
              {{ ticket.currentManager.contactInfo.firstName }}
              {{ ticket.currentManager.contactInfo.lastName }}
            </p>
            <p v-else>{{ ticket.insuranceCompany.companyName }}</p>
          </td>
          <td class="text-center">
            <p v-if="ticket.currentEstimator">
              {{ ticket.currentEstimator.company.companyName }}
            </p>
            <p v-else>Manager has not added a new facility yet</p>
          </td>
        </tr>
      </tbody>
    </v-table>
    <v-divider :thickness="10" class="border-opacity-50" color="info"></v-divider>

    <v-card-title> Vehicle Info </v-card-title>
    <v-table v-if="ticket.vehicleInfo">
      <thead>
        <tr>
          <th class="text-center">Make</th>
          <th class="text-center">Model</th>
          <th class="text-center">Year</th>
          <th class="text-center">License Plate</th>
          <th class="text-center">License State</th>
          <th class="text-center">Expiration</th>
          <th class="text-center">Odometer</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="text-center">{{ ticket.vehicleInfo.make }}</td>
          <td class="text-center">{{ ticket.vehicleInfo.model }}</td>
          <td class="text-center">{{ ticket.vehicleInfo.year }}</td>
          <td class="text-center">{{ ticket.vehicleInfo.licensePlate }}</td>
          <td class="text-center">{{ ticket.vehicleInfo.licenseState }}</td>
          <td class="text-center">
            {{ dateFormat(ticket.vehicleInfo.licenseExpiration, 'keyboardDate') }}
          </td>
          <td class="text-center">{{ ticket.vehicleInfo.odometerValue }}</td>
        </tr>
      </tbody>
    </v-table>
    <v-divider :thickness="10" class="border-opacity-50" color="info"></v-divider>
    <v-card-text v-if="ticket.vehicleCondition">
      <v-card-title class="px-0 d-flex align-center justify-space-between"
        ><span>Vehicle Condition</span>
        <span>
          Impact direction
          <v-chip label size="large" color="red">{{
            formatImpactDirection(ticket.vehicleCondition.impactDirection)
          }}</v-chip>
        </span></v-card-title
      >

      <v-carousel>
        <v-carousel-item
          v-for="photo in getPhotoNames(ticket)"
          :key="photo"
          v-bind:src="
            SERVER_URL +
            'images/user' +
            ticket.ticketOwner.id +
            '/' +
            ticket.dateOfIncident +
            '/' +
            photo
          "
          cover
        ></v-carousel-item>
      </v-carousel>
    </v-card-text>
    <v-divider :thickness="10" class="border-opacity-50" color="info"></v-divider>

    <v-card-title>Other contacts</v-card-title>
    <v-card-text class="d-flex flex-row">
      <v-list v-for="contact in ticket.otherContacts" :key="contact.id">
        <v-btn variant="tonal" class="ma-3" color="black" @click="getContact(contact)">
          {{ contact.firstName }}
        </v-btn>
      </v-list>
      <v-dialog v-model="dialog" width="auto">
        <ContactComponent :contact="contactData" />
      </v-dialog>
    </v-card-text>
    <v-card-text v-if="ticket.estimatedParts.length">
      <p class="text-h6">Total Price: {{ getTotalPrice(ticket) }}$</p>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import ContactComponent from '@/components/ContactComponent.vue'
import { SERVER_URL } from '@/helpers/envVars'
import { type TicketDetailsInterface } from '@/models/TicketDetails'
import type { ContactInfo } from '@/stores/TicketInList'
import { ref } from 'vue'
import { useDate } from 'vuetify/labs/date'
defineProps(['ticket'])

const contactData = ref<ContactInfo>()
const dialog = ref(false)
const date = useDate()
const dateFormat = (ticketDate: Date, typeFormat: string) => date.format(ticketDate, typeFormat)

function getContact(contact: ContactInfo) {
  dialog.value = true
  contactData.value = contact
}

function getPhotoNames(ticket: TicketDetailsInterface) {
  return ticket.vehicleCondition.photoFileNames.split(';')
}

function formatImpactDirection(str: string) {
  return str.replace('_', ' ').toLowerCase()
}

function getTotalPrice(ticket: TicketDetailsInterface) {
  let sum = 0
  ticket.estimatedParts.forEach((element) => (sum += element.price))
}

function getTicketStatus(ticket: TicketDetailsInterface) {
  switch (ticket.ticketStatus) {
    case 'ASSIGNED' || 'IN_PROGRESS' || 'IN_REVIEW' || 'REPAIR':
      return { status: 'In progress', color: 'orange' }
    case 'NEW':
      return { status: 'New', color: 'blue' }
    case 'REPAIRED':
      return { status: 'Repaired', color: 'green' }
    case 'TOTAL_LOSS':
      return { status: 'Total loss', color: 'red' }
    default:
      return { status: 'New', color: 'blue' }
  }
}
</script>

<style scoped></style>
