import { useState } from "react";

import axios from "axios";

import { useNavigate } from "react-router-dom";

import {

    FaUser,
    FaEnvelope,
    FaLock,
    FaEye,
    FaEyeSlash

} from "react-icons/fa";
import toast from "react-hot-toast";

import config from "../config";

function Register() {

    const navigate = useNavigate();

    const [username, setUsername] =
        useState("");

    const [email, setEmail] =
        useState("");

    const [password, setPassword] =
        useState("");
        const [showPassword, setShowPassword] =
    useState(false);

    const [loading, setLoading] =
    useState(false);

    const handleRegister = async (e) => {
        setLoading(true);

        e.preventDefault();

        try {

            await axios.post(

                `${config.BASE_URL}/auth/signup`,

                {
                    username,
                    email,
                    password
                }
            );

            toast.success(
                "Registration Successful 😄🔥"
            );
            setLoading(false);

            navigate("/");

        } catch (error) {

            console.log(error);
            setLoading(false);

            toast.error(
                "Registration Failed ❌"
            );
        }
    };

    return (

        <div className="min-h-screen flex">

            {/* LEFT SIDE */}

            <div className="hidden md:flex w-1/2 bg-gradient-to-br from-purple-600 to-pink-600 justify-center items-center">

                <div className="text-white p-10">

                    <h1 className="text-6xl font-bold mb-6">

                        Join Us 🚀

                    </h1>

                    <p className="text-2xl text-gray-200">

                        Create your account and manage students easily.

                    </p>

                </div>

            </div>

            {/* RIGHT SIDE */}

            <div className="flex-1 flex justify-center items-center bg-gray-100">

                <div className="w-[400px] bg-white/70 backdrop-blur-lg shadow-2xl rounded-3xl p-10">

                    <h2 className="text-4xl font-bold text-center mb-8 text-gray-800">

                        Register 😄🔥

                    </h2>

                    <form
                        onSubmit={handleRegister}
                        className="space-y-6"
                    >

                        {/* USERNAME */}

                        <div className="relative">

                            <FaUser className="absolute top-4 left-4 text-gray-500" />

                            <input
                                type="text"
                                placeholder="Enter Username"
                                value={username}
                                onChange={(e) =>
                                    setUsername(e.target.value)
                                }
                                className="w-full pl-12 p-4 rounded-2xl border border-gray-300 outline-none focus:ring-2 focus:ring-pink-500"
                            />

                        </div>

                        {/* EMAIL */}

                        <div className="relative">

                            <FaEnvelope className="absolute top-4 left-4 text-gray-500" />

                            <input
                                type="email"
                                placeholder="Enter Email"
                                value={email}
                                onChange={(e) =>
                                    setEmail(e.target.value)
                                }
                                className="w-full pl-12 p-4 rounded-2xl border border-gray-300 outline-none focus:ring-2 focus:ring-purple-500"
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
                                className="w-full pl-12 p-4 rounded-2xl border border-gray-300 outline-none focus:ring-2 focus:ring-blue-500"
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

                        {/* BUTTON */}

                       <button

    type="submit"

    disabled={loading}

    className={`

        w-full text-white p-4 rounded-2xl font-bold
        transition-all duration-300

        ${loading
            ? "bg-gray-400 cursor-not-allowed"
            : "bg-gradient-to-r from-purple-500 to-pink-600 hover:scale-105"
        }

    `}

>

    {
        loading
            ? "Creating Account... 🔄"
            : "Create Account"
    }

</button>

                    </form>

                    {/* LOGIN LINK */}

                    <p className="text-center mt-6 text-gray-700">

                        Already have an account?

                        <span

                            onClick={() =>
                                navigate("/")
                            }

                            className="text-blue-500 cursor-pointer font-bold ml-2"

                        >

                            Login

                        </span>

                    </p>

                </div>

            </div>

        </div>
    );
}

export default Register;