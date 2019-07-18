import { useState, useEffect } from 'react';
import { API_URL, CORS_URL } from '../constants';

const getAccount = accountId => {
  return fetch(`${API_URL}/api/v1/account/${accountId}`, {
    mode: 'cors',
    credentials: 'include',
    headers: {
      "Accept": "application/json;charset=UTF-8",
      'Access-Control-Allow-Origin': CORS_URL
    }
  })
}

export const useAccount = accountId => {
  const [account, setAccount] = useState({});
  useEffect(() => {
    (async () => {
      const response = await getAccount(accountId);
      if (response.ok) {
        const data = await response.json();
        setAccount(data);
      }
    })();
  }, [accountId]);
  return account;
}