package br.ufs.projetopoo.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

//Simplifica o uso do BigDecimal para operações matematicas precisas
public class BigDecimalUtils {

    // Transforma um objeto qualquer em Bigdecimal
    public static BigDecimal toBigDecimal(Object valor){

        if (valor == null) {
                return BigDecimal.ZERO;
            }
            if (valor instanceof BigDecimal) {
                return (BigDecimal) valor;
            }
            if (valor instanceof String) {
                return new BigDecimal((String) valor);
            }
            if (valor instanceof Float) {
                return new BigDecimal(Float.toString((Float) valor));
            }
            if (valor instanceof Double) {
                return new BigDecimal(Double.toString((Double) valor));
            }
            if (valor instanceof Number) {
                return new BigDecimal(valor.toString());
            }
            throw new IllegalArgumentException("Tipo não suportado");
    }
    //Soma 2 objetos
    public static BigDecimal somar(Object a, Object b){

        BigDecimal _a = toBigDecimal(a);
        BigDecimal _b = toBigDecimal(b);

        return _a.add(_b);

    }
    //Subtrai 2 objetos
    public static BigDecimal subtrair(Object a, Object b){

        BigDecimal _a = toBigDecimal(a);
        BigDecimal _b = toBigDecimal(b);

        return _a.subtract(_b);

    }
    //Multiplica 2 objetos
    public static BigDecimal multiplicar(Object a, Object b){

        BigDecimal _a = toBigDecimal(a);
        BigDecimal _b = toBigDecimal(b);

        return _a.multiply(_b);

    }
    // Divide 2 objetos
    public static BigDecimal dividir(Object a, Object b){

        BigDecimal _a = toBigDecimal(a);
        BigDecimal _b = toBigDecimal(b);

        //Exceção da divisão por zero
        if (_b.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Divisão por zero");
        }

        return _a.divide(_b,2, RoundingMode.HALF_UP);
    }
}
    
