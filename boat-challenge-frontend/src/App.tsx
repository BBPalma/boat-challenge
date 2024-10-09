import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import BoatList from './components/boat/BoatList';
import BoatDetails from './components/boat/BoatDetails';
import AddOrUpdateBoat from './components/boat/AddOrUpdateBoat';
import Footer from './components/Footer';
import { Box } from '@chakra-ui/react';
import AuthForm from './components/login/AuthForm';

function App() {
  return (
    <Router>
      <Box display="flex" flexDirection="column" minHeight="100vh">
          <Box flex="1" overflow="hidden" display="flex" flexDirection="column">
            <Routes>
              <Route path="/" element={<AuthForm mode="login" />} />
              <Route path="/login" element={<AuthForm mode="login" />} />
              <Route path="/signup" element={<AuthForm mode="signup" />} />
              <Route path="/boats" element={<BoatList />} />
              <Route path="/boats/new" element={<AddOrUpdateBoat />} />
              <Route path="/boats/edit/:id" element={<AddOrUpdateBoat />} />
              <Route path="/boats/:id" element={<BoatDetails />} />
            </Routes>
          </Box>
        <Footer />
      </Box>
    </Router>
  );
}

export default App
