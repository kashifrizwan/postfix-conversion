Coding Test Application
===
This is a Java Spring Boot coding test for TruckItIn. This application consist of one REST API. This REST endpoint perform basic four operation calculations with brackets. However, instead of just performing the calculations, it converts the infix equation to a postfix equation using a stack and return the converted equation. In case of invalid equation, a relevant error message is returned.

##Run Application
<b>API Details:</b>
<br />
POST http://localhost:8080/evaluateExpression
<br />
Request Body: {"equation":"(5+7)*(6-2)"}
<br />
Response Body: { "postFix": "57+62-**", "result": 48 }
<br />
<br />
<b>Run Project:</b>
mvn spring-boot:run
<br />
<b>Run Unit Tests:</b>
mvn test
<br />
<b>Run SpringLint:</b>
mvn com.github.mauricioaniche:springlint-maven-plugin:0.5:springlint

## Note
Project code is scanned with SonarLint, SpringLint and Spring JavaFormat. Report "springlint-result.html" can be found at the project root directory.