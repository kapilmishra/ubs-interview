package com.ubs.opsit.interviews.impl;

import java.util.Arrays;
import java.util.regex.Pattern;

import com.ubs.opsit.interviews.TimeConverter;

/**
 * The Class TimeConverterImpl.
 */
public class TimeConverterImpl implements TimeConverter
{

    /** The Constant INPUT TIME_REGEX_PATTERN. */
    public static final String INPUT_TIME_REGEX = "([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])";

    /** The Constant INPUT_TIME_PATTERN. */
    public static final Pattern INPUT_TIME_PATTERN = Pattern.compile(INPUT_TIME_REGEX);

    /** The Constant INVALID_TIME. */
    public static final String INVALID_TIME = "Invalid Time";

    /**
     * Convert time to Berlin clock representation.
     *
     * @param inputTime
     *            the input time
     * @return the string
     */
    @Override
    public String[] convertTime(String inputTime)
    {
        int[] splittedInputTime = Arrays.asList(inputTime.split(":")).stream().mapToInt(Integer::parseInt).toArray();
      
        
        return new String [] {
                        getBerlinSeconds(splittedInputTime[2]), 
                        getTopBerlinHours(splittedInputTime[0]),
                        getBottomBerlinHours(splittedInputTime[0]),
                        getTopBerlinMinutes(splittedInputTime[1]),
                        getBottomBerlinMinutes(splittedInputTime[1]) 
        };
    }


    /**
     * Gets the berlin seconds.
     *
     * @param number
     *            the number
     * @return the seconds
     */
    public String getBerlinSeconds(int number)
    {
        if (number % 2 == 0)
            return "Y";
        else
            return "O";
    }

    /**
     * Gets the top berlin hours.
     *
     * @param number
     *            the number
     * @return the top hours
     */
    public String getTopBerlinHours(int number)
    {
        return getOnOff(4, getTopNumberOfOnSigns(number));
    }

    /**
     * Gets the bottom berlin hours.
     *
     * @param number
     *            the number
     * @return the bottom hours
     */
    public String getBottomBerlinHours(int number)
    {
        return getOnOff(4, number % 5);
    }

    /**
     * Gets the top berlin minutes.
     *
     * @param number
     *            the number
     * @return the top minutes
     */
    public String getTopBerlinMinutes(int number)
    {
        return getOnOff(11, getTopNumberOfOnSigns(number), "Y").replaceAll("YYY", "YYR");
    }

    /**
     * Gets the bottom berlin minutes.
     *
     * @param number
     *            the number
     * @return the bottom minutes
     */
    public String getBottomBerlinMinutes(int number)
    {
        return getOnOff(4, number % 5, "Y");
    }

    /**
     * Gets the on off.
     *
     * @param lamps
     *            the lamps
     * @param onSigns
     *            the on signs
     * @return the on off
     */
    // Default value for onSign would be useful
    public String getOnOff(int lamps, int onSigns)
    {
        return getOnOff(lamps, onSigns, "R");
    }

    /**
     * Gets the on off.
     *
     * @param lamps
     *            the lamps
     * @param onSigns
     *            the on signs
     * @param onSign
     *            the on sign
     * @return the on off
     */
    public String getOnOff(int lamps, int onSigns, String onSign)
    {
        String out = "";
        // String multiplication would be useful
        for (int i = 0; i < onSigns; i++)
        {
            out += onSign;
        }
        for (int i = 0; i < (lamps - onSigns); i++)
        {
            out += "O";
        }
        return out;
    }

    /**
     * Gets the top number of on signs.
     *
     * @param number
     *            the number
     * @return the top number of on signs
     */
    public int getTopNumberOfOnSigns(int number)
    {
        return (number - (number % 5)) / 5;
    }
}