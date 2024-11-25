document.addEventListener("DOMContentLoaded", () => {
  const savedTheme = localStorage.getItem("theme");
  if (savedTheme) {
    document.body.classList.remove("light", "dark");
    document.body.classList.add(savedTheme);
    const themeToggleButton = document.getElementById("themeToggle");
    themeToggleButton.innerHTML =
      savedTheme === "light"
        ? '<i class="fas fa-sun"></i> Dark Mode'
        : '<i class="fas fa-moon"></i> Light Mode';
  }
});

// Theme toggle functionality
const themeToggleButton = document.getElementById("themeToggle");
themeToggleButton.addEventListener("click", () => {
  const currentTheme = document.body.classList.contains("dark")
    ? "dark"
    : "light";
  const newTheme = currentTheme === "dark" ? "light" : "dark";
  document.body.classList.toggle("dark");
  document.body.classList.toggle("light");
  themeToggleButton.innerHTML =
    newTheme === "light"
      ? '<i class="fas fa-sun"></i> Dark Mode'
      : '<i class="fas fa-moon"></i> Light Mode';
  localStorage.setItem("theme", newTheme); // Save new theme to localStorage
});

// Toggle navbar
document.addEventListener("DOMContentLoaded", function () {
  // Make sure elements are available
  const hamburgerMenu = document.getElementById("hamburgerMenu");
  const navbarButtons = document.getElementById("navbarButtons");

  if (hamburgerMenu && navbarButtons) {
    // Handle hamburger menu toggle
    hamburgerMenu.addEventListener("click", function () {
      navbarButtons.classList.toggle("show"); // Toggle the "show" class to show/hide the navbar
    });

    // Optional: If the user clicks outside the navbar, close it
    document.addEventListener("click", function (event) {
      if (
        !navbarButtons.contains(event.target) &&
        !hamburgerMenu.contains(event.target)
      ) {
        navbarButtons.classList.remove("show");
      }
    });
  } else {
    console.error("Hamburger or Navbar Buttons element not found.");
  }
});
