#language: ru

Функция: Авторизация администратором

  Предыстория:
    Пусть В системе заведен пользователь "admin" с параметрами:
      | admin  | true |
      | status | 1    |
    И Открыт браузер на главной странице

  Сценарий: Авторизация администратором в системе
    Если Авторизоваться пользователем "admin"
    То На главной странице будут отображены элементы "Домашняя страница"
    То На главной странице будут отображены элементы "Вошли как"
    То На главной странице будут отображены элементы "Моя страница"
    То На главной странице будут отображены элементы "Проекты"
    То На главной странице будут отображены элементы "Администрирование"
    То На главной странице будут отображены элементы "Помощь"
    То На главной странице будут отображены элементы "Моя учётная запись"
    То На главной странице будут отображены элементы "Выйти"
    То На главной странице будут отображены элементы "Поиск"
    И На главной странице не отображены элементы "Войти"
    И На главной странице не отображены элементы "Регистрация"



