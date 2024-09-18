package app.page;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class TimelineTracker implements Handler {

    public static final String URL = "/TimelineTracker";


    @Override
    public void handle(Context context) throws Exception {

            String html = "<html>";
        
            // Head
            html += "<head>";
            html += "<meta charset='UTF-8'>";
            html += "<meta name='viewport' content='width=device-width, initial-scale=1.0'>";
            html += "<title>ClimateTrend Dashboard</title>";
            html += "<link rel='stylesheet' href='Level2B.css'>";
            html += "</head>";
        
            // Body
            html += "<body>";
        
            // Header
            html += """
                <div class='header'>
                    <div class='logo'>
                        <div class='logo-icon'></div>
                    </div>
                    <div class='logo-text'>ClimaTrend</div>
                    <nav>
                        <a href='#' class='nav-item'>Home</a>
                        <a href='#' class='nav-item'>Global Tracker</a>
                        <a href='#' class='nav-item'>City Tracker</a>
                        <a href='#' class='nav-item'>TimeLine Tracker</a>
                        <a href='#' class='nav-item'>Periods Tracker</a>
                    </nav>
                </div>
            """;
        
            // Shadow
            html += "<div class='shadow'></div>";
        
            // Search Panel
            html += """
                <div class='search-panel'>
                    <div class='search-section'>
                        <div class='search-title'>Country</div>
                        <div class='select-wrapper'>
                            <select class='select-boxforcountry'>
                                <option>Select</option>
                            </select>
                            <div class='select-arrow'></div>
                        </div>
                    </div>
        
                    <div class='search-section'>
                        <div class='search-title'>City/State</div>
                        <div class='select-wrapper'>
                            <form class='select-boxforcity'>
                                <input type='checkbox' id='CitySelect' name='CitySelect' value='City'>
                                <label for='CitySelect'> City</label>
                                <input type='checkbox' id='CountrySelect' name='CountrySelect' value='Country'>
                                <label for='CountrySelect'> Country</label>
                            </form>
                        </div>
                    </div>
        
                    <div class='search-section'>
                        <div class='search-title'>Start Year</div>
                        <div class='select-wrapper'>
                            <select class='select-box'>
                                <option>Select</option>
                            </select>
                            <div class='select-arrow'></div>
                        </div>
                    </div>
        
                    <div class='search-section'>
                        <div class='search-title'>End Year</div>
                        <div class='select-wrapper'>
                            <select class='select-box'>
                                <option>Select</option>
                            </select>
                            <div class='select-arrow'></div>
                        </div>
                    </div>
        
                    <div class='search-section'>
                        <div class='search-title'>Output</div>
                        <div class='select-wrapper'>
                            <select class='select-box'>
                                <option>Select</option>
                            </select>
                            <div class='select-arrow'></div>
                        </div>
                    </div>
        
                    <div class='search-section'>
                        <div class='search-title'>Statistic</div>
                        <div class='select-wrapper'>
                            <select class='select-box'>
                                <option>Select</option>
                            </select>
                            <div class='select-arrow'></div>
                        </div>
                    </div>
                </div>
            """;
        
            // Search Button
            html += "<button class='search-button'>Search</button>";
        
            // Results Container
            html += """
                <div class='results-container'>
                    <div class='results-inner'>
                        <table>
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>City</th>
                                    <th>Latitude</th>
                                    <th>Longitude</th>
                                    <th>first year temp</th>
                                    <th>last year temp</th>
                                    <th>change</th>
                                    <th>avg temp</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>city name</td>
                                    <td>null</td>
                                    <td>null</td>
                                    <td>null</td>
                                    <td>null</td>
                                    <td>null</td>
                                    <td>null</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            """;
        
            html += "</body>";
            html += "</html>";
        
            // Output HTML
            context.html(html);
        }
        
    
}