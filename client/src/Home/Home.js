import React, { useState  } from 'react';
import { useUserInfo } from '../hooks/useUserInfo';
import requiresAuthentication from '../hoc/requiresAuthentication';
import { CreateExpense } from '../Expense/CreateExpense';
import { CreateAccount } from '../UserInfo/CreateAccount';
import Navbar from './Navbar';
import { Route, Switch } from 'react-router-dom';
import { Dashboard } from './Dashboard';
import { AdminView } from '../Admin/AdminView';
import { removeAccount } from '../hooks/useAccount';

const Home = props => {
  const [account, setAccount] = useState({});
  const [lastCreatedExpense, setLastCreatedExpense] = useState("");
  const [lastCreatedAccount, setLastCreatedAccount] = useState("");
  const [lastRemovedAccount, setLastRemovedAccount] = useState("");
  const [lastUpdatedAddress, setLastUpdatedAddress] = useState("");
  const [showCreateAccountForm, setShowCreateAccountForm] = useState(false);
  const [date, setDate] = useState(new Date());
  const userInfo = useUserInfo(lastCreatedAccount, lastUpdatedAddress, lastRemovedAccount);

  function handleSignOut() {
    document.cookie='api_token=""; max-age=-1;';
    props.history.push("/");
  }

  function handleCreatedExpense(expenseId) {
    setLastCreatedExpense(expenseId);
  }

  function handleAccountChange(event) {
    const acct = userInfo.accounts.find(a => a.name === event.target.value);
    setAccount(acct);
  }

  function handleAddressUpdate(address) {
    setLastUpdatedAddress(address.street);
  }

  function handleShowCreateAccount() {
    setShowCreateAccountForm(true);
  }

  function handleCloseCreateAccount() {
    setShowCreateAccountForm(false);
  }

  function handleAccountCreate(accountId) {
    setLastCreatedAccount(accountId);
  }

  function handleAccountRemove(accountId) {
    removeAccount(accountId).then(result => {
      if (result.status === 204) {
        setLastRemovedAccount(accountId);
      }
    })
  }

  function handleDateChange(date) {
    setDate(date);
  }

  function showDashboard() {
    props.history.push("/home/dashboard")
  }

  function showProfile() {
    props.history.push("/home/profile")
  }

  if (!Object.keys(account).length && userInfo !== null && userInfo.accounts && userInfo.accounts.length) {
    setAccount(userInfo.accounts[0]);
  }

  if (userInfo && userInfo.name.firstname && Object.keys(account).length) {
    return (
      <div>
        <Navbar 
          accounts={userInfo.accounts.map(account => account.name)}
          onAccountSelect={handleAccountChange}
          title={`${userInfo.name.firstname}'s Expense Dashboard`}
          date={date}
          handleDateChange={handleDateChange}
          handleSignOut={handleSignOut}
          handleOpenCreateAccount={handleShowCreateAccount}
          isCreateAccountOpen={showCreateAccountForm}
          showDashboard={showDashboard}
          showProfile={showProfile}
          {...props}
        />
        <CreateExpense 
          onExpenseCreate={handleCreatedExpense}
        />
        <CreateAccount
          isOpen={showCreateAccountForm}
          onAccountCreate={handleAccountCreate}
          handleClose={handleCloseCreateAccount}
        />
        <Switch>
          <Route exact path="/home/dashboard" render={props => (
              <Dashboard
                expenseId={lastCreatedExpense}
                account={account}
                month={date.getMonth()}
                year={date.getFullYear()}
                {...props}
              />
            )} 
          />
          <Route exact path="/home/profile" render={props => (
              <AdminView
                userInfo={userInfo}
                handleAddressChange={handleAddressUpdate}
                onAccountCreate={handleAccountCreate}
                onAccountRemove={handleAccountRemove}
                {...props}
              />
            )}
          />
        </Switch>
      </div>
    )
  } else {
    return (
      <h1>Loading...</h1>
    )
  }
}

export default requiresAuthentication(Home);