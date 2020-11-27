package com.example.danbidemo1;

import com.example.danbidemo1.controllers.SystemController;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addTag_isCorrect() {
        assertEquals("#청소년 #가정 #금연 ", SystemController.addTag("청소년,가정, 금연"));
    }

}