import React from 'react';
import { Redirect } from 'react-router';

function requiresAuthentication(Component) {

  class RequiresAuthentication extends React.Component {

    constructor(props) {
      super(props);
      this.state = {
        authenticated: Boolean(document.cookie && document.cookie.startsWith('api_token'))
      }
    }

    render() {
      if (this.state.authenticated) {
        const {forwardedRef, ...rest} = this.props;
        return <Component ref={forwardedRef} {...rest} />;
      } else {
        return <Redirect to="/error" />;
      }
    }

  }
  return React.forwardRef((props, ref) => {
    return <RequiresAuthentication {...props} forwardedRef={ref} />
  });
}

export default requiresAuthentication;