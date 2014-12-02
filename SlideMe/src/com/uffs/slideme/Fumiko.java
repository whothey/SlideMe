package com.uffs.slideme;

import org.flixel.FlxG;
import org.flixel.FlxSprite;
import org.flixel.ui.FlxVirtualPad;
import org.flixel.FlxButton;

public class Fumiko extends FlxSprite
{
	protected String ImgFumiko = "player.png";
	protected FlxVirtualPad _pad;
	
	public Fumiko(int x, int y, FlxVirtualPad pad)
	{
		super(x, y);
		_pad = pad;
		
		loadGraphic(ImgFumiko, true, true, 28, 24);
		
		// Animations
		addAnimation("idle", new int[]{0});
		addAnimation("walking", new int[]{0, 1, 2});
		addAnimation("running", new int[]{3, 4, 5});
		addAnimation("jumping", new int[]{9, 10, 11, 12});
		
		play("idle");
	}
	
	public void update()
	{
		super.update();
		
		// Movement
		acceleration.x = 0;
		if (FlxG.keys.RIGHT || _pad.buttonRight.status == FlxButton.PRESSED) {
			setFacing(RIGHT);
			acceleration.x += drag.x;
		} else if (FlxG.keys.LEFT || _pad.buttonLeft.status == FlxButton.PRESSED) {
			setFacing(LEFT);
			acceleration.x -= drag.x;
		}
	}
}
