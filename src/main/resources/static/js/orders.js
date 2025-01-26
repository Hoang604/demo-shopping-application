function confirmDelete(userId, orderId) {
    Swal.fire({
            title: 'Delete this order?',
            text: "This action cannot be undone!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                deleteOrder(userId, orderId); 
            }
        });
}

async function deleteOrder(userId, orderId) {
    const csrfToken = document.querySelector('input[name="_csrf"]').value;
    
    try {
        const response = await fetch(`/users/${userId}/orders/${orderId}`, {
            method: 'DELETE',
            headers: {
                'X-CSRF-TOKEN': csrfToken
            }
        });

        // if status = 204, then response.json() will throw an error, set responseBody to null
        const responseBody = response.status !== 204 ? await response.json() : null;

        switch (response.status) {
            case 204:
                await Swal.fire({
                    title: 'Deleted!',
                    text: 'Order deleted successfully',
                    icon: 'success',
                    confirmButtonColor: 'var(--success)',
                    timer: 2000,
                    timerProgressBar: true
                });
                window.location.href = `/users/${userId}/orders/`; // Redirect về danh sách
                break;

            case 403:
                await Swal.fire({
                    title: 'Access Denied!',
                    text: responseBody.error,
                    icon: 'warning',
                    confirmButtonColor: 'var(--primary)'
                });
                break;

            case 404:
                await Swal.fire({
                    title: 'Not Found!',
                    text: responseBody.error,
                    icon: 'error',
                    confirmButtonColor: 'var(--danger)'
                });
                window.location.href = `/users/${userId}/orders/`; // Redirect về danh sách
                break;

            default:
                throw new Error(responseBody?.error || `HTTP error! status: ${response.status}`);
        }

    } catch (error) {
        await Swal.fire({
            title: 'Error!',
            text: error.message,
            icon: 'error',
            confirmButtonColor: 'var(--danger)'
        });
    }
}