package com.dono.psakkos.dono;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception
    {
        Dono dono = new Dono();

        String password = dono.computePassword("helloworldofpasswords", "test");

        assertEquals("15698afa02c9628690d18d5cfe456f1a6e1f2181f029be3b431ec84a28d2f6!A", password);
    }
}