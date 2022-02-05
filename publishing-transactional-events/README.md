Run the RabbitMQ broker:
```shell
docker run --rm -p 5672:5672 rabbitmq:3
```

Run the application:
```shell
./gradlew bootRun
```