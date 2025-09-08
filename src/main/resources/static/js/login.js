import {clearMessage, showError, showSuccess, validateEmail} from "./util.js";

function signIn(email, password) {
    return fetch('/api/user/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
        }).then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text || "Login failed"); });
            }
            return response.text();
        });
}

function saveCredential(jwtToken, doc, persistent){
    const maxDay = 7;
    let cookieString = `jwt=${jwtToken}; path=/; samesite=strict`;
    if (persistent) {
        const expires = new Date(Date.now() + maxDay * 24 * 60 * 60 * 1000).toUTCString();
        cookieString += `; expires=${expires}`;
    }
    doc.cookie = cookieString;
}

document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".login-form-wrapper");
    if (!form) return;
    const emailInput = form.querySelector("input[name='email']");
    const passwordInput = form.querySelector("input[name='password']");
    const rememberMeCheckbox = form.querySelector("input[name='remember_me']");

    const togglePassword = document.querySelector("#togglePassword");

    togglePassword.addEventListener("click", () => {
        const type = passwordInput.getAttribute("type") === "password" ? "text" : "password";
        passwordInput.setAttribute("type", type);
        togglePassword.classList.toggle("fa-eye-slash");
    });

    form.addEventListener("submit", (event) => {
        event.preventDefault();
        clearMessage(form);

        const email = emailInput.value.trim();
        const password = passwordInput.value.trim();

        if(!validateEmail(email)){
            showError(form, "Invalid email");
            return;
        }
        if(!password){
            showError(form, "Invalid password format");
            return;
        }
        const rememberMeChecked = rememberMeCheckbox && rememberMeCheckbox.checked;

        signIn(email, password)
            .then(jwt => {
                saveCredential(jwt, document, rememberMeChecked);
                setTimeout(() => {
                    window.location.href = "/";
                }, 2000);
            })
            .catch(err => {
                showError(form, err.message);
            });
    });

});
