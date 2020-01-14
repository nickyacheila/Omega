package omega;
import java.awt.*;

public class Hexagon extends Polygon {


    int hasBeenSelectedBy = -1;
    private static final long serialVersionUID = 1L;
    public static final int SIDES = 6;
    private Point[] points = new Point[SIDES];
    private Point center = new Point(0, 0);
    private int radius;
    private int rotation = 90;
    private int serialNumber;



    void setSelected(int n) {
        hasBeenSelectedBy = n;

    }


    public Hexagon(Point center, int radius) {
        npoints = SIDES;
        xpoints = new int[SIDES];
        ypoints = new int[SIDES];

        this.center = center;
        this.radius = radius;


        updatePoints();
    }

    public Hexagon(int x, int y, int radius) {
        this(new Point(x, y), radius);
    }


    private double findAngle(double fraction) {
        return fraction * Math.PI * 2 + Math.toRadians((rotation + 180) % 360);
    }

    private Point findPoint(double angle) {
        int x = (int) (center.x + Math.cos(angle) * radius);
        int y = (int) (center.y + Math.sin(angle) * radius);

          return new Point(x, y);
    }

    protected void updatePoints() {
        for (int p = 0; p < SIDES; p++) {
            double angle = findAngle((double) p / SIDES);
            Point point = findPoint(angle);
            xpoints[p] = point.x;
            ypoints[p] = point.y;
            points[p] = point;

        }

    }

    void draw(Graphics g) {

        if (hasBeenSelectedBy == 0)
            g.setColor(Color.white);
        else if (hasBeenSelectedBy == 1)
            g.setColor(Color.BLACK);
        else
            g.setColor(new Color(77, 145, 131));


        g.fillPolygon(this);
        g.setColor(new Color(0xFFDD88));
        g.drawPolygon(this);


    }


    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }
}
