import { BrowserRouter, Route, Routes } from "react-router-dom"
import Login from "./Layout/Login"
import Authentication from "./Layout/Authentication"
import Home from "./Layout/Home"


function App() {

  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/Authentication" element={<Authentication />} />
          <Route path="/home" element={<Home />} />
          <Route path="*" element={<Login />} />
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
