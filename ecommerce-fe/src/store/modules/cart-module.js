import {
  getCart,
  addToCart,
  deleteFromCart,
  deleteAllFromCart,
} from '@/api/cart-api'

const state = {
  cartItems: [],
}
const actions = {
  getCartItems({ commit }) {
    getCart().then((response) => {
      commit('UPDATE_CART_ITEMS', response.data.payload)
    })
  },
  addCartItem({ commit }, cartItem) {
    if (!localStorage.getItem('token')) {
      alert('Please login first')
      return
    }
    addToCart(
      cartItem.id,
      cartItem.title,
      cartItem.quantity,
      cartItem.price,
    ).then((response) => {
      commit('UPDATE_CART_ITEMS', response.data.payload)
    })
  },
  removeCartItem({ commit }, cartItem) {
    deleteFromCart(cartItem.id).then((response) => {
      commit('UPDATE_CART_ITEMS', response.data.payload)
    })
  },
  removeAllCartItems({ commit }) {
    deleteAllFromCart().then((response) => {
      commit('UPDATE_CART_ITEMS', response.data.payload)
    })
  },
}

const getters = {
  cartItems: (state) => state.cartItems,
  cartTotal: (state) => {
    return state.cartItems
      .reduce((acc, cartItem) => {
        return cartItem.quantity * cartItem.price + acc
      }, 0)
      .toFixed(2)
  },
  cartQuantity: (state) => {
    return state.cartItems.reduce((acc, cartItem) => {
      return cartItem.quantity + acc
    }, 0)
  },
}
const mutations = {
  UPDATE_CART_ITEMS(state, payload) {
    state.cartItems = payload
  },
}

const cartModule = {
  state,
  actions,
  mutations,
  getters,
}

export default cartModule
