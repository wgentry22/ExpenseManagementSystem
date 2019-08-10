import React from 'react';
import { Box, CircularProgress } from '@material-ui/core';
import { MonthlyExpenseSummary } from '../Expense/MonthlyExpenseSummary';
import { ExpenseChart } from '../Expense/Chart/ExpenseChart';
import { useExpensesInMonth } from '../hooks/useExpenses';
import { MONTHS } from '../constants';
import { AccountSummaryByDay } from '../Account/AccountSummaryByDay';

export const Dashboard = props => {
  const { expenseId, month, year, account } = props;
  const monthlyExpenses = useExpensesInMonth(expenseId, month, year);
  const label = `Viewing for ${MONTHS[month]} ${year}`;

  if (monthlyExpenses && Object.keys(monthlyExpenses).length) {
    return (
      <Box
        display={'flex'}
        flexDirection={'column'}
        alignContent={'space-between'}
        width={'100%'}
        marginTop={'-50px'}
      >
        <Box 
          display={'flex'}
          flexDirection={'row'}
          justifyContent={'space-between'}
          alignItems={'baseline'}
          width={'100%'}
        >
            <AccountSummaryByDay
              label={label}
              expenseId={expenseId}
              month={month}
              year={year}
              account={account}
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