# Spring Cloud Config Demo

Project showing how Spring Cloud Config works

## Installation

Use provided `docker-compose.yaml` file to setup RabbitMQ and Vault.  
Service `config-server` is a Spring Cloud Config Server.   
Service `product-service` represents Spring Cloud Config Client.

## Demos

Examples were prepared using 'vault' profile for config-server.

> We can trigger a bus from any node connected to the bus - eg. config client or server Spring Cloud monitor works with local git repository too It automatically changes all clients

### Differences between beans annotated with @RefreshScope

1. Go to http://localhost:8200 and setup secrets as described in `config-server/README.md`
2. Run `config-service`
3. Run an instance of `product-service` on port 9010
4. Execute request `curl -X GET http://localhost:9010/with-refresh-scope/props` - controller annotated with
   @RefreshScope

```json
{
   "injectedUsingValue": {
      "commonProperty": "I am imported from vault/secret/application",
      "propLocation": "vault/secret/product-service",
      "hello": "Hello from Vault"
   },
   "injectedUsingEnvironment": {
      "commonProperty": "I am imported from vault/secret/application",
      "propLocation": "vault/secret/product-service",
      "hello": "Hello from Vault"
   },
   "configurationPropertiesWithRefreshScope": {
      "configurationPropertiesHello": "Hello from Vault (configuration properties)"
   }
}
```

5. Execute request `curl -X GET http://localhost:9010/without-refresh-scope/props`

```json
{
   "injectedUsingValue": {
      "commonProperty": "I am imported from vault/secret/application",
      "propLocation": "vault/secret/product-service",
      "hello": "Hello from Vault"
   },
   "injectedUsingEnvironment": {
      "commonProperty": "I am imported from vault/secret/application",
      "propLocation": "vault/secret/product-service",
      "hello": "Hello from Vault"
   },
   "configurationPropertiesWithRefreshScope": {
      "configurationPropertiesHello": "Hello from Vault (configuration properties)"
   }
}
```

5. Update properties in Vault - e.g. change the value of property `hello` to `Hello from demo no 1`, and
   property `configuration-prop.hello` to `Hello from demo no 1 (configuration properties)`
6. Execute request `curl -X GET http://localhost:9010/with-refresh-scope/props` - controller annotated with
   @RefreshScope - no changes
7. Execute request `curl -X GET http://localhost:9010/without-refresh-scope/props` - no changes
8. **Refresh** `product-service` - e.g. by executing

```shell
curl -vvv -X POST http://localhost:9010/actuator/refresh
* Mark bundle as not supporting multiuse
< HTTP/1.1 200 
< Content-Type: application/vnd.spring-boot.actuator.v3+json
[
  "hello",
  "configuration-prop.hello"
]%                                                                                             
```

9. Execute request `curl -X GET http://localhost:9010/with-refresh-scope/props` - controller annotated with
   @RefreshScope

```json
{
   "injectedUsingValue": {
      "commonProperty": "I am imported from vault/secret/application",
      "propLocation": "vault/secret/product-service",
      "hello": "Hello from demo no 1"
   },
   "injectedUsingEnvironment": {
      "commonProperty": "I am imported from vault/secret/application",
      "propLocation": "vault/secret/product-service",
      "hello": "Hello from demo no 1"
   },
   "configurationPropertiesWithRefreshScope": {
      "configurationPropertiesHello": "Hello from demo no 1 (configuration properties)"
   }
}
```

10. Execute request `curl -X GET http://localhost:9010/without-refresh-scope/props`

```json
{
   "injectedUsingValue": {
      "commonProperty": "I am imported from vault/secret/application",
      "propLocation": "vault/secret/product-service",
      "hello": "Hello from Vault"
   },
   "injectedUsingEnvironment": {
      "commonProperty": "I am imported from vault/secret/application",
      "propLocation": "vault/secret/product-service",
      "hello": "Hello from demo no 1"
   },
   "configurationPropertiesWithRefreshScope": {
      "configurationPropertiesHello": "Hello from demo no 1 (configuration properties)"
   }
}
```

#### Conclusions

1. Property injected using `ConfigurationProperties` was updated in both cases.
2. Property read from `Evironment` was updated in both cases.
3. Property read from `@Value` was updated only in the bean annotated with `@RefreshScope`

### Refreshing all instances connected to Spring Cloud Bus

1. Go to http://localhost:8200 and setup secrets as described in `config-server/README.md`
2. Run `config-service`
3. Run an instance of `product-service` on port 9010
4. Execute request `curl -X GET http://localhost:9011/with-refresh-scope/props` - first instance

```json
{
   "injectedUsingValue": {
      "commonProperty": "I am imported from vault/secret/application",
      "propLocation": "vault/secret/product-service",
      "hello": "Hello from Vault"
   },
   "injectedUsingEnvironment": {
      "commonProperty": "I am imported from vault/secret/application",
      "propLocation": "vault/secret/product-service",
      "hello": "Hello from Vault"
   },
   "configurationPropertiesWithRefreshScope": {
      "configurationPropertiesHello": "Hello from Vault (configuration properties)"
   }
}
```

5. Execute request `curl -X GET http://localhost:9010/without-refresh-scope/props`

```json
{
   "injectedUsingValue": {
      "commonProperty": "I am imported from vault/secret/application",
      "propLocation": "vault/secret/product-service",
      "hello": "Hello from Vault"
   },
   "injectedUsingEnvironment": {
      "commonProperty": "I am imported from vault/secret/application",
      "propLocation": "vault/secret/product-service",
      "hello": "Hello from Vault"
   },
   "configurationPropertiesWithRefreshScope": {
      "configurationPropertiesHello": "Hello from Vault (configuration properties)"
   }
}
```

5. Update properties in Vault - e.g. change the value of property `hello` to `Hello from demo no 2`, and
   property `configuration-prop.hello` to `Hello from demo no 2 (configuration properties)`
6. Execute request `curl -X GET http://localhost:9010/with-refresh-scope/props` - controller annotated with
   @RefreshScope - no changes
7. Execute request `curl -X GET http://localhost:9010/without-refresh-scope/props` - no changes
8. Refresh all clients of Spring Cloud Bus - e.g. by executing
   request `curl -X POST http://localhost:8888/actuator/busrefresh`
9. Instance on port 9010 - updated
9. Instance on port 9011 - updated
