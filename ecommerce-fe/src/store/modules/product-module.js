import { getProductList } from '@/api/product-api'

const state = {
  productItems: [],
}
const mutations = {
  UPDATE_PRODUCT_ITEMS(state, payload) {
    state.productItems = payload
  },
}
const actions = {
  getProductItems({ commit }) {
    getProductList().then((response) => {
      commit('UPDATE_PRODUCT_ITEMS', response.data.payload)
    })
  },
}
const getters = {
  productItems: (state) => state.productItems,
  productItemById: (state) => (id) => {
    return state.productItems.find((productItem) => productItem.id === id)
  },
}
const productModule = {
  state,
  mutations,
  actions,
  getters,
}

export default productModule
