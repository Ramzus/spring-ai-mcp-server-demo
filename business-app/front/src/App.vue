<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'

interface Order {
  id: number
  customerName: string
  orderDate: string
  totalAmount: number
  photoUrl: string
  status: string
}

// State variables
const orders = ref<Order[]>([])
const loading = ref(true)
const error = ref<string | null>(null)

// Default placeholder images for orders that don't have photos (DIY, home improvement, tools, Leroy Merlin context)
const defaultPhotoUrls = [
  'https://images.unsplash.com/photo-1503389152951-9c3d0c6b8c9a?auto=format&fit=crop&w=450&q=80', // paint supplies
  'https://images.unsplash.com/photo-1515378791036-0648a3ef77b2?auto=format&fit=crop&w=450&q=80', // tools
  'https://images.unsplash.com/photo-1464983953574-0892a716854b?auto=format&fit=crop&w=450&q=80', // home improvement
  'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=450&q=80', // woodwork
  'https://images.unsplash.com/photo-1519125323398-675f0ddb6308?auto=format&fit=crop&w=450&q=80', // garden
  'https://images.unsplash.com/photo-1465101178521-c1a9136a3b99?auto=format&fit=crop&w=450&q=80', // power tools
  'https://images.unsplash.com/photo-1515168833906-d2a3b82b1e1c?auto=format&fit=crop&w=450&q=80', // paint roller
  'https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=450&q=80', // construction
  'https://images.unsplash.com/photo-1526178613658-3f1622045557?auto=format&fit=crop&w=450&q=80', // garden tools
  'https://images.unsplash.com/photo-1465101046530-73398c7f28ca?auto=format&fit=crop&w=450&q=80', // hardware
  'https://images.unsplash.com/photo-1500534314209-a25ddb2bd429?auto=format&fit=crop&w=450&q=80', // home decor
  'https://images.unsplash.com/photo-1519125323398-675f0ddb6308?auto=format&fit=crop&w=450&q=80'  // garden
];

function getRandomPhotoUrl() {
  const idx = Math.floor(Math.random() * defaultPhotoUrls.length);
  return defaultPhotoUrls[idx];
}

// Default fallback data
const fallbackOrders = [
  { id: 1, customerName: 'John Doe', orderDate: '2023-10-01', totalAmount: 150.0, photoUrl: getRandomPhotoUrl(), status: 'Pending' },
  { id: 2, customerName: 'Jane Smith', orderDate: '2023-10-02', totalAmount: 200.0, photoUrl: getRandomPhotoUrl(), status: 'Shipped' },
  { id: 3, customerName: 'Alice Johnson', orderDate: '2023-10-03', totalAmount: 300.0, photoUrl: getRandomPhotoUrl(), status: 'Delivered' },
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
      .map(order => ({
        ...order,
        photoUrl: order.photoUrl || getRandomPhotoUrl() // Use random photo if missing
      }))
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
          :key="order.id"
          class="order-card"
        >
          <img :src="order.photoUrl" alt="Order Photo" class="order-photo" />
          <div class="order-details">
            <h2>{{ order.customerName }}</h2>
            <p><strong>Order Date:</strong> {{ order.orderDate }}</p>
            <p><strong>Total Amount:</strong> ${{ order.totalAmount.toFixed(2) }}</p>
            <p><strong>Status:</strong> <span :class="`status-${order.status?.toLowerCase()}`">{{ order.status }}</span></p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>