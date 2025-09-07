import {clearMessage, showError, showSuccess, validateEmail} from './util.js'

function createUserAccount(email, verificationCode, name, password, birthDate){
    return fetch('/api/user/create', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                verificationCode: verificationCode,
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

function sendVerificationCode(email) {
    return fetch('/api/user/verification-code', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: new URLSearchParams({ email })
        }).then(res => {
            if (!res.ok) {
                return res.text().then(msg => { throw new Error(msg); });
            }
        })
}

function basicValidation(name, email, verificationCode, password, confirmPassword, birthDate){
    if (!name) {
        return "Name is required.";
    }
    if (!validateEmail(email)) {
        return "Please enter a valid email address.";
    }
    if(!verificationCode){
        return "Please enter verification code sent to your email";
    }
    if (password < 6) {
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
    const verificationCodeInput = form.querySelector("input[name='code']");
    const passwordInput = form.querySelector("input[name='password']");
    const confirmPasswordInput = form.querySelector("input[name='confirm_password']");
    const birthDateInput = form.querySelector("input[name='birthdate']");

    const togglePasswordIcons = form.querySelectorAll(".toggle-password");

    const verificationButton = form.querySelector("#verificationButton");

    togglePasswordIcons.forEach(icon => {
        icon.addEventListener("click", () => {
            const input = icon.previousElementSibling;
            const type = input.getAttribute("type") === "password" ? "text" : "password";
            input.setAttribute("type", type);
            icon.classList.toggle("fa-eye-slash");
        });
    });

    verificationButton.addEventListener("click", ()=>{
        clearMessage(form);

        const email = emailInput.value.trim();
        if(!validateEmail(email)) return;
        sendVerificationCode(email)
            .then(() => {
                showSuccess(form, "Sent verification code");
                verificationButton.classList.add("fulfilled");
                verificationButton.disabled = true;
            })
            .catch(error => {
                showError(form, error.message);
            });
    });

    form.addEventListener("submit", (event) => {
        event.preventDefault();
        clearMessage(form);

        const name = nameInput.value.trim();
        const email = emailInput.value.trim();
        const verificationCode = verificationCodeInput.value.trim();
        const password = passwordInput.value.trim();
        const confirmPassword = confirmPasswordInput.value.trim();
        const birthDate = birthDateInput.value;

        const error = basicValidation(name, email, verificationCode, password, confirmPassword, birthDate);
        if(error){
            showError(form, error);
            return;
        }
        createUserAccount(email, verificationCode, name, password, birthDate)
            .then(() => {
                showSuccess(form, "Successfully created account");
                setTimeout(() => {
                    window.location.href = "/login";
                }, 2000);
            })
            .catch(error => {
                clearMessage(form);
                showError(form, error.message);
            });
    });

});