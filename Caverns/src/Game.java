import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

//Vector.sub(ep, tp);
//ev.setmag(max_speed);

//ev = Vector.sub(ep, Vector.add(tp, Vector.mult(tv,t)))
//ev.setmag(max_speed);


public class Game extends JFrame implements KeyListener{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Vector Center = new Vector(0, 0);

    private String Key = "";
    private boolean Space;

    public int pSize = 100;

    private Blocks block;
    //private Inventory inventory;
    
    private float timer2 = 0;
    private float timer3 = 0;

    //window vars
    private final int MAX_FPS; //maximum refresh rate
    private final int WIDTH; //window width
    private final int HEIGHT; //window height

    enum GAME_STATES{
        MENU,
        PLAY,
        DEATH
    }
    public GAME_STATES GameStates = GAME_STATES.PLAY;

    //double buffer strategy
    private BufferStrategy strategy;
    public ArrayList<Integer> keys = new ArrayList<>();

    //loop variables
    private boolean isRunning = true; //is the window running
    private long rest = 0; //how long to sleep the main thread

    //timing variables
    private float dt; //delta time
    private long lastFrame; //time since last frame
    private long startFrame; //time since start of frame
    private int fps; //current fps

    public Vector p;
    public Vector v;
    

    static int rate = 500;

    public Game(int width, int height, int fps){
        super("Caverns Beta-0.2");
        this.MAX_FPS = fps;
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    public BufferedImage loadTexture(String filepath){
        try {
            return ImageIO.read(new File(filepath));
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public Image loadTextureGif(String filepath){
        try {
            return new ImageIcon(new File(filepath).toURI().toURL()).getImage();
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /*
     * init()
     * initializes all variables needed before the window opens and refreshes
     */
    public void init(){
        //initializes window size
        setBounds(0, 0, WIDTH, HEIGHT);
        setResizable(false);


        //set jframe visible
        setVisible(true);

        //set default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create double buffer strategy
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        addKeyListener(this);
        setFocusable(true);

        loading();

        block = new Blocks();
        //inventory = new Inventory();
        
        //set initial lastFrame var
        lastFrame = System.currentTimeMillis();

        //set background window color
        setBackground(Color.BLUE);
        p = new Vector(-100 * 4997, -100 * 4997);
        v = new Vector(0, 0);
        

    }

    /*
     * update()
     * updates all relevant game variables before the frame draws
     */
    private void update() {
        //update current fps
        fps = (int) (1f / dt);

        switch(GameStates) {
            case PLAY:
                handleKeys();
                break;
		    case MENU:
			    break;
		    default:
			    break;
        }
        p.add(Vector.mult(v, dt));
    }

    /*
     * draw()
     * gets the canvas (Graphics2D) and draws all elements
     * disposes canvas and then flips the buffer
     */
    private void loading(){
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        g.drawString("Loading...", 100, 100);
        g.dispose();
        strategy.show();
    }
    private void draw(){
        //get canvas
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

        switch(GameStates) {
            case MENU:
                break;
            case PLAY:
                //clear screen
                g.clearRect(0,0,WIDTH, HEIGHT);

                block.draw(g, p, v, pSize, Space, Key, dt);
        		block.inventory.inventory(g);
                Space = false;

                g.setColor(Color.RED);
                g.fillRect(320, 320, 60, 60);

                //draw fps
                g.setColor(Color.WHITE);
                g.drawString(Long.toString(fps) + "  " +
                        (int)Math.ceil((-p.y + 250 + Center.y) / 100) + " " +
                        (int)Math.ceil((-p.x + 250 + Center.x) / 100), 10, 50
                );
                g.setColor(Color.WHITE);
                //g.fillRect(700, 0, WIDTH, HEIGHT);
                break;
            default:
            	break;
        }


        //release resources, show the buffer
        g.dispose();
        strategy.show();
    }

    private void handleKeys() {
        for(int i = 0; i < keys.size(); i++) {
            switch (keys.get(i)) {
                case KeyEvent.VK_A:
                    Key = "left";
                    block.test(p, v, Space, Key, dt);
                    break;
                case KeyEvent.VK_D:
                    Key = "right";
                    block.test(p, v, Space, Key, dt);
                    break;
                case KeyEvent.VK_S:
                    Key = "down";
                    block.test(p, v, Space, Key, dt);
                    break;
                case KeyEvent.VK_W:
                    Key = "up";
                    block.test(p, v, Space, Key, dt);
                    break;
                case KeyEvent.VK_SPACE:
                    Space = true;
                    block.test(p, v, Space, Key, dt);
                    break;
                case KeyEvent.VK_F1:
                    block.save(p);
                    break;
                case KeyEvent.VK_F2:
                    block.load(p);
                    break;
                case KeyEvent.VK_R:
                	timer3 += dt;
                	if(timer3 >= 0.08f) {
                		if(block.inventory.highlight0 > 0) block.inventory.highlight0--;
                		else if (block.inventory.page < 2){
                			block.inventory.highlight0 = 2;
                			block.inventory.page++;
                		}
                		timer3 = 0;
                	}
                	break;
                case KeyEvent.VK_F:
                	timer2 += dt;
                	if(timer2 >= 0.08f) {
                		if (block.inventory.highlight0 < 2) block.inventory.highlight0++;
                		else if (block.inventory.page > 0){
                			block.inventory.highlight0 = 0;
                			block.inventory.page--;
                		}
                		timer2 = 0;
                	}
                	break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(!keys.contains((keyEvent.getKeyCode()))) {
            keys.add(keyEvent.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        for(int i = keys.size() - 1; i >= 0; i--)
            if(keys.get(i) == keyEvent.getKeyCode())
                keys.remove(i);
        v = new Vector(0, 0);
    }

    /*
         * run()
         * calls init() to initialize variables
         * loops using isRunning
            * updates all timing variables and then calls update() and draw()
            * dynamically sleeps the main thread to maintain a framerate close to target fps
         */
    public void run(){
        init();

        while(isRunning){

            //new loop, clock the start
            startFrame = System.currentTimeMillis();

            //calculate delta time
            dt = (float)(startFrame - lastFrame)/1000;

            //update lastFrame for next dt
            lastFrame = startFrame;

            //call update and draw methods
            update();
            draw();

            //dynamic thread sleep, only sleep the time we need to cap the framerate
            //rest = (max fps sleep time) - (time it took to execute this frame)
            rest = (1000/MAX_FPS) - (System.currentTimeMillis() - startFrame);
            if(rest > 0){ //if we stayed within frame "budget", sleep away the rest of it
                try{ Thread.sleep(rest); }
                catch (InterruptedException e){ e.printStackTrace(); }
            }
        }
    }

    //entry point for application
    public static void main(String[] args) {
        Game game = new Game(850, 700, 60);
        game.run();
    }

}