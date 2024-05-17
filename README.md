
The program will fetch the JSON data from the specified API endpoint, convert it to CSV format, and save the resulting CSV file as `data.csv` in the current working directory.

## Program Flow

1. The `fetchJsonData()` method sends a GET request to the API endpoint with basic authentication using the provided username and password. It reads the response and returns the JSON data as a string.

2. The `convertJsonToCsv(String jsonData)` method takes the JSON data as input and converts it to a CSV string. It first creates a header row with the keys from the first JSON object. Then, it iterates over the JSON array and appends each object's values as a new row in the CSV string.

3. The `saveDataToFile(String data)` method takes the CSV string and writes it to a file named `data.csv` in the current working directory.

4. In the `main()` method, the program calls `fetchJsonData()` to retrieve the JSON data, then `convertJsonToCsv(String jsonData)` to convert it to CSV, and finally `saveDataToFile(String data)` to save the CSV data to a file.

## Dependencies

- `org.json` library for parsing and manipulating JSON data.

## License

This project is licensed under the [MIT License](LICENSE).
