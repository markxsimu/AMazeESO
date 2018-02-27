package falstad;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.example.MarcAndChris.SkyrimMaze.R;

import generation.CardinalDirection;
import generation.Cells;
import generation.Seg;


/**
 * Created by Marc on 11/27/2017.
 */

public class MazePanel extends View {
   // private Paint paint;
   // private Canvas canvas;
   // private Bitmap bitmap;
    MazeController controller;
    Seg seg;

    private Canvas canvas;
    private Paint paint;
    private Bitmap bitmap;
    private Bitmap wallbit;
    private BitmapShader wallShade;
    private Bitmap skybit;
    private Bitmap floorbit;
    private BitmapShader skyShader;
    private BitmapShader floorshade;
    private Matrix matrix;
    private int layoutWidth;
    private int layoutHeight;
    private static final String LOG_TAG = "GraphicsWrapper";
    private static final int WIDTH = Constants.VIEW_WIDTH;
    private static final int HEIGHT = Constants.VIEW_HEIGHT;
    private static final int SHADEW = 400;
    private static final int SHADEH = 330;


    public MazePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        wallbit = BitmapFactory.decodeResource(context.getResources(), R.drawable.alduin);
        wallbit = Bitmap.createScaledBitmap(wallbit, SHADEW + 325, SHADEH + 200, false);
        wallShade = new BitmapShader(wallbit, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        skybit = BitmapFactory.decodeResource(context.getResources(), R.drawable.alduin);
        skybit = Bitmap.createScaledBitmap(skybit, SHADEW, SHADEH, false);
        skyShader = new BitmapShader(skybit, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        floorbit = BitmapFactory.decodeResource(context.getResources(), R.drawable.alduin);
        floorbit = Bitmap.createScaledBitmap(floorbit, SHADEW + 50, SHADEH + 50, true);
        floorshade = new BitmapShader(floorbit, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

    }


    public void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         *
         *
        Rect myrect = new Rect();
        myrect.set(0, 0, canvas.getWidth(), canvas.getHeight());
        Paint blue = new Paint();
        blue.setColor(Color.BLUE);
        blue.setStyle(Paint.Style.STROKE);
        blue.setStrokeWidth(8);
         */


       // canvas.drawLine(canvas.getHeight(), canvas.getWidth(), canvas.getWidth() / 8, canvas.getHeight() / 8, blue);

       // blue.setStyle(Paint.Style.FILL);
       // canvas.drawRect(myrect, blue);
        if (bitmap != null){
            super.onDraw(canvas);
            //canvas.drawBitmap(bitmap, matrix, paint);
            canvas.drawBitmap(bitmap, 0, 0, paint);
        }



    }

    public void setGraphics() {
        init();
    }


    public void setColorToBlack() {
        paint.setColor(Color.BLACK);
    }

    public void setColorToDarkGray() {
        paint.setColor(Color.DKGRAY);


    }

    public void setColorToWhite() {
        paint.setColor(Color.WHITE);

    }

    public void setColorToRed() {
        paint.setColor(Color.RED);

    }


    public void setColorToYellow() {
        paint.setColor(Color.YELLOW);
    }

    public void setToColorWhiteOrGray(Cells seencells, int x, int y, CardinalDirection curDir) {

        paint.setColor(seencells.hasWall(x, y, curDir) ? Color.WHITE : Color.GRAY);

    }

    public void MazeDrawLine(int a, int b, int c, int d) {
        canvas.drawLine(a, b, c, d, paint);
    }

    public void MazeFillOval(int i, int j, int cirsiz) {
        RectF oval = new RectF(i, j, cirsiz, cirsiz);
        canvas.drawOval(oval, paint);

    }

    public void MazeFillRect(int i, int j, int w, int h) {
        canvas.drawRect(i, j, w, h, paint);

    }

    public void setSegColor(Seg seg) {
        this.seg = seg;

        paint.setColor(Color.rgb(seg.col[0], seg.col[1], seg.col[2]));

    }

    public void MazeFillPolygon(int[] xps, int[] yps, int i) {
        paint.setShader(wallShade);
        int counter;
        Path path = new Path();
        path.moveTo(xps[0], yps[0]);
        for (counter = 1; counter < xps.length; counter++) {
            path.lineTo(xps[i], yps[i]);
        }
    }


    public static int mazeGetRGB(int[] color) {
        return Color.rgb(color[0], color[1], color[2]);

    }

    public static int[] newColor(int col) {
        int[] vals = new int[3];
        vals[0] = Color.red(col);
        vals[1] = Color.green(col);
        vals[2] = Color.blue(col);
        return vals;

    }
}

