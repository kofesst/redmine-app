# Руководство пользователя
## Окно авторизации
При запуске приложения открывается окно авторизации, состоящее
из двух полей ввода: 
- Хост[^1] - **обязательно**
- API-ключа[^2] - **обязательно**

При наличии заранее созданных в приложении аккаунтов[^3] пользователь может выбрать один из них в
появившемся выпадающем списке, после чего данные в форме автоматически заполнятся.

Если пользователь ранее уже авторизовывался, но ещё не вышел из профиля, авторизация произойдёт автоматически.

При авторизации возможны некоторые ошибки:
- Хост не найден - некорректный IP-адрес сервера
- Неверный API-ключ - на данном хосте нет такого API-ключа
- Timeout - сервер не отвечает. Необходимо повторить попытку ещё раз или подождать 5 минут

После нажатия на кнопку "Отправить запрос" данные отправятся на сервер для подтверждения.

В случае, если данные для входа новые, приложение предложит сохранить их в аккаунт[^3].

## Главное окно
После успешной авторизации в верхней панели с названием текущего окна с правой стороны появится кнопка
выхода из текущего профиля, по нажатии на которую пользователя перекидывает на
[окно авторизации](#окно-авторизации), а текущая сессия очищается.

Также пользователю становится доступен основной функционал
приложения:
- [Список задач](#окно-списка-задач)
- [Список проектов](#окно-списка-проектов)
- [Список аккаунтов](#окно-списка-аккаунтов)

## Окно списка задач
В верхней части окна есть два раздела:
- Требует внимания - задачи со статусом "Надо сделать" или "В работе" с исполнителем в лице текущего пользователя,
или задачи со статусом "Надо проверить" с автором в лице текущего пользователя
- Мои задачи - открытые задачи с автором в лице текущего пользователя

При необходимости ниже разделов есть [панель сортировки и фильтрации задач](#панель-сортировки).

По нажатии на любую задачу из списка откроется [окно деталей задачи](#окно-деталей-задачи).

## Окно деталей задачи
В данном окне представлена основная информация о задаче:
- Название
- Описание
- Автор
- Исполнитель
- Статус
- Приоритет
- Оценочное время
- Затраченное время
- Дата начала
- Дата обновления
- Дедлайн
- Список вложений
- Журнал изменений

По нажатии на любое вложение из списка вложений откроется браузерное окно загрузки вложения на устройство.

В правом нижнем углу окна находится кнопка изменения задачи, по нажатии на которую откроется
[окно изменения задачи](#окно-сохранения-задачи).

## Окно сохранения задачи
Данное окно состоит из следующих полей ввода:
- Название задачи - **обязательно**
- Трекер задачи - **обязательно**
- Приоритет - **обязательно**
- Исполнитель
- Статус - **обязательно**
- Дедлайн
- Описание
- Описание изменения
- Прикрепленные вложения

По нажатии на кнопку "Добавить вложение", приложение запросит разрешение на
доступ к хранилищу устройства, если оно ещё не получено. После получения разрешения,
откроется системное окно выбора файла, после выбора которого, он добавится в список
прикреплённых вложений, которое можно открепить, нажав на соответствующую кнопку.

По нажатии на кнопку "Сохранить":
- Если до этого было выбрано изменение задачи, задача изменится
- Если до этого было выбрано добавление задачи в проект, новая задача добавится

## Окно списка проектов
Данное окно состоит из списка проектов, доступных текущему пользователю.

По нажатии на любой проект из списка откроется [окно деталей проекта](#окно-деталей-проекта).

## Окно деталей проекта
В данном окне находится список задач проекта, а также [панель сортировки и фильтрации](#панель-сортировки).

По нажатии на любую задачу из списка откроется [окно деталей задачи](#окно-деталей-задачи).

В правом нижнем углу находится кнопка добавления новой задачи, по нажатии на которую
откроется [окно добавления задачи](#окно-сохранения-задачи).

## Окно списка аккаунтов
Данное окно состоит из списка аккаунтов[^3], созданных пользователем.

По нажатии на любой из аккаунтов из списка откроется [окно изменения аккаунта](#окно-сохранения-аккаунта).

В правом нижнем углу находится кнопка добавления аккаунта, по нажатии на которую также откроется 
[окно добавления аккаунта](#окно-сохранения-аккаунта).

## Окно сохранения аккаунта
Данное окно состоит из следующих полей:
- Название аккаунта - **обязательно**
- Хост[^1] - **обязательно**
- API-ключ[^2] - **обязательно**

По нажатии на кнопку "Сохранить":
- Если до этого было выбрано изменение аккаунта, аккаунт изменится
- Если до этого было выбрано добавление аккаунта, новый аккаунт добавится

Также, если до этого было выбрано изменение аккаунта, ниже кнопки "Сохранить"
будет кнопка "Удалить", по нажатии на которую выбранный аккаунт удалится.

## Панель сортировки
Сортировка производится по ID задачи или по приоритету задачи (по возрастанию или убыванию).

Фильтрация производится по трекеру или по статусу. В случае, если фильтрация не нужна, можно
выбрать параметр "Нет", чтобы фильтрация не производилась.

[^1]: Хост - корневой IP-адрес сервера с веб-приложением "Redmine".
[^2]: API-ключ - уникальный для каждого пользователя ключ доступа, который можно увидеть в информации о своём профиле веб-приложения "Redmine".
[^3]: Аккаунт хранит в себе хост и API-ключ. Служит для упрощения подстановки данных для входа.
