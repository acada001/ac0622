package demo;

public class Tool {
    
    public enum ToolType {
        LADDER,CHAINSAW,JACKHAMMER
    }
    //tool attributes
    private String toolCode;
    private ToolType type;
    private String brand;
    
    public Tool(String code, ToolType type, String brand)
    {
        this.toolCode = code;
        this.type = type;
        this.brand = brand;
    }

    public String getCode()
    {
        return this.toolCode;
    }
    public ToolType getType()
    {
        return this.type;
    }
    public String getBrand()
    {
        return this.brand;
    }


}
