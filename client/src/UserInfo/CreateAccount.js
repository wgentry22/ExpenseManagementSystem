import React, { useState } from 'react';
import * as Yup from 'yup';
import { Formik, Form } from 'formik';
import { API_URL, ACCOUNT_TYPES, CORS_URL } from '../constants';
import { SnackbarFactory } from '../components/SnackbarFactory';
import { makeStyles, Container, Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, Box, MenuItem } from '@material-ui/core';

const defaultValues = {
  balance: 0,
  type: "",
  name: "",
  monthlyDeposits: 0
}

const AccountSchema = Yup.object().shape({
  balance: Yup.number().positive('Most be positive').required('Required'),
  type: Yup.string().required("Required"),
  name: Yup.string().required("Required"),
  monthlyDeposits: Yup.number().required('Required').moreThan(-1, "Cannot be less than 0")
});

const attemptCreateAccount = account => {
  return fetch(`${API_URL}/api/v1/account`, {
    method: 'POST',
    credentials: 'include',
    mode: 'cors',
    headers: {
      "Accept": "application/json;charset=UTF-8",
      "Content-Type": "application/json;charset=UTF-8",
      'Access-Control-Allow-Origin': CORS_URL,
      'Access-Control-Expose-Headers': 'Location'
    },
    body: JSON.stringify(account)
  });
}

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
}))

export const CreateAccount = props => {
  const { isOpen, handleClose, onAccountCreate } = props;
  const classes = useStyles();
  const [hasError, setHasError] = useState(false);
  const [submitted, setSubmitted] = useState(false);
  const [accountType, setAccountType] = useState('');

  function handleAccountTypeChange(event) {
    setAccountType(event.target.value);
  }

  return (
    <Container
      maxWidth={'md'}
      className={classes.paper}
    >
      <Formik
        initialValues={defaultValues}
        validationSchema={AccountSchema}
        validateOnChange={false}
        validateOnBlur={true}

        onSubmit={ (values, actions) => {
          setSubmitted(true);
          const response = attemptCreateAccount(values);
          response.then(result => {
            if (result.ok) {
              const createdAccount = result.headers.get('Location').split('/').pop();
              console.log(`Created account id: ${createdAccount}`);
              onAccountCreate(createdAccount);
              actions.resetForm(defaultValues);
              setHasError(false);
              handleClose();
            } else {
              setHasError(true);
            }
          });
        }}

        render={({
          values, 
          errors, 
          touched, 
          handleChange, 
          isSubmitting, 
          handleSubmit,
          ...formikProps
        }) => (
          <Form>
            <Dialog open={isOpen} onClose={handleClose}>
              <DialogTitle>Create Account</DialogTitle>
              <DialogContent>
                <Box
                  display={'flex'}
                  flexDirection={'column'}
                  
                >
                  <TextField 
                    type="text"
                    name="name"
                    label="Account Name"
                    value={values.name}
                    error={touched.name || Boolean(errors.name) }
                    helperText={errors.name}
                    fullWidth
                    onChange={handleChange}
                    margin={'normal'}
                  />
                  <TextField 
                    select
                    name="accountType"
                    label="Account Type"
                    value={values.type}
                    error={touched.type || Boolean(errors.type) }
                    helperText={errors.type}
                    fullWidth
                    margin={'normal'}
                    onChange={e => {
                      handleAccountTypeChange(e);
                      formikProps.setFieldValue('type', e.target.value);
                    }}
                  >
                    {ACCOUNT_TYPES.map(type => {
                      return (
                        <MenuItem key={type.key} value={type.value} selected={type.value === accountType}>
                          {type.key}
                        </MenuItem>
                      )
                    })}
                  </TextField>
                  <TextField 
                    name="balance"
                    label="Current Balance"
                    error={touched.balance || Boolean(errors.balance) }
                    helperText={errors.balance}
                    fullWidth
                    value={values.balance}
                    onChange={handleChange}
                    margin={'normal'}
                  />
                  <TextField 
                    name="monthlyDeposits"
                    label="Current Balance"
                    error={touched.monthlyDeposits || Boolean(errors.monthlyDeposits) }
                    helperText={errors.monthlyDeposits}
                    fullWidth
                    value={values.monthlyDeposits}
                    onChange={handleChange}
                    margin={'normal'}
                  />
                </Box>
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
                  Submit
                </Button>
                <Button
                  color="secondary" 
                  onClick={() => {
                    handleClose();
                    formikProps.resetForm();
                  }}
                  variant="contained"
                >
                  Cancel
                </Button>
              </DialogActions>
            </Dialog>
            <SnackbarFactory
              submitted={submitted}
              hasError={hasError}
              successMsg={`Account created successfully!`}
              errorMsg={`Failed to create account`}
            />
          </Form>
        )}
      >
        
      </Formik>
    </Container>
  )
}