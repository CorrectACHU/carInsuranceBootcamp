import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      component: () => import('@/views/LoginPage.vue')
    },
    {
      path: '/',
      component: () => import('@/views/HomePage.vue')
    },
    {
      path: '/user',
      component: () => import('@/views/user/UserHomePage.vue')
    },
  ]
})

export default router
