<template>
  <header>
    <v-toolbar color="blue" style="height:10dvh;">

    </v-toolbar>
  </header>
  <main>
  <v-sheet class="bg-light-blue-lighten-5" rounded style="height:80dvh; padding-top: 20dvh;">
    <v-card class="mx-auto px-6 py-8" max-width="344">
      <v-form
        v-model="form"
        @submit.prevent="onSubmit"
      >
        <v-text-field
          v-model="email"
          :readonly="loading"
          :rules="[required]"
          class="mb-2"
          clearable
          label="Email"
        ></v-text-field>

        <v-text-field
          v-model="password"
          :readonly="loading"
          :rules="[required]"
          clearable
          label="Password"
          placeholder="Enter your password"
        ></v-text-field>

        <br>

        <v-btn
          :disabled="!form"
          :loading="loading"
          block
          color="success"
          size="large"
          type="submit"
          variant="elevated"
        >
          Sign In
        </v-btn>
      </v-form>
    </v-card>
  </v-sheet>
  </main>
  <footer>
    <v-footer class="bg-grey-lighten-1" style='height:10vh'>
    <v-row justify="center" no-gutters>
      <v-btn
        color="white"
        variant="text"
        class="mx-2"
        rounded="xl"
      >
        About
      </v-btn>
      <v-col class="text-center mt-4" cols="12">
        {{ new Date().getFullYear() }} — <strong>Car Insurance</strong>
      </v-col>
    </v-row>
  </v-footer>
  </footer>
  </template>

<script lang="ts">
import $api from '../http'
import { defineComponent, reactive, toRefs } from 'vue';
export default defineComponent({
  name:'LoginPage',
  components: {},
  setup() {
    const state = reactive({
      form:false as boolean,
      email:'l' as string,
      password:'u' as string,
      loading: false as boolean,
      onSubmit: () => {
        console.log(state.email, "ssafsadf");
        let {email, password} = state
        const logJSONData = async (email: string, password: string) => {
          const JSssas = JSON.stringify({email, password})
          const res = $api.post('api/auth/login', JSssas)
          console.log(res);
          
        } 
        logJSONData(email, password)
        if (!state.form) return

        state.loading = true

        setTimeout(() => {
          state.loading = false
          console.log(state.loading)}, 2000)
      
      },
      required: (v:string) => {return !!v || 'Field is required'}
    })
    return {...toRefs(state)}
  }
})

</script>

<style scoped>

</style>
<!-- 
<script>
  export default {
    data: () => ({
      form: false,
      email: null,
      password: null,
      loading: false,
    }),

    methods: {
      onSubmit () {
        if (!this.form) return

        this.loading = true

        setTimeout(() => (this.loading = false), 2000)
      },
      required (v) {
        return !!v || 'Field is required'
      },
    },
  }
</script> -->