import * as React from 'react';
import {
    Dialog,
    Button,
    Input,
    VStack
} from '@chakra-ui/react';
import { useToast } from "@chakra-ui/toast";
import { useCreatePayment } from '../hooks/usePayments';
import { Field } from "./ui/field";

interface NewPaymentModalProps {
    isOpen: boolean;
    onClose: () => void;
}

const NewPaymentModal: React.FC<NewPaymentModalProps> = ({ isOpen, onClose}) => {
    const createPayment = useCreatePayment();
    const toast = useToast();
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const form = e.target as HTMLFormElement;
        const formData = new FormData(form);

        try {
            await createPayment.mutateAsync({
                name: formData.get('name') as string,
                amount: parseFloat(formData.get('amount') as string),
                nextDue: formData.get('nextDue') as string,
                status: 'ACTIVE'
            });

            toast({
                title: 'Payment created',
                status: 'success',
                duration: 3000,
            });
            onClose();
        } catch (error) {
            toast({
                title: 'Error creating payment',
                description: error instanceof Error ? error.message : 'Unknown error',
                status: 'error',
                duration: 5000,
            });
        }
    };

    return (
        <Dialog.Root isOpen={isOpen} onClose={onClose}>
            <Dialog.Context />
            <Dialog.Content>
                <form onSubmit={handleSubmit}>
                    <Dialog.Header>Create New Payment</Dialog.Header>
                    <Dialog.CloseTrigger />
                    <Dialog.Body>
                        <VStack gap={4}>
                            <Field isRequired label={"Payment Name"}>
                                <Input name="name" placeholder="Enter payment name" />
                            </Field>
                            <Field isRequired label={"Amount"}>
                                <Input name="amount" type="number" step="0.01" placeholder="Enter amount" />
                            </Field>
                            <Field isRequired label={"Next Due Date"}>
                                <Input name="nextDue" type="date" />
                            </Field>
                        </VStack>
                    </Dialog.Body>

                    <Dialog.Footer>
                        <Button variant="ghost" mr={3} onClick={onClose}>
                            Cancel
                        </Button>
                        <Button
                            colorScheme="blue"
                            type="submit"
                            _loading={createPayment.isLoading}
                        >
                            Create Payment
                        </Button>
                    </Dialog.Footer>
                </form>
            </Dialog.Content>
        </Dialog.Root>
    );
};

export default NewPaymentModal;