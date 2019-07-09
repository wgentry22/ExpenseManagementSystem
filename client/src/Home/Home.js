import React, { useState  } from 'react';
import { useUserInfo } from '../hooks/useUserInfo';
import { AppBar, IconButton, Toolbar, Typography, Button, makeStyles, Drawer } from '@material-ui/core';
import Menu from '@material-ui/icons/Menu';
import { NavbarMenuList } from './NavbarMenuList';
import requiresAuthentication from '../hooks/requiresAuthentication';

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1,
  },
  title: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
    color: 'white'
  }
}))

const Home = props => {
  const classes = useStyles();
  const userInfo = useUserInfo();
  const [menuOpen, setMenuOpen] = useState(false);

  function toggleMenu(event) {
    if (event.target.classList.contains("MuiListItemText-primary")) {
      return false;
    }
    if (menuOpen) {
      setMenuOpen(false);
    } else {
      setMenuOpen(true);
    }
  }

  function handleSignOut(event) {
    document.cookie='api_token=""; max-age=-1;';
    props.history.push("/");
  }

  if (userInfo && userInfo.name.firstname) {
    return (
      <div className={classes.root} >
        <AppBar position="static">
          <Toolbar>
            <IconButton 
              edge="start"
              className={classes.menuButton} 
              onClick={toggleMenu} 
            >
              <Menu />
              <Drawer anchor={"left"} open={menuOpen}>
                <NavbarMenuList handleMenuClose={toggleMenu}/>
              </Drawer>
            </IconButton>
            <Typography variant="h6" className={classes.title}>
              {userInfo.name.firstname}'s Expense Dashboard
            </Typography>
            <Button 
              variant="contained" 
              color="secondary"
              type="submit"
              onClick={e => {
                e.preventDefault();
                handleSignOut(e);
              }}
            >
              Sign Out
            </Button>
          </Toolbar>
        </AppBar>
      </div>
    )
  } else {
    return (
      <h1>Loading...</h1>
    )
  }
}

export default requiresAuthentication(Home);