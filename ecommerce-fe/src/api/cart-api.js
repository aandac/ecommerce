import httpClient from './http-client'

const getCart = () => httpClient.get('public/cart')
const addToCart = (id, title, price) =>
  httpClient.post('public/cart', {
    id,
    title,
    price,
  })
const deleteFromCart = (id, title, quantity, price) =>
  httpClient.post('public/cart/delete', {
    id,
    title,
    quantity,
    price,
  })

export { getCart, addToCart, deleteFromCart }
