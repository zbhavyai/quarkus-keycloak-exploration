# Sample App

### Protected Endpoints

```
/api/users/me           # user role required
/api/admin              # admin role required
```

### Open Endpoints

```
/api/public
```

### Keycloak admin console

1. First open Dev UI at [http://localhost:8080/q/dev/io.quarkus.quarkus-oidc/provider](http://localhost:8080/q/dev/io.quarkus.quarkus-oidc/provider)

2. Then click on `Keycloak Admin`

3. Use below credentials:

   **user**: admin
   **password**: admin

### Testing

1. Start the application in dev mode

2. Get the access token. Replace `$PORT` with keycloak port

   ```bash
   export access_token=$(\
       curl --insecure -X POST http://localhost:$PORT/realms/quarkus/protocol/openid-connect/token \
       --user backend-service:secret \
       -H 'content-type: application/x-www-form-urlencoded' \
       -d 'username=admin&password=admin&grant_type=password' | jq --raw-output '.access_token' \
   )
   ```

3. Make the request

   ```bash
   curl -X GET http://localhost:8080/api/users/me -H "Authorization: Bearer "$access_token
   ```

   ```bash
   curl -X GET http://localhost:8080/api/admin -H "Authorization: Bearer "$access_token
   ```

   ```bash
   curl -X GET http://localhost:8080/api/public
   ```

   ```bash
   curl -X GET http://localhost:8080/api/protected/test
   ```
