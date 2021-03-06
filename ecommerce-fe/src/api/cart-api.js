import httpClient from './http-client'

const getCart = () => httpClient.get('api/cart')
const addToCart = (id, title, quantity, price) =>
  httpClient.post('api/cart', {
    id,
    title,
    quantity,
    price,
  })
const deleteFromCart = (id) =>
  httpClient.post('api/cart/delete', {
    id,
  })

const deleteAllFromCart = () => httpClient.post('api/cart/delete')

const oneClickCheckout = () => httpClient.post('api/cart/checkout')

export {
  getCart,
  addToCart,
  deleteFromCart,
  deleteAllFromCart,
  oneClickCheckout,
}
