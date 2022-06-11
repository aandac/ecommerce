<template>
  <div class="bg-light min-vh-100 d-flex flex-row align-items-center">
    <CContainer>
      <CRow class="justify-content-center">
        <CCol :md="4">
          <CCardGroup>
            <CCard class="p-4">
              <CCardBody>
                <VeeForm
                  v-slot="{ handleSubmit }"
                  :validation-schema="schema"
                  class="w-100"
                  ref="myForm"
                  as="div"
                >
                  <form @submit="handleSubmit($event, onSubmit)">
                    <h1>Login</h1>
                    <p class="text-medium-emphasis">Sign In to your account</p>
                    <Field name="email" v-slot="{ field /* errors */ }">
                      <CInputGroup class="mb-3">
                        <CInputGroupText>
                          <CIcon icon="cil-user" />
                        </CInputGroupText>
                        <CFormInput
                          placeholder="Username"
                          autocomplete="username"
                          v-bind="field"
                        />
                      </CInputGroup>
                    </Field>
                    <Field name="password" v-slot="{ field /* errors */ }">
                      <CInputGroup class="mb-4">
                        <CInputGroupText>
                          <CIcon icon="cil-lock-locked" />
                        </CInputGroupText>
                        <CFormInput
                          type="password"
                          placeholder="Password"
                          autocomplete="current-password"
                          v-bind="field"
                        />
                      </CInputGroup>
                    </Field>
                    <CRow>
                      <CCol :xs="6">
                        <CButton
                          color="primary"
                          class="px-4"
                          type="submit"
                          :class="{ 'loading-spinner': loading }"
                        >
                          Login
                        </CButton>
                      </CCol>
                    </CRow>
                  </form>
                </VeeForm>
              </CCardBody>
            </CCard>
          </CCardGroup>
        </CCol>
      </CRow>
    </CContainer>
  </div>
</template>

<script>
import { Form as VeeForm, Field } from 'vee-validate'
import { mapActions, mapState } from 'vuex'
import * as yup from 'yup'

export default {
  name: 'Login',
  components: {
    Field,
    VeeForm,
  },
  data() {
    const schema = yup.object({
      email: yup.string().required().email(),
      password: yup.string().required().min(8),
    })

    return {
      schema,
      loading: false,
      showError: false,
    }
  },
  methods: {
    ...mapActions('userModule', ['LOGIN']),
    async onSubmit(values) {
      this.loading = true
      try {
        await this.LOGIN({ formData: values })

        let redirectRoute =
          this.$router.currentRoute.value.query?.redirectAfterLogin

        await this.$router.push(redirectRoute || this.defaultRoute)
      } catch (error) {
        this.loading = false
        alert(error?.response?.data?.errors?.[0]?.message)
      }
    },
  },
  computed: {
    ...mapState('userModule', ['defaultRoute']),
  },
}
</script>
