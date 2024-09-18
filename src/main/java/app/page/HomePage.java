package app.page;

import app.JDBC.JDBC;
import app.classes.Global;
import io.javalin.http.Context;
import io.javalin.http.Handler;


public class HomePage implements Handler {

    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {

           // Step 1: Fetch the first year temperature data using JDBC
           JDBC jdbc = new JDBC();
           Global firstYearTemp = jdbc.getFirstYearTemp();
           Global lastYearTemp = jdbc.getLastYearTemp();

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
                        <img src="WorldTrackerLogo.png" alt="WorldChanges Logo"> 
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
                                    <div class="data-year" id="popStartYear">1960</div>
                                    <div class="data-value"><span id="popStartValue">1,900,000</span> people</div>
                                </div>
                                <div>
                                    <div class="data-year" id="popEndYear">2013</div>
                                    <div class="data-value"><span id="popEndValue">4,000,541</span> people</div>
                                </div>
                            </div>
                        </div>
                        <div class="data-column">
                            <div class="data-title">Temperature</div>
                            <div class="temperature-data">
                                <div class="temperature-range">
                                    <div class="temperature-item">
                                    

                                        <div class="data-year" id="tempStartYear">"""
                                        + firstYearTemp.getYear() +"""
                                              </div>

                                        <div class="data-value"><span id="tempStartValue">34</span>c</div>
                                    </div>
                                    <div class="temperature-item">


                                        <div class="data-year" id="tempEndYear"> """
                                            + lastYearTemp.getYear()+ """
                                            </div>

                                        <div class="data-value"><span id="tempEndValue">35.2</span>c</div>
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
                        <p class="Team5">Our Team</p>
                        <div class="column">
                            <div class="card">
                                <img src="Per1.jpg" alt="Jane" style="width:100%">
                                <div class="teamBox-content">
                                    <h2>Ting-Chu Yang</h2>
                                    <p class="title">CEO &amp; Founder</p>
                                    <p>Some text that describes me lorem ipsum ipsum lorem.</p>
                                    <p>example@example.com</p>
                                </div>
                            </div>
                        </div>
                        <div class="column">
                            <div class="card">
                                <img src="memberPhuc.jpg" alt="Mike" style="width:100%">
                                <div class="teamBox-content">
                                    <h2>Phuc Tran</h2>
                                    <p class="title">Database</p>
                                    <p>Some text that describes me lorem ipsum ipsum lorem.</p>
                                    <p>example@example.com</p>
                                </div>
                            </div>
                        </div>
                        <div class="column">
                            <div class="card">
                                <img src="img3.jpg" alt="Alice" style="width:100%">
                                <div class="teamBox-content">
                                    <h2>Khoa Nguyen</h2>
                                    <p class="title">Database</p>
                                    <p>Some text that describes me lorem ipsum ipsum lorem.</p>
                                    <p>example@example.com</p>
                                </div>
                            </div>
                        </div>
                        <div class="column">
                            <div class="card">
                                <img src="Ảnh/PhotoRoom_20240201_115321 3.jpg" alt="Bob" style="width:100%">
                                <div class="teamBox-content">
                                    <h2>Tuan Nguyen</h2>
                                    <p class="title">UI-UX</p>
                                    <p>Some text that describes me lorem ipsum ipsum lorem.</p>
                                    <p>example@example.com</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <h1>Personas</h1>
    <div class="personas-container">
        <div class="persona">
            <img src="Per1.png" alt="Marie">
            <h2>Marie — The Artist</h2>
            <p><strong>Age:</strong> 26</p>
            <p><strong>Location:</strong> London, UK</p>
            <p><strong>Occupation:</strong> Artist/Art Buyer</p>
            <p><strong>Income:</strong> £32,000</p>
            <p><strong>Goal:</strong> Looking to sell her art easily online and gain exposure as an up and coming artist.</p>
        </div>
        <div class="persona">
            <img src="Per2.png" alt="Alex">
            <h2>Alex — The Tech Enthusiast</h2>
            <p><strong>Age:</strong> 32</p>
            <p><strong>Location:</strong> San Francisco, USA</p>
            <p><strong>Occupation:</strong> Software Developer</p>
            <p><strong>Income:</strong> $120,000</p>
            <p><strong>Goal:</strong> Seeking innovative platforms to discover and invest in digital art and NFTs.</p>
        </div>
        <div class="persona">
            <img src="Per3.png" alt="Sophie">
            <h2>Sophie — The Art Collector</h2>
            <p><strong>Age:</strong> 45</p>
            <p><strong>Location:</strong> Paris, France</p>
            <p><strong>Occupation:</strong> Gallery Owner</p>
            <p><strong>Income:</strong> €90,000</p>
            <p><strong>Goal:</strong> Wants to find emerging artists and unique pieces to feature in her gallery.</p>
        </div>
    </div>
            </body>
            </html>
            
                """;

        context.html(html);
    }
}
