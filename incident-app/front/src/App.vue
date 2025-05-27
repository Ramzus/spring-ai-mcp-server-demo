<template>
  <div id="app">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
      <div class="container">
        <router-link class="navbar-brand" to="/">
          <i class="fas fa-exclamation-triangle me-2"></i>
          Incident Management
        </router-link>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav me-auto">
            <li class="nav-item">
              <router-link class="nav-link" to="/">All Incidents</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/current">Current Incidents</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/create">Create Incident</router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <main class="container mt-4">
      <router-view />
    </main>

    <footer class="bg-light text-center text-muted py-3 mt-5">
      <div class="container">
        <small>&copy; 2024 Incident Management System. Built with Vue.js & Spring Boot.</small>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

main {
  flex: 1;
}

.router-link-active {
  font-weight: bold;
}

.severity-badge {
  font-size: 0.8rem;
}

.status-badge {
  font-size: 0.8rem;
}
</style>
