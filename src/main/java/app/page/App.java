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
    }

}