#language: ru

Функция: Авторизация подтвержденным пользователем

  Предыстория: :
    Пусть В системе заведен пользователь "admin" с параметрами:
      | admin  | false |
      | status | 1     |
    И Открыт браузер на главной странице

  Сценарий: Авторизация подтвержденным пользователем
    Если Авторизоваться пользователем "admin"
    То Будут отображены элементы "Домашняя страница"
    То Будут отображены элементы "Моя страница"
    То Будут отображены элементы "Проекты"
    То Будут отображены элементы "Помощь"
    То Будут отображены элементы "Моя учётная запись"
    То Будут отображены элементы "Выйти"
    То Будут отображены элементы "Поиск"
    И Не отображены элементы "Войти"
    И Не отображены элементы "Регистрация"