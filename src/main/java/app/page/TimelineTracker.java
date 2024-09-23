package app.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import app.JDBC.JDBCforTimelineTracker;
import app.classes.City;
import app.classes.Country;
import app.classes.Global;
import app.classes.State;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class TimelineTracker implements Handler {

    public static final String URL = "/TimelineTracker";

    @Override
    public void handle(Context context) throws Exception {
        String html = "<html>";
        html += generateHead();
        html += generateBodyStart();
        html += generateHeader();
        html += generateSearchPanel(context);
        html += generateResults(context);
        html += generateBodyEnd();

        context.html(html);
    }

    private String generateHead() {
        return "<head>" +
               "<meta charset='UTF-8'>" +
               "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
               "<title>ClimateTrend Dashboard</title>" +
               "<link rel='stylesheet' href='level3A.css'>" +
               "</head>";
    }

    private String generateBodyStart() {
        return "<body><div class='container'>";
    }

    private String generateHeader() {
        return "<div class='header'>" +
               "<div class='logo'>" +
               "<img src='ClimaTrendLogo.png' alt='ClimaTrendLogo'>" +
               "</div>" +
               "<div class='header-elements'>" +
               "<a href='/'>Home</a>" +
               "<a href='/GlobalTracker'>Global Tracker</a>" +
               "<a href='/CityTracker'>City Tracker</a>" +
               "<a href='/TimelineTracker'>Timeline Tracker</a>" +
               "<a href='/PeriodTracker'>Periods Tracker</a>" +
               "</div>" +
               "</div>" +
               "<div class='shadow'></div>";
    }

    private String generateSearchPanel(Context context) {
        JDBCforTimelineTracker jdbc = new JDBCforTimelineTracker();
        String selectBox = context.formParam("selectBox");
        List<String> selectedRegions = context.formParams("selectedreion");
        List<String> selectedYears = context.formParams("selectstartyear");
        String period = context.formParam("period");

        String html = "<form method='post' action='/TimelineTracker'>" +
                      "<div class='search-panel'>";

        html += generateDisplayRegion(selectBox);

        if (selectBox != null && !selectBox.isEmpty()) {
            html += generateRegionSelection(selectBox, selectedRegions, jdbc);
            html += generateYearSelection(selectedYears);
            html += generatePeriodSelection(period);

            html += "</div>"; 
            html += "<button class='search-button'>Search</button>";
            
        }

        
        html += "</form>"; 
        return html;
    }


    private String generateDisplayRegion(String selectBox) {
        String html = "<div class='search-section'>" +
                      "<div class='search-title'>Display Region</div>" +
                      "<div class='select-wrapper'>" +
                      "<select name='selectBox' class='select-boxfordisplay' onchange='this.form.submit()'>" +
                      "<option value='' " + (selectBox == null ? "selected" : "") + ">Select</option>" +
                      "<option value='Global' " + ("Global".equals(selectBox) ? "selected" : "") + ">Global</option>" +
                      "<option value='Country' " + ("Country".equals(selectBox) ? "selected" : "") + ">Country</option>" +
                      "<option value='State' " + ("State".equals(selectBox) ? "selected" : "") + ">State</option>" +
                      "<option value='City' " + ("City".equals(selectBox) ? "selected" : "") + ">City</option>" +
                      "</select>" +
                      "<div class='select-arrow'></div>" +
                      "</div>" +
                      "</div>";
        return html;
    }

    private String generateRegionSelection(String selectBox, List<String> selectedRegions, JDBCforTimelineTracker jdbc) {
        if (selectBox != null && !"Global".equals(selectBox)) {
            String html = "<div class='search-section'>" +
                          "<div name='selectedreion' class='search-title'>" + selectBox + "</div>" +
                          "<div class='select-wrapper'>" +
                          "<select multiple id='multiple-selects' name='selectedreion' class='select-multiple'>";

            ArrayList<String> names;
            switch (selectBox) {
                case "Country":
                    names = jdbc.getCountryName();
                    break;
                case "State":
                    names = jdbc.getStateName();
                    break;
                case "City":
                    names = jdbc.getCityName();
                    break;
                default:
                    names = new ArrayList<>();
            }

            for (String name : names) {
                html += "<option value='" + name + "' " +
                        (selectedRegions != null && selectedRegions.contains(name) ? "selected" : "") +
                        ">" + name + "</option>";
            }

            html += "</select>" +
                    "</div>" +
                    "</div>";
            return html;
        }
        return "";
    }

    private String generateYearSelection(List<String> selectedYears) {
        String html = "<div class='search-section'>" +
                      "<div name='selectstartyear' class='search-title'>Start Year</div>" +
                      "<div class='select-wrapper'>" +
                      "<select name='selectstartyear' class='select-multiple' multiple>";

        for (int year = 1750; year <= 2012; year++) {
            html += "<option value=\"" + year + "\"" +
                    (selectedYears != null && selectedYears.contains(String.valueOf(year)) ? " selected" : "") +
                    ">" + year + "</option>";
        }

        html += "</select>" +
                "</div>" +
                "</div>";
        return html;
    }

    private String generatePeriodSelection(String period) {
        return "<div class='search-section'>" +
               "<div name='period' class='search-title'> Periods</div>" +
               "<div class='select-wrapper'>" +
               "<input type='number' id='multiple' name='period' class='select-type' min='1' max='99'" +
               (period != null ? " value='" + period + "'" : "") +
               "/>" +
               "</div>" +
               "</div>";
    }

    private String generateResults(Context context) {
        
        String selectBox = context.formParam("selectBox");
        List<String> selectedYears = context.formParams("selectstartyear");
        String period = context.formParam("period");
        List<String> selectedRegions = context.formParams("selectedreion");
        if (selectBox == null || selectBox.isEmpty()) {
            return "";
        }
        if (selectBox != null && selectedYears != null && !selectedYears.isEmpty() && period != null && !period.isEmpty()) {
            int periodValue = Integer.parseInt(period);
            JDBCforTimelineTracker jdbc = new JDBCforTimelineTracker();

            switch (selectBox) {
                case "Global":
                    return generateGlobalResults(selectedYears, periodValue, jdbc);
                case "Country":
                    return generateCountryResults(selectedYears, periodValue, selectedRegions, jdbc);
                case "State":
                    return generateStateResults(selectedYears, periodValue, selectedRegions, jdbc);
                case "City":
                    return generateCityResults(selectedYears, periodValue, selectedRegions, jdbc);
                default:
                    return "";
            }
        }
        return "";
    }

    private String generateGlobalResults(List<String> selectedYears, int periodValue, JDBCforTimelineTracker jdbc) {
        ArrayList<Global> globalDataList = new ArrayList<>();

        for (String yearStr : selectedYears) {
            int startYear = Integer.parseInt(yearStr);
            int endYear = startYear + periodValue;
            Global globalData = jdbc.getGlobalavgtemp(startYear, endYear);
            globalData.setInitialYear(startYear);
            globalData.setPeriod(periodValue);
            globalDataList.add(globalData);
        }

        if (!globalDataList.isEmpty()) {
            return generateResultTable(globalDataList, periodValue, (data1, data2) -> Float.compare(data1.getAverageTemp(), data2.getAverageTemp()),
                (data, rank) -> {
                    String row = "<tr>";
                    row += "<td>" + rank + "</td>";
                    row += "<td>Global</td>";
                    row += "<td>" + data.getInitialYear() + "</td>";
                    row += "<td>" + periodValue + " years</td>";
                    row += "<td>" + data.getStartYear() + "</td>";
                    row += "<td>" + data.getEndYear() + "</td>";
                    
                    if (data.getAverageTemp() == 0) {
                        row += "<td>N/A</td>";
                    } else {
                        row += String.format("<td>%.3f", data.getAverageTemp()) + "&deg;C</td>";
                    }
                    
                    row += "</tr>";
                    return row;
                });
        } else {
            return "<p>No data found for the chosen years.</p>";
        }
    }

    private String generateCountryResults(List<String> selectedYears, int periodValue, List<String> selectedRegions, JDBCforTimelineTracker jdbc) {
        ArrayList<Country> countryDataList = new ArrayList<>();
        for (String country : selectedRegions) {
            for (String yearStr : selectedYears) {
                int startYear = Integer.parseInt(yearStr);
                int endYear = startYear + periodValue;
                Country countryData = jdbc.getCountryAvgTemp(country, startYear, endYear);
                countryData.setInitialYear(startYear);
                countryDataList.add(countryData);
            }
        }
        if (!countryDataList.isEmpty()) {
            return generateResultTable(countryDataList, periodValue, (data1, data2) -> Float.compare(data2.getAverageTemp(), data1.getAverageTemp()),
                (data, rank) -> {
                    String row = "<tr>";
                    row += "<td>" + rank + "</td>";
                    row += "<td>" + data.getCountryName() + "</td>";
                    row += "<td>" + data.getInitialYear() + "</td>";
                    row += "<td>" + periodValue + " years</td>";
                    row += data.getStartYear() == 0 ? "<td>N/A</td>" : "<td>" + data.getStartYear() + "</td>";
                    row += data.getEndYear() == 0 ? "<td>N/A</td>" : "<td>" + data.getEndYear() + "</td>";
                    if (data.getAverageTemp() == 0) {
                        row += "<td>N/A</td>";
                    } else {
                        row += String.format("<td>%.3f", data.getAverageTemp()) + "&deg;C</td>";
                    }
                    row += "</tr>";
                    return row;
                });
        } else {
            return "<p>No data found for the chosen countries and years.</p>";
        }
    }

    private String generateStateResults(List<String> selectedYears, int periodValue, List<String> selectedRegions, JDBCforTimelineTracker jdbc) {
        ArrayList<State> stateDataList = new ArrayList<>();

        for (String state : selectedRegions) {
            for (String yearStr : selectedYears) {
                int startYear = Integer.parseInt(yearStr);
                State stateData = jdbc.getStateAvgTemp(state, startYear, startYear + periodValue);
                stateData.setInitialYear(startYear);
                stateDataList.add(stateData);
            }
        }

        if (!stateDataList.isEmpty()) {
            return generateResultTable(stateDataList, periodValue, (data1, data2) -> Float.compare(data1.getAverageTemp(), data2.getAverageTemp()),
                (data, rank) -> {
                    String row = "<tr>";
                    row += "<td>" + rank + "</td>";
                    row += "<td>" + data.getName() + "</td>";
                    row += "<td>" + data.getInitialYear() + "</td>";
                    row += "<td>" + periodValue + " years</td>";
                    row += data.getStartYear() == 0 ? "<td>N/A</td>" : "<td>" + data.getStartYear() + "</td>";
                    row += data.getEndYear() == 0 ? "<td>N/A</td>" : "<td>" + data.getEndYear() + "</td>";
                    
                    if (data.getAverageTemp() == 0) {
                        row += "<td>N/A</td>";
                    } else {
                        row += String.format("<td>%.3f", data.getAverageTemp()) + "&deg;C</td>";
                    }
                    
                    row += "</tr>";
                    return row;
                });
        } else {
            return "<p>No data found for the chosen states and years.</p>";
        }
    }

    private String generateCityResults(List<String> selectedYears, int periodValue, List<String> selectedRegions, JDBCforTimelineTracker jdbc) {
        ArrayList<City> cityDataList = new ArrayList<>();

        for (String city : selectedRegions) {
            for (String yearStr : selectedYears) {
                int startYear = Integer.parseInt(yearStr);
                City cityData = jdbc.getCityAvgTemp(city, startYear, startYear + periodValue);
                cityData.setInitialYear(startYear);
                cityDataList.add(cityData);
            }
        }

        if (!cityDataList.isEmpty()) {
            return generateResultTable(cityDataList, periodValue, (data1, data2) -> Float.compare(data1.getAverageTemp(), data2.getAverageTemp()),
                (data, rank) -> {
                    String row = "<tr>";
                    row += "<td>" + rank + "</td>";
                    row += "<td>" + data.getName() + "</td>";
                    row += "<td>" + data.getInitialYear() + "</td>";
                    row += "<td>" + periodValue + " years</td>";
                    row += data.getStartYear() == 0 ? "<td>N/A</td>" : "<td>" + data.getStartYear() + "</td>";
                    row += data.getEndYear() == 0 ? "<td>N/A</td>" : "<td>" + data.getEndYear() + "</td>";
                    
                    if (data.getAverageTemp() == 0) {
                        row += "<td>N/A</td>";
                    } else {
                        row += String.format("<td>%.3f", data.getAverageTemp()) + "&deg;C</td>";
                    }
                    
                    row += "</tr>";
                    return row;
                });
        } else {
            return "<p>No data found for the chosen cities and years.</p>";
        }
    }

    private <T> String generateResultTable(List<T> dataList, int periodValue, Comparator<T> comparator, ResultRowGenerator<T> rowGenerator) {
        Collections.sort(dataList, comparator);
        
        String html = "<div class='results-container'>" +
                      "<div class='results-inner'>" +
                      "<table>" +
                      "<thead>" +
                      "<tr>" +
                      "<th>NO</th>" +
                      "<th>NAME</th>" +
                      "<th>YEAR</th>" +
                      "<th>PERIOD</th>" +
                      "<th>FIRST YEAR <br/> TEMPERATURE</th>" +
                      "<th>LAST YEAR <br/> TEMPERATURE</th>" +
                      "<th>CHANGE</th>" +
                      "</tr>" +
                      "</thead>" +
                      "<tbody>";

        int rank = 1;
        for (T data : dataList) {
            html += rowGenerator.generateRow(data, rank);
            rank++;
        }

        html += "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>";

        return html;
    }

    private String generateBodyEnd() {
        return "</div></body></html>";
    }

    private interface ResultRowGenerator<T> {
        String generateRow(T data, int rank);
    }
    
}
