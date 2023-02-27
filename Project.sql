CREATE TABLE Schedule(
	courseId CHAR(12) NOT NULL UNIQUE,
    sectionNumber VARCHAR(8) NOT NULL UNIQUE,
    title VARCHAR(64),
    year INT,
    semester CHAR(6),
    instructor VARCHAR(24),
    department CHAR(16),
    program VARCHAR(48),
    PRIMARY KEY(courseId, sectionNumber));
CREATE TABLE Students(
	empID INT NOT NULL PRIMARY KEY,
    firstName VARCHAR(40),
    lastName VARCHAR(40),
    email CHAR(60),
    gender CHAR CHECK(gender = 'M' OR gender = 'F' OR gender = 'U'));
CREATE TABLE Courses(
	courseID CHAR(12) NOT NULL PRIMARY KEY,
    courseTitle VARCHAR(64),
    department CHAR(16));
CREATE TABLE Classes(
	empID INT REFERENCES Students(empID),
    courseID CHAR(12) REFERENCES Schedule(courseID),
    sectionNumber VARCHAR(8) REFERENCES Schedule(sectionNumber),
    year INT,
    semester CHAR(6),
    grade CHAR CHECK(grade = 'A' OR grade = 'B' OR grade = 'C' OR grade = 'D' OR grade = 'F' OR grade = 'W'),
    PRIMARY KEY(courseID, empID, sectionNumber));
CREATE TABLE AgregateGrades(
    grade CHAR PRIMARY KEY,
    numberStudents INT);
LOAD DATA INFILE 'C:/Program Files/MySQL/tmp/ScheduleSpring2022.txt' 
INTO TABLE Schedule
FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n'
IGNORE 1 lines;

INSERT INTO Courses(courseID, courseTitle, department)
SELECT courseID, title, department
FROM Schedule;

INSERT INTO Students VALUES (10001,"Jair", "R", "jairr@gmail.com", 'M'), 
(10002,"Melissa", "M", "melissam@gmail.com", 'F'),
 (10003,"Peter", "D", "peterd@gmail.com", 'M'),
(10004,"Bennito", "M", "bennitom@gmail.com", 'M'),
 (10005,"Nicolas", "M", "nicolasm@gmail.com", 'U'),
 (10006,"Rafael", "C", "rafaelc@gmail.com", 'M'),
 (10007,"Freya", "A", "freyaa@gmail.com", 'F'),
 (10008,"Leonel", "M", "leonelm@gmail.com", 'M'),
 (10009,"Sergio", "A", "sergioa@gmail.com", 'M'),
 (10010,"Brithany", "F", "brithanyf@gmail.com", 'U'),
 (10011,"Marie", "C", "mariec@gmail.com", 'F'),
 (10012,"Jennifer", "L", "jenniferl@gmail.com", 'F'),
 (10013,"Megan", "F", "meganf@gmail.com", 'F'),
 (10014,"Mia", "K", "miak@gmail.com", 'F'),
 (10015,"Cristiano", "R", "cristianor@gmail.com", 'M'),
 (10016,"Ibai", "L", "ibail@gmail.com", 'U'),
 (10017,"Auron", "P", "auronp@gmail.com", 'M'),
 (10018,"Dwayne", "J", "dwaynej@gmail.com", 'M'),
 (10019,"Henrry", "C", "henrryc@gmail.com", 'M'),
 (10020,"Alex", "T", "alext@gmail.com", 'U');

INSERT INTO Classes VALUES (10001, "22100 P", "32132", 2021, "Fall", 'F'),
 (10002, "22100 P", "32132", 2021, "Fall", 'A'),
 (10003, "22100 P", "32132", 2021, "Fall", 'W'),
 (10004, "22100 R", "32150", 2021, "Fall", 'B'),
 (10005, "22100 P", "32132", 2021, "Fall", 'A'),
 (10006, "22100 R", "32150", 2021, "Fall", 'C'),
(10007, "22100 P", "32132", 2021, "Fall", 'B'),
 (10008, "22100 P", "32132", 2021, "Fall", 'A'),
 (10009, "22100 P", "32150", 2021, "Fall", 'D'),
(10010, "22100 R", "32132", 2021, "Fall", 'C'),
(10011, "22100 R", "32150", 2021, "Fall", 'A'),
 (10012, "22100 R", "32150", 2021, "Fall", 'B'),
 (10013, "22100 R", "32150", 2021, "Fall", 'B'),
 (10014, "22100 P", "32132", 2021, "Fall", 'W'),
 (10015, "22100 R", "32150", 2021, "Fall", 'F'),
(10016, "22100 P", "32132", 2021, "Fall", 'D'),
 (10017, "22100 P", "32132", 2021, "Fall", 'C'),
 (10018, "22100 P", "32132", 2021, "Fall", 'B'),
 (10019, "22100 R", "32150", 2021, "Fall", 'W'),
 (10020, "22100 R", "32150", 2021, "Fall", 'A');

INSERT INTO AgregateGrades SELECT grade, count(grade) FROM Classes WHERE courseID LIKE "22100 P" GROUP BY grade;
Select * from Students





    