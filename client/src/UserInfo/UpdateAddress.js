import React, { useState } from 'react';
import { makeStyles, Container, TextField, MenuItem, Card, CardHeader, CardContent, Box, CardActions, Button } from '@material-ui/core';
import { API_URL, CORS_URL, STATES, convertStateNameToAbbreviation } from '../constants';
import * as Yup from 'yup';
import { Formik, Form } from 'formik';
import { SnackbarFactory } from '../components/SnackbarFactory';
import { MuiThemeProvider } from '@material-ui/core/styles';
import { emsSecondaryTheme } from '../themes/emsTheme';

const attemptUpdateAddress = address => {
  return fetch(`${API_URL}/api/v1/info/address`, {
    method: 'PUT',
    credentials: 'include',
    mode: 'cors',
    headers: {
      "Accept": "application/json;charset=UTF-8",
      "Content-Type": "application/json;charset=UTF-8",
      'Access-Control-Allow-Origin': CORS_URL,
    },
    body: JSON.stringify(address)
  });
}

const AddressSchema = Yup.object().shape({
  street: Yup.string().required('Required'),
  city: Yup.string().required('Required'),
  state: Yup.string().required('Required'),
  zipCode: Yup.number().required('Required').positive('Zip Code must be positive')
});

const getDefaultValues = currentAddress => {
  return {
    street: currentAddress.street,
    city: currentAddress.city,
    state: currentAddress.state,
    zipCode: currentAddress.zipCode
  }
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
  },
  rowLeft: {
    marginRight: theme.spacing(1)
  },
  rowRight: {
    marginLeft: theme.spacing(1)
  },
}));

export const UpdateAddress = props => {
  const classes = useStyles();
  const { address, handleAddressChange } = props;
  const [hasError, setHasError] = useState(false);
  const [submitted, setSubmitted] = useState(false);
  const [selectedState, setSelectedState] = useState('');

  const defaultValues = getDefaultValues(address);

  if (selectedState !== '' && defaultValues.state !== '') {
    setSelectedState(convertStateNameToAbbreviation(defaultValues.state));
  }
  
  return (
    <Container
      maxWidth={'md'}
      className={classes.paper}
    >
      <Formik
        initialValues={defaultValues}
        defaultValues={defaultValues}
        validationSchema={AddressSchema}
        validateOnChange={false}
        validateOnBlur={true}

        onSubmit={(values, actions) => {
          setSubmitted(true);
          const result = attemptUpdateAddress(values);
          result.then(res => {
            res.json(data => {
              handleAddressChange(data.address);
              actions.resetForm(defaultValues);
              setHasError(false);
            })
          }).catch(err => {
            setHasError(true);
          })
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
            <Card>
              <CardHeader title={'Your Address'} />
              <CardContent>
                  <TextField
                    type="text"
                    name="street"
                    label="Street Address"
                    value={values.street}
                    error={touched.street || Boolean(errors.street) }
                    helperText={errors.street}
                    fullWidth
                    onChange={handleChange}
                    margin={'normal'}
                  />
                  <TextField
                    type="text"
                    name="city"
                    label="City"
                    value={values.city}
                    error={touched.city || Boolean(errors.city) }
                    helperText={errors.city}
                    fullWidth
                    onChange={handleChange}
                    margin={'normal'}
                  />
                  <Box
                    display={'flex'}
                    flexDirection={'row'}
                    justifyContent={'space-between'}
                  >
                    <TextField
                      select
                      type="text"
                      name="state"
                      label="State"
                      className={classes.rowLeft}
                      value={convertStateNameToAbbreviation(values.state)}
                      error={touched.state || Boolean(errors.state) }
                      helperText={errors.state}
                      fullWidth
                      margin={'normal'}
                      onChange={
                        e => {
                          formikProps.setFieldValue('state', e.target.value);
                          setSelectedState(e.target.value);
                        }
                      }
                    >
                      {STATES.map(state => {
                        return (
                          <MenuItem key={state.abbreviation} value={state.abbreviation} selected={state.abbreviation === values.state} >
                            {state.abbreviation}
                          </MenuItem>
                        )
                      })}
                    </TextField>
                    <TextField
                      type="text"
                      name="zipCode"
                      label="Zip Code"
                      className={classes.rowRight}
                      value={values.zipCode}
                      error={touched.zipCode || Boolean(errors.zipCode) }
                      helperText={errors.zipCode}
                      onChange={handleChange}
                      margin={'normal'}
                    />
                  </Box>
              </CardContent>
              <CardActions>
                <MuiThemeProvider theme={emsSecondaryTheme}>
                  <Button
                    type="submit"
                    color={'primary'}
                    variant="contained"
                    fullWidth
                    disabled={isSubmitting || Boolean(Object.keys(errors).length)}
                    onSubmit={handleSubmit}
                  >
                    Update Address
                  </Button>
                  <Button
                    type="button"
                    color={'secondary'}
                    variant={'contained'}
                    onClick={() => formikProps.resetForm(defaultValues)}
                  >
                    Reset
                  </Button>
                </MuiThemeProvider>
              </CardActions>
              <SnackbarFactory
                submitted={submitted}
                hasError={hasError}
                successMsg={`Address updated successfully!`}
                errorMsg={`Failed to update address`}
              />
            </Card>
          </Form>
        )}
      />
    </Container>
  )
}