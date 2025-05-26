<template>
  <div>
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h2>All Incidents</h2>
      <router-link to="/create" class="btn btn-primary">
        <i class="fas fa-plus me-2"></i>Create New Incident
      </router-link>
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

    <!-- Incidents List -->
    <div v-else>
      <!-- Filter Options -->
      <div class="row mb-3">
        <div class="col-md-6">
          <select v-model="filterStatus" @change="applyFilters" class="form-select">
            <option value="">All Statuses</option>
            <option value="OPEN">Open</option>
            <option value="IN_PROGRESS">In Progress</option>
            <option value="RESOLVED">Resolved</option>
            <option value="CLOSED">Closed</option>
          </select>
        </div>
        <div class="col-md-6">
          <select v-model="filterSeverity" @change="applyFilters" class="form-select">
            <option value="">All Severities</option>
            <option value="CRITICAL">Critical</option>
            <option value="HIGH">High</option>
            <option value="MEDIUM">Medium</option>
            <option value="LOW">Low</option>
          </select>
        </div>
      </div>

      <!-- Incidents Table -->
      <div class="table-responsive">
        <table class="table table-striped table-hover">
          <thead class="table-dark">
            <tr>
              <th>ID</th>
              <th>Title</th>
              <th>Severity</th>
              <th>Status</th>
              <th>Reporter</th>
              <th>Assigned To</th>
              <th>Created</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="incident in filteredIncidents" :key="incident.id">
              <td>{{ incident.id }}</td>
              <td>
                <router-link :to="`/incident/${incident.id}`" class="text-decoration-none">
                  {{ incident.title }}
                </router-link>
              </td>
              <td>
                <span :class="getSeverityBadgeClass(incident.severity)" class="badge severity-badge">
                  {{ incident.severity }}
                </span>
              </td>
              <td>
                <span :class="getStatusBadgeClass(incident.status)" class="badge status-badge">
                  {{ formatStatus(incident.status) }}
                </span>
              </td>
              <td>{{ incident.reporterName }}</td>
              <td>{{ incident.assignedTo || 'Unassigned' }}</td>
              <td>{{ formatDate(incident.createdDate) }}</td>
              <td>
                <router-link :to="`/incident/${incident.id}`" class="btn btn-sm btn-outline-primary me-2">
                  View
                </router-link>
              </td>
            </tr>
          </tbody>
        </table>

        <!-- Empty State -->
        <div v-if="filteredIncidents.length === 0" class="text-center py-5">
          <i class="fas fa-inbox fa-3x text-muted mb-3"></i>
          <h5 class="text-muted">No incidents found</h5>
          <p class="text-muted">{{ incidents.length === 0 ? 'No incidents have been created yet.' : 'No incidents match the current filters.' }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import IncidentService from '../services/IncidentService'

export default {
  name: 'IncidentList',
  data() {
    return {
      incidents: [],
      filteredIncidents: [],
      loading: true,
      error: null,
      filterStatus: '',
      filterSeverity: ''
    }
  },
  async mounted() {
    await this.loadIncidents()
  },
  methods: {
    async loadIncidents() {
      try {
        this.loading = true
        this.error = null
        const response = await IncidentService.getAllIncidents()
        this.incidents = response.data
        this.applyFilters()
      } catch (error) {
        this.error = 'Failed to load incidents. Please try again later.'
        console.error('Error loading incidents:', error)
      } finally {
        this.loading = false
      }
    },
    applyFilters() {
      this.filteredIncidents = this.incidents.filter(incident => {
        const statusMatch = !this.filterStatus || incident.status === this.filterStatus
        const severityMatch = !this.filterSeverity || incident.severity === this.filterSeverity
        return statusMatch && severityMatch
      })
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
