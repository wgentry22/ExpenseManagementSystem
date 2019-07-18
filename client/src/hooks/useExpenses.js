import { useState, useEffect } from 'react';
import { API_URL, CORS_URL } from '../constants';

const getAllExpenses = (limit, page) => {
  return fetch(`${API_URL}/api/v1/expense?limit=${limit}&page=${page}`, {
    mode: 'cors',
    credentials: 'include',
    headers: {
      "Accept": "application/json;charset=UTF-8",
      'Access-Control-Allow-Origin': CORS_URL
    }
  });
}

const getAllExpensesInMonth = (month, year) => {
  let url = `${API_URL}/api/v1/month/expense`;
  if (typeof month === 'number' && typeof year === 'number') {
    url += `?month=${month}&year=${year}`;
  }
  return fetch(url, {
    mode: 'cors',
    credentials: 'include',
    headers: {
      "Accept": "application/json;charset=UTF-8",
      'Access-Control-Allow-Origin': CORS_URL
    }
  });
}

const getAllExpensesInMonthByDay = (month, year) => {
  let url = `${API_URL}/api/v1/month/expense/days`;
  if (typeof month === 'number' && typeof year === 'number') {
    url += `?month=${month}&year=${year}`;
  }
  return fetch(url, {
    mode: 'cors',
    credentials: 'include',
    headers: {
      "Accept": "application/json;charset=UTF-8",
      'Access-Control-Allow-Origin': CORS_URL
    }
  });
}

export const useExpenses = (createdExpenseId, limit, page) => {
  const [expenses, setExpenses] = useState(null);

  useEffect(() => {
    (async () => {
      const response = await getAllExpenses(limit, page);
      if (response.ok) {
        const data = await response.json();
        setExpenses(data);
      }
    })()
    
  }, [createdExpenseId, limit, page]);
  return expenses;
}


export const useExpensesInMonth = (currentExpenseId, month, year) => {
  const [expenses, setExpenses] = useState({});

  useEffect(() => {
    (async () => {
      const response = await getAllExpensesInMonth(month + 1, year);
      if (response.ok) {
        const data = await response.json();
        setExpenses(data);
      }
    })()
    
  }, [currentExpenseId, month, year]);
  return expenses;
}

export const useExpensesInMonthByDay = (currentExpenseId, month, year) => {
  const [expenses, setExpenses] = useState({});

  useEffect(() => {
    (async () => {
      const response = await getAllExpensesInMonthByDay(month + 1, year);
      if (response.ok) {
        const data = await response.json();
        setExpenses(data);
      }
    })()
  }, [currentExpenseId, month, year]);
  return expenses;
}