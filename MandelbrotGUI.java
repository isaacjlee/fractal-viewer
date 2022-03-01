// Isaac Lee
// Data Structures and Algorithms 2
// Assignment 3

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.beans.value.*;
import java.util.*;

/**
 * A class which makes an interactive application permitting the user to explore the Mandelbrot set.
 * @author Isaac Lee
 */
public class MandelbrotGUI extends Application
{
	private static final double BUTTON_SIZE = 50;
	private static final double DEFAULT_COMPLEX_LEFT_X = -2.3;
	private static final double DEFAULT_COMPLEX_TOP_Y = 1.8;
	private static final double DEFAULT_COMPLEX_SIDE_LENGTH = 3.6;
	private static final int DEFAULT_MAX_ITERATIONS = 100;
	private static final int MAX_MAX_ITERATIONS = 200;
	private static final int MAX_MAX_COLOUR_VALUE = 255;
	private static final int DEFAULT_MAX_RED = 255;
	private static final int DEFAULT_MAX_GREEN = 127;
	private static final int DEFAULT_MAX_BLUE = 63;
	private static final int DRAWING_SURFACE_SIDE_LENGTH = 600;
	private static final int MAX_RADIUS = 2;
	private static final double PERCENTAGE_TO_MOVE_REGION_BY = 0.2;
	private static final double PERCENTAGE_TO_ZOOM_IN_BY = 0.2;
	private static final double PERCENTAGE_TO_ZOOM_OUT_BY = 0.25;
	private double complexLeftX;
	private double complexTopY;
	private double complexSideLength;
	private int maxIterations;
	private int maxRed;
	private int maxGreen;
	private int maxBlue;
	private BorderPane boarderPain;
	private Button left;
	private Button up;
	private Button right;
	private Button down;
	private Button zoomIn;
	private Button zoomOut;
	private Button def;
	private Button exit;
	private Button random;
	private Canvas canvasLeft;
	private Canvas canvasUp;
	private Canvas canvasRight;
	private Canvas canvasDown;
	private Canvas canvasZoomIn;
	private Canvas canvasZoomOut;
	private Canvas canvasDef;
	private Canvas canvasExit;
	private GraphicsContext graConLeft;
	private GraphicsContext graConUp;
	private GraphicsContext graConRight;
	private GraphicsContext graConDown;
	private GraphicsContext graConZoomIn;
	private GraphicsContext graConZoomOut;
	private GraphicsContext graConDef;
	private GraphicsContext graConExit;
	private ToolBar bottomTooBa;
	private ToolBar rightTooBa;
	private Text iterChooserLabel;
	private Text redChooserLabel;
	private Text greenChooserLabel;
	private Text blueChooserLabel;
	private WritableImage wrIm;
	private PixelWriter pixWrit;
	private ImageView imVi;
	private Slider iterationChooser;
	private Slider redChooser;
	private Slider greenChooser;
	private Slider blueChooser;
	/**
	 * The method that launches the application.
	 * @param args A string array.
	 */
	public static void main(String[] args)
	{
		launch(args);
	}
	/**
	 * Makes a scene, sets the stage and shows it.
	 * @param stage The stage where all the action happens.
	 */
	public void start(Stage stage)
	{
		initializeComponents();
		addHandlers();
		Scene seen = new Scene(boarderPain);
		stage.setScene(seen);
		stage.setTitle("Interactive Mandelbrot Viewer");
		stage.show();
	}
	/**
	 * Makes all the various GUI components and puts them in the BorderPane, initializes the instance variables describing where the
	 * Mandelbrot set should be drawn and what it should look like and draws the default view of the Mandelbrot set.
	 */
	private void initializeComponents()
	{
		boarderPain = new BorderPane();
		canvasLeft = new Canvas(BUTTON_SIZE,BUTTON_SIZE);
		graConLeft = canvasLeft.getGraphicsContext2D();
		graConLeft.fillRect(15,22.5,20,5);
		graConLeft.fillPolygon(new double[] {15,15,5},new double[] {15,35,25},3);
		left = new Button(null,canvasLeft);
		canvasUp = new Canvas(BUTTON_SIZE,BUTTON_SIZE);
		graConUp = canvasUp.getGraphicsContext2D();
		graConUp.fillRect(22.5,15,5,20);
		graConUp.fillPolygon(new double[] {35,15,25},new double[] {15,15,5},3);
		up = new Button(null,canvasUp);
		canvasRight = new Canvas(BUTTON_SIZE,BUTTON_SIZE);
		graConRight = canvasRight.getGraphicsContext2D();
		graConRight.fillRect(15,22.5,20,5);
		graConRight.fillPolygon(new double[] {35,35,45},new double[] {15,35,25},3);
		right = new Button(null,canvasRight);
		canvasDown = new Canvas(BUTTON_SIZE,BUTTON_SIZE);
		graConDown = canvasDown.getGraphicsContext2D();
		graConDown.fillRect(22.5,15,5,20);
		graConDown.fillPolygon(new double[] {35,15,25},new double[] {35,35,45},3);
		down = new Button(null,canvasDown);
		canvasZoomIn = new Canvas(BUTTON_SIZE,BUTTON_SIZE);
		graConZoomIn = canvasZoomIn.getGraphicsContext2D();
		graConZoomIn.fillRect(5,22.5,20,5);
		graConZoomIn.setFill(Color.WHITE);
		graConZoomIn.fillOval(25,15,20,20);
		graConZoomIn.setFill(Color.BLACK);
		graConZoomIn.fillRect(30,24,10,2);
		graConZoomIn.fillRect(34,20,2,10);
		zoomIn = new Button(null,canvasZoomIn);
		canvasZoomOut = new Canvas(BUTTON_SIZE,BUTTON_SIZE);
		graConZoomOut = canvasZoomOut.getGraphicsContext2D();
		graConZoomOut.fillRect(5,22.5,20,5);
		graConZoomOut.setFill(Color.WHITE);
		graConZoomOut.fillOval(25,15,20,20);
		graConZoomOut.setFill(Color.BLACK);
		graConZoomOut.fillRect(30,24,10,2);
		zoomOut = new Button(null,canvasZoomOut);
		canvasDef = new Canvas(BUTTON_SIZE,BUTTON_SIZE);
		graConDef = canvasDef.getGraphicsContext2D();
		graConDef.fillText("Default",5,30);
		def = new Button(null,canvasDef);
		canvasExit = new Canvas(BUTTON_SIZE,BUTTON_SIZE);
		graConExit = canvasExit.getGraphicsContext2D();
		graConExit.fillText("Exit",15,30);
		exit = new Button(null,canvasExit);
		bottomTooBa = new ToolBar(left,up,right,down,new Separator(),zoomIn,zoomOut,new Separator(),def,exit);
		boarderPain.setBottom(bottomTooBa);
		iterChooserLabel = new Text("Choose how many iterations are performed:");
		iterationChooser = new Slider(0,MAX_MAX_ITERATIONS,DEFAULT_MAX_ITERATIONS);
		iterationChooser.setShowTickMarks(true);
		iterationChooser.setShowTickLabels(true);
		iterationChooser.setMajorTickUnit(50);
		iterationChooser.setBlockIncrement(1);
		redChooserLabel = new Text("Choose how much red gets used:");
		redChooser = new Slider(0,MAX_MAX_COLOUR_VALUE,DEFAULT_MAX_RED);
		redChooser.setShowTickMarks(true);
		redChooser.setShowTickLabels(true);
		redChooser.setMajorTickUnit(85);
		redChooser.setBlockIncrement(1);
		greenChooserLabel = new Text("Choose how much green gets used:");
		greenChooser = new Slider(0,MAX_MAX_COLOUR_VALUE,DEFAULT_MAX_GREEN);
		greenChooser.setShowTickMarks(true);
		greenChooser.setShowTickLabels(true);
		greenChooser.setMajorTickUnit(85);
		greenChooser.setBlockIncrement(1);
		blueChooserLabel = new Text("Choose how much blue gets used:");
		blueChooser = new Slider(0,MAX_MAX_COLOUR_VALUE,DEFAULT_MAX_BLUE);
		blueChooser.setShowTickMarks(true);
		blueChooser.setShowTickLabels(true);
		blueChooser.setMajorTickUnit(85);
		blueChooser.setBlockIncrement(1);
		random = new Button("Random view!");
		rightTooBa = new ToolBar(iterChooserLabel,iterationChooser,redChooserLabel,redChooser,greenChooserLabel,greenChooser,blueChooserLabel,blueChooser,random);
		rightTooBa.setOrientation(Orientation.VERTICAL);
		boarderPain.setRight(rightTooBa);
		wrIm = new WritableImage(DRAWING_SURFACE_SIDE_LENGTH,DRAWING_SURFACE_SIDE_LENGTH);
		pixWrit = wrIm.getPixelWriter();
		complexLeftX = DEFAULT_COMPLEX_LEFT_X;
		complexTopY = DEFAULT_COMPLEX_TOP_Y;
		complexSideLength = DEFAULT_COMPLEX_SIDE_LENGTH;
		maxIterations = DEFAULT_MAX_ITERATIONS;
		maxRed = DEFAULT_MAX_RED;
		maxGreen = DEFAULT_MAX_GREEN;
		maxBlue = DEFAULT_MAX_BLUE;
		drawMandelbrot();
		imVi = new ImageView(wrIm);
		boarderPain.setCenter(imVi);
	}
	/**
	 * Draws the portion of the Mandelbrot set corresponding to the current specifications for size, placement, detail and colour.
	 */
	private void drawMandelbrot()
	{
		for(int x = 0;x < DRAWING_SURFACE_SIDE_LENGTH;x++)
		{
			for(int y = 0;y < DRAWING_SURFACE_SIDE_LENGTH;y++)
			{
				double real = complexLeftX + x * complexSideLength / (DRAWING_SURFACE_SIDE_LENGTH - 1);
				double imaginary = complexTopY - y * complexSideLength / (DRAWING_SURFACE_SIDE_LENGTH - 1);
				Complex z = new Complex(real,imaginary);
				pixWrit.setColor(x,y,pickColor(doIterations(z)));
			}
		}
	}
	/**
	 * Assigns a colour to a pixel corresponding to a point in the complex plane based on the number of iterations that were computed
	 * for this point and the current colour settings.
	 * @param numIter An int representing how many iterations were done on the corresponding complex number.
	 * @return A Color to be assigned to the corresponding pixel.
	 */
	private Color pickColor(int numIter)
	{
		if(numIter == maxIterations)
		{
			return(Color.BLACK);
		}
		int rando = (int)Math.pow(2,numIter) % maxIterations;
		return Color.rgb(maxRed - rando * maxRed / maxIterations,maxGreen - rando * maxGreen / maxIterations,maxBlue - rando * maxBlue / maxIterations);
	}
	/**
	 * Given a complex number, computes how many iterations are done on it to determine whether it is in the Mandelbrot set.
	 * @param c A Complex object to be iterated on.
	 * @return An int representing the number of iterations completed.
	 */
	private int doIterations(Complex c)
	{
		int numIterations = 0;
		Complex curr = new Complex(c);
		while(curr.modulus() <= MAX_RADIUS && numIterations < maxIterations)
		{
			curr.multiply(curr);
			curr.add(c);
			numIterations++;
		}
		return numIterations;
	}
	/**
	 * Adds the various handlers for the GUI components.
	 */
	private void addHandlers()
	{
		left.setOnAction(new EventHandler<ActionEvent>()
		{
			/**
			 * Moves the screen left.
			 * @param e An ActionEvent generated by the user clicking the left button.
			 */
			public void handle(ActionEvent e)
			{
				complexLeftX -= PERCENTAGE_TO_MOVE_REGION_BY * complexSideLength;
				drawMandelbrot();
			}
		});
		up.setOnAction(new EventHandler<ActionEvent>()
		{
			/**
			 * Moves the screen up.
			 * @param e An ActionEvent generated by the user clicking the up button.
			 */
			public void handle(ActionEvent e)
			{
				complexTopY += PERCENTAGE_TO_MOVE_REGION_BY * complexSideLength;
				drawMandelbrot();
			}
		});
		right.setOnAction(new EventHandler<ActionEvent>()
		{
			/**
			 * Moves the screen right.
			 * @param e An ActionEvent generated by the user clicking the right button.
			 */
			public void handle(ActionEvent e)
			{
				complexLeftX += PERCENTAGE_TO_MOVE_REGION_BY * complexSideLength;
				drawMandelbrot();
			}
		});
		down.setOnAction(new EventHandler<ActionEvent>()
		{
			/**
			 * Moves the screen down.
			 * @param e An ActionEvent generated by the user clicking the down button.
			 */
			public void handle(ActionEvent e)
			{
				complexTopY -= PERCENTAGE_TO_MOVE_REGION_BY * complexSideLength;
				drawMandelbrot();
			}
		});
		zoomIn.setOnAction(new EventHandler<ActionEvent>()
		{
			/**
			 * Zooms into the center of the screen.
			 * @param e An ActionEvent generated by the user clicking the zoomIn button.
			 */
			public void handle(ActionEvent e)
			{
				complexLeftX += PERCENTAGE_TO_ZOOM_IN_BY / 2 * complexSideLength;
				complexTopY -= PERCENTAGE_TO_ZOOM_IN_BY / 2 * complexSideLength;
				complexSideLength -= PERCENTAGE_TO_ZOOM_IN_BY * complexSideLength;
				drawMandelbrot();
			}
		});
		zoomOut.setOnAction(new EventHandler<ActionEvent>()
		{
			/**
			 * Zooms out of the center of the screen.
			 * @param e An ActionEvent generated by the user clicking the zoomOut button.
			 */
			public void handle(ActionEvent e)
			{
				complexLeftX -= PERCENTAGE_TO_ZOOM_OUT_BY / 2 * complexSideLength;
				complexTopY += PERCENTAGE_TO_ZOOM_OUT_BY / 2 * complexSideLength;
				complexSideLength += PERCENTAGE_TO_ZOOM_OUT_BY * complexSideLength;
				drawMandelbrot();
			}
		});
		def.setOnAction(new EventHandler<ActionEvent>()
		{
			/**
			 * Resets all of the instance variables, the sliders and the view of the Mandelbrot set to their original states.
			 * @param e An ActionEvent generated by the user clicking the def button.
			 */
			public void handle(ActionEvent e)
			{
				complexLeftX = DEFAULT_COMPLEX_LEFT_X;
				complexTopY = DEFAULT_COMPLEX_TOP_Y;
				complexSideLength = DEFAULT_COMPLEX_SIDE_LENGTH;
				maxIterations = DEFAULT_MAX_ITERATIONS;
				maxRed = DEFAULT_MAX_RED;
				maxGreen = DEFAULT_MAX_GREEN;
				maxBlue = DEFAULT_MAX_BLUE;
				iterationChooser.adjustValue(DEFAULT_MAX_ITERATIONS);
				redChooser.adjustValue(DEFAULT_MAX_RED);
				greenChooser.adjustValue(DEFAULT_MAX_GREEN);
				blueChooser.adjustValue(DEFAULT_MAX_BLUE);
				drawMandelbrot();
			}
		});
		exit.setOnAction(new EventHandler<ActionEvent>()
		{
			/**
			 * Terminates the program.
			 * @param e An ActionEvent generated by the user clicking the exit button.
			 */
			public void handle(ActionEvent e)
			{
				System.exit(0);
			}
		});
		iterationChooser.valueProperty().addListener(new ChangeListener<Object>()
		{
			/**
			 * Changes the number of iterations that must be performed on a complex number before it is considered to be part of the
			 * Mandelbrot set and redraws the screen accordingly.
			 * @param a An ObservableValue<?>.
			 * @param b An Object.
			 * @param c An Object.
			 */
			@Override
			public void changed(ObservableValue<?> a,Object b,Object c)
			{
				maxIterations = (int)iterationChooser.getValue();
				drawMandelbrot();
			}
		});
		redChooser.valueProperty().addListener(new ChangeListener<Object>()
		{
			/**
			 * Changes the proportion of red used in drawing the current portion of the Mandelbrot set.
			 * @param a An ObservableValue<?>.
			 * @param b An Object.
			 * @param c An Object.
			 */
			@Override
			public void changed(ObservableValue<?> a,Object b,Object c)
			{
				maxRed = (int)redChooser.getValue();
				drawMandelbrot();
			}
		});
		greenChooser.valueProperty().addListener(new ChangeListener<Object>()
		{
			/**
			 * Changes the proportion of green used in drawing the current portion of the Mandelbrot set.
			 * @param a An ObservableValue<?>.
			 * @param b An Object.
			 * @param c An Object.
			 */
			@Override
			public void changed(ObservableValue<?> a,Object b,Object c)
			{
				maxGreen = (int)greenChooser.getValue();
				drawMandelbrot();
			}
		});
		blueChooser.valueProperty().addListener(new ChangeListener<Object>()
		{
			/**
			 * Changes the proportion of blue used in drawing the current portion of the Mandelbrot set.
			 * @param a An ObservableValue<?>.
			 * @param b An Object.
			 * @param c An Object.
			 */
			@Override
			public void changed(ObservableValue<?> a,Object b,Object c)
			{
				maxBlue = (int)blueChooser.getValue();
				drawMandelbrot();
			}
		});
		random.setOnAction(new EventHandler<ActionEvent>()
		{
			/**
			 * Draws a portion of the Mandelbrot set in a random part of the complex plane with a random level of magnitude, a random
			 * level of detail and random colours.
			 * @param e An ActionEvent generated by the user clicking the random button.
			 */
			public void handle(ActionEvent e)
			{
				complexLeftX = MAX_MAX_COLOUR_VALUE;
				Random randolph = new Random();
				while(complexLeftX + complexSideLength > DEFAULT_COMPLEX_LEFT_X + DEFAULT_COMPLEX_SIDE_LENGTH || complexTopY - complexSideLength < DEFAULT_COMPLEX_TOP_Y - DEFAULT_COMPLEX_SIDE_LENGTH)
				{
					complexLeftX = DEFAULT_COMPLEX_LEFT_X + DEFAULT_COMPLEX_SIDE_LENGTH * randolph.nextDouble();
					complexTopY = DEFAULT_COMPLEX_TOP_Y - DEFAULT_COMPLEX_SIDE_LENGTH * randolph.nextDouble();
					complexSideLength = DEFAULT_COMPLEX_SIDE_LENGTH * randolph.nextDouble();
				}
				maxIterations = (int)(MAX_MAX_ITERATIONS * randolph.nextDouble());
				maxRed = (int)(MAX_MAX_COLOUR_VALUE * randolph.nextDouble());
				maxGreen = (int)(MAX_MAX_COLOUR_VALUE * randolph.nextDouble());
				maxBlue = (int)(MAX_MAX_COLOUR_VALUE * randolph.nextDouble());
				iterationChooser.setValue(maxIterations);
				redChooser.setValue(maxRed);
				greenChooser.setValue(maxGreen);
				blueChooser.setValue(maxBlue);
				drawMandelbrot();
			}
		});
	}
}
