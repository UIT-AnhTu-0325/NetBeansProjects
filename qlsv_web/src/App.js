import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Layout } from "./component/layout";
import { Home } from "./component/home";
import { ListSubject } from "./component/listSubject";
import { ListStudent } from "./component/listStudents";
import "./base/base.css";
import { SubjectDetail } from "./component/subjectDetail";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="/subjects" element={<ListSubject />} />
          <Route path="/students" element={<ListStudent />} />
          <Route path="/subjects/:id" element={<SubjectDetail />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
