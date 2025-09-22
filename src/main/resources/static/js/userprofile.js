function toggleDropdown() {
  const dropdown = document.getElementById("dropdown-menu");
  dropdown.style.display = (dropdown.style.display === "block") ? "none" : "block";
}

window.addEventListener("click", function (event) {
  const dropdown = document.getElementById("dropdown-menu");
  if (!event.target.closest(".profile-dropdown")) {
    dropdown.style.display = "none";
  }
});

document.getElementById('profilePictureInput').addEventListener('change', function(event) {
  const file = event.target.files[0];
  if (!file) return;

  const formData = new FormData();
  formData.append("profilePicture", file);

  fetch('/api/user/profilePicture', {
    method: 'POST',
    body: formData
  })
      .then(response => {
        if (!response.ok) {
          throw new Error('Upload failed');
        }
        return response.text();
      })
      .then(data => {
        // Refresh page after successful upload

        window.location.reload();
      })
      .catch(error => {
        console.error('Error uploading profile picture:', error);
      });x
});

document.querySelectorAll('.problem-item').forEach(item => {
  item.addEventListener('click', (event) => {

    item.style.transform = 'scale(0.98)';
    setTimeout(() => {
      item.style.transform = 'scale(1)';
    }, 100);

    const problemId = item.id;
    window.location.href = `/problem/${problemId}`;

  });
});
