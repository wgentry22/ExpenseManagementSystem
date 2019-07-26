import React, { useState  } from 'react';
import { useUserInfo } from '../hooks/useUserInfo';
import { makeStyles } from '@material-ui/core';
import requiresAuthentication from '../hoc/requiresAuthentication';
import { CreateExpense } from '../Expense/CreateExpense';
import { CreateAccount } from '../UserInfo/CreateAccount';
import Navbar from './Navbar';
import { HomeContainer } from '../components/view/HomeContainer';

const Home = props => {
  const [account, setAccount] = useState({});
  const [lastCreatedExpense, setLastCreatedExpense] = useState("");
  const [lastCreatedAccount, setLastCreatedAccount] = useState("");
  const [showCreateAccountForm, setShowCreateAccountForm] = useState(false);
  const [date, setDate] = useState(new Date());
  const [view, setView] = useState("dashboard");
  const userInfo = useUserInfo(lastCreatedAccount);

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

  function handleShowCreateAccount() {
    setShowCreateAccountForm(true);
  }

  function handleCloseCreateAccount() {
    setShowCreateAccountForm(false);
  }

  function handleAccountCreate(accountId) {
    setLastCreatedAccount(accountId);
  }

  function handleDateChange(date) {
    setDate(date);
  }

  function showDashboard() {
    setView('dashboard');
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
        />
        <HomeContainer
          view={view}
          expenseId={lastCreatedExpense}
          account={account}
          month={date.getMonth()}
          year={date.getFullYear()}
        />
        <CreateExpense 
          onExpenseCreate={handleCreatedExpense}
        />
        <CreateAccount
          isOpen={showCreateAccountForm}
          onAccountCreate={handleAccountCreate}
          handleClose={handleCloseCreateAccount}
        />
      </div>
    )
  } else {
    return (
      <h1>Loading...</h1>
    )
  }
}

export default requiresAuthentication(Home);