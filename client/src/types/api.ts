export interface Transaction {
    id: number;
    date: string;
    description: string;
    amount: number;
    type: 'DEBIT' | 'CREDIT';
    category: string;
}

export interface Payment {
    id: number;
    name: string;
    amount: number;
    nextDue: string;
    status: 'ACTIVE' | 'INACTIVE';
}

export interface DashboardStats {
    balance: number;
    monthlyIncome: number;
    monthlyExpenses: number;
    savingsGoal: number;
    currentSavings: number;
    balanceChange: {
        percentage: number;
        trend: 'increase' | 'decrease';
    };
}

export interface ExpenseCategory {
    name: string;
    amount: number;
    percentage: number;
    color: string;
}

export interface UpcomingPayment {
    id: number;
    name: string;
    amount: number;
    dueDate: string;
    daysUntilDue: number;
}