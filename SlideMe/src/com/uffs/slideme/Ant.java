package com.uffs.slideme;

public class Ant extends Mob
{
	private String _antImg = "ant.png";
	
	public Ant()
	{
		super(0, 0);
		loadGraphic(_antImg, true, true, 47, 24);
		addAnimation("idle", new int[] {0});
		addAnimation("walking", new int[] {1, 2}, 2, true);
		addAnimation("attaking", new int[] {3}, 1);
		
		play("walking");
		
		kill(); // Ants will be enemies, an FlxGroup will handle it
	}
	
	@Override
	public void update()
	{
		super.update();
		
		acceleration.y = 120;
	}
}