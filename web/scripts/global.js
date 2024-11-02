let lastErrorId = 0;

function throwError(message){
    console.log("throwError");
    let errorContainer = document.getElementById("errorContainer");

    let errorElement = document.createElement("div");
        errorElement.className = "error";
        errorElement.id = "error_" + lastErrorId;
    lastErrorId++;

    errorContainer.appendChild(errorElement);


    let errorIcon = document.createElement("img");
        errorIcon.src = "errorIcon.svg";
        errorIcon.className = "errorSvg";
    errorElement.appendChild(errorIcon);

    let errorMessage = document.createElement("div");
        errorMessage.innerHTML = message;
        errorMessage.className = "errorMessage";
    errorElement.appendChild(errorMessage);

    setTimeout(() => {
        errorElement.remove();
      }, "4000");
      
}