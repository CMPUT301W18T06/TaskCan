package com.example.n8tech.taskcan;

import com.example.n8tech.taskcan.Models.NotificationContent;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing for NotificationContent class.
 *
 * @see NotificationContent
 * @author CMPUT301W18T06
 */
public class NotificationContentTest {
    public void NotificationContentTest(){
    }

    @Test
    public void testNotification(){
        NotificationContent notif = new NotificationContent(null, "channel ID", "title", "description");
        assertEquals("channel ID", notif.getChannelID());
        assertEquals("title", notif.getTitle());
        assertEquals("description", notif.getDescription());
        assertEquals(null, notif.getContext());
    }


}
