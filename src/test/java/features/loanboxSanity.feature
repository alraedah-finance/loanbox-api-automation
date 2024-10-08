@sanity @loanBox_sanity @loanBoxRegression @regression
Feature: Mobile automation

  Background:
    Given header Authorization = 'Bearer ' + loanBox_token


  @loanBox
  Scenario Outline: Validate whether find companies call is successfully triggered when only id document is passed
    And url baseUrlLoanBox + '/api/integration/v1/companies'
    And param idDocumentNumber = idDocumentNumber
    And param page = page
    And param size = size
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_companies.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      | idDocumentNumber | page | size |
      | 1102506886       | 0    | 1    |

  @loanBox
  Scenario Outline: Validate whether find companies call is successfully triggered when only phone number is passed with page & size
    And url baseUrlLoanBox + '/api/integration/v1/companies'
    And param phone = phone
    And param page = page
    And param size = size
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_companies.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      | page | phone        | size |
      | 0    | 966542266943 | 1    |


  @loanBox
  Scenario Outline: Validate whether find companies call is successfully triggered when only mail is passed with page & size
    And url baseUrlLoanBox + '/api/integration/v1/companies'
    And param email = email
    And param page = page
    And param size = size
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_companies.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      | page | email                 | size |
      | 0    | testing7979@gmail.com | 1    |


  @loanBox
  Scenario Outline: Validate whether find companies call is successfully triggered when only CR is passed
    And url baseUrlLoanBox + '/api/integration/v1/companies/'+commercialRegistryNumber+''
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_company.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      | commercialRegistryNumber |
      | 4046100174               |


  @loanBox
  Scenario Outline: Validate whether find contacts call is successfully triggered when only id document is passed
    And url baseUrlLoanBox + '/api/integration/v1/contacts'
    And param idDocumentNumber = idDocumentNumber
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_contact.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      | idDocumentNumber |
      | 1102506886       |


  @loanBox
  Scenario Outline: Validate whether find contacts call is successfully triggered when only id document is passed
    And url baseUrlLoanBox + '/api/integration/v1/contacts'
    And param email = email
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_contact.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      | email                 |
      | testing7979@gmail.com |


  @loanBox
  Scenario Outline: Validate whether find contacts call is successfully triggered when only id document is passed
    And url baseUrlLoanBox + '/api/integration/v1/contacts'
    And param phone = phone
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_contact.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      | phone        |
      | 966542266943 |

  @loanBox
  Scenario Outline: Validate whether onboard contact call is successfully triggered when only phone number is passed
    And url baseUrlLoanBox + '/api/integration/v1/contacts/'+idDocumentNumber+'/onboard'
   And request idDocumentNumber
    And method put
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_contact.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      | idDocumentNumber  |
      | 1102506886        |

  @loanBox
  Scenario Outline: Validate whether find loans call is successfully triggered when only ID document number is passed
    And url baseUrlLoanBox + '/api/integration/v1/loans'
    And param idDocumentNumber = idDocumentNumber
    And param page = page
    And param size = size
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_loans.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      | idDocumentNumber |page | size |
      | 1102506886       |  0  |   1  |

  @loanBox
  Scenario Outline: Validate whether find loans call is successfully triggered when only commercialRegistryNumber is passed
    And url baseUrlLoanBox + '/api/integration/v1/loans'
    And param commercialRegistryNumber = commercialRegistryNumber
    And param page = page
    And param size = size
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_loans.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      | commercialRegistryNumber |page | size |
      | 4046100174               |  0  |   1  |

  @loanBox
  Scenario Outline: Validate whether find loan call is successfully triggered when only number is passed
    And url baseUrlLoanBox + '/api/integration/v1/loans/'+number+''
    And param number = number
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_loan.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      | number                |
      | L-202311-0001-3001046 |

  @loanBox
  Scenario Outline: Validate whether find payment schedule call is successfully triggered when only number is passed
    And url baseUrlLoanBox + '/api/integration/v1/loans/'+number+'/payment-schedule'
    And param number = number
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/payment_schedule.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      | number                |
      | L-202311-0001-3001046 |


  @loanBox
  Scenario Outline: Validate whether the Find loan applications call is successfully triggered when only id document number is passed
    And url baseUrlLoanBox + '/api/integration/v1/loan-applications'
    And param idDocumentNumber = idDocumentNumber
    And param page = page
    And param size = size
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/loan_applications.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      | idDocumentNumber |page | size|
      | 1102506886       | 0   | 1   |

  @loanBox
  Scenario Outline: Validate whether the find loan applications call is successfully triggered when only commercialRegistryNumber is passed
    And url baseUrlLoanBox + '/api/integration/v1/loan-applications'
    And param commercialRegistryNumber = commercialRegistryNumber
    And param page = page
    And param size = size
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/loan_applications.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      | commercialRegistryNumber |page | size|
      | 4046100174               | 0   | 1   |

  @loanBox
  Scenario Outline: Validate whether the find loan application by number call is successfully triggered
    And url baseUrlLoanBox + '/api/integration/v1/loan-applications/'
    And path number
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/loan_application.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      | number                |
      | L-202311-0001-3001046 |

  @loanBox
  Scenario Outline: Validate whether find transactions call is successfully triggered when only number is passed
    And url baseUrlLoanBox + '/api/integration/v1/transactions/loan/'
    And path number = number
    And param page = page
    And param size = size
    And method get
    Then status 200
    * print response
    * string jsonSchemaExpected = read('classpath:jsonFiles/find_transactions.json')
    And call read('commonFunctions.feature@SchemaValidation') { jsonData : '#(response)', jsonSchemaExpected : '#(jsonSchemaExpected)' }

    Examples:
      | number                |page | size |
      | L-202311-0001-3001046 | 0   |  1   |


