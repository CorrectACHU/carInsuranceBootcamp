<template>
  <v-card class="pa-5">
    <v-table>
      <thead>
        <tr>
          <th class="text-center">Name</th>
          <th class="text-center">Type</th>
          <th class="text-center">Email</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="text-center">{{ contact.firstName }} {{ contact.lastName }}</td>
          <td class="text-center">{{ contact.contactType }}</td>
          <td class="text-center">{{ contact.email }}</td>
        </tr>
      </tbody>
    </v-table>
    <v-divider :thickness="10" class="border-opacity-50 py-2" color="info"></v-divider>

    <v-text>Phones: </v-text>
    <v-list v-for="phone in contact.phones" :key="phone.id">
      <v-list-item :title="phone.phoneType" :subtitle="phone.phoneNumber"> </v-list-item>
    </v-list>
    <v-divider :thickness="10" class="border-opacity-50 py-2" color="info"></v-divider>

    <v-text>Addresses: </v-text>
    <v-list v-for="address in contact.addresses" :key="address.id">
      <v-list-item :title="address.addressType">
        <p>{{ address.addressInline }} {{ address.zip }} {{ address.state }}</p>
      </v-list-item>
    </v-list>
  </v-card>
</template>

<script setup lang="ts">
defineProps(['contact'])
</script>
