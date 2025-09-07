document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".verification-form");
    const codeInput = form.querySelector("input[name='code']");

    codeInput.addEventListener("input", () => {
        codeInput.value = codeInput.value.replace(/\D/g, ""); 
    });

    form.addEventListener("submit", (event) => {
        let errorMessage = "";

        let oldError = form.querySelector(".verification-error");
        if (oldError) oldError.remove();

        if (codeInput.value.length !== 6) {
            errorMessage = "Please enter a valid 6-digit verification code.";
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

        setTimeout(() => {
            errorDiv.remove();
        }, 3000);
    }
});
