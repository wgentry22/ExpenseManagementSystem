import React from 'react';
import { Card, CardHeader, CardContent, CardActions } from '@material-ui/core';

export const FlexCard = props => {
  const { title, subtitle, component, actions } = props;

  return (
    <Card>
      <CardHeader title={title || '' } subtitle={subtitle || ''} />
      <CardContent
        component={component}
      />
      <CardActions
        component={actions}
      />
    </Card>
  )

}