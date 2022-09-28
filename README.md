# Authorization Server with Spring Security and Oauth2.0
This is a demo to implement the Authorization Server with Spring Security and OAuth2.0 framework. In this demo, we issue the access token with JWT format and signed with the cryptographic key. When we try to access the Resource Server (our protected resources. e.g., the REST APIs), we have to provide the sigined JWT token. And the Resource Server should validate that JWT token.
</br></br> 
Please read the documents to find more details about [Spring Security](https://spring.io/projects/spring-security) and [OAuth2.0](https://oauth.net/2/).
</br></br>
Prerequisites: [Java8 or above](https://openjdk.org/)
</br></br>
## Getting Started
To install this demo application, please run the following command in a terminal window:
<br>
```cmd
git clone https://github.com/hailiangxie/myoauth2authorization_proj.git
cd myoauth2authorization_proj
```
## Run the Application
To run the application, run the following command in a terminal window:
<br>
```cmd
cd myoauth2authorization_proj
./mvnw spring-boot:run
```
After everything starts, you should be able to test the Authorization Server.
## Test
You shoulbe be able to test the Authorization Server endpoints by API Testing tools. For example, `curl`, `Postman`.
- Get the authorization code: Open the web browser and input the url (e.g., http://localhost:9041/oauth/authorize).
```
The endpoint /oauth/authorize is protect and it should promot to login with username and password. If the authentication is ok then the authorization code should be generated.
```
- Issue a new access token with authorization code by calling the endpoint `/oauth/token`.
```
The following parameter should be provided:
1. grant_type=authorization_code
2. authorization_code=(e.g., AB1234)
3. scope=(e.g., read)
And also we have to provide the clientId and clientSecret to authenticate.
```
- Issue a new access token with basic authentication by calling the endpoint `/oauth/token`.
```
The following parameter should be provided:
1. grant_type=password
2. username (e.g., user)
3. password (e.g., password)
4. scope (e.g., read)
And also we have to provide the clientId and clientSecret to authenticate.
```
- Validate the issued access token by calling the endpoint `/oauth/check_token`.
```
The following parameter should be provided:
1. token=(the access token issued last step)
And also we have to provide the clientId and clientSecret to authenticate.
```
- Refresh the access token which has been expired by calling the endpoint `/oauth/token`.
```
The following parameter should be provided:
1. grant_type=refresh_token
2. refresh_token=(the refresh toekn was issued with access token in the first step)
And also we have to provide the clientId and clientSecret to authenticate.
```
- Get the cryptographic public key by calling the endpoint `/oauth/token`. This public key should be configured in the Resource Server.
```
We only have to provide the clientId and clientSecret to authenticate.
```
## See Also
The following guides may also be helpful:
- [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
- [Spring Security Architecture](https://spring.io/guides/topicals/spring-security-architecture/)
