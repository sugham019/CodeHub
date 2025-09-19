
document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.leaderboard-row').forEach(row => {
        row.addEventListener('click', () => {
            const userId = row.getAttribute('id');
            window.location.href = `/user/${userId}`;
        });
    });
});
