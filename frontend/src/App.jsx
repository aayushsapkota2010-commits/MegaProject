import {
  BrowserRouter,
  Routes,
  Route
} from "react-router-dom";

import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import ProtectedRoute from "./components/ProtectedRoute";
import Register from "./pages/Register";
import ForgotPassword from "./pages/ForgotPassword";

function App() {

  return (

    <BrowserRouter>

      <Routes>

        <Route
          path="/"
          element={<Login />}
        />

      <Route
  path="/dashboard"

  element={

    <ProtectedRoute>

      <Dashboard />

    </ProtectedRoute>
  }
/>
<Route
    path="/register"
    element={<Register />}
/>
<Route
    path="/forgot-password"
    element={<ForgotPassword />}
/>

      </Routes>

    </BrowserRouter>
  );
}

export default App;