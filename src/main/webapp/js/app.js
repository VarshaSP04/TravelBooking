/* ===== Global JS Utilities ===== */
document.addEventListener('DOMContentLoaded', () => {
    initScrollHeader();
    initMobileNav();
    initScrollAnimations();
    // Animate stat numbers on homepage
    document.querySelectorAll('.count-up').forEach(el => {
        const target = parseInt(el.dataset.target || el.textContent);
        if (!isNaN(target) && target > 0) animateCountUp(el, target);
    });
});

/* Header scroll effect */
function initScrollHeader() {
    const header = document.querySelector('.site-header');
    if (!header) return;
    window.addEventListener('scroll', () => {
        header.classList.toggle('scrolled', window.scrollY > 50);
    });
    // Trigger on load
    if (window.scrollY > 50) header.classList.add('scrolled');
}

/* Mobile navigation */
function initMobileNav() {
    const toggle = document.querySelector('.nav-toggle');
    const menu = document.querySelector('.nav-menu');
    if (!toggle || !menu) return;

    toggle.addEventListener('click', () => {
        menu.classList.toggle('open');
    });

    // Close on outside click
    document.addEventListener('click', (e) => {
        if (!menu.contains(e.target) && !toggle.contains(e.target)) {
            menu.classList.remove('open');
        }
    });
}

/* Scroll-triggered animations */
function initScrollAnimations() {
    const targets = document.querySelectorAll('.fade-in, .slide-in-left, .slide-in-right, .scale-in, .stagger');
    if (!targets.length) return;

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('visible');
                observer.unobserve(entry.target);
            }
        });
    }, { threshold: 0.15 });

    targets.forEach(el => observer.observe(el));
}

/* Count-up animation for stat numbers */
function animateCountUp(el, target, duration = 1500) {
    let start = 0;
    const step = (timestamp) => {
        if (!start) start = timestamp;
        const progress = Math.min((timestamp - start) / duration, 1);
        el.textContent = Math.floor(progress * target).toLocaleString();
        if (progress < 1) requestAnimationFrame(step);
    };
    requestAnimationFrame(step);
}

/* Toast notification */
function showToast(message, type = 'info', duration = 3000) {
    const toast = document.createElement('div');
    toast.className = `alert alert-${type}`;
    toast.style.cssText = `position:fixed;top:20px;right:20px;z-index:9999;min-width:300px;animation:slideInRight 0.3s ease;`;
    toast.textContent = message;
    document.body.appendChild(toast);
    setTimeout(() => {
        toast.style.opacity = '0';
        toast.style.transform = 'translateX(100%)';
        toast.style.transition = 'all 0.3s ease';
        setTimeout(() => toast.remove(), 300);
    }, duration);
}

/* Format currency */
function formatCurrency(amount) {
    return '$' + Number(amount).toLocaleString('en-US', { minimumFractionDigits: 2 });
}

/* Format date */
function formatDate(dateStr) {
    return new Date(dateStr).toLocaleDateString('en-US', {
        year: 'numeric', month: 'short', day: 'numeric'
    });
}
