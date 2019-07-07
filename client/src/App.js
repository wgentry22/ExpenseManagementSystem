import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Login from './Login/Login';
import Registration from './Registration/Registration';

function App() {
  return (
    <BrowserRouter>
      <Switch>
        <Route exact path="/" component={Login} />
        <Route path="/register" component={Registration} />
      </Switch>
    </BrowserRouter>
  );
}

export default App;
