import { login } from '@/api/auth-api'

const state = {
  user: null,
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
}
const mutations = {}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
}
