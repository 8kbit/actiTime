# Текст задания:

Разработать REST API, которое предоставляет возможность создания, редактирования и удаления пользователей. 
Доступ к API разрешён только авторизованным пользователям.

В рамках приложения существуют две роли: менеджер и сотрудник.

Пользователь с ролью "менеджер" может просматривать, редактировать и удалять пользователей. Также менеджер может добавить/удалить роль "менеджер" для любого пользователя.
Пользователь с ролью "сотрудник" может только просматривать пользователей, без возможности редактирования и удаления.


# Инструкции по установке:

1) Установить базу PostgreSQL. База должна быть пустой.
база доступна на localhost:5432/actitime
db_user: p2p
db_password: n2bkjErp2ppNvW

Настройки базы расположены в actitime/build.gradle и actitime/src/main/resources/application.properties

2) Перед запуском накатить скрипт обновления: зайти в папку /actitime и выполнить "gradlew update"

3) Запуск проекта: зайти в папку /actitime и выполнить "gradlew appRun"
Страница логина доступна по URL: localhost:8080/static/html/login.html

4) Для логина указать admin admin
