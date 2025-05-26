import { createRouter, createWebHistory } from 'vue-router'
import IncidentList from '../components/IncidentList.vue'
import CurrentIncidents from '../components/CurrentIncidents.vue'
import CreateIncident from '../components/CreateIncident.vue'
import IncidentDetail from '../components/IncidentDetail.vue'

const routes = [
  {
    path: '/',
    name: 'IncidentList',
    component: IncidentList
  },
  {
    path: '/current',
    name: 'CurrentIncidents',
    component: CurrentIncidents
  },
  {
    path: '/create',
    name: 'CreateIncident',
    component: CreateIncident
  },
  {
    path: '/incident/:id',
    name: 'IncidentDetail',
    component: IncidentDetail,
    props: true
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
