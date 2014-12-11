package com.uffs.slideme;

import org.flixel.*;
import org.flixel.event.IFlxButton;


public class CreditsState extends FlxState {

	private FlxText credits;
	private FlxButton btnBack;
	private FlxSprite backGround;
	
	@Override
	public void create() {
		
		backGround = new FlxSprite(0,0).loadGraphic("background.png", false, false, FlxG.width, FlxG.height);
		backGround.scale = new FlxPoint(2,2);
		backGround.setOriginToCorner();
		add(backGround);
		
		credits = new FlxText( FlxG.width/4, FlxG.height-10, FlxG.width/2,
				"Team TSUKINI\nGabriel H. Rudey\nTiago A. Debastiani\n\n" +
				"ASSETS\nFumiko: Sylvius Fischer\nBackground: Dovyski\nPencil: May Cakez"
				);
		credits.setSize(12);
		credits.setAlignment("center");
		add(credits);
		
		btnBack = new FlxButton(5,5,"Back", new IFlxButton(){@Override public void callback(){ClickBack();}});
		btnBack.label.setAlignment("center");
		add(btnBack);
	}
	
	@Override
	public void update(){
		if (FlxG.mouse.pressed()){
			credits.velocity.y = -40;
		}else{
			credits.velocity.y = -20;
		}
		
		if (!credits.onScreen()){
			credits.y = FlxG.height-2;
		}
		super.update();
	}
	
	private void ClickBack()
	{
		FlxG.switchState(new MenuState());
	}
	/*
	@Override
	public void destroy(){
		super.destroy();
		credits.destroy();
		btnBack.destroy();
		backGround.destroy();
	}
	*/

}