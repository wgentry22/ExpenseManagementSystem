import React, { useState } from 'react';
import { getExpenseTypeName } from '../constants';
import { useExpenses } from '../hooks/useExpenses';
import { Box, Container, Table, makeStyles, Paper, TableCell, TableBody, TableHead, TableRow, CircularProgress, Typography, Toolbar } from '@material-ui/core';
import ArrowRight from '@material-ui/icons/ArrowRight';
import ArrowLeft from '@material-ui/icons/ArrowLeft';

const tableHeaders = [
  { id: 'location', label: 'Location', align: 'left'},
  { id: 'amount', label: 'Amount', align: 'right'},
  { id: 'type', label: 'Category', align: 'right'},
  { id: 'date', label: 'Date', align: 'right'}
];

const useStyles = makeStyles(theme => ({
  root: {
    width: '100%',
  },
  paper: {
    marginTop: theme.spacing(3),
    width: '100%',
    marginBottom: theme.spacing(2),
  },
  tableBody: {
    height: 378,
    maxHeight: 378,
    overflowY: 'scroll',
  }
}));

const useHeaderStyles = makeStyles(theme => ({
  root: {
    paddingLeft: theme.spacing(2),
    paddingRight: theme.spacing(1),
  },
  title: {
    flex: '0 0 auto',
  },
}));

const TableHeader = (page) => {
  const classes = useHeaderStyles();

  return (
    <Toolbar className={classes.root}>
      <Typography 
        variant="h5"
        className={classes.title}
      >
        Expenses
      </Typography>
      <Box
        display={'flex'}
        flexDirection={'row'}
        justifyContent={'space-between'}
      >
        {(page > 0) ? (getPageButton('previous')) : (<div></div>)}
        {(getPageButton('next'))}
      </Box>
    </Toolbar>
  )
}

const getPageButton = variant => {
  if (variant === "next") {
    return (
      <TableCell>
        <ArrowRight />
      </TableCell>
    )
  } else if (variant === 'previous') {
    return (
      <TableCell>
        <ArrowLeft />
      </TableCell>
    )
  }
}

export const AllExpensesTable = props => {

  const classes = useStyles();
  const [limit, setLimit] = useState(10);
  const [page, setPage] = useState(0);
  const expenses = useExpenses(props.expenseId, limit, page);

  if (expenses && expenses.length) {
    return (
      <Container maxWidth={'md'} className={classes.root}>
        <Paper className={classes.paper}>
          <TableHeader />
          <Table size="small" >
            <TableHead>
              <TableRow>
                {tableHeaders.map(tr => (
                  <TableCell
                    key={tr.id}
                    align={tr.align}
                    padding={'default'}
                  >
                    {tr.label}
                  </TableCell>
                ))}
              </TableRow>
            </TableHead>
            <TableBody 
              className={classes.tableBody}
            >
              {expenses.map(expense => (
                <TableRow 
                  key={expense.id}
                >
                  <TableCell  align={'left'}>{expense.location}</TableCell>
                  <TableCell  align={'right'}>{expense.amount}</TableCell>
                  <TableCell  align={'right'}>{getExpenseTypeName(expense.expenseType)}</TableCell>
                  <TableCell  align={'right'}>{new Date(expense.date).toDateString()}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Paper>
      </Container>
    )
  } else {
    return <CircularProgress />;
  }
}
