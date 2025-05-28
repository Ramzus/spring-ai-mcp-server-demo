import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import Toast, { POSITION } from "vue-toastification"
import "vue-toastification/dist/index.css"

const app = createApp(App)
app.use(Toast, {
  position: POSITION.TOP_RIGHT,
  timeout: 4000,
  closeOnClick: true,
  pauseOnFocusLoss: true,
  pauseOnHover: true,
  draggable: true,
  draggablePercent: 0.6,
  showCloseButtonOnHover: false,
  hideProgressBar: false,
  closeButton: "button",
  icon: true,
  rtl: false
})
app.mount('#app')
