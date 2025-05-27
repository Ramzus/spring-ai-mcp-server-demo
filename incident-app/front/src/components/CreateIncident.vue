<template>
  <div>
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h2>Create New Incident</h2>
      <router-link to="/" class="btn btn-outline-secondary">
        <i class="fas fa-arrow-left me-2"></i>Back to List
      </router-link>
    </div>

    <!-- Success Message -->
    <div v-if="successMessage" class="alert alert-success alert-dismissible fade show" role="alert">
      <i class="fas fa-check-circle me-2"></i>
      {{ successMessage }}
      <button type="button" class="btn-close" @click="successMessage = ''" aria-label="Close"></button>
    </div>

    <!-- Error Message -->
    <div v-if="error" class="alert alert-danger alert-dismissible fade show" role="alert">
      <i class="fas fa-exclamation-triangle me-2"></i>
      {{ error }}
      <button type="button" class="btn-close" @click="error = ''" aria-label="Close"></button>
    </div>

    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="card">
          <div class="card-body">
            <form @submit.prevent="createIncident">
              <div class="mb-3">
                <label for="title" class="form-label">Title <span class="text-danger">*</span></label>
                <input
                  type="text"
                  class="form-control"
                  id="title"
                  v-model="form.title"
                  :class="{ 'is-invalid': validation.title }"
                  placeholder="Enter incident title"
                  maxlength="255"
                  required
                >
                <div v-if="validation.title" class="invalid-feedback">
                  {{ validation.title }}
                </div>
              </div>

              <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <textarea
                  class="form-control"
                  id="description"
                  v-model="form.description"
                  :class="{ 'is-invalid': validation.description }"
                  placeholder="Enter incident description"
                  rows="4"
                  maxlength="1000"
                ></textarea>
                <div v-if="validation.description" class="invalid-feedback">
                  {{ validation.description }}
                </div>
                <div class="form-text">{{ form.description.length }}/1000 characters</div>
              </div>

              <div class="row">
                <div class="col-md-6">
                  <div class="mb-3">
                    <label for="severity" class="form-label">Severity <span class="text-danger">*</span></label>
                    <select
                      class="form-select"
                      id="severity"
                      v-model="form.severity"
                      :class="{ 'is-invalid': validation.severity }"
                      required
                    >
                      <option value="">Select severity</option>
                      <option value="LOW">Low</option>
                      <option value="MEDIUM">Medium</option>
                      <option value="HIGH">High</option>
                      <option value="CRITICAL">Critical</option>
                    </select>
                    <div v-if="validation.severity" class="invalid-feedback">
                      {{ validation.severity }}
                    </div>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="mb-3">
                    <label for="reporterName" class="form-label">Reporter Name <span class="text-danger">*</span></label>
                    <input
                      type="text"
                      class="form-control"
                      id="reporterName"
                      v-model="form.reporterName"
                      :class="{ 'is-invalid': validation.reporterName }"
                      placeholder="Enter reporter name"
                      maxlength="100"
                      required
                    >
                    <div v-if="validation.reporterName" class="invalid-feedback">
                      {{ validation.reporterName }}
                    </div>
                  </div>
                </div>
              </div>              <div class="mb-3">
                <label for="assignedTo" class="form-label">Assigned To</label>
                <input
                  type="text"
                  class="form-control"
                  id="assignedTo"
                  v-model="form.assignedTo"
                  :class="{ 'is-invalid': validation.assignedTo }"
                  placeholder="Enter person or team name (optional)"
                  maxlength="100"
                >
                <div v-if="validation.assignedTo" class="invalid-feedback">
                  {{ validation.assignedTo }}
                </div>
              </div>

              <div class="mb-3">
                <label for="resolution" class="form-label">Resolution</label>
                <textarea
                  class="form-control"
                  id="resolution"
                  v-model="form.resolution"
                  :class="{ 'is-invalid': validation.resolution }"
                  placeholder="Enter resolution details (optional)"
                  rows="3"
                  maxlength="1000"
                ></textarea>
                <div v-if="validation.resolution" class="invalid-feedback">
                  {{ validation.resolution }}
                </div>
                <div class="form-text">{{ form.resolution.length }}/1000 characters</div>
              </div>

              <div class="d-flex justify-content-end gap-2">
                <button type="button" class="btn btn-secondary" @click="resetForm">
                  Reset
                </button>
                <button type="submit" class="btn btn-primary" :disabled="submitting">
                  <span v-if="submitting" class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                  <i v-else class="fas fa-save me-2"></i>
                  {{ submitting ? 'Creating...' : 'Create Incident' }}
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
  name: 'CreateIncident',
  data() {
    return {      form: {
        title: '',
        description: '',
        severity: '',
        reporterName: '',
        assignedTo: '',
        resolution: ''
      },
      validation: {},
      submitting: false,
      error: '',
      successMessage: ''
    }
  },
  methods: {
    async createIncident() {
      if (!this.validateForm()) {
        return
      }

      try {
        this.submitting = true
        this.error = ''
        this.successMessage = ''

        const response = await IncidentService.createIncident(this.form)
        
        this.successMessage = `Incident #${response.data.id} created successfully!`
        this.resetForm()
        
        // Redirect to the created incident after a short delay
        setTimeout(() => {
          this.$router.push(`/incident/${response.data.id}`)
        }, 2000)

      } catch (error) {
        if (error.response && error.response.status === 400) {
          // Handle validation errors from backend
          this.handleValidationErrors(error.response.data)
        } else {
          this.error = 'Failed to create incident. Please try again later.'
        }
        console.error('Error creating incident:', error)
      } finally {
        this.submitting = false
      }
    },
    validateForm() {
      this.validation = {}
      let isValid = true

      if (!this.form.title.trim()) {
        this.validation.title = 'Title is required'
        isValid = false
      } else if (this.form.title.length > 255) {
        this.validation.title = 'Title must not exceed 255 characters'
        isValid = false
      }

      if (this.form.description.length > 1000) {
        this.validation.description = 'Description must not exceed 1000 characters'
        isValid = false
      }

      if (!this.form.severity) {
        this.validation.severity = 'Severity is required'
        isValid = false
      }

      if (!this.form.reporterName.trim()) {
        this.validation.reporterName = 'Reporter name is required'
        isValid = false
      } else if (this.form.reporterName.length > 100) {
        this.validation.reporterName = 'Reporter name must not exceed 100 characters'
        isValid = false
      }      if (this.form.assignedTo.length > 100) {
        this.validation.assignedTo = 'Assigned to must not exceed 100 characters'
        isValid = false
      }

      if (this.form.resolution.length > 1000) {
        this.validation.resolution = 'Resolution must not exceed 1000 characters'
        isValid = false
      }

      return isValid
    },
    handleValidationErrors(errorData) {
      // Handle backend validation errors
      if (errorData.errors) {
        errorData.errors.forEach(error => {
          const field = error.field
          if (field && this.form.hasOwnProperty(field)) {
            this.validation[field] = error.defaultMessage
          }
        })
      } else {
        this.error = errorData.message || 'Validation failed'
      }
    },    resetForm() {
      this.form = {
        title: '',
        description: '',
        severity: '',
        reporterName: '',
        assignedTo: '',
        resolution: ''
      }
      this.validation = {}
      this.error = ''
      this.successMessage = ''
    }
  }
}
</script>
