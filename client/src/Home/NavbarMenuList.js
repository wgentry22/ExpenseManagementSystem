import React from 'react';
import { ListItem, ListItemText, List, ListItemIcon, makeStyles } from '@material-ui/core';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ViewModuleRounded from '@material-ui/icons/ViewModuleRounded';

const useStyles = makeStyles(theme => ({
  root: {
    backgroundColor: theme.palette.background.paper,
    minWidth: 200,
    display: 'flex',
    flexDirection: 'column',
  },
  li: {
    display: 'flex',
    flexDirection: 'row',
    flexGrow: 1,
    alignItems: 'center',
    justifyContent: 'space-between'
  },
  list: {
    display: 'flex',
    flexDirection: 'column',
    justifyItems: 'space-between',
  }
}));

export const NavbarMenuList = props => { 
  const classes = useStyles();
  let index = 0;

  const list = [
    (
      <div className={classes.li}>
        <ListItemText primary={'Close'} />
        <ListItemIcon><ChevronLeftIcon /></ListItemIcon>
      </div>
    ),
    (
      <div className={classes.li}>
        <ListItemText primary={'Dashboard'} />
        <ListItemIcon><ViewModuleRounded /></ListItemIcon>
      </div>
    )
  ];

  const listItems = list.map(item => {
    return (
      <ListItem
        button
        divider
        key={`menu-list-item-${++index}`}
      >
        {item}
      </ListItem>
    )
  });

  return (
    <List className={classes.root}>
      {listItems}
    </List>
  )

}