import { useState, useEffect } from 'react';
import { API_URL } from '../constants';

const getUserInfo = async () => {
  const result = await fetch(`${API_URL}/api/v1/info`, {
    mode: 'cors',
    credentials: 'include',
    headers: {
      "Accept": "application/json;charset=UTF-8",
      'Access-Control-Allow-Origin': 'http://localhost:3000'
    }
  });
  return result;
}

export const useUserInfo = () => {

  const [userInfo, setUserInfo] = useState(null);

  useEffect(() => {
    (async () => {
      let info;
      try {
        const response = await getUserInfo();
        info = await response.json();
      } catch (e) {

      }
      setUserInfo(info);
    })();
  }, []);
  return userInfo;
}