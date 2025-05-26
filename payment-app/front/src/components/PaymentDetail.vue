<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import PaymentService, { type Payment, type Order } from '../services/PaymentService';
import './PaymentDetail.css';

// Props
const props = defineProps<{
  paymentId: number;
  onClose: () => void;
}>();

// State
const payment = ref<Payment | null>(null);
const order = ref<Order | null>(null);
const loading = ref(true);
const error = ref<string | null>(null);

// Format currency for display
const formatCurrency = (amount: number) => {
  return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'EUR'
  }).format(amount);
};

// Format date for display
const formatDate = (dateStr: string) => {
  const date = new Date(dateStr);
  return new Intl.DateTimeFormat('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  }).format(date);
};

// Total order amount computation
const orderTotal = computed(() => {
  if (!order.value?.orderItems) return 0;
  return order.value.orderItems.reduce((total, item) => total + (item.price * item.quantity), 0);
});

// Check if payment amount matches order total
const paymentMatchesOrder = computed(() => {
  if (!payment.value || !order.value) return true;
  return Math.abs(payment.value.totalAmount - orderTotal.value) < 0.01; // Allow for small floating point differences
});

// Status CSS classes
const statusClass = computed(() => {
  if (!payment.value) return '';
  return `status-${payment.value.status.toLowerCase()}`;
});

// Load payment and order details
const loadPaymentDetails = async () => {
  loading.value = true;
  error.value = null;
  
  try {
    const result = await PaymentService.getEnrichedPayment(props.paymentId);
    payment.value = result.payment;
    order.value = result.order;
  } catch (err) {
    console.error('Error fetching payment details:', err);
    error.value = 'Failed to load payment details. Please try again later.';
  } finally {
    loading.value = false;
  }
};

// Retry payment
const retryPayment = async () => {
  if (!payment.value) return;
  
  try {
    loading.value = true;
    const updatedPayment = await PaymentService.retryPayment(payment.value.paymentId);
    payment.value = updatedPayment;
  } catch (err) {
    console.error('Error retrying payment:', err);
    error.value = 'Failed to retry payment. Please try again later.';
  } finally {
    loading.value = false;
  }
};

// Initialize component
onMounted(loadPaymentDetails);
</script>

<template>  <div class="payment-detail" @click="onClose">
    <div class="modal-content" @click.stop>
      <div class="modal-header">
        <h2 class="modal-title">Payment Details</h2>
        <button class="close-button" @click="onClose">&times;</button>
      </div>
      
      <div class="modal-body">
        <!-- Loading state -->
        <div v-if="loading" class="loading-container">
          <div class="loading-spinner"></div>
          <p>Loading payment details...</p>
        </div>
        
        <!-- Error state -->
        <div v-else-if="error" class="error-container">
          <p class="error-message">{{ error }}</p>
          <button class="action-button" @click="loadPaymentDetails">Try Again</button>
        </div>
        
        <!-- Payment details -->
        <div v-else-if="payment" class="payment-info">
          <div class="detail-header">
            <div class="status-badge" :class="statusClass">
              {{ payment.status }}
            </div>
            <div class="payment-id">
              Payment #{{ payment.paymentId }}
            </div>
          </div>
          
          <div class="detail-section payment-section">
            <h3>Payment Information</h3>
            <div class="detail-row">
              <span class="detail-label">Customer:</span>
              <span class="detail-value">{{ payment.customerName }}</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">Date:</span>
              <span class="detail-value">{{ formatDate(payment.paymentDate) }}</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">Amount:</span>
              <span class="detail-value amount">{{ formatCurrency(payment.totalAmount) }}</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">Order ID:</span>
              <span class="detail-value">{{ payment.orderId }}</span>
            </div>
          </div>
          
          <!-- Order details if available -->
          <div v-if="order" class="detail-section order-section">
            <h3>Related Order</h3>
            <div class="detail-row">
              <span class="detail-label">Order Date:</span>
              <span class="detail-value">{{ formatDate(order.orderDate) }}</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">Order Status:</span>
              <span class="detail-value">{{ order.status }}</span>
            </div>
            
            <!-- Order items -->
            <h4>Items</h4>
            <table class="items-table">
              <thead>
                <tr>
                  <th>Product</th>
                  <th>Quantity</th>
                  <th>Price</th>
                  <th>Total</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in order.orderItems" :key="item.id">
                  <td>{{ item.productName }}</td>
                  <td>{{ item.quantity }}</td>
                  <td>{{ formatCurrency(item.price) }}</td>
                  <td>{{ formatCurrency(item.price * item.quantity) }}</td>
                </tr>
              </tbody>
              <tfoot>
                <tr>
                  <th colspan="3">Total</th>
                  <td>{{ formatCurrency(orderTotal) }}</td>
                </tr>
              </tfoot>
            </table>
            
            <!-- Payment verification -->
            <div v-if="!paymentMatchesOrder" class="warning-message">
              Warning: Payment amount does not match order total!
            </div>
          </div>
          
          <div v-else class="detail-section order-not-found">
            <h3>Related Order</h3>
            <p>Order information unavailable</p>
          </div>
          
          <!-- Actions -->
          <div class="actions">
            <button 
              v-if="payment.status === 'FAILED'" 
              class="action-button retry"
              @click="retryPayment"
            >
              Retry Payment
            </button>
            <button class="action-button" @click="onClose">Close</button>
          </div>
        </div>
        
        <div v-else class="not-found">
          <p>Payment not found</p>
          <button class="action-button" @click="onClose">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>


