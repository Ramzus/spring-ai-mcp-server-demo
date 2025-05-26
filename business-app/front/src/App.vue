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

// Default fallback data
const fallbackOrders = [
  { orderId: 1, customerName: 'John Doe', orderDate: '2023-10-01', totalAmount: 150.0, numberOfItems: 3, status: 'Pending', paymentStatus: 'PENDING' },
  { orderId: 2, customerName: 'Jane Smith', orderDate: '2023-10-02', totalAmount: 200.0, numberOfItems: 5, status: 'Shipped', paymentStatus: 'COMPLETED' },
  { orderId: 3, customerName: 'Alice Johnson', orderDate: '2023-10-03', totalAmount: 300.0, numberOfItems: 2, status: 'Delivered', paymentStatus: 'COMPLETED' },
]

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
    
    // Fallback data in case of error
    orders.value = fallbackOrders
  } finally {
    loading.value = false
  }
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
            <p><strong>Total Amount:</strong> ${{ order.totalAmount.toFixed(2) }}</p>
            <p><strong>Status:</strong> <span :class="`status-${order.status?.toLowerCase()}`">{{ order.status }}</span></p>
            <p><strong>Payment:</strong> <span :class="`payment-${order.paymentStatus?.toLowerCase()}`">{{ order.paymentStatus }}</span></p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>