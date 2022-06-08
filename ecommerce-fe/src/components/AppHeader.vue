<template>
  <CHeader position="sticky" class="mb-4">
    <CContainer fluid>
      <CHeaderNav class="d-none d-md-flex me-auto">
        <CNavItem>
          <CNavLink href="/">Ecommerce App</CNavLink>
        </CNavItem>
      </CHeaderNav>
      <CHeaderNav>
        <CNavItem>
          <CNavLink href="/login">Login</CNavLink>
        </CNavItem>
        <CNavItem>
          <CNavLink href="/register">Register</CNavLink>
        </CNavItem>
        <CNavItem>
          <CNavLink href="/cart">
            <CIcon class="mx-2" icon="cil-cart" size="lg" />
          </CNavLink>
        </CNavItem>
      </CHeaderNav>
    </CContainer>
    <CHeaderDivider />
    <CContainer fluid>
      <AppBreadcrumb />
    </CContainer>
  </CHeader>
</template>

<script>
import AppBreadcrumb from './AppBreadcrumb'

export default {
  name: 'AppHeader',
  components: {
    AppBreadcrumb,
  },
}
</script>
