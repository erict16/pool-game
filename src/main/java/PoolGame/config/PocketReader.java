package PoolGame.config;

import PoolGame.GameManager;
import PoolGame.objects.Pocket;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PocketReader implements Reader{
    @Override
    public void parse(String path, GameManager gameManager) {
        JSONParser parser = new JSONParser();
        ArrayList<Pocket> pockets = new ArrayList<>();
        try {
            Object object = parser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) object;
            JSONObject jsonTable = (JSONObject) jsonObject.get("Table");
            JSONArray jsonPockets = (JSONArray)  jsonTable.get("pockets");

            for (Object obj : jsonPockets) {
                // Read JSON File and get the configurations of the pockets
                JSONObject jsonPocket = (JSONObject) obj;
                double radius = (double) jsonPocket.get("radius");
                double positionX = (double) ((JSONObject) jsonPocket.get("position")).get("x");
                double positionY = (double) ((JSONObject) jsonPocket.get("position")).get("y");

                pockets.add(new Pocket(positionX - 50, positionY - 25, radius));
            }
            // Initial the pockets
            gameManager.setPockets(pockets);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
