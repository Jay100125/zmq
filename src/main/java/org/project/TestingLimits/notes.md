- if buffer < 8 kb and total data > 8kb then buffer will be set to 8 kb
- buffer < 8 kb and total data < 8 kb then buffer will be equal to total data
- buffer >= 8 kb and total data < buffer size then buffer will be equal to total data
- buffer >= 8 kb and total data >= 8 kb then OS set buffer according to its internal mechanism

- Total data = sendMore() + send() combine
- data is only sent when we encounter send() method.
