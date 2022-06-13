<template>
  <CForm class="row g-3" @submit.prevent="onSubmit">
    <CCol xs="12">
      <CFormLabel for="productTitle">Title</CFormLabel>
      <CFormInput
        id="productTitle"
        v-model="title"
        placeholder="Write your product title"
      />
    </CCol>
    <CCol md="3">
      <CFormLabel for="inputPrice">Price</CFormLabel>
      <CInputGroup id="inputPrice" class="mb-3">
        <CInputGroupText>$</CInputGroupText>
        <CFormInput v-model="price" />
      </CInputGroup>
    </CCol>
    <CCol md="3">
      <CFormLabel for="inputSku">SKU</CFormLabel>
      <CFormInput id="inputSku" v-model="sku" />
    </CCol>
    <CCol md="3">
      <CFormLabel for="inputInventory">Inventory</CFormLabel>
      <CFormInput id="inputInventory" v-model="inventory" />
    </CCol>
    <CCol md="3">
      <CFormLabel for="inputDelivery">Shipment Delivery Times</CFormLabel>
      <CFormSelect
        id="inputDelivery"
        size="sm"
        v-model="shipmentDeliveryTimes"
        class="mb-3"
        aria-label="Small select example"
      >
        <option>Select delivery options</option>
        <option value="THREE_BUSINESS_DAY">1-3 Business Day Delivery</option>
        <option value="FIVE_BUSINESS_DAY">3-5 Business Day Delivery</option>
        <option value="TEN_BUSINESS_DAY">10 Business Day Delivery</option>
      </CFormSelect>
    </CCol>
    <CCol xs="12">
      <input
        type="file"
        ref="file"
        id="formFileMultiple"
        multiple
        @change="onSelect"
      />
    </CCol>
    <CCol sm>
      <CButton color="info" type="submit">Save</CButton>
    </CCol>
  </CForm>
</template>

<script>
import { saveProduct } from '@/api/merchant-api'

export default {
  data() {
    return {
      files: [],
      title: '',
      shipmentDeliveryTimes: 'THREE_BUSINESS_DAY',
      sku: '',
      price: '0',
      inventory: '0',
    }
  },
  methods: {
    onSelect() {
      this.files = this.$refs.file.files
    },
    async onSubmit() {
      const formData = new FormData()
      debugger
      const body = {
        sku: this.sku,
        title: this.title,
        price: this.price,
        inventory: this.inventory,
        shipmentDeliveryTimes: this.shipmentDeliveryTimes,
      }
      formData.append('body', JSON.stringify(body))
      // Object.entries(body).forEach(([key, value]) => {
      //   formData.append(key, value)
      // })
      formData.append('file', this.files)
      try {
        await saveProduct(formData)
      } catch (err) {
        console.log(err)
      }
    },
  },
}
</script>
