package com.uffs.slideme;

import org.flixel.FlxG;
import org.flixel.FlxGroup;
import org.flixel.FlxSprite;
import org.flixel.ui.FlxVirtualPad;
import org.flixel.FlxButton;
import org.flixel.FlxPoint;


public class Fumiko extends FlxSprite
{
	protected String ImgFumiko = "player.png";
	protected FlxVirtualPad _pad;
	protected FlxGroup bullets;
		
	protected boolean justShoot = false;
	protected float shootTime;
	
	public Fumiko(int x, int y, FlxVirtualPad pad)
	{
		super(x, y);
		_pad = pad;
		
		loadGraphic(ImgFumiko, true, true, 24, 31);
		
		// Animations
		addAnimation("idle", new int[]{0});
		addAnimation("walking", new int[]{0, 1, 2}, 5, true);
		addAnimation("running", new int[]{3, 4, 5}, 10, true);
		addAnimation("jumping", new int[]{9, 10, 11, 12});
		
		play("idle");
		
		drag.x = 100;
		
	}
	
	public void update()
	{
		super.update();
		
		// Movement
		acceleration.y = 100; // Gravity
		acceleration.x = 0;
		if (FlxG.keys.RIGHT || _pad.buttonRight.status == FlxButton.PRESSED) {
			setFacing(RIGHT);
			velocity.x = 60;
			acceleration.x += drag.x;
			play("walking");
		} else if (FlxG.keys.LEFT || _pad.buttonLeft.status == FlxButton.PRESSED) {
			setFacing(LEFT);
			velocity.x = -60;
			acceleration.x -= drag.x;
			play("walking");
		} else {
			play("idle");
		}
		
		if (shootTime > 0)
			shootTime -= FlxG.elapsed;
		
		//Shooting
		if (FlxG.keys.A || _pad.buttonA.status == FlxButton.PRESSED){
			if (!justShoot)
				shoot();
			else
				if (shootTime <= 0)
					justShoot = false;
		}
	}
	
	private void shoot(){
		Pencil bala;
		bala = (Pencil)((PlayState)FlxG.getState() ).getBullet().getFirstAvailable();
		
		if (bala != null){
			bala.shoot(new FlxPoint(x+width/2, y+height/2), getFacing());
			justShoot = true;
			shootTime = 0.2f;
		}
		
	}
}
