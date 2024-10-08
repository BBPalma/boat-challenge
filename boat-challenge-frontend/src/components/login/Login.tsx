import { useState } from 'react';
import api from '../../services/api';
import { useNavigate } from 'react-router-dom';
import { Credentials } from '../../types/credentials';
import { Box, Button, Flex, FormControl, FormLabel, Heading, Input, Stack, useToast } from '@chakra-ui/react';
import PasswordInput from './PasswordInput';

const Login: React.FC = () => {
  const [credentials, setCredentials] = useState<Credentials>({ username: '', password: '' });
  const [loading, setLoading] = useState<boolean>(false);
  const navigate = useNavigate();
  const toast = useToast();

  const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    
    if(!(credentials.password && credentials.username)) {
      toast({
          title: "Missing credentials",
          description: "Please enter both username and password.",
          status: "warning",
          duration: 3000,
          isClosable: true,
      });
        return;
    }

    setLoading(true);
    try {
        // Send login request to backend
        const response = await api.post('/auth/login', credentials);

        const token = response.data.jwt; // JWT token returned from backend
        localStorage.setItem('token', token); // Store token in localStorage

        toast({
            title: "Login successful",
            description: "You have successfully logged in.",
            status: "success",
            duration: 3000,
            isClosable: true,
          });

        // Redirect to boats overview page after login
        navigate('/boats');
    } catch (err) {
        toast({
            title: "Login failed",
            description: "Invalid username or password. Please try again.",
            status: "error",
            duration: 5000,
            isClosable: true,
          });
    } finally {
      setLoading(false);
    }
  };

  return (
    <Flex height="100vh" width="100vw" justifyContent="center" alignItems="center">
        <Box
        p={8}
        maxWidth="400px"
        borderWidth={1}
        borderRadius="lg"
        boxShadow="lg"
        width="100%"
      >
        <Heading as="h2" mb={6} textAlign="center">
          Login
        </Heading>

        <form onSubmit={handleLogin}>
          <Stack spacing={4}>
            <FormControl id="username" isRequired>
              <FormLabel>Username</FormLabel>
              <Input
                type="text"
                placeholder="Enter your username"
                value={credentials.username}
                onChange={(e) => setCredentials({ ...credentials, username: e.target.value })}
              />
            </FormControl>

            {/* Password Field */}
            <FormControl id="password" isRequired>
              <FormLabel>Password</FormLabel>
              <PasswordInput onChange={(e) => setCredentials({ ...credentials, password: e.target.value })}/>
            </FormControl>

            {/* Login Button */}
            <Button
              size="lg"
              width="100%"
              type="submit"
              isLoading={loading}
            >
              Login
            </Button>
          </Stack>
        </form>
      </Box>
    </Flex>
  );
}

export default Login;