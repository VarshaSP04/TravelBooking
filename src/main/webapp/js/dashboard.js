/* Dashboard JS */
document.addEventListener('DOMContentLoaded', () => {
    // Animate stat numbers
    document.querySelectorAll('.count-up').forEach(el => {
        const target = parseInt(el.dataset.target || el.textContent);
        animateCountUp(el, target);
    });
});
