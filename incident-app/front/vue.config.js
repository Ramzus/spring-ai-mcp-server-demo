const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 8084,
    proxy: {
      '/api': {
        target: 'http://localhost:8083',
        changeOrigin: true,
        logLevel: 'debug'
      }
    }
  },
  lintOnSave: false
})
