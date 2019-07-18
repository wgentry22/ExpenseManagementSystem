import React from 'react';
import { Redirect } from 'react-router';

function requiresAuthentication(Component) {
  return class extends React.Component {
    constructor(props) {
      super(props);
      this.state = {
        authenticated: Boolean(document.cookie && document.cookie.startsWith('api_token'))
      }
    }

    render() {
      if (this.state.authenticated) {
        return <Component userInfo={this.userInfo} {...this.props} />
      } else {
        return <Redirect to="/error" />
      }
    }
  }
}

export default requiresAuthentication;