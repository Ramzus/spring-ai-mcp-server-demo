import axios from 'axios'

const API_BASE_URL = process.env.VUE_APP_API_BASE_URL || 'http://localhost:8083/api'

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
})

// Request interceptor
apiClient.interceptors.request.use(
  config => {
    console.log('Making request to:', config.url)
    return config
  },
  error => {
    console.error('Request error:', error)
    return Promise.reject(error)
  }
)

// Response interceptor
apiClient.interceptors.response.use(
  response => {
    return response
  },
  error => {
    console.error('Response error:', error.response?.data || error.message)
    return Promise.reject(error)
  }
)

export default {
  // Get all incidents
  getAllIncidents() {
    return apiClient.get('/incidents')
  },

  // Get current incidents (OPEN or IN_PROGRESS)
  getCurrentIncidents() {
    return apiClient.get('/incidents/current')
  },

  // Get incidents by status
  getIncidentsByStatus(status) {
    return apiClient.get(`/incidents/status/${status}`)
  },

  // Get incidents by severity
  getIncidentsBySeverity(severity) {
    return apiClient.get(`/incidents/severity/${severity}`)
  },

  // Get incident by ID
  getIncidentById(id) {
    return apiClient.get(`/incidents/${id}`)
  },

  // Create new incident
  createIncident(incidentData) {
    return apiClient.post('/incidents', incidentData)
  },

  // Update incident
  updateIncident(id, incidentData) {
    return apiClient.put(`/incidents/${id}`, incidentData)
  },

  // Delete incident
  deleteIncident(id) {
    return apiClient.delete(`/incidents/${id}`)
  },

  // Health check
  healthCheck() {
    return apiClient.get('/incidents/health')
  }
}
