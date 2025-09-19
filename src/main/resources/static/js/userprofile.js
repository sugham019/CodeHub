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

// document.addEventListener("DOMContentLoaded", () => {
//
//
//   const progressData = document.getElementById('progress-data');
//
//   const easySolved = parseInt(progressData.dataset.easySolved);
//   const easyTotal = parseInt(progressData.dataset.easyTotal);
//
//   const mediumSolved = parseInt(progressData.dataset.mediumSolved);
//   const mediumTotal = parseInt(progressData.dataset.mediumTotal);
//
//   const hardSolved = parseInt(progressData.dataset.hardSolved);
//   const hardTotal = parseInt(progressData.dataset.hardTotal);
//
//   const easyPercent = (1 / 1) * 100;
//   const mediumPercent = (0 / 1) * 100;
//   const hardPercent = (0 / 1) * 100;
//
//   // --- Circle math ---
//   const radius = 15.9155;
//   const circumference = 2 * Math.PI * radius;
//
//   const easyArc = (easyPercent / 100) * circumference;
//   const mediumArc = (mediumPercent / 100) * circumference;
//   const hardArc = (hardPercent / 100) * circumference;
//
//   // --- Apply to SVG ---
//   const easyCircle = document.querySelector('.easy');
//   const mediumCircle = document.querySelector('.medium');
//   const hardCircle = document.querySelector('.hard');
//
//   easyCircle.setAttribute('stroke-dasharray', `${easyArc} ${circumference}`);
//   easyCircle.setAttribute('stroke-dashoffset', 0);
//
//   mediumCircle.setAttribute('stroke-dasharray', `${mediumArc} ${circumference}`);
//   mediumCircle.setAttribute('stroke-dashoffset', -easyArc);
//
//   hardCircle.setAttribute('stroke-dasharray', `${hardArc} ${circumference}`);
//   hardCircle.setAttribute('stroke-dashoffset', -(easyArc + mediumArc));
//
// });

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
