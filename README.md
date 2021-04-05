# Telegram: @your_notify_bot

### Микросервисы:

* #### discovery-server
    * Обслуживающий сервер

* #### telegram-bot
    * Клиент discovery-server
    * Ответы на запросы от бота
    * Реализация пользовательской сессии при помощи H2 embedded
    * Запросы создания событий прокидываются в notification-service при помощи REST API
    * Отправка сообщений боту, полученных notification-service по REST API

* #### notification-service
    * Клиент discovery-server
    * Получение, обработка и ответ на запросы от telegram-bot при помощи REST API
    * CRUD операции событий в СУБД PostgreSQL
    * Scheduler по времени события с отправкой сообщения telegram-bot при помощи REST API

* #### common
    * Модуль общих DTO микросервисов

### Развертывание:

* Создание образов при помощи Dockerfile
* Частичная автоматизация за счёт docker-compose.yml
* Ручное клонирование, компиляция и развёртывание образов на ВМ клауд-хостинга

### TODO:

* Timezones
* Логирование
* CI/CD
* BD backup