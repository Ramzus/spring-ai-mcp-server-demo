<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'

interface Order {
  orderId: number
  customerName: string
  orderDate: string
  totalAmount: number
  numberOfItems: number
  status: string
  paymentStatus: string
}

// State variables
const orders = ref<Order[]>([])
const loading = ref(true)
const error = ref<string | null>(null)
const advancingOrders = ref<Set<number>>(new Set())
const successMessage = ref<string | null>(null)

// Function to fetch orders from API
const fetchOrders = async () => {
  loading.value = true
  error.value = null
  
  try {
    // Replace with your actual API endpoint
    const response = await axios.get<Order[]>('http://localhost:8081/orders')
    
    orders.value = response.data
      .filter(order => order.status !== 'CREATED') // Filter out orders with CREATED status
  } catch (err) {
    console.error('Error fetching orders:', err)
    error.value = 'Failed to load orders. Please try again later.'
  } finally {
    loading.value = false
  }
}

// Function to advance order to next status
const advanceOrder = async (orderId: number) => {
  if (advancingOrders.value.has(orderId)) return
  
  advancingOrders.value.add(orderId)
  
  try {
    await axios.post(`http://localhost:8081/orders/${orderId}/next`)
    
    // Refresh the orders list after successful advancement
    await fetchOrders()
    
    // Show success message
    successMessage.value = `Order ${orderId} advanced successfully!`
    setTimeout(() => {
      successMessage.value = null
    }, 3000)
    
  } catch (err) {
    console.error('Error advancing order:', err)
    // Handle specific error cases
    if (axios.isAxiosError(err) && err.response) {
      const errorMessage = err.response.data?.message || err.response.statusText || 'Unknown error'
      alert(`Error advancing order ${orderId}: ${errorMessage}`)
    } else {
      alert(`Error advancing order ${orderId}. Please try again.`)
    }
  } finally {
    advancingOrders.value.delete(orderId)
  }
}

// Function to check if an order can be advanced
const canAdvanceOrder = (order: Order): boolean => {
  // Orders at FINISHED status cannot be advanced
  if (order.status === 'FINISHED') return false
  
  // Orders at CANCELLED status cannot be advanced
  if (order.status === 'CANCELLED') return false
  
  return true
}

// Fetch orders when component is mounted
onMounted(fetchOrders)
</script>

<template>
  <div class="order-page">
    <header class="page-header">
      <h1 class="beautiful-title">
        <span class="icon">🛒</span>
        <span>Customer <span class="highlight">Orders</span></span>
      </h1>
      <p>Track and manage your orders efficiently</p>
    </header>
    
    <!-- Loading state -->
    <div v-if="loading" class="loading-container">
      <div class="loading-spinner"></div>
      <p>Loading orders...</p>
    </div>
    
    <!-- Error state -->
    <div v-else-if="error" class="error-container">
      <p class="error-message">{{ error }}</p>
      <button class="retry-button" @click="fetchOrders">Retry</button>
    </div>
    
    <!-- Success message -->
    <div v-if="successMessage" class="success-container">
      <p class="success-message">{{ successMessage }}</p>
    </div>
    
    <!-- Orders list -->
    <div v-else>
      <div v-if="orders.length === 0" class="no-orders">
        <p>No orders found.</p>
      </div>
      <div v-else class="order-list">
        <div
          v-for="order in orders"
          :key="order.orderId"
          class="order-card"
        >
          <div class="order-icon">
            <span class="order-number">{{ order.numberOfItems }}</span>
            <small>items</small>
          </div>
          <div class="order-details">
            <h2>{{ order.customerName }}</h2>
            <p><strong>Order Date:</strong> {{ order.orderDate }}</p>
            <p><strong>Number of Items:</strong> {{ order.numberOfItems }}</p>
            <p><strong>Total Amount:</strong> {{ order.totalAmount.toFixed(2) }}€</p>
            <p><strong>Status:</strong> <span :class="`status-${order.status?.toLowerCase()}`">{{ order.status }}</span></p>
            <p><strong>Payment:</strong> <span :class="`payment-${order.paymentStatus?.toLowerCase()}`">{{ order.paymentStatus }}</span></p>
          </div>
          <div class="order-actions">
            <button 
              v-if="canAdvanceOrder(order)"
              @click="advanceOrder(order.orderId)"
              :disabled="advancingOrders.has(order.orderId)"
              class="advance-button"
              :class="{ 'loading': advancingOrders.has(order.orderId) }"
            >
              <span v-if="advancingOrders.has(order.orderId)" class="button-spinner"></span>
              <span v-else>Advance ➡️</span>
            </button>
            <span v-else class="advance-disabled">
              {{ order.status === 'FINISHED' ? 'Finished' : order.status === 'CANCELLED' ? 'Cancelled' : 'Not Available' }}
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>