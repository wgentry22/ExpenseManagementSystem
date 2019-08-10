import React from 'react';

import { UpdateAddress } from '../UserInfo/UpdateAddress';
import { CircularProgress, Box } from '@material-ui/core';
import { AccountInfo } from '../Account/AccountInfo';

export const AdminView = props => {
  const { userInfo, handleAddressChange, onAccountCreate, onAccountRemove } = props;

  if (userInfo && userInfo.address && userInfo.accounts) {
    return (
      <Box
        display={'flex'}
        flexDirection={'column'}
        alignContent={'space-between'}
        width={'100%'}
      >
        <Box
          display={'flex'}
          flexDirection={'row'}
          justifyContent={'space-between'}
          alignItems={'baseline'}
          width={"100%"}
        >
          <UpdateAddress 
            address={userInfo.address} 
            handleAddressChange={handleAddressChange}
          />
          <AccountInfo 
            accounts={userInfo.accounts} 
            onAccountCreate={onAccountCreate}
            onAccountRemove={onAccountRemove}
          />
        </Box>
      </Box>
    );
  } else {
    return <CircularProgress />
  }
}