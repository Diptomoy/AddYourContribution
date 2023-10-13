// Initialize the input string
let string = "";

// Select all elements with class "button"
let buttons = document.querySelectorAll('.button');

// Create an array of buttons
Array.from(buttons).forEach((button) => {
  button.addEventListener('click', (e) => {
    const inputValue = e.target.innerHTML;

    // Handle the clear (AC) button
    if (inputValue === 'AC') {
      clearInput();
    }
    // Handle the delete (DEL) button
    else if (inputValue === 'DEL') {
      deleteLastCharacter();
    }
    // Handle the equals (=) button
    else if (inputValue === '=') {
      evaluateExpression();
    }
    // Handle other buttons (numbers and operators)
    else {
      appendInput(inputValue);
    }
  });
});

// Function to clear the input
function clearInput() {
  string = "";
  updateInput();
}

// Function to delete the last character in the input
function deleteLastCharacter() {
  string = string.slice(0, -1);
  updateInput();
}

// Function to append input
function appendInput(input) {
  string += input;
  updateInput();
}

// Function to evaluate the expression and update the input
function evaluateExpression() {
  try {
    string = eval(string);
    updateInput();
  } catch (error) {
    // Handle any potential evaluation errors (e.g., invalid expressions)
    string = "Error";
    updateInput();
  }
}

// Function to update the input element
function updateInput() {
  document.querySelector('input').value = string;
}
