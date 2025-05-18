<template>
  <div class="home-page">
    <h1 class="welcome-title fade-in-down">欢迎来到爱人区</h1>
            <p class="welcome-description fade-in-up">
              爱人区你们赢了
            </p>
            <!-- 功能卡片已根据用户要求移除 -->
            <div class="cool-effects-placeholder">
              <div id="particles-js"></div>
        <!-- 炫酷效果将在这里实现 -->
              <p class="effect-text">✨ 敬请期待更多精彩 ✨</p>
            </div>
    <!-- Add more sophisticated content and components later -->
  </div>
</template>

<script setup>
import { onMounted } from 'vue';

// Helper function to load a script dynamically
function loadScript(src, callback) {
  const script = document.createElement('script');
  script.src = src;
  script.onload = callback;
  script.onerror = () => console.error(`Failed to load script: ${src}`);
  document.head.appendChild(script);
}

onMounted(() => {
  loadScript('https://cdn.jsdelivr.net/particles.js/2.0.0/particles.min.js', () => {
    if (window.particlesJS) {
      window.particlesJS('particles-js', {
        "particles": {
          "number": {
            "value": 80,
            "density": {
              "enable": true,
              "value_area": 800
            }
          },
          "color": {
            "value": "#ffffff"
          },
          "shape": {
            "type": "circle",
            "stroke": {
              "width": 0,
              "color": "#000000"
            },
            "polygon": {
              "nb_sides": 5
            }
          },
          "opacity": {
            "value": 0.5,
            "random": false,
            "anim": {
              "enable": false,
              "speed": 1,
              "opacity_min": 0.1,
              "sync": false
            }
          },
          "size": {
            "value": 3,
            "random": true,
            "anim": {
              "enable": false,
              "speed": 40,
              "size_min": 0.1,
              "sync": false
            }
          },
          "line_linked": {
            "enable": true,
            "distance": 150,
            "color": "#ffffff",
            "opacity": 0.4,
            "width": 1
          },
          "move": {
            "enable": true,
            "speed": 6,
            "direction": "none",
            "random": false,
            "straight": false,
            "out_mode": "out",
            "bounce": false,
            "attract": {
              "enable": false,
              "rotateX": 600,
              "rotateY": 1200
            }
          }
        },
        "interactivity": {
          "detect_on": "canvas",
          "events": {
            "onhover": {
              "enable": true,
              "mode": "repulse"
            },
            "onclick": {
              "enable": true,
              "mode": "push"
            },
            "resize": true
          },
          "modes": {
            "grab": {
              "distance": 400,
              "line_linked": {
                "opacity": 1
              }
            },
            "bubble": {
              "distance": 400,
              "size": 40,
              "duration": 2,
              "opacity": 8,
              "speed": 3
            },
            "repulse": {
              "distance": 200,
              "duration": 0.4
            },
            "push": {
              "particles_nb": 4
            },
            "remove": {
              "particles_nb": 2
            }
          }
        },
        "retina_detect": true
      });
    } else {
      console.error('particlesJS not loaded');
    }
  });
});
</script>

<style scoped>
#particles-js {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  z-index: 0; /* Ensure it's behind other content */
}

.home-page .welcome-container {
  position: relative; /* Ensure text is above particles */
  z-index: 1;
}
.home-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: calc(100vh - 120px); /* Adjust based on header/footer height */
  padding: 2rem;
  text-align: center;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  overflow: hidden; /* Prevent scrollbars from animations */
}

h1 {
  font-size: 3rem;
  color: #2c3e50;
  margin-bottom: 1.5rem;
  font-weight: 600;
}

p {
  font-size: 1.3rem;
  color: #555;
  margin-bottom: 2.5rem;
  max-width: 600px;
  line-height: 1.6;
}

.cool-effects-placeholder {
  margin-top: 4rem;
  padding: 2rem;
  border-radius: 10px;
}

.effect-text {
  font-size: 1.8rem;
  color: #4a5568;
  font-weight: 500;
  text-shadow: 1px 1px 2px rgba(0,0,0,0.1);
}

/* Animations */
.fade-in-down {
  animation: fadeInDown 0.8s ease-out forwards;
  opacity: 0;
}

.fade-in-up {
  animation: fadeInUp 0.8s ease-out forwards;
  opacity: 0;
}

.delay-1 {
  animation-delay: 0.4s;
}

@keyframes fadeInDown {
  from {
    opacity: 0;
    transform: translateY(-30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Responsive adjustments */
@media (max-width: 768px) {
  h1 {
    font-size: 2.5rem;
  }
  p {
    font-size: 1.1rem;
  }
  .cool-effects-placeholder {
    margin-top: 2rem; /* Adjust spacing for smaller screens */
  }
  .effect-text {
    font-size: 1.5rem;
  }
}

/* Original .features style, renamed to .features-grid or ensure it's not conflicting */
/* .features { */
    /* flex-direction: column; */
    /* align-items: center; */
  /* } */
  /* .feature-card { */
    /* width: 90%; */
    /* max-width: 300px; */
  /* } */
</style>