<template>
  <CDropdown variant="nav-item">
    <CDropdownToggle placement="bottom-end" class="py-0" :caret="false">
      <CAvatar :src="avatar" size="md" />
    </CDropdownToggle>
    <CDropdownMenu class="pt-0">
      <CDropdownHeader component="h6" class="bg-light fw-semibold py-2">
        Account
      </CDropdownHeader>
      <div
        class="text-muted px-3 pt-2 max-width-300-px text-truncate mb-2"
        :title="user?.email"
      >
        {{ user?.email }}
      </div>
      <CDropdownHeader component="h6" class="bg-light fw-semibold py-2">
        Settings
      </CDropdownHeader>
      <CDropdownItem v-if="isMerchant" href="/merchant">
        <CIcon icon="cil-user" />
        Products
      </CDropdownItem>
      <CDropdownItem v-if="isCustomer" href="/profile">
        <CIcon icon="cil-user" />
        Profile
      </CDropdownItem>
      <CDropdownDivider />
      <CDropdownItem @click="onClickLogout">
        <CIcon icon="cil-lock-locked" />
        Logout
      </CDropdownItem>
    </CDropdownMenu>
  </CDropdown>
</template>

<script>
import avatar from '@/assets/images/avatars/2.jpg'
import { mapState, mapMutations } from 'vuex'

export default {
  name: 'AppHeaderDropdownAccnt',
  setup() {
    return {
      avatar: avatar,
      itemsCount: 42,
    }
  },
  methods: {
    ...mapMutations('userModule', ['LOGOUT']),
    onClickLogout() {
      this.LOGOUT()
    },
  },
  computed: {
    ...mapState('userModule', ['user', 'isAdmin', 'isMerchant', 'isCustomer']),
  },
}
</script>
