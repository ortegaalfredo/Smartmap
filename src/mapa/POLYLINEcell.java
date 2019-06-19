/*
 * POLYLINEcell.java
 *
 * Created on 3 de abril de 2006, 04:41
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
import javax.microedition.lcdui.*;

public class POLYLINEcell extends Cell {
    
    // Elemento visible
   public class Segment {
        short index;
        int x1,y1,x2,y2;
        byte type;
    }
   // Fast Mode
   boolean fastMode = false;
   
   public Segment newSegment() {return new Segment();}

   // Retorna el segmento mas cercano en un radio a un punto dado, o null
   public Segment SegmentOn(int x,int y,int aRadius) {
       Segment lSeg,lResult=null;
       int lDistance;
       for (int i=0;i<mVisibleSegments.size();i++ ) {
           lSeg=(Segment)mVisibleSegments.elementAt(i);
           lDistance=PntToSegmentDistance(x,y,lSeg.x1,lSeg.y1,lSeg.x2,lSeg.y2);
           if (lDistance<aRadius) {
               lResult=lSeg;
               aRadius=lDistance;
               }
       }
       return lResult;
   }

    /**
     * Creates a new instance of POLYLINEcell
     */
    public POLYLINEcell(String lFileName,String lTextFileName,boolean lLoadBounds) {
        super(lFileName,lTextFileName,lLoadBounds);
        mSelectedIndex=-1;
    }

    
 private void drawSingleType(Graphics aGraphics,Matrix aMatrix,int aType) {
   int lVertexIndex = 0;
   int lColor=0;
   switch (aType) {
          default: return;
          case 6: //calle comun
                  lColor=0xffffff;
                  break;
          case 4: //Avenida
                  lColor=0xffffA0;
                  break;
          case 1: //Autopista
                  lColor=0xff6000;
                  break;
          }
  //  aGraphics.setColor(lColor);
    Segment lSeg;
    for (int i=0;i<mVisibleSegments.size();i++ ) {
                        lSeg=(Segment)mVisibleSegments.elementAt(i);
                        if (lSeg.type==aType) {
                            int x1,y1,x2,y2;
                            x1=aMatrix.transformXF(lSeg.x1);
                            x2=aMatrix.transformXF(lSeg.x2);
                            y1=aMatrix.transformYF(lSeg.y1);
                            y2=aMatrix.transformYF(lSeg.y2);
                            if ((x1!=x2) || (y1!=y2))
                                aGraphics.setColor(0);
                            if (this.fastMode)
                                 aGraphics.drawLine(x1,y1,x2,y2);
                            else this.DrawThickLine(aGraphics, x1, y1, x2, y2, 1.6); 
                            }
                        }
if (!this.fastMode)
    for (int i=0;i<mVisibleSegments.size();i++ ) {
                        lSeg=(Segment)mVisibleSegments.elementAt(i);
                        if (lSeg.type==aType) {
                            int x1,y1,x2,y2;
                            x1=aMatrix.transformXF(lSeg.x1);
                            x2=aMatrix.transformXF(lSeg.x2);
                            y1=aMatrix.transformYF(lSeg.y1);
                            y2=aMatrix.transformYF(lSeg.y2);
                            if ((x1!=x2) || (y1!=y2))
                                aGraphics.setColor(lColor);
                                this.DrawThickLine(aGraphics, x1, y1, x2, y2, 1); 
                            }
                        }

 }
    // NEW 2008!
 int mScale=1;
 
 public void setScale(int aScale) {
     this.mScale=aScale;
 }
 public double aTan2(double y, double x) {
	double coeff_1 = Math.PI / 4d;
	double coeff_2 = 3d * coeff_1;
	double abs_y = Math.abs(y);
	double angle;
	if (x >= 0d) {
		double r = (x - abs_y) / (x + abs_y);
		angle = coeff_1 - coeff_1 * r;
	} else {
		double r = (x + abs_y) / (abs_y - x);
		angle = coeff_2 - coeff_1 * r;
	}
	return y < 0d ? -angle : angle;
}
 
    public void DrawThickLine(javax.microedition.lcdui.Graphics aGraphics, int x1,int y1,int x2,int y2,double thick) {
     int x3,y3,x4,y4;
     int x5,y5,x6,y6;
     double x = x2-x1,y = y2-y1;
     if ((x==0) && (y==0)) return;
     double angle =  this.aTan2(y,x);
     double length =  thick / mScale * 10000;
     double crossangle = angle+Math.PI/2;
     
     //weee lo puse al reves!
     double sumcos = Math.sin(crossangle)*length;
     double sumsin = Math.cos(crossangle)*length;
          
     x3=(int)(x1+ sumsin);
     y3=(int)(y1+ sumcos);

     x4=(int)(x1- sumsin);
     y4=(int)(y1- sumcos);
     
     x5=(int)(x2+ sumsin);
     y5=(int)(y2+ sumcos);

     x6=(int)(x2- sumsin);
     y6=(int)(y2- sumcos);
     
     aGraphics.fillTriangle(x3,y3,x4,y4,x5,y5);
     aGraphics.fillTriangle(x5,y5,x6,y6,x4,y4);

    }
    // Dibuja una calle entera (Sin clipear)
    public void DrawSingleStreet(javax.microedition.lcdui.Graphics aGraphics,Matrix aMatrix,short aStreetIndex) {
        if ((aStreetIndex<0) || (aStreetIndex>mPoints.length ))
            return;
        int lVertexIndex=0;
        for (int i=0;i<aStreetIndex;i++)
            lVertexIndex+=mPoints[i];
        int oldX=0,oldY=0,actX,actY;
        for (int i=0;i<mPoints[aStreetIndex];i++)
             {
             actX=aMatrix.transformXF(Px[lVertexIndex]);
             actY=aMatrix.transformYF(Py[lVertexIndex]);
             if (i>0) {
                  if (this.fastMode)
                       aGraphics.drawLine(oldX,oldY,actX,actY);
                  else this.DrawThickLine(aGraphics, oldX, oldY, actX, actY, 1);
                    }
            oldX=actX;
            oldY=actY;
            lVertexIndex++;
            }
    }
    
    public void PaintTo(javax.microedition.lcdui.Graphics aGraphics,Matrix aMatrix,boolean aDrawFast) {
    if (!aDrawFast) // las calles son lentas porque son muchas.
        drawSingleType(aGraphics,aMatrix,6);
    drawSingleType(aGraphics,aMatrix,4);
    drawSingleType(aGraphics,aMatrix,1);
    // Dibuja calle seleccionada
    if (mSelectedIndex>=0) {
        aGraphics.setColor(0xFF0000);
        DrawSingleStreet(aGraphics,aMatrix,mSelectedIndex);
        }
    if (mActiveIntersection!=null) {
        aGraphics.setColor(0xFF0000);
        DrawSingleStreet(aGraphics,aMatrix,mActiveIntersection.streetA);
        DrawSingleStreet(aGraphics,aMatrix,mActiveIntersection.streetB);
        aGraphics.setColor(0xFF0000);
        int lPx=aMatrix.transformXF(mActiveIntersection.point.x);
        int lPy=aMatrix.transformYF(mActiveIntersection.point.y);
        aGraphics.fillRoundRect(lPx-3,lPy-3,7,7,2,2);
        }

    }
    
    public void PaintCaptionsTo(javax.microedition.lcdui.Graphics aGraphics,Matrix aMatrix) {
/*        if (mSelectedIndex>=0) {
                String lStr = this.getCaption(mSelectedIndex);
                aGraphics.setFont(Font.getFont(Font.FACE_SYSTEM,Font.STYLE_BOLD,Font.SIZE_SMALL));
                aGraphics.setColor(0xFFFFFF);
                aGraphics.fillRoundRect(0,0,aGraphics.getClipWidth(),13,10,10);
                aGraphics.setColor(0x000000);
                aGraphics.drawRoundRect(0,0,aGraphics.getClipWidth(),13,10,10);
                aGraphics.drawString(lStr,5,1,0);
                }
  */      
    }

    /*
     * Distancia perpendicular minima entre un punto y un segmento
     */
public int PntToSegmentDistance(int Px,int Py,int x1,int y1,int x2,int y2) {
  int Ratio,Dx,Dy;
  Dx    = (x2 - x1)>>12;
  Dy    = (y2 - y1)>>12;
  Ratio = ((Px - x1) * Dx + (Py - y1) * Dy) / (Dx * Dx + Dy * Dy); //ratio de 0 a 4096
  if (Ratio <= 0) return fastDistance(Px, Py, x1, y1);
  else if (Ratio >= (1<<12))  return fastDistance(Px, Py, x2, y2);
    else return fastDistance(Px, Py, ((1<<12) - Ratio) *(x1>>12) + Ratio * (x2>>12), ((1<<12) - Ratio) * (y1>>12) + Ratio * (y2>>12) );
}
            
/*    
Intersecion entre polilineas A y B (false si no existe), la devuelve en P
*/
public boolean polyLineIntersection(int indexA,int indexB, Point2D I) {
   // Cargamos los puntos de la primera calle
   int lStartPosA=0,lStartPosB=0;
   for (int i=0;i<indexA;i++)
       lStartPosA+=mPoints[i];
   for (int i=0;i<indexB;i++)
       lStartPosB+=mPoints[i];
    Point2D A = new Point2D();
    Point2D B = new Point2D();
    Point2D C = new Point2D();
    Point2D D = new Point2D();
    for (int u=1;u<mPoints[indexA];u++) {
        A.x=Px[lStartPosA+u-1];
        A.y=Py[lStartPosA+u-1];
        B.x=Px[lStartPosA+u];
        B.y=Py[lStartPosA+u];
        for (int v=1;v<mPoints[indexB];v++) {
            C.x=Px[lStartPosB+v-1];
            C.y=Py[lStartPosB+v-1];
            D.x=Px[lStartPosB+v];
            D.y=Py[lStartPosB+v];
            if (segmentIntersection(A,B,C,D,I))
                return true;
        }
    }
  return false;
}


/*
Intersecion entre segmentos AB y CD (false si no existe), la devuelve en P
*/
public boolean segmentIntersection(Point2D A,Point2D B,Point2D C,Point2D D,Point2D P) {
    	/*
			  (Ay-Cy)(Dx-Cx)-(Ax-Cx)(Dy-Cy)
	  r = -----------------------------  (eqn 1)
			  (Bx-Ax)(Dy-Cy)-(By-Ay)(Dx-Cx)
	  */
		 int lDenom = (((B.x-A.x)>>8)*((D.y-C.y)>>8)-((B.y-A.y)>>8)*((D.x-C.x)>>8));
                 lDenom=lDenom>>8;
		 if (lDenom==0)
				return false;

		 int r=( ((A.y-C.y)>>8) * ((D.x-C.x)>>8) - ((A.x-C.x)>>8) * ((D.y-C.y)>>8) )/lDenom;
		 /*
			(Ay-Cy)(Bx-Ax)-(Ax-Cx)(By-Ay)
		s = -----------------------------  (eqn 2)
			(Bx-Ax)(Dy-Cy)-(By-Ay)(Dx-Cx)
		*/
		int s=( ((A.y-C.y)>>8) * ((B.x-A.x)>>8) - ((A.x-C.x)>>8) * ((B.y-A.y)>>8) )/lDenom;

                if ((r<=-5) || (r>=(1<<8)+5 ) || (s<=-5) || (s>=(1<<8)+5 )) //Le damos un changui de 5>>8 para las perpendiculares exactas
			return false;

		P.x=(A.x+ ((r*(B.x-A.x))>>8) );
		P.y=(A.y+ ((r*(B.y-A.y))>>8) );

		return true;

		}
                    
    
}
