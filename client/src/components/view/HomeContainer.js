import React from 'react';
import { Dashboard } from '../../Home/Dashboard';

export const HomeContainer = props => {
  const { view } = props;

  if (view === 'dashboard') {
    return (
      <Dashboard {...props} />
    )
  } else {
    return <div>No View Selected</div>
  }
}