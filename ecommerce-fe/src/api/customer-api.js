import httpClient from './http-client'

const postUpdateCustomer = (userDetails) =>
  httpClient.post('api/customer/detail', userDetails)

const getCustomerDetail = () => httpClient.get('api/customer/detail')

export { postUpdateCustomer, getCustomerDetail }
