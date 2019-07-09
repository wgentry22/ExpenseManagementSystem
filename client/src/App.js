import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Login from './Login/Login';
import Registration from './Registration/Registration';
import Home from './Home/Home';

const App = props => {

  return (
    <BrowserRouter>
      <Switch>
        <Route exact path="/" render={routerProps => (<Login {...routerProps} hasError={false}/>)} />
        <Route exact path="/error" render={routerProps => (<Login {...routerProps} hasError={true}/>)} />
        <Route path="/register" component={Registration} />
        <Route path="/home" component={Home} />
      </Switch>
    </BrowserRouter>
  );
}

export default App;
