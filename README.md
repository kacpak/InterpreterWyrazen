# Interpreter wyrażeń #

Celem zadania jest wykonanie interpretera wyrażeń, które służą do przeszukiwania i selekcjonowania rekordów ze zbioru złożonego z rekordów o ośmiu polach typu całkowitego nazwanych kolejno a, b, c, d, e, f, g, h.

Wyrażenia powinny składać się z nawiasów, operatorów logicznych ‘and’ i ‘or’ oraz operatorów porównania arytmetycznego =, <, <=, >=, >, !=. Przykładowe wyrażenie wybierające rekordy ze zbioru to:

(a = 1 and b = 2) or (a = 2 and b = 1)

W wyrażeniach mogą być użyte następujące znaki białe: <spacja>, <tab>. Zakłada się, że wyrażenie interpretowane jest do znak końca linii <lf> lub <cr><lf>.

Program powinien być napisany beż użycia zewnętrznych narzędzi i komponentów służących do generacji procedur rozbioru wg opisu gramatyki języka. Dodatkowo powinien zostać wyodrębniony fragment wykonujący rozbiór wyrażenia do postaci drzewa, a następnie drzewo powinno być zaprezentowane na ekranie.

Po zaprezentowaniu drzewa i potwierdzeniu kontynuacji przez użytkownika na ekranie powinny zostać zaprezentowane wybrane rekordy.