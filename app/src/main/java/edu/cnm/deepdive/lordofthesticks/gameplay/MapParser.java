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

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Class for looking through a {@link TiledMap} and getting specific layers to create boundaries and borders for the game.
 */
public class MapParser {
  private static final String MAP_LAYER_NAME_GROUND = "ground";
  private static final String MAP_LAYER_NAME_BOUNDS = "bounds";
  private static final String MAP_LAYER_NAME_DANGERS = "dangers";

  /**
   * Method that goes through the {@link com.badlogic.gdx.maps.Map} asset going through each layer then through each object layer and
   * defining them using {@link Bounds}, {@link Ground} or {@link DangerZone} to give them specified qualities.
   * @param world {@link World} to use for the map
   * @param tiledMap {@link TiledMap} the map loaded into the assets folder
   */
  public static void parseMapLayers(World world, TiledMap tiledMap) {
    for (MapLayer layer : tiledMap.getLayers()) {
      for (MapObject object : layer.getObjects()) {
        Shape shape;
        if (object instanceof PolylineMapObject) {
          shape = createPolyline((PolylineMapObject) object);
        } else {
          continue;
        }
        if (layer.getName().equals(MAP_LAYER_NAME_GROUND))
          new Ground(world, shape);
        if (layer.getName().equals(MAP_LAYER_NAME_BOUNDS))
          new Bounds(world, shape);
        if (layer.getName().equalsIgnoreCase(MAP_LAYER_NAME_DANGERS))
          new DangerZone(world, shape);
      }
    }
  }
  private static ChainShape createPolyline(PolylineMapObject polyline) {
    float[] vertices = polyline.getPolyline().getTransformedVertices();
    Vector2[] worldVerticies = new Vector2[vertices.length / 2];
    for (int i = 0; i < worldVerticies.length; i++) {
      worldVerticies[i] = new Vector2(vertices[i * 2] / StickTest.PIXEL_PER_METER,
          vertices[i * 2 + 1] / StickTest.PIXEL_PER_METER);
    }
    ChainShape cs = new ChainShape();
    cs.createChain(worldVerticies);
    return cs;
  }
}