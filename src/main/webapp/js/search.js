/* Search JS */
document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById('heroSearch');
    if (searchInput) {
        searchInput.addEventListener('keydown', (e) => {
            if (e.key === 'Enter') {
                e.preventDefault();
                const q = searchInput.value.trim();
                if (q) window.location.href = searchInput.dataset.action + '?q=' + encodeURIComponent(q);
            }
        });
    }

    const searchBtn = document.getElementById('heroSearchBtn');
    if (searchBtn) {
        searchBtn.addEventListener('click', () => {
            const q = searchInput.value.trim();
            if (q) window.location.href = searchInput.dataset.action + '?q=' + encodeURIComponent(q);
        });
    }
});
