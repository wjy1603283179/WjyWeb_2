<template>
  <router-view />
</template>

<script setup>
// No specific script needed for App.vue in this basic setup,
// as layouts will handle the main structure.
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap');

/* Global styles */
body {
  margin: 0;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #34495e; /* Darker, more modern text color */
  background-color: #f0f2f5; /* A light, neutral background */
  line-height: 1.6;
}

#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  transition: background-color 0.3s ease; /* Smooth background transitions */
}

/* Basic reset and enhancements */
a {
  text-decoration: none;
  color: #3498db; /* Consistent link color */
  transition: color 0.2s ease-in-out;
}

a:hover {
  color: #2980b9;
}

button,
input[type="submit"],
input[type="button"] {
  cursor: pointer;
  font-family: inherit;
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 6px;
  background-color: #3498db;
  color: white;
  font-size: 1rem;
  font-weight: 500;
  transition: background-color 0.2s ease-in-out, transform 0.1s ease;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

button:hover,
input[type="submit"]:hover,
input[type="button"]:hover {
  background-color: #2980b9;
  transform: translateY(-1px);
}

button:active,
input[type="submit"]:active,
input[type="button"]:active {
  transform: translateY(0);
}

/* Utility classes for animations (can be expanded) */
.fade-in {
  animation: fadeInAnimation 0.5s ease-in-out forwards;
}

@keyframes fadeInAnimation {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

.slide-up {
  animation: slideUpAnimation 0.5s ease-in-out forwards;
}

@keyframes slideUpAnimation {
  from {
    transform: translateY(20px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

/* Card style (can be used across components) */
.card {
  background-color: #ffffff;
  border-radius: 8px;
  padding: 1.5rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: box-shadow 0.3s ease-in-out, transform 0.3s ease-in-out;
}

.card:hover {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);
  transform: translateY(-3px);
}

/* Global scrollbar styling (optional, for a more modern feel) */
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

::-webkit-scrollbar-track {
  background: #f0f2f5; /* Match body background */
  border-radius: 10px;
}

::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 10px;
}

::-webkit-scrollbar-thumb:hover {
  background: #a8a8a8;
}

/* Input field styling */
input[type="text"],
input[type="email"],
input[type="password"],
textarea {
  font-family: inherit;
  padding: 0.75rem 1rem;
  border: 1px solid #d1d5db;
  border-radius: 6px;
  font-size: 1rem;
  color: #374151;
  background-color: #fff;
  transition: border-color 0.2s ease-in-out, box-shadow 0.2s ease-in-out;
  width: 100%; /* Default to full width, can be overridden */
  box-sizing: border-box; /* Include padding and border in the element's total width and height */
}

input[type="text"]:focus,
input[type="email"]:focus,
input[type="password"]:focus,
textarea:focus {
  outline: none;
  border-color: #3498db;
  box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.2);
}

textarea {
  resize: vertical; /* Allow vertical resizing, disable horizontal */
  min-height: 100px;
}


/* Headings */
h1, h2, h3, h4, h5, h6 {
  color: #2c3e50; /* Slightly darker than body text for emphasis */
  font-weight: 600;
  margin-top: 1.5em; /* Add some space above headings */
  margin-bottom: 0.75em;
}

h1 { font-size: 2.2rem; }
h2 { font-size: 1.8rem; }
h3 { font-size: 1.5rem; }
h4 { font-size: 1.2rem; }

</style>
