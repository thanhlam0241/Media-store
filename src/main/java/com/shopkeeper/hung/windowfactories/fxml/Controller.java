package com.shopkeeper.hung.windowfactories.fxml;

import javafx.scene.layout.AnchorPane;

public abstract class Controller {
    private Controller parent;
    private AnchorPane root;

    public void setRoot(AnchorPane root){
        this.root=root;
    }
    public AnchorPane getRoot(){
        return this.root;
    }
    public void setParent(Controller parent){
        this.parent= parent;
    }
    public Controller getParent(){
        return this.parent;
    }

    public void add(Controller controller){
        if(controller == null){
            System.out.println("bug controller ==null , cannot be added");
            return;
        }
        controller.setParent(this);
        this.getRoot().getChildren().add(controller.getRoot());
    }
    public void add(Controller controller, int x, int y){
        if(controller == null){
            System.out.println("bug controller ==null , cannot be added");
            return;
        }
        AnchorPane anchorPane = controller.getRoot();
        controller.setParent(this);
        anchorPane.setLayoutX(x);
        anchorPane.setLayoutY(y);
        this.getRoot().getChildren().add(anchorPane);
    }

    public void close(Controller controller){
        AnchorPane anchorPane= controller.getParent().getRoot();
        anchorPane.getChildren().remove(controller.getRoot());
        controller=null;
    }

    public void close(){
        this.getParent().close(this);
    }

    public void toFront(){
        this.getRoot().toFront();
    }

}
