import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

public class Slice{
    MyPoint center;     //central point of the circle
    double radius;      //the radius of the circle
    double startAngle, angle, endAngle; //counterclockwise 
    MyColor color;   
    //default constructor
    Slice(){this.radius = 1;this.startAngle=0;}  
    //parametrized constructor          
    Slice(MyPoint center, double radius, double startAngle, double angle, MyColor color){
        this.center = center;
        this.radius = radius;
        this.startAngle = startAngle;
        this.angle = angle;
        this.color = color;
        this.endAngle = startAngle + angle;
    }
    //setter methods
    public void setCenter(MyPoint p){this.center = p;}
    public void setRadius(double r){this.radius = r;}
    public void setStartAngle(double startAngle){this.startAngle = startAngle;}
    public void setAngle(double angle){this.angle = angle;}
    public void setColor(MyColor color){this.color = color;}
    public void setEndAngle(double endAngle){this.endAngle = endAngle;}
    //getter methods
    public MyPoint getCenter(){return this.center;}
    public double getRadius(){return this.radius;}
    public double getStartAngle(){return this.startAngle;}
    public double getAngle(){return this.angle;}
    public MyColor getMyColor(){return this.color;}
    public double getEndAngle(){return this.endAngle;}
    //method to draw the object in a canvas using a GraphicsContext object
    public void draw(GraphicsContext GC){
        GC.setFill(this.color.getJavaFxColor());
        GC.fillArc(center.getX()-radius, center.getY()-radius,
                   radius*2, radius*2, startAngle, angle, ArcType.ROUND);
    }
    //method to get a string representation of the object
    @Override
    public String toString(){
        return "Slice\nangle extension = " + angle + 
                "\nstart angle = " + startAngle + 
                "\ncenter point = " + center;
    }
}
