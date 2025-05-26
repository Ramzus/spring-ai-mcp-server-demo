<template>
  <div>
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h2>Current Incidents</h2>
      <router-link to="/create" class="btn btn-primary">
        <i class="fas fa-plus me-2"></i>Create New Incident
      </router-link>
    </div>

    <div class="alert alert-info" role="alert">
      <i class="fas fa-info-circle me-2"></i>
      Showing incidents with status: <strong>Open</strong> or <strong>In Progress</strong>
    </div>

    <!-- Loading State -->
    <div v-if="loading" class="text-center">
      <div class="spinner-border text-primary" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>

    <!-- Error State -->
    <div v-else-if="error" class="alert alert-danger" role="alert">
      <i class="fas fa-exclamation-triangle me-2"></i>
      {{ error }}
    </div>

    <!-- Current Incidents -->
    <div v-else>
      <div class="row">
        <div v-for="incident in incidents" :key="incident.id" class="col-md-6 col-lg-4 mb-4">
          <div class="card h-100">
            <div class="card-header d-flex justify-content-between align-items-center">
              <small class="text-muted">#{{ incident.id }}</small>
              <span :class="getSeverityBadgeClass(incident.severity)" class="badge">
                {{ incident.severity }}
              </span>
            </div>
            <div class="card-body">
              <h5 class="card-title">{{ incident.title }}</h5>
              <p class="card-text text-muted" style="font-size: 0.9rem;">
                {{ incident.description ? incident.description.substring(0, 100) + (incident.description.length > 100 ? '...' : '') : 'No description' }}
              </p>
              <div class="mt-3">
                <div class="d-flex justify-content-between align-items-center mb-2">
                  <span class="text-muted small">Status:</span>
                  <span :class="getStatusBadgeClass(incident.status)" class="badge">
                    {{ formatStatus(incident.status) }}
                  </span>
                </div>
                <div class="d-flex justify-content-between align-items-center mb-2">
                  <span class="text-muted small">Reporter:</span>
                  <span class="small">{{ incident.reporterName }}</span>
                </div>
                <div class="d-flex justify-content-between align-items-center mb-2">
                  <span class="text-muted small">Assigned to:</span>
                  <span class="small">{{ incident.assignedTo || 'Unassigned' }}</span>
                </div>
                <div class="d-flex justify-content-between align-items-center">
                  <span class="text-muted small">Created:</span>
                  <span class="small">{{ formatDate(incident.createdDate) }}</span>
                </div>
              </div>
            </div>
            <div class="card-footer">
              <router-link :to="`/incident/${incident.id}`" class="btn btn-primary btn-sm w-100">
                <i class="fas fa-eye me-2"></i>View Details
              </router-link>
            </div>
          </div>
        </div>
      </div>

      <!-- Empty State -->
      <div v-if="incidents.length === 0" class="text-center py-5">
        <i class="fas fa-check-circle fa-3x text-success mb-3"></i>
        <h5 class="text-success">All clear!</h5>
        <p class="text-muted">No current incidents to display. All incidents are resolved or closed.</p>
      </div>
    </div>
  </div>
</template>

<script>
import IncidentService from '../services/IncidentService'

export default {
  name: 'CurrentIncidents',
  data() {
    return {
      incidents: [],
      loading: true,
      error: null
    }
  },
  async mounted() {
    await this.loadCurrentIncidents()
  },
  methods: {
    async loadCurrentIncidents() {
      try {
        this.loading = true
        this.error = null
        const response = await IncidentService.getCurrentIncidents()
        this.incidents = response.data
      } catch (error) {
        this.error = 'Failed to load current incidents. Please try again later.'
        console.error('Error loading current incidents:', error)
      } finally {
        this.loading = false
      }
    },
    getSeverityBadgeClass(severity) {
      const classes = {
        CRITICAL: 'bg-danger',
        HIGH: 'bg-warning text-dark',
        MEDIUM: 'bg-info',
        LOW: 'bg-success'
      }
      return classes[severity] || 'bg-secondary'
    },
    getStatusBadgeClass(status) {
      const classes = {
        OPEN: 'bg-primary',
        IN_PROGRESS: 'bg-warning text-dark',
        RESOLVED: 'bg-success',
        CLOSED: 'bg-secondary'
      }
      return classes[status] || 'bg-secondary'
    },
    formatStatus(status) {
      return status.replace('_', ' ')
    },
    formatDate(dateString) {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString()
    }
  }
}
</script>
