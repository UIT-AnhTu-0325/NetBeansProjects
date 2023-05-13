import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Layout } from "./component/layout";
import { Home } from "./component/home";
import { ListSubject } from "./component/listSubject";
import { ListStudent } from "./component/listStudents";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home/>} />
          <Route path="/subjects" element={<ListSubject />} />
          <Route path="/students" element={<ListStudent />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;