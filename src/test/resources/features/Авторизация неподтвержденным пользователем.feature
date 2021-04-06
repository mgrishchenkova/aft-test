#language:ru
Функция: Авторизация неподтвержденным пользователем

  Предыстория:
    Пусть В системе заведен пользователь "users" с параметрами:
      | admin  | false |
      | status | 2     |
    И Открыт браузер на главной странице
    И На странице "Заголовок" нажать на элемент "Войти"

    Сценарий: Авторизация неподтвержденным пользователем
      Если Авторизоваться пользователем "users"
      То Будет открыта страница "Домашняя страница"
      То На странице входа отображена ошибка "Ваша учётная запись создана и ожидает подтверждения администратора."
      То На главной странице будут отображены элементы "Войти"
      То На главной странице будут отображены элементы "Регистрация"
      И На главной странице не отображены элементы "Моя страница"