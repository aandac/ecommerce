import httpClient from './http-client'

const login = (email, password) =>
  httpClient.post('public/authentication/email', {
    email,
    password,
  })

export { login }
