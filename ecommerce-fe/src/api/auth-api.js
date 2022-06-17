import httpClient from './http-client'

const login = (email, password) =>
  httpClient.post('public/authentication/email', {
    email,
    password,
  })

const register = (
  email,
  password,
  shippingAddress,
  billingAddress,
  creditCardNumber,
  creditCardMonth,
  creditCardYear,
  creditCardCvv,
) =>
  httpClient.post('public/customer', {
    email,
    password,
    shippingAddress,
    billingAddress,
    creditCardNumber,
    creditCardMonth,
    creditCardYear,
    creditCardCvv,
  })

const getUser = () => httpClient.get('api/user')

export { login, getUser, register }
