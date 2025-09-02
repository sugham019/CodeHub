document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".forgot-form");
    const emailInput = form.querySelector("input[type='email']");

    form.addEventListener("submit", function (event) {
        const emailValue = emailInput.value.trim();
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        let errorDiv = form.querySelector(".verification-error");
        if (errorDiv) errorDiv.remove();

        if (!emailValue) {
            event.preventDefault();
            showError(form, "Email address is required.");
        } else if (!emailRegex.test(emailValue)) {
            event.preventDefault();
            showError(form, "Please enter a valid email address.");
        }
    });

    function showError(formElement, message) {
        const errorDiv = document.createElement("div");
        errorDiv.className = "verification-error";
        errorDiv.textContent = message;
        formElement.insertBefore(errorDiv, formElement.firstChild);
    }
});
