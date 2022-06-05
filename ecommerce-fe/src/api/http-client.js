import axios from 'axios'

const httpClient = axios.create({
  baseURL: 'http://localhost:8081',
  headers: {
    'Content-Type': 'application/json',
  },
})

const requestInterceptors = (config) => {
  if (localStorage.getItem('token')) {
    config.headers['Authorization'] = 'Bearer ' + localStorage.getItem('token')
  } else {
    delete config.headers['Authorization']
    return config
  }

  return config
}

httpClient.interceptors.request.use(requestInterceptors)

export default httpClient
