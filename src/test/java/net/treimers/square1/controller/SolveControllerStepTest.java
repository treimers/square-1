package net.treimers.square1.controller;

import java.beans.PropertyChangeListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.BeforeClass;
import org.junit.Test;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.Move;
import net.treimers.square1.model.Position;

/**
 * Drives the solve dialog arrows the way a user does: solve, one step forward, then back and forward again.
 */
public class SolveControllerStepTest {
	private static final ColorBean COLORS = new ColorBean() {
		private final Color[] colors = new Color[] {
				Color.WHITE, Color.YELLOW, Color.ORANGE, Color.BLUE, Color.RED, Color.GREEN, Color.GRAY, Color.BLACK
		};

		@Override
		public void addColorChangeListener(PropertyChangeListener listener) {
		}

		@Override
		public void removeColorChangeListener(PropertyChangeListener listener) {
		}

		@Override
		public Color[] getDefaultColors() {
			return colors;
		}

		@Override
		public Color[] getColors() {
			return colors;
		}
	};

	@BeforeClass
	public static void startJavaFx() throws InterruptedException {
		// Closing the last test window must not shut JavaFX down. Later tests in the
		// same run still need the toolkit, including move animations.
		Platform.setImplicitExit(false);
		CountDownLatch latch = new CountDownLatch(1);
		try {
			Platform.startup(() -> latch.countDown());
		} catch (IllegalStateException alreadyStarted) {
			latch.countDown();
		}
		if (!latch.await(10, TimeUnit.SECONDS))
			throw new IllegalStateException("JavaFX did not start");
	}

	@Test
	public void arrowsKeepWorkingAfterTheFirstAnimatedStep() throws Exception {
		AtomicReference<SolveController> controllerRef = new AtomicReference<>();
		AtomicReference<Stage> stageRef = new AtomicReference<>();
		runOnFx(() -> {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/net/treimers/square1/solvepanel.fxml"));
			Parent root = loader.load();
			SolveController controller = loader.getController();
			controller.init(COLORS);
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
			Position scrambled = new Position().move(new Move(1, 0, true)).move(new Move(-1, 2, true));
			controller.setPosition(scrambled);
			controller.handleSolve();
			controllerRef.set(controller);
			stageRef.set(stage);
		});
		SolveController controller = controllerRef.get();
		runOnFx(controller::handleRight);
		waitUntilSettled(controller);
		if (displayedStep(controller) != 1)
			throw new AssertionError("one forward click reached step " + displayedStep(controller));
		runOnFx(() -> slider(controller).setValueChanging(true));
		runOnFx(controller::handleRight);
		waitUntilSettled(controller);
		if (displayedStep(controller) != 2)
			throw new AssertionError("forward while the slider was still dragging stayed at step " + displayedStep(controller));
		runOnFx(controller::handleLeft);
		waitUntilSettled(controller);
		if (displayedStep(controller) != 1)
			throw new AssertionError("back stayed at step " + displayedStep(controller));
		runOnFx(controller::handleRight);
		waitUntilSettled(controller);
		if (displayedStep(controller) != 2)
			throw new AssertionError("forward after back stayed at step " + displayedStep(controller));
		runOnFx(() -> stageRef.get().close());
	}

	private static javafx.scene.control.Slider slider(SolveController controller) throws Exception {
		var field = SolveController.class.getDeclaredField("slider");
		field.setAccessible(true);
		return (javafx.scene.control.Slider) field.get(controller);
	}

	private static void runOnFx(FxTask task) throws Exception {
		CountDownLatch latch = new CountDownLatch(1);
		AtomicReference<Throwable> failure = new AtomicReference<>();
		Platform.runLater(() -> {
			try {
				task.run();
			} catch (Throwable t) {
				failure.set(t);
			} finally {
				latch.countDown();
			}
		});
		if (!latch.await(30, TimeUnit.SECONDS))
			throw new AssertionError("fx task timed out");
		if (failure.get() != null)
			throw new AssertionError(failure.get().getMessage(), failure.get());
	}

	private static int displayedStep(SolveController controller) throws Exception {
		var field = SolveController.class.getDeclaredField("displayedStep");
		field.setAccessible(true);
		return field.getInt(controller);
	}

	private static void waitUntilSettled(SolveController controller) throws Exception {
		var animatorField = SolveController.class.getDeclaredField("animator");
		animatorField.setAccessible(true);
		Object animator = animatorField.get(controller);
		var running = animator.getClass().getMethod("isRunning");
		var stepField = SolveController.class.getDeclaredField("displayedStep");
		stepField.setAccessible(true);
		var targetField = SolveController.class.getDeclaredField("animatingTarget");
		targetField.setAccessible(true);
		for (int i = 0; i < 400; i++) {
			AtomicReference<Boolean> active = new AtomicReference<>();
			runOnFx(() -> active.set((Boolean) running.invoke(animator)));
			if (!active.get())
				return;
			Thread.sleep(50);
		}
		throw new AssertionError("animation did not finish; step=" + stepField.get(controller) + " target="
				+ targetField.get(controller));
	}

	private interface FxTask {
		void run() throws Exception;
	}
}
