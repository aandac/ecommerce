<template>
  <CRow :xs="{ cols: 1, gutter: 4 }" :md="{ cols: 3 }">
    <div v-for="productItem in productItems" :key="productItem.id">
      <CCol xs>
        <CCard>
          <CCardImage
            orientation="top"
            v-if="productItem.images[0]"
            :src="productItem.images[0]"
          />
          <CCardBody>
            <CCardTitle>{{ productItem.title }}</CCardTitle>

            <CRow>
              <CCol xs="6" sm="9">
                <CButton
                  color="info"
                  shape="rounded-pill"
                  @click="
                    this.addToCart(
                      productItem.id,
                      productItem.title,
                      productItem.price,
                    )
                  "
                >
                  Add to Cart
                </CButton>
              </CCol>
              <CCol xs="6" sm="3">
                <strong>$ {{ productItem.price }}</strong>
              </CCol>
            </CRow>
          </CCardBody>
        </CCard>
      </CCol>
    </div>
  </CRow>
</template>

<script>
import { getProductList } from '@/api/product-api'
import { addToCart } from '@/api/cart-api'

export default {
  data: () => {
    return { productItems: [] }
  },
  async mounted() {
    this.loadProductList()
  },
  methods: {
    async loadProductList() {
      const res = await getProductList(0, 0, 10)
      this.productItems = res.data.payload
    },
    async addToCart(id, title, price) {
      await addToCart(id, title, price)
    },
  },
}
</script>
