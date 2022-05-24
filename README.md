# DB3-Project<br/>
Sotirios Panagiotou, 4456<br/>
Dimitrios Giannakopoulos, 4336<br/>


# Description:<br/>

This project implements query selections on worldbank.com countries' data on different metrics and displays them on 3 different plots - barchart, trendline and scatterplot.

# Installation:<br/>

1. Install MySQL
2. Install Python3 (Preferably 3.8)
3. Install the mysqlclient module (pip install mysqlclient)
4. Install an IDE, e.g Eclipse

# Run:<br/>

1. Run the etl process on trifacta and save the output csv as `joined.csv` on the data folder.<br/>
2. Run the ddl.py script on the ddl folder (edit the script to match your DB login info)<br/>
(Or simply just import the db dump [backup.dump] into mysql)
3. Edit the application.properties file at src\main\resources to match your DB<br/>
4. Start the project by running the MainApplication class on src\main\java\db3<br/>
5. Head to 127.0.0.1:8080 to view the app.<br/>

# Problems:<br/>

A possible "problem" is that when a time spacing (5/10/20 years) is selected, the time filtering doesn't display the right selections.