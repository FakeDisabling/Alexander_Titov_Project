# Руководство по установке и развертыванию приложения

## 1. Установка необходимых зависимостей  
Перед запуском убедитесь, что у вас установлены:  

- [Docker](https://docs.docker.com/get-docker/)  
- [JDK 17+](https://adoptium.net/)  
- [Maven](https://maven.apache.org/install.html)

## 2. Разархивируйте архив с приложением
## 3. Перейдите в папку с приложением
Это можно использовать с помощью команды
> cd path/to/project
## 4. Запустите приложения с помощью Dockerfile
Как только перешли папку с приложением, используйте команду
> docker-compose up
## 5. Используйте postman для работы с методами приложения
+ http://localhost:8080/auth/register - регистрация пользователя (POST)
+ http://localhost:8080/comment/{id} - просмотр комментариев под обьявлением (GET).
+ http://localhost:8080/comment/{id} - написание комментария под обьявлением (POST).
+ http://localhost:8080/listing - просмотр списка всех обьявлений (GET). 
+ http://localhost:8080/listing/{searchString} - поиск по названию обьявления (GET). Также можно сделать поиск по категорию /{category}
+ http://localhost:8080/listing - добавление обьявления (POST). 
+ http://localhost:8080/listing/{id} - обновление обьявления (PUT). 
+ http://localhost:8080/listing/{id} - удаление обновления (DELETE).
+ http://localhost:8080/message/{user1Id}/{user2Id} - вывод чата между одним пользователем и вторым (GET). 
+ http://localhost:8080/message - отправка сообщению пользователю (POST).
+ http://localhost:8080/promotion/{id} - продвижение обьявления в топ (POST).
+ http://localhost:8080/saleHistory/{id} - просмотр истории продаж выбранного пользователя (GET).  
+ http://localhost:8080/saleHistory - просмотр истории своих продаж (GET).
+ http://localhost:8080/profile - изменение своего профиля (PUT).
+ http://localhost:8080/profile/{id} - изменение профиля выбранного пользователя (PUT).
