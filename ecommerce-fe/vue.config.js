const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: 'warning',
  publicPath: '/',
  productionSourceMap: process.env.NODE_ENV !== 'production',
  ...(process.env.NODE_ENV !== 'production' && { css: { sourceMap: true } }),
  configureWebpack: {
    resolve: {
      symlinks: false,
    },
    ...(process.env.NODE_ENV !== 'production' && { devtool: 'source-map' }),
  },
})
