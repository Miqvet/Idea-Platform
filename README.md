## Решение тестового задания

### Предусловия 
1. Задание было понято как нахождение пути из точки А в точку B без возможности множественного перехода по графу(т.е. нас интересуют только прямые рейсы)
2. Форматы всех полей были взяты из json файла.

### Ограничения
1. Для решения задачи поиска кратчайшего пути в неориентированном графе данное решение не подойдет

### Запуск
1. ```mvn clean compile package```
2. ```java -jar target/idea_platform-1.0-SNAPSHOT.jar tickets.json```

### Вывод
```
Минимальное время:
Борт: SU, Время в пути: 360 min
Борт: S7, Время в пути: 390 min
Борт: TK, Время в пути: 350 min
Борт: BA, Время в пути: 485 min

Среднее: 13960,000
Медиана: 13500,000
Разница: 460,000
```

### Возможные ошибки
1. В ходе тестов на платформе линукс(Debian) ядре 6.1.X была встречена ошибка команда mvn в первый раз не дала нужного результата(выдала ошибку компиляции), в повторный запуск все скомпилировалось