import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javafx.scene.canvas.GraphicsContext;

public class HistogramAlphaBet{
    Map<Character,Integer> frequencies = new HashMap<Character, Integer>();
    Map<Character,Double> probabilities = new HashMap<Character, Double>();
    HistogramAlphaBet(String str){  
        String text = str.replaceAll("[^a-zA-Z]", "").toLowerCase();
        for(int i = 0; i < text.length(); ++i){
            Character key = text.charAt(i);
            if(frequencies.containsKey(key)){
                frequencies.put(key,frequencies.get(key)+1);
            }
            else{
                frequencies.put(key,1);
            }
        }
        double totalProb = getSumOfFrequencies();
        for(Character K : frequencies.keySet()){
            probabilities.put(K, (double)frequencies.get(K)/totalProb);
        }
        probabilities = sortDownProbabilities();
    }
    HistogramAlphaBet(Map<Character, Integer> freq){
        this.frequencies = freq;
        frequencies = sortKeyOfFrequencies();
        double totalProb = getSumOfFrequencies();
        for(Character K : frequencies.keySet()){
            probabilities.put(K, (double)frequencies.get(K)/totalProb);
        }
        probabilities = sortDownProbabilities();
    }
    public int getSumOfFrequencies(){
        return frequencies.values().stream().reduce(0, Integer::sum);
    }
    public Map<Character,Integer> sortKeyOfFrequencies(){
        return frequencies.entrySet().stream()
        .sorted(Entry.comparingByValue())
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
    public Map<Character,Double> sortDownProbabilities(){
        return probabilities.entrySet().stream()
        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }
    public void printFrequencies(){
        System.out.print(frequencies);
    }
    public void printProbabilities(){
        System.out.print(probabilities);
    }
    public class MyPieChart{
        int n;                //number of most frequent characters
        Map<Character, Slice> pie;    
        double uStartAngle;     //the start angle defined by user
        MyPoint center;         //the center of the chart
        MyColor fillers[] = MyColor.values();   //Array of colors to fill the Slices
        double radius;          //radius of the chart
        double restProbabilities;        
        DecimalFormat formatter = new DecimalFormat("0.0000");

        MyPieChart(int n, double startAngle, MyPoint center, double radius){
            pie = new HashMap<Character, Slice>();
            this.n = n;
            this.uStartAngle = startAngle;
            this.center = center;
            this.radius = radius;
            int i = 0;
            double restAngle = 0;
            double carryProb = 0;
            for(Character k : probabilities.keySet()){
                double angle = 360*probabilities.get(k);
                if(i<n){
                    pie.put(k, new Slice(center, radius, startAngle,angle, fillers[i]));
                    startAngle += angle;
                }
                else{
                    restAngle += angle;
                    carryProb+=probabilities.get(k);
                }
                i++;
            }
            //pie.put('#', new Slice(center, radius, startAngle, restAngle, fillers[fillers.length-1]));
            //restProbabilities = 1-carryProb;
            //restProbabilities =carryProb;
        }
        public void draw(GraphicsContext GC){
            double textheight = 20;
            for(Character key:pie.keySet()){
                if(key=='#'){
                    pie.get(key).draw(GC);
                    GC.fillRect(10,textheight+10, 25,12);
                    GC.setFill(MyColor.Black.getJavaFxColor());
                    GC.fillText("Other letters: "+formatter.format(restProbabilities), 40, textheight+= 20);                  
                }
                else{
                    pie.get(key).draw(GC);
                    GC.fillRect(10,textheight + 10, 25,12);
                    GC.setFill(MyColor.Black.getJavaFxColor());
                    GC.fillText(key+": "+ formatter.format(probabilities.get(key)), 40, textheight+= 20);  
                }
            }
        }
        public void printPie(){
            System.out.println(pie.keySet());//check
        }
    }
}

        

    

