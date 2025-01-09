import React from 'react';
import {
    Box,
    Grid,
    Heading,
    Stack,
    Text,
    Card,
    Stat,
    Progress,
    StatUpIndicator,
    Alert,
    Spinner
} from '@chakra-ui/react';
import { MdTrendingUp, MdTrendingDown } from 'react-icons/md';
import { useDashboardData } from '../hooks/useDashboard';

const Dashboard: React.FC = () => {
    const { stats, categories, upcomingPayments, isLoading, error } = useDashboardData();

    if (isLoading) {
        return (
            <Box textAlign="center" py={10}>
                <Spinner size="xl" />
            </Box>
        );
    }

    if (error || !stats) {
        return (
            <Alert.Root status="error">
                <Alert.Content>
                    Error loading dashboard data
                </Alert.Content>
            </Alert.Root>
        );
    }

    return (
        <Stack gap={6}>
            <Box>
                <Heading size="lg" mb={4}>Dashboard</Heading>
                <Text color="gray.600">Overview of your financial status</Text>
            </Box>

            <Grid templateColumns={{ base: "1fr", md: "repeat(3, 1fr)" }} gap={6}>
                {/* Current Balance */}
                <Card.Root>
                    <Card.Body>
                        <Stat.Root>
                            <Stat.Label>Current Balance</Stat.Label>
                            <Stat.ValueText>${stats.balance.toLocaleString()}</Stat.ValueText>
                            <Stat.HelpText>
                                <StatUpIndicator />
                                23.36%
                            </Stat.HelpText>
                        </Stat.Root>
                    </Card.Body>
                </Card.Root>

                {/* Monthly Income */}
                <Card.Root>
                    <Card.Body>
                        <Stat.Root>
                            <Stat.Label>Monthly Income</Stat.Label>
                            <Stat.ValueText color="green.500">
                                <Box as="span" mr={2}>
                                    <MdTrendingUp />
                                </Box>
                                ${stats.monthlyIncome.toLocaleString()}
                            </Stat.ValueText>
                            <Stat.HelpText>From all sources</Stat.HelpText>
                        </Stat.Root>
                    </Card.Body>
                </Card.Root>

                {/* Monthly Expenses */}
                <Card.Root>
                    <Card.Body>
                        <Stat.Root>
                            <Stat.Label>Monthly Expenses</Stat.Label>
                            <Stat.ValueText color="red.500">
                                <Box as="span" mr={2}>
                                    <MdTrendingDown />
                                </Box>
                                ${stats.monthlyExpenses.toLocaleString()}
                            </Stat.ValueText>
                            <Stat.HelpText>Total spending</Stat.HelpText>
                        </Stat.Root>
                    </Card.Body>
                </Card.Root>
            </Grid>

            {/* Savings Goal */}
            <Card.Root>
                <Card.Header>
                    <Heading size="md">Savings Goal Progress</Heading>
                </Card.Header>
                <Card.Body>
                    <Box mb={4}>
                        <Text mb={2}>
                            ${stats.currentSavings.toLocaleString()} of ${stats.savingsGoal.toLocaleString()}
                        </Text>
                        <Progress.Root
                            value={(stats.currentSavings / stats.savingsGoal) * 100}
                            colorScheme="blue"
                            borderRadius="full"
                        />
                    </Box>
                    <Text color="gray.600" fontSize="sm">
                        You're {Math.round((stats.currentSavings / stats.savingsGoal) * 100)}% of the way to your savings goal
                    </Text>
                </Card.Body>
            </Card.Root>

            {/* Recent Activity Summary */}
            <Grid templateColumns={{ base: "1fr", md: "repeat(2, 1fr)" }} gap={6}>
                <Card.Root>
                    <Card.Header>
                        <Heading size="md">Expense Categories</Heading>
                    </Card.Header>
                    <Card.Body>
                        <Stack gap={3}>
                            <Box>
                                <Text mb={1}>Food & Dining</Text>
                                <Progress.Root value={75} colorScheme="green" size="sm" />
                            </Box>
                            <Box>
                                <Text mb={1}>Transportation</Text>
                                <Progress.Root value={45} colorScheme="blue" size="sm" />
                            </Box>
                            <Box>
                                <Text mb={1}>Entertainment</Text>
                                <Progress.Root value={30} colorScheme="purple" size="sm" />
                            </Box>
                        </Stack>
                    </Card.Body>
                </Card.Root>

                <Card.Root>
                    <Card.Header>
                        <Heading size="md">Upcoming Payments</Heading>
                    </Card.Header>
                    <Card.Body>
                        <Stack gap={4}>
                            <Box p={3} borderWidth={1} borderRadius="md">
                                <Text fontWeight="medium">Netflix Subscription</Text>
                                <Text color="gray.600" fontSize="sm">Due in 3 days</Text>
                            </Box>
                            <Box p={3} borderWidth={1} borderRadius="md">
                                <Text fontWeight="medium">Electricity Bill</Text>
                                <Text color="gray.600" fontSize="sm">Due in 5 days</Text>
                            </Box>
                        </Stack>
                    </Card.Body>
                </Card.Root>
            </Grid>
        </Stack>
    );
};

export default Dashboard;