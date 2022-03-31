# Config Server

## Docs
https://cloud.spring.io/spring-cloud-config/reference/html

## Used profiles
A list of the profiles used in this demo. Profiles can be combined - more info in the docs.

* native - uses filesystem
* git - uses git repository
* vault - uses Vault

### Profile: native
This profile uses classpath or filesystem repository. 
In this project, it reads configuration from src/main/resources/config/** locations.

### Profile: git
This profile uses git repository from https://github.com/akolata/spring-cloud-config-repo

#### Tip
It is recommended to set cloned repository directory to a path, which won't get auto-cleaned by some built-in OS mechanism.
More info here: https://cloud.spring.io/spring-cloud-config/reference/html/#_version_control_backend_filesystem_use

### Profile: vault
This profile uses Vault as a backend repository.

#### Setup
1. Run Vault using provided docker-compose file
2. Go to localhost:8200
3. Select 'secret' backend
4. Create secret `application` (available to all apps):
```json
{
  "common-property": "I am imported from vault/secret/application"
}
```
5. Create secret `product-service`:
```json
{
  "prop-location": "vault/secret/product-service",
  "hello": "Hello from Vault"
}
```
6.Create secret `product-service,prod`:
```json
{
  "prop-location": "vault/secret/product-service,prod",
  "hello": "Hello from Vault (for prod profile)"
}
```

## Endpoints
Example HTTP requests are located in `src/test/http` directory.