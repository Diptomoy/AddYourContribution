import { Box, Heading, Container, Stack, Image, Text } from '@chakra-ui/react';
import React from 'react';
import { Carousel } from 'react-responsive-carousel';
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import image1 from '../assets/dumb.jpg';
import image2 from '../assets/dumb2.jpg';
import image3 from '../assets/dumb3.jpg';
import image4 from '../assets/dumb4.jpg';
import image5 from '../assets/dumb5.png';

const headingOptions = {
  pos: 'absolute',
  left: '50%',
  top: '50%',
  transform: 'translate(-50%,-50%)',
  textTransform: 'uppercase',
  p: '4',
  size: '4xl',
};
const Home = () => {
  return (
    <Box>
      <MyCarousel />
      <Container maxW={'container.xl'} minH={'100vh'} p={'16'}>
        <Heading
          textTransform={'uppercase'}
          py={'2'}
          w={'fit-content'}
          borderBottom={'2px solid'}
          margin={'auto'}
        >
          Services
        </Heading>
        <Stack
          h={'full'}
          p={'4'}
          alignItems={'center'}
          direction={['column', 'row']}
        >
          <Image
            src={image5}
            h={['40', '400']}
            alt="vector-graphics"
            filter={'hue-rotate(-130deg)'}
          />
          <Text
            letterSpacing={'widest'}
            lineHeight={'190%'}
            p={['4', '16']}
            textAlign={'center'}
          >
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Perferendis
            ipsum laboriosam possimus quod corporis dolor consectetur fugit
            libero. Nulla fugiat rerum nostrum provident sequi incidunt repellat
            accusantium nihil dolore aliquam?
          </Text>
        </Stack>
      </Container>
    </Box>
  );
};

const MyCarousel = () => (
  <Carousel
    autoPlay
    infiniteLoop
    interval={1000}
    showStatus={false}
    showThumbs={false}
    showArrows={false}
  >
    <Box w={'full'} h={'100vh'}>
      <img src={image1} alt="hyaat" />
      <Heading bgColor={'blackAlpha.600'} color={'white'} {...headingOptions}>
        Watch the future
      </Heading>
    </Box>
    <Box w={'full'} h={'100vh'}>
      <img src={image2} alt="hyaat" />
      <Heading bgColor={'whiteAlpha.600'} color={'black'} {...headingOptions}>
        Gaming is the future
      </Heading>
    </Box>
    <Box w={'full'} h={'100vh'}>
      <img src={image3} alt="hyaat" />
      <Heading bgColor={'blackAlpha.600'} color={'white'} {...headingOptions}>
        Gaming on console
      </Heading>
    </Box>
    <Box w={'full'} h={'100vh'}>
      <img src={image4} alt="hyaat" />
      <Heading bgColor={'blackAlpha.600'} color={'white'} {...headingOptions}>
        Chill Vibes
      </Heading>
    </Box>
  </Carousel>
);
export default Home;
