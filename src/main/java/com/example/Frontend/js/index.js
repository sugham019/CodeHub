function scrollToHero(event) {
  event.preventDefault();
  document.getElementById("learning-section").scrollIntoView({
    behavior: "smooth"
  });
}