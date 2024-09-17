package app.page;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PeriodTracker implements Handler {

    @Override
    public void handle(Context context) throws Exception {

            String html = "<html>";
        
            // Head
            html += "<head>";
            html += "<meta charset='UTF-8'>";
            html += "<meta name='viewport' content='width=device-width, initial-scale=1.0'>";
            html += "<title>ClimateTrend Dashboard</title>";
            html += "<link rel='stylesheet' href='level3A(City).css'>";
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
                        <div class='search-title'>Display Region</div>
                        <div class='select-wrapper'>
                            <select class='select-boxfordisplay'>
                                <option>Select</option>
                            </select>
                            <div class='select-arrow'></div>
                        </div>
                    </div>
        
                    <div class='search-section'>
                        <div class='search-title'>City</div>
                        <div class='select-wrapper'>
                            <select class='select-boxforstate'>
                                <option>Select</option>
                            </select>
                            <div class='select-arrow'></div>
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
                        <div class='search-title'>Periods</div>
                        <div class='select-wrapper'>
                            <input type='text' id='period' name='period' placeholder='Enter'>
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
                                    <th>Year</th>
                                    <th>Period</th>
                                    <th>First year</th>
                                    <th>Last year</th>
                                    <th>Change</th>
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
