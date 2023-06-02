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
public class Arma {
  private int wpow;
  private int wvel;
  private String nom;

  public Arma(int wpow, int wvel, String nom) {
    this.wpow = wpow;
    this.wvel = wvel;
    this.nom = nom;
  }

  public int getWpow() {
    return wpow;
  }

  public int getWvel() {
    return wvel;
  }

  public String getNom() {
    return nom;
  }     
  
}
