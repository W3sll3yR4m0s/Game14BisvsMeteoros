package dev.medi.code.bis.game.objects;

import static dev.medi.code.bis.config.DeviceSettings.screenHeight;
import static dev.medi.code.bis.config.DeviceSettings.screenWidth;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.opengl.CCBitmapFontAtlas;

import dev.medi.code.bis.config.ConfigurationConstants;
import dev.medi.code.bis.game.scenes.GameScene;

public class Ammunition extends CCLayer {

    private int quantity;
    private CCBitmapFontAtlas text;

    private GameScene delegate;

    public void setDelegate(GameScene delegate) {
        this.delegate = delegate;
    }

    // Class Constructor
    public Ammunition() {

        this.quantity = ConfigurationConstants.quantityAmmunition;

        this.text = CCBitmapFontAtlas.bitmapFontAtlas(
                String.valueOf(this.quantity),
                "UniSansSemiBold_Numbers_240.fnt"
        );

        this.text.setScale((float) 240 / (240*4));

        this.setPosition(screenWidth() - 43, 85);
        this.addChild(this.text);
    }

    public void increase() {

        quantity++;
        this.text.setString(String.valueOf(this.quantity));
    }

    public void decrease() {

        if (quantity >= 0) {
            quantity--;
            this.text.setString(String.valueOf(this.quantity));
        }
    }

    public int getQuantity() {
        return this.quantity;
    }
}
