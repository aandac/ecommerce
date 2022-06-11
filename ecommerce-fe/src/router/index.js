import { createRouter, createWebHistory } from 'vue-router'
import store from '@/store'
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
        meta: { authenticated: true },
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

const navigationRules = {
  authenticated: (to, from, user) => {
    if (user) {
      return true
    } else {
      store.commit('userModule/LOGOUT')
      return false
    }
  },
}

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  scrollBehavior() {
    // always scroll to top
    return { top: 0 }
  },
})
let redirectPath = null
let redirectParams = {}

router.beforeEach(async (to, from, next) => {
  redirectPath = null
  redirectParams = {}

  let user = null
  try {
    user = await store.dispatch('userModule/USER')
    debugger
  } catch (error) {
    // if auth not required, don't show any error, continue
    if (!to.meta.authenticated) {
      next(true)
      return
    }

    // user is necessary to continue but there is an error while getting it
    next(false)
    store.commit('userModule/LOGOUT', {
      redirectAfterLogin: true,
    })
    // to show toast notification if necessary
    alert(error)
    return
  }

  if (navigationRules.authenticated(to, from, user)) {
    next(true)
    return
  }
  next(false)
})

export default router
