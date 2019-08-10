import React from 'react';
import Container from '@material-ui/core/Container';
import { Formik, Form } from 'formik';
import { NavLink } from 'react-router-dom';
import { API_URL, CORS_URL } from '../constants';
import * as Yup from 'yup';
import { TextField, Card, CardHeader, CardContent, CardActions, Button, makeStyles, Box } from '@material-ui/core';
import { ApplicationSnackbar } from '../components/ApplicationSnackbar';

const LoginFormSchema = Yup.object().shape({
  userId: Yup.string().required('Required'),
  password: Yup.string().required('Required')
});

const attemptAuthentication = form => {
  return fetch(`${API_URL}/login`, {
    method: 'POST', 
    mode: 'cors',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      'Access-Control-Allow-Origin': CORS_URL
    },
    body: JSON.stringify(form)
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

const Login = props => {

  const styles = useStyles();

  const { hasError } = props;

  return (
    <Container maxWidth={'md'} className={styles.form}>
      <Formik
        initialValues={{
          userId: '',
          password: ''
        }}

        validationSchema={LoginFormSchema}

        onSubmit={async (values, actions) => {
          const response = await attemptAuthentication(values);
          if (response.ok) {
            const token = await response.json().then(data => data.token);
            document.cookie = `api_token=${token}; path=/; `
            props.history.push("/home/dashboard");
            return;
          } else {
            response.text().then(data => actions.setStatus({ creds: data }));
            actions.setSubmitting(false);
            actions.setTouched({
              userId: false, 
              password: false
            })
          }
        }}

        render={({
          errors,
          status,
          touched,
          handleChange,
          isSubmitting,
        }) => (
          <Container maxWidth={'sm'} >
            <Form>
              <Card>
                <CardHeader title="Sign In"/>
                <CardContent>
                  <TextField 
                    type="text"
                    name="userId"
                    id="userId"
                    label={"User ID"}
                    error={touched.userId || Boolean(errors.userId)}
                    fullWidth
                    margin={'normal'}
                    onChange={handleChange}
                    helperText={errors.userId}
                  />
                  <TextField 
                    type="password"
                    name="password"
                    id="password"
                    label={"Password"}
                    error={touched.password || Boolean(errors.password)}
                    fullWidth
                    margin={'normal'}
                    onChange={handleChange}
                    helperText={errors.password}
                  />
                  { (status && status.creds) ? status.creds : null }
                </CardContent>
                <CardActions>
                  <Button 
                    type="submit"
                    color="primary"
                    variant="contained"
                    fullWidth
                    disabled={isSubmitting || Boolean(Object.keys(errors).length)}
                  >
                    Submit
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
                to="/register"
                className={styles.link}
              >
                Not a member?
              </NavLink>
            </Box>
          </Container>
        )}
      >
      </Formik>
      <ApplicationSnackbar 
        key={'unauthenticated-snackbar'}
        variant="error"
        open={hasError} 
        message={'You must sign in before continuing'} 
      />
    </Container>
  )

}

export default Login;