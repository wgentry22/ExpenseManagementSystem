import React, { useState } from 'react';
import { MuiThemeProvider, Box, makeStyles, ExpansionPanel, ExpansionPanelSummary, Typography, Card, CardHeader, CardContent, ExpansionPanelDetails, Table, TableHead, TableBody, TableRow, TableCell, Button, ButtonGroup, Dialog, DialogContent, DialogActions, DialogTitle } from '@material-ui/core';
import { ExpandMore, CancelOutlined, RefreshOutlined } from '@material-ui/icons';
import { CreateAccount } from '../UserInfo/CreateAccount';
import { getNormalizedAccountType } from '../constants'
import { emsTertiaryTheme } from '../themes/emsTheme'

const CreateAccountButton = props => {
  const { openCreateAccountForm } = props;
  return (
    <Button
      variant="contained"
      size="small"
      color="secondary"
      style={{marginTop: "5%"}}
      onClick={() => openCreateAccountForm()}
    >
      Create Account
    </Button>
  )
}

const AccountActions = props => {
  const [isPromptOpen, setIsPromptOpen] = useState(false);
  const { account, onAccountRemove } = props;

  function handleDeleteAction(evt) {
    setIsPromptOpen(true);
  }

  function handleClosePrompt() {
    setIsPromptOpen(false);
  }

  function handleAccountRemove(accountName) {
    handleClosePrompt();
    onAccountRemove(accountName);
  }

  return (
    <ButtonGroup size="small" component={"div"}>
      <Button>
        <RefreshOutlined />
      </Button>
      <Button
        onClick={() => handleDeleteAction(account)}
      >
        <CancelOutlined/>
      </Button>
      <PromptAccountAction 
        name={account.name}
        isOpen={isPromptOpen}
        handleAccountRemove={handleAccountRemove}
        handleClosePrompt={handleClosePrompt}
      />
    </ButtonGroup>
  )
}

const PromptAccountAction = props => {
  const { name, isOpen, handleAccountRemove, handleClosePrompt } = props;
  return (
    <Dialog
      open={isOpen}
      onClose={handleAccountRemove}
    >
      <DialogTitle>Delete Account Confirmation</DialogTitle>
      <DialogContent>
        <Typography>Are you sure you want to delete {name}?</Typography>
      </DialogContent>
      <DialogActions>
        <MuiThemeProvider theme={emsTertiaryTheme}>
          <Button
            variant="contained"
            color="primary"
            onClick={() => handleAccountRemove(name)}
            fullWidth
          >
            Delete {name}
          </Button>
          <Button
            variant="contained"
            color="secondary"
            onClick={() => handleClosePrompt()}
          >
            Cancel
          </Button>
        </MuiThemeProvider>
      </DialogActions>
    </Dialog>
  )
}

export const AccountInfo = props => {
  const { accounts, onAccountCreate, onAccountRemove } = props;
  const [isCreateAccountFormOpen, setIsCreateAccountFormOpen] = useState(false);

  function openCreateAccountForm() {
    setIsCreateAccountFormOpen(true);
  }

  function closeCreateAccountForm() {
    setIsCreateAccountFormOpen(false);
  }

  return (
    <Card>
      <CardHeader 
        title={"Your Accounts"} 
        action={<CreateAccountButton openCreateAccountForm={openCreateAccountForm} />}
      />
      <CardContent>
        <AccountPanels accounts={accounts} onAccountRemove={onAccountRemove} />
      </CardContent>
      <CreateAccount
        isOpen={isCreateAccountFormOpen}
        handleClose={closeCreateAccountForm}
        onAccountCreate={onAccountCreate}
      />
    </Card>
  )
}


const currency = new Intl.NumberFormat("en-US", {
  style: 'currency',
  currency: 'USD',
  currencyDisplay: 'symbol',
  maximumSignificantDigits: '1',
})

const useStyles = makeStyles(theme => ({
  secondaryText: {
    color: theme.palette.text.secondary,
  }
}));

const AccountPanels = props => {
  const { accounts, onAccountRemove } = props;
  const classes = useStyles();
  const panels = accounts.map(account => {
    return (
      <ExpansionPanel
        key={`account-info-${account.name}`}
      >
        <ExpansionPanelSummary
          expandIcon={<ExpandMore />}
        >
          <Box
            display="flex"
            flexDirection="column"
          >
            <Typography>{account.name}</Typography>
            <Typography variant="subtitle2" className={classes.secondaryText}>{getNormalizedAccountType(account.type)}</Typography>
          </Box>
        </ExpansionPanelSummary>
        <ExpansionPanelDetails>
          <Table size={'small'}>
            <TableHead>
              <TableRow>
                <TableCell align="center">Account Balance</TableCell>
                <TableCell align="center">Monthly Deposits</TableCell>
                <TableCell align="center">Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              <TableRow>
                <TableCell align="center">{currency.format(account.balance)}</TableCell>
                <TableCell align="center">{currency.format(account.monthlyDeposits)}</TableCell>
                <TableCell align="center"><AccountActions account={account} onAccountRemove={onAccountRemove} /></TableCell>
              </TableRow>
            </TableBody>
          </Table>
        </ExpansionPanelDetails>
      </ExpansionPanel>
    )
  })

  return <div>{panels}</div>
}