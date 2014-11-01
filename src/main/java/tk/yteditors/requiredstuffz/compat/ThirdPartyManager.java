package tk.yteditors.requiredstuffz.compat;

import java.util.ArrayList;
import java.util.List;

public class ThirdPartyManager {

	private List<IThirdPartyCompat> compats = new ArrayList<IThirdPartyCompat>();

	public void index() {

		compats.add(new Waila());
	}

	public void preInit() {

		for (IThirdPartyCompat theClass : compats) {
			theClass.preInit();
		}
	}

	public void init() {

		for (IThirdPartyCompat theClass : compats) {
			theClass.init();
		}
	}

	public void postInit() {

		for (IThirdPartyCompat theClass : compats) {
			theClass.postInit();
		}
	}
}
