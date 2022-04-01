# Config Server

## Docs & Links

https://cloud.spring.io/spring-cloud-config/reference/html
https://cloud.spring.io/spring-cloud-static/spring-cloud.html
https://docs.spring.io/spring-cloud-bus/docs/current/reference/html/#refresh-scope

> (Spring Tips: refreshable configuration with Spring Cloud Bus, and the Spring Cloud Config Monitor)
https://youtu.be/aC_siBP8rx8

## Used profiles

A list of the profiles used in this demo. Profiles can be combined - more info in the docs.

* native - uses filesystem
* git - uses git repository
* vault - uses Vault

To change profile, modify property `spring.profiles.active` in `bootstrap.properties`

### Profile: native

This profile uses classpath or filesystem repository. In this project, it reads configuration from
src/main/resources/config/** locations.

TODO: Refreshing might not work with native profile ???

### Profile: git

This profile uses git repository from https://github.com/akolata/spring-cloud-config-repo

#### Tip

It is recommended to set cloned repository directory to a path, which won't get auto-cleaned by some built-in OS
mechanism. More info
here: https://cloud.spring.io/spring-cloud-config/reference/html/#_version_control_backend_filesystem_use

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
  "hello": "Hello from Vault",
  "configuration-prop.hello": "Hello from Vault (configuration properties)"
}
```

6.Create secret `product-service,prod`:

```json
{
  "prop-location": "vault/secret/product-service,prod",
  "hello": "Hello from Vault (for prod profile)",
  "configuration-prop.hello": "Hello from Vault (configuration properties for prod profile)"
}
```

## Endpoints

Example HTTP requests are located in `src/test/http` directory.

### bus refresh

Execute `POST http://localhost:8888/actuator/busrefresh` to trigger RefreshRemoteApplicationEvent manually.  
It will refresh all config clients using spring-cloud-bus.

### monitor

Execute `POST http://localhost:8888/monitor`  
Usually this endpoint will be triggered by VCS webhook, but it can also be triggered manually to refresh specific apps.

## Docs keynotes

> The default configuration also detects filesystem changes in local git repositories. In that case, the webhook is not used. However, as soon as you edit a config file, a refresh is broadcast.

This didn't work using profile `native`.
