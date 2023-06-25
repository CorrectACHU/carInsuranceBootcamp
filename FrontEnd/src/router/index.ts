import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: () => import('@/views/MainPage.vue'),
    },
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
      component: () => import('@/views/user/UserMainPage.vue')
    },
    {
      path: '/user/ticket',
      component: () => import('@/views/user/UserTicketDetails.vue')
    },
  ]
})

export default router
