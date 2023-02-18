import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Project4 extends Application{
    String namefile = "C:/Program Files/MySQL/tmp/ScheduleSpring2022.txt";
    String nameTable = "Schedule";
    String url = "jdbc:mysql://localhost:3306/project4";
    String user = "root";
    String password = "root";
    @Override
    public void start(Stage ps) throws Exception{
        StudentsDatabase db = new StudentsDatabase(url, user, password);
        MyPoint center = new MyPoint(300,250, null);
        
        ps.setTitle("Project 4");
        Label numberSlices = new Label("Pick between R or P 22100 course");
        Button newbtn = new Button("Submit");  
        GridPane root = new GridPane();
        TextField userInput1 = new TextField(); 
        root.addRow(1, numberSlices, userInput1);    
        root.addRow(3, newbtn); 
        newbtn.setOnAction(e->{
            Map<Character, Integer> grades = new HashMap<>();
            try{
                StudentsDatabase.Schedule tableSchedule = db.new Schedule(namefile);
                StudentsDatabase.Students tableStudents = db.new Students();
                StudentsDatabase.Courses tableCourses = db.new Courses();
                StudentsDatabase.Classes tableClasses = db.new Classes();
                StudentsDatabase.AggregateGrades tableAGrades = db.new AggregateGrades(userInput1.getText());
                 

                //tableSchedule.updateInstructor("22100 P","Elver Galarga");
                tableClasses.updateGrade("10001", "A");
                grades = tableAGrades.getDataFromTable();
            }
            catch(Exception ex){System.out.println(ex);}
            HistogramAlphaBet histogram = new HistogramAlphaBet(grades);
            Stage stage2 = new Stage();
            Pane p = new Pane();
            Canvas canvas = new Canvas(600,600);
            GraphicsContext GC = canvas.getGraphicsContext2D();
            HistogramAlphaBet.MyPieChart testPie = histogram.new MyPieChart(6, 0, center, 100);
            testPie.printPie();
            testPie.draw(GC);
            p.getChildren().add(canvas);
            Scene sc = new Scene(p,600,600,MyColor.White.getJavaFxColor()); 
            stage2.setTitle("Pie Chart"); 
            stage2.setScene(sc); 
            stage2.show();
        });
        Scene scene = new Scene(root, 500, 500);
        ps.setScene(scene);
        ps.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}



