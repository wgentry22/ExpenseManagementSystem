import React from 'react';
import { getExpenseTypeName, MONTHS } from '../constants';
import { Card, CardHeader, CardContent, ExpansionPanel, ExpansionPanelSummary, CircularProgress, Typography, ExpansionPanelDetails, makeStyles, Table, TableHead, TableRow, TableCell, TableBody, Container } from '@material-ui/core';
import ExpandMore from '@material-ui/icons/ExpandMore';

const useStyles = makeStyles(theme => ({
  paper: {
    marginTop: theme.spacing(3),
    width: '100%',
    overflowX: 'auto',
    marginBottom: theme.spacing(2),
  }
}));

const getMonthlyExpensePanels = expenses => {
  return Object.keys(expenses).map(expenseType => {
    let total = 0;
    if (expenses[expenseType] && expenses[expenseType].length) {
      total = expenses[expenseType].reduce((a, b) => a+ b.amount, 0);
    }

    const expenseRows = (expenses[expenseType] && expenses[expenseType].length) ? expenses[expenseType].map(expense => {
      return (
        <TableRow
          key={expense.id}
        >
          <TableCell>{expense.location}</TableCell>
          <TableCell>${expense.amount.toFixed(2)}</TableCell>
          <TableCell>{new Date(expense.date).toLocaleDateString("en-US")}</TableCell>
        </TableRow>
      )
    }) : (<Typography>No {getExpenseTypeName(expenseType)} expenses yet!</Typography>)
    if (!Array.isArray(expenseRows)) {
      return (
        <ExpansionPanel
          key={expenseType}
        >
          <ExpansionPanelSummary
            expandIcon={<ExpandMore />}
          >
            <Typography variant="h6">{ `${getExpenseTypeName(expenseType)} - $${total.toFixed(2)}` }</Typography>
          </ExpansionPanelSummary>
          <ExpansionPanelDetails>
            { expenseRows }
          </ExpansionPanelDetails>
        </ExpansionPanel>
      )
    } else {
      return (
        <ExpansionPanel
          key={expenseType}
        >
          <ExpansionPanelSummary
            expandIcon={<ExpandMore />}
          >
            <Typography variant="h6">{ `${getExpenseTypeName(expenseType)} - $${total.toFixed(2)}` }</Typography>
          </ExpansionPanelSummary>
          <ExpansionPanelDetails>
            <Table size="small">
              <TableHead>
                <TableRow>
                  <TableCell>Location</TableCell>
                  <TableCell>Amount</TableCell>
                  <TableCell>Date</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                { expenseRows }
              </TableBody>
            </Table>
          </ExpansionPanelDetails>
        </ExpansionPanel>
      )
    }
  })
}

export const MonthlyExpenseSummary = props => {
  const classes = useStyles();
  const { expenses } = props;

  if (expenses && Object.keys(expenses).length) {
    return (
      <Container maxWidth={'xs'} className={classes.paper}>
        <Card>
          <CardHeader 
            title={`Monthly Expenses`}
            subheader={`Viewing for ${MONTHS[props.month]} ${props.year}`}
          />
          <CardContent>
            {getMonthlyExpensePanels(expenses)}
          </CardContent>
        </Card>
      </Container>
    )
  } else {
    return (
      <CircularProgress />
    )
  }
  

}