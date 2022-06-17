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
    <CCol xs="2">
      <CButton color="info" type="submit" @click="uploadFile"
        >Upload File
      </CButton>
    </CCol>
    <CCol xs="10">
      <input type="file" ref="file" id="file" @change="onSelect" />
    </CCol>
    <CCol sm>
      <CButton color="info" @click="saveProduct">Save</CButton>
    </CCol>
  </CForm>
</template>

<script>
import { saveProduct, uploadFiles } from '@/api/merchant-api'

export default {
  data() {
    return {
      files: [],
      uploadedFile: '',
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
    async saveProduct() {
      await saveProduct(
        this.sku,
        this.title,
        this.price,
        this.inventory,
        this.shipmentDeliveryTimes,
        [this.uploadedFile],
      )
    },
    async uploadFile() {
      const formData = new FormData()
      formData.append('file', this.files.item(0))
      uploadFiles(formData).then((response) => {
        this.uploadedFile = response.data
      })
    },
  },
}
</script>
