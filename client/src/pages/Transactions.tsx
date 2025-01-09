import React from 'react';
import {
    Box,
    Heading,
    Input,
    InputElement,
    Card,
    CardHeader,
    CardBody,
    Stack,
    Text,
    Badge,
    Spinner,
    Alert, HStack,
} from '@chakra-ui/react';
import { MdSearch } from 'react-icons/md';
import { useTransactions } from '../hooks/useTransactions';
import { Table } from "@chakra-ui/react"
import {Transaction} from "../types/api";
const Transactions: React.FC = () => {
    const [searchQuery, setSearchQuery] = React.useState('');
    const { data: transactions, isLoading, error } = useTransactions();

    if (isLoading) {
        return <Spinner />;
    }

    if (error) {
        return (
            <Alert.Root status="error">
                Error loading transactions
            </Alert.Root>
        );
    }

    return (
        <Stack gap={6}>
            <Box>
                <Heading size="lg" mb={4}>Transactions</Heading>
                <Text color="gray.600">View and manage your recent transactions</Text>
            </Box>

            <Card.Root>
                <CardHeader>
                    <HStack maxW="400px">
                        <InputElement pointerEvents="none">
                            <MdSearch color="gray.400" />
                        </InputElement>
                        <Input
                            placeholder="Search transactions..."
                            value={searchQuery}
                            onChange={(e) => setSearchQuery(e.target.value)}
                        />
                    </HStack>
                </CardHeader>
                <CardBody>
                    <Table.Root>
                        <Table.Header>
                            <Table.Row>
                                <Table.ColumnHeader>Date</Table.ColumnHeader>
                                <Table.ColumnHeader>Description</Table.ColumnHeader>
                                <Table.ColumnHeader>Category</Table.ColumnHeader>
                                <Table.ColumnHeader>Amount</Table.ColumnHeader>
                            </Table.Row>
                        </Table.Header>
                        <Table.Body>
                            {transactions?.map((transaction: Transaction) => (
                                <Table.Row key={transaction.id}>
                                    <Table.Cell>{transaction.date}</Table.Cell>
                                    <Table.Cell>{transaction.description}</Table.Cell>
                                    <Table.Cell>
                                        <Badge colorScheme={transaction.type === 'CREDIT' ? 'green' : 'blue'}>
                                            {transaction.category}
                                        </Badge>
                                    </Table.Cell>
                                    <Table.Cell color={transaction.type === 'CREDIT' ? 'green.500' : undefined}>
                                        ${Math.abs(transaction.amount).toFixed(2)}
                                    </Table.Cell>
                                </Table.Row>
                            ))}
                        </Table.Body>
                    </Table.Root>
                </CardBody>
            </Card.Root>
        </Stack>
    );
};

export default Transactions;