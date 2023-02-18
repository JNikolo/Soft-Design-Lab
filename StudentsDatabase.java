import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

class StudentsDatabase implements TableInterface, StudentsDatabaseInterface{
    String url, username, password;
    Connection connection;
    StudentsDatabase(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
        this.connection = getConnection(url, username, password);
    }
    public Connection getConnection(String url, String username, String password){
        Connection connection = null;
        
        try{
            //Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("\nConnection to the databse server successful");
        }
        catch(SQLException e){System.out.println(e);}
        //catch(ClassNotFoundException c){System.out.println(c);}
        return connection;
    }
    
    class Schedule{
        Schedule(String nameFile) throws SQLException{
            try{
                TableInterface.dropTable(connection, "Schedule");
                TableInterface.createTable(connection, ddlCreateTableSchedule);
                TableInterface.populateTable(connection, TableInterface.loadDataInFileTable(nameFile,"Schedule"));
            }
            catch(SQLException e){System.out.println(e);}
        }
        public void updateInstructor(String courseID, String newInstructor) throws SQLException{
            String query = "UPDATE Schedule SET instructor = " + newInstructor + " WHERE courseID = " + courseID;
                PreparedStatement psUpdGrade = connection.prepareStatement(query);
                try{
                    psUpdGrade.executeUpdate();
                }catch(SQLException e){System.out.println(e);}
        }
    }
    class Students{
        Students(){        
            try{
                TableInterface.dropTable(connection, "Students");

                TableInterface.createTable(connection, ddlCreateTableStudents);
                TableInterface.populateTable(connection, ddlInsertTableStudents);
            }
            catch(SQLException e){System.out.println(e);}
        }
    }
    class Courses{
        Courses(){
            try{
                TableInterface.dropTable(connection, "Courses");
                TableInterface.createTable(connection, ddlCreateTableCourses);
                TableInterface.populateTable(connection, ddlInsertCourses);
            }
            catch(SQLException e){System.out.println(e);}
        }
    }
    class Classes{
        Classes(){
            try{
                TableInterface.dropTable(connection, "Classes");

                TableInterface.createTable(connection, ddlCreateTableClasses);
                TableInterface.populateTable(connection, ddlInsertTableClasses);
            }
            catch(SQLException e){System.out.println(e);}
        }
        public void updateGrade(String studentID, String newGrade) throws SQLException{
            String query = "UPDATE Classes SET grade = " + newGrade + " WHERE empID = " + studentID;
            PreparedStatement psUpdGrade = connection.prepareStatement(query);
            try{
                psUpdGrade.executeUpdate();
            }catch(SQLException e){System.out.println(e);}
        }
    }
    class AggregateGrades{
        AggregateGrades(String iD){
            try{
                String ddlInsertAggregateGrade =
                "INSERT INTO AggregateGrades SELECT grade, count(grade) "+ 
                "FROM Classes WHERE courseID LIKE '22100 "+iD +"' GROUP BY grade;";
                TableInterface.dropTable(connection, "AggregateGrades");
                TableInterface.createTable(connection, ddlCreateTableAggregateGrades);
                TableInterface.populateTable(connection, ddlInsertAggregateGrade);
            }catch(SQLException e){System.out.println(e);}
    }
        public Map<Character, Integer> getDataFromTable(){
            Map<Character, Integer> result = new HashMap<>();
            try{
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM AggregateGrades");
                while(rs.next()){
                    Character grade = rs.getString("grade").charAt(0);
                    Integer numberStudents = rs.getInt("numberStudents");
                    result.put(grade, numberStudents);
                }
            }catch(Exception e){System.out.println(e);}
            return result;
        }
    }  
}