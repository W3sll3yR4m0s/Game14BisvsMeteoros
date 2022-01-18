package dev.medi.code.bis.game.engines;

import org.cocos2d.layers.CCLayer;

import java.util.Random;

import dev.medi.code.bis.config.Assets;
import dev.medi.code.bis.config.Runner;
import dev.medi.code.bis.game.interfaces.MeteorsEngineDelegate;
import dev.medi.code.bis.game.objects.Meteor;

public class MeteorsEngine extends CCLayer {
	
	private MeteorsEngineDelegate delegate;

	private int seed = 1;

//	// Standard Class Constructor
//    public MeteorsEngine(int score) {
//        if (score != 0) {
//            this.seed = score;
//        }
//
//    }

	// Class Constructor
    public MeteorsEngine(int score) {
        this.schedule("meteorsEngine", 1.0f / 10f);

        this.seed = score;
    }

    public void meteorsEngine(float dt) {

        // Pause
        if (Runner.check().isIsGamePlaying() && !Runner.check().isIsGamePaused()) {

            if (new Random().nextInt(20 - (15*(this.seed/100))) == 0) {
                this.getDelegate().createMeteor(new Meteor(Assets.METEOR));
            }
        }

//        // Luck: 1 in 30 generates a new meteors
//        if (new Random().nextInt(30) == 0) {
//            this.getDelegate().createMeteor(new Meteor(Assets.METEOR));
//        }
    }

    public void setDelegate(MeteorsEngineDelegate delegate) {
        this.delegate = delegate;
    }

    public MeteorsEngineDelegate getDelegate() {
        return delegate;
    }


}
