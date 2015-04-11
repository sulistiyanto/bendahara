/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import configure.configAnimation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TimelineBuilder;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author sulistiyanto
 */
public class loadingStatusUp extends configAnimation {

    public loadingStatusUp(final Node node) {
        super(
                node,
                TimelineBuilder.create()
                .keyFrames(
                        new KeyFrame(Duration.millis(0),
                                new KeyValue(node.opacityProperty(), 1, WEB_EASE),
                                new KeyValue(node.translateYProperty(), 0, WEB_EASE)
                        ),
                        new KeyFrame(Duration.millis(200),
                                new KeyValue(node.opacityProperty(), 0, WEB_EASE),
                                new KeyValue(node.translateYProperty(), -20, WEB_EASE)
                        )
                )
                .build()
        );
        setCycleDuration(Duration.seconds(1));
        setDelay(Duration.seconds(1));
    }

    @Override
    protected void stopping() {
        super.stopping();
        node.setTranslateY(0); // restore default
    }
}
