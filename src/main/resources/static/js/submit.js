
document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".code-panel form");
    const languageSelect = document.getElementById("language");
    const codeEditor = document.querySelector(".code-editor");

    const testCaseDialog = document.getElementById("testcase-dialog");

    const testCaseNumber = testCaseDialog.querySelector(".testcase-number");
    const status = testCaseDialog.querySelector(".status");
    const testCaseTime = testCaseDialog.querySelector(".testcase-time span");
    const testCaseMemory = testCaseDialog.querySelector(".testcase-memory span");

    const textarea = document.getElementById("editor");
    const lineNumbers = document.getElementById("lineNumbers");
    const problemElement = document.querySelector(".problem-title");


    form.addEventListener("submit", async(event) => {
        event.preventDefault();
        let errors = [];

        const code = codeEditor.value;

        if (!languageSelect.value.trim()) errors.push("Please select a programming language.");
        if (!code.trim()) errors.push("Please write your solution before submitting.");

        if (errors.length > 0) {
            showError(errors.join("\n"));
            return;
        }

        const problemId = problemElement.getAttribute("id");
        const language = languageSelect.value.trim();

        try {
            const response = await fetch(`/api/code/submit?problemId=${problemId}&language=${language}`, {
                method: "POST",
                headers: {
                    "Content-Type": "text/plain",
                },
                body: code
            });

            if (response.ok) {
                const result = await response.json();
                console.log(result);
                renderTestCaseResults(result);
            } else {
                console.error("Submission failed with status:", response.status);
            }
        } catch (error) {
            console.error("Error during submission:", error);
        }
    });

    function renderTestCaseResults(result) {
        if(testCaseDialog.style.display !== "block"){
            testCaseDialog.style.display = "block";
        }
        testCaseNumber.textContent = result.logs || "N/A";
        testCaseTime.textContent = result.executionTimeInMs || "N/A";
        testCaseMemory.textContent = result.peakMemoryUsageKB || "N/A";

        status.classList.remove("passed", "fail");

        if(result.allTestPassed){
            status.textContent = "Passed";
            status.classList.add("passed");
        }else{
            status.textContent = "Failed";
            status.classList.add("failed");
        }
    }

    function showError(message) {
        let errorContainer = document.querySelector(".submit-error");

        if (!errorContainer) {
            errorContainer = document.createElement("div");
            errorContainer.className = "submit-error";
            form.parentNode.insertBefore(errorContainer, form);
        }

        errorContainer.textContent = message;
        errorContainer.style.display = "block";

        setTimeout(() => {
            errorContainer.style.display = "none";
        }, 4000);
    }

    function updateLineNumbers() {
        const lines = textarea.value.split("\n").length;
        let numbers = "";
        for (let i = 1; i <= lines; i++) {
            numbers += i + "\n";
        }
        lineNumbers.textContent = numbers;
    }

    textarea.addEventListener("input", updateLineNumbers);
    textarea.addEventListener("scroll", () => {
        lineNumbers.scrollTop = textarea.scrollTop;
    });

    updateLineNumbers();
});
