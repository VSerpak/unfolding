package de.fhpotsdam.unfolding.mapdisplay;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;

/**
 * A factory to create MapDisplays for the UnfoldingMap, depending on specified map features.
 * 
 */
@SuppressWarnings("rawtypes")
public class MapDisplayFactory {

	public static final String P2D_CLASSNAME = "processing.opengl.PGraphics2D";
	public static final String OPEN_GL_CLASSNAME = "processing.opengl.PGraphicsOpenGL";

	public static final boolean DEFAULT_USE_MASK = true;
	public static final boolean DEFAULT_USE_DISTORTION = false;

	public static final String OSM_API_KEY = "YOUR-OWN-KEY"; // original one invalid since June 2013
	public static final int OSM_STYLE_ID = 65678; // test: 69960; // original: 998

	public static AbstractMapDisplay getMapDisplay(PApplet p, String id, float x, float y, float width, float height,
			AbstractMapProvider provider, UnfoldingMap map) {
		return getMapDisplay(p, id, x, y, width, height, DEFAULT_USE_MASK, DEFAULT_USE_DISTORTION, provider, map);
	}

	public static AbstractMapDisplay getMapDisplay(PApplet p, String id, float x, float y, float width, float height,
			boolean useMask, boolean useDistortion, AbstractMapProvider provider, UnfoldingMap map) {

		AbstractMapDisplay mapDisplay = null;

		if (provider == null) {
			provider = getDefaultProvider();
		}

		try {
			// NB: PGraphics2D subclasses PGraphicsOpenGL thus p.g in both cases is instance.
			Class p2dClass = Class.forName(P2D_CLASSNAME);
			Class openGLClass = Class.forName(OPEN_GL_CLASSNAME);
//			if (p2dClass.isInstance(p.g)) {
//				mapDisplay = new P2DMapDisplay(p, provider, x, y, width, height);
//				PApplet.println("No OpenGL mapDisplay available. Using P2DMapDisplay.");
//			}else if (openGLClass.isInstance(p.g)){
//				mapDisplay = new OpenGLMapDisplay(p, provider, x, y, width, height);
//				PApplet.println("Using OpenGLMapDisplay.");				
//			}
      mapDisplay = new OpenGLMapDisplay(p, provider, x, y, width, height);
      PApplet.println("Using OpenGLMapDisplay.");       			
		}catch (ClassNotFoundException e){
			mapDisplay = new P2DMapDisplay(p, provider, x, y, width, height);
		}

		mapDisplay.createDefaultMarkerManager(map);
		return mapDisplay;
	}

	public static AbstractMapProvider getDefaultProvider() {
		return new OpenStreetMap.OSMGrayProvider();
	}
}
