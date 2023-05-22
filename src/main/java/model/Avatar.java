package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Avatar extends Circle {
    public Avatar(int x,int y,String imageAddress) {
        super(x,y,40);
        this.setFill(new ImagePattern(new Image(imageAddress)));
    }
}
