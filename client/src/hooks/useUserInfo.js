import { useState, useEffect } from 'react';
import { API_URL, CORS_URL } from '../constants';

const getUserInfo = async () => {
  // const result = await fetch(`${API_URL}/api/v1/info`, {
  //   mode: 'cors',
  //   credentials: 'include',
  //   headers: {
  //     "Accept": "application/json;charset=UTF-8",
  //     'Access-Control-Allow-Origin': CORS_URL
  //   }
  // });
  // return result;
  return fetch(`${API_URL}/api/v1/info`, {
    mode: 'cors',
    credentials: 'include',
    headers: {
      "Accept": "application/json;charset=UTF-8",
      'Access-Control-Allow-Origin': CORS_URL
    }
  });
}

export const useUserInfo = lastCreatedAccount => {
  const [userInfo, setUserInfo] = useState(null);

  useEffect(() => {
    getUserInfo().then(result => {
      if (result.ok) {
        result.json().then(data => {
          setUserInfo(data);
        })
      }
    })
  }, [lastCreatedAccount]);
  return userInfo;
}