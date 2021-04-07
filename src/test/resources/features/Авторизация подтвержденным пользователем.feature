#language: ru

Функция: Авторизация подтвержденным пользователем

  Предыстория: :
    Пусть В системе заведен пользователь "admin" с параметрами:
      | admin  | false |
      | status | 1     |
    И Открыт браузер на главной странице
    И На странице "Заголовок" нажать на элемент "Войти"

  Сценарий: Авторизация подтвержденным пользователем
    Если Авторизоваться пользователем "admin"
    То Будет открыта страница "Домашняя странца"
    И На главной странице пользователя "admin" элемент Вошли как имеет текст "Вошли как ${admin ->логин}"
    И На главной странице будут отображены элементы "Домашняя страница"
    И На главной странице будут отображены элементы "Моя страница"
    И На главной странице будут отображены элементы "Проекты"
    И На главной странице будут отображены элементы "Помощь"
    И На главной странице будут отображены элементы "Моя учётная запись"
    И На главной странице будут отображены элементы "Выйти"
    И На главной странице будут отображены элементы "Поиск"
    И На главной странице не отображены элементы "Войти"
    И На главной странице не отображены элементы "Регистрация"
    И На главной странице не отображены элементы "Администрирование"