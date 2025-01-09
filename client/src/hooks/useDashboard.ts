import { useQuery } from 'react-query';
import { api } from '../api/client';
import { DashboardStats, ExpenseCategory, UpcomingPayment } from '../types/api';

export const useDashboardStats = () => {
    return useQuery<DashboardStats>(
        'dashboard-stats',
        async () => {
            const response = await api.get('/dashboard/stats');
            return response.data;
        }
    );
};

export const useExpenseCategories = (timeframe: 'week' | 'month' | 'year' = 'month') => {
    return useQuery<ExpenseCategory[]>(
        ['expense-categories', timeframe],
        async () => {
            const response = await api.get('/dashboard/expense-categories', {
                params: { timeframe }
            });
            return response.data;
        }
    );
};

export const useUpcomingPayments = (limit: number = 5) => {
    return useQuery<UpcomingPayment[]>(
        ['upcoming-payments', limit],
        async () => {
            const response = await api.get('/dashboard/upcoming-payments', {
                params: { limit }
            });
            return response.data;
        }
    );
};

// Optional: Add a hook for fetching all dashboard data at once
export const useDashboardData = () => {
    const statsQuery = useDashboardStats();
    const categoriesQuery = useExpenseCategories();
    const paymentsQuery = useUpcomingPayments();

    const isLoading = statsQuery.isLoading || categoriesQuery.isLoading || paymentsQuery.isLoading;
    const error = statsQuery.error || categoriesQuery.error || paymentsQuery.error;

    return {
        stats: statsQuery.data,
        categories: categoriesQuery.data,
        upcomingPayments: paymentsQuery.data,
        isLoading,
        error
    };
};