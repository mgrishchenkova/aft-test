#language: ru
Функция: Проверка видимости проекта

  Предыстория:
    Пусть В системе заведен пользователь "adminPrivateProject" с параметрами:
      | admin  | true |
      | status | 1    |
    И В системе заведен проект "project" с параметрами:
      | is_public | false |
    И Открыт браузер на главной странице
    И На странице "Заголовок" нажать на элемент "Войти"

  @ui
  Сценарий: Проверка видимости приватного проекта для администратора
    Если Авторизоваться пользователем "adminPrivateProject"
    То Будет открыта страница "Домашняя страница"
    И На странице "Заголовок" нажать на элемент "Проекты"
    То Будет открыта страница "Проекты"
    И Отображается проект "project"
