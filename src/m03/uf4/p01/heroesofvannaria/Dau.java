/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m03.uf4.p01.heroesofvannaria;

/**
 *
 * @author jmartin
 */
public class Dau {
  private int cares;
  
  public Dau(int cares){
    this.cares = cares;
  }
  
  public int tirada(){
    return (int)(Math.random()*cares + 1);
  }
}
