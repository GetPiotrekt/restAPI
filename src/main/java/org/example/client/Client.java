package org.example.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        // Utwórz gniazdo (socket) i strumienie do komunikacji z serwerem
        try (Socket socket = new Socket("localhost", 13612); // Mój poprzedni numer indeksu;)
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            // Zapytanie o informacje o Pokemonie "Ditto" z API Pokemon
            sendRequest(writer, "GET /pokemon/ditto HTTP/1.1");

            // Odbierz i wyświetl odpowiedź od serwera
            String response = receiveResponse(reader);
            System.out.println("Odpowiedź od serwera: " + response);

        } catch (IOException e) {
            // Obsłuż wyjątki związane z komunikacją sieciową
            e.printStackTrace();
        }
    }

    // Metoda do wysyłania żądania do serwera
    private static void sendRequest(BufferedWriter writer, String request) throws IOException {
        writer.write(request + "\n");  // Zapisz żądanie do strumienia wyjściowego
        writer.flush();  // Wymuś przesłanie danych
        System.out.println("\nWysłano zapytanie do serwera: " + request);
    }

    // Metoda do odbierania odpowiedzi od serwera
    private static String receiveResponse(BufferedReader reader) throws IOException {
        StringBuilder response = new StringBuilder();
        String line;
        // Odczytuj linie odpowiedzi od serwera
        while ((line = reader.readLine()) != null) {
            response.append(line).append("\n");  // Dodaj odczytaną linię do odpowiedzi
        }
        return response.toString();  // Zwróć pełną odpowiedź serwera
    }
}