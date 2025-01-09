import React from 'react';
import { Box, Flex, Container, Link as ChakraLink } from '@chakra-ui/react';
import { Link as RouterLink, Outlet, LinkProps as RouterLinkProps } from 'react-router-dom';
import { LinkProps as ChakraLinkProps } from '@chakra-ui/react';

const Link = React.forwardRef<HTMLAnchorElement, RouterLinkProps & ChakraLinkProps>((props, ref) => (
    <ChakraLink as={RouterLink} ref={ref} {...props} />
));

const Layout: React.FC = () => {
    return (
        <Box>
            <Box as="nav" bg="blue.500" color="white" py={4}>
                <Container maxWidth="container.xl">
                    <Flex gap={4}>
                        <Link to="/" color="white" _hover={{ textDecoration: 'underline' }}>
                            Dashboard
                        </Link>
                        <Link to="/payments" color="white" _hover={{ textDecoration: 'underline' }}>
                            Payments
                        </Link>
                        <Link to="/transactions" color="white" _hover={{ textDecoration: 'underline' }}>
                            Transactions
                        </Link>
                    </Flex>
                </Container>
            </Box>
            <Box as="main" p={4}>
                <Container maxWidth="container.xl">
                    <Outlet />
                </Container>
            </Box>
        </Box>
    );
};

export default Layout;