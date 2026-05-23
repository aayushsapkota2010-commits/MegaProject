import { useState } from "react";

import axios from "axios";

import toast from "react-hot-toast";

import config from "../config";

function ForgotPassword() {

    const [step, setStep] =
        useState(1);

    const [email, setEmail] =
        useState("");

    const [otp, setOtp] =
        useState("");

    const [newPassword, setNewPassword] =
        useState("");

    /* SEND OTP */

    const sendOtp = async () => {

        try {

            await axios.post(

                `${config.BASE_URL}/otp/send`,

                {
                    email
                }
            );

            toast.success(
                "OTP Sent 😄🔥"
            );

            setStep(2);

        } catch (error) {

            console.log(error);

            toast.error(
                "Failed To Send OTP ❌"
            );
        }
    };

    /* VERIFY OTP */

    const verifyOtp = async () => {

        try {

            await axios.post(

                `${config.BASE_URL}/otp/verify`,

                {
                    email,
                    otp
                }
            );

            toast.success(
                "OTP Verified 😄🔥"
            );

            setStep(3);

        } catch (error) {

            console.log(error);

            toast.error(
                "Invalid OTP ❌"
            );
        }
    };

    /* RESET PASSWORD */

    const resetPassword = async () => {

        try {

            await axios.post(

                `${config.BASE_URL}/auth/reset-password`,

                {
                    email,
                    newPassword
                }
            );

            toast.success(
                "Password Reset Successful 😄🔥"
            );

        } catch (error) {

            console.log(error);

            toast.error(
                "Reset Failed ❌"
            );
        }
    };

    return (

        <div className="min-h-screen flex justify-center items-center bg-gradient-to-br from-blue-500 to-purple-700">

            <div className="bg-white p-10 rounded-3xl shadow-2xl w-[400px]">

                <h1 className="text-3xl font-bold text-center mb-8">

                    Forgot Password 🔐

                </h1>

                {/* STEP 1 */}

                {

                    step === 1 && (

                        <div className="space-y-4">

                            <input

                                type="email"

                                placeholder="Enter Email"

                                value={email}

                                onChange={(e) =>
                                    setEmail(e.target.value)
                                }

                                className="w-full p-4 rounded-2xl border outline-none"

                            />

                            <button

                                onClick={sendOtp}

                                className="w-full bg-blue-500 text-white p-4 rounded-2xl font-bold"

                            >

                                Send OTP

                            </button>

                        </div>
                    )
                }

                {/* STEP 2 */}

                {

                    step === 2 && (

                        <div className="space-y-4">

                            <input

                                type="text"

                                placeholder="Enter OTP"

                                value={otp}

                                onChange={(e) =>
                                    setOtp(e.target.value)
                                }

                                className="w-full p-4 rounded-2xl border outline-none"

                            />

                            <button

                                onClick={verifyOtp}

                                className="w-full bg-purple-500 text-white p-4 rounded-2xl font-bold"

                            >

                                Verify OTP

                            </button>

                        </div>
                    )
                }

                {/* STEP 3 */}

                {

                    step === 3 && (

                        <div className="space-y-4">

                            <input

                                type="password"

                                placeholder="Enter New Password"

                                value={newPassword}

                                onChange={(e) =>
                                    setNewPassword(e.target.value)
                                }

                                className="w-full p-4 rounded-2xl border outline-none"

                            />

                            <button

                                onClick={resetPassword}

                                className="w-full bg-green-500 text-white p-4 rounded-2xl font-bold"

                            >

                                Reset Password

                            </button>

                        </div>
                    )
                }

            </div>

        </div>
    );
}

export default ForgotPassword;