if ('scrollRestoration' in history) {
  history.scrollRestoration = 'manual';
}

function loadDSAContent(topic, element) {
  fetch(`/dashboard/dsa/content/${topic}/`)
    .then(response => {
      if (!response.ok) throw new Error("Content not found");
      return response.text();
    })
    .then(html => {
      document.getElementById("dsa-content").innerHTML = html;

      document.querySelectorAll('.sidebar-list-item').forEach(i => i.classList.remove('active'));
      element.classList.add('active');

      const scrollContainer = document.querySelector('.main-container');
      if (scrollContainer) {
        scrollContainer.scrollTop = 0;
      }
    })
    .catch(error => {
      document.getElementById("dsa-content").innerHTML = "<p>Error loading content.</p>";
      console.error(error);
    });
}

window.onload = () => {
  const firstItem = document.querySelector(".sidebar-list-item.active") || document.querySelector(".sidebar-list-item");
  if (firstItem) {
    loadDSAContent('home', firstItem);
  }
};
