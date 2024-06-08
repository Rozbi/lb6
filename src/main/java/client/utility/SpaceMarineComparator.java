package client.utility;

import lib.spaceMarine.SpaceMarine;

import java.util.Comparator;

/**компаратор для Pririty Queue*/
public class SpaceMarineComparator implements Comparator<SpaceMarine> {
    public int compare(SpaceMarine sm1, SpaceMarine sm2){
        if (sm1.getId()>sm2.getId()){
            return 1;
        } else{
            return -1;
        }
    }
}

