import {clearMessage, showError, showSuccess, validateEmail} from './util.js'

function createUserAccount(email, name, password, birthDate){
    return fetch('/api/user/create', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                email: email,
                displayName: name,
                password: password,
                birthDate: birthDate,
                accessLevel: 'GENERAL'  // Dont try other access level, server will reject
                                        // it and fallback to GENERAL without providing private access key in query
            })
        }).then(res => {
            if (!res.ok) {
                return res.text().then(msg => { throw new Error(msg); });
            }
        })
}

function basicValidation(name, email, password, confirmPassword, birthDate){
    if (!name) {
        return "Name is required.";
    }
    if (!validateEmail(email)) {
        return "Please enter a valid email address.";
    }
    if (password.length < 6) {
        return "Password must be at least 6 characters long.";
    }
    if (password !== confirmPassword) {
        return "Passwords do not match.";
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".signup-form");
    if (!form) return;

    const nameInput = form.querySelector("input[name='name']");
    const emailInput = form.querySelector("input[name='email']");
    const passwordInput = form.querySelector("input[name='password']");
    const confirmPasswordInput = form.querySelector("input[name='confirm_password']");
    const birthDateInput = form.querySelector("input[name='dob']");

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
        event.preventDefault();
        clearMessage(form);

        const name = nameInput.value.trim();
        const email = emailInput.value.trim();
        const password = passwordInput.value.trim();
        const confirmPassword = confirmPasswordInput.value.trim();
        const birthDate = birthDateInput.value;

        const error = basicValidation(name, email, password, confirmPassword, birthDate);
        if(error){
            showError(form, error);
            return;
        }
        createUserAccount(email, name, password, birthDate)
            .then(() => {
                showSuccess(form, "Sent verification code to your email");
                setTimeout(() => {
                    window.location.href = `/verification?email=${encodeURIComponent(email)}`;
                }, 2000);
            })
            .catch(error => {
                clearMessage(form);
                showError(form, error.message);
            });
    });

});