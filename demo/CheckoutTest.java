package demo;

import org.junit.Test;

import demo.Tool.ToolType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.Assert;

public class CheckoutTest {

    Tool t1 = new Tool("CHNS", ToolType.CHAINSAW, "Stihl");
    Tool t2 = new Tool("LADW", ToolType.LADDER, "Werner");
    Tool t3 = new Tool("JAKD", ToolType.JACKHAMMER, "DeWalt");
    Tool t4 = new Tool("JAKR", ToolType.JACKHAMMER, "Ridgid");

    Charge c1 = new Charge(ToolType.LADDER, 1.99f, true, true, false);
    Charge c2 = new Charge(ToolType.CHAINSAW, 1.49f, true, false, true);
    Charge c3 = new Charge(ToolType.JACKHAMMER, 2.99f, true, false, false);

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy");



    @Test(expected = IllegalArgumentException.class)
    public void constructorExceptionTest(){
        LocalDate date = LocalDate.parse("09/03/15", dtf);
        Checkout ch = new Checkout(t4, c3, date, 5, 101);
    }

    @Test
    public void testJuly4th() {
        LocalDate date = LocalDate.parse("07/02/20", dtf);
        Checkout ch = new Checkout(t2, c1, date, 3, 10);
        Assert.assertEquals(ch.finalPrice, 3.58, 0.001);
        ch = new Checkout(t4, c3, date, 4, 50);
        Assert.assertEquals(ch.finalPrice, 1.49, 0.001);
        date = LocalDate.parse("07/02/15", dtf);
        ch = new Checkout(t1, c2, date, 5, 25);
        Assert.assertEquals(ch.finalPrice, 3.35, 0.001);
        ch = new Checkout(t1, c2, date, 9, 0);
        Assert.assertEquals(ch.finalPrice, 8.94, 0.001);
    }
    @Test
    public void testLaborDay() {
        LocalDate date = LocalDate.parse("09/03/15", dtf);
        Checkout ch = new Checkout(t3, c3, date, 6, 0);
        Assert.assertEquals(ch.finalPrice, 8.97, 0.001);
    }
}
