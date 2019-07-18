import { useState, useEffect } from 'react';
import { API_URL, CORS_URL } from '../constants';

const getUserInfo = async () => {
  const result = await fetch(`${API_URL}/api/v1/info`, {
    mode: 'cors',
    credentials: 'include',
    headers: {
      "Accept": "application/json;charset=UTF-8",
      'Access-Control-Allow-Origin': CORS_URL
    }
  });
  return result;
}

export const useUserInfo = () => {

  const [userInfo, setUserInfo] = useState(null);

  useEffect(() => {
    (async () => {
      const response = await getUserInfo();
      if (response.ok) {
        const data = await response.json();
        setUserInfo(data);
        return;
      }
    })();
  }, []);
  return userInfo;
}