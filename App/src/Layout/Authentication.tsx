// src/Layout/Authentication.tsx
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Authentication = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const authCode = new URLSearchParams(window.location.search).get("code");
    if (!authCode) return;

    const fetchToken = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/auth/outbound/authentication?code=${authCode}`,
          { method: "POST" }
        );

        const data = await response.json();
        localStorage.setItem("token", data.jwt);
        navigate("/home");
      } catch (error) {
        console.error("Lỗi xử lý mã xác thực:", error);
        navigate("/login");
      }
    };

    fetchToken();
  }, [navigate]);

  return <div>Đang xác thực...</div>;
};

export default Authentication;
