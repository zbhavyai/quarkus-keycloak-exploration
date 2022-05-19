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

### Testing

1. Run the keycloak server. Make sure to have imported [quarkus-realm.json](config/quarkus-realm.json).

   ```
   ./bin/kc.sh start-dev --http-port=8090 --http-relative-path /auth
   ```

2. Start the application in production mode:

   ```
   $ mvn clean install -DskipTests
   $ java -jar target/quarkus-app/quarkus-run.jar
   ```

3. Get the access token from the keycloak server for `admin` user

   ```bash
   export access_token=$(\
       curl --insecure -X POST http://localhost:8090/auth/realms/quarkus/protocol/openid-connect/token \
       --user backend-service:secret \
       -H 'content-type: application/x-www-form-urlencoded' \
       -d 'username=admin&password=admin&grant_type=password' | jq --raw-output '.access_token' \
   )
   ```

4. Make the request

   ```bash
   $ curl -X GET http://localhost:8080/api/users/me -H "Authorization: Bearer "$access_token
   ```

   ```bash
   $ curl -X GET http://localhost:8080/api/admin -H "Authorization: Bearer "$access_token
   ```

   ```bash
   $ curl -X GET http://localhost:8080/api/public
   ```

   ```bash
   $ curl -X GET http://localhost:8080/api/protected/test
   ```

### Keycloak admin console (only for dev mode)

1. First open Dev UI at [http://localhost:8080/q/dev/io.quarkus.quarkus-oidc/provider](http://localhost:8080/q/dev/io.quarkus.quarkus-oidc/provider)

2. Then click on `Keycloak Admin`

3. Use below credentials:

   **user**: admin
   **password**: admin
