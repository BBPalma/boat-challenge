import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/login/Login';
import BoatList from './components/boat/BoatList';
import BoatDetails from './components/boat/BoatDetails';
import AddOrUpdateBoat from './components/boat/AddOrUpdateBoat';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/boats" element={<BoatList />} />
        <Route path="/boats/new" element={<AddOrUpdateBoat />} />
        <Route path="/boats/edit/:id" element={<AddOrUpdateBoat />} />
        <Route path="/boats/:id" element={<BoatDetails />} />
      </Routes>
    </Router>
  );
}

export default App
