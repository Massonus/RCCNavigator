var theme = document.getElementById("theme");
var nav = document.getElementById('navbar');


function changeStyle() {

    let checked = document.getElementById("checker").checked;

    if (checked) {

        theme.href = "/css/dark-theme.css";
        nav.className = "navbar fixed-top navbar-expand-lg navbar-dark bg-dark";
        localStorage.setItem('theme', '/css/dark-theme.css');
        localStorage.setItem('class', 'navbar fixed-top navbar-expand-lg navbar-dark bg-dark');
        localStorage.setItem('checker', 'true');
    } else {

        theme.href = "/css/light-theme.css";
        nav.className = "navbar fixed-top navbar-expand-lg navbar-light bg-light";
        localStorage.setItem('theme', '/css/light-theme.css');
        localStorage.setItem('class', 'navbar fixed-top navbar-expand-lg navbar-light bg-light');
        localStorage.setItem('checker', 'false');
    }
}

function setTheme() {
    theme.href = localStorage.getItem('theme') || '/css/light-theme.css';
    nav.className = localStorage.getItem('class') || 'navbar fixed-top navbar-expand-lg navbar-light bg-light';

    let checker = document.getElementById("checker");
    let getItem = localStorage.getItem('checker') || false;
    let isChecked = JSON.parse(getItem);

    if (isChecked) {
        checker.setAttribute("checked", "checked");
    } else {
        checker.removeAttribute("checked");
    }
}