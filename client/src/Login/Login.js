import React from 'react';
import Container from '@material-ui/core/Container';
import { Formik, Form } from 'formik';
import { NavLink } from 'react-router-dom';
import * as Yup from 'yup';
import { TextField, Card, CardHeader, CardContent, CardActions, Button, makeStyles, Box } from '@material-ui/core';

const LoginFormSchema = Yup.object().shape({
  userId: Yup.string().required('Required'),
  password: Yup.string().required('Required')
});

const attemptAuthentication = form => {
  return fetch("http://localhost:8080/login", {
    method: 'POST', 
    mode: 'cors',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
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


  return (
    <Container maxWidth={'md'} className={styles.form}>
      <Formik
        initialValues={{
          userId: '',
          password: ''
        }}

        validationSchema={LoginFormSchema}

        onSubmit={async (values, actions) => {
          try {
            const response = await attemptAuthentication(values);
            if (response.ok) {
              alert('Authentication works!');
            } else {
              response.text().then(data => actions.setStatus({ creds: data }));
            }
          } catch (e) {
            actions.setSubmitting(false);
            actions.setTouched({
              userId: false,
              password: false
            })
          } finally {
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
          dirty,
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
                    error={(touched.userId && dirty.userId) || Boolean(errors.userId)}
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
                    error={(touched.password && dirty.password) || Boolean(errors.password)}
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
    </Container>
  )

}

export default Login;