document.addEventListener("DOMContentLoaded", () => {
    const addProblemForm = document.getElementById("addProblemForm");
    const addProblemMessage = document.getElementById("addProblemMessage");
    const removeProblemMessage = document.getElementById("removeProblemMessage");

    // Add Problem
    addProblemForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        addProblemMessage.textContent = "Submitting...";
        const formData = new FormData(addProblemForm);
        const jsonText = formData.get("testCase").trim();
        let skills = formData.get("skills")
            ?.trim()
            .split(",")
            .map(s => s.trim())
            .filter(Boolean) || [];

        let expectedOutputsArray;
        let inputsArray;
        try {
            const jsonData = JSON.parse(jsonText);

            inputsArray = jsonData.inputs || [];
            expectedOutputsArray = jsonData.expectedOutputs || [];

        } catch (err) {
            addProblemMessage.textContent = "Invalid JSON format";
            return;
        }

        let bannedLibraryJson = {};
        const lang = "JAVA";
        const lib = formData.get("bannedLib");

        if (lib) {
            bannedLibraryJson[lang] = lib;
        }

        const problemDto = {
            title: formData.get("title"),
            pageDescription: formData.get("pageDescription"),
            inputs: inputsArray,
            inputType: formData.get("inputType"),
            expectedOutputs: expectedOutputsArray,
            outputType: formData.get("outputType"),
            difficulty: formData.get("difficulty"),
            bannedLibrary: bannedLibraryJson,
            skills: skills,
            hint: formData.get("hint").trim() | null
        };

        console.log(problemDto);

        try {
            const response = await fetch("/api/problem/add", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(problemDto)
            });

            if (response.ok) {
                const problemId = await response.text();
                addProblemMessage.textContent = `Problem added successfully! ID: ${problemId}`;
                addProblemForm.reset();
                window.location.reload();
            } else {
                addProblemMessage.textContent = "Failed to add problem!";
            }
        } catch (error) {
            addProblemMessage.textContent = "Error contacting server!";
        }
    });

    // Remove Problem buttons
    document.querySelectorAll(".remove-problem-btn").forEach(button => {
        button.addEventListener("click", async () => {
            const problemId = button.getAttribute("data-id");
            removeProblemMessage.textContent = `Removing problem ${problemId}...`;

            try {
                const response = await fetch(`/api/problem/remove?problemId=${encodeURIComponent(problemId)}`, {
                    method: "POST"
                });

                if (response.ok) {
                    removeProblemMessage.textContent = `Problem ${problemId} removed successfully!`;
                    button.closest(".card-question").remove();
                    window.location.reload();
                } else {
                    removeProblemMessage.textContent = "Failed to remove problem!";
                }
            } catch (error) {
                removeProblemMessage.textContent = "Error contacting server!";
            }
        });
    });
});
