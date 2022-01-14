### User 
#### Get all restaurants with menu
curl -v http://localhost:8080/api/restaurants --user user@yandex.ru:password

#### Get restaurant ID 1 with menu
curl -v http://localhost:8080/api/restaurants/1 --user user@yandex.ru:password

#### Get menu for restaurant ID 1
curl -v http://localhost:8080/api/menu/1 --user user@yandex.ru:password

#### Vote for restaurant ID 1
curl -s -i -X POST http://localhost:8080/api/votes/1 --user user@yandex.ru:password

#### Get Vote 
curl -v http://localhost:8080/api/votes--user user@yandex.ru:password

