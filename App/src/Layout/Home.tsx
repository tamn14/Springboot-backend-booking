// src/Layout/Home.tsx
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Home = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) navigate("/login");
  }, [navigate]);

  return (
    <div className="container mt-5">
      <h1>Trang Home</h1>
      <button
        className="btn btn-secondary mt-3"
        onClick={() => {
          localStorage.removeItem("token");
          navigate("/login");
        }}
      >
        Đăng xuất
      </button>
    </div>
  );
};

export default Home;
