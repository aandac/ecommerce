import httpClient from './http-client'

const getProductList = (q, page, size) =>
  httpClient.get('public/product', { params: { q, page, size } })

export { getProductList }
