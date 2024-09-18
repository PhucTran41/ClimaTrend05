package app.page;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

public class App {
    public static final int JAVALIN_PORT = 7001;
    public static final String CSS_DIR = "css/";
    public static final String IMAGES_DIR = "images/";

    public static void main(String[] args) {
        Javalin app = Javalin.create((config) -> {
            config.registerPlugin(new RouteOverviewPlugin("/help/routes"));
            config.addStaticFiles(CSS_DIR);
            config.addStaticFiles(IMAGES_DIR);
        }).start(JAVALIN_PORT);

        configureRoutes(app);

    }

    public static void configureRoutes(Javalin app) {
        app.get("/", new HomePage());
        app.get("/GlobalTracker", new GlobalTracker());
        app.get("/CityTracker", new CityTracker());
        app.get("/PeriodTracker", new PeriodTracker());
        app.get("/TimelineTracker", new TimelineTracker());

        app.post("/GlobalTracker", new GlobalTracker());
        app.post("/CityTracker", new CityTracker());
        app.post("/PeriodTracker", new PeriodTracker());
        app.post("/TimelineTracker", new TimelineTracker());



    }
}