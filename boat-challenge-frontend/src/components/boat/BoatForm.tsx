import { useState } from "react";
import { Box, Button, FormControl, FormLabel, Input, Stack, useToast } from "@chakra-ui/react";
import { Boat, BoatContent } from "../../types/boat";

interface BoatFormProps {
    initialData?: BoatContent;
    onSubmit: (data: BoatContent) => Promise<void>;
  }

const BoatForm: React.FC<BoatFormProps> = ({ initialData = { name: "", description: "" }, onSubmit }) => {
    const [formState, setFormState] = useState(initialData);
    const toast = useToast();

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
      setFormState({
          ...formState,
          [e.target.name]: e.target.value,
        });
      };
    
    const handleSubmit = async () => {
      try {
          await onSubmit(formState);
          toast({
            title: "Success",
            description: "Boat details saved successfully.",
            status: "success",
            duration: 3000,
            isClosable: true,
          });
        } catch (error) {
          toast({
            title: "Error",
            description: "Failed to save boat details.",
            status: "error",
            duration: 5000,
            isClosable: true,
          });
        }
      };

    return(
      <Box p={8}>
        <Stack spacing={4}>
          <FormControl id="name" isRequired>
            <FormLabel>Boat Name</FormLabel>
            <Input
              name="name"
              value={formState.name}
              onChange={handleInputChange}
              placeholder="Boat name"
            />
          </FormControl>
          <FormControl id="description" isRequired>
            <FormLabel>Description</FormLabel>
            <Input
              name="description"
              value={formState.description}
              onChange={handleInputChange}
              placeholder="Boat description"
            />
          </FormControl>
          <Button colorScheme="teal" onClick={handleSubmit}>
            Save Boat
          </Button>
        </Stack>
      </Box>
    );
};

export default BoatForm;