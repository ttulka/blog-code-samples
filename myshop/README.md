# MyShop

Source code for the example web application "Package by Component with Clean Modules in Java".

More info http://blog.net21.cz

## Build
```
mnv clean install
```

## Run
```
cd spring-boot
mvn spring-boot:run
```

## Modules

- `parent`      - the parent module containing meta-info about dependencies and project settings
- `domain`      - the business domain model
- `db`          - persistence implementation of the domain repositories
- `web`         - web-based UI implemented in Spring Web and Spring MVC
- `spring`      - Spring configurations, glue for ports and adapters, dependency-injection
- `spring-boot` - Spring Boot-based application, running on Tomcat 