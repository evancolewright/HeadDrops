package com.evancolewright.headdrops;

public enum HeadType
{
    DEFAULT("default_drop_chance", "default_head"),
    SLAIN("slain_drop_chance", "slain_head");

    private final String chancePath;
    private final String itemPath;

    HeadType(String chancePath, String itemPath)
    {
        this.chancePath = chancePath;
        this.itemPath = itemPath;
    }

    public String getChancePath()
    {
        return chancePath;
    }

    public String getItemPath()
    {
        return itemPath;
    }
}
