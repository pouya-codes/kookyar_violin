

package com.PouyaApp.KookYarVioliN;


import java.sql.Types;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint("DrawAllocation")
public class PitchView extends View {

    private float centerPitch, currentPitch,midiRef;
    private int width, height;
    private int laFrequnes ;
    private final Paint paint = new Paint();

    public PitchView(Context context) {
        super(context);
      }

    public PitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PitchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setCenterPitch(float centerPitch) {
        this.centerPitch = centerPitch;
        invalidate();
    }

    public void setCurrentPitch(float currentPitch) {
        this.currentPitch = currentPitch;
        invalidate();
    }
    public void setLaFrequnse(int Frequnes) {
        this.laFrequnes = Frequnes;
        invalidate();
    }
    public void setMidiRef(float midiRef) {
        this.midiRef = midiRef;
        invalidate();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h/2, oldw, oldh);
        width = w;
        height =h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
    	if(centerPitch>=12){
        paint.setTextSize(width/20);
        paint.setColor(Color.BLACK);
      canvas.drawText(laFrequnes+" Hz", width/10,25, paint);
    	
        float halfWidth = width / 2;
        paint.setStrokeWidth(6.0f);
        paint.setColor(Color.WHITE);
        canvas.drawLine(halfWidth, 0, halfWidth, height, paint);

        
        float dx = (currentPitch - centerPitch)/2 ;
      
        if (-1 < dx && dx < 1) {
        	
            paint.setStrokeWidth(4.0f);
            paint.setColor(Color.rgb(85, 107, 47));
            if(-1 <dx && dx< -0.05) {
//         		 icon = BitmapFactory.decodeResource(getResources(),R.drawable.seft);
//           		 canvas.drawBitmap(icon, width/2 -(2*(icon.getWidth())),0, paint);
            	
            }
            else if (dx>0.05){
//          		Bitmap icon2 = BitmapFactory.decodeResource(getResources(),R.drawable.shol);
//         		canvas.drawBitmap(icon2, width/2 +((icon2.getWidth())),0, paint);
            }
            else {
//          		 icon = BitmapFactory.decodeResource(getResources(),R.drawable.seft);
//           		 canvas.drawBitmap(icon, width/2 -(2*(icon.getWidth())),0, paint);
//          		Bitmap icon2 = BitmapFactory.decodeResource(getResources(),R.drawable.shol);
//         		canvas.drawBitmap(icon2, width/2 +((icon2.getWidth())),0, paint);
            }

            
        } else {
        	paint.setColor(Color.RED);
            paint.setStrokeWidth(8.0f);
            
            if(dx<-1) {
            	if(currentPitch==12) {
                    paint.setTextSize(width/20);
                    paint.setColor(Color.YELLOW);
                    canvas.drawText("به سیم ضربه بزنید", width/4-width/16,(height-height/8), paint);
            	}
            	else {
                paint.setTextSize(width/20);
                paint.setColor(Color.YELLOW);
                canvas.drawText("سیم را سفت کنید", width/4-width/16,(height-height/8) , paint);
//          		 icon = BitmapFactory.decodeResource(getResources(),R.drawable.seft2);
//           		 canvas.drawBitmap(icon, 0,0, paint);
            	}
            }
            else{

            	
                paint.setTextSize(width/20);
                paint.setColor(Color.YELLOW);
                canvas.drawText("سیم را شل کنید",(width-width/4),(height-height/8) , paint);
//          		 icon = BitmapFactory.decodeResource(getResources(),R.drawable.shol2);
//           		 canvas.drawBitmap(icon,width-icon.getWidth(),0, paint);
            	
            }
            paint.setColor(Color.RED);
            dx = (dx < 0) ? -1 : 1;
        }
        double phi = dx * Math.PI / 4;
        canvas.drawLine(halfWidth, height,
        		halfWidth + (float)Math.sin(phi) * height * 0.9f,
        		height - (float)Math.cos(phi) * height * 0.9f, paint);
        

        paint.setStrokeWidth(6.0f);
		paint.setColor(Color.WHITE);
		double x1 = 1 * Math.PI / 4;
		canvas.drawLine(halfWidth + (float) Math.sin(x1) * height * 0.90f,
				height - (float) Math.cos(x1) * height * 0.90f, halfWidth
						+ (float) Math.sin(x1) * height * 1.00f, height
						- (float) Math.cos(x1) * height * 1.00f, paint);
		x1 = -1 * Math.PI / 4;
		canvas.drawLine(halfWidth + (float) Math.sin(x1) * height * 0.90f,
				height - (float) Math.cos(x1) * height * 0.90f, halfWidth
						+ (float) Math.sin(x1) * height * 1.00f, height
						- (float) Math.cos(x1) * height * 1.00f, paint);
		// ------------------------------------------
		x1 = 0.75 * Math.PI / 4;
		canvas.drawLine(halfWidth + (float) Math.sin(x1) * height * 0.90f,
				height - (float) Math.cos(x1) * height * 0.90f, halfWidth
						+ (float) Math.sin(x1) * height * 1.00f, height
						- (float) Math.cos(x1) * height * 1.00f, paint);

		x1 = -0.75 * Math.PI / 4;
		canvas.drawLine(halfWidth + (float) Math.sin(x1) * height * 0.90f,
				height - (float) Math.cos(x1) * height * 0.90f, halfWidth
						+ (float) Math.sin(x1) * height * 1.00f, height
						- (float) Math.cos(x1) * height * 1.00f, paint);
		// ------------------------------------------
		x1 = 0.5 * Math.PI / 4;
		canvas.drawLine(halfWidth + (float) Math.sin(x1) * height * 0.90f,
				height - (float) Math.cos(x1) * height * 0.90f, halfWidth
						+ (float) Math.sin(x1) * height * 1.00f, height
						- (float) Math.cos(x1) * height * 1.00f, paint);

		x1 = -0.5 * Math.PI / 4;
		canvas.drawLine(halfWidth + (float) Math.sin(x1) * height * 0.90f,
				height - (float) Math.cos(x1) * height * 0.90f, halfWidth
						+ (float) Math.sin(x1) * height * 1.00f, height
						- (float) Math.cos(x1) * height * 1.00f, paint);
		// ------------------------------------------
		x1 = 0.25 * Math.PI / 4;
		canvas.drawLine(halfWidth + (float) Math.sin(x1) * height * 0.90f,
				height - (float) Math.cos(x1) * height * 0.90f, halfWidth
						+ (float) Math.sin(x1) * height * 1.00f, height
						- (float) Math.cos(x1) * height * 1.00f, paint);

		x1 = -0.25 * Math.PI / 4;
		canvas.drawLine(halfWidth + (float) Math.sin(x1) * height * 0.90f,
				height - (float) Math.cos(x1) * height * 0.90f, halfWidth
						+ (float) Math.sin(x1) * height * 1.00f, height
						- (float) Math.cos(x1) * height * 1.00f, paint);
        
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(width/2, height, height/20, paint);
        
        
    
        	
        paint.setARGB(150, 254, 50, 200);
        paint.setTextAlign(Align.CENTER);
        paint.setTextSize(width/20);
        canvas.drawText(drawName() ,width/2 ,height/2, paint);
      
    	}

    }

	private String drawName() {
		// TODO Auto-generated method stub
		 String[] noteName = { "دو", "ر بمل", "ر", "می بمل", "می", "فا",
				"سل بمل", "سل", "لا بمل", "لا", "سی بمل", "سی" };
		 String[] positionName = { "اول", "دوم", "سوم", "چهارم", "پنجم",
				"ششم", "هفتم" };
		float tone = midiRef % 1;
		float midi = (int) midiRef;
		int noteIndex = (int) (midi % 12);
		int position = (int) (midi / 12) - 2;
		String drawName ;
		if(tone==0) drawName = noteName[noteIndex  ]+ " پوزیسیون " + positionName[position];
		else {
			if (noteIndex == 0 || noteIndex == 2 || noteIndex == 4
					|| noteIndex == 5 || noteIndex == 7
					|| noteIndex == 9 || noteIndex == 11) {
				drawName = noteName[(noteIndex)  ]+" سری "+ " پوزیسیون " + positionName[position];
			} else {
				drawName = noteName[(noteIndex+1)  ]+" کرن "+ " پوزیسیون " + positionName[position];
			}
			
		}
		return drawName ;
	}
}