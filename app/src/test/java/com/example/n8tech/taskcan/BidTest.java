/*
 *  Copyright (c) 2018 Alexander Filbert, Carolyn Binns, Jeanna Somoza, 	JingMing Huang, Matthew Quigley, Nathanael Belayneh
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.n8tech.taskcan;

import android.test.ActivityInstrumentationTestCase2;

import com.example.n8tech.taskcan.Models.Bid;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Views.SignInActivity;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by cbinns on 2/22/2018.
 * Edited by msquigle on 2/23/2018.
 */

public class BidTest {

    public BidTest() {

    }

    @Test
    public void testAddBidder() {
        User user1 = new User("Joe", "7355608", "joe@n8tech.com", "123-456-7890");
        Bid bid1 = new Bid();
        bid1.setBidder(user1);
        bid1.setBidAmount(12.21);
        assertEquals(bid1.getBidder(), user1);
        assertEquals(bid1.getBidAmount(), 12.21, 0.00);
    }
}
