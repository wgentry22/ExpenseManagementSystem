import React from 'react';
import { ApplicationSnackbar } from './ApplicationSnackbar';

export const SnackbarFactory = props => {
  const { hasError, submitted, successMsg, errorMsg, sleep } = props;

  if (submitted) {
    if (!hasError) {
      return (
        <ApplicationSnackbar 
          key={'create-expense-success'}
          variant={'success'}
          open={true}
          sleep={(Boolean(sleep)) ? sleep : 2000}
          message={successMsg}
        />
      )
    } else {
      return (
        <ApplicationSnackbar 
          key={'create-expense-failure'}
          variant={'error'}
          open={true}
          sleep={(Boolean(sleep)) ? sleep : 2000}
          message={errorMsg}
        />
      )
    }
  } else {
    return (<div></div>)
  }
}