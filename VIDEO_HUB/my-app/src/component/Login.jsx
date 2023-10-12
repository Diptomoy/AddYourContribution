import React from 'react';
import {
  Container,
  Heading,
  VStack,
  Input,
  Button,
  Text,
} from '@chakra-ui/react';
import { Link } from 'react-router-dom';
const Login = () => {
  return (
    <Container maxW={'container.xl'} h={'100vh'} p={'16'}>
      <form>
        <VStack
          alignItems={'stretch'}
          spacing={'8'}
          w={['full', '96']}
          m={'auto'}
          my={'16'}
        >
          <Heading>Welcome Back</Heading>
          <Input
            placeholder={'Email'}
            type={'email'}
            required
            focusBorderColor={'purple.900'}
          />
          <Input
            placeholder={'Password'}
            type={'password'}
            required
            focusBorderColor={'purple.900'}
          />
          <Button variant={'link'} alignSelf={'end'}>
            <Link to={'/forgetpassword'}>Forget Password?</Link>
          </Button>
          <Button colorScheme="purple" type="submit" variant={'solid'}>
            Login
          </Button>
          <Text textAlign={'left'}>New User?</Text>
          <Button
            variant={'link'}
            colorScheme={'purple'}
            bgColor={'purple'}
            py={'3'}
          >
            <Link to={'/signup'}>Sign Up</Link>
          </Button>
        </VStack>
      </form>
    </Container>
  );
};

export default Login;
