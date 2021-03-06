#language: ru
Функция: Создание пользователя. Пользователь без прав администратора

  Предыстория:
    Если В системе заведен пользователь "userRestNotAdmin" с параметрами:
      | admin  | false |
      | status | 1     |
    И У пользователя "userRestNotAdmin" есть доступ к API и ключ API

  @api
  Сценарий:Создание пользователя. Пользователь без прав администратора
    Если Отправить POST - запрос "userRestNotAdmin"-ом  на создание пользователя "userCreateRest1"
    То Статус код ответа 403