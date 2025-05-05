// src/Layout/Login.tsx
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import OAuthConfig from "../Config/Config";

const Login = () => {
  const [username, setUserName] = useState("");
  const [password, setPassWord] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    const accessToken = localStorage.getItem("token");
    if (accessToken) navigate("/home");
  }, [navigate]);

  const handleLogin_Basic = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError("");
    setSuccess("");

    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });

      if (!response.ok) {
        const err = await response.json();
        throw new Error(err.message || "Đăng nhập thất bại");
      }

      const { jwt } = await response.json();
      localStorage.setItem("token", jwt);
      setSuccess("Đăng nhập thành công");
      alert("Đăng nhập thành công");
      navigate("/home");
    } catch (err: any) {
      setError(err.message);
    }
  };

  const handleLogin_Google = () => {
    const { clientId, redirectUri, authUri } = OAuthConfig;
    const scope = "openid email profile";
    const url = `${authUri}?client_id=${clientId}&redirect_uri=${encodeURIComponent(
      redirectUri
    )}&response_type=code&scope=${encodeURIComponent(scope)}&access_type=offline&prompt=consent`;
    window.location.href = url;
  };

  return (
    <div className="container py-5">
      <section className="vh-100 d-flex align-items-center justify-content-center">
        <div className="col-md-6">
          <div className="card p-5 shadow-lg border-0 rounded-4">
            <h3 className="text-center fw-bold mb-4">Đăng nhập</h3>

            {error && <div className="alert alert-danger">{error}</div>}
            {success && <div className="alert alert-success">{success}</div>}

            <form onSubmit={handleLogin_Basic}>
              <div className="mb-3">
                <input
                  type="text"
                  className="form-control"
                  placeholder="Username"
                  value={username}
                  onChange={(e) => setUserName(e.target.value)}
                  required
                />
              </div>

              <div className="mb-3">
                <input
                  type="password"
                  className="form-control"
                  placeholder="Password"
                  value={password}
                  onChange={(e) => setPassWord(e.target.value)}
                  required
                />
              </div>

              <button type="submit" className="btn btn-primary w-100 mb-3">
                Đăng nhập
              </button>
            </form>

            <div className="text-center">Hoặc</div>

            <button
              type="button"
              className="btn btn-danger w-100 mt-3"
              onClick={handleLogin_Google}
            >
              Đăng nhập với Google
            </button>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Login;
