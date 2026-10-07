import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public abstract class CSVReader {
    private int linesRead = 0;
    private int linesAccepted = 0;
    private int linesSkipped = 0;

    protected abstract boolean processLine(String line);

    public boolean read(String filename) {
        linesRead = 0;
        linesAccepted = 0;
        linesSkipped = 0;
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                linesRead++;
                if (processLine(line)) {
                    linesAccepted++;
                } else {
                    linesSkipped++;
                }
            }
            scanner.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int getLinesRead(){
        return linesRead;
    }
    public int getLinesAccepted(){return linesAccepted;}
    public int getLinesSkipped(){return linesSkipped;}
    public static Date parseDate(String text){
        if(text==null||text.length()!=10){
            return null;
        }
        try{
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            return dateFormat.parse(text);
        }
        catch (ParseException e){
            return null;
        }
    }
}