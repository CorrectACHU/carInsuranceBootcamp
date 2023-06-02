<template>
  <v-card class="mx-auto px-6 py-8 cardSize" >
    <v-form v-model="form" @submit.prevent="submit">
      <v-text-field
          v-model="email"
          :readonly="loading"
          class="mb-2"
          clearable
          label="Email"
      ></v-text-field>

      <v-text-field
          v-model="password"
          :readonly="loading"
          clearable
          label="Password"
          placeholder="Enter your password"
      ></v-text-field>

      <br>
      <v-btn
          :disabled="!form || hasErrors"
          :loading="loading"
          :class="{ 'green': isSuccess, 'red': isError }"
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
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();



const form = ref(false);
const email = ref('');
const password = ref('');
const loading = ref(false);
const isSuccess = ref(false);
const isError = ref(false);


const submit = async () => {
  try {
      const response = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email: email.value,
          password: password.value,
        }),
      });
  
      if (response.ok) {
        isSuccess.value = true;
        isError.value = false;
        router.push("/")
      } else {
        throw new Error('Request failed');
      }
    } catch (error) {
      console.error(error);
      isSuccess.value = false;
      isError.value = true;
    }
  }


const hasErrors = ref(false);

</script>

<style scoped>
.cardSize {
  max-width: 50vh;
}
.green {
  background-color: green;
}

.red {
  background-color: red;
}
</style>