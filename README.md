Transfer APP
* List of used libraries is listed in libraries.gradle
* Without currency
* Money in minor units
* BCrypt hash generation for account passwords
* Lock timeout: 1 second. Multiple transfer operations for same account id are handled sequentially.
* DB schema initialization script is in schema.sql
* Without server certificates
* No retries for failed transfer operations

Command for building jar:

```gradle shadowJar```

Command for running application:
``` java -jar transfer.jar [port]``` default port is 8080

API:
POST /money/rest/transfer.do
```$xslt
Json example:
{
	"senderId": "0",
	"receiverId": "1",
	"amount": "100",
	"password": "test"
}
```
POST money/rest/balance.do
```$xslt
Json example:
{
	"id":"1",
	"password":"test"
}
```