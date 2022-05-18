package com.github.evancolewright.headdrops;

import lombok.Getter;

@Getter
public enum HeadDropType
{
    NORMAL("normal","normal.drop_chance", "normal.head"),
    SLAIN("slain", "slain.drop_chance", "slain.head");

    private final String rootPath;
    private final String chancePath;
    private final String itemPath;

    HeadDropType(String rootPath, String chancePath, String itemPath)
    {
        this.rootPath = rootPath;
        this.chancePath = chancePath;
        this.itemPath = itemPath;
    }
}
