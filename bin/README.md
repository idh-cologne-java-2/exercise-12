Softwaretechnologie: Java 2

# Übung 12


Klonen Sie dieses Repository direkt in Eclipse und importieren Sie das Maven-Projekt. Legen Sie einen neuen Branch an, den Sie nach Ihrem GitHub-Benutzernamen benennen.


## Aufgabe 1

Sie finden (mal wieder) die Klasse `Document` im aktuellen Projekt. Die kennen Sie bereits aus früheren Projekten, und sie kann benutzt werden um eine Daten einzulesen und zu tokenisieren. Als Übungsmaterial gibt es außerdem mal wieder Bram Stoker's Dracula. Implementieren Sie die Methode `printStats(File f)`. Diese soll eine neue CSV-Datei anlegen, die die folgenden basalen statistischen Daten über den Text enthält:

- Anzahl der Wörter
- Anzahl der Types (also verschiedener Wörter)
- Anzahl der Wörter die kürzer sind als 5 Zeichen
- Häufigkeit des Wortes "blood"
- Das am häufigsten vorkommende Wort
- Das am häufigsten vorkommende großgeschriebene Wort

Diese Informationen sollen in der CSV-Datei jeweils in Spalten stehen, also ungefähr so:

```
Anzahl der Wörter,Anzahl Types,...
17,10,
```

Verwenden Sie zur Berechnung der einzelnen Daten die Stream-API!


## Aufgabe 2
Das Semester neigt sich ja allmählich dem Ende zu, daher gibt es keine neue Programmieraufgabe. Gehen Sie stattdessen Ihre Notizen und Aufzeichnungen durch und stellen Sie sicher, dass Sie alles (wichtige) verstanden haben. Nutzen Sie die kommenden zwei Sitzungen um ggf. Lücken zu füllen und nochmal nachzufragen.

----

Wenn Sie fertig sind, committen Sie alle Ihre Änderungen, und pushen Sie den neuen Branch auf das remote namens `origin` (= GitHub). 