import React from 'react';
import { Container, Card, CardHeader, CardContent, makeStyles, Paper, Typography } from '@material-ui/core';
import { getExpenseTypeName, chartColors, MONTHS } from '../../constants';
import Chart from 'react-apexcharts';

const useStyles = makeStyles(theme => ({
  paper: {
    marginTop: theme.spacing(3),
    width: '100%',
    overflowX: 'auto',
    marginBottom: theme.spacing(2),
  }
}));

const ExpenseDoughnut = props => {
  const { expenses, chartLabel, labels } = props;
  const currency = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
  });
  const expenseData = labels.map(label => {
    return expenses[label].reduce((acc, expense) => acc + expense.amount, 0);
  });

  const chartOptions = {
    chart: {
      type: 'donut'
    },
    plotOptions: {
      pie: {
        donut: {
          size: "65%",
          labels: {
            value: {
              formatter: function(value) {
                return currency.format(value);
              },
            },
          }
        }
      }
    },
    legend: {
      labels: {
        colors: 'white'
      }
    },
    labels: labels.map(label => getExpenseTypeName(label)),
    colors: chartColors,
  }

  return (
    <Card>
      <CardHeader title={'Monthly Expenses'} subheader={chartLabel} />
      <CardContent>
        <Chart 
          type={'donut'}
          series={expenseData}
          options={chartOptions}
        />
      </CardContent>
    </Card>
  )
}

export const ExpenseChart = props => {
  const { expenses, label, month, year } = props;
  const classes = useStyles();

  const labels = Object.keys(expenses);
  if (expensesAreEmpty(expenses)) {
    return (
      <Container maxWidth={'sm'} className={classes.paper}>
        <Paper>
          <Card>
            <CardHeader title={label} />
            <CardContent>
              <Typography>No expenses yet for {MONTHS[month]} {year}</Typography>
            </CardContent>
          </Card>
        </Paper>
      </Container>
    )
  } else {
    return (
      <Container maxWidth={'sm'} className={classes.paper}>
        <Paper>
          <ExpenseDoughnut chartLabel={label} expenses={expenses} labels={labels} />
        </Paper>
      </Container>
    )
  }
}

const expensesAreEmpty = expenses => {
  let isEmpty = true;
  for (const expenseType in expenses) {
    if (!isEmpty) {
      break;
    }
    isEmpty = expenses[expenseType].length === 0;
  }
  return isEmpty;
}