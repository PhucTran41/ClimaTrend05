package app.page;

import java.util.List;
import java.util.Map;

import app.JDBC.JDBC;
import app.classes.Global;
import app.classes.Population;
import io.javalin.http.Context;
import io.javalin.http.Handler;



public class HomePage implements Handler {

    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {

           //Fetch the data using JDBC
           JDBC jdbc = new JDBC();
           Global firstYearTemp = jdbc.getFirstYearTemp();
           Global lastYearTemp = jdbc.getLastYearTemp();
           Population firstYearPopuWorld = jdbc.getPopulation("WLD", jdbc.getPopulationFirstYear());
           Population lastYearPopuWorld = jdbc.getPopulation("WLD", jdbc.getPopulationLastYear());
           
           //Retrieve TEAM MEMBERS
            List<String> teamMembers = jdbc.getTeamMembers();

            // Retrieve Persona Details for Persona ID 1
            Map<String, String> personaDetails1 = jdbc.getPersonaDetails(1); // PersonaID = 1
        
            // Retrieve Persona Details for Persona ID 2
            Map<String, String> personaDetails2 = jdbc.getPersonaDetails(2); // PersonaID = 2

             // Retrieve Persona Details for Persona ID 3
             Map<String, String> personaDetails3 = jdbc.getPersonaDetails(3); // PersonaID = 2
           

        String html = """
            
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Document</title>
                <link rel="stylesheet" href="LandingPage.css">
            </head>
            <body>
                <!-- navigation bar -->
                <nav> 
                    <div class="logo">
                        <img src="ClimaTrendLogo.png" alt="ClimaTrendLogo"> 
                    </div>    
                    <div class="nav-elements">
                        <a href="/">Home Page</a>
                        <a href="/GlobalTracker">Global Tracker</a>
                        <a href="/CityTracker">City Tracker</a>
                        <a href="/TimelineTracker">Timeline Tracker</a>
                        <a href="/PeriodTracker">Periods Tracker</a>
                    </div>
                </nav>
            
                <!-- hero section -->
                <section class="hero">
                    <div class="hero-container"></div>
                    <div class="column-center">
                        <h1 class="heading-title">
                            Explore the World Through <span class="highlight">Population</span> and <span class="highlight">Temperature</span> Data
                        </h1>
                        <p class="description">
                            "How have global population and temperatures changed over the decades? 
                            Explore trends through scientific data"
                        </p>
                        <a href="#available-data">
                            <button>View Now</button>
                        </a>
                    </div>
                        <img src="Earth.png" alt="illustration" class="hero-image"/>
                </section>
            
                    <!-- Our Mission -->
                    <section class="our-mission">
                        <h2>Our Mission</h2>
                        <p class="main-text">
                            Climate change is one of the most pressing social challenges of our time, with far-reaching impacts on ecosystems, economies, and communities.
                        </p>
                        <p class="secondary-text">
                            Our website tackles this challenge by providing a platform where governments, scientists, and the public can analyze and understand patterns of change in global temperatures and population over a 260-year period.
                        </p>
                        <p class="secondary-text">
                            By offering both high-level summaries and in-depth analyses, the site empowers users to make informed decisions, drive policy changes, and increase public awareness of climate trends.
                        </p> 
                    </section>
                
                
                    <!-- available data section -->
                    <section id="available-data" class="data-section">
                        <h2>Our Available Data</h2>
                        <div class="data-container">
                            <div class="data-column">
                                <div class="data-title">Population</div>
                                <div class="data-range">
                                    <div>
                                        <div class="data-year" id="popStartYear">FROM&nbsp;"""
                                            + firstYearPopuWorld.getYear() +  """
                                        </div>

                                        <br>

                                        <div class="data-value"><span id="popStartValue">"""

                                            + firstYearPopuWorld.getPopulation() + """

                                        </span> people</div>
                                    </div>
                                    <div>
                                        <div class="data-year" id="popEndYear">TO&nbsp;"""
                                            + lastYearPopuWorld.getYear() + """
                                        </div>

                                        <br>

                                        <div class="data-value"><span id="popEndValue">"""
                                            +  lastYearPopuWorld.getPopulation() + """
                                        </span> people</div>
                                    </div>
                                </div>
                            </div>
                    
                            <div class="data-column">
                                <div class="data-title">Temperature</div>
                                <div class="temperature-data">
                                    <div class="temperature-range">
                                        <div class="temperature-item">
                                            <div class="data-year" id="tempStartYear">FROM&nbsp;"""

                                                + firstYearTemp.getYear() + """

                                            </div>

                                            <br>

                                            <div class="data-value"><span id="tempStartValue">"""

                                                + firstYearTemp.getAverageTemp() + """

                                                    </span>&deg;C</div>
                                        </div>
                                        <div class="temperature-item">
                                            <div class="data-year" id="tempEndYear">TO&nbsp;"""

                                                + lastYearTemp.getYear() + """

                                            </div>

                                            <br>

                                            <div class="data-value"><span id="tempEndValue">"""

                                                + lastYearTemp.getAverageTemp() + """

                                                    </span>&deg;C</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                    

                <!-- How to use  -->
                <section class="how-to-use">
                    <h2>How to Use Our Website</h2>
                    <div class="grid">
                        <div class="card">
                            <h3>Global Tracker</h3>
                            <p>Compare temperature and population changes globally and at the national level to see how different countries have been impacted by climate change over time.</p>
                            <a href="/GlobalTracker" class="icon icon-right"></a>
                        </div>
                        <div class="card">
                            <h3 class ="CityTracker">City Tracker</h3>
                            <p>Explore temperature trends in specific cities or states within a country to get a clearer view of local climate effects.</p>
                            <a href="#city-tracker" class="icon icon-right"></a>
                        </div>
                        <div class="card">
                            <h3>TimeLine Tracker</h3>
                            <p>Examine how temperatures have changed over various time periods for different regions, allowing you to compare shifts in average temperatures.</p>
                            <a href="/TimelineTracker" class="icon icon-right"></a>
                        </div>
                        <div class="card">
                            <h3>Period Tracker</h3>
                            <p>Identify and compare different time periods with similar temperature and population patterns to uncover trends and insights.</p>
                            <a href="/PeriodTracker" class="icon icon-right"></a>
                        </div>
                    </div>
                </section>
                <!-- team section -->
                <div class="teamBox">
                    <div class="row">
                        <p class="Team5">Meet the ClimaTrend Team!</p>
                        <div class="column">
                            <div class="card">
                                <img src="memberPhuc.jpg" alt="Phuc" style="width:100%">
                                <div class="teamBox-content">
                                    <h2> """
                                         + teamMembers.get(0) + 
                                         """
                                            </h2>
                                    <p class="title">Member 1</p>
                                    <h3>"""
                                        + teamMembers.get(1) + """
                                            </h3>
                                </div>
                            </div>
                        </div>
                        <div class="column">
                            <div class="card">
                                <img src="memberJoanna.jpg" alt="Joanna" style="width:100%">
                                <div class="teamBox-content">
                                    <h2>"""
                                        + teamMembers.get(2) + 
                                        """
                                            </h2>
                                    <p class="title">Member 2</p>
                                    <h3>"""
                                        + teamMembers.get(3) + """
                                            </h3>
                                </div>
                            </div>
                        </div>
                        <div class="column">
                            <div class="card">
                                <img src="memberKhoa.jpg" alt="Khoa" style="width:100%">
                                <div class="teamBox-content">
                                    <h2>"""
                                        + teamMembers.get(4) + 
                                        """
                                            </h2>
                                    <p class="title">Member 3</p>
                                    <h3>"""
                                        + teamMembers.get(5) + """
                                            </h3>
                                </div>
                            </div>
                        </div>
                        <div class="column">
                            <div class="card">
                                <img src="memberTuan.jpg" alt="Tuan" style="width:100%">
                                <div class="teamBox-content">
                                    <h2>"""
                                        + teamMembers.get(6) + 
                                        """
                                            </h2>
                                    <p class="title">Member 4</p>
                                    <h3>"""
                                        + teamMembers.get(7) + """
                                            </h3>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <h1>ClimaTrend's User Personas</h1>

                        <div class="personas-container">
                            <div class="persona">
                                <img src="Per1.png" alt="Marie">

                                <h2>""" 
                                    + personaDetails1.get("personaName") + 
                                    """
                                         </h2>

                                <br>

                                <p> <strong>Age:</strong> """ 
                                    + personaDetails1.get("personaAge") + 
                                    """
                                     </p>

                                <br>

                                <p><strong>Background:</strong> """ 
                                    + personaDetails1.get("personaBack") + 
                                    """
                                    </p>

                                <br>

                                <p><strong>Needs:</strong></p>
                                <li>""" 
                                    + personaDetails1.get("personaNeeds1") + 
                                    """
                                        </li>

                                        <li>""" 
                                            + personaDetails1.get("personaNeeds2") + 
                                            """
                                                </li>

                                <br>

                                <p><strong>Goals:</strong></p>
                                    <li>""" 
                                        + personaDetails1.get("personaGoals1") + 
                                        """
                                            </li>

                                            <li>""" 
                                                + personaDetails1.get("personaGoals2") + 
                                                """
                                                    </li>

                                <br>

                                <p><strong>Pain Points:</strong></p>
                                    <li>""" 
                                            + personaDetails1.get("personaPain1") + 
                                            """
                                                </li>
                
                                                <li>""" 
                                                    
                                                    + personaDetails1.get("personaPain2") + 
                                                """
                                                    </li>

                                <br>

                                <p><strong>Exeperiences:</strong></p>
                                    <li>""" 
                                            + personaDetails1.get("personaExp1") + 
                                            """
                                                </li>
                
                                                <li>""" 
                                                    
                                                    + personaDetails1.get("personaExp2") + 
                                                """
                                                    </li>

                                <br>
                                  
                                <p><strong>User's Quote:</strong> """ 
                                    + personaDetails1.get("personaQuote") + 
                                    """
                                        </p>

                            </div>

                            <div class="persona">

                                <img src="Per2.png" alt="Alex">
 
                                <h2>""" 
                                    + personaDetails2.get("personaName") + 
                                    """
                                         </h2>

                                <br>

                                <p> <strong>Age:</strong> """ 
                                    + personaDetails2.get("personaAge") + 
                                    """
                                     </p>

                                <br>

                                <p><strong>Background:</strong> """ 
                                    + personaDetails2.get("personaBack") + 
                                    """
                                    </p>

                                <br>

                                <p><strong>Needs:</strong></p>
                                <li>""" 
                                    + personaDetails2.get("personaNeeds1") + 
                                    """
                                        </li>

                                        <li>""" 
                                            + personaDetails2.get("personaNeeds2") + 
                                            """
                                                </li>

                                <br>

                                <p><strong>Goals:</strong></p>
                                    <li>""" 
                                        + personaDetails2.get("personaGoals1") + 
                                        """
                                            </li>

                                            <li>""" 
                                                + personaDetails2.get("personaGoals2") + 
                                                """
                                                    </li>

                                <br>

                                <p><strong>Pain Points:</strong></p>
                                    <li>""" 
                                            + personaDetails2.get("personaPain1") + 
                                            """
                                                </li>
                
                                                <li>""" 
                                                    
                                                    + personaDetails2.get("personaPain2") + 
                                                """
                                                    </li>

                                <br>

                                <p><strong>Exeperiences:</strong></p>
                                    <li>""" 
                                            + personaDetails2.get("personaExp1") + 
                                            """
                                                </li>
                
                                                <li>""" 
                                                    
                                                    + personaDetails2.get("personaExp2") + 
                                                """
                                                    </li>

                                <br>
                                  
                                <p><strong>User's Quote:</strong> """ 
                                    + personaDetails2.get("personaQuote") + 
                                    """
                                        </p>

                            </div>


                            <div class="persona">
                                <img src="Per3.png" alt="Persona3">


                                <h2>""" 
                                    + personaDetails3.get("personaName") + 
                                    """
                                         </h2>

                                <br>

                                <p> <strong>Age:</strong> """ 
                                    + personaDetails3.get("personaAge") + 
                                    """
                                     </p>

                                <br>

                                <p><strong>Background:</strong> """ 
                                    + personaDetails3.get("personaBack") + 
                                    """
                                    </p>

                                <br>

                                <p><strong>Needs:</strong></p>
                                <li>""" 
                                    + personaDetails3.get("personaNeeds1") + 
                                    """
                                        </li>

                                        <li>""" 
                                            + personaDetails3.get("personaNeeds2") + 
                                            """
                                                </li>

                                <br>

                                <p><strong>Goals:</strong></p>
                                    <li>""" 
                                        + personaDetails3.get("personaGoals1") + 
                                        """
                                            </li>

                                            <li>""" 
                                                + personaDetails3.get("personaGoals2") + 
                                                """
                                                    </li>

                                <br>

                                <p><strong>Pain Points:</strong></p>
                                    <li>""" 
                                            + personaDetails3.get("personaPain1") + 
                                            """
                                                </li>
                
                                                <li>""" 
                                                    
                                                    + personaDetails3.get("personaPain2") + 
                                                """
                                                    </li>

                                <br>

                                <p><strong>Exeperiences:</strong></p>
                                    <li>""" 
                                            + personaDetails3.get("personaExp1") + 
                                            """
                                                </li>
                
                                                <li>""" 
                                                    
                                                    + personaDetails3.get("personaExp2") + 
                                                """
                                                    </li>

                                <br>
                                  
                                <p><strong>User's Quote:</strong> """ 
                                    + personaDetails3.get("personaQuote") + 
                                    """
                                        </p>

                            </div>
                        </div>

                                </body>
                                </html>
            
                """;

        context.html(html);
    }
}
