import { InputGroup, Input, InputRightElement, IconButton } from "@chakra-ui/react";
import { useState } from "react";
import { ViewIcon, ViewOffIcon } from "@chakra-ui/icons";

interface PasswordInputProps {
    onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
}

const PasswordInput: React.FC<PasswordInputProps > = ({ onChange }) => {
    const [show, setShow] = useState(false)
    const handleClick = () => setShow(!show)
  
    return (
      <InputGroup size='md'>
        <Input
          type={show ? 'text' : 'password'}
          placeholder='Enter password (min. 12 characters)'
          onChange={onChange}
          minLength={12}
        />
        <InputRightElement>
          <IconButton
            variant='ghost'
            onClick={handleClick} 
            aria-label='Show/Hide password'
            icon={show ? <ViewOffIcon/> : <ViewIcon/>}
            size='sm'
            isRound={true}
          />
        </InputRightElement>
      </InputGroup>
    )
}

export default PasswordInput;