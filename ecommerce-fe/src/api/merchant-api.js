import httpClient from './http-client'

const saveProduct = (
  sku,
  title,
  price,
  inventory,
  shipmentDeliveryTimes,
  files,
  active = true,
) =>
  httpClient.post('api/merchant/product', {
    sku,
    title,
    price,
    inventory,
    shipmentDeliveryTimes,
    files,
    active,
  })

const uploadFiles = (formData) =>
  httpClient.post('api/merchant/product/file', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })

export { saveProduct, uploadFiles }
