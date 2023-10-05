import React, { useState } from "react";
import TextareaAutosize from "@mui/base/TextareaAutosize";
import axios from "axios";
import Highlight from "react-highlight";


function Generate() {
  const [text, setText] = useState("");
  const [code, setCode] = useState("");
  const [oldCode, setOldCode] = useState("");
  const [data, setData] = useState("");
  const [copy, setCopy] = useState("Copy to Clipboard");

  const find = async (event) => {
    event.preventDefault();
    let url = "https://ttd-one.vercel.app/find/";
    url = url.concat(oldCode);
    console.log(url);
    axios.get(url).then(function (response) {
      console.log(response.data);
      setData(response.data);
    });
  };

  const submit = async (event) => {
    event.preventDefault();
    const formData = new FormData();
    formData.append("text", text);

    axios
      .post("https://ttd-one.vercel.app/insert", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        console.log(response.data);
        setCode(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  };
  function copyText() {
    navigator.clipboard.writeText(data);
    setCopy("Text Copied!");
}

  return (
    <div className="bg-[#D3E0EA]">
      <div className=" p-20 flex flex-col textArea">
        <div className="access flex items-center justify-center mt-3 ">
          <div className="flex flex-row codeInput">
            <span className="text-3xl  p-0 text-[#393E46] font-semibold  max-[640px]:text-2xl hide" style={{fontSize:'1.5em'}}>
              Have a code?
            </span>
            <input
              className="text-2xl mx-3 bg-[#F6F5F5]  rounded-lg p-1 text-[#0B2447] w-48 input"
              style={{ height: "40px" }}
              value={oldCode}
              placeholder="Enter code here"
              onChange={(e) => setOldCode(e.target.value)}
            ></input>
            <button
              className=" text-[#FFFFFF] font-semibold text-2xl mx-3 rounded-lg p-1 w-28 shadow-xl bg-[#276678]"
              style={{ height: "40px" }}
              onClick={find}
            >
              Submit
            </button>
          </div>
        </div>
        {data ? (
          <div className="my-3 py-3">
            <button className="bg-[#276678] mb-3 hover:bg-[#276678] text-white font-bold py-2 px-4 rounded-full" onClick={copyText}>
              {copy}
            </button>
            <Highlight className="bg-[#F6F5F5] border-black border-2 border-dashed" >{data}</Highlight>
          </div>
        ) : (
          <form onSubmit={submit}>
            <TextareaAutosize
              aria-label="minimum height"
              minRows={15}
              className=" mt-4 mb-3 text-md "
              placeholder="Enter your texts, codes here and click the upload button to get a 4 character code"
              style={{ minWidth: 200, maxWidth: 1800 }}
              name="text"
              value={text}
              onChange={(e) => setText(e.target.value)}
            />

            {code ? (
              <div className="mx-auto flex items-center justify-center">
                <p className=" text-2xl text-slate-800 font-semibold">
                  Your Code:
                </p>
                <div className="text-2xl text-slate-800 rounded-lg  ml-5">
                  {code}
                </div>
              </div>
            ) : (
              <div>
                <div className="mx-auto flex items-center justify-center">
                  <button className="text-[#FFFFFF] font-semibold text-2xl mx-3 rounded-lg p-1 w-28 shadow-xl bg-[#276678]">
                    Upload
                  </button>
                </div>
              </div>
            )}
          </form>
        )}
      </div>
    </div>
  );
}

export default Generate;
