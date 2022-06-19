package demo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.lang.StringBuilder;
import java.text.DecimalFormat;

//import demo.Tool.ToolType;

public class Checkout {
    public Tool tool;
    public Charge charge;
    public int numDays;
    public LocalDate outDate;
    public LocalDate dueDate;
    public int discount;
    public int chargeDays;
    public float startPrice;
    public float discountPrice;
    public float finalPrice;
    public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy");
    public DecimalFormat df = new DecimalFormat();

    public Checkout(Tool tool, Charge charge, LocalDate date, int numdays, int discount) throws IllegalArgumentException
    {
        if(numdays < 1 || discount < 0 || discount > 100)
        {
            throw new IllegalArgumentException("Cannot be illegal day or discount argument");
        }
        df.setMaximumFractionDigits(2);
        this.tool = tool;
        this.charge = charge;
        this.outDate = date;
        this.numDays = numdays;
        this.discount = discount;
        outDate = date;
        generateContract();
    }
    
    //generate the contract based on input values
    public void generateContract()
    {
        dueDate = outDate.plusDays(numDays);
        chargeDays = calculateChargeDays();
        startPrice = charge.getCharge() * chargeDays;
        float percent = discount;
        percent = percent/100;
        discountPrice = startPrice * percent;
        BigDecimal value = new BigDecimal(discountPrice).setScale(2, RoundingMode.HALF_UP);
        discountPrice = value.floatValue();
        finalPrice = startPrice - discountPrice;
    }

    private int calculateChargeDays()
    {
        int numChargeDays = 0;
        LocalDate currentday = outDate.plusDays(1);
        for(int i = 1; i <= numDays; i++)
        {
            if(!charge.getHoliday())
            {
                //check july 4th
                if(currentday.getMonth() == Month.JULY && currentday.getDayOfMonth() == 4)
                {
                    if(currentday.getDayOfWeek() == DayOfWeek.SUNDAY)
                    {
                        if(charge.getWeekend())
                        {
                            numChargeDays++;
                        }
                        //skip Monday
                        i++;
                        currentday = currentday.plusDays(2);
                        
                    }
                    else if(currentday.getDayOfWeek() == DayOfWeek.SATURDAY)
                    {
                        if(!charge.getWeekend() && numChargeDays > 0)
                        {
                            //Friday was charged
                            numChargeDays--;
                        }
                        currentday = currentday.plusDays(1);
                    }
                    else 
                    {
                        currentday = currentday.plusDays(1);
                    }
                    continue;                    
                }
                //checkLaborDay
                else if(isLaborDay(currentday))
                {
                    currentday = currentday.plusDays(1);
                    continue;
                }
                //july 4th edge case where last day is Friday July 3rd and Holidays are off
                if(currentday.getMonth() == Month.JULY && currentday.getDayOfMonth() == 3 && currentday.getDayOfWeek() == DayOfWeek.FRIDAY && i == numDays)
                {
                    continue;
                }

            }
            
            //check weekends
            if(!charge.getWeekend())
            {
                if((currentday.getDayOfWeek() == DayOfWeek.SATURDAY) || (currentday.getDayOfWeek() == DayOfWeek.SUNDAY))
                {
                    currentday = currentday.plusDays(1);
                    continue;
                }
            }
            numChargeDays++;
            currentday = currentday.plusDays(1);
        }

        return numChargeDays;
    }
    //shcekc to see if date is laborday
    private boolean isLaborDay(LocalDate date)
    {
        boolean laborDay = false;
        //first monday of Septemeber has to meet these conditions
        if(date.getMonth() == Month.SEPTEMBER && date.getDayOfMonth() < 8 && date.getDayOfWeek() == DayOfWeek.MONDAY)
        {
            laborDay = true;
        }
        return laborDay;
    }

    public void printContract()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Tool code: " + tool.getCode() + "\n");
        sb.append("Tool type: " + tool.getType() + "\n");
        sb.append("Tool brand: " + tool.getBrand() + "\n");
        sb.append("Rental Days: " + numDays + "\n");
        sb.append("CheckoutDate: " + dtf.format(outDate) + "\n");
        sb.append("Due Date: " + dtf.format(dueDate) + "\n");
        sb.append("Daily Rental Charge: $" + charge.getCharge() + "\n");
        sb.append("Charge Days: " + chargeDays + "\n");
        sb.append("Pre-Discount Charge: $" + df.format(startPrice) + "\n");
        sb.append("Discount Percent: " + discount + "%\n");
        sb.append("Discount Amount: $" + df.format(discountPrice) + "\n");
        sb.append("Final Price: $" + df.format(finalPrice));
        String output = sb.toString();
        System.out.println(output);
    }

}
