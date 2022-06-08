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
                <CButton color="info" shape="rounded-pill" href="#">
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
  },
}
</script>
