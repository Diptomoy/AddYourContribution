const metadata = {
    images: [],

    ch: {
        size: 1,
        level: 1,
        bettel: 1,
    },

    totalPoints: 0,

    score: {
        1: [2, 8],

        2: [3, 8],

        3: [3, 8],

        4: [4, 8],
    },

    achivements: [
        // {
        //     level: 1,
        //     size: 4,
        //     moves: 10,
        //     points: 50,
        //     win: true
        // }
    ],

    isFree: true,
};

const current = {
    points: 0,
    level: 0,
    size: 0,
    isFirstClick: true,
    firstSelectedElm: null,
    matrix: [],
    currentPoint: 0,
    currentMoves: 0,
    disclosedCell: 0,
};

function generateCard() {
    let block = document.getElementById("achivements");

    let movesStyle = document.createElement("div");
    let moves = document.createElement("span");
    moves.textContent = current.currentMoves + " moves";
    movesStyle.appendChild(moves);

    moves = document.createElement("span");
    moves.textContent = current.currentPoint;
    moves.className = "points";

    let points = document.createElement("span");
    points.textContent = " points";
    points.className = "pointsText";

    let heighScore = document.createElement("div");
    heighScore.className = "heighScore";

    heighScore.appendChild(moves);
    heighScore.appendChild(points);
    heighScore.appendChild(movesStyle);

    let img = document.createElement("img");
    img.src =
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT6aF2efuTOPw6BmL8epcdf4YHuo7088sqVU5lE1GTGbQ&s";
    img.className = "levelImg";

    movesStyle = document.createElement("div");
    movesStyle.className = "scoreStack";

    movesStyle.appendChild(img);
    movesStyle.appendChild(heighScore);

    heighScore = document.createElement("div");
    heighScore.textContent = `LEVEL ${current.level}`;
    heighScore.className = "levelDesc";

    let innerStyle = document.createElement("div");
    innerStyle.className = "innerStyle";

    innerStyle.appendChild(heighScore);
    innerStyle.appendChild(movesStyle);

    heighScore = document.createElement("div");
    heighScore.textContent =
        current.size ** 2 < 10 ? `0${current.size ** 2}` : current.size ** 2;
    heighScore.className = "matrix";

    img = document.createElement("img");
    img.src = "./images/Red-Ribbon-Badge-Transparent-PNG.png";
    img.className = "Batch";

    movesStyle = document.createElement("div");
    movesStyle.className = "levelBlock";

    movesStyle.appendChild(innerStyle);
    movesStyle.appendChild(heighScore);
    movesStyle.appendChild(img);

    block.appendChild(movesStyle);
}

function terminateGame(win) {
    metadata.achivements.push({
        level: current.level,
        size: current.size,
        moves: current.currentMoves,
        points: current.currentPoint,
        win: current.disclosedCell == 0,
    });
    metadata.totalPoints += current.currentPoint;
    document.getElementById("tPoints").textContent = metadata.totalPoints;
    generateCard();

    localStorage.setItem("total_point", metadata.totalPoints);
    localStorage.setItem("levels", current.level);

    current.points = 0;
    current.level = 0;
    current.size = 0;
    current.isFirstClick = true;
    current.firstSelectedElm = null;
    current.matrix = [];
    current.currentPoint = 0;
    current.currentMoves = 0;
    current.disclosedCell = 0;
    console.log(metadata.achivements);
    let r = confirm(`want to play ${win ? "next level" : "try again"}`);
    if (win && r) {
        l = getLevelAndSize();
        metadata.ch.bettel++;
        prepareMatrix(...l);
    } else if (!win && r) prepareMatrix(metadata.ch.level, metadata.ch.size);
}

async function handleClicks(c) {
    let cell = c.target;
    const [row, col] = String(cell.id).replace("rc", "").split("-");

    cell.classList.add("containImage");
    cell.style.backgroundImage = `url(${
        metadata.images[current.matrix[row][col]]
    })`;

    setTimeout(
        () => {
            metadata.isFree = false;
            if (current.isFirstClick) {
                current.firstSelectedElm = cell;
                current.isFirstClick = false;
            } else {
                let preCell = current.firstSelectedElm;

                if (
                    cell.style.backgroundImage == preCell.style.backgroundImage
                ) {
                    current.currentPoint += current.points;
                    current.disclosedCell -= 2;
                    cell.removeEventListener("click", handleClicks);
                    preCell.removeEventListener("click", handleClicks);
                } else {
                    cell.style.backgroundImage = "";
                    preCell.style.backgroundImage = "";
                    cell.classList.remove("containImage");
                    preCell.classList.remove("containImage");
                    current.currentPoint -= 0.5;
                }

                current.firstSelectedElm = null;
                current.isFirstClick = true;
            }

            current.currentMoves++;
            document.getElementById("moves").textContent = current.currentMoves;
            document.getElementById("points").textContent =
                current.currentPoint;

            if (current.disclosedCell == 0) terminateGame(true);
            metadata.isFree = true;
        },
        !metadata.isFree ? 1000 : 500
    );
}

function generateCell(row, col, isEditional = false) {
    let cell = document.createElement("div");
    cell.id = `rc${row}-${col}`;
    cell.className = "board-cell";

    if (!isEditional) cell.addEventListener("click", handleClicks);
    else {
        cell.style.background = "#ffffff88";
    }

    return cell;
}

function generateGrid() {
    current.level = metadata.ch.level;
    let level = current.level;
    let size = current.size;

    let mainBoard = document.getElementById("mainBoard");
    while (mainBoard.firstChild) {
        mainBoard.removeChild(mainBoard.lastChild);
    }

    let dim = "";
    for (let index = 0; index < size; index++) {
        dim += "1fr ";
    }

    mainBoard.style.gridTemplateColumns = dim;
    mainBoard.style.gridTemplateRows = dim;
    current.points = level * (level + 1);
    current.currentMoves = 0;
    document.getElementById("levels").textContent = metadata.ch.level;
    document.getElementById("battel").textContent = metadata.ch.bettel;
    document.getElementById("moves").textContent = current.currentMoves;
    document.getElementById("points").textContent = current.currentPoint;
    document.getElementById("pPoints").textContent = current.points;
    let p = Math.pow(size, 2);
    current.disclosedCell = p % 2 == 0 ? p : p - 1;

    current.matrix.forEach((arr, ind) => {
        arr.forEach((v, i) => {
            mainBoard.appendChild(generateCell(ind, i, v == -1));
        });
    });
}

function prepareMatrix(level, size) {
    current.size = size;
    current.level = level;

    let arr = [];
    let img = level + 1;
    let pair = 0;
    let p = Math.pow(size, 2);

    if (p % 2 == 0) {
        pair = p / 2;
    } else {
        pair = (p - 1) / 2;
        arr.push(-1);
    }

    p = pair;
    let ig;
    for (ig = 0; ig < img; ig++) {
        k =
            ig == img - 1
                ? p
                : Math.floor(Math.random() * (p - img + ig + 1)) + 1;

        for (let tmp = 0; tmp < k * 2; tmp++) arr.push(ig);

        p -= k;
    }

    arr.sort(() => Math.random() - 0.5);

    for (ig = 0; ig < size; ig++) {
        current.matrix.push(arr.slice(ig * size, ig * size + size));
    }

    generateGrid();
}

function prepareImages() {
    metadata.images = [
        "./images/test1.jpeg",
        "./images/test2.jpeg",
        "./images/test3.jpeg",
        "./images/test4.jpeg",
    ];
}

function getLevelAndSize() {
    if (metadata.ch.size >= metadata.score[metadata.ch.level][1]) {
        metadata.ch.level++;
        if (metadata.ch.level == Math.max(Object.keys(metadata.score))) {
            ans = confirm("thanks for playing, want to restart game?");
            if (ans) {
                metadata.ch.level = 1;
                metadata.ch.size = 2;
            } else {
                console.warn("game over");
                throw "game over";
            }
        }

        metadata.bettel = 1;
        metadata.ch.size = metadata.score[metadata.ch.level][0];
        return [metadata.ch.level, metadata.ch.size];
    } else {
        metadata.images.sort(() => Math.random() - 0.5);
        return [metadata.ch.level, ++metadata.ch.size];
    }
}

function main() {
    let name = localStorage.getItem("playerName");

    if (!name) {
        while (!(name = prompt("enter your name")));
        localStorage.setItem("playerName", name);
    } else if (localStorage.getItem("total_point")) {
        alert(
            `hello ${name}, are you ready to challege yourself by bitting your heigh scrore ${localStorage.getItem(
                "total_point"
            )}\nyou played excellent with Level${localStorage.getItem(
                "levels"
            )}. best of luck...`
        );
    }

    document.getElementById("playerName").textContent = name;

    let tVal = localStorage.getItem("");
    prepareImages();

    l = getLevelAndSize();
    prepareMatrix(...l);
}

function clearDetails() {
    localStorage.removeItem("total_point");
    localStorage.removeItem("levels");
    alert("sucessfully remove details");
}

main();
