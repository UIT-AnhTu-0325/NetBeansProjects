import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Layout } from "./component/layout";
import { Home } from "./component/home";
import { ListSubject } from "./component/listSubject";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home/>} />
          <Route path="/subjects" element={<ListSubject />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
