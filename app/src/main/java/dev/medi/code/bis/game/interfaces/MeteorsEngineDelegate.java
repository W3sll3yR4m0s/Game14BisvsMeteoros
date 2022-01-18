package dev.medi.code.bis.game.interfaces;

import dev.medi.code.bis.game.objects.Meteor;

public interface MeteorsEngineDelegate {

    public void createMeteor(Meteor meteor);
    public void removeMeteor(Meteor meteor);

//    public void createMeteor(
//            Meteor meteor, float x, float y, float vel,
//            double ang, int vl
//    );

}
