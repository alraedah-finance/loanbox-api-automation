@regression @mobileRegression
Feature: Mobile automation
  Background:
    Given header Authorization = 'Bearer ' + mobile_token

   @mobile
  Scenario Outline:  Validate whether the login token call is getting 400 when invalid legalId
    And url baseUrl + '/oauth2/token'
    Given header Authorization = call read('classpath:util/Basic_auth.js') { username: 'mobile', password: 'mobile-secret-pre' }
    And header Content-Type = 'application/x-www-form-urlencoded'
    And form field legal_id = legal_id
    And form field otp = 1234
    And form field client_id = 'mobile'
    And form field grant_type = 'urn:alraedah:params:oauth:grant-type:otp'
    And form field scope = 'openid'
    And method post
    Then status 400
    * print response

    Examples:
      | legal_id  |
      |1021661111 |

  @mobile
  Scenario Outline:  Validate whether the login token call is getting 400 when  invalid OTP
    And url baseUrl + '/oauth2/token'
    Given header Authorization = call read('classpath:util/Basic_auth.js') { username: 'mobile', password: 'mobile-secret-pre' }
    And header Content-Type = 'application/x-www-form-urlencoded'
    And form field legal_id = legal_id
    And form field otp = 6789
    And form field client_id = 'mobile'
    And form field grant_type = 'urn:alraedah:params:oauth:grant-type:otp'
    And form field scope = 'openid'
    And method post
    Then status 400
    * print response


    Examples:
      | legal_id  |
      |1021665276 |

  @mobile
  Scenario Outline:  Validate whether the login token call is getting 401 when passing invalid username
    And url baseUrl + '/oauth2/token'
    Given header Authorization = call read('classpath:util/Basic_auth.js') { username: 'invalid', password: 'mobile-secret-pre' }
    And header Content-Type = 'application/x-www-form-urlencoded'
    And form field legal_id = legal_id
    And form field otp = 1234
    And form field client_id = 'mobile'
    And form field grant_type = 'urn:alraedah:params:oauth:grant-type:otp'
    And form field scope = 'openid'
    And method post
    Then status 401
    * print response

    Examples:
      | legal_id  |
      |1021665276 |

  @mobile
  Scenario Outline:  Validate whether the login token call is getting 401 when passing invalid password
    And url baseUrl + '/oauth2/token'
    Given header Authorization = call read('classpath:util/Basic_auth.js') { username: 'mobile', password: 'invalid' }
    And header Content-Type = 'application/x-www-form-urlencoded'
    And form field legal_id = legal_id
    And form field otp = 1234
    And form field client_id = 'mobile'
    And form field grant_type = 'urn:alraedah:params:oauth:grant-type:otp'
    And form field scope = 'openid'
    And method post
    Then status 401
    * print response

    Examples:
      | legal_id  |
      |1021665276 |

  @mobile
  Scenario Outline:  Validate whether the mobile number verification call is getting 403 status code
    And url baseUrl + '/oauth2/api/users/'+id+'/verify'
    * def phonePayLoad = read('classpath:Requestpayload_New/phonePayLoad.json')
    And phonePayLoad.phone = phone
    And request phonePayLoad
    And method post
    Then status 200
    * print response

    Examples:
      | id | phone     |
      |    | 511111132 |

  @mobile
  Scenario Outline:  Validate whether the mobile number verification call is getting 404 status code
    And url baseUrl + '/oauth2/api/users/'+id+'/verify'
    * def phonePayLoad = read('classpath:Requestpayload_New/phonePayLoad.json')
    And phonePayLoad.phone = phone
    And request phonePayLoad
    And method post
    Then status 200
    * print response

    Examples:
      | id         | phone |
      | 1111111132 |       |

  @mobile
  Scenario Outline:  Validate whether the mobile number verification call is successfully triggered
    And url baseUrl + '/oauth2/api/users/'+id+'/verify'
    * def phonePayLoad = read('classpath:Requestpayload_New/phonePayLoad.json')
    And phonePayLoad.phone = phone
    And request phonePayLoad
    And method post
    Then status 200
    * print response

    Examples:
      |    id    |  phone  |
      |097y12fdsh|511111132|
      |1111111132|000000000|
      |1111111132|*&%##%975|


  @mobile
  Scenario Outline: Validate whether send OTP call is getting 400 status code
    And url baseUrl + '/oauth2/api/otp'
    * def otpPayload = read('classpath:Requestpayload_New/otpPayload.json')
    And otpPayload.legalId = legalId
    And request otpPayload
    And method post
    Then status 404
    * print response

    Examples:
      | legalId   |
      |           |
      | 444523664 |
      | 1289&$@*  |
      |    null   |

  @mobile
  Scenario Outline: Validate whether verify OTP call is getting 400 status code
    And url baseUrl + '/oauth2/api/otp/verify'
    And header accept = '*/*'
    * def verifyotpPayload = read('classpath:Requestpayload_New/verifyotpPayload.json')
    And verifyotpPayload.legalId = legalId
    And request verifyotpPayload
    And method post
    Then status 404
    * print response

    Examples:
      | legalId    |
      | 1087537568 |

  @mobile
  Scenario Outline: Validate whether verify OTP call is getting 400 status code
    And url baseUrl + '/oauth2/api/otp/verify'
    And header accept = '*/*'
    * def verifyotpPayload = read('classpath:Requestpayload_New/verifyotpPayload.json')
    And verifyotpPayload.otp = otp
    And request verifyotpPayload
    And method post
    Then status 404
    * print response

    Examples:
      | otp  |
      | 5725 |
      |1234562|

  @mobile
  Scenario Outline: Validate whether verify OTP call is successfully triggered
    And url baseUrl + '/oauth2/api/otp/verify'
    And header accept = '*/*'
    * def verifyotpPayload = read('classpath:Requestpayload_New/verifyotpPayload.json')
    And verifyotpPayload.legalId = legalId
    And verifyotpPayload.otp = otp
    And request verifyotpPayload
    And method post
    Then status 404
    * print response

    Examples:
      | legalId  | otp|
      |          |1234|
      |&#$^&54477|1234|


  @mobile
  Scenario Outline: Validate whether verify OTP call is successfully triggered
    And url baseUrl + '/oauth2/api/otp/verify'
    And header accept = '*/*'
    * def verifyotpPayload = read('classpath:Requestpayload_New/verifyotpPayload.json')
    And verifyotpPayload.legalId = legalId
    And verifyotpPayload.otp = otp
    And request verifyotpPayload
    And method post
    Then status 200
    * print response

    Examples:
      | legalId  | otp|
      |1088151558| 1  |

  @mobile
  Scenario Outline: Validate whether the registration post call is getting 404 status code
    And url baseUrl + '/oauth2/api/users/'+id+'/onboarding'
    And param transactionId = transactionId
    And param randomKey = randomKey
    And request {}
    And method post
    Then status 400
    * print response

    Examples:
      | id         | transactionId | randomKey |
      | 9876545335 | transid       | randmkey  |
      |            | transid       | randmkey  |
      |     null   | transid       | randmkey  |

  @mobile
  Scenario Outline: Validate whether the registration post call is getting 403 status code
    And url baseUrl + '/oauth2/api/users/'+id+'/onboarding'
    And param transactionId = transactionId
    And param randomKey = randomKey
    And request {}
    And method post
    Then status 404
    * print response

    Examples:
      | id         | transactionId | randomKey |
      | 8765!$#586 | transid       | randmkey  |
      | 3546!$#586 |               | randmkey  |
      | 7546!$#586 | transid       |           |

  @mobile
  Scenario Outline: Validate whether the registration check call is getting 404 status code
    And url baseUrl + '/oauth2/api/users/'+id+'/onboarding'
    And method get
    Then status 200
    * print response

    Examples:
      | id         |
      | 9877556455 |

  @mobile
  Scenario Outline: Validate whether the registration check call is successfully triggered
    And url baseUrl + '/oauth2/api/users/'+id+'/onboarding'
    And method get
    Then status 200
    * print response

    Examples:
      |    id     |
      | 123**_2126|
      |           |
      |107654565432|

  @mobile
  Scenario Outline: Validate whether the registration check call is getting 403 status code
    And url baseUrl + '/oauth2/api/users/'+id+'/onboarding'
    And method get
    Then status 200
    * print response

    Examples:
      | id         |
      | 9877556455 |


  @mobile
  Scenario Outline: Validate whether send OTP call is getting 400 status code
    And url baseUrl + '/oauth2/api/otp'
    * def otpPayload = read('classpath:Requestpayload_New/otpPayload.json')
    And otpPayload.legalId = legalId
    And request otpPayload
    And method post
    Then status 404
    * print response

    Examples:
      | legalId    |
      |            |
      | 0000000000 |



  @mobile
  Scenario Outline: Validate whether find companies call is getting 403 when passing invalid ID DocumentNumber
    And url baseUrl + '/loanbox/api/integration/v1/companies'
    And param idDocumentNumber = idDocumentNumber
    And method get
    Then status 403
    * print response

    Examples:
      | idDocumentNumber |
      | 1111             |

  @mobile
  Scenario Outline: Validate whether find companies call is getting 400 when passing more than 1 query
    And url baseUrl + '/loanbox/api/integration/v1/companies'
    And param idDocumentNumber = idDocumentNumber
    And param phone = phone
    And method get
    Then status 400
    * print response

    Examples:
      | idDocumentNumber | phone           |
      | 1021665276       | 966590031364    |

  @mobile
  Scenario Outline: Validate whether find companies call is getting 401 status code when passed invalid token
    And url baseUrl + '/loanbox/api/integration/v1/companies'
    Given header Authorization = 'Bearer ' + 'eyJraWQiOiJmOGNiMGNhOS05NWU2LTRkNzctOWQxZS0yMWYxNjU4MTQ2OTYiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJtb2JpbGUiLCJhdWQiOiJtb2JpbGUiLCJsZWdhbF9pZCI6IjEwMDAwMDA2NTQiLCJuYmYiOjE3MjE3MTEyODUsInVzZXJfaWQiOjI3NjMsInBob25lIjoiOTY2NTAwMDAwNjU0IiwidXNlcl9uYW1lIjoiVFVSS0kgTUVUQUIgQUJERUxNT0hTU0VOIEFMSkJSRUVOIiwic2NvcGUiOlsid3JpdGUiLCJyZWFkIl0sImlzcyI6Imh0dHA6Ly9hcHAtZmluYW5jZS1hdXRoLnByZXByb2R1Y3Rpb24uc3ZjIiwiZXhwIjoxNzI0MzAzMjg1LCJpYXQiOjE3MjE3MTEyODUsImp0aSI6Ijg5YzY1NWU2LWIwZDctNDNlOC1iMDU0LTQ4MzI3ZDZkNGZlZCJ9.pwfhcnEUl-5CKWO314HTd7OLJ61vT9-Hwq7f-gVS0-gjaJcAgB83E_YgZmuH0_31FmgzJhr3YyVPExwOKt9sJFKExxXQSaw0vm7MK1qtLdFNzqbOxuwE0DYosnDhWgrKI1ubLgarhehnYL-09diqXW5qS_YKUnJ9cssH58RXoHEinYUtvu9XJdCbxF4NKsxgqCyPiCbFb-pOicSF3VDcEDm0K3K73lfXN4NLKvgP70n0lLvrLbZ9m1ntgjpcAO8KhDy4MKfdvOESyTyapgns7a-R6yHhgTyYPJ0iM_p5Q47jhJjeBXaPOWvz9JQmMznzsQAjKknU_laiRmEiiU0LrQ'
    And param idDocumentNumber = idDocumentNumber
    And method get
    Then status 401
    * print response

    Examples:
      |idDocumentNumber|
      |   1021665276   |

  @mobile
  Scenario Outline: Validate whether find companies call is getting 401 status code when passed expired token
    And url baseUrl + '/loanbox/api/integration/v1/companies'
    Given header Authorization = 'Bearer ' + 'eyJraWQiOiJmOGNiMGNhOS05NWU2LTRkNzctOWQxZS0yMWYxNjU4MTQ2OTYiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJtb2JpbGUiLCJhdWQiOiJtb2JpbGUiLCJsZWdhbF9pZCI6IjEwMDAwMDA2NTQiLCJuYmYiOjE3MjE3MTEyODUsInVzZXJfaWQiOjI3NjMsInBob25lIjoiOTY2NTAwMDAwNjU0IiwidXNlcl9uYW1lIjoiVFVSS0kgTUVUQUIgQUJERUxNT0hTU0VOIEFMSkJSRUVOIiwic2NvcGUiOlsid3JpdGUiLCJyZWFkIl0sImlzcyI6Imh0dHA6Ly9hcHAtZmluYW5jZS1hdXRoLnByZXByb2R1Y3Rpb24uc3ZjIiwiZXhwIjoxNzI0MzAzMjg1LCJpYXQiOjE3MjE3MTEyODUsImp0aSI6Ijg5YzY1NWU2LWIwZDctNDNlOC1iMDU0LTQ4MzI3ZDZkNGZlZCJ9.pwfhcnEUl-5CKWO314HTd7OLJ61vT9-Hwq7f-gVS0-gjaJcAgB83E_YgZmuH0_31FmgzJhr3YyVPExwOKt9sJFKExxXQSaw0vm7MK1qtLdFNzqbOxuwE0DYosnDhWgrKI1ubLgarhehnYL-09diqXW5qS_YKUnJ9cssH58RXoHEinYUtvu9XJdCbxF4NKsxgqCyPiCbFb-pOicSF3VDcEDm0K3K73lfXN4NLKvgP70n0lLvrLbZ9m1ntgjpcAO8KhDy4MKfdvOESyTyapgns7a-R6yHhgTyYPJ0iM_p5Q47jhJjeBXaPOWvz9JQmMznzsQAjKknU_laiRmEiiU0LrQ'
    And param idDocumentNumber = idDocumentNumber
    And method get
    Then status 401
    * print response

    Examples:
      |idDocumentNumber|
      |   1021665276   |


  @mobile
  Scenario Outline: Validate whether find company call is getting 403 when passing invalid CR is passed
    And url baseUrl + '/loanbox/api/integration/v1/companies/'+commercialRegistryNumber+''
    And method get
    Then status 403
    * print response

    Examples:
      |commercialRegistryNumber|
      |   2111617111           |


  @mobile
  Scenario Outline: Validate whether find contacts call is getting 400 when passing more than 1 query
    And url baseUrl + '/loanbox/api/integration/v1/contacts'
    And param idDocumentNumber = idDocumentNumber
    And param phone = phone
    And method get
    Then status 400
    * print response

    Examples:
      | idDocumentNumber | phone        |
      | 1021665276       | 966590031364 |

  @mobile
  Scenario Outline: Validate whether find contacts call is getting 403 status code
    And url baseUrl + '/loanbox/api/integration/v1/contacts'
    And param idDocumentNumber = idDocumentNumber
    And method get
    Then status 403
    * print response

    Examples:
      | idDocumentNumber |
      | 1021111116       |


  @mobile
  Scenario Outline: Validate whether onboard contact call getting 404 error when passing invalid id document is
    And url baseUrl + '/loanbox/api/integration/v1/contacts/'+idDocumentNumber+'/onboard'
    And request idDocumentNumber
    And method put
    Then status 404
    * print response

    Examples:
      |idDocumentNumber|
      |   1021000006   |


  @mobile
  Scenario Outline: Validate whether find loans call getting 403 status when passing invalid commercialRegistryNumber
    And url baseUrl + '/loanbox/api/integration/v1/loans'
    And param commercialRegistryNumber = commercialRegistryNumber
    And method get
    Then status 403
    * print response

    Examples:
      |commercialRegistryNumber|
      |       2000000505       |


  @mobile
  Scenario Outline: Validate whether find loans call getting 400 status when passing more than 1 query
    And url baseUrl + '/loanbox/api/integration/v1/loans'
    And param commercialRegistryNumber = commercialRegistryNumber
    And param idDocumentNumber = idDocumentNumber
    And method get
    Then status 400
    * print response

    Examples:
      |commercialRegistryNumber|idDocumentNumber|
      |       2062617505       |1021665276      |

  @mobile
  Scenario Outline: Validate whether find loan call getting 403 status when passing invalid loan number
    And url baseUrl + '/loanbox/api/integration/v1/loans/'+number+''
    And param number = number
    And method get
    Then status 403
    * print response

    Examples:
      |     number              |
      | L-202312-0001-3000000   |

  @mobile
  Scenario Outline: Validate whether find payment schedule call is getting 403 status code when passing invalid loan number
    And url baseUrl + '/loanbox/api/integration/v1/loans/'+number+'/payment-schedule'
    And param number = number
    And method get
    Then status 403
    * print response

    Examples:
      |     number              |
      | L-202312-0001-3000000   |


  @mobile
  Scenario Outline: Validate whether the Find loan applications call is getting 400 status code when passing more than one query
    And url baseUrl + '/loanbox/api/integration/v1/loan-applications'
    And param commercialRegistryNumber = commercialRegistryNumber
    And param idDocumentNumber = idDocumentNumber
    And method get
    Then status 400
    * print response


    Examples:
      |commercialRegistryNumber|idDocumentNumber |
      | 1010694443             |1119067930       |


  @mobile
  Scenario Outline: Validate whether the Find loan application is getting 404 status code when passing invalid loan number
    And url baseUrl + '/loanbox/api/integration/v1/loan-applications/'
    And path number
    And method get
    Then status 404
    * print response

    Examples:
      |    number             |
      | L-202312-0001-3000008 |

  @mobile
  Scenario Outline: Validate whether find transactions call is getting 403 status code when passing invalid loan number
    And url baseUrl + '/loanbox/api/integration/v1/transactions/loan/'
    And path number = number
    And method get
    Then status 403
    * print response

    Examples:
      |   number                 |
      |   L-202312-0001-3000008  |
