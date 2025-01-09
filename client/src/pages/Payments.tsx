import React from 'react';
import {
    Box,
    Heading,
    Grid,
    Card,
    Stack,
    Text,
    Button,
    Flex,
    VStack,
    Spinner,
    Alert,
    useDisclosure
} from '@chakra-ui/react';
import { usePayments } from '../hooks/usePayments';
import NewPaymentModal from '../components/NewPaymentModal';
import { MdAdd, MdRepeat } from "react-icons/md";
import {Payment} from "../types/api";
const Payments: React.FC = () => {
    const { open, onOpen, onClose } = useDisclosure();
    const { data: payments, isLoading, error } = usePayments();

    if (isLoading) {
        return (
            <Box textAlign="center" py={10}>
                <Spinner size="xl" />
            </Box>
        );
    }

    if (error) {
        return (
            <Alert.Root status="error">
                <Alert.Content>
                    Error loading payments: {error instanceof Error ? error.message : 'Unknown error'}
                </Alert.Content>
            </Alert.Root>
        );
    }

    return (
        <Stack gap={6}>
            <Box>
                <Heading size="lg" mb={4}>Payments</Heading>
                <Text color="gray.600">Manage your recurring payments and make new transfers</Text>
            </Box>

            <Grid templateColumns={{ base: "1fr", md: "repeat(2, 1fr)" }} gap={6}>
                <Card.Root>
                    <Card.Header>
                        <Flex justify="space-between" align="center">
                            <Heading size="md">Quick Transfer</Heading>
                            <Button
                                colorScheme="blue"
                                onClick={onOpen}
                            >
                                <MdAdd style={{ marginRight: '0.5rem' }} />
                                New Transfer
                            </Button>
                        </Flex>
                    </Card.Header>
                    <Card.Body>
                        <VStack gap={4} align="stretch">
                            <Text color="gray.600">
                                Start a new payment or transfer money between accounts
                            </Text>
                        </VStack>
                    </Card.Body>
                </Card.Root>

                <Card.Root>
                    <Card.Header>
                        <Flex justify="space-between" align="center">
                            <Heading size="md">Recurring Payments</Heading>
                            <Button variant="outline">
                                <MdRepeat style={{ marginRight: '0.5rem' }} />
                                Manage
                            </Button>
                        </Flex>
                    </Card.Header>
                    <Card.Body>
                        <VStack gap={4} align="stretch">
                            {payments?.map((payment: Payment) => (
                                <Box
                                    key={payment.id}
                                    p={4}
                                    borderWidth={1}
                                    borderRadius="md"
                                    _hover={{ bg: 'gray.50' }}
                                >
                                    <Flex justify="space-between" align="center">
                                        <Box>
                                            <Text fontWeight="medium">{payment.name}</Text>
                                            <Text color="gray.600" fontSize="sm">
                                                Next payment: {payment.nextDue}
                                            </Text>
                                        </Box>
                                        <Box textAlign="right">
                                            <Text fontWeight="bold">${payment.amount}</Text>
                                            <Text
                                                fontSize="sm"
                                                color={payment.status === 'ACTIVE' ? 'green.500' : 'gray.500'}
                                            >
                                                {payment.status}
                                            </Text>
                                        </Box>
                                    </Flex>
                                </Box>
                            ))}
                        </VStack>
                    </Card.Body>
                </Card.Root>
            </Grid>

            <NewPaymentModal isOpen={open} onClose={onClose} />
        </Stack>
    );
};

export default Payments;