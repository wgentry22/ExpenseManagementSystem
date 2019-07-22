import React from 'react';
import { CircularProgress, Paper, makeStyles, Container, Card, CardHeader, CardContent } from '@material-ui/core';
import { getExpenseTypeName, chartColors, MONTHS } from '../constants';
import Chart from 'react-apexcharts';

const useStyles = makeStyles(theme => ({
  paper: {
    marginTop: theme.spacing(3),
    width: '100%',
    overflowX: 'auto',
    marginBottom: theme.spacing(2),
  },
  actions: {
    paddingRight: '24px',
    paddingLeft: '24px'
  }
}));

export const AccountSummary = props => {
  const classes = useStyles();
  const { account, expenses, month, year } = props;
  const labels = Object.keys(expenses);
  const cardLabel = `Percentage of monthly deposits ${MONTHS[month]} ${year}`;

  const monthlyExpenditures = labels.map(label => {
    return (((expenses[label].reduce((acc, expense) => acc + expense.amount, 0)) / account.monthlyDeposits) * 100).toFixed(2) + '%';
  });

  const chartOptions = {
    xaxis: {
      categories: labels.map(label => getExpenseTypeName(label)),
      labels: {
        style: {
          colors: 'white'
        }
      },
      axisBorder: {
        color: 'white'
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
    plotOptions: {
      bar: {
        distributed: true
      }
    },
    colors: chartColors,
    tooltip: {
      theme: 'dark',
      y: {
        formatter: function(value) {
          return value + '%';
        }
      }
    },
    chart: {
      toolbar: {
        show: false
      }
    },
    dataLabels: {
      enabled: true,
      formatter: function(value) {
        return value + '%';
      }
    },
  }

  if (account && Object.keys(account).length && Object.keys(monthlyExpenditures).filter(entry => !Number.isNaN(entry)).length === 5) {
    return (
      <Container 
        maxWidth={'sm'} 
        className={classes.paper}
       >
        <Paper>
          <Card>
            <CardHeader title={account.name} subheader={cardLabel}/>
            <CardContent>
              <Chart
                type="bar"
                options={chartOptions}
                series={[{name: `${MONTHS[month]} ${year}`, data: monthlyExpenditures}]}
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