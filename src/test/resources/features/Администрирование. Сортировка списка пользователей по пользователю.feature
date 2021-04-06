#language: ru

Функция: Проверка сортировки списка пользователей по пользователю

  Предыстория:
    Пусть В системе заведен пользователь "admin" с параметрами:
      | admin  | true |
      | status | 1    |
    И Открыт браузер на главной странице
    И В системе заведен пользователь "testUser1" с параметрами:
      | admin  | false |
      | status | 1     |
    И В системе заведен пользователь "testUser2" с параметрами:
      | admin  | false |
      | status | 1     |
    И Авторизоваться пользователем "admin"

  Сценарий: Администрирование. Сортировка списка пользователей по пользователю
    Если На странице "Заголовок" нажать на элемент "Администрирование"
    То Будет открыта страница "Администрирование"
    И На странице "Администрирование" нажать на элемент "Пользователи"
    То На странице "Администрирование" отображается элемент "Таблица пользователей"
    И Таблица с пользователями отсортирована по "логину" пользователей по "возрастанию"
    Если На странице "Администрирование" нажать на элемент "Пользователь"
    То Таблица с пользователями отсортирована по "логину" пользователей по "убыванию"