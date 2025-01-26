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
    var csrfToken = document.querySelector('input[name="_csrf"]').value;
    try {
        const response = await fetch(`/users/${userId}/orders/${orderId}`, {
            method: 'DELETE',
            headers: {
                'X-CSRF-TOKEN': csrfToken
            }
        });

        if (response.ok) {
            await Swal.fire({
                title: 'Order Deleted!',
                text: 'The order has been deleted successfully.',
                icon: 'success',
                confirmButtonColor: 'var(--success)',
                confirmButtonText: 'OK'
            });
            window.location.href = `/users/${userId}/orders/`;
        } else {
            Swal.fire({
                title: 'Error!',
                text: 'An error occurred while deleting the order.',
                icon: 'error',
                confirmButtonColor: 'var(--danger)',
                confirmButtonText: 'OK'
            });
        }
    } catch (error) {
        Swal.fire({
            title: 'Error!',
            text: error.message + `/users/${userId}/orders/${orderId}`,
            icon: 'error',
            confirmButtonColor: 'var(--danger)',
            confirmButtonText: 'OK'
        });
    }
}