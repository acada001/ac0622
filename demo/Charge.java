package demo;

import demo.Tool.ToolType;

public class Charge {
    private ToolType type;
    private float charge;
    private boolean weekday;
    private boolean weekend;
    private boolean holiday;

    public Charge(ToolType type, float charge, boolean weekday, boolean weekend, boolean holiday)
    {
        this.type = type;
        this.charge = charge;
        this.weekday = weekday;
        this.weekend = weekend;
        this.holiday = holiday;
    }
    
    public ToolType getType()
    {
        return type;
    }
    public float getCharge()
    {
        return charge;
    }
    public boolean getWeekday()
    {
        return weekday;
    }
    public boolean getWeekend()
    {
        return weekend;
    }
    public boolean getHoliday()
    {
        return holiday;
    }
}
