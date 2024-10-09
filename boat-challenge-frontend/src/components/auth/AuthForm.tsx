import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Credentials } from '../../types/credentials';
import { Box, Button, Flex, FormControl, FormLabel, Heading, Input, Stack, useToast, Text } from '@chakra-ui/react';
import api from '../../services/api';
import PasswordInput from './PasswordInput';


interface AuthFormProps {
  mode: 'login' | 'signup';
}

const AuthForm: React.FC<AuthFormProps> = ({ mode }) => {
  const [credentials, setCredentials] = useState<Credentials>({ username: '', password: '' });
  const [loading, setLoading] = useState<boolean>(false);
  const navigate = useNavigate();
  const toast = useToast();

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    
    //naive implementation, but clear possible existing token
    localStorage.removeItem('token');

    setLoading(true);
    try {
        if (mode === 'login') {
            const response = await api.post('/auth/login', credentials);
            const token = response.data.jwt;
            localStorage.setItem('token', token);
            toast({
                title: "Login successful",
                description: "You have successfully logged in.",
                status: "success",
                duration: 3000,
                isClosable: true,
            });
            navigate('/boats');
        } else {
            await api.post('/auth/signup', credentials);
            toast({
                title: "Signup successful",
                description: "You have successfully signed up.",
                status: "success",
                duration: 3000,
                isClosable: true,
            });
            navigate('/login');
        }
    } catch (err) {
        toast({
            title: `${mode.charAt(0).toUpperCase() + mode.slice(1)} failed`,
            description: `An error occurred during ${mode}. Please try again.`,
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
          {mode === 'login' ? 'Login' : 'Signup'}
        </Heading>

        <form onSubmit={handleSubmit}>
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

            <FormControl id="password" isRequired>
              <FormLabel>Password</FormLabel>
              <PasswordInput onChange={(e) => setCredentials({ ...credentials, password: e.target.value })}/>
            </FormControl>

            <Button
              size="lg"
              width="100%"
              type="submit"
              isLoading={loading}
            >
              {mode === 'login' ? 'Login' : 'Signup'}
            </Button>
          </Stack>
        </form>
        <Text mt={4} textAlign="center">
          {mode === 'login' ? (
            <>
              Don't have an account? <Button variant="link" onClick={() => navigate('/signup')}>Signup</Button>
            </>
          ) : (
            <>
              Already have an account? <Button variant="link" onClick={() => navigate('/login')}>Login</Button>
            </>
          )}
        </Text>
      </Box>
    </Flex>
  );
}

export default AuthForm;