import { login, getUser } from '@/api/auth-api'
import router from '@/router'

const state = {
  user: null,
  isAdmin: false,
  role: null,
  defaultRoute: '/',
}
const actions = {
  async LOGIN(context, payload) {
    return new Promise((resolve, reject) => {
      context.commit('LOGOUT')

      const success = (token, res) => {
        localStorage.setItem('token', token)
        resolve(res)
      }

      const error = () => {
        console.error('An error happened while saving token. Please check it.')
      }

      login(payload.formData.email, payload.formData.password)
        .then((res) => {
          if (res.data?.payload?.token) {
            success(res.data?.payload?.token, res)
          } else {
            error()
          }
          resolve(res)
        })
        .catch((error) => {
          reject(error)
        })
    })
  },
  async USER(context, payload) {
    return new Promise((resolve, reject) => {
      if (!localStorage.getItem('token')) {
        reject('TOKEN_NOT_FOUND')
        return
      }

      if (context.state.user && payload?.refresh !== true) {
        resolve(context.state.user)
        return
      }

      getUser()
        .then((res) => {
          context.commit('SET_USER', res.data?.payload)
          resolve(res.data?.payload)
        })
        .catch((error) => {
          reject(error)
        })
    })
  },
}
const mutations = {
  SET_USER(state, payload) {
    state.user = payload

    // check and set isAdmin state
    if (
      payload?.authorities?.includes('ADMIN') ||
      localStorage.getItem('isAdmin') === 'true'
    ) {
      localStorage.setItem('isAdmin', true)
      state.isAdmin = true
    } else {
      state.isAdmin = false
    }

    state.defaultRoute = '/'
  },
  LOGOUT(state, payload) {
    state.user = null
    state.isAdmin = null
    state.defaultRoute = '/'
    localStorage.removeItem('token')
    localStorage.removeItem('isAdmin')

    let queryForRedirect = null
    if (
      payload?.redirectAfterLogin &&
      router.options.history.location !== '/'
    ) {
      queryForRedirect = { redirectAfterLogin: router.options.history.location }
    }
    let noRedirectPages = ['/login', '/register']
    if (!noRedirectPages.includes(router.currentRoute.value.fullPath)) {
      let loginUrl = '/login'
      router.push({
        path: loginUrl,
        ...(queryForRedirect && { query: queryForRedirect }),
      })
    }
  },
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
}
