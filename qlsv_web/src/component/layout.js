import { Outlet, Link } from "react-router-dom";
import "./layout.css";
export const Layout = () => {
  return (
    <>
      <div className="container">
        <nav className="nav">
          <div className="nav__logo">Logo Here </div>
          <ul className="nav__list">
            <li className="">
              <Link to="/">Home</Link>
            </li>
            <li>
              <Link to="/subjects">Subjects</Link>
            </li>
            <li>
              <Link to="/students">Students</Link>
            </li>
          </ul>
        </nav>
      </div>

      <Outlet />
    </>
  );
};
