import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { addBoat, updateBoat, fetchBoatById } from '../../services/boat';
import { Box, Flex, Heading, Spinner, useToast } from '@chakra-ui/react';
import { BoatContent } from '../../types/boat';
import BoatForm from './BoatForm';

const AddOrUpdateBoat: React.FC = () => {
    const { id } = useParams<{ id: string }>(); // Get the boat id from the URL (if available)
    const [boatContent, setBoatContent] = useState<BoatContent>({
        name: '',
        description: '',
    });
    const [isEditMode, setIsEditMode] = useState(false);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();
    const toast = useToast();

    // Fetch the boat details if we're in edit mode/id exists
    useEffect(() => {
        if (id) {
          setIsEditMode(true); // Set the component to edit mode
          const fetchBoatDetails = async () => {
              try {
                  const boat = await fetchBoatById(parseInt(id));
                  let {id: _, ...boatInfo} = boat;
                  setBoatContent({...boatInfo});
                  setLoading(false);
              } catch (err) {
                toast({
                  title: "Error",
                  description: "Unable to fetch boat details.",
                  status: "error",
                  duration: 5000,
                  isClosable: true,
                });
              } finally {
                setLoading(false);
              }
          };

          fetchBoatDetails();
        } else {
            setIsEditMode(false); // Set the component to add mode if no `id` is provided
            setLoading(false);
        }
    }, [id, toast]);

    const handleSubmit = async (boatContent: BoatContent) => {
      let errorMessage = 'Something went wrong';
  
      try {
        let successMessage = '';
        if (isEditMode && id) {
          errorMessage = 'Unable to update boat details.';
          await updateBoat(parseInt(id), boatContent); // Update boat if in edit mode
          successMessage = 'Boat updated successfully!';
        } else {
          errorMessage = 'Unable to save new boat.';
          await addBoat(boatContent); // Add a new boat if in add mode
          successMessage = 'Boat added successfully!';
        }

        toast({
          title: "Update/Save successful",
          description: successMessage,
          status: "success",
          duration: 3000,
          isClosable: true,
        });
        
        navigate(-1);

      } catch (err) {
          toast({
            title: "Error",
            description: errorMessage,
            status: "error",
            duration: 5000,
            isClosable: true,
          });
      }
    };

    if (loading) {
      return (
        <Flex justify="center" align="center" height="100vh">
          <Spinner size="xl" />
        </Flex>
      );
    }

    return (
      <Box p={8}>
        <Heading>{isEditMode ? 'Update Boat' : 'Add New Boat'}</Heading>
        <BoatForm 
            onSubmit={handleSubmit}
            onCancel={() => navigate(-1)}
            initialData={boatContent}/>    
      </Box>
    );
};

export default AddOrUpdateBoat;