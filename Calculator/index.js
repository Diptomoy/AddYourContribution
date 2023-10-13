var buttons = document.getElementsByClassName("button");
var display = document.getElementById("display-container");
var operand1 = 0;
var operand2 = null;
var operator = null;

for (var i = 0; i < buttons.length; i++) {
  buttons[i].addEventListener("click", function () {
    var value = this.getAttribute("data-value");

    if (value == "+/-") {
      display.innerText = "-";
    } else if (value == "AC") {
      display.innerText = " ";
    } else if (
      value == "+" ||
      value == "-" ||
      value == "*" ||
      value == "/" ||
      value == "%"
    ) {
      operator = value;
      operand1 = parseFloat(display.textContent);
      display.innerText = " ";
    } else if (value == "=") {
      operand2 = parseFloat(display.textContent);
      if (operator == "+") {
        var eval = operand1 + operand2;
        display.innerText = eval;
      } else if (operator == "-") {
        var eval = operand1 - operand2;
        display.innerText = eval;
      } else if (operator == "*") {
        var eval = operand1 * operand2;
        display.innerText = eval;
      } else if (operator == "/") {
        var eval = operand1 / operand2;
        display.innerText = eval;
      } else {
        var eval = operand1 / 100;
        display.innerText = eval;
      }
    } else {
      display.innerText += value;
    }
  });
}
