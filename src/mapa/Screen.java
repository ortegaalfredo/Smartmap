/*
 * Screen.java
 *
 * Created on 3 de abril de 2006, 03:00
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

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.io.*;

public class Screen extends Canvas {
            /* Constantes */
    public final byte cPOI = 0;
    public final byte cPOLYLINE = 1;
    public final byte cPOLYGON = 2;   
    public final int  cFastViewUmbral = 5000;

    // Borde en el cual el cursor hace un scroll
    public final int cCursorBorder = 5;
    
    // Modo de uso del cursor
    final static int cMODE_PAN=0;
    final static int cMODE_CURSOR=1;
    
    int mCursorMode = cMODE_CURSOR;
    
    int mIncCursor = 2,mRepeatInc=2;
    // Cursor
    public Point2D mCursor = new Point2D();
    //Matriz de transformacion
    Matrix mMatrix = new Matrix();
      // Coordenadas para la matriz, escala y translacion
    int mScale = 1;
    int mTranslateX,mTranslateY;
    
      // Bookmarks (Adentro hay puntos)
    public java.util.Vector mBookmarks = new java.util.Vector();
    
      // Modo FullScreen
    boolean mFullScreenMode = false;
    

// Indica si es necesario recalcular el clipping
    boolean mFastMode = false;
// Indica si es necesario recalcular el clipping
    public boolean mDirty=false;
    // Celdas
    Cell mPois,mPOLYLINEs,mPOLYGONs;
    /* Sector visible de la pantalla */
    public int mCurrentLeft,mCurrentTop;
    public int mCurrentRight,mCurrentBottom;
    // Hilo de modificacion de scroll
    public RefreshTask mRT;
    // Caption en el tooltip del cursor
    String mCursorCaption = null;
    /* Flag que indica si se esta repintando actualmente */
     public boolean mRepainting = false;        
     final public static int cMinClipWidth=166;
 //---------------------------------------------------------------------------------------------------------- 
     /*
      *Calcula lo que hay abajo del cursor y selecciona la calle, etc.
      */
   public void calcCursor() {
            mCursorCaption=null;
            if (mCursorMode==cMODE_PAN) {
                mPOLYLINEs.mSelectedIndex=-1;
                mPois.mSelectedIndex=-1;
                mPOLYGONs.mSelectedIndex=-1;
                return;
                }
            int lcx=mCursor.x*mScale-mTranslateX;
            // Calculamos el radio que corresponde a 2 pixels en la pantalla, por 3, ya que el fastdistance no hace el sqrt.
            int lRadius=(((mCursor.x+2)*mScale-mTranslateX)-lcx)*3;
            int lcy=mCursor.y*mScale-mTranslateY;
            POICELL.Segment seg=((POICELL)mPois).SegmentOn(lcx,lcy,lRadius);
            if (seg!=null) {
                mCursorCaption=mPois.getCaption(seg.index);
                mPOLYLINEs.mSelectedIndex=-1;
                mPois.mSelectedIndex=seg.index;
                mPOLYGONs.mSelectedIndex=-1;
                return;
                }
            POLYGONcell.Segment seg3=((POLYGONcell)mPOLYGONs).SegmentOn(lcx,lcy,0);
            if (seg3!=null) {
                mCursorCaption=mPOLYGONs.getCaption(seg3.index);
                mPOLYLINEs.mSelectedIndex=-1;
                mPois.mSelectedIndex=-1;
                mPOLYGONs.mSelectedIndex=seg3.index;
                return;
                }
 
            POLYLINEcell.Segment seg2=((POLYLINEcell)mPOLYLINEs).SegmentOn(lcx,lcy,lRadius);
            if (seg2!=null) {
                mCursorCaption=mPOLYLINEs.getCaption(seg2.index);
                mPOLYLINEs.mSelectedIndex=seg2.index;
                mPois.mSelectedIndex=-1;
                mPOLYGONs.mSelectedIndex=-1;
                return;
                }
 
   } 
   /* 
    * Pinta el cursor y el caption
    */
   public void paintCursor(Graphics g) {
        if (mCursorMode==cMODE_CURSOR) {
            g.setColor(0x00c0000BF);
            g.drawLine((int)(mCursor.x-3),(int)(mCursor.y-3),(int)(mCursor.x+3),(int)(mCursor.y+3));
            g.drawLine((int)(mCursor.x-3),(int)(mCursor.y+3),(int)(mCursor.x+3),(int)(mCursor.y-3));
            // lcx es la conversion inversa, de pantalla-->lat/long
            int lcx=mCursor.x*mScale-mTranslateX;
            int lcy=mCursor.y*mScale-mTranslateY;
            if (mCursorCaption!=null) {
                int lCaptionLength=g.getFont().stringWidth(mCursorCaption);
                int lCaptionHeight=g.getFont().getHeight();
                g.setColor(0xFFFFFF);
                int lTipX=(int)mCursor.x+5;
                int lTipY=(int)mCursor.y+5;
                // Controlamos que el tooltip no se vaya de la pantalla
                if (lTipX+lCaptionLength > getWidth())
                    lTipX=(int)mCursor.x-5-lCaptionLength;
                if (lTipY+lCaptionHeight > getHeight())
                    lTipY=(int)mCursor.y-5-lCaptionHeight;
                g.fillRoundRect(lTipX,lTipY,lCaptionLength,lCaptionHeight,5,5);
                g.setColor(0x0);
                g.drawString(mCursorCaption,lTipX,lTipY,0);
                return;
                }
            
            }
        
    }

//---------------------------------------------------------------------------------------------------------- 
        /* Seteamos matriz  en base a mSacel y a mTranslateX (LLamar a esto despues de cambiar esos valores!)*/
     public void calculateMatrix() {
        mMatrix.reset();
        mMatrix.scale(mScale,mScale);
        mMatrix.translate(mTranslateX,mTranslateY);
     }
//---------------------------------------------------------------------------------------------------------- 
    /* Pintamos bookmarks*/
    private void paintBookmarks(Graphics g) {
        g.setColor(0x0000FF);
        BookMark bm;
        for (int i=0;i<mBookmarks.size();i++) {
            bm=(BookMark)mBookmarks.elementAt(i);
            if (this.isPointInsideScreen(bm.x,bm.y)) {
                int px=mMatrix.transformXF(bm.x);
                int py=mMatrix.transformYF(bm.y);
                g.drawArc(px-3,py-3,8,8,0,360);
                g.drawArc(px-1,py-1,4,4,0,360);
                String lLat=(bm.y>0)?"S":"N";
                String lLong=(bm.x>0)?"E":"W";
                lLat+="58."+(535160-(Math.abs(bm.x)/94))+"º";
                lLong+="34."+(541140+((12510870-Math.abs(bm.y))/94))+"º";
//                lLat+=Math.abs(bm.y)+"º";
//                lLong+=Math.abs(bm.x)+"º";
                
                g.drawString(lLat,px+5,py+5,0);
                g.drawString(lLong,px+5,py+2+g.getFont().getHeight(),0);
            }
        }
    }
//---------------------------------------------------------------------------------------------------------- 
     protected void paint(Graphics g)
      {
        if (mRepainting) return;
            mRepainting=true;
        calculateMatrix();
        g.setColor(0x00FFB040);
        g.fillRect(g.getClipX(), g.getClipY(), g.getClipWidth(),g.getClipHeight());
        Font font= Font.getFont(Font.FACE_SYSTEM,Font.STYLE_PLAIN,Font.SIZE_SMALL);
        g.setFont(font);
        
        mCurrentLeft=0*mScale-mTranslateX;
        mCurrentTop=0*mScale-mTranslateY;
        
        mCurrentRight=getWidth()*mScale-mTranslateX;
        mCurrentBottom=getHeight()*mScale-mTranslateY;
        
        int lScreenWidth = mCurrentRight-mCurrentLeft;
        
                // El rectangulo de clip tiene un limite de tamaño minimo que hay que respetar, o
                // algunas lineas se clipean mal (Nuestro algoritmo de clip no es exacto)
        if (lScreenWidth<cMinClipWidth) {
            int lCenter=mCurrentLeft+lScreenWidth/2;
            mCurrentLeft=lCenter-cMinClipWidth/2;
            mCurrentRight=lCenter+cMinClipWidth/2;
            lCenter=mCurrentTop+((mCurrentBottom-mCurrentTop)/2);
            mCurrentTop=lCenter-cMinClipWidth/2;
            mCurrentBottom=lCenter+cMinClipWidth/2;
        }
        
        if (mScale>9000) mFastMode = true; // Over 9000!!
                    else mFastMode = false;
        // Calculamos si se selecciono una calle con el cursor
        calcCursor();
        
        // Pintamos elementos
        ((POLYLINEcell)mPOLYLINEs).setScale(this.mScale); // para que se vean bien las calles, necesitamos la escala
        mPOLYLINEs.PaintTo(g,mMatrix,mFastMode);
        mPOLYGONs.PaintTo(g,mMatrix,mFastMode);
        mPois.PaintTo(g,mMatrix,mFastMode);
        
        // Pintamos captions
        if (mScale<6000) 
              mPois.PaintCaptionsTo(g,mMatrix);
        if (mScale<2000) 
              mPOLYGONs.PaintCaptionsTo(g,mMatrix);
        mPOLYLINEs.PaintCaptionsTo(g,mMatrix);

        // Dibujamos bookmarks
        paintBookmarks(g);              
        
        // Dibujamos cursor
        paintCursor(g);
        mRepainting=false;
      }
    
//---------------------------------------------------------------------------------------------------------- 
         /** Creates a new instance of Screen */
    public Screen(String aTileName) {
    LoadTile(aTileName);
    mRT = new RefreshTask(this);
    mCursor.x=getWidth()/2;
    mCursor.y=getHeight()/2;
    loadConfiguration();
    }
    
    public void Start() {
    mRT.start();
    mRepainting=false;
    }
//---------------------------------------------------------------------------------------------------------- 
     /* Carga el tile desde el almacenamiento */
   public void LoadTile(String aTileName) {
   String lTileName;
   lTileName="/"+aTileName;
  try { 
        mPois = new POICELL(lTileName+".POI",lTileName+".txt.POI",true);
        mPOLYLINEs = new POLYLINEcell(lTileName+".POL",lTileName+".txt.POL",false);
        mPOLYGONs = new POLYGONcell(lTileName+".POLYG",lTileName+".txt.POLYG",false);
        // Por defecto nos posicionamos en el medio. Esto se overridea con la configuracion
        mTranslateX= -(mPois.mRectLeft+mPois.mRectWidth/2);
        mTranslateY= (mPois.mRectTop+mPois.mRectHeight/2);
//        mTranslateX=-8710567;
//        mTranslateY=-7040914;
        mScale=5999;
        this.saveConfiguration();
        }   catch (Exception e) {e.printStackTrace();};
   }
   
   
public void ZoomIn() {
mRT.scaleChange=-30<<8;    
}

public void ZoomOut() {
    mRT.scaleChange=30<<8;
}
   
     //---------------------------------------------------------------------------------------------------------- 
     // Procesamos eventos de los botones
protected void keyReleased(int keyCode) {
           mRepeatInc=0;
}

protected void keyRepeated(int keyCode) {
            mRepeatInc++;
            if (mRepeatInc>5) mIncCursor=4;
            if (mRepeatInc>10) mIncCursor=8;
            keyPressed(keyCode);
         }
         protected void keyPressed(int keyCode)
         {
            // the codes coming back do not map exactly to the GAME ACTION keys
            // we need to map them using getGameAction
            System.out.println(""+keyCode);
            if (keyCode<0) {
            switch(getGameAction(keyCode))
            {
            case UP:   keyCode='2';
                       break;
            case LEFT: keyCode='4';
                       break;
            case DOWN: keyCode='8';
                       break;
            case RIGHT:keyCode='6';
                       break;
            }
            }          
            
            switch(keyCode) {
                     // Modo Full Screen
            case '7': mFullScreenMode=!mFullScreenMode;
                      setFullScreenMode(mFullScreenMode);
                      break;
                      // Arriba
            case 38:
            case '2':if (mCursorMode==cMODE_PAN) 
                          mRT.translateYChange+=80*mScale;
                       else {
                            mCursor.y-=mIncCursor;
                            if ((mCursor.y<cCursorBorder) && (mRT.translateYChange==0)) {
                                mCursor.y=cCursorBorder+10;
                                mRT.translateYChange+=80*mScale;
                                }
                            }
                       break;
                       //Izquierda
            case 37:
            case '4': if (mCursorMode==cMODE_PAN)
                          mRT.translateXChange+=80*mScale;
                       else {
                            mCursor.x-=mIncCursor;
                            if ((mCursor.x<cCursorBorder) && (mRT.translateXChange==0)) {
                                mCursor.x=cCursorBorder+10;
                                mRT.translateXChange+=80*mScale;
                                }
                            }
                       break;
                       //Abajo
            case 40:
            case '8': if (mCursorMode==cMODE_PAN)
                          mRT.translateYChange+=-80*mScale;
                       else {
                            mCursor.y+=mIncCursor;
                            if ((mCursor.y>getHeight()-cCursorBorder) && (mRT.translateYChange==0)) {
                                mCursor.y=getHeight()-cCursorBorder-10;
                                mRT.translateYChange+=-80*mScale;
                                }
                            }
                       break;
                       //Derecha
            case 39:
            case '6':if (mCursorMode==cMODE_PAN)
                          mRT.translateXChange+=-80*mScale;
                       else {
                            mCursor.x+=mIncCursor;
                            if ((mCursor.x>getWidth()-cCursorBorder) && (mRT.translateXChange==0)) {
                                mCursor.x=getWidth()-cCursorBorder-10;
                                mRT.translateXChange+=-80*mScale;
                                }
                            }
                       break;
                case '3': ZoomOut();
                         break;
                case '1': ZoomIn();
                         break;
                case '5':if  (mCursorMode==cMODE_PAN) {
                              mCursorMode= cMODE_CURSOR;
                              mCursor.x=getWidth()/2;
                              mCursor.y=getHeight()/2;
                              repaint();
                              }
                         else mCursorMode= cMODE_PAN;
                         break;
                         
                case '0': //Eliminar bookmarks
                          mBookmarks.removeAllElements();
                          break;
                case '*': //Agregar bookmark
                        BookMark bm = new BookMark();
                        bm.x=mCursor.x*mScale-mTranslateX;
                        bm.y=mCursor.y*mScale-mTranslateY;
                        mBookmarks.addElement(bm);
                        break;
            }

         if (mCursorMode==cMODE_CURSOR) {
                mIncCursor=2;
                repaint();
                }
         }

// methods for devices with pointers (PDAs) 
//---------------------------------------------------------------------------------------------------------- 
protected void pointerPressed(int x, int y) {
            mCursorMode=cMODE_CURSOR;
}
protected void pointerDragged(int x, int y)
         {
            mCursor.x = x;
            mCursor.y = y;
            repaint();
         }
 
         
//---------------------------------------------------------------------------------------------------------- 
 public boolean isPointInsideScreen(int Px,int Py) {
         if  ((mCurrentLeft-20<=Px) && (Px<=mCurrentRight+20) &&
              (mCurrentTop-20<=Py) && (Py<=mCurrentBottom+20)) 
              return true;
         else return false;
         }

//---------------------------------------------------------------------------------------------------------- 
  // Guarda los datos de configuracion en almacenamiento persistente
  public void saveConfiguration() {
  if ((mRT!=null) && (mBookmarks!=null))
  try {
      java.io.ByteArrayOutputStream bos =  new java.io.ByteArrayOutputStream();
      java.io.DataOutputStream dos = new java.io.DataOutputStream(bos);
      // Guardamos posicion de la pantalla
      dos.writeInt(mTranslateX);
      dos.writeInt(mTranslateY);
      dos.writeInt(mScale);
      // Guardamos posicion del cursor
      dos.writeInt(mCursor.x);
      dos.writeInt(mCursor.y);
      // Guardamos opciones
      dos.writeBoolean(mRT.softScroll);
      dos.writeBoolean(((POLYLINEcell)(this.mPOLYLINEs)).fastMode);
      // Guardamos bookmarks
      dos.writeShort(mBookmarks.size());
      for (int i=0;i<mBookmarks.size();i++) 
           ((BookMark)mBookmarks.elementAt(i)).SaveToStream(dos);
      dos.close();
      byte lConfig[] = bos.toByteArray();
      javax.microedition.rms.RecordStore.deleteRecordStore("SmartMap");
      javax.microedition.rms.RecordStore rs = javax.microedition.rms.RecordStore.openRecordStore("SmartMap",true);
//      if (rs.getNumRecords()==0)
        rs.addRecord(lConfig,0,lConfig.length);
/*      else {
            int lRecordID=rs.getNextRecordID();
            rs.setRecord(lRecordID,lConfig,0,lConfig.length);
            }
  */    rs.closeRecordStore();
  } catch (Exception e) {e.printStackTrace();};
  
  
  }

  // Carga los datos de configuracion desde almacenamiento persistente
  public void loadConfiguration() {
  javax.microedition.rms.RecordStore rs;
 try {
     rs = javax.microedition.rms.RecordStore.openRecordStore("SmartMap",true);
  } catch (Exception e) {e.printStackTrace();return;}
  try {
      javax.microedition.rms.RecordEnumeration re = rs.enumerateRecords(null,null,false);
      
      if (re.hasNextElement()) {
          java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(re.nextRecord());
          java.io.DataInputStream dis = new java.io.DataInputStream(bais);
      // Cargamos posicion de la pantalla
          mTranslateX=dis.readInt();
          mTranslateY=dis.readInt();
          mScale=dis.readInt();
      // Cargamos posicion del cursor
          mCursor.x=dis.readInt();
          mCursor.y=dis.readInt();
      // Cargamos opciones
          mRT.softScroll=dis.readBoolean();
          ((POLYLINEcell)(this.mPOLYLINEs)).fastMode=dis.readBoolean();
      // Cargamos bookmarks
          short lNumBookmarks = dis.readShort();
          BookMark bm;
          for (int i=0;i<lNumBookmarks;i++) {
                bm = new BookMark(dis);
                mBookmarks.addElement(bm);
                }
      }
  } catch (Exception e) {e.printStackTrace();}
 try {
   rs.closeRecordStore();
  } catch (Exception e) {e.printStackTrace();}
      
  }
//---------------------------------------------------------------------------------------------------------- 
 //---------------------------------------------------------------------------------------------------------- 
 // Thread de modificacion de matriz (Para un scroll suave)
  public class RefreshTask extends java.lang.Thread {
        
        public int scaleChange,translateXChange,translateYChange;
        public boolean softScroll = false;
        
        private Screen mVI;
        public RefreshTask( Screen aVI) {
            mVI=aVI;
        }
        public void run() {
            int lChange;
            int cMinChange = 2*mVI.mScale;
            boolean lRepaint;
            boolean lWaitForClip=false;

            // Parche IBM J2ME
            try {sleep(100);} catch (Exception e) {};
            mVI.mDirty=true;
            lRepaint=false;
            mVI.repaint();

            while (true) { try {
            lRepaint=false;
            while (mVI.mRepainting || mVI.mDirty) 
                try {sleep(50);} catch (Exception e) {};
               
            if ((translateXChange!=0) || (translateYChange!=0) || (scaleChange!=0)) {
                int lChangeX,lChangeY;
                int lChangeScale,lOldScale;
                
               if (!softScroll) { //Sin scroll
                    lChangeX=translateXChange;
                    lChangeY=translateYChange;
                    lChangeScale=scaleChange;
                } else { //Con scroll
                    lChange=scaleChange/2;
                    if (Math.abs(scaleChange)<=255) lChange=scaleChange;
                    lChangeScale=lChange;
                    if (scaleChange<0) {// ZoomIN, hacemos primero el clip porque es jodido
                        lWaitForClip = true;
                        lChangeScale=scaleChange;
                        }
                
                    lChange=translateXChange/2;
                    if (Math.abs(translateXChange)<=cMinChange) lChange=translateXChange;
                    lChangeX=lChange;
                
                    lChange=translateYChange/2;
                    if (Math.abs(translateYChange)<=cMinChange) lChange=translateYChange;
                    lChangeY=lChange;
                    }
                //Transladamos
                mVI.mTranslateX+=lChangeX;
                mVI.mTranslateY+=lChangeY;

                //Escalamos
                lOldScale=mVI.mScale;
                mVI.mScale*=(1<<8)+lChangeScale/100;
                mVI.mScale>>=8;
                //Centramos
                int lRatioX,lRatioY;
                if (mCursorMode!=cMODE_CURSOR)
                    lRatioY=lRatioX=(1<<8)/2;
                else {
                     lRatioX=(mCursor.x<<8)/mVI.getWidth();
                     lRatioY=(mCursor.y<<8)/mVI.getHeight();
                     }
                
                // Centramos zoom en el cursor
                mVI.mTranslateX+=(((mVI.getWidth()*mVI.mScale)-(mVI.getWidth()*(lOldScale)))*lRatioX)>>8;
                mVI.mTranslateY+=(((mVI.getHeight()*mVI.mScale)-(mVI.getHeight()*(lOldScale)))*lRatioY)>>8;
                
                translateXChange-=lChangeX;
                translateYChange-=lChangeY;
                scaleChange-=lChangeScale;
                
                if (Math.abs(translateXChange)<=cMinChange) translateXChange=0;
                if (Math.abs(translateYChange)<=cMinChange) translateYChange=0;
                if (Math.abs(scaleChange)<=1) scaleChange=0;

                try {sleep(30);} catch (Exception e) {};
                lRepaint=true;
                if ((translateXChange==0) && (translateYChange==0) && (scaleChange==0)) 
                    mVI.mDirty=true; //Si terminamos de scrollar, seteamos Dirty=true para que se actualizen las calles 
                }

            if (lRepaint)
                {
                if (lWaitForClip)
                    while (mVI.mDirty)
                        try {sleep(50);} catch (Exception e) {};
                mVI.repaint();
                }
            else try {sleep(100);} catch (Exception e) {};
            } catch (Exception e) {};
            } 
        }
    }   
  
}
