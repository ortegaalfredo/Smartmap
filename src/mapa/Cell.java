/*
 * Cell.java
 *
 * Created on 3 de abril de 2006, 04:32
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
import java.io.IOException;
import javax.microedition.lcdui.*;
import java.lang.*;
/**
 *
 * @author aortega
 */
public abstract class Cell {
    
    // Interseccion encontrada en esta celda
    public Intersection mActiveIntersection;
    // Nombre del objeto seleccionado
    public short mSelectedIndex;
    // Array de vertices (De todas las figuras juntos)
    public int Px[],Py[];
    // Tipos de cada figura
    public byte mTypes[];
    // Cantidad de vertices por cada figura
    public short mPoints[];
    // Titulos
    public byte mCaptions[];
    // Bound de este Cell (Real, calculado a partir de los vertices)
    public int RectX1,RectY1,RectX2,RectY2;

    // Esto es pitagoras
    int Distance(int x1,int y1,int x2,int y2) {
        int px=(x2-x1)>>8,py=(y2-y1)>>8;
        return (px*px+py*py);//(int)Math.sqrt(px*px+py*py);
    }
    // Esto es como pitagoras, pero mas rapido
    int fastDistance(int x1,int y1,int x2,int y2) {
        return Math.abs(x2-x1)+Math.abs(y2-y1);
    }
    
   java.util.Vector mVisibleSegments = new java.util.Vector();


    /* Devuelve el string numero aIndex desde el array de strings mCaptions
      mCaptions es un array de bytes conteniendo:
     [len][..string..][len][..lstring...] y asi.*/
    
    int mLastIndex=0,mLastPos=0;
    public String getCaption(short aIndex) {
    byte lLen;
    if (mLastIndex>=aIndex) {
        mLastIndex=0;
        mLastPos=0;
        }
    int lIndex=mLastPos,i;
    for (i=mLastIndex;i<aIndex;i++)   {
         if (lIndex>=mCaptions.length)
             break;
         lLen = mCaptions[lIndex++];
         lIndex+=lLen;
         }
    mLastIndex=i;
    mLastPos=lIndex;
    if (lIndex>=mCaptions.length) {
        mLastIndex=0;
        mLastPos=0;
        return "";
        }
    lLen = mCaptions[lIndex++];
    StringBuffer lStr= new StringBuffer();
    for (i=0;i<lLen;i++)  
        lStr.append((char)mCaptions[lIndex+i]);
    return lStr.toString();
    }
    
     public abstract void PaintTo(Graphics aGraphics,Matrix aMatrix,boolean aDrawFast);
     public abstract void PaintCaptionsTo(Graphics aGraphics,Matrix aMatrix);

     // bounds
     int mRectLeft, mRectTop, mRectWidth, mRectHeight;

    /**
     * Creates a new instance of Cell
     * dis: Input stream del archivo de datos
     * nFigures: Cantidad de figuras a cargar
     * nVertexs: Suma de los vertices de esas figuras
     * aTextIS: Input stream del archivo de titulos de las figuras. Puede ser null.
     */
 
       public Cell(String aFileName,String aTextFileName,boolean aReadBound) {
        try {
        java.io.InputStream is=getClass().getResourceAsStream(aFileName);
        java.io.DataInputStream dis = new java.io.DataInputStream(is);
        
        if (aReadBound) {
            mRectLeft=dis.readInt();
            mRectTop=dis.readInt();
            mRectWidth=dis.readInt();
            mRectHeight=dis.readInt();                   
            }
        
        int nFigures =  dis.readInt();
        int nVertexs =  dis.readInt();
        mActiveIntersection = null;
        mPoints = new short[nFigures];
        mTypes = new byte[nFigures];
        byte lBuf[] = new byte[nFigures];
        Px = new int[nVertexs];
        Py = new int[nVertexs];
        // Cargamos tipos
        dis.read(mTypes);
        // Cargamos Numero de Vertices
        
        dis.read(lBuf);

        RectX1=RectY1=99999;
        RectX2=RectY2=-99999;

        byte lCharBuf[] = new byte[100];
               
        // Cargamos numero de puntos por figuras
        for (int i=0;i<nFigures;i++)   {
            int lPoints = lBuf[i];
            if (lPoints<0) lPoints+=256;
            mPoints[i]=(short)lPoints;
        }
        // Cargamos vertices
        int cMaxBuf = 500;//cantidad de vertex a cargar de una vez
        int lVertexsBuf;        
        int lVertexsRestantes = nVertexs;
        lCharBuf = new byte[cMaxBuf*4];
        java.io.ByteArrayInputStream bais;
        java.io.DataInputStream dis2;
        int lVertexsCont=0;
        int lFileCount=0;
        while (lVertexsRestantes>0) {
            if (lVertexsRestantes>=cMaxBuf) lVertexsBuf=cMaxBuf;
            else lVertexsBuf=lVertexsRestantes;
            int lReaded=dis.read(lCharBuf,0,lVertexsBuf*4)/4;
            lVertexsRestantes-=lReaded;
            bais = new java.io.ByteArrayInputStream(lCharBuf);
            dis2 = new java.io.DataInputStream(bais);
            for (int i=0;i<lReaded;i++,lVertexsCont++)
                Px[lVertexsCont]=dis2.readInt();
            if (lReaded!=lVertexsBuf) { // Se termino el archivo
                bais.close();
                dis2.close();
                dis.close();
                try {
                    is=getClass().getResourceAsStream(aFileName+"."+lFileCount++);
                    dis = new java.io.DataInputStream(is);
                } catch (Exception e) {break;}
                
            }
        bais.close();
        dis2.close();
        }
        lVertexsCont=0;
        lVertexsRestantes=nVertexs;
        while (lVertexsRestantes>0) {
            if (lVertexsRestantes>=cMaxBuf) lVertexsBuf=cMaxBuf;
            else lVertexsBuf=lVertexsRestantes;
            int lReaded=dis.read(lCharBuf,0,lVertexsBuf*4)/4;
            lVertexsRestantes-=lReaded;
            bais = new java.io.ByteArrayInputStream(lCharBuf);
            dis2 = new java.io.DataInputStream(bais);
            for (int i=0;i<lReaded;i++,lVertexsCont++)
                Py[lVertexsCont]=dis2.readInt();
            if (lReaded!=lVertexsBuf) { // Se termino el archivo
                bais.close();
                dis2.close();
                dis.close();
                try {
                    is=getClass().getResourceAsStream(aFileName+"."+lFileCount++);
                    dis = new java.io.DataInputStream(is);
                } catch (Exception e) {break;}
            }
            bais.close();
            dis2.close();
            }

        // Cargamos texto
        lFileCount=0;
        java.io.InputStream aTextIS=getClass().getResourceAsStream(aTextFileName);
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        
        while (aTextIS!=null) {
            lCharBuf = new byte[aTextIS.available()];
            aTextIS.read(lCharBuf);
            aTextIS.close();
            aTextIS=null; //Liberamos lo antes posible
            baos.write(lCharBuf);
            try {
                aTextIS=getClass().getResourceAsStream(aTextFileName+"."+lFileCount++);
                } catch (Exception e) {
                    break;
                    }
        }
        mCaptions=baos.toByteArray();
        baos.close();
        baos=null;

       } catch (java.io.IOException e) {};
    }
    
}
