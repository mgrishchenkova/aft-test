#language: ru

Функция: Получение пользователей. Пользователь без прав администратора

  Предыстория:
    Если В системе заведен пользователь "user1" с параметрами:
      | admin  | false |
      | status | 1     |
    И У пользователя "user1" есть доступ к API и ключ API
    И В системе заведен пользователь "user2" с параметрами:
      | admin  | false |
      | status | 1     |

  Сценарий: Получение пользователей. Пользователь без прав администратора
    Если Отправить запрос GET на получение пользователя2 "user1"  используя ключ API пользователя1 "user1"
    То Статус код ответа 200
    И Тело ответа содержит данные пользователя1 "user1"
    И В ответе присутствуют поля с параметрами "admin": false, "api_key": "ключ API из предусловия"

    Если Отправить запрос GET на получение пользователя2 "user2"  используя ключ API пользователя1 "user1"
    То Статус код ответа 200
    И Тело ответа содержит данные пользователя2 "user2"
    И В ответе отсутствуют поля с параметрами admin и api_key






