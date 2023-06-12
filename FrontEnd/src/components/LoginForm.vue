<template>
  <v-card class="mx-auto px-6 py-8 cardSize">
    <div>
      <v-alert v-model="alert" variant="tonal" closable close-label="Close Alert" color="error">
        {{ message }}
      </v-alert>
    </div>
    <v-form v-model="form" @submit.prevent="submit">
      <v-text-field
        v-model="email"
        :readonly="loading"
        class="mb-2"
        clearable
        label="Email"
        :rules="[rules.required, rules.emailRule]"
      ></v-text-field>

      <v-text-field
        v-model="password"
        :append-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
        :rules="rules.passwordRules"
        :type="showPassword ? 'text' : 'password'"
        name="input-10-1"
        label="Password"
        hint="At least 8 characters"
        counter
        @click:append="showPassword = !showPassword"
      ></v-text-field>

      <br />
      <v-btn
        :disabled="!form || hasErrors"
        :loading="loading"
        :class="{ isSuccessButton: isSuccess, isErrorButton: isError }"
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
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const form = ref(false)
const email = ref('')
const password = ref('')
const loading = ref(false)
const isSuccess = ref(false)
const isError = ref(false)
const showPassword = ref(false)

const rules = {
  required: (value: string) => !!value || 'Field is required',
  emailRule: (value: string) =>
    !value || /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(value) || 'E-mail must be valid',
  passwordRules: [
    (value: string) => !!value || 'Please enter a password',
    (value: string) => !!value || 'Please type password.',
    (value: string) => (value && /\d/.test(value)) || 'At least one digit',
    (value: string) => (value && /[A-Z]{1}/.test(value)) || 'At least one capital latter',
    (value: string) => (value && /[^A-Za-z0-9]/.test(value)) || 'At least one special character',
    (value: string) => value.length >= 8 || 'minimum 8 characters'
  ]
}

const alert = ref(false)
const message = ref('')

const submit = async () => {
  try {
    const response = await fetch('http://localhost:8080/api/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        email: email.value,
        password: password.value
      }),
      credentials: 'include'
    })

    if (response.ok) {
      isSuccess.value = true
      isError.value = false
      router.push('/')
    } else if (response.status === 403) {
      alert.value = true
      message.value = 'User with this email and password does not exist'
    }
  } catch (error) {
    alert.value = true
    message.value = 'Something went wrong, please try reloading the page'
    isSuccess.value = false
    isError.value = true
  }
}

const hasErrors = ref(false)
</script>

<style scoped>
.cardSize {
  max-width: 50vh;
}
.isSuccessButton {
  background-color: green;
}

.isErrorButton {
  background-color: red;
}
</style>
