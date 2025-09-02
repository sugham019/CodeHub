if ('scrollRestoration' in history) {
  history.scrollRestoration = 'manual';
}
function showSQLContent(topic, element) {
  fetch(`/dashboard/sql/content/${topic}/`)
    .then(response => {
      if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
      return response.text();
    })
    .then(html => {
      document.getElementById("sql-content-box").innerHTML = html;

      document.querySelectorAll(".sidebar-item").forEach(item => item.classList.remove("active"));
      element.classList.add("active");
    const scrollContainer = document.querySelector('.content-area');
    if (scrollContainer) {
    scrollContainer.scrollTop = 0;
    }

    window.scrollTo(0, 0);
    })
    .catch(error => {
      document.getElementById("sql-content-box").innerHTML = "<p>Error loading content.</p>";
      console.error(error);
    });
}

window.onload = () => {
  const firstItem = document.querySelector(".sidebar-item.active");
  if (firstItem) {
    showSQLContent('home', firstItem);
  }
};