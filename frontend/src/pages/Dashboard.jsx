import { useEffect, useState } from "react";

import axios from "axios";

import { useNavigate } from "react-router-dom";

import config from "../config";
import toast from "react-hot-toast";

function Dashboard() {

    const navigate = useNavigate();

    const [students, setStudents] = useState([]);

    const [name, setName] = useState("");

    const [email, setEmail] = useState("");

    const [course, setCourse] = useState("");

    const [editingId, setEditingId] =
        useState(null);
const [searchTerm, setSearchTerm] =
    useState("");

    const [showModal, setShowModal] =
    useState(false);

    const [darkMode, setDarkMode] =
    useState(true);
    const handleLogout = () => {

        localStorage.removeItem("token");

        navigate("/");
    };

    const addStudent = async (e) => {

        e.preventDefault();

        try {

            const token =
                localStorage.getItem("token");

            if (editingId) {

                await axios.put(

                    `${config.BASE_URL}/students/${editingId}`,

                    {
                        name,
                        email,
                        course
                    },

                    {
                        headers: {
                            Authorization:
                                `Bearer ${token}`
                        }
                    }
                );

                toast.success("Student Updated 😄🔥");

            } else {

                await axios.post(

                    `${config.BASE_URL}/students`,

                    {
                        name,
                        email,
                        course
                    },

                    {
                        headers: {
                            Authorization:
                                `Bearer ${token}`
                        }
                    }
                );

                toast.success("Student Added 😄🔥");
            }

            setName("");
            setEmail("");
            setCourse("");

            setEditingId(null);
            setShowModal(false);

            fetchStudents();

        } catch (error) {

            console.log(error);

            toast.success("Operation Failed ❌");
        }
    };

    const deleteStudent = async (id) => {

        try {

            const token =
                localStorage.getItem("token");

            await axios.delete(

                `${config.BASE_URL}/students/${id}`,

                {
                    headers: {
                        Authorization:
                            `Bearer ${token}`
                    }
                }
            );

            toast.success("Student Deleted 😄🔥");

            fetchStudents();

        } catch (error) {

            console.log(error);

            toast.error("Failed To Delete Student ❌");
        }
    };

    const editStudent = (student) => {

        setName(student.name);

        setEmail(student.email);

        setCourse(student.course);

        setEditingId(student.id);
        setShowModal(true);
    };

    const fetchStudents = async () => {

        try {

            const token =
                localStorage.getItem("token");

            const response = await axios.get(

                `${config.BASE_URL}/students`,

                {
                    headers: {
                        Authorization:
                            `Bearer ${token}`
                    }
                }
            );

            setStudents(response.data);

        } catch (error) {

            console.log(error);

            toast.error("Failed To Fetch Students ❌");
        }
    };

    useEffect(() => {

        fetchStudents();

    }, []);

const filteredStudents = students.filter(

    (student) =>

        student.name
            .toLowerCase()
            .includes(searchTerm.toLowerCase())
);

return (

<div className={`

    flex min-h-screen transition-all duration-500

    ${darkMode
        ? "bg-gray-900 text-white"
        : "bg-gray-100 text-black"
    }

`}>
        {/* SIDEBAR */}

<div className={`

    w-64 p-6 flex flex-col transition-all duration-500

    ${darkMode
        ? "bg-gray-800 text-white"
        : "bg-white text-black shadow-xl"
    }

`}>
            <h1 className="text-3xl font-bold text-blue-400 mb-10">

                Admin Panel 🚀

            </h1>

            <ul className="space-y-6 text-lg">

                <li className="hover:text-blue-400 cursor-pointer">

                    Dashboard

                </li>

                <li className="hover:text-blue-400 cursor-pointer">

                    Students

                </li>

                <li className="hover:text-blue-400 cursor-pointer">

                    Analytics

                </li>

                <li className="hover:text-blue-400 cursor-pointer">

                    Settings

                </li>

            </ul>

        </div>

        {/* MAIN CONTENT */}

<div className={`

    flex-1 p-8 transition-all duration-500

    ${darkMode
        ? "bg-gray-900 text-white"
        : "bg-gray-100 text-black"
    }

`}>
            {/* TOP NAVBAR */}

            <div className="flex justify-between items-center mb-10">

                <div>

                    <h1 className="text-4xl font-bold text-blue-400">

                        Student Dashboard 😄🔥

                    </h1>

                    <p className="text-gray-400 mt-2">

                        Manage your students professionally

                    </p>

                </div>

           <div className="flex gap-4">

    <button

        onClick={() =>
            setDarkMode(!darkMode)
        }

        className="bg-gray-700 hover:bg-gray-600 px-5 py-2 rounded-xl font-semibold"

    >

        {
            darkMode
                ? "☀️ Light"
                : "🌙 Dark"
        }

    </button>

    <button

        onClick={handleLogout}

        className="bg-red-500 hover:bg-red-600 px-5 py-2 rounded-xl font-semibold"

    >

        Logout

    </button>

</div>
            </div>

            {/* DASHBOARD CARDS */}

            <div className="grid md:grid-cols-3 gap-6 mb-10">

                <div className={`

    p-6 rounded-2xl shadow-lg

    ${darkMode
        ? "bg-gray-800"
        : "bg-white"
    }

`}>

                    <h2 className="text-xl text-gray-400">

                        Total Students

                    </h2>

                    <p className="text-4xl font-bold text-blue-400 mt-3">

                        {students.length}

                    </p>

                </div>

                <div className={`

    p-6 rounded-2xl shadow-lg transition-all duration-500

    ${darkMode
        ? "bg-gray-800"
        : "bg-white"
    }

`}>

                    <h2 className="text-xl text-gray-400">

                        Courses

                    </h2>

                    <p className="text-4xl font-bold text-green-400 mt-3">

                        5

                    </p>

                </div>

                <div className={`

    p-6 rounded-2xl shadow-lg

    ${darkMode
        ? "bg-gray-800"
        : "bg-white"
    }

`}>

                    <h2 className="text-xl text-gray-400">

                        Active Users

                    </h2>

                    <p className="text-4xl font-bold text-yellow-400 mt-3">

                        12

                    </p>

                </div>

            </div>

            {/* FORM SECTION */}

           <div className="flex justify-end mb-6">

    <button

        onClick={() => {

            setShowModal(true);

            setEditingId(null);

            setName("");

            setEmail("");

            setCourse("");
        }}

        className="bg-blue-500 hover:bg-blue-600 px-6 py-3 rounded-2xl font-bold shadow-lg"

    >

        Add Student ➕

    </button>

</div>

<div className="mb-8">

    <input

        type="text"

        placeholder="Search Students 🔍"

        value={searchTerm}

        onChange={(e) =>
            setSearchTerm(e.target.value)
        }

        className={`

    w-full p-4 rounded-2xl outline-none shadow-lg transition-all duration-500

    ${darkMode
        ? "bg-gray-800 text-white"
        : "bg-white text-black"
    }

`}

    />

</div>
            {/* STUDENT LIST */}

            <h2 className="text-3xl font-bold mb-6 text-yellow-400">

                Students List 📚

            </h2>

            <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">

                {
    filteredStudents.length > 0 ? (

        filteredStudents.map((student) => (

            <div

                key={student.id}

                className="bg-gray-800 p-6 rounded-2xl shadow-lg hover:scale-105 transition"

            >

                <h3 className="text-2xl font-bold text-blue-400 mb-2">

                    {student.name}

                </h3>

                <p className="text-gray-300">

                    📧 {student.email}

                </p>

                <p className="text-gray-300 mb-4">

                    📘 {student.course}

                </p>

                <div className="flex gap-3">

                    <button

                        onClick={() =>
                            editStudent(student)
                        }

                        className="bg-yellow-500 hover:bg-yellow-600 px-4 py-2 rounded-lg font-semibold"

                    >

                        Edit

                    </button>

                    <button

                        onClick={() =>
                            deleteStudent(student.id)
                        }

                        className="bg-red-500 hover:bg-red-600 px-4 py-2 rounded-lg font-semibold"

                    >

                        Delete

                    </button>

                </div>

            </div>
        ))

    ) : (

        <div className="text-center text-gray-400 text-2xl">

            No Students Found 😢

        </div>
    )
}
                

            </div>

        </div>
{
    showModal && (

        <div className="fixed inset-0 bg-black bg-opacity-60 flex justify-center items-center z-50">

            <div className={`

    p-8 rounded-2xl w-[400px] shadow-2xl transition-all duration-500

    ${darkMode
        ? "bg-gray-800 text-white"
        : "bg-white text-black"
    }

`}>

                <div className="flex justify-between items-center mb-6">

                    <h2 className="text-2xl font-bold text-green-400">

                        {
                            editingId
                                ? "Update Student ✏️"
                                : "Add Student ➕"
                        }

                    </h2>

                    <button

                        onClick={() =>
                            setShowModal(false)
                        }

                        className="text-red-400 text-2xl"

                    >

                        ✕

                    </button>

                </div>

                <form
                    onSubmit={addStudent}
                    className="grid gap-4"
                >

                    <input
                        type="text"
                        placeholder="Enter Name"
                        value={name}
                        onChange={(e) =>
                            setName(e.target.value)
                        }
                        className={`

    p-3 rounded-lg outline-none transition-all duration-500

    ${darkMode
        ? "bg-gray-700 text-white"
        : "bg-gray-100 text-black"
    }

`}
                    />

                    <input
                        type="email"
                        placeholder="Enter Email"
                        value={email}
                        onChange={(e) =>
                            setEmail(e.target.value)
                        }
                        className={`

    p-3 rounded-lg outline-none transition-all duration-500

    ${darkMode
        ? "bg-gray-700 text-white"
        : "bg-gray-100 text-black"
    }

`}
                    />

                    <input
                        type="text"
                        placeholder="Enter Course"
                        value={course}
                        onChange={(e) =>
                            setCourse(e.target.value)
                        }
className={`

    p-3 rounded-lg outline-none transition-all duration-500

    ${darkMode
        ? "bg-gray-700 text-white"
        : "bg-gray-100 text-black"
    }

`}                    />

                    <button
                        type="submit"
                        className="bg-blue-500 hover:bg-blue-600 p-3 rounded-lg font-bold"
                    >

                        {
                            editingId
                                ? "Update Student"
                                : "Add Student"
                        }

                    </button>

                </form>

            </div>

        </div>
    )
}
    </div>
);
}

export default Dashboard;