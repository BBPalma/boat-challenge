import { Box, Text} from '@chakra-ui/react';

const Footer: React.FC = () => {
  return (
    <Box as="footer" py={4} bg="teal.600" color="white">
      <Text textAlign="center">
        OWT Boat gallery © {new Date().getFullYear()}
      </Text>
    </Box>
  );
};

export default Footer;