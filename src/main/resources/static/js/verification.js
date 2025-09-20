import {showSuccess} from "./util.js";

document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".verification-form");
    const codeInput = form.querySelector("input[name='code']");

    codeInput.addEventListener("input", () => {
        codeInput.value = codeInput.value.replace(/\D/g, "");
    });

    form.addEventListener("submit", async (event) => {
        event.preventDefault();
        let errorMessage = "";

        let oldError = form.querySelector(".verification-error");
        if (oldError) oldError.remove();

        if (codeInput.value.length !== 6) {
            errorMessage = "Please enter a valid 6-digit verification code";
            showError(form, errorMessage);
            return;
        }
        const urlParams = new URLSearchParams(window.location.search);
        const email = urlParams.get("email");
        if (!email) {
            showError(form, "Missing email parameter in URL.");
            return;
        }

        try {
            const response = await fetch(`/api/user/complete?email=${encodeURIComponent(email)}`, {
                method: "POST",
                headers: { "Content-Type": "text/plain" },
                body: codeInput.value
            });

            if (response.ok) {
                // showSuccess(form, "Successfully created user account");
                setTimeout(() => {
                    window.location.href = "/login";
                }, 2000);
            } else {
                const errorText = await response.text();
                showError(form, errorText || "Verification failed. Please try again");
            }
        } catch (err) {
            showError(form, "Something went wrong. Please try again");
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
