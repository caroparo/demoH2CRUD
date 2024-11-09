### Project Setup ###
- Maven
- Java 8
- Spring Boot 2.7.18
- In-memory H2 database

### Features ###
- API Endpoints (localhost:9091)
    1. /currencies
        - POST:     to add a new currency
            - request body: currency {code, name}
                - code: ISO 4217 currency code
            - success: 201 Created
        - GET:      to list all existing currencies
            - returned currency details format: {id, code, chineseName}
            - success: 200 Ok
    2. /currencies/{id}
        - GET:      to get an existing currency details by {id}
            - returned currency details format: {id, code, chineseName}
            - success: 200 Ok
        - PUT:      to update an existing currency by {id}
            - request body: currency {code, name}
                - code: ISO 4217 currency code
            - success: 200 Ok
        - DELETE:   to delete an existing currency by {id}
            - success: 200 Ok
    3. /api
        - GET:      to get a translated CoinDesk BPI rates for all currencies defined in the database\
            - response: { timeUpdated, bpi: { code: { code, name, rate } } }
                - timeUpdated: yyyy/MM/dd HH:mm:ss (system local time)
                - name: translated zh-TW name as defined in the database
                - rate: the exchange rate returned from CoinDesk
                    - "not available" if CoinDesk doesn't provide a rate for the specified currency code
            - success: 200 Ok
        - not versioned for simplicity
    4. /h2-console
        - h2:mem:demodb
        - username: sa
        - password:
- Request Validation
    - invalid request bodies or params
        - 400: bad requests
        - 409 conflict
    - valid routes with invalid method
        - 405: method not allowed
    - invalid routes:
        - 404 not found
- Tests with Surefire Reports
    - \> mvn test
        - Outputs may contain en_US.UTF-8 characters. Please set LC_ALL and LANG if needed to.
    - BPIServiceTests
        1. GET_CoinDeskBPI_Expected:
            - hits external CoinDesk BPI endpoint
            - expects
                - Ok status
                - non-null response
                - non-null response.bpi
                - number of bpi entries > 0
    - BPITests
        1. GET_TranslatedBPI_Expected
            - hits /api/
            - expects
                - Ok status
                - JSON "timeUpdated", "bpi" paths to exist
            - prints to System.out
                - "Response content: " + responseContent
    - CurrencyControllerTests: @Transactional tests
        1. Get_Currencies_Expected
            - GET /currencies/
            - expects
                - Ok status
                - has 3 entries
                - valid nonnull entries {id, code, chineseName}
            - prints to System.out
                - "Response content: " + responseContent
        2. Add_Currencies_Created
            - POST /currencies/
                - body: {"code": "JPY", "name": "日圓"}
            - expects
                - Created status
                - response id exists
                - response code to be "JPY"
                - response chineseName to be "日圓"
        3. Get_Currency_Expected
            - GET /currencies/2
            - expects
                - Ok status
                - response id exists
                - response code to be "GBP"
                - response chineseName to be "英鎊"
        4. Update_Currency_Expected
            - PUT /currencies/2
                - body: {"code": "GBP", "name": "英磅"}
            - expects
                - Ok status
                - response id exists
                - response code to be "GBP"
                - response chineseName to be "英磅" (originally "英鎊")
            - prints to System.out
                - "Response content: " + responseContent
        5. Delete_Currency_Expected
            - DELETE /currencies/2
            - expects Ok status
- Initial Database Definition
    - sql/schema.sql
    - sql/data.sql