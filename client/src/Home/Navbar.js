import React, { useState } from 'react';
import { makeStyles, AppBar, Toolbar, IconButton, Typography, Button, Drawer, List, ListItem, ListItemText, ListItemIcon, Box, TextField, MenuItem, InputAdornment } from '@material-ui/core';
import { Menu, ChevronLeftOutlined, DateRange, AccountBalance, Dashboard } from '@material-ui/icons';
import { MuiPickersUtilsProvider, DatePicker } from '@material-ui/pickers';
import DateFnsUtils from '@date-io/date-fns';

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    flexGrow: 1,
  },
  list: {
    width: 250
  }
}));

const useNavbarActionsStyles = makeStyles(theme => ({
  account: {
    position: 'relative',
    width: '40%',
    color: 'white'
  },
  appBar: {
    width: '65%'
  },
}))

export const NavbarActions = props => {
  const classes = useNavbarActionsStyles();
  const { accounts, onAccountSelect, isOpen, onDatePickerClose, onDatePickerOpen, date, handleDateChange, handleSignOut } = props;
  const [account, setAccount] = useState(accounts[0]);

  function handleAccountChange(evt) {
    setAccount(evt.target.value);
    onAccountSelect(evt);
  }

  return (
    <Box
      display={'flex'}
      flexDirection={'row'}
      alignItems={'center'}
      justifyContent={'space-between'}
      className={classes.appBar}
    >
      <TextField
        select
        margin={'dense'}
        name="account"
        label={'Account'}
        variant="outlined"
        value={account}
        className={classes.account}
        onChange={e => handleAccountChange(e)}
      >
        {accounts.map(acct => {
          return (
            <MenuItem key={`account-${acct}`} value={acct}>
              {acct}
            </MenuItem>
          )
        })}
      </TextField>
      <MuiPickersUtilsProvider utils={DateFnsUtils}>
        <DatePicker
          inputVariant={'outlined'}
          views={["year", "month"]}
          value={date}
          label={'Month to view'}
          margin={'dense'}
          open={isOpen}
          onOpen={() => onDatePickerOpen()}
          onClose={() => onDatePickerClose()}
          onChange={e => handleDateChange(e)}
          InputProps={{
            endAdornment: <InputAdornment><DateRange /></InputAdornment>
          }}
        />
      </MuiPickersUtilsProvider>
      <Button
        variant="contained"
        color="secondary"
        onClick={() => handleSignOut()}
      >
        Sign Out
      </Button>
    </Box>
  )
}

export const NavbarDrawer = props => {
  const classes = useStyles();
  const { isOpen, handleDrawerClose, handleCreateAccountModal, showDashboard } = props;

  return (
    <Drawer
      open={isOpen}
      anchor="left"
    >
      <div className={classes.list}>
        <List>
          <ListItem
            button 
            key={'close-navbar-drawer'}
            onClick={handleDrawerClose}
          >
            <ListItemText primary={'Close'} />
            <ListItemIcon>
              <ChevronLeftOutlined />
            </ListItemIcon>
          </ListItem>
          <ListItem
            button
            key={'open-dashboard-view'}
            onClick={() => {
              handleDrawerClose();
              showDashboard();
            }}
          >
            <ListItemText primary={'Dashboard'} />
            <ListItemIcon>
              <Dashboard />
            </ListItemIcon>
          </ListItem>
          <ListItem
            button
            key={'open-create-account-modal'}
            onClick={() => {
              handleDrawerClose();
              handleCreateAccountModal(true);
            }}
          >
            <ListItemText primary={'Create Account'} />
            <ListItemIcon>
              <AccountBalance />
            </ListItemIcon>
          </ListItem>
        </List>
      </div>
    </Drawer>
  )
}

const Navbar = props => {
  const classes = useStyles();
  const [isDrawerOpen, setIsDrawerOpen] = useState(false);
  const [isDatePickerOpen, setIsDatePickerOpen] = useState(false);
  const { title, handleOpenCreateAccount, isCreateAccountOpen } = props;

  function handleDrawerClose() {
    setIsDrawerOpen(false);
  }

  function handleDrawerOpen() {
    setIsDrawerOpen(true);  
  }

  function handleDatePickerClose() {
    setIsDatePickerOpen(false);
  }

  function handleDatePickerOpen() {
    setIsDatePickerOpen(true);
  }

  return (
    <div className={classes.root}>
      <AppBar position="relative">
        <Toolbar>
          <IconButton 
            edge="start"
            className={classes.menuButton}
            onClick={handleDrawerOpen}
          >
            <Menu />
          </IconButton>
          <Typography 
            className={classes.title} 
            variant="h6"
          >
            {title}
          </Typography>
          <NavbarActions 
            isOpen={isDatePickerOpen}
            onDatePickerSelect={handleDatePickerOpen}
            onDatePickerOpen={handleDatePickerOpen}
            onDatePickerClose={handleDatePickerClose}
            {...props}
          />
        </Toolbar>
      </AppBar>
      <NavbarDrawer 
        isOpen={isDrawerOpen}
        handleDrawerClose={handleDrawerClose}
        isCreateAccountOpen={isCreateAccountOpen}
        handleCreateAccountModal={handleOpenCreateAccount}
        showDashboard={props.showDashboard}
      />
    </div>
  )
}

export default Navbar;