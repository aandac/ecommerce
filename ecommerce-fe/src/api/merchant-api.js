import httpClient from './http-client'

const saveProduct = (formData) =>
  httpClient.post('api/merchant/product', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })

export { saveProduct }
