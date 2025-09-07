
const ERROR_CLASS = "verification-error";

const SUCCESS_CLASS = "verification-success";

export function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email.toLowerCase());
}

export function showError(targetElement, message) {
    const errorDiv = document.createElement("div");
    errorDiv.className = ERROR_CLASS;
    errorDiv.textContent = message;
    targetElement.insertBefore(errorDiv, targetElement.firstChild);
}

export function showSuccess(targetElement, message) {
    const successDiv = document.createElement("div");
    successDiv.className = SUCCESS_CLASS;
    successDiv.textContent = message;
    targetElement.insertBefore(successDiv, targetElement.firstChild);
}

export function clearMessage(targetElement){
    const oldError = targetElement.querySelector(".verification-error");
    if (oldError) oldError.remove();

    const oldSuccess = targetElement.querySelector(".verification-success");
    if (oldSuccess) oldSuccess.remove();
}
