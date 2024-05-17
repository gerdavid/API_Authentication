import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonToCsv {
    private static final String API_ENDPOINT = "http://test.hiskenya.org/api/analytics.json?dimension=dx%3AotgQMOXuyIn%3BM4Rzp)ew1Im%3ByQFyy
    QBhXQf&dimension=pe%3A202301%3B202302%3B202303%3B202303%3B202304%3B202305%3B202306%3B202307%3B202308%3B202309%3B202310%3B2023012
    &tabletLayout=true&columns=dx&rows=pe&skiRounding=false&completeOnly=false&filter=ou%3AUSER_ORGUNIT";
    private static final String USERNAME = "programmingtest";
    private static final String PASSWORD = "Kenya@2040";
    private static final String OUTPUT_FILE = "data.csv";

    public static void main(String[] args) {
        try {
            String jsonData = fetchJsonData();
            String csvData = convertJsonToCsv(jsonData);
            saveDataToFile(csvData);
            System.out.println("CSV file saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String fetchJsonData() throws IOException {
        URL url = new URL(API_ENDPOINT);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        String authString = USERNAME + ":" + PASSWORD;
        String authEncoded = Base64.getEncoder().encodeToString(authString.getBytes());
        connection.setRequestProperty("Authorization", "Basic " + authEncoded);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            System.out.println("Error: " + responseCode);
            return null;
        }
    }

    private static String convertJsonToCsv(String jsonData) {
        StringBuilder csvBuilder = new StringBuilder();
        JSONArray jsonArray = new JSONArray(jsonData);

        // Append header row
        JSONObject firstObject = jsonArray.getJSONObject(0);
        String[] keys = JSONObject.getNames(firstObject);
        for (int i = 0; i < keys.length; i++) {
            csvBuilder.append(keys[i]);
            if (i < keys.length - 1) {
                csvBuilder.append(",");
            }
        }
        csvBuilder.append("\n");

        // Append data rows
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            for (int j = 0; j < keys.length; j++) {
                String value = jsonObject.getString(keys[j]);
                csvBuilder.append(value);
                if (j < keys.length - 1) {
                    csvBuilder.append(",");
                }
            }
            csvBuilder.append("\n");
        }

        return csvBuilder.toString();
    }

    private static void saveDataToFile(String data) throws IOException {
        FileWriter writer = new FileWriter(OUTPUT_FILE);
        writer.write(data);
        writer.close();
    }
}
