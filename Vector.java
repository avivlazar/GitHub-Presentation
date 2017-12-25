
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import Formulas.Formulas;



public class Vector<E1 extends Direction, E2 extends Number>{
	
	public static final Color COLOR = Color.BLACK;
	public static final int WIDTH = 2;
	
	private E1 direction;  // Direction
	private E2 length;   //Length
	
	//Constructor 1
	/**Full Vector constructor **/
	public Vector(E2 X_start, E2 Y_start, E2 X_end, E2 Y_end) {
		//Confirm to Constructor 3
		this((X_end.doubleValue() - X_start.doubleValue()), (Y_end.doubleValue() - Y_start.doubleValue()));
	}
	
	
	//Constructor 2
	/**Constructor: startPoint, destinationPoint**/
	public Vector(PointDouble startPoint, PointDouble destinationPoint) {
		//Confirm to Constructor 1
		this(startPoint.getX(), startPoint.getY(), destinationPoint.getX(), destinationPoint.getY());
	}
	
	
	//Constructor 3
	/**Constructor: dx, dy**/
	public Vector(E2 dx, E2 dy) {
		setVector(dx, dy);
	}
	
	
	//Constructor 4
	/**Constructor: direction, length**/
	public Vector(Direction direction, double length) {
//		if(length < 0){
//			direction = direction.getNegativeDirection();
//			length = -length;
//		}
		this.direction = direction;
		this.length = length;
	}
	
	//Constructor 5
	/**Constructor: another vector**/
	public Vector(Vector<D, L> v) {
		this(v.direction, v.length);
   	}
	
		
	//Constructor 6
	/**Constructor: another vector (Cartezy Display)**/
	public Vector(PointDouble vec){
		this(vec.getX(), vec.getY());  //C.3
	}
	
	public Vector (double radiantOrDegree, double length, boolean isRadiant){
		Direction direction;
		if(isRadiant){
			double radiant = radiantOrDegree;
			direction = Formulas.getDirectionByRadiant(radiant);
		}
		else{
			double degree = radiantOrDegree;
			direction = Formulas.getDirectionByDegree(degree);
		}
		this.direction = direction;
		this.length = length;
	}
	
		

	
	//Get Methods
	public Direction getDirection(){
		return direction;
	}
	public double getLength(){
		return length;
	}
	public double getDX(){
		return direction.getX() * length;
	}
	public double getDy() {
		return direction.getY() * length;
	}
	public double getCos(Vector<D, L> v){
		if(length != 0 && v.length != 0){
			return this.multiple(v) / (length * v.length);
		}
		return 1;
	}

	public Vector<D, L> getNegativeVector() {
		double dx = -getDX();
		double dy = -getDy();
		return new Vector<D, L>(dx, dy);
	}
	
	public double getDegree(){
		return Formulas.getDegree(direction);
	}
	
	
	//Set Methods
	public void setLength(double length){
		this.length = length;
	}
	public void setDirection(Double dirX, Double dirY) {
		direction = new Direction(dirX, dirY);
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public void setDx(double dx) {
		double dy = getDy();
		setVector(dx, dy);
	}
	
	public void setDy(double dy) {
		double dx = getDX();
		setVector(dx, dy);
	}
	
	private void setVector(E2 dx.doubleValue(), E2 dy){
		
		length = Math.sqrt(dx.doubleValue()*dx.doubleValue() + dy*dy);
		double dirX = 0;
		double dirY = 0;
		if(length != 0){
			dirX = dx.doubleValue() / length;
			dirY = dy / length;
		}
		else{
			dirX = 1;
			dirY = 0;
		}
		direction = new Direction(dirX, dirY);
		//setDirection(dirX, dirY);
	}
	
	public void castLengthByLevel(int level) {
		length = Formulas.castDoubleToSmallOneByLevel(length, level);
	}


	
	/**Calculations**/
	//MULT (vector with vector)
	public double multiple(Vector<D, L> v) {  
		return ((direction.getX() * v.direction.getX()) + (direction.getY() * v.direction.getY())) * length * v.length;
	}
	//MULT (num with vector)
	public Vector<D, L> multipleWith(double num) {
		return new Vector<D, L>(num * getDX(), num * getDy());
	}
	//ADD (vec with vec)
	public Vector<D, L> addWith(Vector<D, L> v) {    //Vector + Vector
		double dx = this.getDX() + v.getDX();
		double dy = this.getDy() + v.getDy();
		return new Vector<D, L>(dx, dy);
	}
	//ADD (Point with Vector)
	public PointDouble addWith(PointDouble pd){ 
		return new PointDouble(getDX() + pd.getX(), getDy() + pd.getY());
	}
	//SUB (vec with vec)
	public Vector<D, L> subWith(Vector<D, L> v) { 
		return this.addWith( v.getNegativeVector() );
//		return this.addWith(new Vector(v.getDirection(), -v.getLength()));
	}
	//SUB (PointDouble with Vector) 
	public PointDouble subWith(PointDouble start) {  // 
		Vector<D, L> v = this.getNegativeVector();
		return v.addWith(start);
	}
	
	
	
	
	public String toString(){
		StringBuffer stb = new StringBuffer("Direction:  (" + direction.getX() + ", " + direction.getY()
		+ ")" + "\n" + "Length:  " + length + "\n");
		return stb.toString();
	}
	
	public String toString(PointDouble start, PointDouble end){
		StringBuffer stb = new StringBuffer("Start:  (" + (int)start.getX() + ", " + (int)start.getY() + ") \n" + 
	                                        "End:  (" + (int)end.getX() + ", " + (int)end.getY() + "\n" +
				                            toString());
		return stb.toString();
	}
	
	public String toString(String name){
		StringBuffer stb = new StringBuffer("Name:  " + name + "\n" + toString());
		return stb.toString();
	}
	
	public String toString(PointDouble start, PointDouble end, String name){
		StringBuffer stb = new StringBuffer("Name:  " + name + "\n" + toString(start, end));
		return stb.toString();
	}


	public void drawVector(Graphics g, PointDouble start, Color c, boolean isWithArrow){
		drawVector(g, start, c, WIDTH ,isWithArrow);
	}
	

	public void drawVector(Graphics g, PointDouble start, Color c, int width, boolean isWithArrow){
		Polygon poly;  // the Polygon
		int[] arrayX = null;
		int[] arrayY = null;
		
		Direction verticalDirection = new Direction(0, 0);
		
		double Length = getLength();
		double a = - (getDirection().getY());
		double b = getDirection().getX();
		
		verticalDirection.setX(a);
		verticalDirection.setY(b);
		Vector<D, L> lengthVector = new Vector<D, L>(getDirection(), Length);
		Vector<D, L> widthVector = new Vector<D, L>(verticalDirection, width);
		Vector<D, L> longVector = new Vector<D, L>(getDirection(), lengthVector.getLength() + (widthVector.getLength()));
		PointDouble p1 = new Vector<D, L>(verticalDirection, widthVector.getLength() / 2.0).addWith(start);
		PointDouble p2 =new Vector<D, L>(verticalDirection, widthVector.getLength() / 2.0).subWith(start);
		PointDouble p3 = lengthVector.addWith(p2);
		PointDouble p7 = lengthVector.addWith(p1);
		
		if(isWithArrow){
			PointDouble p4 = widthVector.subWith(p3);
			PointDouble p5 = longVector.addWith(start);
			PointDouble p6 = widthVector.addWith(p7);
			arrayX = new int[]{Formulas.castDoubleToInt(p1.getX()), 
		               Formulas.castDoubleToInt(p2.getX()),
		               Formulas.castDoubleToInt(p3.getX()),
		               Formulas.castDoubleToInt(p4.getX()),
		               Formulas.castDoubleToInt(p5.getX()),
		               Formulas.castDoubleToInt(p6.getX()),
		               Formulas.castDoubleToInt(p7.getX())};
           arrayY = new int[]{Formulas.castDoubleToInt(p1.getY()), 
 	               Formulas.castDoubleToInt(p2.getY()),
                    Formulas.castDoubleToInt(p3.getY()),
                    Formulas.castDoubleToInt(p4.getY()),
                    Formulas.castDoubleToInt(p5.getY()),
                    Formulas.castDoubleToInt(p6.getY()),
                    Formulas.castDoubleToInt(p7.getY())};
		}
		else{
			arrayX = new int[]{Formulas.castDoubleToInt(p1.getX()), 
		               Formulas.castDoubleToInt(p2.getX()),
		               Formulas.castDoubleToInt(p3.getX()),
		               Formulas.castDoubleToInt(p7.getX())};
            arrayY = new int[]{Formulas.castDoubleToInt(p1.getY()), 
 	               Formulas.castDoubleToInt(p2.getY()),
                    Formulas.castDoubleToInt(p3.getY()),
                    Formulas.castDoubleToInt(p7.getY())};
		}
		poly = new Polygon(arrayX, arrayY, arrayX.length);
		g.setColor(c);
		g.drawPolygon(poly);
		g.fillPolygon(poly);
	}

	
	
	
	
}
