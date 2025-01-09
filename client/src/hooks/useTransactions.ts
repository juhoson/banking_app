import { useQuery } from 'react-query';
import { api } from '../api/client';
import { Transaction } from '../types/api';

export const useTransactions = (page = 0, size = 10) => {
    return useQuery<Transaction[]>(
        ['transactions', page, size],
        async () => {
            const response = await api.get(`/transactions`, {
                params: { page, size }
            });
            return response.data;
        }
    );
};

export const useTransaction = (id: number) => {
    return useQuery<Transaction>(
        ['transaction', id],
        async () => {
            const response = await api.get(`/transactions/${id}`);
            return response.data;
        }
    );
};