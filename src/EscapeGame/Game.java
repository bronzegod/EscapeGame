/* 
 * Copyright (C) 2018 Ryan Castelli
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package EscapeGame;

import NTropic_Engine.Engine;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 * 2D Escape Game
 *
 * @author NTropy
 */
public class Game extends Engine {

    public static Game mainGame = new Game();

    public Desk desk1 = new Desk(200, 300, 20, 50);

    public Desk desk2 = new Desk(500, 700, 20, 50);

    public Desk[] desks;

    public int deskNum;

    private Game() {
        super();
    }

    public static void main(String args[]) {
        mainGame.fps = 60;
        mainGame.gameName = "Test";
        mainGame.wHeight = 900;
        mainGame.wWidth = 1800;
        mainGame.isRunning = true;
        mainGame.run();
        System.exit(0);
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        super.initialize();
        deskNum = 2;
        desks = new Desk[deskNum];
        desks[0] = desk1;
        desks[1] = desk2;
        while (isRunning) {
            long time = System.currentTimeMillis();

            update();
            this.draw();

            time = (1000 / fps) - (System.currentTimeMillis() - time); //frame delay

            if (time > 0) {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException ie) {
                    System.err.println(ie);
                }
            }

        }
        mainGame.setVisible(false); //insert game over here
    }

    /**
     * Checks for inputs and collisions
     */
    @Override
    public void update() {
        //select active desk
        Desk activeDesk = null;
        for(int j=0;j<deskNum;j++){
            if(mainGame.posX - desks[j].posX < 5 || mainGame.posY - desks[j].posY < 5)
                activeDesk = desks[j];
        }
        System.out.println(activeDesk);
        
        //left collision
        if (!(mainGame.posX + 11 > activeDesk.posX && mainGame.posX < activeDesk.posX + activeDesk.length && mainGame.posY + 10 > activeDesk.posY && mainGame.posY + 31 < activeDesk.posY + activeDesk.width)) {

            if (input.isKeyDown(KeyEvent.VK_RIGHT)) {
                posX++;
            }
        }
        //right collision
        if (!(mainGame.posX - 11 < activeDesk.posX + (2 * activeDesk.length) && mainGame.posX + 9 > activeDesk.posX && mainGame.posY + 10 > activeDesk.posY && mainGame.posY + 31 < activeDesk.posY + activeDesk.width)) {

            if (input.isKeyDown(KeyEvent.VK_LEFT)) {
                posX--;
            }
        }
        //bottom collision
        if (!(mainGame.posY > activeDesk.posY && mainGame.posY < activeDesk.posY + (activeDesk.width - 30) && mainGame.posX + 9 > activeDesk.posX && mainGame.posX < activeDesk.posX + (2 * activeDesk.length) + 10)) {

            if (input.isKeyDown(KeyEvent.VK_UP)) {
                posY--;
            }
        }
        //top collision
        if (!(mainGame.posY + 11 > activeDesk.posY && mainGame.posY < activeDesk.posY + (activeDesk.width - 30) && mainGame.posX + 9 > activeDesk.posX && mainGame.posX < activeDesk.posX + (2 * activeDesk.length) + 10)) {

            if (input.isKeyDown(KeyEvent.VK_DOWN)) {
                posY++;
            }
        }
        //left collision
        if (!(mainGame.posX + 11 > activeDesk.posX && mainGame.posX < activeDesk.posX + activeDesk.length && mainGame.posY + 10 > activeDesk.posY && mainGame.posY + 31 < activeDesk.posY + activeDesk.width)) {

            if (input.isKeyDown(KeyEvent.VK_D)) {
                posX++;
            }
        }
        //right collision
        if (!(mainGame.posX - 11 < activeDesk.posX + (2 * activeDesk.length) && mainGame.posX + 9 > activeDesk.posX && mainGame.posY + 10 > activeDesk.posY && mainGame.posY + 31 < activeDesk.posY + activeDesk.width)) {

            if (input.isKeyDown(KeyEvent.VK_A)) {
                posX--;
            }
        }
        //bottom collision
        if (!(mainGame.posY > activeDesk.posY && mainGame.posY < activeDesk.posY + (activeDesk.width - 29) && mainGame.posX + 9 > activeDesk.posX && mainGame.posX < activeDesk.posX + (2 * activeDesk.length) + 10)) {

            if (input.isKeyDown(KeyEvent.VK_W)) {
                posY--;
            }
        }
        //top collision
        if (!(mainGame.posY + 11 > activeDesk.posY && mainGame.posY < activeDesk.posY + (activeDesk.width - 30) && mainGame.posX + 9 > activeDesk.posX && mainGame.posX < activeDesk.posX + (2 * activeDesk.length) + 10)) {

            if (input.isKeyDown(KeyEvent.VK_S)) {
                posY++;
            }
        }

    }

    @Override
    public void draw() {
        Graphics g = getGraphics();

        Graphics bbg = mainGame.backBuffer.getGraphics();

        bbg.setColor(Color.BLACK); //background
        bbg.fillRect(0, 0, mainGame.wWidth, mainGame.wHeight);

        Color transYellow = new Color(204, 204, 0, 137);
        bbg.setColor(transYellow);
        bbg.fillOval(mainGame.posX - 10, mainGame.posY - 10, 30, 30);

        bbg.setColor(Color.RED);
        bbg.fillOval(mainGame.posX, mainGame.posY, 10, 10);

        bbg.setColor(Color.WHITE);
        for (int j = 0; j < deskNum; j++) {
            bbg.fillRect(desks[j].posX, desks[j].posY, desks[j].width, desks[j].length);
        }

        bbg.setColor(Color.RED);
        for (int k = 0; k < deskNum; k++) {
            for (int j = desks[k].posX - 15; j < desks[k].posX + (2 * desks[k].length) - 5; j++) {
                for (int i = desks[k].posY - 15; i < desks[k].posY + desks[k].width - 45; i++) {
                    if ((int) Math.sqrt(Math.pow((j - mainGame.posX + 10), 2) + Math.pow((i - mainGame.posY + 10), 2)) < 15) {
                        bbg.drawLine(j + 15, i + 15, j + 15, i + 15);
                    }
                }
            }
        }

        g.drawImage(mainGame.backBuffer, mainGame.insets.left, mainGame.insets.top, this);
    }
}
