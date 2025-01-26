async function addToCart() {
    const form = document.getElementById('orderForm');
    const formData = new FormData(form);
    const jsonData = JSON.stringify(Object.fromEntries(formData));
    const csrfToken = document.querySelector("input[name='_csrf']").value;
    const dataObject = JSON.parse(jsonData);

    try {
        const response = await fetch(`/users/${dataObject.userId}/cart/`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': csrfToken
            },
            body: jsonData
        });

        const result = await response.json();

        if (response.ok) {
            const swalResult = await Swal.fire({
                title: 'Success!',
                text: result.message,
                icon: 'success',
                showConfirmButton: true,
                showCancelButton: true,
                confirmButtonText: 'View in Cart',
                cancelButtonText: 'Continue Shopping',
                timer: 3000,
                timerProgressBar: true
            });

            if (swalResult.isConfirmed) {
                window.location.href = response.headers.get('Location');
            } else if (swalResult.dismiss === Swal.DismissReason.cancel) {
                window.location.href = '/products/';
            }
        } else {
            throw new Error(result.error || 'Failed to add to cart');
        }
        
    } catch (error) {
        Swal.fire({
            title: 'Error!',
            text: error.message,
            icon: 'error',
            confirmButtonText: 'OK'
        });
    }
}
        async function submitOrderForm(userId) {
    const form = document.getElementById('orderForm');
    const formData = new FormData(form);
    const { _csrf, ...formValues } = Object.fromEntries(formData);

    try {
        const payload = JSON.stringify(formValues);

        const response = await fetch(`/users/${userId}/orders/`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': _csrf
            },
            body: payload
        });

        const responseData = await response.json();

        if (!response.ok) {
            // Xử lý lỗi từ server
            const errorMessage = responseData.error || 
                            Object.entries(responseData)
                                    .map(([field, msg]) => `${field}: ${msg}`)
                                    .join('\n');
            throw new Error(errorMessage);
        }

        // Xử lý thành công
        const result = await Swal.fire({
            title: 'Order Created!',
            text: responseData.message,
            icon: 'success',
            confirmButtonColor: 'var(--success-color)',
            confirmButtonText: 'View Order',
            showDenyButton: true,
            denyButtonText: 'Continue Shopping',
            denyButtonColor: 'var(--primary-color)',
            showCancelButton: false,
            timer: 5000,
            timerProgressBar: true
        });

        // Xử lý lựa chọn
        if (result.isConfirmed) {
            window.location.href = response.headers.get('Location');
        } else if (result.isDenied) {
            window.location.href = '/products/';
        } else {
            window.location.reload();
        }

    } catch (error) {
        Swal.fire({
            title: 'Order Error!',
            html: `<div class="text-danger">${error.message.replace(/\n/g, '<br>')}</div>`,
            icon: 'error',
            confirmButtonColor: 'var(--danger-color)',
            confirmButtonText: 'Fix Issues',
            scrollbarPadding: false
        });
    }
}