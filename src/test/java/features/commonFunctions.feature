Feature: common functions

  Background:
    * def config = read('classpath:application.yaml')
    * def dbUrl = config.spring.myDatabase.url
    * def dbDriver = config.spring.myDatabase.driver
    * def dbUsername = config.spring.myDatabase.username
    * def dbPassword = config.spring.myDatabase.password
    * def DbUtils = Java.type('util.DbUtils')
    * def myDatabase = new DbUtils(dbUrl, dbDriver, dbUsername, dbPassword)
    * string env = env

  @SchemaValidation
  Scenario:  API response - Schema validation
    * string jsonData = response
    * def schemaUtils = Java.type('util.SchemaUtils')
    * assert schemaUtils.isValid(jsonData,jsonSchemaExpected)

  @APIvsDBValidation
  Scenario: API vs DB validation
    * string jsonData = response
    * def DBValidation = Java.type('util.DBValidation')
    * def DbVal =  DBValidation.responseAndDBValidation(jsonData,dbvalues,dbtable)

  @DBConfiguration
  Scenario: Configure DB
    * def config = read('application.yaml')
    * def dbUrl = config.spring.myDatabase.url
    * def dbDriver = config.spring.myDatabase.driver
    * def dbUsername = config.spring.myDatabase.username
    * def dbPassword = config.spring.myDatabase.password
    * def DbUtils = Java.type('util.DbUtils')
    * def myDatabase = new DbUtils(dbUrl, dbDriver, dbUsername, dbPassword)

  @ExtractfacilityIDFromContractId_Geidea
  Scenario: Extract facility id from contract id from Geidea from file
    * def autoposting = read('classpath:testData/New Geidea File Format.csv')
    * print 'autoposting',autoposting
    * string contract_id = autoposting[0].ExternalFinancierContractId
    * def facilityId = 'PIP-'+ contract_id.substring(contract_id.length() - 6)
    * print 'facilityId --->',facilityId


  @ExtractfacilityIDFromContractId_Sure
  Scenario: Extract facility id from contract id from Sure from file
    * def autoposting = read('classpath:testData/New Sure File Format.csv')
    * print 'autoposting',autoposting
    * string contract_id = autoposting[0].FacilityId
    * def facilityId = 'PIP-'+ contract_id.substring(contract_id.length() - 6)
    * print 'facilityId --->',facilityId

  @JsonParsing
  Scenario: convert string to Json and return Java script object
    * string text = text
    * def DBValidation = Java.type('util.DBValidation')
    * def parseText =  DBValidation.jsonParseDBValues(text)
    * def jsObj = JSON.parse(parseText)
    * print 'JS object  --->',jsObj
