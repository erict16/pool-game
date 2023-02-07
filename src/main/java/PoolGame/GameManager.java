package PoolGame;

import PoolGame.singleton.TimeSingleton;
import PoolGame.objects.*;
import java.util.ArrayList;
import PoolGame.observer.ScoreObserver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.paint.Paint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.Pair;

/**
 * Controls the game interface; drawing objects, handling logic and collisions.
 */
public class GameManager {
    private Table table;
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    private ArrayList<Pocket> pockets = new ArrayList<>();
    private Line cue, trajectory;
    private boolean cueSet = false;
    private boolean cueActive = false;
    private boolean winFlag = false;
    private final ScoreObserver score = new ScoreObserver();
    private final double TABLEBUFFER = Config.getTableBuffer();
    private final double TABLEEDGE = Config.getTableEdge();
    private final double FORCEFACTOR = 0.1;
    private Scene scene;
    private GraphicsContext gc;
    private long startTime;
    private long interval;
    private String selectColor;
    /**
     * Initialises timeline and cycle count.
     */
    public void run() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17),
                t -> this.draw()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Builds GameManager properties such as initialising pane, canvas,
     * graphicscontext, and setting events related to clicks.
     */
    public void buildManager() {
        startTime = System.currentTimeMillis();
        Pane pane = new Pane();
        setClickEvents(pane);
        Button exit = new Button("Exit");
        Button undo = new Button("Undo");
        Button remo = new Button("Remove");
        String[] color = {"Red", "Yellow", "Green",
                "Brown", "Blue", "Purple", "Black", "Orange"};
        ComboBox<String> colourBox = new ComboBox<>();
        for (String c : color) {
            colourBox.getItems().addAll(c);
        }
        Label description = new Label("Choose the color you wanna remove:");

        this.scene = new Scene(pane, table.getxLength() + TABLEBUFFER * 2, table.getyLength() + TABLEBUFFER * 2);
        Canvas canvas = new Canvas(table.getxLength() + TABLEBUFFER * 2, table.getyLength() + TABLEBUFFER * 2);
        gc = canvas.getGraphicsContext2D();
        pane.getChildren().add(canvas);
        pane.getChildren().add(exit);
        pane.getChildren().add(undo);
        pane.getChildren().add(colourBox);
        pane.getChildren().add(description);
        pane.getChildren().add(remo);
        description.setLayoutX(380);
        colourBox.setLayoutX(400);
        colourBox.setLayoutY(20);
        remo.setLayoutX(500);
        remo.setLayoutY(20);
        undo.setLayoutY(40);

        EventHandler<ActionEvent> eventExit = event -> System.exit(0);
        EventHandler<ActionEvent> eventColor = event -> selectColor = colourBox.getValue().toUpperCase();
        EventHandler<ActionEvent> eventRemo = event -> {
            for (Ball ball : balls) {
                if (ball.getColour().equals(Paint.valueOf(selectColor))){
                    while(ball.isActive()) {
                        ball.remove();
                    }
                }
            }
        };

        exit.setOnAction(eventExit);
        colourBox.setOnAction((eventColor));
        remo.setOnAction(eventRemo);
    }

    private void setClickEvents(Pane pane) {
        pane.setOnMousePressed(event -> {
            trajectory = new Line(getCueBall().getxPos(),
                    getCueBall().getyPos(),
                    getCueBall().getxPos(),
                    getCueBall().getyPos());
            trajectory.getStrokeDashArray().addAll(2.0, 10.0);
            trajectory.setStrokeWidth(2);
            trajectory.setStroke(Paint.valueOf("RED"));
            cue = new Line(getCueBall().getxPos(),
                    getCueBall().getyPos(),
                    getCueBall().getxPos(),
                    getCueBall().getyPos());
            cue.setStrokeWidth(5);
            cue.setStroke(Paint.valueOf("BLANCHEDALMOND"));
            pane.getChildren().add(trajectory);
            pane.getChildren().add(cue);
            cueSet = false;
            cueActive = true;
        });

        pane.setOnMouseDragged(event -> {
            trajectory.setEndX(getCueBall().getxPos() -
                    (event.getX() - getCueBall().getxPos()));
            trajectory.setEndY(getCueBall().getyPos() -
                    (event.getY() - getCueBall().getyPos()));
            cue.setEndX(getCueBall().getxPos() +
                    (event.getX() - getCueBall().getxPos()));
            cue.setEndY(getCueBall().getyPos() +
                    (event.getY() - getCueBall().getyPos()));
        });

        pane.setOnMouseReleased(event -> {
            pane.getChildren().remove(cue);
            pane.getChildren().remove(trajectory);
            cueSet = true;
            cueActive = false;
        });
    }

    public Ball getCueBall() {
        for (Ball ball : balls) {
            if(ball.isCue()) {
                return ball;
            }
        }
        return null;
    }

    /**
     * Draws all relevant items - table, cue, balls, pockets - onto Canvas.
     * Used Exercise 6 as reference.
     */
    private void draw() {
        tick();

        // Fill in background
        gc.setFill(Paint.valueOf("white"));
        gc.fillRect(0, 0, table.getxLength() + TABLEBUFFER * 2, table.getyLength() + TABLEBUFFER * 2);

        // Fill in edges
        gc.setFill(Paint.valueOf("brown"));
        gc.fillRect(TABLEBUFFER - TABLEEDGE, TABLEBUFFER - TABLEEDGE, table.getxLength() + TABLEEDGE * 2,
                table.getyLength() + TABLEEDGE * 2);

        // Fill in Table
        gc.setFill(table.getColour());
        gc.fillRect(TABLEBUFFER, TABLEBUFFER, table.getxLength(), table.getyLength());

        // Fill in Pockets
        for (Pocket pocket : pockets) {
            gc.setFill(Paint.valueOf("black"));
            gc.fillOval(pocket.getxPos() - pocket.getRadius(), pocket.getyPos() - pocket.getRadius(),
                    pocket.getRadius() * 2, pocket.getRadius() * 2);
        }

        // Cue
        if ((cue != null) && cueActive) {
            gc.strokeLine(cue.getStartX(), cue.getStartY(),
                    cue.getEndX(), cue.getEndY());
        }

        for (Ball ball : balls) {
            if (ball.isActive()) {
                gc.setFill(ball.getColour());
                gc.fillOval(ball.getxPos() - ball.getRadius(),
                        ball.getyPos() - ball.getRadius(),
                        ball.getRadius() * 2,
                        ball.getRadius() * 2);
            }
        }

        // Score
        gc.setFont(new Font("Monospace", 20));
        gc.setFill(Paint.valueOf("DARKCYAN"));



        // Win flag and Timer
        String seconds, minutes, hours;
        seconds = String.format("%02d", interval%60);
        minutes = String.format("%02d", interval/60);
        hours = String.format("%02d", interval/60/60);
        if (winFlag) {
            gc.setFont(new Font("Monospace", 30));
            gc.setFill(Paint.valueOf("PINK"));
            gc.fillText("You win!\nThe total time cost is "+hours + ":" + minutes + ":" + seconds
                    + "\nThe total score is: " + score.getScore(), 150, 200);
        } else {
            // Timer
            gc.fillText("Score: " + score.getScore(), 100, 30);
//            long currentTime = System.currentTimeMillis();
            TimeSingleton t = TimeSingleton.getInstance();
            long currentTime = t.getTimer();
            interval = (currentTime - startTime)/1000;
            gc.fillText("Time: " + hours + ":" + minutes + ":" + seconds, 200, 30);
        }
    }

    /**
     * Updates positions of all balls, handles logic related to collisions.
     * Used Exercise 6 as reference.
     */
    public void tick() {
        winFlag = true;
        for (Ball ball : balls) {
            ball.tick();
            if (ball.isActive() && !ball.isCue()) {
                winFlag = false;
            }
            if (ball.isCue() && cueSet) {
                hitBall(ball);
            }

            double width = table.getxLength();
            double height = table.getyLength();

            // Check if ball landed in pocket
            for (Pocket pocket : pockets) {
                if (pocket.isInPocket(ball)) {
                    if (ball.isCue()) {
                        this.reset();
                    } else {
                        if (ball.remove()) {
                            if (Paint.valueOf("RED").equals(ball.getColour())) {
                                score.updateScore(1 + score.getScore());
                            } else if (Paint.valueOf("YELLOW").equals(ball.getColour())) {
                                score.updateScore(2 + score.getScore());
                            } else if (Paint.valueOf("GREEN").equals(ball.getColour())) {
                                score.updateScore(3 + score.getScore());
                            } else if (Paint.valueOf("BROWN").equals(ball.getColour())) {
                                score.updateScore(4 + score.getScore());
                            } else if (Paint.valueOf("BLUE").equals(ball.getColour())) {
                                score.updateScore(5 + score.getScore());
                            } else if (Paint.valueOf("PURPLE").equals(ball.getColour())) {
                                score.updateScore(6 + score.getScore());
                            } else if (Paint.valueOf("BLACK").equals(ball.getColour())) {
                                score.updateScore(7 + score.getScore());
                            } else if (Paint.valueOf("ORANGE").equals(ball.getColour())) {
                                score.updateScore(8 + score.getScore());
                            } else {
                                score.updateScore(1 + score.getScore());
                            }
                        } else {
                            // Check if when ball is removed, any other balls are present in its space. (If
                            // another ball is present, blue ball is removed)
                            for (Ball otherBall : balls) {
                                double deltaX = ball.getxPos() - otherBall.getxPos();
                                double deltaY = ball.getyPos() - otherBall.getyPos();
                                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                                if (otherBall != ball && otherBall.isActive() && distance < 10) {
                                    ball.remove();
                                }
                            }
                        }
                    }
                    break;
                }
            }

            // Handle the edges (balls don't get a choice here)
            if (ball.getxPos() + ball.getRadius() > width + TABLEBUFFER) {
                ball.setxPos(width - ball.getRadius());
                ball.setxVel(ball.getxVel() * -1);
            }
            if (ball.getxPos() - ball.getRadius() < TABLEBUFFER) {
                ball.setxPos(ball.getRadius());
                ball.setxVel(ball.getxVel() * -1);
            }
            if (ball.getyPos() + ball.getRadius() > height + TABLEBUFFER) {
                ball.setyPos(height - ball.getRadius());
                ball.setyVel(ball.getyVel() * -1);
            }
            if (ball.getyPos() - ball.getRadius() < TABLEBUFFER) {
                ball.setyPos(ball.getRadius());
                ball.setyVel(ball.getyVel() * -1);
            }

            // Apply table friction
            double friction = table.getFriction();
            ball.setxVel(ball.getxVel() * friction);
            ball.setyVel(ball.getyVel() * friction);

            // Check ball collisions
            for (Ball ballB : balls) {
                if (checkCollision(ball, ballB)) {
                    Point2D ballPos = new Point2D(ball.getxPos(), ball.getyPos());
                    Point2D ballBPos = new Point2D(ballB.getxPos(), ballB.getyPos());
                    Point2D ballVel = new Point2D(ball.getxVel(), ball.getyVel());
                    Point2D ballBVel = new Point2D(ballB.getxVel(), ballB.getyVel());
                    Pair<Point2D, Point2D> changes = calculateCollision(ballPos, ballVel, ball.getMass(), ballBPos,
                            ballBVel, ballB.getMass(), false);
                    calculateChanges(changes, ball, ballB);
                }
            }
        }

        // Wining
        if (balls.size() == 1) {
            winFlag = true;
        }
    }

    /**
     * Resets the game.
     */
    public void reset() {
        for (Ball ball : balls) {
            ball.reset();
        }
        score.updateScore(0);;
    }

    /**
     * @return scene.
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Sets the table of the game.
     * 
     * @param table
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * @return table
     */
    public Table getTable() {
        return this.table;
    }

    /**
     * Sets the balls of the game.
     * 
     * @param balls
     */
    public void setBalls(ArrayList<Ball> balls) {
        this.balls = balls;
    }

    public void setPockets(ArrayList<Pocket> pockets) { this.pockets = pockets; }

    private void hitBall(Ball ball) {
        double deltaX = ball.getxPos() - cue.getStartX();
        double deltaY = ball.getyPos() - cue.getStartY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Check that start of cue is within cue ball
        if (distance < ball.getRadius()) {
            // Collide ball with cue
            double hitxVel = (cue.getStartX() - cue.getEndX()) * FORCEFACTOR;
            double hityVel = (cue.getStartY() - cue.getEndY()) * FORCEFACTOR;

            ball.setxVel(hitxVel);
            ball.setyVel(hityVel);
        }

        cueSet = false;

    }

    /**
     * Changes values of balls based on collision (if ball is null ignore it)
     * 
     * @param changes
     * @param ballA
     * @param ballB
     */
    private void calculateChanges(Pair<Point2D, Point2D> changes, Ball ballA, Ball ballB) {
        ballA.setxVel(changes.getKey().getX());
        ballA.setyVel(changes.getKey().getY());
        if (ballB != null) {
            ballB.setxVel(changes.getValue().getX());
            ballB.setyVel(changes.getValue().getY());
        }
    }


    /**
     * Checks if two balls are colliding.
     * Used Exercise 6 as reference.
     * 
     * @param ballA
     * @param ballB
     * @return true if colliding, false otherwise
     */
    private boolean checkCollision(Ball ballA, Ball ballB) {
        if (ballA == ballB) {
            return false;
        }

        return Math.abs(ballA.getxPos() - ballB.getxPos()) < ballA.getRadius() + ballB.getRadius() &&
                Math.abs(ballA.getyPos() - ballB.getyPos()) < ballA.getRadius() + ballB.getRadius();
    }

    /**
     * Collision function adapted from assignment, using physics algorithm:
     * http://www.gamasutra.com/view/feature/3015/pool_hall_lessons_fast_accurate_.php?page=3
     *
     * @param positionA The coordinates of the centre of ball A
     * @param velocityA The delta x,y vector of ball A (how much it moves per tick)
     * @param massA     The mass of ball A (for the moment this should always be the
     *                  same as ball B)
     * @param positionB The coordinates of the centre of ball B
     * @param velocityB The delta x,y vector of ball B (how much it moves per tick)
     * @param massB     The mass of ball B (for the moment this should always be the
     *                  same as ball A)
     *
     * @return A Pair in which the first (key) Point2D is the new
     *         delta x,y vector for ball A, and the second (value) Point2D is the
     *         new delta x,y vector for ball B.
     */
    public static Pair<Point2D, Point2D> calculateCollision(Point2D positionA, Point2D velocityA, double massA,
            Point2D positionB, Point2D velocityB, double massB, boolean isCue) {

        // Find the angle of the collision - basically where is ball B relative to ball
        // A. We aren't concerned with
        // distance here, so we reduce it to unit (1) size with normalize() - this
        // allows for arbitrary radii
        Point2D collisionVector = positionA.subtract(positionB);
        collisionVector = collisionVector.normalize();

        // Here we determine how 'direct' or 'glancing' the collision was for each ball
        double vA = collisionVector.dotProduct(velocityA);
        double vB = collisionVector.dotProduct(velocityB);

        // If you don't detect the collision at just the right time, balls might collide
        // again before they leave
        // each others' collision detection area, and bounce twice.
        // This stops these secondary collisions by detecting
        // whether a ball has already begun moving away from its pair, and returns the
        // original velocities
        if (vB <= 0 && vA >= 0 && !isCue) {
            return new Pair<>(velocityA, velocityB);
        }

        // This is the optimisation function described in the gamasutra link. Rather
        // than handling the full quadratic
        // (which as we have discovered allowed for sneaky typos)
        // this is a much simpler - and faster - way of obtaining the same results.
        double optimizedP = (2.0 * (vA - vB)) / (massA + massB);

        // Now we apply that calculated function to the pair of balls to obtain their
        // final velocities
        Point2D velAPrime = velocityA.subtract(collisionVector.multiply(optimizedP).multiply(massB));
        Point2D velBPrime = velocityB.add(collisionVector.multiply(optimizedP).multiply(massA));

        return new Pair<>(velAPrime, velBPrime);
    }

}
