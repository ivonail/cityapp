package com.cityevents;

/**
 *
 * @author Ivona
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class App {

    static ArrayList<City> cities = new ArrayList<>();

    public static JSONArray makeJsonObjectAsList(String urlStr, String request) throws MalformedURLException, IOException, ParseException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        StringBuffer response;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        JSONParser jp = new JSONParser();
        JSONObject jo = (JSONObject) jp.parse(response.toString());
        JSONArray ja = (JSONArray) jo.get(request);
        return ja;
    }

    public static int getCityNumber() {
        System.out.println("_____________________________\n");
        System.out.println("To see upcoming events you can use the number before the city name.");
        int number = 0;
        while ((number <= 0) || number > cities.size()) {
            System.out.println("Enter the valid number [" + 1 + "-" + cities.size() + "]:");
            Scanner sc = new Scanner(System.in);
            number = sc.nextInt();
        }
        return number;
    }

    public static String removeHTMLtags(String s) {
        String pom = "";
        if (s != null) {
            pom = s.replaceAll("<[^>]*>", " ");
        } else {
            pom = "There is no description for this event.";
        }
        return pom;
    }

    public static void getEvents(int cityNumber) throws IOException, MalformedURLException, ParseException {
        JSONArray ja = new JSONArray();
        for (City city : cities) {
            if (cityNumber == city.getId()) {
                ja = makeJsonObjectAsList("https://api.meetup.com/find/upcoming_events?photo-host=public&page=20&sig_id=264282164&radius=7&lon=" + city.getLon() + "&lat=" + city.getLat() + "&sig=52283ea81c2b5f4a1296d923ea886e21fc4b8ca6", "events");
                if (ja.size() == 0) {
                    System.out.println("There is no events for this city, sorry.");
                } else {
                    System.out.println("\nUpcoming events for " + city.getCityName() + " are:");
                    for (int i = 0; i < ja.size(); i++) {
                        JSONObject jo1 = (JSONObject) ja.get(i);
                        System.out.println((i + 1) + ". " + (String) jo1.get("name") + ", datum: " + jo1.get("local_date") + ", vreme: " + jo1.get("local_time") + "\nDescription: " + removeHTMLtags((String) jo1.get("description")) + "\n");
                    }
                }
            }
        }
    }

    public static void makeCityList() throws IOException, MalformedURLException, ParseException {
        JSONArray jsonCity = new JSONArray();

        jsonCity = makeJsonObjectAsList("https://api.meetup.com/2/cities?country=rs&offset=0&format=json&photo-host=public&page=20&radius=50&order=size&desc=false&sig_id=264282164&sig=b47c78b285c7c9bcfc011466128e4dffc8461e0c", "results");
        for (int i = 0; i < jsonCity.size(); i++) {
            JSONObject json = (JSONObject) jsonCity.get(i);
            cities.add(new City(i + 1, (String) json.get("city"), (Double) json.get("lat"), (Double) json.get("lon")));

        }

    }

    public static void main(String[] args) throws IOException, MalformedURLException, ParseException {

        makeCityList();
        for (City city : cities) {
            System.out.println(city.getId() + " " + city.getCityName());
        }
        int number = getCityNumber();
        getEvents(number);

    }
}
