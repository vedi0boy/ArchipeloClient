package net.hollowbit.archipelo.screen.screens.mainmenu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import net.hollowbit.archipelo.ArchipeloClient;
import net.hollowbit.archipelo.network.PingGetter;
import net.hollowbit.archipelo.network.PingGetter.PingGetterListener;

public class ServerListing extends Table implements Comparable<ServerListing>{
	
	private static final String[] REGION_NAME = {"World", "North America East", "North America West", "South America East", "South America West", "East Asia", "West Asia", "South Asia", "Eastern Europe", "Western Europe", "Northern Africa", "Southern Africa", "Oceania"};
	private static final String[] TRAFFIC_NAME = {"Low", "Medium", "High"};
	
	private Label nameLabel;
	private Label regionLabel;
	private Label trafficLabel;
	private Label pingLabel;
	private TextButton connectButton;
	private int ping;
	
	public ServerListing (final String name, int region, int traffic, final String address, Skin skin) {
		setBounds(0, 0, 300, 25);
		
		new PingGetter().getPing(address, ArchipeloClient.PORT, getPingGetterListener());
		
		//Initialize labels
		nameLabel = new Label(name, skin);
		add(nameLabel).width(250);
		
		pingLabel = new Label("Connecting...", skin, "small");
		add(pingLabel).fill();
		
		connectButton = new TextButton("Connect", skin);
		connectButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ArchipeloClient.getGame().getNetworkManager().connect(address, ArchipeloClient.PORT);
				ArchipeloClient.getGame().getPrefs().setServer(name);
				super.clicked(event, x, y);
			}
		});
		add(connectButton);
		
		row();
		
		String color = "";
		if (traffic == 0)
			color = "[GREEN]";
		else if (traffic == 1)
			color = "[ORANGE]";
		else
			color = "[RED]";
		
		trafficLabel = new Label("Traffic: " + color + "" + TRAFFIC_NAME[traffic], skin, "small");
		add(trafficLabel).width(250);
		
		regionLabel = new Label("Region: " + REGION_NAME[region], skin, "small");
		add(regionLabel);
	}
	
	@Override
	public int compareTo (ServerListing o) {
		if (this.ping > o.ping)
			return 1;
		if (this.ping < o.ping)
			return -1;
		return 0;
	}
	
	public PingGetter.PingGetterListener getPingGetterListener () {
		return new PingGetterListener() {
			
			@Override
			public void pingRecieved (int ping) {
				if (ping >= 0) {
					String color = "";
					if (ping < 50)
						color = "[GREEN]";
					else if (ping < 300)
						color = "[ORANGE]";
					else
						color = "[RED]";
					pingLabel.setText(color + "" + ping + "ms");
				} else {
					pingLabel.setText("Error.");
					connectButton.setDisabled(true);
				}
			}
		};
	}
	
	
	
}
