package dev.medi.code.bis.game.interfaces;

import dev.medi.code.bis.game.objects.Shoot;

public interface ShootEngineDelegate {

    public void createShoot(Shoot shoot);
    public void removeShoot(Shoot shoot);

}
