package app.page;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class CityTracker implements Handler {

    public static final String URL = "/CityTracker";

    @Override
    public void handle(Context context) throws Exception {

        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>City Tracker</title>
                    <link rel='stylesheet' type='text/css' href='common.css'>
                </head>
                <body>
                    <h1>City Tracker</h1>
                    <p>This page will show temperature trends for different cities.</p>
                    <!-- Add your actual content here -->
                </body>
                </html>
                """;

        context.html(html);
    }
}
