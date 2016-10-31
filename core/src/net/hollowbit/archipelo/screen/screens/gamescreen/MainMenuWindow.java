package net.hollowbit.archipelo.screen.screens.gamescreen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import net.hollowbit.archipelo.ArchipeloClient;
import net.hollowbit.archipelo.network.packets.LogoutPacket;
import net.hollowbit.archipelo.screen.screens.GameScreen;
import net.hollowbit.archipelo.tools.LM;

public class MainMenuWindow extends Window {
	
	GameScreen screen;
	
	//Ui
	TextButton logoutBtn;
	TextButton returnBtn;
	
	public MainMenuWindow (GameScreen screen, Stage stage) {
		super(LM.ui("mainMenu"), ArchipeloClient.getGame().getUiSkin());
		this.screen = screen;
		this.setStage(stage);
		this.setBounds(0, 0, 350, 200);
		this.setMovable(false);
		
		logoutBtn = new TextButton(LM.ui("logout"), getSkin());
		logoutBtn.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ArchipeloClient.getGame().getNetworkManager().sendPacket(new LogoutPacket());//Will receive a logout packet in response and then go to menu
				super.clicked(event, x, y);
			}
			
		});
		add(logoutBtn);
		row();
		
		returnBtn = new TextButton(LM.ui("returnToGame"), getSkin());
		returnBtn.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				remove();
				super.clicked(event, x, y);
			}
			
		});
		add(returnBtn);
	}

}
