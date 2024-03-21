package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        int delimitador = 3000;

        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

        String json = new String(Files.readAllBytes(Paths.get("src/file/all_tracks.json")));

        Scrobble[] scrobble = gson.fromJson(json, Scrobble[].class);

        ArrayList<Scrobble> newScrobbles = new ArrayList<>();

        for (int i = 0; i < scrobble.length; i++) {
            if (i % delimitador == 0) {
                // criar arquivo json
                String filename = "resultados/output_" + (i / delimitador) + ".json";
                String jsonOutput = gson.toJson(newScrobbles);
                try (FileWriter writer = new FileWriter(filename)) {
                    writer.write(jsonOutput);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                newScrobbles.clear();
            }
            newScrobbles.add(scrobble[i]);
        }
        // json
        String filename = "resultados/output_" + (scrobble.length / delimitador + 1) + ".json";
        String jsonOutput = gson.toJson(newScrobbles);
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("terminado :)");

    }

    @Data @AllArgsConstructor @NoArgsConstructor
    public static class Scrobble {
        public String trackName, artistName, albumName;
    }
}