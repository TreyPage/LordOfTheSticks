/*
Copyright 2019 Brian Alexander, John Bailey, Austin DeWitt, Trey Page

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
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

/**
 * Controller creates a table on the game screen that is filled with an image and listens for
 * touch.
 */
class Controller {

  private Viewport viewport;
  private Stage stage;
  private boolean upPressed, downPressed, leftPressed, rightPressed;
  private OrthographicCamera cam;

  Controller() {
    cam = new OrthographicCamera();
    viewport = new FitViewport(800, 480, cam);
    stage = new Stage(viewport, StickTest.spriteBatch);
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

  void draw() {
    stage.draw();
  }

  boolean isUpPressed() {
    return upPressed;
  }

  boolean isDownPressed() {
    return downPressed;
  }

  boolean isLeftPressed() {
    return leftPressed;
  }

  boolean isRightPressed() {
    return rightPressed;
  }

  void resize(int width, int height) {
    viewport.update(width, height);

  }
}