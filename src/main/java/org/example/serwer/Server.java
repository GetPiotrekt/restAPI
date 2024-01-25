package org.example.serwer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class Server {
    public static void main(String[] args) {
        try {
            // Utwórz gniazdo serwera nasłuchujące na porcie 13612 (mój poprzedni numer indeksu;))
            ServerSocket serverSocket = new ServerSocket(13612);
            System.out.println("\nSerwer nasłuchuje na porcie 13612...");

            while (true) {
                // Zaakceptuj połączenie od klienta
                Socket clientSocket = serverSocket.accept();
                // Utwórz nowy wątek obsługujący połączenie
                Thread clientThread = new Thread(() -> {
                    try (
                            // Strumienie do odczytu i zapisu danych z klienta
                            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
                    ) {
                        // Odczytaj żądanie od klienta
                        String request = reader.readLine();
                        System.out.println("\nSerwer otrzymał żądanie: " + request);

                        // Pobierz ścieżkę z żądania klienta
                        String path = getPathFromRequest(request);

                        // Utwórz URL API Pokemon na podstawie ścieżki z żądania klienta
                        String pokemonApiUrl = "https://pokeapi.co/api/v2/pokemon/ditto/";
                        String pokemonApiResponse = fetchDataFromApi(pokemonApiUrl);

                        // Wyświetl określone informacje na jednej linii
                        String formattedResponse = formatApiResponse(pokemonApiResponse);
                        String response = "Wykonywane: " + request + "\n\n" + formattedResponse;

                        // Wysyłanie odpowiedzi do klienta
                        writer.write(response);
                        writer.flush();
                        System.out.println("Serwer wysłał odpowiedź: " + response);

                    } catch (IOException | URISyntaxException e) {
                        // Obsłuż wyjątki związane z komunikacją sieciową
                        e.printStackTrace();
                    }
                });
                // Uruchom nowy wątek obsługujący połączenie
                clientThread.start();
            }
        } catch (IOException e) {
            // Obsłuż wyjątek związany z gniazdem serwera
            e.printStackTrace();
        }
    }

    // Metoda do pobierania danych z API na podstawie URL
    private static String fetchDataFromApi(String apiUrl) throws IOException {
        // Utwórz połączenie HTTP
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            // Sprawdź, czy odpowiedź z serwera jest OK (HTTP 200)
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader apiReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    // Odczytaj odpowiedź z API
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = apiReader.readLine()) != null) {
                        response.append(line);
                    }
                    return response.toString();
                }
            } else {
                // Rzuć wyjątek w przypadku niepowodzenia zapytania do API
                throw new IOException("Kod błędu: " + connection.getResponseCode());
            }
        } finally {
            // Zawsze zamknij połączenie, nawet jeśli wystąpił wyjątek
            connection.disconnect();
        }
    }

    // Metoda do formatowania odpowiedzi z API
    private static String formatApiResponse(String apiResponse) {
        try {
            // Parsuj odpowiedź JSON
            JsonObject jsonObject = JsonParser.parseString(apiResponse).getAsJsonObject();

            // Pobierz potrzebne informacje
            String name = jsonObject.get("name").getAsString();
            String height = jsonObject.get("height").getAsString();
            String weight = jsonObject.get("weight").getAsString();
            String baseExperience = jsonObject.get("base_experience").getAsString();

            // Sformatuj dane na jednej linii
            return "Nazwa: " + name + ", Wysokość: " + height + "m, Waga: " + weight + "kg, Base Experience: " + baseExperience;
        } catch (JsonSyntaxException | IllegalStateException e) {
            // Obsłuż błędy parsowania JSON
            e.printStackTrace();
            return "Nie udało się odczytać danych";
        }
    }

    // Dodana nowa metoda do pobierania ścieżki z żądania klienta
    private static String getPathFromRequest(String request) throws URISyntaxException {
        // Utwórz obiekt URI na podstawie ścieżki w żądaniu HTTP
        URI uri = new URI(request.split(" ")[1]);
        return uri.getPath();
    }
}