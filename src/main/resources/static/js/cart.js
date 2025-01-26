async function submitOrderForm(event, userId, cartItem) {
    event.preventDefault();
    event.stopPropagation();
    const form = document.getElementById('orderForm');
    const formData = new FormData(form);
    const { _csrf, ...data } = Object.fromEntries(formData);
    
    try {
        const response = await fetch(`/users/${userId}/orders/`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': _csrf
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            const result = await Swal.fire({
                title: 'Order Created!',
                text: 'Your order has been placed successfully',
                icon: 'success',
                confirmButtonColor: 'var(--success-color)',
                confirmButtonText: 'View Orders',
                showConfirmButton: true,
                showCancelButton: true,
                cancelButtonText: 'Return to cart',
                cancelButtonColor: 'var(--primary-color)',
                timer: 3000,
                timerProgressBar: true
            });

            deleteCartItem(event, userId, cartItem);

            // Xử lý kết quả sau khi hộp thoại đóng
            if (result.isConfirmed) {
                window.location.href = `/users/${userId}/orders/`;
            } else if (result.isDismissed) {
                window.location.href = `/users/${userId}/cart/`;
            }
        } else {
            throw new Error('Failed to create order');
        }
    } catch (error) {
        Swal.fire({
            title: 'Error!',
            text: error.message || 'Something went wrong',
            icon: 'error',
            confirmButtonColor: 'var(--danger-color)',
            confirmButtonText: 'Try Again'
        });
    }
}

async function confirmDeleteCartItem(event, userId, cartItemId) {
    event.preventDefault();
    event.stopPropagation();

    const result = await Swal.fire({
        title: 'Remove this item?',
        text: 'This action cannot be undone!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: 'var(--danger-color)',
        cancelButtonColor: 'var(--primary-color)',
        confirmButtonText: 'Yes, remove it!',
        timer: 5000,
        timerProgressBar: true
    });

    if (result.isConfirmed) {
        deleteCartItem(event, userId, cartItemId);
    }
}

async function deleteCartItem(event, userId, cartItemId) {
    event.preventDefault();
    event.stopPropagation();

    const form = document.getElementById('orderForm');
    const formData = new FormData(form);
    const { _csrf } = Object.fromEntries(formData);

    try {
        const response = await fetch(`/users/${userId}/cart/${cartItemId}`, {
            method: 'DELETE',
            headers: {
                'X-CSRF-TOKEN': _csrf
            },
        });

        if (response.ok) {
                await Swal.fire({
                title: 'Removed!',
                text: 'Your cart item has been removed successfully',
                icon: 'success',
                confirmButtonColor: 'var(--success-color)',
                confirmButtonText: 'Return to cart',
                showConfirmButton: true,
                timer: 3000,
                timerProgressBar: true
            });

            window.location.href = `/users/${userId}/cart/`;
        }
        else {
            const data = await response.json();
            throw new Error(data.message || 'Failed to remove cart item');
        }

    } catch (error) {
        await Swal.fire({
            title: 'Error!',
            text: error.message || 'Something went wrong',
            icon: 'error',
            confirmButtonColor: 'var(--danger-color)',
            confirmButtonText: 'Try Again'
        });
    }
}