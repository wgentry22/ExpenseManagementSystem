import { createMuiTheme } from '@material-ui/core/styles';
import red from '@material-ui/core/colors/red';
import { green, yellow } from '@material-ui/core/colors';

export const emsTheme = createMuiTheme({
  palette: {
    type: 'dark',
    primary: {
      light: '#5eb8ff',
      main: '#0288d1',
      dark: '#005b9f',
    },
    secondary: {
      light: '#ffff6b',
      main: '#fdd835',
      dark: '#c6a700'
    },
    error: {
      main: red[500]
    },
  }
});

export const emsSecondaryTheme = createMuiTheme({
  palette: {
    primary: {
      main: green[500]
    },
    secondary: {
      main: yellow[500]
    },
    error: {
      main: red[500]
    },
  }, 
})

export const emsTertiaryTheme = createMuiTheme({
  palette: {
    primary: {
      main: red[500]
    },
    secondary: {
      main: yellow[500]
    },
  }, 
})