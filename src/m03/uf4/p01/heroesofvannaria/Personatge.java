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
public class Personatge {
  private String nom;
  private String classe;
  private int fue;
  private int con;
  private int vel;
  private int inte;
  private int sor;
  private Arma arma;
  private int ps;
  private int pa;
  private int pe;
  private int pd;
  private int niv;
  private int pex;

  public Personatge(String nom, String classe, int fue, int con, int vel, int inte, int sor, Arma arma) {
    this.nom = nom;
    this.classe = classe;
    this.fue = fue;
    this.con = con;
    this.vel = vel;
    this.inte = inte;
    this.sor = sor;
    this.arma = arma;
    calculaDerivades();
    niv = 0;
    pex = 0;
  }

  public Personatge(String nom, String classe, int fue, int con, int vel, int inte, int sor, Arma arma, int niv, int pex) {
    this.nom = nom;
    this.classe = classe;
    this.fue = fue;
    this.con = con;
    this.vel = vel;
    this.inte = inte;
    this.sor = sor;
    this.arma = arma;
    this.niv = niv;
    this.pex = pex;
    calculaDerivades();
  }
  
  
  
  private void calculaDerivades(){
    ps = con + fue;
    pd = (fue + arma.getWpow())/4;
    pa = inte + sor + arma.getWvel();
    pe = vel + sor + inte;
  }

  public String getNom() {
    return nom;
  }

  public String getClasse() {
    return classe;
  }

  public int getFue() {
    return fue;
  }

  public int getCon() {
    return con;
  }

  public int getVel() {
    return vel;
  }

  public int getInte() {
    return inte;
  }

  public int getSor() {
    return sor;
  }

  public Arma getArma() {
    return arma;
  }

  public int getPs() {
    return ps;
  }

  public int getPa() {
    return pa;
  }

  public int getPe() {
    return pe;
  }

  public int getPd() {
    return pd;
  }

  public int getNiv() {
    return niv;
  }

  public int getPex() {
    return pex;
  }
  
  public void setPex(int pex){
    this.pex = pex;
  }
  
  public boolean ataca(Dau ... daus){
    int tirada = 0;
    for (int i = 0; i < daus.length; i++) {
      tirada += daus[i].tirada();
    }
    if(tirada <= pa){
      return true;
    }else{
      return false;
    }
  }
  
  public boolean esquiva(Dau ... daus){
    int tirada = 0;
    for (int i = 0; i < daus.length; i++) {
      tirada += daus[i].tirada();
    }
    if(tirada <= pe){
      return true;
    }else{
      return false;
    }
  }
  
  public void repDany(Personatge atacant){
    ps -= atacant.pd;
  }
  
  public void restauraPs(){
    ps = con + fue;
  }
  
  public void afegirPex(int pex){
      if (this.niv < 5){
        this.pex = this.pex + pex;
      }
  }
  
  public void pujarDeNivell(){
    this.niv++;
    /*Sumamos un punto a cada estadística*/
    this.fue++;
    this.con++;
    this.vel++;
    this.inte++;
    this.sor++;

    /*Una vez actualizadas las estadísticas, calculamos las estadísticas derivadas*/
    calculaDerivades();
  }
  
  @Override
  public String toString(){
    return nom + "(fue:" + fue + ",con:" + con + ",vel:" +vel + ",inte:" 
            + inte + ",sor:" + sor + ",niv:" + niv + ",pex: " + pex 
            + ") - " + arma.getNom();
  }
  
  public String toCSV(){
      return nom + ";" + classe + ";" + fue + ";" + con + ";" + vel + ";" + inte
              + ";" + sor + ";" + arma.getNom() + ";" + niv + ";" + pex;
  }
}
