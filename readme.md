# Stock Services

## Requirement
Write 2 services to read and process the data

### Service #1
```
Read the provided text "test3.txt" and broadcast line by line to Kafka as the message broker.
```

### Service #2
```
Read the data from message broker summarize the data in a minutely fashion to find the hightest price and lowest price (per stock symbol) during that minute.
```

## How to run

```bash
# Add kafka to your hosts
sudo nano /etc/hosts
127.0.0.1 kafka

# running docker compose
docker-compose -f docker-compose.yml up

# run service #2
go run cmd/consumer/main.go

# run service #1
go run cmd/publiser/main.go

# output file at "output.txt"
```