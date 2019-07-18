import React from 'react';
import { Box, CircularProgress } from '@material-ui/core';
import { MonthlyExpenseSummary } from '../Expense/MonthlyExpenseSummary';
import { ExpenseChart } from '../Expense/Chart/ExpenseChart';
import { useExpensesInMonth } from '../hooks/useExpenses';
import { MONTHS } from '../constants';
import { AccountSummary } from '../Account/AccountSummary';
import { AccountSummaryByDay } from '../Account/AccountSummaryByDay';

export const Dashboard = props => {
  const { expenseId, month, year, accountName } = props;
  const monthlyExpenses = useExpensesInMonth(expenseId, month, year);
  const label = `Viewing for ${MONTHS[month]} ${year}`;

  if (monthlyExpenses && Object.keys(monthlyExpenses).length) {
    return (
      <Box
        display={'flex'}
        flexDirection={'column'}
        alignContent={'space-between'}
        width={'100%'}
      >
        <Box 
          display={'flex'}
          flexDirection={'row'}
          justifyContent={'space-between'}
          width={'100%'}
        >
            {/* <AccountSummary 
            accountId={accountName}
            expenses={monthlyExpenses}
            label={label}
            month={month}
            year={year}
          /> */}
            <AccountSummaryByDay
              label={label}
              expenseId={expenseId}
              month={month}
              year={year}
            />
            <MonthlyExpenseSummary 
              expenses={monthlyExpenses}
              month={month}
              year={year}
            />
            <ExpenseChart 
              type={'donut'}
              month={month}
              label={label}
              expenses={monthlyExpenses}
              year={year}
              expenseId={expenseId}
            />
        </Box>
      </Box>
     )
  } else {
    return <CircularProgress />
  }


}