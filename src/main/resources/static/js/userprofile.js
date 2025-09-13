function toggleDropdown() {
  const dropdown = document.getElementById("dropdown-menu");
  dropdown.style.display = (dropdown.style.display === "block") ? "none" : "block";
}

window.addEventListener("click", function(event) {
  const dropdown = document.getElementById("dropdown-menu");
  if (!event.target.closest(".profile-dropdown")) {
    dropdown.style.display = "none";
  }
});

const progressData = [
  { date: '2024-08-15', problems: 0 },
  { date: '2024-08-18', problems: 1 },
  { date: '2024-08-22', problems: 1 },
  { date: '2024-08-25', problems: 2 },
  { date: '2024-08-28', problems: 2 },
  { date: '2024-09-02', problems: 3 },
  { date: '2024-09-05', problems: 3 },
  { date: '2024-09-08', problems: 3 },
  { date: '2024-09-11', problems: 3 }
];

const ctx = document.getElementById('progressChart').getContext('2d');
const progressChart = new Chart(ctx, {
  type: 'line',
  data: {
    labels: progressData.map(item => {
      const date = new Date(item.date);
      return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric' });
    }),
    datasets: [{
      label: 'Problems Solved',
      data: progressData.map(item => item.problems),
      borderColor: '#3498db',
      backgroundColor: 'rgba(52, 152, 219, 0.1)',
      borderWidth: 3,
      fill: true,
      tension: 0.4,
      pointBackgroundColor: '#3498db',
      pointBorderColor: '#ffffff',
      pointBorderWidth: 2,
      pointRadius: 5,
      pointHoverRadius: 7
    }]
  },
  options: {
    responsive: true,
    maintainAspectRatio: false,
    plugins: { legend: { display: false } },
    scales: {
      x: {
        grid: { display: false },
        ticks: { font: { size: 12 }, color: '#666' }
      },
      y: {
        beginAtZero: true,
        max: 5,
        grid: { color: 'rgba(0,0,0,0.1)' },
        ticks: { stepSize: 1, font: { size: 12 }, color: '#666' }
      }
    },
    interaction: { intersect: false, mode: 'index' }
  }
});

document.querySelectorAll('.problem-item').forEach(item => {
  item.addEventListener('click', () => {
    item.style.transform = 'scale(0.98)';
    setTimeout(() => { item.style.transform = 'translateY(-1px)'; }, 100);
  });
});
