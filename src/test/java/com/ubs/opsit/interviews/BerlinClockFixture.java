package com.ubs.opsit.interviews;

import static com.ubs.opsit.interviews.support.BehaviouralTestEmbedder.aBehaviouralTestRunner;
import static org.assertj.core.api.Assertions.assertThat;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import org.junit.Test;

import com.ubs.opsit.interviews.impl.TimeConverterImpl;

/**
 * Acceptance test class that uses the JBehave (Gerkin) syntax for writing stories.  You should not need to
 * edit this class to complete the exercise, this is your definition of done.
 */
public class BerlinClockFixture {

    private TimeConverterImpl berlinClock = new TimeConverterImpl();
    private String theTime;

    @Test
    public void testYellowLampShouldBlinkOnOffEveryTwoSeconds() {
        Assert.assertEquals("Y", berlinClock.getBerlinSeconds(0));
        Assert.assertEquals("O", berlinClock.getBerlinSeconds(1));
        Assert.assertEquals("O", berlinClock.getBerlinSeconds(59));
    }
 
    @Test
    public void testTopHoursShouldHave4Lamps() {
        Assert.assertEquals(4, berlinClock.getTopBerlinHours(7).length());
    }
 
    // Top hours should light a red lamp for every 5 hours
    @Test
    public void testTopHoursShouldLightRedLampForEvery5Hours() {
        Assert.assertEquals("OOOO", berlinClock.getTopBerlinHours(0));
        Assert.assertEquals("RROO", berlinClock.getTopBerlinHours(13));
        Assert.assertEquals("RRRR", berlinClock.getTopBerlinHours(23));
        Assert.assertEquals("RRRR", berlinClock.getTopBerlinHours(24));
    }
 
    // Bottom hours should have 4 lamps
    @Test
    public void testBottomHoursShouldHave4Lamps() {
        Assert.assertEquals(4, berlinClock.getBottomBerlinHours(5).length());
    }
 
    // Bottom hours should light a red lamp for every hour left from top hours
    @Test
    public void testBottomHoursShouldLightRedLampForEveryHourLeftFromTopHours() {
        Assert.assertEquals("OOOO", berlinClock.getBottomBerlinHours(0));
        Assert.assertEquals("RRRO", berlinClock.getBottomBerlinHours(13));
        Assert.assertEquals("RRRO", berlinClock.getBottomBerlinHours(23));
        Assert.assertEquals("RRRR", berlinClock.getBottomBerlinHours(24));
    }
 
    // Top minutes should have 11 lamps
    @Test
    public void testTopMinutesShouldHave11Lamps() {
        Assert.assertEquals(11, berlinClock.getTopBerlinMinutes(34).length());
    }
 
    // Top minutes should have 3rd, 6th and 9th lamps in red to indicate first quarter, half and last quarter
    @Test
    public void testTopMinutesShouldHave3rd6thAnd9thLampsInRedToIndicateFirstQuarterHalfAndLastQuarter() {
        String minutes32 = berlinClock.getTopBerlinMinutes(32);
        Assert.assertEquals("R", minutes32.substring(2, 3));
        Assert.assertEquals("R", minutes32.substring(5, 6));
        Assert.assertEquals("O", minutes32.substring(8, 9));
    }
 
    // Top minutes should light a yellow lamp for every 5 minutes unless it's first quarter, half or last quarter
    @Test
    public void testTopMinutesShouldLightYellowLampForEvery5MinutesUnlessItIsFirstQuarterHalfOrLastQuarter() {
        Assert.assertEquals("OOOOOOOOOOO", berlinClock.getTopBerlinMinutes(0));
        Assert.assertEquals("YYROOOOOOOO", berlinClock.getTopBerlinMinutes(17));
        Assert.assertEquals("YYRYYRYYRYY", berlinClock.getTopBerlinMinutes(59));
    }
 
    // Bottom minutes should have 4 lamps
    @Test
    public void testBottomMinutesShouldHave4Lamps() {
        Assert.assertEquals(4, berlinClock.getBottomBerlinMinutes(0).length());
    }
 
    // Bottom minutes should light a yellow lamp for every minute left from top minutes
    @Test
    public void testBottomMinutesShouldLightYellowLampForEveryMinuteLeftFromTopMinutes() {
        Assert.assertEquals("OOOO", berlinClock.getBottomBerlinMinutes(0));
        Assert.assertEquals("YYOO", berlinClock.getBottomBerlinMinutes(17));
        Assert.assertEquals("YYYY", berlinClock.getBottomBerlinMinutes(59));
    }
 
    // Berlin Clock should result in array with 5 elements
    @Test
    public void testBerlinClockShouldResultInArrayWith5Elements()  {
        Assert.assertEquals(5, berlinClock.convertTime("13:17:01").length);
    }
 
    // Berlin Clock it should "result in correct seconds, hours and minutes" in {
    @Test
    public void testBerlinClockShouldResultInCorrectSecondsHoursAndMinutes() {
        String[] berlinTime = berlinClock.convertTime("16:37:16");
        String[] expected = new String[] {"Y", "RRRO", "ROOO", "YYRYYRYOOOO", "YYOO"};
        Assert.assertEquals(expected.length, berlinTime.length);
        for (int index = 0; index < expected.length; index++) {
            Assert.assertEquals(expected[index], berlinTime[index]);
        }
    }
 
    
    @Test
    public void berlinClockAcceptanceTests() throws Exception {
        aBehaviouralTestRunner()
                .usingStepsFrom(this)
                .withStory("berlin-clock.story")
                .run();
    }

    @When("the time is $time")
    public void whenTheTimeIs(String time) {
        theTime = time;
    }

    @Then("the clock should look like $")
    public void thenTheClockShouldLookLike(String theExpectedBerlinClockOutput) {
        assertThat(berlinClock.convertTime(theTime)).isEqualTo(theExpectedBerlinClockOutput);
    }
}
