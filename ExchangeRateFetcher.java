import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExchangeRateFetcher {
    private static final String API_KEY = "f92f8e7a9c1931673b47bb35";

    public static double getExchangeRate(String fromCurrency, String toCurrency) throws IOException {
        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair/" + fromCurrency + "/" + toCurrency;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.readLine();
            reader.close();

            // Parse the JSON response and extract the exchange rate
            int startIndex = response.indexOf("\"rate\":") + 7;
            int endIndex = response.indexOf("}", startIndex);
            String rateString = response.substring(startIndex, endIndex);
            return Double.parseDouble(rateString);
        } else {
            throw new IOException("Error: " + responseCode);
        }
    }
}
