document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".signup-form");
    const usernameInput = form.querySelector("input[name='username']");
    const emailInput = form.querySelector("input[name='email']");
    const passwordInput = form.querySelector("input[name='password']");
    const confirmPasswordInput = form.querySelector("input[name='confirm_password']");
    const togglePasswordIcons = form.querySelectorAll(".toggle-password");

    togglePasswordIcons.forEach(icon => {
        icon.addEventListener("click", () => {
            const input = icon.previousElementSibling;
            const type = input.getAttribute("type") === "password" ? "text" : "password";
            input.setAttribute("type", type);
            icon.classList.toggle("fa-eye-slash");
        });
    });

    form.addEventListener("submit", (event) => {
        let errorMessage = "";

        let oldError = form.querySelector(".verification-error");
        if (oldError) oldError.remove();

        if (!usernameInput.value.trim()) {
            errorMessage = "Username is required.";
        } else if (!validateEmail(emailInput.value.trim())) {
            errorMessage = "Please enter a valid email address.";
        } else if (passwordInput.value.length < 6) {
            errorMessage = "Password must be at least 6 characters long.";
        } else if (passwordInput.value !== confirmPasswordInput.value) {
            errorMessage = "Passwords do not match.";
        }

        if (errorMessage) {
            event.preventDefault();
            showError(form, errorMessage);
        }
    });

    function validateEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email.toLowerCase());
    }

    function showError(formElement, message) {
        const errorDiv = document.createElement("div");
        errorDiv.className = "verification-error";
        errorDiv.textContent = message;
        formElement.insertBefore(errorDiv, formElement.firstChild);
    }
});
