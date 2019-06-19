/*
 * POICELL.java
 *
 * Created on 3 de abril de 2006, 04:38
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
public class POICELL extends Cell {
   
    // Elemento visible
    class Segment {
        short index;
        int x,y;
    }

   public Segment newSegment() {return new Segment();}
   
   // Retorna el segmento mas cercano en un radio a un punto dado, o null
   public Segment SegmentOn(int x,int y,int aRadius) {
       Segment lSeg,lResult=null;
       int lDistance;
       for (int i=0;i<mVisibleSegments.size();i++ ) {
           lSeg=(Segment)mVisibleSegments.elementAt(i);
           lDistance=fastDistance(lSeg.x,lSeg.y,x,y);
           if (lDistance<aRadius) {
               lResult=lSeg;
               aRadius=lDistance;
               }
       }
       return lResult;
   }

    public POICELL(String lFileName,String lTextFileName,boolean lLoadBounds) {
        super(lFileName,lTextFileName,lLoadBounds);
    }
       
    public void PaintTo(javax.microedition.lcdui.Graphics aGraphics,Matrix aMatrix,boolean aDrawFast) {
    }
    
   public void PaintCaptionsTo(javax.microedition.lcdui.Graphics aGraphics,Matrix aMatrix) {
    aGraphics.setColor(0x000000);
    short lPx,lPy;
    Segment lSeg;
    for (int i=0;i<mVisibleSegments.size();i++ ) {
                        lSeg=(Segment)mVisibleSegments.elementAt(i);
                        lPx=aMatrix.transformXF(lSeg.x);
                        lPy=aMatrix.transformYF(lSeg.y);
                        aGraphics.fillRect(lPx-1,lPy-1,3,3);
                        aGraphics.drawString(getCaption(lSeg.index),lPx+4,lPy+4,0);
                        if (lSeg.index == mSelectedIndex) {//Seleccionado
                            aGraphics.drawLine(lPx-3,lPy-3,lPx+3,lPy+3);
                            aGraphics.drawLine(lPx-3,lPy+3,lPx+3,lPy-3);
                            }
        }
    }
    
}
