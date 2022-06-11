import { createStore } from 'vuex'

import userModule from '@/store/modules/user-module'
import cartModule from '@/store/modules/cart-module'
import productModule from '@/store/modules/product-module'

export default createStore({
  state: {
    sidebarVisible: '',
    sidebarUnfoldable: false,
  },
  mutations: {
    toggleSidebar(state) {
      state.sidebarVisible = !state.sidebarVisible
    },
    toggleUnfoldable(state) {
      state.sidebarUnfoldable = !state.sidebarUnfoldable
    },
    updateSidebarVisible(state, payload) {
      state.sidebarVisible = payload.value
    },
  },
  actions: {},
  modules: {
    userModule,
    cartModule,
    productModule,
  },
})
