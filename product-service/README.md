# Spring Cloud Config Server Client

Service `product-service` is a Spring Cloud Config Service client - it fetches properties on startup.

## Docs keynotes

> By default, the configuration values are read on the client’s startup and not again. You can force a bean to refresh its configuration However, it is necessary to note that /refresh won't work for beans with an explicit singleton scope.
> 