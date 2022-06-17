<template>
  <div class="bg-light min-vh-100 d-flex flex-row align-items-center">
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
                  <h1>Register</h1>
                  <p class="text-medium-emphasis">Create your account</p>
                  <Field
                    name="email"
                    v-slot="{ field, /* errors */ errorMessage, meta }"
                  >
                    <CInputGroup class="mb-3">
                      <CInputGroupText>@</CInputGroupText>
                      <CFormInput
                        placeholder="Email"
                        autocomplete="email"
                        v-bind="field"
                        :invalid="!!errorMessage"
                        :valid="meta.valid && meta.touched"
                      />
                      <CFormFeedback invalid>
                        {{ errorMessage }}
                      </CFormFeedback>
                    </CInputGroup>
                  </Field>
                  <Field
                    name="password"
                    v-slot="{ field, /* errors */ errorMessage, meta }"
                  >
                    <CInputGroup class="mb-3">
                      <CInputGroupText>
                        <CIcon icon="cil-lock-locked" />
                      </CInputGroupText>
                      <CFormInput
                        type="password"
                        placeholder="Password"
                        autocomplete="new-password"
                        v-bind="field"
                        :invalid="!!errorMessage"
                        :valid="meta.valid && meta.touched"
                      />
                      <CFormFeedback invalid>
                        {{ errorMessage }}
                      </CFormFeedback>
                    </CInputGroup>
                  </Field>
                  <Field
                    name="repeatPassword"
                    v-slot="{ field, /* errors */ errorMessage, meta }"
                  >
                    <CInputGroup class="mb-4">
                      <CInputGroupText>
                        <CIcon icon="cil-lock-locked" />
                      </CInputGroupText>
                      <CFormInput
                        type="password"
                        placeholder="Repeat password"
                        autocomplete="new-password"
                        v-bind="field"
                        :invalid="!!errorMessage"
                        :valid="meta.valid && meta.touched"
                      />
                      <CFormFeedback invalid>
                        {{ errorMessage }}
                      </CFormFeedback>
                    </CInputGroup>
                  </Field>
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
                        <CIcon icon="cil-cash" />
                      </CInputGroupText>
                      <CFormInput
                        type="text"
                        v-bind="field"
                        :invalid="!!errorMessage"
                        :valid="meta.valid && meta.touched"
                        placeholder="Enter credit card number"
                      />
                    </CInputGroup>
                  </Field>

                  <CInputGroup class="mb-4">
                    <CInputGroupText>
                      <CIcon icon="cil-cash" />
                    </CInputGroupText>
                    <Field
                      name="creditCardMonth"
                      v-slot="{ field, /* errors */ errorMessage, meta }"
                    >
                      <CFormInput
                        class="mr-1"
                        type="text"
                        v-bind="field"
                        :invalid="!!errorMessage"
                        :valid="meta.valid && meta.touched"
                        placeholder="month"
                      />
                    </Field>
                    <Field
                      name="creditCardYear"
                      v-slot="{ field, /* errors */ errorMessage, meta }"
                    >
                      <CFormInput
                        class="mr-1"
                        type="text"
                        v-bind="field"
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
                        :invalid="!!errorMessage"
                        :valid="meta.valid && meta.touched"
                        placeholder="Enter credit cvv"
                      />
                    </Field>
                  </CInputGroup>
                  <div class="d-grid">
                    <CButton color="success" @click="createCustomer"
                      >Create Account
                    </CButton>
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
import { Form as VeeForm, Field } from 'vee-validate'
import { mapActions, mapState } from 'vuex'

export default {
  name: 'Register',
  components: {
    Field,
    VeeForm,
  },
  data: () => {
    const schema = yup.object({
      email: yup.string().required().email(),
      password: yup.string().required().min(8),
      repeatPassword: yup
        .string()
        .required('Repeat Password is a required field')
        .min(8, 'repeat password must be at least 8 characters')
        .oneOf([yup.ref('password'), null], 'Passwords must match'),
      creditCardNumber: yup.string().label('Card number').max(16),
      creditCardCvv: yup.string().label('CVC').min(3).max(4),
      creditCardYear: yup.string().label('Expiry year').min(4).max(4),
      creditCardMonth: yup.string().label('Expiry month').min(2).max(2),
    })
    return {
      schema,
    }
  },
  methods: {
    ...mapActions('userModule', ['CREATE_USER', 'LOGIN']),
    async createCustomer() {},
    async onSubmit(values) {
      await this.CREATE_USER({ formData: values })
      await this.LOGIN({ formData: values })
      this.$router.push('/')
    },
  },
}
</script>
