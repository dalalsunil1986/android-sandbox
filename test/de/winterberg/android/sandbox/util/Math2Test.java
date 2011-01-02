package de.winterberg.android.sandbox.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Benjamin Winterberg
 */
public class Math2Test {

    @Test
    public void testCollision() throws Exception {
        
    }

    @Test
    public void testBetween() throws Exception {
        Assert.assertTrue(Math2.between(2, 1, 3));
        Assert.assertTrue(Math2.between(2, 3, 1));
        Assert.assertTrue(Math2.between(2, 2, 1));
        Assert.assertFalse(Math2.between(2, 3, 4));
    }

}