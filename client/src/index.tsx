import React from 'react';
import ReactDOM from 'react-dom/client';
import { ChakraProvider, ChakraProviderProps } from '@chakra-ui/react';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";

const queryClient = new QueryClient({
    defaultOptions: {
        queries: {
            refetchOnWindowFocus: false,
            retry: 1,
        },
    },
});

const ChakraProviderWithProps: React.FC<ChakraProviderProps> = ChakraProvider;

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);

root.render(
    <React.StrictMode>
        <QueryClientProvider client={queryClient}>
            <ChakraProviderWithProps>
                <BrowserRouter>
                    <App />
                </BrowserRouter>
            </ChakraProviderWithProps>
        </QueryClientProvider>
    </React.StrictMode>
);