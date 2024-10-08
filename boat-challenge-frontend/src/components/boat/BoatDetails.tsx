import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Boat } from '../../types/boat';
import { fetchBoatById } from '../../services/boat';
import { useToast, Box, Heading, Button, Text } from '@chakra-ui/react';

const BoatDetails: React.FC = () => {
  const { id } = useParams<{id: string}>();
  const [boat, setBoat] = useState<Boat | null>(null);
  const navigate = useNavigate();
  const toast = useToast();

  useEffect(() => {
    const getBoatDetails = async () => {
        if (id) {
          try {
              const boat = await fetchBoatById(parseInt(id));
              setBoat(boat);
          } catch (err) {
            toast({
              title: "Error",
              description: "Unable to fetch boat details.",
              status: "error",
              duration: 5000,
              isClosable: true,
            });
          }
        }
    };

    getBoatDetails();
  }, [id]);

  const handleBackToList = () => {
    navigate('/boats'); // Navigate back to the boat list
  };

  return (
    <Box p={8}>
      {boat ? (
        <>
          <Heading mb={6}>{boat.name}</Heading>
          <Button colorScheme="teal" size="md" onClick={() => navigate(`/boats/edit/${boat.id}`)}>Edit</Button>
          <Text fontSize="lg" mb={4}>
            Description: {boat.description}
          </Text>
          {boat.date && 
            <Text fontSize="lg" mb={4}>
              Date: {boat.date.toString()}
            </Text>}

          {boat.length &&
            <Text fontSize="lg" mb={4}>
              Description: {boat.length} meters
            </Text>}
          <Button colorScheme="teal" size="md" onClick={() => handleBackToList()}>
            Back to List
          </Button>
        </>
      ) : (
        <Text>Boat details not available.</Text>
      )}
    </Box>
  );
}

export default BoatDetails;