import { clearMessage, showError, showSuccess, validateEmail } from "./util.js";

document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".forgot-form");

    const codeInput = form.querySelector("input[name='verificationCode']");
    const passwordInput = form.querySelector("input[name='newPassword']");

    const urlParams = new URLSearchParams(window.location.search);
    const emailValue = urlParams.get("email") || ""; // email from ?email=...

    form.addEventListener("submit", function (event) {
        event.preventDefault(); // stop normal form submit

        const codeValue = codeInput.value.trim();
        const passwordValue = passwordInput.value.trim();

        let errorDiv = form.querySelector(".verification-error");
        if (errorDiv) errorDiv.remove();

        if (!codeValue) {
            showError(form, "Verification code is required.");
            return;
        }

        if (!passwordValue) {
            showError(form, "New password is required.");
            return;
        }

        // Build request payload
        const payload = {
            email: emailValue,
            verificationCode: codeValue,
            newPassword: passwordValue
        };

        // Send request to backend
        fetch("/api/user/forgotpassword", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        })
            .then(response => {
                if (response.ok) {
                    // Success — redirect or show success message
                    alert("Password has been reset successfully!");
                    window.location.href = "/login";
                } else {
                    return response.text().then(text => {
                        showError(form, text || "Failed to reset password.");
                    });
                }
            })
            .catch(() => {
                showError(form, "An error occurred. Please try again later.");
            });
    });
});
