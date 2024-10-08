@sanity @sanity_mobile @regression @mobileRegression
Feature: Mobile automation
  Background:
    Given header Authorization = 'Bearer ' + mobile_token


  @mobile
  Scenario Outline:  Validate whether the login token call is successfully triggered
    And url baseUrl + '/oauth2/token'
    Given header Authorization = call read('classpath:util/Basic_auth.js') { username: 'mobile', password: 'mobile-secret-pre' }
    And header Content-Type = 'application/x-www-form-urlencoded'
    And form field legal_id = legal_id
    And form field otp = 1234
    And form field client_id = 'mobile'
    And form field grant_type = 'urn:alraedah:params:oauth:grant-type:otp'
    And form field scope = 'openid'
    And method post
    Then status 200
    * print response
    * def token = response.access_token

    Examples:
      | legal_id  |
      |1021665276 |

  @mobile
  Scenario Outline:  Validate whether the mobile number verification call is successfully triggered
    And url baseUrl + '/oauth2/api/users/'+id+'/verify'
    * def phonePayLoad = read('classpath:Requestpayload_New/phonePayLoad.json')
    And phonePayLoad.phone = phone
    And request phonePayLoad
    And method post
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/mobile_verify.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      |    id    |  phone      |
      |1021665276| 966590031364|

  @mobile
  Scenario Outline: Validate whether the registration check call is successfully triggered
    And url baseUrl + '/oauth2/api/users/'+id+'/onboarding'
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/registration_check.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      |    id     |
      | 1021665276|

  @mobile
  Scenario Outline: Validate whether the registration post call is successfully triggered
    And url baseUrl + '/oauth2/api/users/'+id+'/onboarding'
    And param transactionId = transactionId
    And param randomKey = randomKey
    And request {}
    And method post
    Then status 200
    * print response

    Examples:
      |   id     |transactionId |randomKey|
      |1021665276|    transid   | randmkey|

  @mobile
  Scenario Outline: Validate whether send OTP call is successfully triggered
    And url baseUrl + '/oauth2/api/otp'
    * def otpPayload = read('classpath:Requestpayload_New/otpPayload.json')
    And otpPayload.legalId = legalId
    And request otpPayload
    And method post
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/send_otp.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }


    Examples:
      |  legalId |
      |1021665276|

  @mobile
  Scenario Outline: Validate whether verify OTP call is successfully triggered
    And url baseUrl + '/oauth2/api/otp/verify'
    And header accept = '*/*'
    * def verifyOtpPayload = read('classpath:Requestpayload_New/verifyOtpPayload.json')
    And verifyOtpPayload.legalId = legalId
    And verifyOtpPayload.otp = otp
    And request verifyOtpPayload
    And method post
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/verify_otp.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }


    Examples:
      | legalId  | otp|
      |1021665276|1234|

  @mobile
  Scenario Outline: Validate whether find companies call is successfully triggered when only id document is passed
    And url baseUrl + '/loanbox/api/integration/v1/companies'
    And param idDocumentNumber = idDocumentNumber
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_companies.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      |idDocumentNumber|
      |   1021665276   |

  @mobile
  Scenario Outline: Validate whether find companies call is successfully triggered when only id document is passed with page & size
    And url baseUrl + '/loanbox/api/integration/v1/companies'
    And param idDocumentNumber = idDocumentNumber
    And param page = page
    And param size = size
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_companies.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      |   page    | idDocumentNumber|  size    |
      |     0     |  1021665276     |    10    |

  @mobile
  Scenario Outline: Validate whether find companies call is successfully triggered when only CR is passed
    And url baseUrl + '/loanbox/api/integration/v1/companies/'+commercialRegistryNumber+''
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_company.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      |commercialRegistryNumber|
      |   2062617505           |

  @mobile
  Scenario Outline: Validate whether find contacts call is successfully triggered when only id document is passed
    And url baseUrl + '/loanbox/api/integration/v1/contacts'
    And param idDocumentNumber = idDocumentNumber
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_contact.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      |idDocumentNumber|
      |   1021665276   |

  @mobile
  Scenario Outline: Validate whether onboard contact call is successfully triggered when only id document is passed
    And url baseUrl + '/loanbox/api/integration/v1/contacts/'+idDocumentNumber+'/onboard'
    And request idDocumentNumber
    And method put
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_contact.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      |idDocumentNumber|
      |   1021665276   |

  @mobile
  Scenario Outline: Validate whether find loans call is successfully triggered when only commercialRegistryNumber is passed
    And url baseUrl + '/loanbox/api/integration/v1/loans'
    And param commercialRegistryNumber = commercialRegistryNumber
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_loans.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      |commercialRegistryNumber|
      |       2062617505       |

  @mobile
  Scenario Outline: Validate whether find loan call is successfully triggered when only number is passed
    And url baseUrl + '/loanbox/api/integration/v1/loans/'+number+''
    And param number = number
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_loan.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      |     number              |
      | L-202312-0001-3001148   |

  @mobile
  Scenario Outline: Validate whether find payment schedule call is successfully triggered when only number is passed
    And url baseUrl + '/loanbox/api/integration/v1/loans/'+number+'/payment-schedule'
    And param number = number
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/payment_schedule.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      |     number              |
      | L-202312-0001-3001148   |

  @mobile
  Scenario Outline: Validate whether the Find loan applications call is successfully triggered when only id document number is passed
    And url baseUrl + '/loanbox/api/integration/v1/loan-applications'
    And param idDocumentNumber = idDocumentNumber
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/loan_applications.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      |idDocumentNumber|
      | 1119067930     |

  @mobile
  Scenario Outline: Validate whether the Find loan applications call is successfully triggered when only commercialRegistryNumber is passed
    And url baseUrl + '/loanbox/api/integration/v1/loan-applications'
    And param commercialRegistryNumber = commercialRegistryNumber
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/loan_applications.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      |commercialRegistryNumber|
      | 1010694443             |

  @mobile
  Scenario Outline: Validate whether the Find loan application by number call is successfully triggered
    And url baseUrl + '/loanbox/api/integration/v1/loan-applications/'
    And path number
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/loan_application.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      |    number             |
      | L-202312-0001-3001148 |

  @mobile
  Scenario Outline: Validate whether find transactions call is successfully triggered when only number is passed
    And url baseUrl + '/loanbox/api/integration/v1/transactions/loan/'
    And path number = number
    And param page = page
    And param size = size
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_transactions.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      |   number                 | page | size |
      |   L-202312-0001-3001148  |  0   |   1  |


