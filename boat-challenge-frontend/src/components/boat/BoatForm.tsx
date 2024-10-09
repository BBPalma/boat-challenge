import { useState } from "react";
import { Box, Button, Flex, FormControl, FormLabel, HStack, Input, Stack } from "@chakra-ui/react";
import { BoatContent } from "../../types/boat";

interface BoatFormProps {
    initialData?: BoatContent;
    onSubmit: (data: BoatContent) => Promise<void>;
    onCancel: () => void;
  }

const BoatForm: React.FC<BoatFormProps> = ({ initialData = { name: "", description: "" }, onSubmit, onCancel}) => {
    const [formState, setFormState] = useState(initialData);
    
    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
      setFormState({
          ...formState,
          [e.target.name]: e.target.value,
        });
      };
    
    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();

      onSubmit(formState);
    }

    return(
      <Box p={8}>
        <form onSubmit={handleSubmit}>
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
            <FormControl id="date">
              <FormLabel>Date</FormLabel>
              <Input
                name="date"
                type='date'
                value={formState.date}
                onChange={handleInputChange}
                placeholder="Boat date"
              />
            </FormControl>
            <FormControl id="length">
              <FormLabel>Length</FormLabel>
              <Input
                name="length"
                type="number"
                value={formState.length}
                onChange={handleInputChange}
                placeholder="Boat length"
              />
            </FormControl>
            <Flex justifyContent="flex-end">
              <HStack spacing='24px'>
                <Button type="submit">
                  Save Boat
                </Button>
                <Button onClick={() => onCancel()}>
                  Cancel
                </Button>
              </HStack>
            </Flex>
          </Stack>
        </form>
      </Box>
    );
};

export default BoatForm;