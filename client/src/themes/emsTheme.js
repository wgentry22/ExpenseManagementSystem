import { createMuiTheme } from '@material-ui/core/styles';
import red from '@material-ui/core/colors/red';

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
    error: red,
  }
})