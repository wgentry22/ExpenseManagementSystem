export const PORT = process.env.PORT;
export const CORS_URL = `http://localhost:${PORT}`;
export const API_URL = "http://localhost:8080";
export const chartColors = ["#4287f5", "#f54275", "#42e0f5", "#6ff542", "#f5bc42"];
export const MONTHS = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
export const getExpenseTypeName = expenseType => {
    return EXPENSE_TYPES.filter(type => type.value === expenseType).map(type => type.key).pop();
}
export const getDayOfWeek = day => {
    if (typeof day === 'string') {
        return day.charAt(0) + day.slice(1).toLowerCase();
    } else {
        return null;
    }
}
export const EXPENSE_TYPES = [
    { key: "Food", value: 'FOOD' },
    { key: 'Gas', value: 'GAS' },
    { key: 'Video Games', value: 'VIDEO_GAMES'},
    { key: 'Rent', value: 'RENT'},
    { key: 'Bills', value: 'BILLS'}
]
export const ACCOUNT_TYPES = [
    { key: 'Checking', value: 'CHECKING'},
    { key: 'Savings', value: 'SAVINGS'},
]

export const convertStateNameToAbbreviation = name => {
    return STATES.find(state => state.name === name).abbreviation;
}

export const getNormalizedAccountType = accountType => {
    return ACCOUNT_TYPES.find(type => type.value === accountType).key;
}

export const STATES = [
  {
      "name": "Alabama",
      "abbreviation": "AL"
  },
  {
      "name": "Alaska",
      "abbreviation": "AK"
  },
  {
      "name": "American Samoa",
      "abbreviation": "AS"
  },
  {
      "name": "Arizona",
      "abbreviation": "AZ"
  },
  {
      "name": "Arkansas",
      "abbreviation": "AR"
  },
  {
      "name": "California",
      "abbreviation": "CA"
  },
  {
      "name": "Colorado",
      "abbreviation": "CO"
  },
  {
      "name": "Connecticut",
      "abbreviation": "CT"
  },
  {
      "name": "Delaware",
      "abbreviation": "DE"
  },
  {
      "name": "District Of Columbia",
      "abbreviation": "DC"
  },
  {
      "name": "Federated States Of Micronesia",
      "abbreviation": "FM"
  },
  {
      "name": "Florida",
      "abbreviation": "FL"
  },
  {
      "name": "Georgia",
      "abbreviation": "GA"
  },
  {
      "name": "Guam",
      "abbreviation": "GU"
  },
  {
      "name": "Hawaii",
      "abbreviation": "HI"
  },
  {
      "name": "Idaho",
      "abbreviation": "ID"
  },
  {
      "name": "Illinois",
      "abbreviation": "IL"
  },
  {
      "name": "Indiana",
      "abbreviation": "IN"
  },
  {
      "name": "Iowa",
      "abbreviation": "IA"
  },
  {
      "name": "Kansas",
      "abbreviation": "KS"
  },
  {
      "name": "Kentucky",
      "abbreviation": "KY"
  },
  {
      "name": "Louisiana",
      "abbreviation": "LA"
  },
  {
      "name": "Maine",
      "abbreviation": "ME"
  },
  {
      "name": "Marshall Islands",
      "abbreviation": "MH"
  },
  {
      "name": "Maryland",
      "abbreviation": "MD"
  },
  {
      "name": "Massachusetts",
      "abbreviation": "MA"
  },
  {
      "name": "Michigan",
      "abbreviation": "MI"
  },
  {
      "name": "Minnesota",
      "abbreviation": "MN"
  },
  {
      "name": "Mississippi",
      "abbreviation": "MS"
  },
  {
      "name": "Missouri",
      "abbreviation": "MO"
  },
  {
      "name": "Montana",
      "abbreviation": "MT"
  },
  {
      "name": "Nebraska",
      "abbreviation": "NE"
  },
  {
      "name": "Nevada",
      "abbreviation": "NV"
  },
  {
      "name": "New Hampshire",
      "abbreviation": "NH"
  },
  {
      "name": "New Jersey",
      "abbreviation": "NJ"
  },
  {
      "name": "New Mexico",
      "abbreviation": "NM"
  },
  {
      "name": "New York",
      "abbreviation": "NY"
  },
  {
      "name": "North Carolina",
      "abbreviation": "NC"
  },
  {
      "name": "North Dakota",
      "abbreviation": "ND"
  },
  {
      "name": "Northern Mariana Islands",
      "abbreviation": "MP"
  },
  {
      "name": "Ohio",
      "abbreviation": "OH"
  },
  {
      "name": "Oklahoma",
      "abbreviation": "OK"
  },
  {
      "name": "Oregon",
      "abbreviation": "OR"
  },
  {
      "name": "Palau",
      "abbreviation": "PW"
  },
  {
      "name": "Pennsylvania",
      "abbreviation": "PA"
  },
  {
      "name": "Puerto Rico",
      "abbreviation": "PR"
  },
  {
      "name": "Rhode Island",
      "abbreviation": "RI"
  },
  {
      "name": "South Carolina",
      "abbreviation": "SC"
  },
  {
      "name": "South Dakota",
      "abbreviation": "SD"
  },
  {
      "name": "Tennessee",
      "abbreviation": "TN"
  },
  {
      "name": "Texas",
      "abbreviation": "TX"
  },
  {
      "name": "Utah",
      "abbreviation": "UT"
  },
  {
      "name": "Vermont",
      "abbreviation": "VT"
  },
  {
      "name": "Virgin Islands",
      "abbreviation": "VI"
  },
  {
      "name": "Virginia",
      "abbreviation": "VA"
  },
  {
      "name": "Washington",
      "abbreviation": "WA"
  },
  {
      "name": "West Virginia",
      "abbreviation": "WV"
  },
  {
      "name": "Wisconsin",
      "abbreviation": "WI"
  },
  {
      "name": "Wyoming",
      "abbreviation": "WY"
  }
];