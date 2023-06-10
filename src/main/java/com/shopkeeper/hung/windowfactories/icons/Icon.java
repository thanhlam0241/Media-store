package com.shopkeeper.hung.windowfactories.icons;

import javafx.scene.image.Image;

import java.util.Objects;

public class Icon {
    public static Image getBookIcon(){
        return  new Image(Objects.requireNonNull(Icon.class.getResourceAsStream("book-icon.png")));
    }
    public static Image getFilmIcon(){
        return  new Image(Objects.requireNonNull(Icon.class.getResourceAsStream("film-icon.png")));
    }
    public static Image getMusicIcon(){
        return new Image(Objects.requireNonNull(Icon.class.getResourceAsStream("music-icon.png")));
    }
}
