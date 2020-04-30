

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class BigDecMath {

	/*
	 * Quellen:
	 * - https://de.wikipedia.org/wiki/Logarithmus
	 * - https://de.wikipedia.org/wiki/Exponentialfunktion
	 * 
	 * Informationen zur Klasse:
	 * Werden nicht zugelassene Zahlen verwendet (wie 0^(-2)), wird null zurückgegeben!
	 */
	
	private BigDecimal zm1;
	private RoundingMode rm;

	public BigDecimal pow (BigDecimal x, BigDecimal y, int stellen) {	//Berechnet x^y auf eine belibige Anzahl an Stellen
		if (x.compareTo(BigDecimal.ZERO) == 0) {
			if (y.compareTo(BigDecimal.ZERO) == 1) {	//Nur wenn 0^(positiv) gilt, kann null zurückgegeben werden...
				return BigDecimal.ZERO;
			} else {
				return null;
			}
		} else if (y.compareTo(BigDecimal.ZERO) == 0) {	//Wenn y null ist, kommt immer eins raus
			return BigDecimal.ONE;
		}
		if (y.scale() == 0) {	//Bei natürlichen Exponenten kann die BigDecimal.pow(n) Methode verwendet werden --> ist exakt und multipliziert nur n mal
			return x.pow(y.intValue());
		}
		renew();	//Initialisiert alle nötigen Variablen
		return exp(y.multiply(ln(x, stellen + 2)), stellen);	//2 Sicheheitsstellen sind nötig, wegen des richtigen Übertrages bei der Multiplikation
	}

	public BigDecimal ln (BigDecimal zahl, int stellen) {	//Berechnet den ln(z)
		if (zahl.compareTo(BigDecimal.ZERO) == 0) {	//Dieser Wert ist undefiniert
			return null;
		}
		renew();	//Initialisiert alle nötigen Variablen
		BigDecimal lnZahl = new BigDecimal("0");	//Die zu konvergierende Zahl
		BigDecimal tempComp = zahl.subtract(BigDecimal.ONE).abs();	//Temp-Wert zum Vergleichen
		if (tempComp.compareTo(BigDecimal.ONE) <= 0) {	//Dann gilt |zahl| <= 1 und eine Reihe kann angewendet werden
			lnZahl = lnM1(zahl, stellen);
		} else {	//Hier muss eine andere Reihe für |x| > 1 angewendet werden
			lnZahl = zm1.multiply(lnM1(
					BigDecimal.ONE.add(
							zm1.multiply(
									zahl.subtract(BigDecimal.ONE).divide(zahl, stellen, rm)
									)
							), stellen));
		}
		return lnZahl;
	}

	private BigDecimal lnM1 (BigDecimal zahl, int stellen) {	//Berechnet den ln(z) für |x| <= 1
		if (zahl.compareTo(BigDecimal.ZERO) == 0) {	//Dieser Wert ist undefiniert --> nochmal zur Sicherheit aufgeführt, eigentlich unnötig, da private
			return null;
		}
		renew();	//Initialisiert alle nötigen Variablen
		stellen = stellen + 2;	//Sicherheitsstellen --> Übertragsproblem
		BigDecimal lnZahl = new BigDecimal("0");
		BigDecimal lnZahlLast = new BigDecimal("-1");	//Vergleichsvariable, wann fertig...
		int zz = 1;	//Zählvariable
		while (!(lnZahlLast.compareTo(lnZahl) == 0)) {
			lnZahlLast = lnZahl;
			lnZahl = lnZahl.add(
					m1Krit(zz + 1).multiply(
							zahl.subtract(BigDecimal.ONE).pow(zz).divide(BigDecimal.valueOf(zz), stellen, rm)
							)
					);
			zz++;
		}
		return lnZahl.setScale(stellen - 2, rm);
	}
	
	private BigDecimal m1Krit (int exp) {	//Wenn ein ungerader Exponent vorliegt, wird der Term negativ...
		if (exp % 2 == 0) {
			return BigDecimal.ONE;
		} else {
			return zm1;
		}
	}

	public BigDecimal exp (BigDecimal zahl, int stellen) {	//Berechnet den Wert von e^(zahl) auf "stellen" Nachkommastellen genau
		if (zahl.compareTo(BigDecimal.ZERO) == 0) {	//e^0 ergibt 1
			return BigDecimal.ONE;
		}
		renew();
		stellen = stellen + 2;	//Damit die letzte Stelle auch immer stimmt (Übertragsproblem)... --> zwei Stellen Sicherheit
		BigDecimal expZahl = new BigDecimal("0");
		BigDecimal expZahlLast = new BigDecimal("-1");	//Dürfen nicht gleich sein --> Schleife muss starten
		int i = 0;	//Zählvariable
		while (!(expZahlLast.compareTo(expZahl) == 0)) {	//Ist die Bedingung erfüllt, kommen eh keine neuen Stellen mehr hinzu, es verändert sich nicht mehr
			expZahlLast = expZahl;
			expZahl = expZahl.add(zahl.pow(i).divide(
					fak(i), stellen, rm
					));
			i++;
		}
		return expZahl.setScale(stellen - 2, rm);
	}

	public BigDecimal fak (long zahl) {	//Gibt die Fakultät zurück als BigDecimal
		renew();
		if (zahl == 0) {
			return BigDecimal.ONE;
		}
		BigInteger zz = BigInteger.valueOf(zahl);
		for (long i = zahl - 1; i > 1; i--) {
			zz = zz.multiply(BigInteger.valueOf(i));
		}
		return new BigDecimal(zz);
	}

	private void renew () {
		zm1 = new BigDecimal("-1");
		rm = RoundingMode.HALF_UP;
	}
}
