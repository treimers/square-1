package de.softquadrat.square1.java3d;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * Square-1 demo showing a cube that can be rotated with the mouse.
 */
public class Square1Demo extends Applet implements Constants {
    /** The serial version uid. */
    private static final long serialVersionUID = -4141295364514881551L;

    /**
     * Constructs the Square-1 scene.
     */
    public Square1Demo() {
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(config);
        add("Center", canvas3D);
        SimpleUniverse SimpleUniverse = new SimpleUniverse(canvas3D);
        SimpleUniverse.getViewingPlatform().setNominalViewingTransform();
        
        BranchGroup scene = new BranchGroup();
        Square1 square1 = new Square1();
        scene.addChild(square1);
        // background
        Background background = new Background();
        background.setApplicationBounds(new BoundingSphere());
        background.setColor(0.9f, 0.9f, 0.9f);
        scene.addChild(background); 
        // complete scene
        scene.compile();
        SimpleUniverse.addBranchGraph(scene);
    }

    /**
     * main method called if invoked as application.
     * @param args main args.
     */
    public static void main(String[] args) {
        MainFrame frame = new MainFrame(new Square1Demo(), 600, 400);
        frame.setTitle("Square-1");
    }
}
