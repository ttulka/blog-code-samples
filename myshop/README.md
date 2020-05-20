# Package by Component with Clean Modules in Java: MyShop example

Source code for the example web application "Package by Component with Clean Modules in Java".

More info https://blog.ttulka.com/package-by-component-with-clean-modules-in-java

## Build
```
mnv clean install
```

## Run
```
mvn spring-boot:run -f app/pom.xml
```

## Modules

- `parent`  - the parent module containing meta-info about dependencies and project settings
- `domain`  - the business domain model
- `db`      - persistence implementation of the domain repositories
- `web`     - web-based UI 
- `spring`  - Spring configurations, glue for ports and adapters, dependency-injection
- `app`     - Spring Boot-based application 
