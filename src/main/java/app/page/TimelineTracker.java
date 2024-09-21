package app.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.JDBC.JDBC;
import app.JDBC.JDBCforTimelineTracker;
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
        html += "<link rel='stylesheet' href='level3A.css'>";
        html += "</head>";

        // body
        html += "<body>";
        html += "<div class='container'>";

        // header
        html += "<div class='header'>";
        html += "<div class='logo'>";
        html += "<img src='ClimaTrendLogo.png' alt='ClimaTrend Logo'>";
        html += "</div>";

        // navigation
        html += "<div class='header-elements'>";
        html += "<a href='/'>Home</a>";
        html += "<a href='/GlobalTracker'>Global Tracker</a>";
        html += "<a href='/CityTracker'>City Tracker</a>";
        html += "<a href='/TimelineTracker'>Timeline Tracker</a>";
        html += "<a href='/PeriodTracker'>Periods Tracker</a>";
        html += "</div>";
        html += "</div>";

        html += "<div class='shadow'></div>";

        // Search panel
        html = html + "<form method='post' action='/TimelineTracker'>";
        html += "<div class='search-panel'>";


        JDBC jdbc = new JDBC();
        JDBCforTimelineTracker jdbc2 = new JDBCforTimelineTracker();
        Global firstyear = jdbc.getFirstYearTemp();
        Global lastyear = jdbc.getLastYearTemp();
        
        String selectBox = context.formParam("selectBox");
        // List<String> selectedYears = context.formParams("startYear");
        List<String> selectedCountriesList = context.formParams("countries");
        String[] selectedCountries = selectedCountriesList != null ? selectedCountriesList.toArray(new String[0])  : null;
        ArrayList<String> countryname = jdbc2.getCountryName();
        ArrayList<String> cityName = jdbc2.getCityName();
        ArrayList<String> stateName = jdbc2.getStateName();


        // Display region

        html += "<div class='search-section'>";
        html += "<div class='search-title'>Display Region</div>";
        html += "<div class='select-wrapper'>";
        

        // display form
        html += "<select name='selectBox' class='select-boxfordisplay' onchange='this.form.submit()'>";
        html += "<option value='' " + (selectBox == null ? "selected" : "") + ">--Select--</option>";
        html +="<option value='Word' " + ("Global".equals(selectBox) ? "selected" : "")
                + ">Global</option>";
        html +="<option value='Country' " + ("Country".equals(selectBox) ? "selected" : "")
                + ">Country</option>";
        html += "<option value='State' " + ("State".equals(selectBox) ? "selected" : "")
                + ">State</option>";
        html += "<option value='City' " + ("City".equals(selectBox) ? "selected" : "")
                + ">City</option>";
        html = html + "</select>";
        html += "<div class='select-arrow'></div>";
        html += "</div>";
        html += "</div>";

        
        

        if (selectBox != null) {
            if ("Global".equals(selectBox)) {
                
            }
            else if (selectBox != null && "Country".equals(selectBox)){

                html = html + "<div class='search-section'>";
                html = html + "<div class='search-title'>Country</div>";
                html = html + "<div class='select-wrapper'>";
                html = html + "<select multiple id = 'multiple-selects' class='select-multiple'>";
                for (String cname : countryname) {
                    html += "<option value='" + cname + "' "
                            + (selectedCountries != null && Arrays.asList(selectedCountries).contains(cname) ? "selected"
                                    : "")
                            + ">" + cname + "</option>";
                }
                html += "</select>";
                html += "</div>";
                html += "</div>";
            }
            else if (selectBox != null && "State".equals(selectBox)){
                html = html + "<div class='search-section'>";
                html = html + "<div class='search-title'>State</div>";
                html = html + "<div class='select-wrapper'>";
                html = html + "<select multiple id = 'multiple-selects' class='select-multiple'>";
                for (String sname : stateName) {
                    html += "<option value='" + sname + "' "
                            + (selectedCountries != null && Arrays.asList(selectedCountries).contains(sname) ? "selected"
                                    : "")
                            + ">" + sname + "</option>";
                }
                html += "</select>";
                html += "</div>";
                html += "</div>";
            }
            else if(selectBox != null && "City".equals(selectBox)){
                html = html + "<div class='search-section'>";
                html = html + "<div class='search-title'>City</div>";
                html = html + "<div class='select-wrapper'>";
                html = html + "<select multiple id = 'multiple-selects' class='select-multiple'>";
                for (String cname : cityName) {
                    html += "<option value='" + cname + "' "
                            + (selectedCountries != null && Arrays.asList(selectedCountries).contains(cname) ? "selected"
                                    : "")
                            + ">" + cname + "</option>";
                }
                html += "</select>";
                html += "</div>";
                html += "</div>";

            }
            List<String> selectedYearst = context.formParams("startyear");
            //  startYear
             html = html + "<div class='search-section'>";
             html = html + "<div class='search-title'>Start Year</div>";
             html = html + "<div class='select-wrapper'>";
             html += "<form>";
             html += "<select class='select-multiple'multiple>";
             for (int year = 1750; year <= 2012; year++) {
                html += "<option value=\"" + year + "\""
                        + (selectedYearst != null && selectedYearst.contains(String.valueOf(year)) ? " selected" : "")
                        + ">" + year + "</option>";
            }
            html += "</select>";
             html = html + "</div>";
             html = html + "</div>";


            //period
            html += "<div class='search-section'>";
            html += "<div class='search-title'> Periods</div>";
            html += "<div class='select-wrapper'>";
            html += "<input type='number' id='multiple' name='period' class='select-type' min='1' max='99'/>";
            html += "</div>";
            html += "</div>";

            html += "</div>";
        }


         


         
        // search button
        html += "<button class='search-button'>Search</button>";
        // closing form
        html += "</form>";
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

        // html += "<tbody>";
        // html += "<tr>";
        // html += "<td>1</td>";
        // html += "<td>city name</td>";
        // html += "<td>null</td>";
        // html += "<td>null</td>";
        // html += "<td>null</td>";
        // html += "<td>null</td>";
        // html += "<td>null</td>";
        // html += "</tr>";
        // html += "</tbody>";
        html += "</table>";
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
