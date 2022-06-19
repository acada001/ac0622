import demo.*;

import java.util.HashMap;
import java.io.Console;
import demo.Tool.ToolType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Main{
    public static void main(String[] args)
    {
        Console c = System.console();
        HashMap<String, Tool> tools = createTools();
        HashMap<ToolType, Charge> charges = createCharges();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy");

        String userInput = "";
        LocalDate date = LocalDate.now();
        int numDays = 0;
        int discount = 0;

        while(!userInput.equals("q"))
        {
            System.out.println("Available Tools:");
            for(Tool t : tools.values())
            {
                System.out.println(t.getCode());
            }
            userInput = c.readLine("Select tool code for Rent(q to quit): ");
            if(userInput.equals("q"))
            {
                System.out.println("Goodbye");
                continue;
            }
            else if(!validateSelection(userInput, tools))
            {
                System.out.println("Invalid tool code");
                continue;
            }
            Tool t = tools.get(userInput.toUpperCase());
            Charge ch = charges.get(t.getType());
            userInput = c.readLine("Checkout Date(MM/dd/yy) c to cancel: ");
            if(userInput.equals("c"))
            {
                System.out.println("Canceling order");
                continue;
            }
            try
            {
                date = LocalDate.parse(userInput, dtf);
            }
            catch(DateTimeParseException e)
            {
                System.out.println("invalid date");
                continue;
            }
            userInput = c.readLine("Number of days, c to cancel: ");
            if(userInput.equals("c"))
            {
                System.out.println("Canceling order");
                continue;
            }
            try {
                numDays = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("Input is not a number.");
                continue;
            }
            userInput = c.readLine("Discount, c to cancel: ");
            if(userInput.equals("c"))
            {
                System.out.println("Canceling order");
                continue;
            }
            try {
                discount = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("Input is not a number.");
                continue;
            }
            Checkout check = new Checkout(t, ch, date, numDays, discount);
            check.printContract();
            userInput = c.readLine("Hit Enter to continue");
        }
    }
    //hardcode inventory for now, can be upgraded later to read from a DB or xml file
    public static HashMap<String, Tool> createTools()
    {
        HashMap<String, Tool> tools = new HashMap<String, Tool>();

        Tool t1 = new Tool("CHNS", ToolType.CHAINSAW, "Stihl");
        Tool t2 = new Tool("LADW", ToolType.LADDER, "Werner");
        Tool t3 = new Tool("JAKD", ToolType.JACKHAMMER, "DeWalt");
        Tool t4 = new Tool("JAKR", ToolType.JACKHAMMER, "Ridgid");

        tools.put(t1.getCode(), t1);
        tools.put(t2.getCode(), t2);
        tools.put(t3.getCode(), t3);
        tools.put(t4.getCode(), t4);

        return tools;
    }

    //hardcode price details for now, can be upgraded later to read from a DB or xml file
    public static HashMap<ToolType, Charge> createCharges()
    {
        HashMap<ToolType, Charge> charges = new HashMap<ToolType, Charge>();

        Charge c1 = new Charge(ToolType.LADDER, 1.99f, true, true, false);
        Charge c2 = new Charge(ToolType.CHAINSAW, 1.49f, true, false, true);
        Charge c3 = new Charge(ToolType.JACKHAMMER, 2.99f, true, false, false);

        charges.put(c1.getType(), c1);
        charges.put(c2.getType(), c2);
        charges.put(c3.getType(), c3);

        return charges;
    }

    public static boolean validateSelection(String s, HashMap<String, Tool> tools)
    {
        boolean valid = false;
        if(tools.containsKey(s.toUpperCase()))
        {
            valid = true;
        }
        return valid;
    }
}