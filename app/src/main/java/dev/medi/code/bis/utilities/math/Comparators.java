package dev.medi.code.bis.utilities.math;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCSprite;

//import java.lang.reflect.Method;

public class Comparators extends CCSprite {

//    private int intValue1, intValue2;
//    private float floatValue1, floatValue2;
//    private Comparators comparators;

//    // Standard Class Constructor
//    public Comparators() {
//
//    }

    private static boolean intRestDivision(int value1, int value2) {

        return value1 % value2 != 0;
    }

    private static boolean floatRestDivision(float value1, float value2) {

        return value1 % value2 != 0;
    }

}
