<template>
  <v-footer class="bg-grey-lighten-1 footerSize" >
    <v-row justify="center" no-gutters>
      <v-btn color="white" variant="text" class="mx-2" rounded="xl">
        About
      </v-btn>
      <v-col class="text-center mt-4" cols="12">
        {{ currentYear }} — <strong>Car Insurance</strong>
      </v-col>
    </v-row>
  </v-footer>
</template>

<script setup lang="ts" >
const currentYear: number= new Date().getFullYear()
</script>

<style scoped>
.footerSize {
  height:10vh ;
}
</style>
