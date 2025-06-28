
document.addEventListener('DOMContentLoaded', () => {
  const signupButton = document.querySelector('.auth-section button');

  signupButton.addEventListener('click', () => {
    const username = document.querySelector('input[placeholder="Username"]').value;
    const email = document.querySelector('input[placeholder="Email"]').value;
    const password = document.querySelector('input[placeholder="Password"]').value;

    if (username && email && password) {
      alert(`Welcome to Code Rush, ${username}!`);
    } else {
      alert('Please fill in all fields.');
    }
  });
});


document.addEventListener("DOMContentLoaded", function () {
      const signUpBtn = document.querySelector("#sign-up-btn");
      const signInBtn = document.querySelector("#sign-in-btn");
      const container = document.querySelector("#auth-container");

      signUpBtn.addEventListener("click", () => {
        container.classList.add("sign-up-mode");
      });

      signInBtn.addEventListener("click", () => {
        container.classList.remove("sign-up-mode");
      });
    });