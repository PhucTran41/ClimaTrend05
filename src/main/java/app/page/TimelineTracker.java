package app.page;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class TimelineTracker implements Handler {

    @Override
    public void handle(Context context) throws Exception {


        
       String html = """
               
               """;
        context.html(html);
    }
    
}