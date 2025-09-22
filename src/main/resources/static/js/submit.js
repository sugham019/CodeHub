
async function loadComments(problemId, orderBy) {
    try {
        const response = await fetch(`/api/comment/getAllByProblem?problemId=${encodeURIComponent(problemId)}&orderBy=${encodeURIComponent(orderBy)}`);
        const comments = await response.json();
        const container = document.getElementById('commentsContainer');
        container.innerHTML = '';

        comments.forEach(comment => {
            const commentDiv = document.createElement('div');
            commentDiv.classList.add('comment');

            const imgLink = document.createElement('a');
            imgLink.href = `/user/${comment.userId}`;
            imgLink.target = "_blank";

            const img = document.createElement('img');
            img.src = `/api/user/profilePicture/${comment.userId}`;
            img.alt = comment.userDisplayName;

            imgLink.appendChild(img);

            const contentDiv = document.createElement('div');
            contentDiv.classList.add('comment-content');

            contentDiv.innerHTML = `<strong>${comment.userDisplayName}</strong>: ${comment.content}
                                    <div class="comment-meta">${new Date(comment.postedAt).toLocaleString()}</div>`;

            commentDiv.appendChild(imgLink); // append clickable image link
            commentDiv.appendChild(contentDiv);
            container.appendChild(commentDiv);
        });
    } catch (err) {
        console.error('Failed to load comments:', err);
    }
}
function toggleLibraries(element) {
    const sublist = element.nextElementSibling;
    const arrow = element.querySelector(".arrow");
    if (sublist.style.display === "block") {
        sublist.style.display = "none";
        arrow.style.transform = "rotate(0deg)";
    } else {
        sublist.style.display = "block";
        arrow.style.transform = "rotate(180deg)";
    }
}

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

    const newCommentInput = document.getElementById("newComment");
    const submitCommentBtn = document.getElementById("submitComment");

    const problemId = problemElement.getAttribute("id");

    textarea.addEventListener('keydown', function(e) {
        if (e.key === 'Tab') {
            e.preventDefault();
            const start = this.selectionStart;
            const end = this.selectionEnd;

            this.value = this.value.substring(0, start) + '\t' + this.value.substring(end);

            this.selectionStart = this.selectionEnd = start + 1;
        }
    });

    submitCommentBtn.addEventListener("click", async () => {
        const content = newCommentInput.value.trim();
        if (!content) return;

        try {
            const response = await fetch(`/api/comment/post?problemId=${encodeURIComponent(problemId)}`, {
                method: "POST",
                headers: {
                    "Content-Type": "text/plain",
                },
                body: content
            });

            if (response.ok) {
                newCommentInput.value = "";
                await loadComments(problemId, "NEWEST");
            } else {
                console.error("Failed to submit comment:", response.status);
            }
        } catch (err) {
            console.error("Error submitting comment:", err);
        }
    });

    newCommentInput.addEventListener("keypress", (e) => {
        if (e.key === "Enter") {
            submitCommentBtn.click();
            e.preventDefault();
        }
    });

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

        status.classList.remove("passed", "failed");

        if(result.allTestPassed){
            status.textContent = "Passed";
            status.classList.add("passed");

            fetch("/api/user/ratings", {
            })
                .then(response => {
                    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
                    return response.text();
                })
                .then(ratings => {
                    const ratingSpan = document.getElementById("user-rating");
                    if (ratingSpan) {
                        ratingSpan.textContent = ratings;
                    }
                })
                .catch(error => {
                    console.error("Error fetching ratings:", error);
                });

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
    (async () => {
        await loadComments(problemId, "NEWEST");
    })();
});


