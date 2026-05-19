/* Booking JS — date validation & price calculation */
document.addEventListener('DOMContentLoaded', () => {
    const checkIn = document.getElementById('checkIn');
    const checkOut = document.getElementById('checkOut');
    const guests = document.getElementById('guests');
    const roomType = document.getElementById('roomType');

    if (checkIn) {
        // Set min date to today
        const today = new Date().toISOString().split('T')[0];
        checkIn.setAttribute('min', today);
        if (checkOut) checkOut.setAttribute('min', today);

        checkIn.addEventListener('change', () => {
            if (checkOut) checkOut.setAttribute('min', checkIn.value);
            updatePrice();
        });
    }

    if (checkOut) checkOut.addEventListener('change', updatePrice);
    if (guests) guests.addEventListener('change', updatePrice);
    if (roomType) roomType.addEventListener('change', updatePrice);
});

function updatePrice() {
    const totalEl = document.getElementById('totalPrice');
    const basePrice = parseFloat(document.getElementById('basePrice')?.value || 0);
    const checkIn = document.getElementById('checkIn')?.value;
    const checkOut = document.getElementById('checkOut')?.value;
    const guests = parseInt(document.getElementById('guests')?.value || 1);
    const bookingType = document.getElementById('bookingType')?.value;

    if (!totalEl) return;

    if (bookingType === 'HOTEL' && checkIn && checkOut) {
        const days = Math.ceil((new Date(checkOut) - new Date(checkIn)) / (1000 * 60 * 60 * 24));
        if (days > 0) {
            totalEl.textContent = formatCurrency(basePrice * Math.max(days, 1));
        }
    } else if (bookingType === 'TRIP') {
        totalEl.textContent = formatCurrency(basePrice * guests);
    }
}
