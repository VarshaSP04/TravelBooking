/* Admin JS */
document.addEventListener('DOMContentLoaded', () => {
    // Mobile sidebar toggle
    const sidebarToggle = document.getElementById('adminSidebarToggle');
    const sidebar = document.querySelector('.admin-sidebar');
    if (sidebarToggle && sidebar) {
        sidebarToggle.addEventListener('click', () => sidebar.classList.toggle('open'));
    }

    // Dynamic room type rows
    initDynamicRows('roomTypeRows', 'addRoomType', ['rtType', 'rtPrice', 'rtAvailable']);

    // Dynamic itinerary rows
    initDynamicRows('itineraryRows', 'addItinerary', ['itDay', 'itTitle', 'itDesc']);

    // Confirm delete
    document.querySelectorAll('.btn-delete-confirm').forEach(btn => {
        btn.addEventListener('click', (e) => {
            if (!confirm('Are you sure you want to delete this item?')) {
                e.preventDefault();
            }
        });
    });

    // Animate admin stat numbers
    document.querySelectorAll('.count-up').forEach(el => {
        const target = parseFloat(el.dataset.target || el.textContent);
        if (!isNaN(target)) animateCountUp(el, target);
    });
});

function initDynamicRows(containerId, addBtnId, fieldNames) {
    const container = document.getElementById(containerId);
    const addBtn = document.getElementById(addBtnId);
    if (!container || !addBtn) return;

    addBtn.addEventListener('click', () => {
        const row = document.createElement('div');
        row.className = 'dynamic-row';
        fieldNames.forEach(name => {
            const input = document.createElement('input');
            input.type = name.includes('Price') || name.includes('Day') || name.includes('Available') ? 'number' : 'text';
            input.name = name;
            input.className = 'form-control';
            input.placeholder = name.replace(/^[a-z]+/, '').replace(/([A-Z])/g, ' $1').trim();
            row.appendChild(input);
        });
        const removeBtn = document.createElement('button');
        removeBtn.type = 'button';
        removeBtn.className = 'btn-remove-row';
        removeBtn.textContent = '✕';
        removeBtn.addEventListener('click', () => row.remove());
        row.appendChild(removeBtn);
        container.appendChild(row);
    });

    // Wire up existing remove buttons
    container.querySelectorAll('.btn-remove-row').forEach(btn => {
        btn.addEventListener('click', () => btn.closest('.dynamic-row').remove());
    });
}
