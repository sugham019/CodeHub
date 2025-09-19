function scrollToHero(event) {
  event.preventDefault();
  document.getElementById("learning-section").scrollIntoView({
    behavior: "smooth"
  });
}

document.addEventListener("DOMContentLoaded", () => {
  const heading = document.querySelector(".hero-section h1");
  const text = "Start Your Journey \nTo Compete";
  let index = 0;

  heading.innerHTML = ""; // start empty

  function typeCharacter() {
    if (index < text.length) {
      const char = text.charAt(index);
      // Convert newline character to <br> for HTML
      heading.innerHTML += char === "\n" ? "<br />" : char;
      index++;
      setTimeout(typeCharacter, 100); // typing speed
    }
  }

  typeCharacter();
});