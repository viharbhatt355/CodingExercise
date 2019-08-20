# RESTful API to simulate simple banking operations


##To Run REST services
	mvn spring-boot:run



##Test examples
	mvn clean test

    Using any IDE,  review and run tests in com.company.app.test  


## Swagger REST API UI
	http://localhost:8000/swagger-ui.html


## Simple Examples


```

- POST http://localhost:8000/accounts

`{
    "accountId": 1,
    "balance": 0,
    "owner": "Alice",
    "accountType": "MMA"
}`



- POST http://localhost:8000/accounts

`{
    "accountId": 2,
    "balance": 0,
    "owner": "Bob",
    "accountType": "MMA"
}`



- GET http://localhost:8000/accounts

- PUT http://localhost:8000/accounts/1
`{
    "owner": "Alicia"
}`


- DELETE http://localhost:8000/accounts/1



- POST http://localhost:8000/transactions
`{
  "fromAccount": 1,
  "toAccount": 1,
  "txnAmount": 1500,
  "txnType": "DEPOSIT"
}`



- POST http://localhost:8000/transactions
`{
  "fromAccount": 2,
  "toAccount": 2,
  "txnAmount": 1500,
  "txnType": "DEPOSIT"
}`




- POST http://localhost:8000/transactions
 
{
  "fromAccount": 1,
  "toAccount": 1,
  "txnAmount": 500,
  "txnType": "WITHDRAW"
}

- POST http://localhost:8000/transactions
`{
  "fromAccount": 2,
  "toAccount": 2,
  "txnAmount": 500,
  "txnType": "WITHDRAW"
}`


POST http://localhost:8000/transactions
`{
  "fromAccount": 1,
  "toAccount": 1,
  "txnAmount": 500,
  "txnType": "WITHDRAW"
}`



POST http://localhost:8000/transactions
`{
  "fromAccount": 1,
  "toAccount": 2,
  "txnAmount": 500,
  "txnType": "TRANSFER"
}`




POST http://localhost:8000/transactions
`{
  "fromAccount": 2,
  "toAccount": 2,
  "txnAmount": 500,
  "txnType": "WITHDRAW"
}`

