import React, { useState } from "react";
import axios from "axios";
import Highlight from "react-highlight";

function Access() {
  const [code, setCode] = useState("");
  const [text, setText] = useState("");
  const find = async (event) => {
    event.preventDefault();
    let url = "https://ttd-one.vercel.app/find/";
    url = url.concat(code);
    console.log(url);
    axios.get(url).then(function (response) {
      console.log(response.data);
      setText(response.data);
    });
  };
  return (
    <div className="bg-[#EEEEEE]">
      <div className="pt-24 px-10 flex flex-col">
        <p className="text-3xl mb-2 text-[#0B2447] font-semibold max-[640px]:text-2xl ">
          Enter code here
        </p>
        <br />

        <div className="flex flex-row">
          <input
            className="text-2xl  bg- rounded-lg p-1 text-[#0B2447] w-48"
            value={code}
            onChange={(e) => setCode(e.target.value)}
          ></input>
          <button
            className=" text-slate-800 font-semibold text-2xl bg-slate-100 mx-10 rounded-lg p-1 w-28 shadow-xl"
            onClick={find}
          >
            Submit
          </button>
        </div>
        {text ? (
          <div className="m">
            <Highlight>{text}</Highlight>
          </div>
        ) : (
          <div></div>
        )}
      </div>
    </div>
  );
}

export default Access;
