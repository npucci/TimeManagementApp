package com.example.nicco.timemanagementapp.activities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.nicco.timemanagementapp.R;

import java.util.Random;

/**
 * Author: Niccolo Pucci
 * IAT 359 - Final Project
 * Source of aid: http://v4all123.blogspot.ca/2013/02/pie-chart-in-android.html
 */

public class AnalyticsActivity extends AppCompatActivity
{
    float values [] = { 1, 2, 3, 4, 5 };
    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_analytics );

        LinearLayout pieGraphLinearLayout = ( LinearLayout ) findViewById ( R.id.pieGraphLinearLayout );

        values = calculateData ( values );
        PieGraph pieGraph = new PieGraph (
                this,
                values
        );

        pieGraphLinearLayout.addView ( pieGraph );
    }

    private float [] calculateData ( float [] data )
    {
        float total = 0;
        for (int i = 0; i < data.length; i++) {
            total += data[i];
        }
        for (int i = 0; i < data.length; i++) {
            data[i] = 360 * (data[i] / total);
        }
        return data;
    }

    public class PieGraph extends View {
        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private float[] value_degree;

        RectF rectf = new RectF ( 10, 10, 380, 380);
        float temp = 0;

        public PieGraph(Context context, float[] values) {
            super(context);
            value_degree = new float[values.length];
            for (int i = 0; i < values.length; i++) {
                value_degree[i] = values[i];
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Random r;
            for ( int i = 0; i < value_degree.length; i++ )
            {
                if (i == 0) {
                    r = new Random();
                    int color = Color.argb(100, r.nextInt(256), r.nextInt(256),
                            r.nextInt(256));
                    paint.setColor(color);
                    canvas.drawArc (
                            rectf,
                            0,
                            value_degree[i], true, paint);
                }
                else {
                    temp += value_degree[i - 1];

                    r = new Random();
                    int color = Color.argb(255, r.nextInt(256), r.nextInt(256),
                            r.nextInt(256));
                    paint.setColor(color);

                    canvas.drawArc(rectf, temp, value_degree[i], true, paint);
                }
            }
        }
    }
}
