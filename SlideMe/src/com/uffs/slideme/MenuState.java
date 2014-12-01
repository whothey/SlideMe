package com.uffs.slideme;

import org.flixel.*;
import org.flixel.event.IFlxButton;

public class MenuState extends FlxState {

	private FlxSprite backgnd;
	private FlxText title;
	private FlxText footer;
	private FlxButton btnPlay;
	
	
	@Override
	public void create() {
		
		FlxG.debug = true;
		
		backgnd = new FlxSprite(0,0,"background.png");
		//backgnd.makeGraphic(FlxG.width, FlxG.height);
		add(backgnd);
		
		title = new FlxText(FlxG.width/2 - 50  ,10,100, "SlideMe");
		title.setSize(30);
		title.setAlignment("center");
		title.setColor(0x00ff00f0);
		add(title);
		
		footer = new FlxText(FlxG.width/2 - 70, FlxG.height - 40, 140, "Developed by:\nTsukini");
		footer.setColor(0x000000ff);
		footer.setSize(15);
		footer.setAlignment("center");
		add(footer);
		
		btnPlay = new FlxButton(FlxG.width/2, FlxG.height/2, "Play",new IFlxButton(){@Override public void callback(){ClickPlay();}});
		btnPlay.setColor(0x0000f600);
		btnPlay.label.setColor(0x00000666);
		btnPlay.label.setAlignment("center");
		add(btnPlay);
		
	}
		
	public void ClickPlay()
	{
		FlxG.switchState(new PlayState());
	}

}
