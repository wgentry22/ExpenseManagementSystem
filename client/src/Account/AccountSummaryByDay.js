import React from 'react';
import { useExpensesInMonthByDay } from '../hooks/useExpenses';
import { CircularProgress, Paper, makeStyles, Container, Card, CardHeader, CardContent } from '@material-ui/core';
import { getDayOfWeek, chartColors, MONTHS, EXPENSE_TYPES } from '../constants';
import Chart from 'react-apexcharts';

const useStyles = makeStyles(theme => ({
  paper: {
    marginTop: theme.spacing(3),
    width: '100%',
    overflowX: 'auto',
    marginBottom: theme.spacing(2),
  }
}));

const getChartData = (expenses, labels, days) => {
  const chartData = labels.map(label => ({ name: label, data: []}));
  days.forEach(day => {
    EXPENSE_TYPES.forEach(type => {
      const amount = expenses[day.toUpperCase()][type.value].reduce((acc, expense) => acc + expense.amount, 0) || 0;
      chartData.find(data => data.name === type.key).data.push(amount);
    })
  })
  return chartData;
}

const currencyFormatter = new Intl.NumberFormat("en-US", {
  style: 'currency',
  currency: 'USD'
})

export const AccountSummaryByDay = props => {
  const classes = useStyles();
  const { expenseId, month, year } = props;
  const expenses = useExpensesInMonthByDay(expenseId, month, year);

  const days = Object.keys(expenses).map(day => {
    return getDayOfWeek(day);
  });

  const expenseLabels = EXPENSE_TYPES.map(type => type.key);
  
  const chartOptions = {
    xaxis: {
      categories: days,
      labels: {
        style: {
          colors: 'white'
        }
      },
      axisBorder: {
        color: 'white'
      }
    },
    plotOptions: {
      bar: {
        horizontal: true
      }
    },
    yaxis: {
      labels: {
        style: {
          color: 'white'
        }
      },
      axisBorder: {
        color: 'white'
      }
    },
    legend: {
      labels: {
        colors: 'white'
      }
    },
    colors: chartColors,
    tooltip: {
      theme: 'dark',
      y: {
        formatter: function(value) {
          return currencyFormatter.format(value);
        }
      }
    },
    chart: {
      toolbar: {
        show: false
      },
      stacked: true,
      stackType: '100%'
    },
    
  }

  if (expenses && Object.keys(expenses).length) {
    const chartData = getChartData(expenses, expenseLabels, days);
    return (
      <Container 
        maxWidth={'sm'} 
        className={classes.paper}
      >
        <Paper>
          <Card>
            <CardHeader title={`Daily spends for ${MONTHS[month]} ${year}`} subheader={props.label}/>
            <CardContent>
              <Chart
                type="bar"
                height={'260px'}
                options={chartOptions}
                series={chartData}
              />
            </CardContent>
          </Card>
        </Paper>
      </Container>
    )
  } else {
    return <CircularProgress />
  }
}