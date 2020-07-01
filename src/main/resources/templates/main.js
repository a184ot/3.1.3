alert("hello");
document.body.addEventListener("click", function (evt) {
evt.stopPropagation();
alert(evt.target.getAttribute("name"))
})