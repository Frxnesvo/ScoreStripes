document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const sessionId = urlParams.get('session_id');
    if (sessionId) {
        fetch(`/api/v1/orders/validate-transaction?sessionId=${sessionId}`, {
            method: 'POST'
        })
        .then(response =>{
            if (response.ok) {
                window.location.href = `scorestripes://stripe_success`;
            } else {
                window.location.href = `scorestripes://stripe_cancel`;
            }
        });
    }
});