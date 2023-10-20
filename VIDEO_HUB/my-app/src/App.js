import React from 'react';
import Header from './component/Header';
import Home from './component/Home';
import Login from './component/Login';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Signup from './component/Signup';
import Upload from './component/Upload';
import Video from './component/Video';
import Footer from './component/Footer';
function App() {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/upload" element={<Upload />} />
        <Route path="/videos" element={<Video />} />
      </Routes>
      <Footer />
    </Router>
  );
}

export default App;
