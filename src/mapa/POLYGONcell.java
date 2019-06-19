/*
 * POLYGONcell.java
 *
 * Created on 3 de abril de 2006, 04:43
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

import net.sourceforge.jmicropolygon.PolygonGraphics; 

public class POLYGONcell extends Cell {
    
    // Elemento visible
    public class Segment {
        short index;
        int pX[],pY[];
        byte type;
    }
    public Segment newSegment() {return new Segment();}

  boolean pointInsidePolygon(int x,int y,int polyX[],int polyY[]) {
  int      i, j=0         ;
  boolean  oddNODES=false ;
  int polySides = polyX.length;
  for (i=0; i<polySides; i++) {
    j++; if (j==polySides) j=0;
    if (polyY[i]<y && polyY[j]>=y
    ||  polyY[j]<y && polyY[i]>=y) {
      if (polyX[i]+(y-polyY[i])/(polyY[j]-polyY[i])*(polyX[j]-polyX[i])<x) {
        oddNODES=!oddNODES; }}}
  return oddNODES; 
  }
    
   // Retorna el poligono (Que es un segmento) que contiene a ese punto
   public Segment SegmentOn(int x,int y,int aRadius) {
       Segment lSeg,lResult=null;
       int lDistance;
       for (int i=0;i<mVisibleSegments.size();i++ ) {
           lSeg=(Segment)mVisibleSegments.elementAt(i);
           if (pointInsidePolygon(x,y,lSeg.pX,lSeg.pY)) 
               return lSeg;
       }
       return null;
   }

   /**
     * Creates a new instance of POLYGONcell 
     * @param lFileName 
    * @param lTextFileName 
    * @param lLoadBounds 
    */
    public POLYGONcell(String lFileName,String lTextFileName,boolean lLoadBounds) {
        super(lFileName,lTextFileName,lLoadBounds);
        mSelectedIndex=-1;
    }
    public void PaintCaptionsTo(javax.microedition.lcdui.Graphics aGraphics,Matrix aMatrix) {
    aGraphics.setColor(0x0);
    Segment lSeg;
    int xPoint = 0,yPoint = 0;
    String lStr;
    for (int i=0;i<mVisibleSegments.size();i++ ) {
               lSeg=(Segment)mVisibleSegments.elementAt(i);
               xPoint=aMatrix.transformXF(lSeg.pX[0]);
               yPoint=aMatrix.transformYF(lSeg.pY[0]);
               lStr=getCaption(lSeg.index);
               if (lStr!=null)
                 aGraphics.drawString(lStr,xPoint,yPoint,0);
               }
    }

    public void PaintTo(javax.microedition.lcdui.Graphics aGraphics,Matrix aMatrix,boolean aDrawFast) {
    int lOldType = 0;
    Segment lSeg;
    for (int i=0;i<mVisibleSegments.size();i++ ) {
               lSeg=(Segment)mVisibleSegments.elementAt(i);
               if (lSeg.index == mSelectedIndex) {//Seleccionado
                            aGraphics.setColor(0xFF0000);
                            lOldType=-1;
               }
               else
                if (lOldType!=lSeg.type) {
                    switch (lSeg.type) {
                        case 0x1f: //Rio
                        case 0x48: //Rio mediano
                        case 0x40: //Lago pequeño
                        case 0x41: //Lago pequeño
                        case 0x32: //Mar
                            aGraphics.setColor(0x0000FF);
                            break;
                        case 0x04: //Base militar
                        case 0x05: //Estacionamiento
                        case 0x08: //Shopping
                        case 0x0c: //Complejo industrial
                        case 0x13: //Edificio
                        case 0x1a: //Cementerio
                            aGraphics.setColor(0xa0a0a0);
                            break;
                        case 0x07: //Aeropuerto
                        case 0x0e: //Pista de aeropuerto
                            aGraphics.setColor(0x707070);
                            break;
                        case 0x0a: //Facultad
                        case 0x0b: //Hospital
                        case 0x19: //Deportes
                            aGraphics.setColor(0xa06060);
                            break;
                        case 0x1e: //Parque del estado
                            aGraphics.setColor(0x50C050);
                            break;
                        case 0x17: //Parque
                        case 0x18: //Cancha de golf (??)
                            aGraphics.setColor(0x00C000);
                            break;
                        default:
                            aGraphics.setColor(0xFFd0d0);
                          //  continue;
                            }
                    lOldType = lSeg.type;
                 }
            int xPoints[] = new int[lSeg.pX.length];
            int yPoints[] = new int[lSeg.pY.length];
            for (int q=0;q<lSeg.pX.length;q++)
                { 
                xPoints[q]=aMatrix.transformXF(lSeg.pX[q]);
                yPoints[q]=aMatrix.transformYF(lSeg.pY[q]);
                }
            if (aDrawFast) {
                for (int q=1;q<lSeg.pX.length;q++)
                    aGraphics.drawLine(xPoints[q-1],yPoints[q-1],xPoints[q],yPoints[q]);
                aGraphics.drawLine(xPoints[0],yPoints[0],xPoints[lSeg.pX.length-1],yPoints[lSeg.pX.length-1]);
                }
//                  PolygonGraphics.drawPolygon(aGraphics, xPoints,  yPoints);
                else
                  PolygonGraphics.fillPolygon(aGraphics, xPoints,  yPoints); 
            }
        
    }
        
        
}
