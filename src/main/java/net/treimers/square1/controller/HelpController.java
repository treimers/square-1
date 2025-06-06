package net.treimers.square1.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventTarget;

import javafx.application.HostServices;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;

/**
 * Instance of this class are used as controller for Square-1 help dialog.
 */
public class HelpController implements Initializable {
    /** The help navigation tree. */
	@FXML private TreeView<String> treeView;
    /** The split pane. */
	@FXML private SplitPane splitPane;
    /** The web view showing the help content. */
	@FXML private WebView webView;
    /** Map used to convert resource links to tree items. */
    Map<TreeItem<String>, String> treeLinkMap = new HashMap<>();
    /** Map used to convert tree items to resource links. */
    Map<String, TreeItem<String>> linkTreeMap = new HashMap<>();
    /** The main controller. */
    private Square1Controller square1Controller;
    // The next variable has been added according to a bug in web engine of
    // Java FX web view control
    // http://bugs.java.com/bugdatabase/view_bug.do?bug_id=8117161
    /** Indicator for event handling, used to avoid circular invocations. */
    private boolean isAdjusting;
    /** The host services used to open urls in browser. */
    private HostServices hostServices;

    /**
     * Sets the help controller.
     * @param square1Controller the main controller.
     */
    public void setMainController(Square1Controller square1Controller) {
        this.square1Controller = square1Controller;
    }

    /**
     * Sets the host services used to open urls in browser.
     * 
     * @param hostServices the host services used to open urls in browser.
     */
    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    // initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        splitPane.setDividerPositions(0.2);
        SplitPane.setResizableWithParent(treeView, false);
        TreeItem<String> root = new TreeItem<>("Root");
        addToMaps(root, "1__welcome");
        // first
        TreeItem<String> first = new TreeItem<>("First Steps");
        first.setExpanded(true);
        addToMaps(first, "1__welcome");
        TreeItem<String> intro = new TreeItem<>("Introduction");
        first.getChildren().add(intro);
        addToMaps(intro, "1_1_intro");
        TreeItem<String> notation = new TreeItem<>("Notation");
        first.getChildren().add(notation);
        addToMaps(notation, "1_2_notation");
        TreeItem<String> getstarted = new TreeItem<>("Getting started");
        first.getChildren().add(getstarted);
        addToMaps(getstarted, "1_3_start");
        TreeItem<String> colors = new TreeItem<>("Colors");
        first.getChildren().add(colors);
        addToMaps(colors, "1_4_colors");
        TreeItem<String> rotate = new TreeItem<>("Rotation");
        first.getChildren().add(rotate);
        addToMaps(rotate, "1_5_rotate");
        // positions
        TreeItem<String> positions = new TreeItem<>("Positions");
        positions.setExpanded(true);
        addToMaps(positions, "2__positions");
        TreeItem<String> enterPos = new TreeItem<>("Enter Position");
        positions.getChildren().add(enterPos);
        addToMaps(enterPos, "2_1_enter");
        TreeItem<String> solvePos = new TreeItem<>("Solve Position");
        positions.getChildren().add(solvePos);
        addToMaps(solvePos, "2_2_solve");
        // load & save
        TreeItem<String> file = new TreeItem<>("File Operations");
        file.setExpanded(true);
        addToMaps(file, "3__file");
        // root
        root.getChildren().add(first);
        root.getChildren().add(positions);
        root.getChildren().add(file);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        treeView.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> handleTreeEvent(newValue));
        loadWebView(root);
        // Add event handler for link clicks in the WebView DOM
        webView.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                injectLinkInterceptor();
            }
        });
        // Optional: For internal links, still listen to locationProperty
        webView.getEngine().locationProperty().addListener(e -> handleLinkEvent(e));
    }

    /**
     * Adds a tree item and a resource link to both maps.
     * @param treeItem the tree item.
	 * @param link the resource link.
     */
    private void addToMaps(TreeItem<String> treeItem, String link) {
        treeLinkMap.put(treeItem, link);
        URL resource = getClass().getResource("/net/treimers/square1/" + link + ".html");
        String externalForm = resource.toExternalForm();
        linkTreeMap.put(externalForm, treeItem);
    }

    /**
     * Injects a click event handler for all anchor tags in the loaded HTML document.
     * External links are opened in the system browser.
     */
    private void injectLinkInterceptor() {
        Document doc = webView.getEngine().getDocument();
        if (doc == null) return;
        NodeList nodeList = doc.getElementsByTagName("a");
        for (int i = 0; i < nodeList.getLength(); i++) {
            EventTarget link = (EventTarget) nodeList.item(i);
            link.addEventListener("click", evt -> {
                EventTarget target = evt.getCurrentTarget();
                Element anchor = (Element) target;
                String href = anchor.getAttribute("href");
                if (href != null && (href.startsWith("http://") || href.startsWith("https://"))) {
                    javafx.application.Platform.runLater(() -> {
                        if (hostServices != null) {
                            hostServices.showDocument(href);
                        }
                    });
                    evt.preventDefault(); // Prevents loading in WebView
                }
            }, false);
        }
    }

    /**
     * Handles a link event fired when the user clicks on a hyperlink
     * in the help content.
	 * @param o the observable (the hyper link address).
     */
    private void handleLinkEvent(Observable o) {
        isAdjusting = true;
        try {
            treeView.getSelectionModel().clearSelection();
            if (!(o instanceof ReadOnlyStringProperty))
                return;
            ReadOnlyStringProperty property = (ReadOnlyStringProperty) o;
            String value = property.getValue();
            if (value == null)
                return;
            // Check if it's an internal link
            TreeItem<String> treeItem = linkTreeMap.get(value);
            if (treeItem != null) {
                treeView.getSelectionModel().select(treeItem);
            }
            // External links are now handled in the DOM!
        } catch (Exception e) {
            if (square1Controller != null) {
                square1Controller.alertException(e);
            }
        } finally {
            isAdjusting = false;
        }
    }

    /**
     * Loads the web view with corresponding help content.
     * @param node the current tree node.
     */
    private void loadWebView(TreeItem<String> node) {
        String name = treeLinkMap.get(node);
        webView.getEngine().load(getClass().getResource("/net/treimers/square1/" + name + ".html").toExternalForm());
    }

    /**
     * Handles selection events on the help navigation tree.
     * 
	 * @param v the tree item observable.
	 * @param oldValue the last selected tree item.
     * @param newValue the currently selected tree item.
     */
    private void handleTreeEvent(TreeItem<String> newValue) {
        // avoid circular invocations
        if (isAdjusting)
            return;
        try {
            isAdjusting = true;
            if (newValue == null)
                return;
            loadWebView(newValue);
        } finally {
            isAdjusting = false;
        }
    }
}