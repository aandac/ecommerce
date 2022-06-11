<template>
  <CHeader position="sticky" class="mb-4">
    <CContainer fluid>
      <CHeaderNav class="d-none d-md-flex me-auto">
        <CNavItem>
          <CNavLink href="/">Ecommerce App</CNavLink>
        </CNavItem>
      </CHeaderNav>
      <CHeaderNav>
        <CNavItem v-if="!user">
          <CNavLink href="/login">Login</CNavLink>
        </CNavItem>
        <CNavItem v-if="!user">
          <CNavLink href="/register">Register</CNavLink>
        </CNavItem>
        <CNavItem v-if="user">
          <CNavLink href="/cart">
            <CIcon class="mx-2" icon="cil-cart" size="lg" />
            <CBadge color="info">{{ cartItems.length }}</CBadge>
          </CNavLink>
        </CNavItem>
      </CHeaderNav>
      <div v-if="user">
        <AppHeaderDropdownAccnt />
      </div>
    </CContainer>
    <CHeaderDivider />
    <CContainer fluid>
      <AppBreadcrumb />
    </CContainer>
  </CHeader>
</template>

<script>
import { mapGetters, mapState } from 'vuex'
import AppBreadcrumb from './AppBreadcrumb'
import AppHeaderDropdownAccnt from './AppHeaderDropdownAccnt'

export default {
  name: 'AppHeader',
  components: {
    AppHeaderDropdownAccnt,
    AppBreadcrumb,
  },
  computed: {
    ...mapState('userModule', ['user', 'isAdmin']),
    ...mapGetters(['cartItems']),
  },
  created() {
    this.$store.dispatch('getCartItems')
  },
}
</script>
