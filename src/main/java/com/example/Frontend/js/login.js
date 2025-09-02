document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".login-form-wrapper");
    const usernameInput = form.querySelector("input[name='username']");
    const passwordInput = form.querySelector("input[name='password']");
    const togglePassword = document.querySelector("#togglePassword");

    togglePassword.addEventListener("click", () => {
        const type = passwordInput.getAttribute("type") === "password" ? "text" : "password";
        passwordInput.setAttribute("type", type);
        togglePassword.classList.toggle("fa-eye-slash");
    });

    form.addEventListener("submit", (event) => {
        let errorMessage = "";

        let oldError = form.querySelector(".verification-error");
        if (oldError) oldError.remove();

        if (!usernameInput.value.trim()) {
            errorMessage = "Username is required.";
        } else if (!passwordInput.value.trim()) {
            errorMessage = "Password is required.";
        }

        if (errorMessage) {
            event.preventDefault();
            showError(form, errorMessage);
        }
    });

    function showError(formElement, message) {
        const errorDiv = document.createElement("div");
        errorDiv.className = "verification-error";
        errorDiv.textContent = message;
        formElement.insertBefore(errorDiv, formElement.firstChild);
    }
});
