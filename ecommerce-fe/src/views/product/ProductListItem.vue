<template>
  <div class="card">
    <div class="card-content">
      <div class="content">
        <h4>{{ productItem.title }}</h4>
        <br />
        <CImage
          width="200"
          height="200"
          rounded
          thumbnail
          v-if="productItem.images[0]"
          :src="productItem.images[0]"
        />
      </div>
      <div class="media">
        <div class="media-left">
          <a
            class="button bg-info is-rounded info is-pulled-left"
            @click="addCartItem(productItem)"
          >
            <strong>Add to Cart</strong>
          </a>
        </div>
        <div class="media-right">
          <a class="button is-primary is-light">
            <strong>$ {{ productItem.price }}</strong>
          </a>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'

export default {
  name: 'ProductListItem',
  props: ['productItem'],
  methods: {
    ...mapActions(['addCartItem']),
  },
  computed: {
    ...mapState('userModule', ['user', 'isAdmin']),
  },
}
</script>
