import React from 'react';
import * as Yup from 'yup';
import { NavLink } from 'react-router-dom';
import { makeStyles, Box, Container, Card, CardHeader, CardContent, TextField, Button, CardActions } from '@material-ui/core'
import { Formik, Form } from 'formik';

const RegistrationFormSchema = Yup.object().shape({
  username: Yup.string()
               .required('Required')
               .min(4, 'Username must have 4 characters or more')
               .max(20, 'Username must have 20 characters or less'),
  email: Yup.string()
            .email('Invalid Format')
            .required('Required'),
  password: Yup.string()
               .required('Required')
               .min(8, 'Password must have 8 characters or more')
               .max(30, 'Password must have 30 characters or less')
})

const attemptRegistration = async values => {
  return fetch('http://localhost:8080/register', {
    method: 'POST',
    mode: 'cors',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      'Access-Control-Allow-Origin': 'http://localhost:3000'
    },
    body: JSON.stringify(values)
  });
}

const useStyles = makeStyles(theme => ({
  form: {
    width: '100%',
    marginTop: theme.spacing(5)
  },
  link: {
    marginTop: theme.spacing(1)
  }
}));

const Registration = props => {
  const styles = useStyles();

  return (
    <Container maxWidth={'md'} className={styles.form}>
      <Formik
        initialValues={{
          username: '',
          email: '',
          password: ''
        }}

        validationSchema={RegistrationFormSchema}

        onSubmit={async (values, actions) => {
          try {
            const response = await attemptRegistration(values);
            if (response.ok) {
              alert('Registration successful!');
            } else {
              actions.setTouched({
                username: false,
                password: false,
                email: false
              })
              actions.setErrors({})  
            }
          } catch (e) {
            actions.setTouched({
              username: false,
              password: false,
              email: false
            })
          } 
        }}

        render={({
          errors,
          touched,
          dirty,
          handleChange,
          isSubmitting,
        }) => (
          <Container maxWidth={'sm'} className={styles.form}>
            <Form>
              <Card>
                <CardHeader title={'Register'} />
                <CardContent>
                  <TextField 
                    type="text"
                    name="username"
                    id="username"
                    label={"Username"}
                    error={(touched.username) || Boolean(errors.username)}
                    fullWidth
                    margin={'normal'}
                    onChange={handleChange}
                    helperText={errors.username}
                  />
                  <TextField 
                    type="email"
                    name="email"
                    id="email"
                    label={"Email"}
                    error={(touched.email && dirty.email) || Boolean(errors.email)}
                    fullWidth
                    margin={'normal'}
                    onChange={handleChange}
                    helperText={errors.email}
                  />
                  <TextField 
                    type="password"
                    name="password"
                    id="password"
                    label={"Password"}
                    error={(touched.password && dirty.password) || Boolean(errors.password)}
                    fullWidth
                    margin={'normal'}
                    onChange={handleChange}
                    helperText={errors.password}
                  />
                </CardContent>
                <CardActions>
                  <Button
                    type="submit"
                    color="primary"
                    variant="contained"
                    fullWidth
                    disabled={isSubmitting || Boolean(Object.keys(errors).length)}
                  >
                    Register
                  </Button>
                </CardActions>
              </Card>
            </Form>
            <Box
              display={'flex'}
              flexDirection={'column'}
              alignItems={'flex-end'}
            >
              <NavLink 
                exact 
                to="/"
                className={styles.link}
              >
                Already a member?
              </NavLink>
            </Box>
          </Container>
        )}
      >
      </Formik>
    </Container>
  )

}

export default Registration;