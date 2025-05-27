<script setup lang="ts">
import { ref, onMounted } from 'vue'
import PaymentService, { type Payment } from './services/PaymentService'
import PaymentDetail from './components/PaymentDetail.vue'
import './App.css'

// State variables
const payments = ref<Payment[]>([])
const loading = ref(true)
const error = ref<string | null>(null)
const selectedPaymentId = ref<number | null>(null)

// Default payment icons for different statuses
const paymentIcons = {
  PENDING: '⏳',
  COMPLETED: '✅',
  FAILED: '❌'
};

// Default colors for payment statuses
const statusColors = {
  PENDING: '#f39c12',   // Orange
  COMPLETED: '#2ecc71', // Green
  FAILED: '#e74c3c'     // Red
};

// Function to fetch payments from API
const fetchPayments = async () => {
  loading.value = true
  error.value = null
  
  try {
    const data = await PaymentService.getAllPayments()
    payments.value = data
  } catch (err) {
    console.error('Error fetching payments:', err)
    error.value = 'Failed to load payment data. Please try again later.'
  } finally {
    loading.value = false
  }
}

// Function to retry a failed payment
const retryPayment = async (paymentId: number) => {
  try {
    await PaymentService.retryPayment(paymentId)
    // Refresh the payment list after retry
    await fetchPayments()
  } catch (err) {
    console.error('Error retrying payment:', err)
    error.value = 'Failed to retry payment. Please try again later.'
  }
}

// Get appropriate icon for payment status
const getStatusIcon = (status: string) => {
  return paymentIcons[status as keyof typeof paymentIcons] || '❓';
}

// Get appropriate color for payment status
const getStatusColor = (status: string) => {
  return statusColors[status as keyof typeof statusColors] || '#999';
}

// Format currency for display
const formatCurrency = (amount: number) => {
  return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'EUR'
  }).format(amount);
}

// Format date for display
const formatDate = (dateStr: string) => {
  const date = new Date(dateStr);
  return new Intl.DateTimeFormat('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  }).format(date);
}

// Open payment detail modal
const openPaymentDetail = (paymentId: number) => {
  selectedPaymentId.value = paymentId;
}

// Close payment detail modal
const closePaymentDetail = () => {
  selectedPaymentId.value = null;
}

// Fetch payments when component is mounted
onMounted(fetchPayments)
</script>

<template>
  <div class="payment-page">
    <header class="page-header">
      <div class="header-content">
        <h1 class="beautiful-title">
          <span class="icon">💳</span>
          <span>Payment <span class="highlight">Status</span></span>
        </h1>
        <p>Track and manage payment status for customer orders</p>
      </div>
    </header>
    
    <!-- Loading state -->
    <div v-if="loading" class="loading-container">
      <div class="loading-spinner"></div>
      <p>Loading payment data...</p>
    </div>
    
    <!-- Error state -->
    <div v-else-if="error" class="error-container">
      <p class="error-message">{{ error }}</p>
      <button class="retry-button" @click="fetchPayments">Retry</button>
    </div>
    
    <!-- Payments list -->
    <div v-else>
      <div v-if="payments.length === 0" class="no-payments">
        <p>No payment records found.</p>
      </div>
      <div v-else class="payment-list">
        <div
          v-for="payment in payments"
          :key="payment.paymentId"
          class="payment-card"
          @click="openPaymentDetail(payment.paymentId)"
        >
          <div class="payment-header">
            <div class="status-icon" :style="{ backgroundColor: getStatusColor(payment.status) }">
              {{ getStatusIcon(payment.status) }}
            </div>
            <h2>{{ payment.customerName }}</h2>
          </div>
          <div class="payment-details">
            <div class="detail-row">
              <span class="detail-label">Order ID:</span>
              <span class="detail-value">{{ payment.orderId }}</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">Payment Date:</span>
              <span class="detail-value">{{ formatDate(payment.paymentDate) }}</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">Amount:</span>
              <span class="detail-value amount">{{ formatCurrency(payment.totalAmount) }}</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">Status:</span>
              <span class="detail-value status" :style="{ color: getStatusColor(payment.status) }">
                {{ payment.status }}
              </span>
            </div>
          </div>
          <div class="payment-actions" v-if="payment.status === 'FAILED'">
            <button class="retry-payment-button" @click.stop="retryPayment(payment.paymentId)">
              Retry Payment
            </button>
          </div>
        </div>
      </div>
    </div>
    
    <div class="summary-panel">
      <h3>Summary</h3>
      <div class="summary-content">
        <div class="summary-item">
          <span class="summary-label">Total Payments:</span>
          <span class="summary-value">{{ payments.length }}</span>
        </div>
        <div class="summary-item">
          <span class="summary-label">Completed:</span>
          <span class="summary-value">{{ payments.filter(p => p.status === 'COMPLETED').length }}</span>
        </div>
        <div class="summary-item">
          <span class="summary-label">Pending:</span>
          <span class="summary-value">{{ payments.filter(p => p.status === 'PENDING').length }}</span>
        </div>
        <div class="summary-item">
          <span class="summary-label">Failed:</span>
          <span class="summary-value">{{ payments.filter(p => p.status === 'FAILED').length }}</span>
        </div>
        <div class="summary-item total">
          <span class="summary-label">Total Amount:</span>
          <span class="summary-value">{{ formatCurrency(payments.reduce((sum, p) => sum + p.totalAmount, 0)) }}</span>
        </div>
      </div>
    </div>
    
    <!-- Payment Detail Modal -->
    <PaymentDetail 
      v-if="selectedPaymentId !== null" 
      :payment-id="selectedPaymentId" 
      :on-close="closePaymentDetail"
    />
  </div>
</template>