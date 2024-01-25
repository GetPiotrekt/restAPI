-----===== Wybrany Serwis API Pokemon =====-----

1. PokeAPI
   - https://pokeapi.co/api/v2/pokemon/ditto/ ((https://documenter.getpostman.com/view/8854915/Szf7znEe#e155b1e0-f06c-4ac6-a3d2-f4442e7d3911))
   - Opis: PokeAPI to darmowe i otwarte API dostarczające informacje o Pokemonach (co prawda nie jestem wielkim fanem, ale wpadło w oko, więc uznałem za ciekawe)


-----===== Wykorzystywane Metody =====-----

/// Serwer \\\

1. GET /pokemon/{name}
   - Opis: Pobiera informacje o konkretnym Pokemonie na podstawie jego nazwy. Na stronie jako przykład został podany Ditto, więc na nim wszystko robiłem
   - Przykład żądania: "GET /pokemon/ditto" 
   - Wynik: Informacje o Pokemonie w formie JSON


/// Klient \\\

1. sendRequest(BufferedWriter writer, String request)
   - Opis: Wysyła żądanie do serwera
   - Parametry: "BufferedWriter writer" - strumień do zapisu danych, "String request" - treść żądania
   - Wynik: Wysłanie żądania do serwera

2. receiveResponse(BufferedReader reader)
   - Opis: Odbiera odpowiedź od serwera
   - Parametr: "BufferedReader reader" - strumień do odczytu danych
   - Wynik: Odpowiedź od serwera w formie String



-----===== Protokoły Sieciowe =====-----

1. HTTP (Hypertext Transfer Protocol)
   - Opis: Protokół do przesyłania danych w internecie. Wykorzystywany do komunikacji między klientem a serwerem

2. HTTPS (Hypertext Transfer Protocol Secure)
   - Opis: Zabezpieczona wersja protokołu HTTP, używająca szyfrowania SSL/TLS. Zapewnia bezpieczną komunikację



-----===== Publiczne Interfejsy =====-----

/// Serwer \\\

1. public class Server
   - Metoda Main: Uruchamia serwer nasłuchujący na porcie 13612

2. public static String fetchDataFromApi(String apiUrl) throws IOException
   - Opis: Pobiera dane z API na podstawie podanego URL
   - Parametr: "apiUrl" - Adres URL API
   - Wynik: Odpowiedź z API w formie String

3. public static String formatApiResponse(String apiResponse)
   - Opis: Formatuje odpowiedź z API na czytelny dla użytkownika tekst
   - Parametr: "apiResponse" - Odpowiedź z API w formie JSON
   - Wynik: Sformatowane informacje o Pokemonie

     
/// Klient \\\

1. public class Client
   - Metoda Main: Uruchamia klienta łączącego się z serwerem na localhost:13612

2. public static void sendRequest(BufferedWriter writer, String request) throws IOException
   - Opis: Wysyła żądanie do serwera
   - Parametry: "writer" - Strumień do zapisu danych, "request" - Treść żądania
   - Wynik: Wysłanie żądania do serwera

3. public static String receiveResponse(BufferedReader reader) throws IOException
   - Opis: Odbiera odpowiedź od serwera
   - Parametr: "reader" - Strumień do odczytu danych
   - Wynik: Odpowiedź od serwera w formie String



-----===== Testy (powiadomienia o błędach) =====-----

1. Testowanie Połączenia
   - Opis: Sprawdzenie czy klient może poprawnie połączyć się z serwerem

2. Testowanie Metody GET /pokemon/{name}
   - Opis: Sprawdzenie czy serwer poprawnie obsługuje żądania o informacje o konkretnym Pokemonie

4. Testowanie Błędów (ogólnie)
   - Opis: Sprawdzenie, czy serwer prawidłowo obsługuje błędy, takie jak niepoprawne żądania

