package app.page;

import java.util.ArrayList;
import java.util.List;

import app.JDBC.JDBC;
import app.JDBC.JDBCforGlobalTracker;
import app.classes.Global;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class TimelineTracker implements Handler {

    public static final String URL = "/TimelineTracker";

    @Override
    public void handle(Context context) throws Exception {
        String html = "<html>";

        // head
        html += "<head>";
        html += "<meta charset='UTF-8'>";
        html += "<meta name='viewport' content='width=device-width, initial-scale=1.0'>";
        html += "<title>ClimateTrend Dashboard</title>";
        html += "<link rel='stylesheet' href='level3A(landingPage).css'>";
        html += "</head>";

        // body
        html += "<body>";
        html += "<div class='container'>";

        // header
        html += "<div class='header'>";
        html += "<div class='logo'>";
        html += "<img src='images/ClimaTrendLogo.png' alt='ClimaTrend Logo'>";
        html += "</div>";

        // navigation
        html += "<div class='header-elements'>";
        html += "<a href='/LandingPage'>Home</a>";
        html += "<a href='/GlobalTracker'>Global Tracker</a>";
        html += "<a href='/CityTracker'>City Tracker</a>";
        html += "<a href='/TimeLineTracker'>Timeline Tracker</a>";
        html += "<a href='/PeriodTracker'>Periods Tracker</a>";
        html += "</div>";
        html += "</div>";

        html += "<div class='shadow'></div>";

        // Search panel
        html += "<div class='search-panel'>";

        JDBC jdbc = new JDBC();
        JDBCforGlobalTracker jdbc2 = new JDBCforGlobalTracker();
        Global firstyear = jdbc.getFirstYearTemp();
        Global lastyear = jdbc.getLastYearTemp();

        String selectBox = context.formParam("selectBox");
        List<String> selectedYears = context.formParams("startYear");
        ArrayList<String> name = jdbc2.getCountryName();

        // Display region
        html += "<div class='search-section'>";
        html += "<div class='search-title'>Display Region</div>";
        html += "<div class='select-wrapper'>";

        // display form
        html += "<form method=" + "post" + " action=" + "/TimelineTracker" + ">";
        html += "<select name='selectBox' class='select-box' onchange='this.form.submit()'>";

        html += "<option value='' " + (selectBox == null ? "selected" : "") + ">Select</option>";
        html += "<option value='Global' " + ("Global".equals(selectBox) ? "selected" : "") + ">Global</option>";
        html += "<option value='Country' " + ("Country".equals(selectBox) ? "selected" : "") + ">Country</option>";
        html += "<option value='State'" + ("State".equals(selectBox) ? "selected" : "") + ">State</option>";
        html += "<option value='City'" + ("City".equals(selectBox) ? "selected" : "") + ">City</option>";
        html += "</select>";
        html += "<div class='select-arrow'></div>";
        html += "</div>";
        html += "</div>";
        html += "</div>";

        if (selectBox != null) {
            if ("Global".equals(selectBox)) {

            }

            //startYear
            html = html + "<div class='search-section'>";
            html = html + "<div class='search-title'>Start Year</div>";
            html = html + "<div class='select-wrapper'>";
            html += "<select multiple id='multiple-selecter' name='startyear' class='form-select'>";
            
            for (int i = firstyear.getYear(); i <= lastyear.getYear(); i++) {
                html += "<option value=\"" + i + "\""
                        + (selectedYears != null && selectedYears.contains(String.valueOf(i)) ? " selected" : "")
                        + ">" + i + "</option>";
            }

            html += "</select>";

            html += "</div>";
            html += "</div>";

            //period
            html += "<div class='search-section'>";
            html += "<div class='search-title'> Periods</div>";
            html += "<div class='select-wrapper'>";
            html += "<input type = 'text' id = 'period' name = 'period' placeholder='Enter'>";
                

            html += "</div>";
            html += "</div>";

        }

        // search button
        html += "<button class='search-button'>Search</button>";

        // Results
        html += "<div class='results-container'>";
        html += "<div class='results-inner'>";
        html += "<table>";
        html += "<thead>";
        html += "<tr>";
        html += "<th>NO</th>";
        html += "<th>NAME</th>";
        html += "<th>YEAR</th>";
        html += "<th>PERIOD</th>";
        html += "<th>FIRST YEAR <br/> TEMPERATURE</th>";
        html += "<th>LAST YEAR <br/> TEMPERATURE</th>";
        html += "<th>CHANGE</th>";
        html += "</tr>";
        html += "</thead>";

        html += "<tbody>";
        html += "<tr>";
        html += "<td>1</td>";
        html += "<td>city name</td>";
        html += "<td>null</td>";
        html += "<td>null</td>";
        html += "<td>null</td>";
        html += "<td>null</td>";
        html += "<td>null</td>";
        html += "</tr>";
        html += "</tbody>";
        html += "</table>";
        html += "</div>";
        html += "</div>";

        html += "</div>";
        html += "</body>";
        html += "</html>";

        // closing form
        html += "</form>";

        // Output HTML
        context.html(html);
    }

}
