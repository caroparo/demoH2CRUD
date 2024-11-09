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
            - returned currency details format: {id, code, name}
            - success: 200 Ok
    2. /currencies/{id}
        - GET:      to get an existing currency details by {id}
            - returned currency details format: {id, code, name}
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
- Initial Database Definition
    - sql/schema.sql
    - sql/data.sql