Run the RabbitMQ broker:
```shell
docker run --rm -p 5672:5672 rabbitmq:4
```

Run the application:
```shell
./gradlew bootRun
```

Make a payment:
```
curl -X POST "localhost:8080/payment?reference=A&amount=123"
```

Make an invalid payment:
```
curl -X POST "localhost:8080/payment?reference=X&amount=123"
```

Cause an integration issue:
```
curl -X POST "localhost:8080/payment?reference=Y&amount=123"
```