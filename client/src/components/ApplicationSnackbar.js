import React, { useState } from 'react';
import clsx from 'clsx';
import { Snackbar, makeStyles, SnackbarContent, Icon } from '@material-ui/core';
import ErrorIcon from '@material-ui/icons/Error';
import CheckCircleIcon from '@material-ui/icons/CheckCircle';
import { green } from '@material-ui/core/colors';

const useStyles = makeStyles(theme => ({
  error: {
    backgroundColor: theme.palette.error.dark,
  },
  success: {
    backgroundColor: green[600]
  },
  message: {
    display: 'flex',
    alignItems: 'center'
  },
  icon: {
    fontSize: 20
  },
  iconVariant: {
    opacity: 0.9,
    marginRight: theme.spacing(1),
  }
}));

const variantIcon = {
  success: CheckCircleIcon,
  error: ErrorIcon
}

export const ApplicationSnackbar = props => {
  const classes = useStyles();
  const [open, setOpen] = useState(false);

  function handleClose(e) {
    setOpen(true);
  }

  const { variant } = props;

  return (
    <Snackbar 
      anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      key={'applicationSnackbar'}
      autoHideDuration={6000}
      open={props.open && !open}
      onClose={handleClose}
    >
      <SnackbarContent 
        className={classes[variant]}
        message={
          <span id="registered-user" className={classes.message}>
            <Icon className={clsx(classes.icon, classes.iconVariant)} component={variantIcon[variant]}/>
            {props.message}
          </span>
        }
      />
    </Snackbar>
  )
}