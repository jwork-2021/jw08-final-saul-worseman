package creature.router;

import creature.Creature;
import world.Tile;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import creature.Player;

public class BlinkyChaseRouter implements Router {


    private Creature prey = Player.getPlayer();
    private int preyX;
    private int preyY;
    private int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    public int nextPos[] = new int[2];
    private boolean visited[][];
    private Creature creature;
    public BlinkyChaseRouter(Creature creature) {
        this.creature = creature;

    }

    private void bfs(){
        Queue<int[]> queue = new LinkedList<int[]>();
        visited = new boolean[creature.getWorld().getWidth()][creature.getWorld().getWidth()];
        for (int i = 0; i < creature.getWorld().getWidth(); i++){
            Arrays.fill(visited[i],false);
        }
        //System.out.println(preyX + " " + preyY);
        queue.offer(new int[]{preyX, preyY});
        visited[preyX][preyY] = true;
        while(!queue.isEmpty()){
            int[] tuple = queue.poll();
            for(int i = 0; i < 4; i++){
                int x = tuple[0] + directions[i][0];
                int y = tuple[1] + directions[i][1];
                Tile t = creature.getWorld().tile(x,y);
                if(x == creature.getX() && y == creature.getY()){
                    nextPos[0] = tuple[0];
                    nextPos[1] = tuple[1];
                    return;
                }
                else if(x < 48 && x > 0 && y < 48 && y > 0 && (t == Tile.PATH || t == Tile.LAVA)){
                    if(visited[x][y] == false){
                        queue.add(new int[]{x,y});
                        visited[x][y] = true;
                    }
                }
            }
        }
    }

    public int[] nextSteps(){
        preyX = prey.getX();
        preyY = prey.getY();
        bfs();
        return new int[]{nextPos[0] - creature.getX(),nextPos[1] - creature.getY()};
    }
}
