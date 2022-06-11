<template>
  <div class="container">
    <CRow>
      <div
        class="tile is-parent"
        v-for="productItem in productItems"
        :key="productItem.id"
      >
        <CCol sm="auto">
          <ProductListItem :productItem="productItem" />
        </CCol>
      </div>
    </CRow>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import ProductListItem from './ProductListItem'

export default {
  name: 'ProductList',
  components: {
    ProductListItem,
  },
  computed: {
    ...mapGetters(['productItems']),
  },
  created() {
    this.$store.dispatch('getProductItems')
  },
}
</script>
