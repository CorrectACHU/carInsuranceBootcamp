import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      name:'mainPage',
      path: '/',
      component: () => import('@/views/MainPage.vue'),
    },
    {
      name: 'login',
      path: '/login',
      component: () => import('@/views/LoginPage.vue')
    },
    {
      name: 'userTickets',
      path: '/user/tickets',
      component: () => import('@/views/user/UserMainPage.vue')
    },
    {
      name:'ticketDetails',
      path: '/ticket/:id',
      component: () => import('@/views/user/UserTicketDetails.vue')
    },
  ]
})

export default router
