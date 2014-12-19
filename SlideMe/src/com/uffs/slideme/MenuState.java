package com.uffs.slideme;

import org.flixel.*;
import org.flixel.event.IFlxButton;
import com.badlogic.gdx.utils.IntArray;

public class MenuState extends FlxState {

	private FlxSprite backgnd;
	private FlxText title, footer, bestScore;
	private FlxButton btnPlay, btnCredits, btnSave, btnLoad, btnClear;
	private FlxSave gameSave;
				
	@Override
	public void create() {
		
		FlxG.shake();
		FlxG.vibrate(1200);
		
		gameSave = new FlxSave();
		gameSave.bind("SlidemeSave");
										
		backgnd = new FlxSprite(0,0).loadGraphic("background.png", false, false, FlxG.width, FlxG.height);
		backgnd.scale = new FlxPoint(2,2);
		backgnd.setOriginToCorner();
		add(backgnd);
		
		title = new FlxText(FlxG.width/2 - 70, FlxG.height/5 ,140, "SlideMe");
		title.setSize(30);
		title.setAlignment("center");
		add(title);
		
		footer = new FlxText(0,0, 100, "Developed by:\nTsukini");
		footer.y = FlxG.height - footer.height;
		footer.x = FlxG.width/2 - footer.width/2;
		footer.setColor(0xD3D3D3);
		footer.setAlignment("center");
		add(footer);
		
		bestScore = new FlxText(0, 0, FlxG.width/2, "Best score: 0");
		bestScore.x = FlxG.width/4;
		bestScore.y = title.y+title.height+5;
		bestScore.setAlignment("center");
		if (FlxG.scores.size > 0)
			bestScore.setText("Best score: " + FlxG.scores.get(1));
		add(bestScore);

		btnPlay = new FlxButton(0, FlxG.height/2, "Play",new IFlxButton(){@Override public void callback(){clickPlay();}});
		btnPlay.x = FlxG.width/2 - btnPlay.width/2;
		btnPlay.label.setAlignment("center");
		add(btnPlay);
		
		btnSave = new FlxButton(0,0, "Save", new IFlxButton(){@Override	public void callback(){ saveSave(); } });
		btnSave.x = FlxG.width/4;
		btnSave.y = FlxG.height/2 + btnPlay.height + 5;
		add(btnSave);
		
		btnLoad = new FlxButton(0,0, "Load", new IFlxButton(){@Override	public void callback(){ saveLoad(); } });
		btnLoad.x = FlxG.width/2;
		btnLoad.y = FlxG.height/2 + btnPlay.height + 5;
		add(btnLoad);
		
		btnClear = new FlxButton(0, 0, "Clear Data", new IFlxButton(){@Override	public void callback(){ saveClear(); } });
		btnClear.x = FlxG.width/2 - btnClear.width/2;
		btnClear.y = btnLoad.y + btnLoad.height + 5;
		add(btnClear);
		
		btnCredits = new FlxButton(0 ,0, "Credits", new IFlxButton(){@Override public void callback(){clickCredits();}});
		btnCredits.x = FlxG.width/2 - btnCredits.width/2;
		btnCredits.y = btnClear.y + btnClear.height + 5;
		add(btnCredits);
	}
			
	public void clickPlay(){
		FlxG.switchState(new PlayState());
	}
	
	public void clickCredits(){
		FlxG.switchState(new CreditsState());
	}
	
	private void saveSave(){
		IntArray scores;
		// Don't have a save?
		if (gameSave.data.get("scores", IntArray.class) == null){
			// Instantiate the local data with the save data
			scores = FlxG.scores;
			// Need to make a save to keep the data closing game before Game Over
		} // Have a save?
		else {
			scores = gameSave.data.get("scores", IntArray.class);
			scores = FlxG.scores;
		}
		gameSave.data.put("scores", scores);
		gameSave.flush();
	}
	
	private void saveLoad(){
		IntArray scores = gameSave.data.get("scores", IntArray.class);
		// Fumiko player;
		
		// Don't have a save?
		if (scores == null){
			FlxG.scores.add(0); // Keep the last score
			FlxG.scores.add(0); // Keep the best score
		} else{
			FlxG.scores = scores;
		}	
	}
	
	private void saveClear(){
		gameSave.erase();
	}
}
