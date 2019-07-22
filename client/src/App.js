import React from 'react';
import { BrowserRouter, Route, Switch, Redirect } from 'react-router-dom';
import Login from './Login/Login';
import Registration from './Registration/Registration';
import Home from './Home/Home';
import { emsTheme } from './themes/emsTheme';
import { ThemeProvider } from '@material-ui/styles';

const RedirectToLogin = props => {
  const { hasError } = props;
  return <Redirect to={`/login${hasError ? "?error" : ""}`} />
}

const App = props => {

  return (
    <ThemeProvider theme={emsTheme}>
      <BrowserRouter>
        <Switch>
          <Route exact path="/login" render={routerProps => (<Login {...routerProps} hasError={false}/>)} />
          <Route exact path="/error" render={routerProps => (<Login {...routerProps} hasError={true}/>)} />
          <Route path="/register" component={Registration} />
          <Route path="/home" component={Home} />
          <Route component={RedirectToLogin} />
        </Switch>
      </BrowserRouter>
    </ThemeProvider>
  );
}

export default App;
