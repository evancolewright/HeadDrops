package com.github.evancolewright.headdrops.utilities;

import org.apache.commons.lang.WordUtils;

public final class StringUtils
{
    static String capitalizeMultiWordMaterialString(String string, String separator)
    {
        StringBuilder stringBuilder = new StringBuilder();
        String[] tokens = string.split(separator);
        for (int i = 0; i < tokens.length; ++i)
            stringBuilder.append(WordUtils.capitalize(tokens[i]) + (i == (tokens.length - 1) ? "" : " "));
        return stringBuilder.toString();
    }
}
