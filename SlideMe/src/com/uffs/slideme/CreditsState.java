package com.uffs.slideme;

import org.flixel.*;
import org.flixel.event.IFlxButton;


public class CreditsState extends FlxState {

	private FlxText crdts;
	private FlxButton btnBack;
	
	@Override
	public void create() {
		crdts = new FlxText( FlxG.width/4, FlxG.height-10, FlxG.width/2,
				"TSUKINI:\n\tGabriel H. Rudey\n\tTiago A. Debastiani\n\n" +
				"ASSETS:\n\tFumiko: Sylvius Fischer\n\tBackground: Dovyski"
				);
		add(crdts);
		
		btnBack = new FlxButton(5,5,"Back", new IFlxButton(){@Override public void callback(){ClickBack();}});
		btnBack.label.setAlignment("center");
		btnBack.setAlpha(0);
		add(btnBack);
	}
	
	@Override
	public void update(){
		crdts.y -= 0.3;
		
		if (!crdts.onScreen()){
			crdts.kill();
			btnBack.setAlpha(1);
		}
	}
	
	private void ClickBack()
	{
		FlxG.switchState(new MenuState());
	}

}