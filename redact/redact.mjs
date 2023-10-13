// initialize variables
const toRedact = document.querySelector("#toRedact");
const toScramble = document.querySelector("#toScramble");
const toReplace = document.querySelector("#replacement-field");
const redactBtn = document.querySelector("#redact-btn");
const copyBtn = document.querySelector(".copy");
const redactedField = document.querySelector("#redacted");
const toRedactErrorField = document.querySelector(".toRedactError");
const toScrambleErrorField = document.querySelector(".toScrambleError");
const toReplaceErrorField = document.querySelector(".toReplaceError");
const redactedContent = document.querySelector("#redacted-content");
const redactContent = document.querySelector("#redact-content");
const time = document.querySelector("#time-taken");
const charsScrambled = document.querySelector("#chars-scrambled");
const wordsScanned = document.querySelector("#words-scanned");
const wordsMatched = document.querySelector("#words-matched");
const backwards = document.querySelector("#back");
const refresh = document.querySelector("#refresh");
const copied = document.querySelector(".copied");
const facebook = document.querySelector("#facebook");
const twitter = document.querySelector("#twitter");
const whatsapp = document.querySelector("#whatsapp");
const sms = document.querySelector("#sms");
let valid = false;
let loading = false;

// validate inputs and set error mesages
function setSuccess(input, errorField, message) {
	input.style.outline = "none";
	errorField.innerHTML = message;
	errorField.style.color = "rgba(61, 161, 15, 0.925)";
}

function setError(input, errorField, message) {
	input.style.outline = "2px solid red";
	errorField.innerHTML = message;
	errorField.style.color = "red";
}

function validateToRedact() {
	if (toRedact.value.trim() === "") {
		valid = false;
		setError(toRedact, toRedactErrorField, "Please enter text to redact*");
	} else {
		valid = true;
		setSuccess(toRedact, toRedactErrorField, "Text to redact");
	}
	return valid;
}

function validateToScramble() {
	if (toScramble.value.trim() === "") {
		valid = false;
		setError(
			toScramble,
			toScrambleErrorField,
			"Please enter word(s) to Scramble*"
		);
	} else {
		valid = true;
		setSuccess(toScramble, toScrambleErrorField, "word(s) to Scramble");
	}
	return valid;
}
function validateToReplace() {
	if (toReplace.value.trim() === "") {
		valid = false;
		setError(
			toReplace,
			toReplaceErrorField,
			"Please enter replacement character(s)*"
		);
	} else {
		valid = true;
		setSuccess(toReplace, toReplaceErrorField, "Replacement Character(s)");
	}
	return valid;
}

function validateInputs() {
	toRedact.addEventListener("blur", () => {
		validateToRedact();
	});
	toRedact.addEventListener("keyup", () => {
		validateToRedact();
	});
	toScramble.addEventListener("blur", () => {
		validateToScramble();
	});
	toScramble.addEventListener("keyup", () => {
		validateToScramble();
	});
	toReplace.addEventListener("blur", () => {
		validateToReplace();
	});
	toReplace.addEventListener("keyup", () => {
		validateToReplace();
	});
}
validateInputs();

// function to sisplay loader
function showLoader() {
	redactBtn.innerHTML = '<i class="fa fa-circle-o-notch fa-spin"></i>';
	setTimeout(() => {
		redactContent.style.display = "none";
		redactedContent.style.display = "block";
	}, 1000);
}
// scramble onclick
function scrambleText() {
	redactBtn.addEventListener("click", (e) => {
		e.preventDefault();
		if (validateToRedact() && validateToScramble() && validateToReplace()) {
			showLoader();
			scramble__words();
		}
	});
}
scrambleText();

// function to scramble words
function scramble__words() {
	let startTime = new Date().getTime();
	let timeTaken;
	let totalChar = 0;

	// array of original text minus space
	let originalArr = toRedact.value
		.toLowerCase()
		.trim()
		.split(/(\s+)/)
		.filter((el) => el.trim().length > 0);

	// array of words to be scrambled  minus space
	let wordsToRedact = toScramble.value
		.toLowerCase()
		.trim()
		.split(/(\s+)/)
		.filter((el) => el.trim().length > 0);

	// Join words to redact to enable regex OR (|) method
	let regexOptions = wordsToRedact.join("|");

	// convert words to regex;
	let regex = new RegExp(regexOptions, "gi");

	let found;
	if (originalArr.length === 1) {
		found = originalArr[0].includes(wordsToRedact[0]);
	} else {
		found = originalArr.some((r) => wordsToRedact.includes(r));
	}

	let redacted = "";

	if (found) {
		redacted = toRedact.value.replace(regex, (match) => {
			return match
				.trim()
				.split("")
				.map((el) => (el ? toReplace.value : ""))
				.join("")
				.substr(0, match.length);
		});
		redactedField.value = redacted;
	} else {
		redactedField.value = "No Match Found!";
	}

	// Lord Help us!!!
	let matched = redacted
		.split(" ")
		.filter((el) => el.includes(toReplace.value))
		.map((el) => {
			let firstIdx;
			let lastIdx;
			// Now this is one crazy down the rabbit i don't understand algo...
			if (toReplace.value.length > 1) {
				let arr = toReplace.value.split("");
				let empt = [];
				let empt2 = [];
				let idx;
				let idx2;
				for (let i = 0; i < arr.length; i++) {
					idx = el.indexOf(arr[i]);
					idx2 = el.lastIndexOf(arr[i]);
					empt.push(idx);
					empt2.push(idx2);
				}
				firstIdx = Math.min(...empt);
				lastIdx = Math.max(...empt2);
			} else {
				firstIdx = el.indexOf(toReplace.value);
				lastIdx = el.lastIndexOf(toReplace.value);
			}
			let newStr = el.substr(firstIdx, lastIdx + 1);
			return newStr;
		});

	wordsScanned.textContent = originalArr.length;
	wordsMatched.textContent =
		redactedField.value === "No Match Found!" ? 0 : matched.length;
	let stringed = matched.join("");
	totalChar = stringed.length;
	charsScrambled.textContent =
		redactedField.value === "No Match Found!" ? 0 : totalChar;
	let endTime = new Date().getTime();
	timeTaken = endTime - startTime;
	time.textContent = timeTaken + 1.2 + "s";
}

// function  to go back to the redact page
function goBack() {
	backwards.addEventListener("click", (e) => {
		e.preventDefault();
		redactedContent.style.display = "none";
		redactContent.style.display = "block";
		redactBtn.innerHTML = "Redact";
	});
}
goBack();

// function to refresh the page
function restart() {
	refresh.addEventListener("click", (e) => {
		e.preventDefault();
		redactedContent.style.display = "none";
		redactContent.style.display = "block";
		toRedact.value = "";
		toScramble.value = "";
		toReplace.value = "";
		redactedField.value = "";
		wordsScanned.textContent = "";
		wordsMatched.textContent = "";
		charsScrambled.textContent = "";
		time.textContent = "";
		window.location.reload();
	});
}
restart();

//function to copy to clipboard
function copyText() {
	copyBtn.addEventListener("click", (e) => {
		e.preventDefault();
		redactedField.select();
		navigator.clipboard.writeText(redactedField.value);
		copied.style.display = "block";
		setTimeout(() => {
			copied.style.display = "none";
		}, 1000);
	});
}
copyText();

// function share on social media
function shareOnSocialMedia(socialmedia, url) {
	socialmedia.addEventListener("click", (e) => {
		e.preventDefault();
		window.open(url + encodeURIComponent(redactedField.value), "_blank");
	});
}
shareOnSocialMedia(twitter, "https://twitter.com/intent/tweet?text=");
shareOnSocialMedia(facebook, "https://www.facebook.com/sharer/sharer.php?u=");
shareOnSocialMedia(whatsapp, "https://api.whatsapp.com/send?text=");
shareOnSocialMedia(sms, "sms:?body=");

document.querySelector("footer span").textContent = new Date().getFullYear();

///////////////////// SCRAMBLE WORDS //////////////////////////////////

let dict = "0123456789qwertyuiop!@#$%^&*()<>".split("");

let el = document.querySelector(".scramble-text");

function runStrings(count) {
	let string = "";
	for (let i = 0; i < count; i++) {
		string += dict[Math.floor(Math.random() * dict.length)];
	}
	return string;
}

function init(str) {
	let count = str.length;
	let delay = 50;

	let generate = setInterval(() => {
		el.setAttribute("data-before", runStrings(count));
		el.setAttribute("data-after", runStrings(count));

		if (delay > 0) {
			delay--;
		} else {
			if (count < str.length) {
				el.textContent += str[str.length - count - 1];
			}
			count--;

			if (count === -1) {
				clearInterval(generate);
			}
		}
	}, 32);
}
init("Redacter by Chimoski");
