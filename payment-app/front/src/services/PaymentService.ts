// PaymentService.ts - Service for payment API calls and integration with order service
import axios from 'axios';

// Types
export interface Payment {
  paymentId: number;
  orderId: number;
  customerName: string;
  paymentDate: string;
  totalAmount: number;
  status: string;
}

export interface Order {
  orderId: number;
  customerName: string;
  orderDate: string;
  orderItems: OrderItem[];
  status: string;
}

export interface OrderItem {
  id: number;
  productName: string;
  quantity: number;
  price: number;
}

// Configuration
const PAYMENT_API_URL = 'http://localhost:8082';
const ORDER_API_URL = 'http://localhost:8081';  // Order service URL

class PaymentService {
  /**
   * Get all payments
   */
  async getAllPayments(): Promise<Payment[]> {
    try {
      const response = await axios.get<Payment[]>(`${PAYMENT_API_URL}/payments`);
      return response.data;
    } catch (error) {
      console.error("Error fetching payments:", error);
      throw error;
    }
  }

  /**
   * Get payment by ID
   */
  async getPayment(id: number): Promise<Payment> {
    try {
      const response = await axios.get<Payment>(`${PAYMENT_API_URL}/payments/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching payment with id ${id}:`, error);
      throw error;
    }
  }

  /**
   * Create a new payment
   */
  async createPayment(payment: Omit<Payment, 'paymentId'>): Promise<Payment> {
    try {
      const response = await axios.post<Payment>(`${PAYMENT_API_URL}/payments`, payment);
      return response.data;
    } catch (error) {
      console.error("Error creating payment:", error);
      throw error;
    }
  }

  /**
   * Update an existing payment
   */
  async updatePayment(payment: Payment): Promise<Payment> {
    try {
      const response = await axios.put<Payment>(
        `${PAYMENT_API_URL}/payments/${payment.paymentId}`, 
        payment
      );
      return response.data;
    } catch (error) {
      console.error(`Error updating payment with id ${payment.paymentId}:`, error);
      throw error;
    }
  }

  /**
   * Retry a failed payment
   */
  async retryPayment(id: number): Promise<Payment> {
    try {
      const response = await axios.post<Payment>(`${PAYMENT_API_URL}/payments/${id}/retry`);
      return response.data;
    } catch (error) {
      console.error(`Error retrying payment with id ${id}:`, error);
      throw error;
    }
  }

  /**
   * Delete a payment
   */
  async deletePayment(id: number): Promise<void> {
    try {
      await axios.delete(`${PAYMENT_API_URL}/payments/${id}`);
    } catch (error) {
      console.error(`Error deleting payment with id ${id}:`, error);
      throw error;
    }
  }

  /**
   * Get payments by order ID
   */
  async getPaymentsByOrderId(orderId: number): Promise<Payment[]> {
    try {
      const response = await axios.get<Payment[]>(`${PAYMENT_API_URL}/payments/order/${orderId}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching payments for order ${orderId}:`, error);
      throw error;
    }
  }

  /**
   * Get order details for a payment (integration with order service)
   */
  async getOrderDetails(orderId: number): Promise<Order | null> {
    try {
      const response = await axios.get<Order>(`${ORDER_API_URL}/orders/${orderId}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching order details for order ${orderId}:`, error);
      // Return null instead of throwing to handle case where order service is unavailable
      return null;
    }
  }

  /**
   * Get enriched payment data with order details
   */
  async getEnrichedPayment(paymentId: number): Promise<{ payment: Payment, order: Order | null }> {
    const payment = await this.getPayment(paymentId);
    let order = null;
    
    try {
      order = await this.getOrderDetails(payment.orderId);
    } catch (error) {
      // Silently handle error - order data is optional
      console.warn(`Could not fetch order data for payment ${paymentId}`, error);
    }

    return { payment, order };
  }
}

// Create singleton instance
export default new PaymentService();
