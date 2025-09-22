import { clearMessage, showError, showSuccess, validateEmail } from "./util.js";

document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".forgot-form");
    const emailInput = form.querySelector("input[type='email']");

    form.addEventListener("submit", function (event) {
        event.preventDefault();
        clearMessage(form);

        const emailValue = emailInput.value.trim();

        if (!emailValue) {
            showError(form, "Email address is required.");
            return;
        } else if (!validateEmail(emailValue)) {
            showError(form, "Please enter a valid email address.");
            return;
        }

        fetch(`/api/user/verification-code?email=${encodeURIComponent(emailValue)}`, {
            method: "POST"
        })
            .then(response => {
                if (response.ok) {
                    showSuccess(form, "Verification code sent to your email.");
                    window.location.href = `/resetpassword?email=${encodeURIComponent(emailValue)}`;
                } else {
                    return response.text().then(text => {
                        showError(form, text || "Failed to send verification code.");
                    });
                }
            })
            .catch(() => {
                showError(form, "An error occurred. Please try again later.");
            });
    });
});
