import httpClient from './http-client'

const login = (email, password) =>
  httpClient.post('public/authentication/email', {
    email,
    password,
  })

const getUser = () => httpClient.get('api/user')

export { login, getUser }
