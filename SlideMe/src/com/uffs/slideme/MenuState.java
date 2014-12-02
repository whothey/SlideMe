package com.uffs.slideme;

import org.flixel.*;
import org.flixel.event.IFlxButton;

public class MenuState extends FlxState {

	private FlxSprite backgnd;
	private FlxText title;
	private FlxText footer;
	private FlxButton btnPlay;
	private FlxButton btnCredits;
	
	
	@Override
	public void create() {
		
		FlxG.debug = true;
				
		backgnd = new FlxSprite(0,0).loadGraphic("background.png", false, false, FlxG.width, FlxG.height);
		backgnd.scale = new FlxPoint(2,2);
		backgnd.setOriginToCorner();
		add(backgnd);
		
		FlxG.shake();
		
		title = new FlxText(FlxG.width/2 - 70, FlxG.height/5 ,140, "SlideMe");
		title.setSize(30);
		title.setAlignment("center");
		add(title);
		
		footer = new FlxText(FlxG.width/2 - 70, FlxG.height - 40, 140, "Developed by:\nTsukini");
		footer.setColor(0xD3D3D3);
		footer.setSize(12);
		footer.setAlignment("center");
		add(footer);
		
		btnPlay = new FlxButton(0, FlxG.height/2, "Play",new IFlxButton(){@Override public void callback(){ClickPlay();}});
		btnPlay.x = FlxG.width/2 - btnPlay.width/2;
		btnPlay.label.setColor(0x00000666);
		btnPlay.label.setAlignment("center");
		add(btnPlay);
		
		btnCredits = new FlxButton(0 ,0, "Credits", new IFlxButton(){@Override public void callback(){ClickCredits();}});
		btnCredits.x = FlxG.width/2 - btnCredits.width/2;
		btnCredits.y = FlxG.height/2 + btnPlay.height + 5;
		add(btnCredits);
		
	}
		
	public void ClickPlay()
	{
		FlxG.switchState(new PlayState());
	}
	
	public void ClickCredits()
	{
		FlxG.switchState(new CreditsState());
	}

}
