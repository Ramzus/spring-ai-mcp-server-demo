<template>
  <div>
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
      <router-link to="/" class="btn btn-primary mt-3">
        <i class="fas fa-arrow-left me-2"></i>Back to List
      </router-link>
    </div>

    <!-- Incident Detail -->
    <div v-else-if="incident">
      <div class="d-flex justify-content-between align-items-start mb-4">
        <div>
          <h2>Incident #{{ incident.id }}</h2>
          <p class="text-muted mb-0">{{ incident.title }}</p>
        </div>
        <div class="d-flex gap-2">
          <button class="btn btn-outline-primary" @click="showEditModal = true">
            <i class="fas fa-edit me-2"></i>Edit
          </button>
          <router-link to="/" class="btn btn-outline-secondary">
            <i class="fas fa-arrow-left me-2"></i>Back to List
          </router-link>
        </div>
      </div>

      <div class="row">
        <div class="col-md-8">
          <div class="card">
            <div class="card-header">
              <h5 class="card-title mb-0">Incident Details</h5>
            </div>
            <div class="card-body">
              <div class="row mb-3">
                <div class="col-sm-3"><strong>Status:</strong></div>
                <div class="col-sm-9">
                  <span :class="getStatusBadgeClass(incident.status)" class="badge">
                    {{ formatStatus(incident.status) }}
                  </span>
                </div>
              </div>
              <div class="row mb-3">
                <div class="col-sm-3"><strong>Severity:</strong></div>
                <div class="col-sm-9">
                  <span :class="getSeverityBadgeClass(incident.severity)" class="badge">
                    {{ incident.severity }}
                  </span>
                </div>
              </div>
              <div class="row mb-3">
                <div class="col-sm-3"><strong>Description:</strong></div>
                <div class="col-sm-9">{{ incident.description || 'No description provided' }}</div>
              </div>
              <div class="row mb-3">
                <div class="col-sm-3"><strong>Reporter:</strong></div>
                <div class="col-sm-9">{{ incident.reporterName }}</div>
              </div>              <div class="row mb-3">
                <div class="col-sm-3"><strong>Assigned To:</strong></div>
                <div class="col-sm-9">{{ incident.assignedTo || 'Unassigned' }}</div>
              </div>
              <div class="row mb-3">
                <div class="col-sm-3"><strong>Resolution:</strong></div>
                <div class="col-sm-9">{{ incident.resolution || 'No resolution provided' }}</div>
              </div>
              <div class="row mb-3">
                <div class="col-sm-3"><strong>Created:</strong></div>
                <div class="col-sm-9">{{ formatDateTime(incident.createdDate) }}</div>
              </div>
              <div class="row">
                <div class="col-sm-3"><strong>Last Updated:</strong></div>
                <div class="col-sm-9">{{ formatDateTime(incident.updatedDate) }}</div>
              </div>
            </div>
          </div>
        </div>

        <div class="col-md-4">
          <div class="card">
            <div class="card-header">
              <h5 class="card-title mb-0">Quick Actions</h5>
            </div>
            <div class="card-body">
              <div class="d-grid gap-2">
                <button 
                  v-if="incident.status === 'OPEN'" 
                  class="btn btn-warning"
                  @click="updateStatus('IN_PROGRESS')"
                  :disabled="updating"
                >
                  <i class="fas fa-play me-2"></i>Start Progress
                </button>
                <button 
                  v-if="incident.status === 'IN_PROGRESS'" 
                  class="btn btn-success"
                  @click="updateStatus('RESOLVED')"
                  :disabled="updating"
                >
                  <i class="fas fa-check me-2"></i>Mark Resolved
                </button>
                <button 
                  v-if="incident.status === 'RESOLVED'" 
                  class="btn btn-secondary"
                  @click="updateStatus('CLOSED')"
                  :disabled="updating"
                >
                  <i class="fas fa-times me-2"></i>Close Incident
                </button>
                <button 
                  v-if="incident.status !== 'OPEN'" 
                  class="btn btn-outline-primary"
                  @click="updateStatus('OPEN')"
                  :disabled="updating"
                >
                  <i class="fas fa-undo me-2"></i>Reopen
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Edit Modal -->
      <div v-if="showEditModal" class="modal fade show d-block" tabindex="-1" style="background-color: rgba(0,0,0,0.5)">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">Edit Incident</h5>
              <button type="button" class="btn-close" @click="closeEditModal"></button>
            </div>
            <form @submit.prevent="updateIncident">
              <div class="modal-body">
                <div class="mb-3">
                  <label class="form-label">Title</label>
                  <input 
                    type="text" 
                    class="form-control" 
                    v-model="editForm.title"
                    maxlength="255"
                  >
                </div>
                <div class="mb-3">
                  <label class="form-label">Description</label>
                  <textarea 
                    class="form-control" 
                    v-model="editForm.description"
                    rows="3"
                    maxlength="1000"
                  ></textarea>
                </div>
                <div class="mb-3">
                  <label class="form-label">Severity</label>
                  <select class="form-select" v-model="editForm.severity">
                    <option value="LOW">Low</option>
                    <option value="MEDIUM">Medium</option>
                    <option value="HIGH">High</option>
                    <option value="CRITICAL">Critical</option>
                  </select>
                </div>
                <div class="mb-3">
                  <label class="form-label">Status</label>
                  <select class="form-select" v-model="editForm.status">
                    <option value="OPEN">Open</option>
                    <option value="IN_PROGRESS">In Progress</option>
                    <option value="RESOLVED">Resolved</option>
                    <option value="CLOSED">Closed</option>
                  </select>
                </div>                <div class="mb-3">
                  <label class="form-label">Assigned To</label>
                  <input 
                    type="text" 
                    class="form-control" 
                    v-model="editForm.assignedTo"
                    maxlength="100"
                  >
                </div>
                <div class="mb-3">
                  <label class="form-label">Resolution</label>
                  <textarea 
                    class="form-control" 
                    v-model="editForm.resolution"
                    rows="3"
                    maxlength="1000"
                    placeholder="Describe how this incident was resolved..."
                  ></textarea>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" @click="closeEditModal">Cancel</button>
                <button type="submit" class="btn btn-primary" :disabled="updating">
                  <span v-if="updating" class="spinner-border spinner-border-sm me-2"></span>
                  Save Changes
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import IncidentService from '../services/IncidentService'

export default {
  name: 'IncidentDetail',
  props: {
    id: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      incident: null,
      loading: true,
      error: null,
      updating: false,
      showEditModal: false,
      editForm: {}
    }
  },
  async mounted() {
    await this.loadIncident()
  },
  watch: {
    id: {
      handler() {
        this.loadIncident()
      }
    }
  },
  methods: {
    async loadIncident() {
      try {
        this.loading = true
        this.error = null
        const response = await IncidentService.getIncidentById(this.id)
        this.incident = response.data
        this.initEditForm()
      } catch (error) {
        if (error.response && error.response.status === 404) {
          this.error = 'Incident not found'
        } else {
          this.error = 'Failed to load incident. Please try again later.'
        }
        console.error('Error loading incident:', error)
      } finally {
        this.loading = false
      }
    },
    async updateStatus(newStatus) {
      try {
        this.updating = true
        const updateData = { status: newStatus }
        const response = await IncidentService.updateIncident(this.id, updateData)
        this.incident = response.data
        this.initEditForm()
      } catch (error) {
        console.error('Error updating incident status:', error)
        // You might want to show an error message here
      } finally {
        this.updating = false
      }
    },
    async updateIncident() {
      try {
        this.updating = true
        const response = await IncidentService.updateIncident(this.id, this.editForm)
        this.incident = response.data
        this.closeEditModal()
      } catch (error) {
        console.error('Error updating incident:', error)
        // You might want to show an error message here
      } finally {
        this.updating = false
      }
    },    initEditForm() {
      if (this.incident) {
        this.editForm = {
          title: this.incident.title,
          description: this.incident.description,
          severity: this.incident.severity,
          status: this.incident.status,
          assignedTo: this.incident.assignedTo,
          resolution: this.incident.resolution
        }
      }
    },
    closeEditModal() {
      this.showEditModal = false
      this.initEditForm()
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
    formatDateTime(dateString) {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleString()
    }
  }
}
</script>
