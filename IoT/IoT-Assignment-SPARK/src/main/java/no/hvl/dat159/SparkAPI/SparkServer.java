package no.hvl.dat159.SparkAPI;

import static spark.Spark.*;

import com.google.gson.*;
import no.hvl.dat159.Entities.Heat;
import no.hvl.dat159.Entities.Room;
import no.hvl.dat159.Entities.Temperature;

public class SparkServer {

    private static Room room = null;
    private static Temperature temp = null;
    private static Heat heat = null;

    public static void main(String[] args) {

        room = new Room(25, 30, 20);
        temp = room.getTemperature();
        heat = room.getHeat();
        port(8080);


        //get room info
        get("room/", (request, response) -> roomToJson());

        // update room info

        put("room/", (request, response) -> {

            Gson gson = new Gson();

            room = gson.fromJson(request.body(), Room.class);

            return roomToJson();

        });

        //get temperature info
        get("room/temperature", (request, response) -> tempToJson());

        //put temperature info
        put("room/temperature", (request, response) -> {

            Gson gson = new Gson();

            temp = gson.fromJson(request.body(), Temperature.class);

            return tempToJson();

        });

        //get heat state
        get("room/heat", (request, response) -> heatToJson());

        //update heat state
        put("room/heat", (request, response) -> {

            Gson gson = new Gson();

            heat = gson.fromJson(request.body(), Heat.class);

            return tempToJson();

        });


    }


    static String roomToJson() {

        Gson gson = new Gson();

        String jsonInString = gson.toJson(room);

        return jsonInString;
    }

    static String tempToJson() {
        Gson gson = new Gson();
        String jsonInString = gson.toJson(temp);
        return jsonInString;
    }

    static String heatToJson() {
        Gson gson = new Gson();
        String jasonInString = gson.toJson(heat);
        return jasonInString;
    }

}
