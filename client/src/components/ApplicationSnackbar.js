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
  const [open, setOpen] = useState(true);

  function handleClose(e) {
    setOpen(false);
  }

  const { variant } = props;

  return (
    <Snackbar 
      anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      key={'applicationSnackbar'}
      autoHideDuration={props.sleep}
      open={(props.open && open)}
      onClose={handleClose}
    >
      <SnackbarContent 
        className={classes[variant]}
        message={
          <span id="application-snackbar" className={classes.message}>
            <Icon className={clsx(classes.icon, classes.iconVariant)} component={variantIcon[variant]}/>
            {props.message}
          </span>
        }
      />
    </Snackbar>
  )
}