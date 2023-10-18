import {
  Avatar,
  Button,
  Container,
  Heading,
  Input,
  Text,
  VStack,
} from '@chakra-ui/react';
import React from 'react';
import { Link } from 'react-router-dom';
import { SiGnuprivacyguard } from 'react-icons/si';
const Signup = () => {
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
          <Heading borderBottom={'solid white'} w={'fit-content'}>
            VIDEO HUB
          </Heading>
          <Avatar alignSelf={'center'} boxSize={'32'} />

          <Input
            placeholder={'Name'}
            type={'text'}
            required
            focusBorderColor={'yellow.500'}
          />
          <Input
            placeholder={'Email'}
            type={'email'}
            required
            focusBorderColor={'green.500'}
          />
          <Input
            placeholder={'Password'}
            type={'password'}
            required
            focusBorderColor={'purple.500'}
          />

          <Button colorScheme={'purple'} type={'submit'}>
            Sign Up
          </Button>

          <Text textAlign={'right'}>
            Already Signed Up?{' '}
            <Button variant={'link'} colorScheme={'yellow'}>
              <Link to={'/login'}>
                <SiGnuprivacyguard />
              </Link>
            </Button>
          </Text>
        </VStack>
      </form>
    </Container>
  );
};

export default Signup;
