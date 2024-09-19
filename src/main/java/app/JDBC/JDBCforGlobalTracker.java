package app.JDBC;


public class JDBCforGlobalTracker {

    public static final String DATABASE = "jdbc:sqlite:database/Movies.db";

    public JDBCforGlobalTracker() {
        System.out.println("Created JDBC Connection Object");
    
    String html = "<html>";

// Head
html = html + "<head>";
html = html + "<meta charset='UTF-8'>";
html = html + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>";
html = html + "<title>ClimateTrend Dashboard</title>";
html = html + "<link rel='stylesheet' href='Level2A.css'>";
html = html + "</head>";

// Body
html = html + "<body>";
html = html + "<div class='container'>";

// Header
html = html + "<div class='header'>";
html = html + "<div class='logo'>";
html = html + "<img src='images/World_Tracker_Logo__1_-removebg-preview.png' alt='WorldChanges Logo'>";
html = html + "</div>";
html = html + "<div class='header-elements'>";
html = html + "<a href='/LandingPage'>Home</a>";
html = html + "<a href='/GlobalTracker'>Global Tracker</a>";
html = html + "<a href='/CityTracker'>City Tracker</a>";
html = html + "<a href='/TimeLineTracker'>Timeline Tracker</a>";
html = html + "<a href='/PeriodTracker'>Periods Tracker</a>";
html = html + "</div>";
html = html + "</div>";

html = html + "<div class='shadow'></div>";

// Search Panel
html = html + "<div class='search-panel'>";
html = html + "<div class='search-section'>";
html = html + "<div class='search-title'>Display Region</div>";
html = html + "<div class='select-wrapper'>";
html = html + "<select class='select-boxfordisplay'><option>Select</option></select>";
html = html + "<div class='select-arrow'></div>";
html = html + "</div></div>";

html = html + "<div class='search-section'>";
html = html + "<div class='search-title'>Start Year</div>";
html = html + "<div class='select-wrapper'>";
html = html + "<select class='select-boxfordisplay'><option>Select</option></select>";
html = html + "<div class='select-arrow'></div>";
html = html + "</div></div>";

html = html + "<div class='search-section'>";
html = html + "<div class='search-title'>End Year</div>";
html = html + "<div class='select-wrapper'>";
html = html + "<select class='select-boxfordisplay'><option>Select</option></select>";
html = html + "<div class='select-arrow'></div>";
html = html + "</div></div>";

html = html + "<div class='search-section'>";
html = html + "<div class='search-title'>Order</div>";
html = html + "<div class='select-wrapper'>";
html = html + "<select class='select-boxfordisplay'><option>Select</option></select>";
html = html + "<div class='select-arrow'></div>";
html = html + "</div></div>";

html = html + "<div class='search-section'>";
html = html + "<div class='search-title'>Output</div>";
html = html + "<div class='select-wrapper'>";
html = html + "<select class='select-boxfordisplay'><option>Select</option></select>";
html = html + "<div class='select-arrow'></div>";
html = html + "</div></div>";
html = html + "</div>";

// Search Button
html = html + "<button class='search-button'>Search</button>";

// Results Container
html = html + "<div class='results-container'><div class='results-inner'>";
html = html + "<table>";
html = html + "<thead><tr>";
html = html + "<th>NO</th>";
html = html + "<th>NAME</th>";
html = html + "<th>YEAR</th>";
html = html + "<th>PERIOD</th>";
html = html + "<th>FIRST YEAR <br/> TEMPERATURE</th>";
html = html + "<th>LAST YEAR <br/> TEMPERATURE</th>";
html = html + "<th>CHANGE</th>";
html = html + "</tr></thead>";

html = html + "<tbody>";
html = html + "<tr><td>1</td><td>City name</td><td>null</td><td>null</td><td>null</td><td>null</td><td>null</td></tr>";
html = html + "<tr><td>1</td><td>city name</td><td>null</td><td>null</td><td>null</td><td>null</td><td>null</td></tr>";
html = html + "<tr><td>1</td><td>city name</td><td>d</td><td>d</td><td>null</td><td>null</td><td>null</td></tr>";
html = html + "<tr><td>1</td><td>1.000.000.000</td><td>nullsd</td><td>null</td><td>null</td><td>null</td><td>null</td></tr>";

html = html + "<!-- More rows here -->";

html = html + "</tbody></table></div></div>";
html = html + "</div></body></html>";


}
}