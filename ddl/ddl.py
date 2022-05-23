import csv
import MySQLdb

mydb = MySQLdb.connect(host='localhost',
    user='root',
    passwd='')
cursor = mydb.cursor()

cursor.execute("DROP DATABASE IF EXISTS db3")
cursor.execute("CREATE DATABASE db3")
cursor.execute("USE db3")

sql ='''CREATE TABLE Countries(
   id INT NOT NULL AUTO_INCREMENT,
   Name VARCHAR(20),
   Code VARCHAR(10),
   LongName VARCHAR(255),
   Region VARCHAR(255),
   Currency VARCHAR(255),
   PRIMARY KEY (id),
   INDEX (Code)
)'''
cursor.execute(sql)

sql ='''CREATE TABLE Indicators(
   id INT NOT NULL AUTO_INCREMENT,
   Name VARCHAR(255),
   Code VARCHAR(255),
   PRIMARY KEY (id),
   INDEX (Code)
)'''
cursor.execute(sql)

sql ='''CREATE TABLE Statistics(
   id INT NOT NULL AUTO_INCREMENT,
   Country VARCHAR(10),
   Year INT,
   Indicator VARCHAR(255),
   Value FLOAT,
   PRIMARY KEY (id),
   FOREIGN KEY (Country) REFERENCES Countries(Code) ON DELETE CASCADE,
   FOREIGN KEY (Indicator) REFERENCES Indicators(Code) ON DELETE CASCADE
)'''
cursor.execute(sql)

csv_reader = csv.reader(open('../data/joined.csv'), delimiter=',')
line_count = 0
years = {}
for row in csv_reader:
        if line_count == 0:
            index = 0
            for i in row:
                if i.isnumeric() and (int(i) > 1000 and int(i) < 3000):
                    years[i] = index
                index += 1
            line_count += 1
        else:
            sqlq = f'SELECT COUNT(1) FROM Countries WHERE Name = "{row[1]}"'
            cursor.execute(sqlq)
            if not cursor.fetchone()[0]:
                sqlq = f'INSERT INTO Countries(Name, Code, LongName, Region, Currency) VALUES ("{row[1]}", "{row[0]}", "{row[-3]}", "{row[-2]}", "{row[-1]}")'
                cursor.execute(sqlq)
                
                
            sqlq = f'SELECT COUNT(1) FROM Indicators WHERE Name = "{row[2]}"'
            cursor.execute(sqlq)
            if not cursor.fetchone()[0]:
                sqlq = f'INSERT INTO Indicators(Name, Code) VALUES ("{row[2]}", "{row[3]}")'
                cursor.execute(sqlq)
                
            for i in years.keys():
                if(row[years[i]] != ""):
                    sqlq = f'INSERT INTO `Statistics`(Country, Year, Indicator, Value) VALUES ("{row[0]}", {i},"{row[3]}", {row[years[i]]})'
                    cursor.execute(sqlq)
#
            line_count += 1
            
mydb.commit()
cursor.close()
print("Done")
