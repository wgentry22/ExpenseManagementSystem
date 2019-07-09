import React from 'react';
import { ListItem, ListItemText, List, IconButton } from '@material-ui/core';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';

export const NavbarMenuList = props => {

  const list = [
    (<IconButton onClick={e => props.handleMenuClose(e)}>
      <ChevronLeftIcon />
    </IconButton>)
  ];
  for (let i = 1; i < 6; i++) {
    list.push(`Item ${i}`);
  }

  const listItems = list.map(item => {
    return (
      <ListItem
        button
        key={item}
      >
        <ListItemText primary={item} />
      </ListItem>
    )
  });

  return (
    <List>
      {listItems}
    </List>
  )

}