import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { fetchBoats, deleteBoat } from '../../services/boat';
import { Boat } from '../../types/boat';

import { Box, Heading, Text, IconButton, Stack, useToast, Flex, Button } from '@chakra-ui/react';
import { EditIcon, DeleteIcon } from '@chakra-ui/icons';

const BoatList: React.FC = () => {
  const [boats, setBoats] = useState<Boat[]>([]);
  const navigate = useNavigate();
  const toast = useToast();
  
  useEffect(() => {
    const getBoats = async () => {
        try {
            const fetchedBoats = await fetchBoats();
            setBoats(fetchedBoats);
        } catch (err) {
            toast({
              title: "Error fetching boats.",
              description: "Unable to fetch boats. Please try again later.",
              status: "error",
              duration: 3000,
              isClosable: true,
            });
        }
    };

    getBoats();
  }, [toast]);

  // Handle clicking on a boat to see details
  const handleViewDetails = (boatId: number) => {
    navigate(`/boats/${boatId}`);
  };

  // Handle opening boat editing
  const handleEdit = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>, boatId: number) => {
    e.stopPropagation();
    navigate(`/boats/edit/${boatId}`);
  }

  // Handle deleting a boat
  const handleDelete = async (e: React.MouseEvent<HTMLButtonElement, MouseEvent>, boatId: number) => {
    e.stopPropagation();
    try {
        await deleteBoat(boatId);
        setBoats(boats.filter(boat => boat.id !== boatId)); // Remove the deleted boat from the state
    } catch (err) {
      toast({
        title: "Error deleting boat.",
        description: "Failed to delete the boat. Please try again.",
        status: "error",
        duration: 3000,
        isClosable: true,
      });
    }
  };

  return (
    <Box p={8}>
      <Heading mb={6} textAlign="center">
        Boats List
      </Heading>

      {boats.length === 0 ? (
        <Text textAlign="center" fontSize="xl" color="gray.500">
          No boats available.
        </Text>
      ) : (
        <Stack spacing={5}>
          <Button onClick={() => navigate('/boats/new')}>Add New Boat</Button>

          {boats.map((boat) => (
            <Box
              key={boat.id}
              p={5}
              shadow='md' 
              borderWidth='1px'
              borderRadius='lg'
              backgroundColor="white"
              cursor="pointer"
              _hover={{ boxShadow: "lg", bg:"gray.100", transition: "all 0.2s ease-in-out" }}
              onClick={() => handleViewDetails(boat.id)}
            >
              <Flex justify="space-between" align="center">
                <Box>
                  <Heading fontSize="xl">{boat.name}</Heading>
                  <Text mt={2} color="gray.600">
                    {boat.description}
                  </Text>
                </Box>
                <Stack direction='row' spacing={2} align='center'>
                  <IconButton 
                    onClick={(e) => handleEdit(e, boat.id)}
                    aria-label='Edit Boat'
                    icon={<EditIcon/>}
                    size="sm"/>
                  <IconButton 
                    onClick={(e) => handleDelete(e, boat.id)}
                    aria-label='Delete Boat'
                    icon={<DeleteIcon/>}
                    size="sm"/>
                </Stack>
              </Flex>
            </Box>
          ))}
        </Stack>
      )}
    </Box>
  );
}

export default BoatList;