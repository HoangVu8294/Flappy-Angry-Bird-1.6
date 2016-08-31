package com.fab.game;
 
import com.badlogic.gdx.Game; 
import com.fab.helpers.AssetsLoader;
import com.fab.screens.CreditScreen;

public class FABGame extends Game {

	@Override
    public void create() { AssetsLoader.load(); setScreen(new CreditScreen(this)); }

    @Override
    public void dispose() { super.dispose(); AssetsLoader.dispose(); }
}
