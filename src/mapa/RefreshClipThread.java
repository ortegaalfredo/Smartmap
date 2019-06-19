/*
 * RefreshClipThread.java
 *
 * Created on 4 de abril de 2006, 07:14
 *
 * Copyright (c) 2006 Alfredo Adrián Ortega
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 
 *
 */

package mapa;

/**
 *
 * @author aortega
 */
import java.lang.*;
public class RefreshClipThread extends Thread{
    
    POLYLINEcell mPolyline;
    POLYGONcell mPolygon;
    POICELL mPoi;
    Screen mScreen;
    
    /**
     * En este constructor no hacemos nada
     */
    public RefreshClipThread(Screen aScreen) {
        mPolyline=(POLYLINEcell)aScreen.mPOLYLINEs;
        mPolygon=(POLYGONcell)aScreen.mPOLYGONs;
        mPoi=(POICELL)aScreen.mPois;
        mScreen=aScreen;
    }

    /*
     * Procedimiento para clipear las calles.
     * Este es el que mas tarda
     *
     */ 
   public void clipStreets() {
           int x1,y1,x2,y2;
           int mVertexs=0;
           boolean lAddSegment=false;
           POLYLINEcell.Segment lSegment;
           
           java.util.Vector lVect = new java.util.Vector(100,50);
           mVertexs=0;   
           for (int i=0;i<mPolyline.mTypes.length;i++) {
               if (mScreen.mFastMode && mPolyline.mTypes[i]==6) {
                   mVertexs+=mPolyline.mPoints[i]; //Si es modo rapido, no agregamos calles y ahorramos memoria
                   continue;
                   }
               for (int q=0;q<mPolyline.mPoints[i];q++,mVertexs++) 
                   if (q>0)
                   {
                   x1=mPolyline.Px[mVertexs-1];
                   y1=mPolyline.Py[mVertexs-1];
                   x2=mPolyline.Px[mVertexs];
                   y2=mPolyline.Py[mVertexs];
                   if (mScreen.isPointInsideScreen(x1,y1) || mScreen.isPointInsideScreen(x2,y2))
                       {
                       lSegment = mPolyline.newSegment();
                       lSegment.type=mPolyline.mTypes[i];
                       lSegment.index=(short)i;
                       lSegment.x1=x1;
                       lSegment.y1=y1;
                       lSegment.x2=x2;
                       lSegment.y2=y2;
                       lVect.addElement(lSegment);
                   }
               }
            }
           
            while (mScreen.mRepainting) 
                try {sleep(10);} catch (Exception e) {};
           mPolyline.mVisibleSegments.removeAllElements();
           mPolyline.mVisibleSegments=lVect;

   }
   
 /*
  *Clipea POIs, no tarda casi nada porque generalmente no hay
  */
   public void clipPois() {
           int x1,y1,x2,y2;
           int mVertexs=0;
           boolean lAddSegment=false;
           POICELL.Segment lSegment;
           
           java.util.Vector lVect = new java.util.Vector(10,10);
           mVertexs=0;   
           for (int i=0;i<mPoi.mTypes.length;i++) {
               x1=mPoi.Px[i];
               y1=mPoi.Py[i];
                if (mScreen.isPointInsideScreen(x1,y1))
                       {
                       lSegment = mPoi.newSegment();
                       lSegment.x=x1;
                       lSegment.y=y1;
                       lSegment.index=(short)i;
                       lVect.addElement(lSegment);
                        }
               }
           
            while (mScreen.mRepainting) 
                try {sleep(10);} catch (Exception e) {};
           mPoi.mVisibleSegments.removeAllElements();
           mPoi.mVisibleSegments=lVect;
   }
   
   /*
    *Clipea poligonos.
    *TODO: Tesselar (Triangular) y clipear triangulos para que ande mejor.
    */
    public void clipPolygons() {
           int x1,y1;
           int mVertexs=0,mFirstVertex=0;
           boolean lAddSegment=false;
           POLYGONcell.Segment lSegment;
           
           java.util.Vector lVect = new java.util.Vector(10,10);
           mVertexs=0;   
           for (int i=0;i<mPolygon.mTypes.length;i++) {
               mFirstVertex=mVertexs;
               lAddSegment=false;
               for (int q=0;q<mPolygon.mPoints[i];q++,mVertexs++)  {
                   x1=mPolygon.Px[mVertexs];
                   y1=mPolygon.Py[mVertexs];
                   if (mScreen.isPointInsideScreen(x1,y1)) {
                        lAddSegment=true;
                        mVertexs+=mPolygon.mPoints[i]-q;
                        break;
                        }
                  }
               if (lAddSegment)
                       {
                       lSegment = mPolygon.newSegment();
                       lSegment.type=mPolygon.mTypes[i];
                       lSegment.index=(short)i;
                       lSegment.pX=new int[mPolygon.mPoints[i]];
                       lSegment.pY=new int[mPolygon.mPoints[i]];
                       for (int t = 0;t<mPolygon.mPoints[i];t++)
                                {
                                lSegment.pX[t]=mPolygon.Px[t+mFirstVertex];
                                lSegment.pY[t]=mPolygon.Py[t+mFirstVertex];
                                }
                       lVect.addElement(lSegment);
                       }
            }
           
            while (mScreen.mRepainting) 
                try {sleep(10);} catch (Exception e) {};
           mPolygon.mVisibleSegments.removeAllElements();
           mPolygon.mVisibleSegments=lVect;
   }
   
   public void run() {
       while (true) {
           if (!mScreen.mDirty)
                   try {sleep(100);} catch (Exception e) {}
           else
           try {
           mScreen.mDirty=false;
           clipStreets();
           clipPois();
           clipPolygons();
           mScreen.repaint();
           } catch (Exception e){};
           }
       }
}
