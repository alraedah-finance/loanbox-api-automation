@loanBoxRegression @regression
Feature: Loan box automation

  Background:
    Given header Authorization = 'Bearer ' + loanBox_token


  @loanBox
  Scenario Outline: Validate whether find companies call is getting 401 error status code when passing invalid token
    And url baseUrlLoanBox + '/api/integration/v1/companies'
    Given header Authorization = 'Bearer ' + 'eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJzcWRoRldMS0dEUDJXV1NrdnlQOFZxUXFWV0FBbjZuY3BJckl5LUlyUTBFIn0.eyJleHAiOjE3MjQ2NjA0MjMsImlhdCI6MTcyNDY1ODYyMywianRpIjoiZjhiM2FkOTAtYjBlMy00NGE4LWE2OTktMWQ5YWEyYzllOWFlIiwiaXNzIjoiaHR0cHM6Ly91YXQtYXV0aC1sb2FuYm94LmFscmFlZGFoLmRldi9yZWFsbXMvSW50ZWdyYXRpb24iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiMmJmM2EzNWItOGY1MS00NTQxLTliMWYtZTMwMzlkNTcyZWVkIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiY29tcGFueS1pbnRlZ3JhdGlvbiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cHM6Ly91YXQtYm8tbG9hbmJveC5hbHJhZWRhaC5kZXYiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtaW50ZWdyYXRpb24iLCJvZmZsaW5lX2FjY2VzcyIsIkludGVncmF0aW9uQ2xpZW50IiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJjbGllbnRIb3N0IjoiMTAuMjQ0LjEuMCIsInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UtYWNjb3VudC1jb21wYW55LWludGVncmF0aW9uIiwiY2xpZW50QWRkcmVzcyI6IjEwLjI0NC4xLjAiLCJjbGllbnRfaWQiOiJjb21wYW55LWludGVncmF0aW9uIn0.pwHPmxXwCiushKelFmRf5p5zKDOnUXUqC2Dc6xHDRopLxe9QqZwsbJ89djMapE-q-Q05CICnGHN0AuywruTYDYuVYMYdG7Lpwu02ZW92ReUcJZqBSqVjXvmOBRkiu-Mn90kkiGB-lBRM-IBgg5JCtVmVfNLLENkeEe9b--QExkYzj7ZTDPBiTj5CqDUiKM-7RipHFmtMOnzaxr9T7woE-CLBqFOXUJ6togMS8Y-deS22HjL2XtUXfIBb1vsep7sdP7cP2YuP2zH5vdI6u67f_S6U2ajUH4k-rag9LJcdwcdWP12tTQ4lLrJWK4vaC_zrqRgbynnyZkuk49BHPHFDw'
    And param idDocumentNumber = idDocumentNumber
    And param page = page
    And param size = size
    And method get
    Then status 401
    * print response

    Examples:
      | idDocumentNumber | page | size |
      | 1102506886       | 0    | 1    |


  @loanBox
  Scenario Outline: Validate whether find companies call is getting 400 status code when passing more than 1 query
    And url baseUrlLoanBox + '/api/integration/v1/companies'
    And param idDocumentNumber = idDocumentNumber
    And param phone = phone
    And method get
    Then status 400
    * print response

    Examples:
      | idDocumentNumber |     phone         |
      | 1102506886       |    966542266943   |

  @loanBox
  Scenario Outline: Validate whether find companies call is getting 404 status code when passing invalid Commercial Registry Number
    And url baseUrlLoanBox + '/api/integration/v1/companies/'+commercialRegistryNumber+''
    And method get
    Then status 404
    * print response

    Examples:
      | commercialRegistryNumber |
      | 4046100000               |


  @loanBox
  Scenario Outline: Validate whether find companies call is getting 401 status code when passing invalid token
    And url baseUrlLoanBox + '/api/integration/v1/companies/'+commercialRegistryNumber+''
    Given header Authorization = 'Bearer ' + 'eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJzcWRoRldMS0dEUDJXV1NrdnlQOFZxUXFWV0FBbjZuY3BJckl5LUlyUTBFIn0.eyJleHAiOjE3MjQ2NjA0MjMsImlhdCI6MTcyNDY1ODYyMywianRpIjoiZjhiM2FkOTAtYjBlMy00NGE4LWE2OTktMWQ5YWEyYzllOWFlIiwiaXNzIjoiaHR0cHM6Ly91YXQtYXV0aC1sb2FuYm94LmFscmFlZGFoLmRldi9yZWFsbXMvSW50ZWdyYXRpb24iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiMmJmM2EzNWItOGY1MS00NTQxLTliMWYtZTMwMzlkNTcyZWVkIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiY29tcGFueS1pbnRlZ3JhdGlvbiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cHM6Ly91YXQtYm8tbG9hbmJveC5hbHJhZWRhaC5kZXYiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtaW50ZWdyYXRpb24iLCJvZmZsaW5lX2FjY2VzcyIsIkludGVncmF0aW9uQ2xpZW50IiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJjbGllbnRIb3N0IjoiMTAuMjQ0LjEuMCIsInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UtYWNjb3VudC1jb21wYW55LWludGVncmF0aW9uIiwiY2xpZW50QWRkcmVzcyI6IjEwLjI0NC4xLjAiLCJjbGllbnRfaWQiOiJjb21wYW55LWludGVncmF0aW9uIn0.pwHPmxXwCiushKelFmRf5p5zKDOnUXUqC2Dc6xHDRopLxe9QqZwsbJ89djMapE-q-Q05CICnGHN0AuywruTYDYuVYMYdG7Lpwu02ZW92ReUcJZqBSqVjXvmOBRkiu-Mn90kkiGB-lBRM-IBgg5JCtVmVfNLLENkeEe9b--QExkYzj7ZTDPBiTj5CqDUiKM-7RipHFmtMOnzaxr9T7woE-CLBqFOXUJ6togMS8Y-deS22HjL2XtUXfIBb1vsep7sdP7cP2YuP2zH5vdI6u67f_S6U2ajUH4k-rag9LJcdwcdWP12tTQ4lLrJWK4vaC_zrqRgbynnyZkuk49BHPHFDw'
    And method get
    Then status 401
    * print response

    Examples:
      | commercialRegistryNumber |
      | 4046100174               |

  @loanBox
  Scenario Outline: Validate whether find contacts call is getting 401 status code when passing invalid token
    And url baseUrlLoanBox + '/api/integration/v1/contacts'
    Given header Authorization = 'Bearer ' + 'eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJzcWRoRldMS0dEUDJXV1NrdnlQOFZxUXFWV0FBbjZuY3BJckl5LUlyUTBFIn0.eyJleHAiOjE3MjQ2NjA0MjMsImlhdCI6MTcyNDY1ODYyMywianRpIjoiZjhiM2FkOTAtYjBlMy00NGE4LWE2OTktMWQ5YWEyYzllOWFlIiwiaXNzIjoiaHR0cHM6Ly91YXQtYXV0aC1sb2FuYm94LmFscmFlZGFoLmRldi9yZWFsbXMvSW50ZWdyYXRpb24iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiMmJmM2EzNWItOGY1MS00NTQxLTliMWYtZTMwMzlkNTcyZWVkIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiY29tcGFueS1pbnRlZ3JhdGlvbiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cHM6Ly91YXQtYm8tbG9hbmJveC5hbHJhZWRhaC5kZXYiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtaW50ZWdyYXRpb24iLCJvZmZsaW5lX2FjY2VzcyIsIkludGVncmF0aW9uQ2xpZW50IiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJjbGllbnRIb3N0IjoiMTAuMjQ0LjEuMCIsInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UtYWNjb3VudC1jb21wYW55LWludGVncmF0aW9uIiwiY2xpZW50QWRkcmVzcyI6IjEwLjI0NC4xLjAiLCJjbGllbnRfaWQiOiJjb21wYW55LWludGVncmF0aW9uIn0.pwHPmxXwCiushKelFmRf5p5zKDOnUXUqC2Dc6xHDRopLxe9QqZwsbJ89djMapE-q-Q05CICnGHN0AuywruTYDYuVYMYdG7Lpwu02ZW92ReUcJZqBSqVjXvmOBRkiu-Mn90kkiGB-lBRM-IBgg5JCtVmVfNLLENkeEe9b--QExkYzj7ZTDPBiTj5CqDUiKM-7RipHFmtMOnzaxr9T7woE-CLBqFOXUJ6togMS8Y-deS22HjL2XtUXfIBb1vsep7sdP7cP2YuP2zH5vdI6u67f_S6U2ajUH4k-rag9LJcdwcdWP12tTQ4lLrJWK4vaC_zrqRgbynnyZkuk49BHPHFDw'
    And param idDocumentNumber = idDocumentNumber
    And method get
    Then status 401
    * print response

    Examples:
      | idDocumentNumber |
      | 1102506886       |

  @loanBox
  Scenario Outline: Validate whether find contacts call is getting 400 status code when passing more than 1 query
    And url baseUrlLoanBox + '/api/integration/v1/contacts'
    And param idDocumentNumber = idDocumentNumber
    And param email = email
    And method get
    Then status 400
    * print response

    Examples:
      | idDocumentNumber | email                  |
      | 1102506886       | testing7979@gmail.com  |


  @loanBox
  Scenario Outline: Validate whether find contacts call is getting 404 status code when passing invalid ID document number
    And url baseUrlLoanBox + '/api/integration/v1/contacts'
    And param idDocumentNumber = idDocumentNumber
    And method get
    Then status 404
    * print response

    Examples:
      | idDocumentNumber |
      | 1102506777       |

  @loanBox
  Scenario Outline: Validate whether onboard contact call is getting 404 status code  when passing invalid ID document number
    And url baseUrlLoanBox + '/api/integration/v1/contacts/'+idDocumentNumber+'/onboard'
    And request idDocumentNumber
    And method put
    Then status 404
    * print response

    Examples:
      | idDocumentNumber  |
      | 1102501111        |

  @loanBox
  Scenario Outline: Validate whether onboard contact call is getting 405 status code when passing invalid method
    And url baseUrlLoanBox + '/api/integration/v1/contacts/'+idDocumentNumber+'/onboard'
    And request idDocumentNumber
    And method get
    Then status 405
    * print response

    Examples:
      | idDocumentNumber  |
      | 1102506886        |

  @loanBox
  Scenario Outline: Validate whether find loans call is getting 400 status code when passing more than 1 query
    And url baseUrlLoanBox + '/api/integration/v1/loans'
    And param idDocumentNumber = idDocumentNumber
    And param commercialRegistryNumber = commercialRegistryNumber
    And method get
    Then status 400
    * print response

    Examples:
      | idDocumentNumber |commercialRegistryNumber |
      | 1102506886       |  4046100174             |

  @loanBox
  Scenario Outline: Validate whether find loans call is getting 401 status code when passing invalid token
    And url baseUrlLoanBox + '/api/integration/v1/loans'
    Given header Authorization = 'Bearer ' + 'eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJzcWRoRldMS0dEUDJXV1NrdnlQOFZxUXFWV0FBbjZuY3BJckl5LUlyUTBFIn0.eyJleHAiOjE3MjQ2NjA0MjMsImlhdCI6MTcyNDY1ODYyMywianRpIjoiZjhiM2FkOTAtYjBlMy00NGE4LWE2OTktMWQ5YWEyYzllOWFlIiwiaXNzIjoiaHR0cHM6Ly91YXQtYXV0aC1sb2FuYm94LmFscmFlZGFoLmRldi9yZWFsbXMvSW50ZWdyYXRpb24iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiMmJmM2EzNWItOGY1MS00NTQxLTliMWYtZTMwMzlkNTcyZWVkIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiY29tcGFueS1pbnRlZ3JhdGlvbiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cHM6Ly91YXQtYm8tbG9hbmJveC5hbHJhZWRhaC5kZXYiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtaW50ZWdyYXRpb24iLCJvZmZsaW5lX2FjY2VzcyIsIkludGVncmF0aW9uQ2xpZW50IiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJjbGllbnRIb3N0IjoiMTAuMjQ0LjEuMCIsInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UtYWNjb3VudC1jb21wYW55LWludGVncmF0aW9uIiwiY2xpZW50QWRkcmVzcyI6IjEwLjI0NC4xLjAiLCJjbGllbnRfaWQiOiJjb21wYW55LWludGVncmF0aW9uIn0.pwHPmxXwCiushKelFmRf5p5zKDOnUXUqC2Dc6xHDRopLxe9QqZwsbJ89djMapE-q-Q05CICnGHN0AuywruTYDYuVYMYdG7Lpwu02ZW92ReUcJZqBSqVjXvmOBRkiu-Mn90kkiGB-lBRM-IBgg5JCtVmVfNLLENkeEe9b--QExkYzj7ZTDPBiTj5CqDUiKM-7RipHFmtMOnzaxr9T7woE-CLBqFOXUJ6togMS8Y-deS22HjL2XtUXfIBb1vsep7sdP7cP2YuP2zH5vdI6u67f_S6U2ajUH4k-rag9LJcdwcdWP12tTQ4lLrJWK4vaC_zrqRgbynnyZkuk49BHPHFDw'
    And param idDocumentNumber = idDocumentNumber
    And method get
    Then status 401
    * print response

    Examples:
      | idDocumentNumber |
      | 1102506886       |


  @loanBox
  Scenario Outline: Validate whether find loan call is getting 404 status code when passing invalid loan number
    And url baseUrlLoanBox + '/api/integration/v1/loans/'+number+''
    And param number = number
    And method get
    Then status 404
    * print response

    Examples:
      | number                |
      | L-202311-0001-3000000 |

  @loanBox
  Scenario Outline: Validate whether find loan call is getting 401 status code when passing invalid token
    And url baseUrlLoanBox + '/api/integration/v1/loans/'+number+''
    Given header Authorization = 'Bearer ' + 'eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJzcWRoRldMS0dEUDJXV1NrdnlQOFZxUXFWV0FBbjZuY3BJckl5LUlyUTBFIn0.eyJleHAiOjE3MjQ2NjA0MjMsImlhdCI6MTcyNDY1ODYyMywianRpIjoiZjhiM2FkOTAtYjBlMy00NGE4LWE2OTktMWQ5YWEyYzllOWFlIiwiaXNzIjoiaHR0cHM6Ly91YXQtYXV0aC1sb2FuYm94LmFscmFlZGFoLmRldi9yZWFsbXMvSW50ZWdyYXRpb24iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiMmJmM2EzNWItOGY1MS00NTQxLTliMWYtZTMwMzlkNTcyZWVkIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiY29tcGFueS1pbnRlZ3JhdGlvbiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cHM6Ly91YXQtYm8tbG9hbmJveC5hbHJhZWRhaC5kZXYiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtaW50ZWdyYXRpb24iLCJvZmZsaW5lX2FjY2VzcyIsIkludGVncmF0aW9uQ2xpZW50IiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJjbGllbnRIb3N0IjoiMTAuMjQ0LjEuMCIsInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UtYWNjb3VudC1jb21wYW55LWludGVncmF0aW9uIiwiY2xpZW50QWRkcmVzcyI6IjEwLjI0NC4xLjAiLCJjbGllbnRfaWQiOiJjb21wYW55LWludGVncmF0aW9uIn0.pwHPmxXwCiushKelFmRf5p5zKDOnUXUqC2Dc6xHDRopLxe9QqZwsbJ89djMapE-q-Q05CICnGHN0AuywruTYDYuVYMYdG7Lpwu02ZW92ReUcJZqBSqVjXvmOBRkiu-Mn90kkiGB-lBRM-IBgg5JCtVmVfNLLENkeEe9b--QExkYzj7ZTDPBiTj5CqDUiKM-7RipHFmtMOnzaxr9T7woE-CLBqFOXUJ6togMS8Y-deS22HjL2XtUXfIBb1vsep7sdP7cP2YuP2zH5vdI6u67f_S6U2ajUH4k-rag9LJcdwcdWP12tTQ4lLrJWK4vaC_zrqRgbynnyZkuk49BHPHFDw'
    And param number = number
    And method get
    Then status 401
    * print response

    Examples:
      | number                |
      | L-202311-0001-3001046 |

  @loanBox
  Scenario Outline: Validate whether find payment schedule call is getting 401 status code when passing invalid token
    And url baseUrlLoanBox + '/api/integration/v1/loans/'+number+'/payment-schedule'
    Given header Authorization = 'Bearer ' + 'eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJzcWRoRldMS0dEUDJXV1NrdnlQOFZxUXFWV0FBbjZuY3BJckl5LUlyUTBFIn0.eyJleHAiOjE3MjQ2NjA0MjMsImlhdCI6MTcyNDY1ODYyMywianRpIjoiZjhiM2FkOTAtYjBlMy00NGE4LWE2OTktMWQ5YWEyYzllOWFlIiwiaXNzIjoiaHR0cHM6Ly91YXQtYXV0aC1sb2FuYm94LmFscmFlZGFoLmRldi9yZWFsbXMvSW50ZWdyYXRpb24iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiMmJmM2EzNWItOGY1MS00NTQxLTliMWYtZTMwMzlkNTcyZWVkIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiY29tcGFueS1pbnRlZ3JhdGlvbiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cHM6Ly91YXQtYm8tbG9hbmJveC5hbHJhZWRhaC5kZXYiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtaW50ZWdyYXRpb24iLCJvZmZsaW5lX2FjY2VzcyIsIkludGVncmF0aW9uQ2xpZW50IiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJjbGllbnRIb3N0IjoiMTAuMjQ0LjEuMCIsInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UtYWNjb3VudC1jb21wYW55LWludGVncmF0aW9uIiwiY2xpZW50QWRkcmVzcyI6IjEwLjI0NC4xLjAiLCJjbGllbnRfaWQiOiJjb21wYW55LWludGVncmF0aW9uIn0.pwHPmxXwCiushKelFmRf5p5zKDOnUXUqC2Dc6xHDRopLxe9QqZwsbJ89djMapE-q-Q05CICnGHN0AuywruTYDYuVYMYdG7Lpwu02ZW92ReUcJZqBSqVjXvmOBRkiu-Mn90kkiGB-lBRM-IBgg5JCtVmVfNLLENkeEe9b--QExkYzj7ZTDPBiTj5CqDUiKM-7RipHFmtMOnzaxr9T7woE-CLBqFOXUJ6togMS8Y-deS22HjL2XtUXfIBb1vsep7sdP7cP2YuP2zH5vdI6u67f_S6U2ajUH4k-rag9LJcdwcdWP12tTQ4lLrJWK4vaC_zrqRgbynnyZkuk49BHPHFDw'
    And param number = number
    And method get
    Then status 401
    * print response

    Examples:
      | number                |
      | L-202311-0001-3001046 |

  @loanBox
  Scenario Outline: Validate whether find payment schedule call is getting 404 status code when passing invalid loan number
    And url baseUrlLoanBox + '/api/integration/v1/loans/'+number+'/payment-schedule'
    And param number = number
    And method get
    Then status 404
    * print response

    Examples:
      | number                |
      | L-202311-0001-3000000 |

  @loanBox
  Scenario Outline: Validate whether the Find loan applications call is getting 401 status code when passing invalid token
    And url baseUrlLoanBox + '/api/integration/v1/loan-applications'
    Given header Authorization = 'Bearer ' + 'eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJzcWRoRldMS0dEUDJXV1NrdnlQOFZxUXFWV0FBbjZuY3BJckl5LUlyUTBFIn0.eyJleHAiOjE3MjQ2NjA0MjMsImlhdCI6MTcyNDY1ODYyMywianRpIjoiZjhiM2FkOTAtYjBlMy00NGE4LWE2OTktMWQ5YWEyYzllOWFlIiwiaXNzIjoiaHR0cHM6Ly91YXQtYXV0aC1sb2FuYm94LmFscmFlZGFoLmRldi9yZWFsbXMvSW50ZWdyYXRpb24iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiMmJmM2EzNWItOGY1MS00NTQxLTliMWYtZTMwMzlkNTcyZWVkIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiY29tcGFueS1pbnRlZ3JhdGlvbiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cHM6Ly91YXQtYm8tbG9hbmJveC5hbHJhZWRhaC5kZXYiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtaW50ZWdyYXRpb24iLCJvZmZsaW5lX2FjY2VzcyIsIkludGVncmF0aW9uQ2xpZW50IiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJjbGllbnRIb3N0IjoiMTAuMjQ0LjEuMCIsInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UtYWNjb3VudC1jb21wYW55LWludGVncmF0aW9uIiwiY2xpZW50QWRkcmVzcyI6IjEwLjI0NC4xLjAiLCJjbGllbnRfaWQiOiJjb21wYW55LWludGVncmF0aW9uIn0.pwHPmxXwCiushKelFmRf5p5zKDOnUXUqC2Dc6xHDRopLxe9QqZwsbJ89djMapE-q-Q05CICnGHN0AuywruTYDYuVYMYdG7Lpwu02ZW92ReUcJZqBSqVjXvmOBRkiu-Mn90kkiGB-lBRM-IBgg5JCtVmVfNLLENkeEe9b--QExkYzj7ZTDPBiTj5CqDUiKM-7RipHFmtMOnzaxr9T7woE-CLBqFOXUJ6togMS8Y-deS22HjL2XtUXfIBb1vsep7sdP7cP2YuP2zH5vdI6u67f_S6U2ajUH4k-rag9LJcdwcdWP12tTQ4lLrJWK4vaC_zrqRgbynnyZkuk49BHPHFDw'
    And param idDocumentNumber = idDocumentNumber
    And method get
    Then status 401
    * print response

    Examples:
      | idDocumentNumber |
      | 1102506886       |


  @loanBox
  Scenario Outline: Validate whether the Find loan applications call is getting 400 status code when passing more than one query
    And url baseUrlLoanBox + '/api/integration/v1/loan-applications'
    And param idDocumentNumber = idDocumentNumber
    And param commercialRegistryNumber = commercialRegistryNumber
    And method get
    Then status 400
    * print response

    Examples:
      | idDocumentNumber |commercialRegistryNumber |
      | 1102506886       | 4046100174              |

  @loanBox
  Scenario Outline: Validate whether the find loan application call is getting 404 when passing invalid loan number
    And url baseUrlLoanBox + '/api/integration/v1/loan-applications/'
    And path number
    And method get
    Then status 404
    * print response

    Examples:
      | number                |
      | L-202311-0001-3000000 |

  @loanBox
  Scenario Outline: Validate whether the find loan application by number call is getting 401 status code when passing invalid token
    And url baseUrlLoanBox + '/api/integration/v1/loan-applications/'+number+''
    Given header Authorization = 'Bearer ' + 'eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJzcWRoRldMS0dEUDJXV1NrdnlQOFZxUXFWV0FBbjZuY3BJckl5LUlyUTBFIn0.eyJleHAiOjE3MjQ2NjA0MjMsImlhdCI6MTcyNDY1ODYyMywianRpIjoiZjhiM2FkOTAtYjBlMy00NGE4LWE2OTktMWQ5YWEyYzllOWFlIiwiaXNzIjoiaHR0cHM6Ly91YXQtYXV0aC1sb2FuYm94LmFscmFlZGFoLmRldi9yZWFsbXMvSW50ZWdyYXRpb24iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiMmJmM2EzNWItOGY1MS00NTQxLTliMWYtZTMwMzlkNTcyZWVkIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiY29tcGFueS1pbnRlZ3JhdGlvbiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cHM6Ly91YXQtYm8tbG9hbmJveC5hbHJhZWRhaC5kZXYiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtaW50ZWdyYXRpb24iLCJvZmZsaW5lX2FjY2VzcyIsIkludGVncmF0aW9uQ2xpZW50IiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJjbGllbnRIb3N0IjoiMTAuMjQ0LjEuMCIsInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UtYWNjb3VudC1jb21wYW55LWludGVncmF0aW9uIiwiY2xpZW50QWRkcmVzcyI6IjEwLjI0NC4xLjAiLCJjbGllbnRfaWQiOiJjb21wYW55LWludGVncmF0aW9uIn0.pwHPmxXwCiushKelFmRf5p5zKDOnUXUqC2Dc6xHDRopLxe9QqZwsbJ89djMapE-q-Q05CICnGHN0AuywruTYDYuVYMYdG7Lpwu02ZW92ReUcJZqBSqVjXvmOBRkiu-Mn90kkiGB-lBRM-IBgg5JCtVmVfNLLENkeEe9b--QExkYzj7ZTDPBiTj5CqDUiKM-7RipHFmtMOnzaxr9T7woE-CLBqFOXUJ6togMS8Y-deS22HjL2XtUXfIBb1vsep7sdP7cP2YuP2zH5vdI6u67f_S6U2ajUH4k-rag9LJcdwcdWP12tTQ4lLrJWK4vaC_zrqRgbynnyZkuk49BHPHFDw'
    And path number
    And method get
    Then status 401
    * print response

    Examples:
      | number                |
      | L-202311-0001-3001046 |







