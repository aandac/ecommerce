<template>
  <div class="bg-light">
    <CContainer>
      <CRow class="justify-content-center">
        <CCol :md="9" :lg="7" :xl="6">
          <CCard class="mx-4">
            <CCardBody class="p-4">
              <VeeForm
                v-slot="{ handleSubmit }"
                :validation-schema="schema"
                class="w-100"
                ref="myForm"
                as="div"
              >
                <form @submit="handleSubmit($event, onSubmit)">
                  <Field
                    name="shippingAddress"
                    v-slot="{ field, /* errors */ errorMessage, meta }"
                  >
                    <CInputGroup class="mb-4">
                      <CInputGroupText>
                        <CIcon icon="cil-address-book" />
                      </CInputGroupText>
                      <CFormInput
                        type="text"
                        v-bind="field"
                        v-model="userDetail.shippingAddress"
                        :invalid="!!errorMessage"
                        :valid="meta.valid && meta.touched"
                        placeholder="Enter shipping address"
                      />
                    </CInputGroup>
                  </Field>
                  <Field
                    name="billingAddress"
                    v-slot="{ field, /* errors */ errorMessage, meta }"
                  >
                    <CInputGroup class="mb-4">
                      <CInputGroupText>
                        <CIcon icon="cil-cash" />
                      </CInputGroupText>
                      <CFormInput
                        type="text"
                        v-bind="field"
                        v-model="userDetail.billingAddress"
                        :invalid="!!errorMessage"
                        :valid="meta.valid && meta.touched"
                        placeholder="Enter billing address"
                      />
                    </CInputGroup>
                  </Field>
                  <Field
                    name="creditCardNumber"
                    v-slot="{ field, /* errors */ errorMessage, meta }"
                  >
                    <CInputGroup class="mb-4">
                      <CInputGroupText>
                        <CIcon icon="cil-credit-card" />
                      </CInputGroupText>
                      <CFormInput
                        type="text"
                        v-bind="field"
                        v-model="userDetail.creditCardNumber"
                        :invalid="!!errorMessage"
                        :valid="meta.valid && meta.touched"
                        placeholder="Enter credit card number"
                      />
                    </CInputGroup>
                  </Field>

                  <CInputGroup class="mb-4">
                    <CInputGroupText>
                      <CIcon icon="cil-credit-card" />
                    </CInputGroupText>
                    <Field
                      name="creditCardExpireMonth"
                      v-slot="{ field, /* errors */ errorMessage, meta }"
                    >
                      <CFormInput
                        class="mr-1"
                        type="text"
                        v-bind="field"
                        v-model="userDetail.creditCardExpireMonth"
                        :invalid="!!errorMessage"
                        :valid="meta.valid && meta.touched"
                        placeholder="month"
                      />
                    </Field>
                    <Field
                      name="creditCardExpireYear"
                      v-slot="{ field, /* errors */ errorMessage, meta }"
                    >
                      <CFormInput
                        class="mr-1"
                        type="text"
                        v-bind="field"
                        v-model="userDetail.creditCardExpireYear"
                        :invalid="!!errorMessage"
                        :valid="meta.valid && meta.touched"
                        placeholder="year"
                      />
                    </Field>
                    <Field
                      name="creditCardCvv"
                      v-slot="{ field, /* errors */ errorMessage, meta }"
                    >
                      <CFormInput
                        type="text"
                        v-bind="field"
                        v-model="userDetail.creditCardCvv"
                        :invalid="!!errorMessage"
                        :valid="meta.valid && meta.touched"
                        placeholder="Enter credit cvv"
                      />
                    </Field>
                  </CInputGroup>
                  <div class="d-grid">
                    <CButton color="success">Update Profile</CButton>
                  </div>
                </form>
              </VeeForm>
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>
    </CContainer>
  </div>
</template>
<script>
import * as yup from 'yup'
import { Field, Form as VeeForm } from 'vee-validate'
import { getCustomerDetail } from '@/api/customer-api'
import { mapActions } from 'vuex'

export default {
  name: 'Profile',
  components: {
    Field,
    VeeForm,
  },

  data: () => {
    const schema = yup.object({
      creditCardNumber: yup.string().label('Card number').max(16),
      creditCardCvv: yup.string().label('CVC').min(3).max(4),
      creditCardExpireYear: yup.string().label('Expiry year').min(4).max(4),
      creditCardExpireMonth: yup.string().label('Expiry month').min(2).max(2),
    })
    return {
      schema,
      userDetail: '',
    }
  },
  async mounted() {
    getCustomerDetail()
      .then((res) => {
        this.userDetail = res.data?.payload
      })
      .catch((error) => {
        alert(error)
      })
  },
  methods: {
    ...mapActions('userModule', ['UPDATE_CUSTOMER']),
    async onSubmit() {
      await this.UPDATE_CUSTOMER(this.userDetail)
    },
  },
}
</script>
