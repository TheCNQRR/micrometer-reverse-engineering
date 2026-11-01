## ФИ: Поздняков Александр

## Исследуемая библиотека: micrometer, исследуемая часть - micrometer-core (часть библиотеки), версия 1.17.1

## Ссылки на репозиторий библиотеки:

- [micrometer](https://github.com/micrometer-metrics/micrometer/tree/main)

- [micrometer-core](https://github.com/micrometer-metrics/micrometer/tree/main/micrometer-core)

## Требования к запуску
- Java 21
- Gradle
- Docker

## Инструкция по запуску проекта:
1) Склонировать проект
2) Поднять окружение с помощью команды `docker-compose -up d`
3) Запустить проект через кнопку run в интерфейсе IntelliJ IDEA или командой `./gradlew run`

## Доступные эндпоинты:

1) POST http://localhost:8080/api/users/register - создание нового пользователя

Body:
```json
{
    "username": "user1",
    "displayName": "User 1"
}
```

2) GET http://localhost:8080/api/users - получение всех пользователей

3) GET http://localhost:8080/api/users/{id} - получение пользователя по id

4) GET http://localhost:8080/actuator - индексная страница со ссылками на все доступные эндпоинты

5) GET http://localhost:8080/actuator/health - общий статус приложения

6) GET http://localhost:8080/actuator/info - статическая информация о приложении

7) GET http://localhost:8080/actuator/metrics - список всех зарегистрированных метри

8) GET http://localhost:8080/actuator/metrics/{name} - детали конкретной метрики