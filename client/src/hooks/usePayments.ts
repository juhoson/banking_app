import { api } from '../api/client';
import { Payment } from '../types/api';
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";

export const usePayments = () => {
    return useQuery<Payment[]>(
        'payments',
        async () => {
            const response = await api.get('/payments');
            return response.data;
        }
    );
};

export const useCreatePayment = () => {
    const queryClient = useQueryClient();

    return useMutation(
        async (newPayment: Omit<Payment, 'id'>) => {
            const response = await api.post('/payments', newPayment);
            return response.data;
        },
        {
            onSuccess: () => {
                queryClient.invalidateQueries('payments');
            },
        }
    );
};