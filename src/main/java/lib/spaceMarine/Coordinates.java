package lib.spaceMarine;

import lib.utility.Validatable;

import java.util.Objects;
/**класс координат*/
public class Coordinates implements Validatable {
        private Long x; //Значение поля должно быть больше -42, Поле не может быть null
        private float y; //Значение поля должно быть больше -359, Поле не может быть null
    public Coordinates(Long x, float y){
        this.x=x;
        this.y=y;
    }
    /** возвращает true, если поля удовлетворяют условиям, иначе false */
    @Override
    public boolean validate() {
        if (x==null || x <= -42){
            return false;
        }
        return (y>=-359);
    }
    @Override
    public String toString() {
        return x + ";" + y;
    }
     @Override
    public int hashCode(){
        return Objects.hash(x,y);
    }
    @Override
    public boolean equals(Object o){
        if (this==o){
            return true;
        }
        if ((o==null)|(this.getClass()!=o.getClass())){
            return false;
        }
        Coordinates that=(Coordinates) o;
        return Objects.equals(x, that.x)&&Objects.equals(y,that.y);
    }
}