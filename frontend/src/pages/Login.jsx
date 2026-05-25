import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import config from "../config";
import toast from "react-hot-toast";

import {

    FaUser,
    FaLock,
    FaEye,
    FaEyeSlash

} from "react-icons/fa";
function Login() {

    const [email, setEmail] = useState("");

    const [password, setPassword] = useState("");

    const [showPassword, setShowPassword] =
    useState(false);

    const [loading, setLoading] =
    useState(false);
    const navigate = useNavigate();

    const handleLogin = async (e) => {

        e.preventDefault();
        setLoading(true);

        try {

            const response = await axios.post(

                `${config.BASE_URL}/auth/login`,

                {
                    email,
                    password
                }

            );

     const token = response.data;

if (
    token === "User not found" ||
    token === "Invalid Password"
) {

    toast.error(token);
    return;
}

localStorage.setItem("token", token);
            setLoading(false);

            toast.success("Login Successful 😄🔥");
            navigate("/dashboard");

            console.log(token);

        } catch (error) {

            console.log(error);
            setLoading(false);

            toast.error("Login Failed ❌");
        }
    };
return (

    <div className="min-h-screen flex">

        {/* LEFT SIDE */}

        <div className="hidden md:flex w-1/2 bg-gradient-to-br from-blue-600 to-purple-700 justify-center items-center">

            <div className="text-white p-10">

                <h1 className="text-6xl font-bold mb-6">

                    Welcome Back 😄🔥

                </h1>

                <p className="text-2xl text-gray-200">

                    Manage students with a modern dashboard.

                </p>

            </div>

        </div>

        {/* RIGHT SIDE */}

        <div className="flex-1 flex justify-center items-center bg-gray-100">

            <div className="w-[400px] bg-white/70 backdrop-blur-lg shadow-2xl rounded-3xl p-10">

                <h2 className="text-4xl font-bold text-center mb-8 text-gray-800">

                    Login 🚀

                </h2>

                <form
                    onSubmit={handleLogin}
                    className="space-y-6"
                >

                    {/* USERNAME */}

                    <div className="relative">

                        <FaUser className="absolute top-4 left-4 text-gray-500" />

                       <input
    type="email"
    placeholder="Enter Email"
    value={email}
    onChange={(e) =>
        setEmail(e.target.value)
    }
                            className="w-full pl-12 p-4 rounded-2xl border border-gray-300 outline-none focus:ring-2 focus:ring-blue-500"
                        />

                    </div>

                    {/* PASSWORD */}

                    <div className="relative">

                        <FaLock className="absolute top-4 left-4 text-gray-500" />

                        <input
                            type={
    showPassword
        ? "text"
        : "password"
}
                            placeholder="Enter Password"
                            value={password}
                            onChange={(e) =>
                                setPassword(e.target.value)
                            }
                            className="w-full pl-12 p-4 rounded-2xl border border-gray-300 outline-none focus:ring-2 focus:ring-purple-500"
                        />
                        <div

    onClick={() =>
        setShowPassword(!showPassword)
    }

    className="absolute top-4 right-4 text-gray-500 cursor-pointer"

>

    {
        showPassword
            ? <FaEyeSlash />
            : <FaEye />
    }

</div>

                    </div>
                    <p

    onClick={() =>
        navigate("/forgot-password")
    }

    className="text-right text-blue-500 cursor-pointer font-semibold"

>

    Forgot Password?

</p>

                    {/* BUTTON */}

                 <button

    type="submit"

    disabled={loading}

    className={`

        w-full text-white p-4 rounded-2xl font-bold
        transition-all duration-300

        ${loading
            ? "bg-gray-400 cursor-not-allowed"
            : "bg-gradient-to-r from-blue-500 to-purple-600 hover:scale-105"
        }

    `}

>

    {
        loading
            ? "Loading... 🔄"
            : "Login"
    }

</button>

                    <p className="text-center mt-6 text-gray-700">

    Don't have an account?

    <span

        onClick={() =>
            navigate("/register")
        }

        className="text-blue-500 cursor-pointer font-bold ml-2"

    >

        Register

    </span>

</p>

                </form>

            </div>

        </div>

    </div>
);
}

export default Login;   