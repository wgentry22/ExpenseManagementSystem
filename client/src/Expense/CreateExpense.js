import React, { useState } from 'react';
import * as Yup from 'yup';
import { Formik, Form } from 'formik';
import { API_URL, EXPENSE_TYPES } from '../constants';
import { Container, TextField, Button, MenuItem, makeStyles, Dialog, DialogTitle, DialogContent, DialogActions } from '@material-ui/core';
import { MuiPickersUtilsProvider, DatePicker } from '@material-ui/pickers';
import DateFnsUtils from '@date-io/date-fns';
import { ApplicationSnackbar } from '../components/ApplicationSnackbar';

const locationRegex = /^[a-zA-Z0-9\s'].*/;

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

const ExpenseSchema = Yup.object().shape({
  location: Yup.string().matches(locationRegex, 'Invalid Format').required('Required'),
  amount: Yup.number().positive('Must be positive').required('Required'),
  expenseType: Yup.string().required('Required'),
  date: Yup.string().required('Required')
});


const getValidationMessages = field => {
  if (Boolean(field.value)) {
    if (field.name === 'location' && !locationRegex.exec(field.value)) {
      return "Invalid Format";
    }

    if (field.name === 'account' && typeof field.name === 'number' &&field.value < 0) {
      return "Must be positive";
    }
  } else {
    return "Required";
  }
}

const validate = (field, errors, formikProps) => {
  if (errors.hasOwnProperty(field.name)) {
    errors[field.name] = getValidationMessages(field);
  } else {
    Object.defineProperty(errors, field.name, {value: getValidationMessages(field), writable: true, configurable: true})
  }
  formikProps.setErrors(errors);
}

const attemptCreateExpense = async expense => {
  const response = await fetch(`${API_URL}/api/v1/expense`, {
    method: 'POST',
    mode: 'cors',
    credentials: 'include',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      'Access-Control-Allow-Origin': 'http://localhost:3000',
      'Access-Control-Expose-Headers': 'Location'
    },
    body: JSON.stringify(expense)
  });
  return response;
}

const snackbarFactory = hasError => {
  if (!hasError) {
    return (
      <ApplicationSnackbar 
        key={'create-expense-success'}
        variant={'success'}
        open={true}
        sleep={2000}
        message={'Expense created successfully!'}
      />
    )
  } else {
    return (
      <ApplicationSnackbar 
        key={'create-expense-failure'}
        variant={'error'}
        open={true}
        sleep={2000}
        message={'Failed to create expense'}
      />
    )
  }
}

export const CreateExpense = props => {
  const classes = useStyles();
  const [submitted, setSubmitted] = useState(false);
  const [hasError, setHasError] = useState(null);
  const [expenseType, setExpenseType] = useState('');
  const [date, setDate] = useState(new Date());
  const { isOpen, handleClose } = props;


  const defaultValues = {
    location: '',
    expenseType: '',
    amount: "",
    date: date
  }

  function handleDateChange(date) {
    setDate(date);
  }

  function handleExpenseTypeChange(event) {
    setExpenseType(event.target.value);
  }

  return (
    <Container maxWidth={'xs'} className={classes.paper}>
      <Formik
        initialValues={defaultValues}

        validationSchema={ExpenseSchema}

        validateOnChange={false}
        validateOnBlur={true}

        onSubmit={ async (values, actions) => {
          setSubmitted(true);
          const response = await attemptCreateExpense(values);
          try {
            if (response.ok) {
              setHasError(false);
              const expenseId = response.headers.get('Location').split('/').pop();
              props.onExpenseCreate(expenseId);
              props.handleClose();
            } else {
              setHasError(true);
            }
          } finally {
            actions.setSubmitting(false);
            actions.resetForm();
            actions.setTouched({
              location: false,
              expenseType: false,
              amount: false,
              date: false
            });
            return;
          }
        }}

        render={({
          values,
          errors,
          touched,
          handleChange,
          isSubmitting,
          setFieldValue,
          handleSubmit,
          ...formikProps
        }) => (
          <Form>
            <Dialog open={isOpen} onClose={handleClose}>
              <DialogTitle>Create Expense</DialogTitle>
              <DialogContent>
                <TextField 
                  type="text"
                  name="location"
                  id="location"
                  label={"Location"}
                  error={touched.location || Boolean(errors.location)}
                  fullWidth
                  margin={'normal'}
                  onBlur={e => validate({name: e.target.name, value: e.target.value}, errors, formikProps)}
                  value={values.location}
                  onChange={handleChange}
                  helperText={errors.location}
                />
                <TextField 
                  name="amount"
                  id="amount"
                  label={"Amount"}
                  error={touched.amount || Boolean(errors.amount)}
                  fullWidth
                  margin={'normal'}
                  value={values.amount}
                  onChange={handleChange}
                  helperText={errors.amount}
                  onBlur={e => validate({name: e.target.name, value: e.target.value}, errors, formikProps)}
                />
                <TextField 
                  select
                  name="expenseType"
                  id="expenseType"
                  label={"Expense Type"}
                  error={touched.expenseType || Boolean(errors.expenseType)}
                  helperText={errors.expenseType}
                  fullWidth
                  margin={'normal'}
                  value={values.expenseType}
                  onChange={e => {
                    handleChange(e);
                    handleExpenseTypeChange(e);
                  }}
                >
                  {EXPENSE_TYPES.map(type => (
                    <MenuItem key={type.key} value={type.value} selected={type.value === expenseType}>
                      {type.key}
                    </MenuItem>
                  ))}
                </TextField>
                <MuiPickersUtilsProvider utils={DateFnsUtils}>
                    <DatePicker 
                      margin={'normal'}
                      id="date"
                      name="date"
                      label={'Expense Date'}
                      value={values.date}
                      fullWidth
                      error={Boolean(errors.date)}
                      helperText={errors.date}
                      format="MMMM dd, yyyy"
                      onChange={e => {
                        const time = e.toISOString();
                        setFieldValue('date', time);
                        handleDateChange(time);
                      }}
                      onBlur={e => validate({name: e.target.name, value: e.target.value}, errors, formikProps)}
                    />
                </MuiPickersUtilsProvider>
              </DialogContent>
              <DialogActions className={classes.actions}>
                <Button 
                  type="submit"
                  color="primary"
                  variant="contained"
                  fullWidth
                  disabled={isSubmitting || Boolean(Object.keys(errors).length)}
                  onClick={handleSubmit}
                >
                  Submit Expense
                </Button>
                <Button 
                  color="secondary" 
                  onClick={e => {
                    handleClose(e);
                    formikProps.resetForm();
                  }}
                  variant="contained"
                >
                  Cancel
                </Button>
              </DialogActions>
            </Dialog>
            { (submitted) ? snackbarFactory(hasError) : (<div></div>) }
          </Form>
        )}
      >
      </Formik>
    </Container>
  )
}