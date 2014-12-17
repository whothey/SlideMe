package com.uffs.slideme;

import org.flixel.FlxG;
import org.flixel.FlxGroup;
import org.flixel.ui.FlxVirtualPad;
import org.flixel.FlxButton;
import org.flixel.FlxPoint;


public class Fumiko extends Mob
{	
	protected String ImgFumiko = "player_shoot.png";
	protected FlxVirtualPad _pad;
	protected FlxGroup bullets;
	
	protected boolean justShoot = false, isShooting;
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
		addAnimation("jumping", new int[]{9, 10}, 2);
		addAnimation("falling", new int[]{11, 12}, 2);
		addAnimation("sliding", new int[]{12});
		addAnimation("shoot", new int[]{13,14,15,16,17},5, true);
		
		play("idle");
		
		drag.x = 200;
		drag.y = 60;
	}
	
	public boolean isSliding()
	{
		if ((FlxG.keys.RIGHT || _pad.buttonRight.status == FlxButton.PRESSED) ||
			(FlxG.keys.LEFT || _pad.buttonLeft.status == FlxButton.PRESSED)   ||
			(FlxG.keys.UP || _pad.buttonB.status == FlxButton.PRESSED && isTouching(DOWN)))
			return false;
		
		return true;
	}
	
	public void update()
	{
		super.update();

		// Animation Control
		if (velocity.y < 0) {
			if (isShooting)
				play("shoot");
			else
				play("jumping");
		} else if(velocity.y > 0) {
			if (isShooting)
				play("shoot");
			else
				play("falling");
		} else if (velocity.x == 0) {
			if (isShooting)
				play("shoot");
			else
				play("idle");
		} else if (isSliding()) {
			play("sliding");
		} else if (velocity.x != 0) {
			if (isShooting)
				play("shoot");
			else
				play("running");
		} else if (isTouching(DOWN)){
			if (isShooting)
				play("shoot");
			else
				play("idle");
		}
		
		// Movement
		acceleration.y = 100; // Gravity
		acceleration.x = 0;
		if (FlxG.keys.RIGHT || _pad.buttonRight.status == FlxButton.PRESSED) {
			setFacing(RIGHT);
			velocity.x = 100;
			acceleration.x += drag.x;

			if (isTouching(DOWN))
				play("running");
			
		} else if (FlxG.keys.LEFT || _pad.buttonLeft.status == FlxButton.PRESSED) {
			setFacing(LEFT);
			velocity.x = -100;
			acceleration.x -= drag.x;

			if (isTouching(DOWN))
				play("running");
		}
		
		// Jumps
		if ((FlxG.keys.UP || _pad.buttonB.status == FlxButton.PRESSED) && isTouching(DOWN)) {
			velocity.y = -60;
		}
		
		// Shooting
		if (shootTime > 0){
			shootTime -= FlxG.elapsed;
		}
		if (shootTime<=0){
			isShooting = false;
		}
		
		if ((FlxG.keys.A || _pad.buttonA.status == FlxButton.PRESSED)){
			if (!justShoot)
				shoot();
			else
				if (shootTime <= 0)
					justShoot = false;
		}
	}
	
	private void shoot(){
		Pencil bullet;
		bullet = (Pencil)( (PlayState)FlxG.getState() ).getBullet().getFirstAvailable();
		isShooting = true;
		
		if (bullet != null){
			if (getFacing() == LEFT)
				bullet.shoot(new FlxPoint(getMidpoint().x-width, getMidpoint().y), getFacing());
			else
				bullet.shoot(getMidpoint(), getFacing());
			justShoot = true;
			shootTime = 0.4f;
		}
	}
}