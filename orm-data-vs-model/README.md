# Project to demonstrate ORM pitfalls

See more at https://blog.ttulka.com/data-model-vs-domain-model/

## Run
```sh
./gradlew bootRun
```

## Generate some data
```sh
curl localhost:8080/person -X POST | jq
```

## See the exception
```sh
curl localhost:8080/person/<personalNumber>/parent
```
