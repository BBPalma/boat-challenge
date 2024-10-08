import { extendTheme } from "@chakra-ui/react";

// Extend the default Chakra theme
const theme = extendTheme({
  // Customize the button component
  components: {
    Button: {
      // Set the default color scheme to "teal"
      defaultProps: {
        colorScheme: "teal",
        variant: "outline"
      },
    },
  },
});

export default theme;