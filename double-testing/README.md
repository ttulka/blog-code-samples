# Double Testing

Write your tests once and run them twice - as both unit and integration tests.

Source code for http://blog.ttulka.com/double-testing-130

## Unit Phase
```
mvn clean install -DskipITs
```

## Integration Phase
```
mvn clean verify -f sample-application/tests/pom.xml
```
