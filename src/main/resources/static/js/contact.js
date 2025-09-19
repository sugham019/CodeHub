document.addEventListener("DOMContentLoaded", () => {

    const form = document.getElementById('contactForm');
    const successBox = document.getElementById('contact-success');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const name = form.name.value;
        const email = form.email.value;
        const message = form.message.value;

        const button = form.querySelector('.contact-submit-btn');
        const originalText = button.textContent;

        button.disabled = true;
        button.textContent = 'Sending…';
        button.classList.add('sending');

        try {
            const response = await fetch(`/contact?name=${encodeURIComponent(name)}&email=${encodeURIComponent(email)}`, {
                method: 'POST',
                headers: { 'Content-Type': 'text/plain' },
                body: message
            });

            if (response.ok) {
                successBox.classList.remove('hidden');
                form.reset();
                setTimeout(() => successBox.classList.add('hidden'), 5000);
            } else {
                alert('There was a problem sending your message.');
            }
        } catch (error) {
            alert('Error sending message. Please try again.');
        } finally {
            button.disabled = false;
            button.textContent = originalText;
            button.classList.remove('sending');
        }
    });


});