package com.possessor;

import java.math.BigDecimal;

/**
 * Created by rpiotrowicz on 2017-02-01.
 */
public class MathHelper {

    public static BigDecimal getRounded(BigDecimal bigDecimal){
       return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
