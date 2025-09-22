

function redirectToProfile(button) {
    const userId = button.getAttribute("id");
    window.location.href = `/user/${userId}`;
}
