/*
 * Matrix.java
 *
 * Created on 4 de agosto de 2005, 02:19
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
 */

package mapa;

/**
 *
 * @author aortega
 */
public class Matrix {
       /** Matriz de transformacion **/
    public int a11,a12,a21,a22;
    /** Matriz de traslacion **/
    int b1,b2;
    
    // Las operaciones (<< 16) y (>>16) son para usar punto fijo de 16 bits
    
    public void reset() {
        a11=a22=1 << 20;
        a12=a21=0 << 20;
        b1=b2=0;
    }
    
    public void translate(int x,int y) {
       b1+=x;b2+=y;
    }
    
    public void scale(int sX,int sY) {
        a11=(a11/sX);
        a12=(a12/sY);
        a21=(a21/sX);
        a22=(a22/sY);
    }
    
    public short transformXF(int X) {
        return (short)((a11*(X+b1))>>20); //optimizacion (sin angulos)
    }

    public short transformYF(int Y) {
        return (short)((a22*(Y+b2))>>20); //optimizacion (sin angulos)
    }
    
    /** Creates a new instance of Matrix */
    public Matrix() {
    }
    
}
