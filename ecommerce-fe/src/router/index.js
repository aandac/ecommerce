import { createRouter, createWebHistory } from 'vue-router'

import DefaultLayout from '@/layouts/DefaultLayout'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: DefaultLayout,
    redirect: '/product',
    children: [
      {
        path: '/product',
        name: 'Products',
        component: () => import('@/views/product/Products.vue'),
      },
      {
        path: '/cart',
        name: 'Carts',
        component: () => import('@/views/Carts.vue'),
      },
    ],
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/pages/Login.vue'),
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/pages/Register.vue'),
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'Page404',
    component: () => import('@/views/pages/Page404'),
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  scrollBehavior() {
    // always scroll to top
    return { top: 0 }
  },
})

export default router
