package app.page;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class HomePage implements Handler {

    @Override
    public void handle(Context context) throws Exception {


        
       String html = "<html><body><h1>Home Page</h1></body></html>";
        context.html(html);
    }
    
}
