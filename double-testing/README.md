# Double Testing

Write your tests once and run them twice - as both unit and integration tests.

## Unit Phase
```
mvn clean install -DskipITs
```

## Integration Phase
```
mvn clean verify -f sample-application/tests/pom.xml
```
