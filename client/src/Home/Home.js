import React, { useState  } from 'react';
import { useUserInfo } from '../hooks/useUserInfo';
import { AppBar, IconButton, Toolbar, Typography, Button, makeStyles, Drawer, Fab, Box, TextField, MenuItem, InputAdornment } from '@material-ui/core';
import { Menu as MenuIcon } from '@material-ui/icons';
import { NavbarMenuList } from './NavbarMenuList';
import requiresAuthentication from '../hoc/requiresAuthentication';
import { Dashboard } from './Dashboard';
import Add from '@material-ui/icons/Add';
import { CreateExpense } from '../Expense/CreateExpense';
import { DateRange } from '@material-ui/icons';
import { MuiPickersUtilsProvider, DatePicker } from '@material-ui/pickers';
import DateFnsUtils from '@date-io/date-fns';

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
  },
  menu: {
    width: 'auto'
  },
  fabContainer: {
    position: 'fixed',
    marginTop: theme.spacing(15),
    marginLeft: '50%',
    marginRight: '50%',
    bottom: 0,
    top: 'auto',
  },
  account: {
    position: 'relative',
    width: '40%',
    color: 'white'
  },
  appBar: {
    width: '65%'
  }
}))

const Home = props => {
  const classes = useStyles();
  const userInfo = useUserInfo();
  const [menuOpen, setMenuOpen] = useState(false);
  const [accountName, setAccountName] = useState("");
  const [lastCreatedExpense, setLastCreatedExpense] = useState("");
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [date, setDate] = useState(new Date());
  const [isOpen, setIsOpen] = useState(false);

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

  function handleFabClick() {
    setIsModalOpen(true);
  }

  function handleModalClose() {
    setIsModalOpen(false);
  }

  function handleCreatedExpense(expenseId) {
    setLastCreatedExpense(expenseId);
  }

  function handleAccountChange(event) {
    setAccountName(event.target.value);
  }

  if (userInfo && userInfo.name.firstname && userInfo.accounts && userInfo.accounts.length) {
    if (!Boolean(accountName)) {
      setAccountName(userInfo.accounts[0].name);
    }
    const accounts = userInfo.accounts.map(account => {
      return (
        <MenuItem key={`account-${account.name}`} value={account.name}>
          {account.name}
        </MenuItem>
      )
    })
    return (
      <div className={classes.root} >
        <AppBar position="relative">
          <Toolbar>
            <IconButton 
              edge="start"
              className={classes.menuButton} 
              onClick={toggleMenu} 
            >
              <MenuIcon />
              <Drawer anchor={"left"} open={menuOpen}>
                <div className={classes.menu}>
                  <NavbarMenuList handleMenuClose={toggleMenu}/>
                </div>
              </Drawer>
            </IconButton>
            <Typography variant="h6" className={classes.title}>
              {userInfo.name.firstname}'s Expense Dashboard
            </Typography>
            <Box
              display={'flex'}
              flexDirection={'row'}
              alignItems={'center'}
              justifyContent={'space-between'}
              className={classes.appBar}
            >
              <TextField
                select
                name="account"
                label={`Account`}
                variant="outlined"
                value={accountName}
                onChange={e => handleAccountChange(e)}
                className={classes.account}
                margin={'dense'}
              >
                {accounts}
              </TextField>
              <MuiPickersUtilsProvider 
                utils={DateFnsUtils}
              >
                <DatePicker 
                  inputVariant="outlined"
                  views={['year', 'month']}
                  value={date}
                  format="MMMM yyyy"
                  label={'Month to view'}
                  margin={'dense'}
                  open={isOpen}
                  onOpen={() => setIsOpen(true)}
                  onClose={() => setIsOpen(false)}
                  onChange={e => {
                    setDate(e);
                  }}
                  InputProps={{
                    endAdornment: <InputAdornment><DateRange /></InputAdornment>
                  }}
                />
              </MuiPickersUtilsProvider>
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
            </Box>
          </Toolbar>
        </AppBar>
        <Dashboard 
          expenseId={lastCreatedExpense}
          accountName={userInfo.accounts[0].name}
          month={date.getMonth()}
          year={date.getFullYear()}
        />
        <Box
          display={'flex'}
          flexDirection={'column'}
          justifyContent={'center'}
          alignItems={'center'}
          className={classes.fabContainer}
        >
          <Fab 
            color="secondary"
            onClick={() => handleFabClick()}
          >
              <Add />
          </Fab>
          <CreateExpense 
            isOpen={isModalOpen}
            handleClose={handleModalClose}
            onExpenseCreate={handleCreatedExpense}
          />
        </Box>
      </div>
    )
  } else {
    return (
      <h1>Loading...</h1>
    )
  }
}

export default requiresAuthentication(Home);