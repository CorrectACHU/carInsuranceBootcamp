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
<<<<<<< HEAD
      component: () => import('@/views/user/UserMainPage.vue')
=======
      component: () => import('@/views/user/UserHomePage.vue')
>>>>>>> main
    },
  ]
})

export default router
