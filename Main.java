import demo.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import demo.Tool.ToolType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Main{
    public static void main(String[] args)
    {
        //create a tool
        ToolType t = ToolType.LADDER;
        String toolCode = "LADW";
        String brand = "Werner";
        Tool tool = new Tool(toolCode, t, brand);
        Charge charge = new Charge(t, 1.99f, true, true, false);
        String day = "09/03/15";
        int numDays = 5;
        int discount = 20;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy");
        LocalDate date = LocalDate.parse(day, dtf);

        Checkout check = new Checkout(tool, charge, date, numDays, discount);
        check.printContract();
        while(true)
        {
            //wait forever
        }
    }
}