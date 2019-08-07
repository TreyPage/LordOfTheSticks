package edu.cnm.deepdive.lordofthesticks.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Controller {
  Viewport viewport;
  Stage stage;
  boolean upPressed,downPressed, leftPressed, rightPressed;
  OrthographicCamera cam;

  public Controller() {
    cam = new OrthographicCamera();
    viewport = new FitViewport(800, 480, cam);
    stage = new Stage(viewport, StickTest.batch);


    Table table = new Table();
    Table table2 = new Table();
    table.setFillParent(true);
    table2.top();
    table.left().bottom();

    Image upImg = new Image(new Texture("flatDark25.png"));
    upImg.setSize(50, 50);
    upImg.addListener(new InputListener() {

      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        upPressed = true;
        return true;
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        upPressed = false;
      }
    });

    Image downImg = new Image(new Texture("flatDark26.png"));
    downImg.setSize(50, 50);
    downImg.addListener(new InputListener() {

      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        downPressed = true;
        return true;
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        downPressed = false;
      }
    });
    Image rightImg = new Image(new Texture("flatDark24.png"));
    rightImg.setSize(50, 50);
    rightImg.addListener(new InputListener() {

      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        rightPressed = true;
        return true;
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        rightPressed = false;
      }
    });
    Image leftImg = new Image(new Texture("flatDark23.png"));
    leftImg.setSize(50, 50);
    leftImg.addListener(new InputListener() {

      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        leftPressed = true;
        return true;
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        leftPressed = false;
      }
    });



    table.add();
    table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight()).padLeft(50).padBottom(30);
    table.add();
    table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight()).padLeft(50).padBottom(30);
    table.add().width(100);
    table.add(upImg).growX().size(upImg.getWidth(), upImg.getHeight()).padLeft(200).padBottom(30);
    table.row().padBottom(15);



    stage.addActor(table);
    Gdx.input.setInputProcessor(stage);
  }
  public void draw(){
    stage.draw();


  }

  public boolean isUpPressed() {
    return upPressed;
  }

  public boolean isDownPressed() {
    return downPressed;
  }

  public boolean isLeftPressed() {
    return leftPressed;
  }

  public boolean isRightPressed() {
    return rightPressed;
  }
  public void resize(int width, int height){
    viewport.update(width,height);

  }
}