import {
  Box,
  VStack,
  Heading,
  Stack,
  HStack,
  Button,
  Input,
  Text,
} from '@chakra-ui/react';
import React from 'react';
import { AiOutlineSend } from 'react-icons/ai';
import { FiYoutube, FiTwitter, FiLinkedin } from 'react-icons/fi';
const Footer = () => {
  return (
    <Box bgColor={'blackAlpha.900'} minH={'40'} p={'16'} color={'white'}>
      <Stack direction={['column', 'row']}>
        <VStack alignItems={'stretch'} w={'full'} px={'4'}>
          <Heading
            size={'md'}
            textTransform={'uppercase'}
            textAlign={['center', 'left']}
          >
            Subscribe to our NewsLetter
          </Heading>
          <HStack borderBottom={'2px solid white'} py={'2'}>
            <Input
              placeholder="Enter Email here..."
              border={'none'}
              borderRadius={'none'}
              focusBorderColor={'none'}
            />
            <Button
              p={'0'}
              color={'purple'}
              variant={'ghost'}
              borderRadius={'20px 20px 20px 0'}
            >
              <AiOutlineSend />
            </Button>
          </HStack>
        </VStack>
        <VStack
          w={'full'}
          borderRight={['none', '1px solid white']}
          borderLeft={['none', '1px solid white']}
        >
          <Heading
            size={'md'}
            textTransform={'uppercase'}
            textAlign={'center'}
            borderBottom={'2px solid white'}
          >
            VIDEO HUB
          </Heading>
          <Text>All Rights Reserved</Text>
        </VStack>
        <VStack w={'full'}>
          <Heading
            size={'md'}
            textTransform={'uppercase'}
            borderBottom={'2px solid white'}
          >
            Social Media
          </Heading>
        </VStack>
        <HStack>
          <Button variant={'link'} color={'purple'} p={'1'}>
            <a target="_blank" href="https://www.youtube.com/">
              <FiYoutube size={'28px'} />
            </a>
          </Button>
          <Button variant={'link'} color={'purple'} p={'1'}>
            <a target="_blank" href="https://twitter.com/Arrowe19">
              <FiTwitter size={'28px'} />
            </a>
          </Button>
          <Button variant={'link'} color={'purple'} p={'1'}>
            <a
              target="_blank"
              href="https://www.linkedin.com/in/aranya-das-820741210/"
            >
              <FiLinkedin size={'28px'} />
            </a>
          </Button>
        </HStack>
      </Stack>
    </Box>
  );
};
export default Footer;
