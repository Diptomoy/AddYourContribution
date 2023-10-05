import React from 'react'
function Navbar() {
  return (
    <div className='fixed top-0 z-20 w-full bg-gradient-to-r from-[#1687A7] to-[#1687A7]'>
    <div className='mx-5 flex p-3 justify-between'>
    <div className=''><a href="https://ttdrive.vercel.app/"><p className='text-white font-bold text-4xl max-[640px]:text-3xl font-Montserrat'>Temporary Text Drive</p></a></div>
    <div className='mx-3'><a href='https://github.com/TheAkashRoy/TT-Drive' target="_blank" rel="noreferrer"> <i className="fa fa-github" href="github.com/TheAkashRoy"></i></a></div>
    </div>
    </div>
    
  )
}

export default Navbar
