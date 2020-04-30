# BigDecMath
Dies soll eine kleine library sein, die der Java-Klassen "BigInteger" und "BigDecimal" weitere mathematische Funktionen hinzufügt. 

## Methoden
Die Klasse besitzt (bis jetzt) vier Methoden, die es ermöglichen, mit "BigDecimal" (und somit natürlich auch mit "BigInteger"...) Berechnungen durchzuführen.

* **`.pow(BigDecimal x, BigDecimal y, int stellen)`**  
Diese Methode berechnet im Prinzip x^(y) und das auf `stellen` Stellen genau. Der Rückgabewert ist ein Objekt vom Typ "BigDecimal" mit der Länge `stellen`.

* **`.ln(BigDecimal zahl, int stellen)`**  
Diese Methode berechnet den natürlichen Logarithmus von `zahl` auf `stellen` Stellen genau (`ln(zahl)`). Der Rückgabewert ist ein Objekt vom Typ "BigDecimal" mit der Länge `stellen`.

* **`.exp(BigDecimal zahl, int stellen)`**  
Diese Methode berechnet `e^(zahl)` auf `stellen` Stellen genau. Auch hier ist der Rückgabewert ein Objekt vom Typ "BigDecimal".

* **`.fak(long zahl)`**  
Diese Methode berechnet die Fakultät von `zahl` und gibt das Ergebnis als "BigDecimal" zurück. Natürlich ist das nicht die sinnvollste Anwendung, ich hatte genau dese Methode jedoch einmal für ein Projekt bentötigt.

##Warnung
Alle Methoden sollten einwandfrei funktionieren, jedoch handelt es sich mathematisch um Summen, die so noch keineswegs für die Berechnung mit einem Programm optimiert wurden. Die Methoden liefern zwar, benötigen jedoch etwas Zeit, zumal "BigDecimal" nicht die schnellste Klasse ist.
