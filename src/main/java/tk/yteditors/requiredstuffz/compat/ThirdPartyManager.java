package tk.yteditors.requiredstuffz.compat;

import cpw.mods.fml.common.Loader;
import tk.yteditors.requiredstuffz.util.ClassFinder;
import tk.yteditors.requiredstuffz.util.LogHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ThirdPartyManager {

	private List<IThirdPartyCompat> compats = new ArrayList<IThirdPartyCompat>();

	public void index() {

		List<Class<?>> classes = ClassFinder.find("tk.yteditors.requiredstuffz.compat");
		for (Class<?> theClass : classes) {
			boolean containsIThirdPartyCompatInterface = false;
			for (Type t : theClass.getInterfaces()) {
				if (t.getTypeName().equals(IThirdPartyCompat.class.getTypeName())) {
					containsIThirdPartyCompatInterface = true;
				}
			}
			if (containsIThirdPartyCompatInterface) {
				LogHelper.info(theClass.getName());
				try {
					IThirdPartyCompat instance = (IThirdPartyCompat) theClass.newInstance();
					if (Loader.isModLoaded(instance.getModId())) {
						compats.add(instance);
						LogHelper.info("Adding compatibility for mod: " + instance.getModId());
					}
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
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
