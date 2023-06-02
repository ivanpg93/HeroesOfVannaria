/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m03.uf4.p01.heroesofvannaria;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author jmartin
 */
public class HeroesOfVannaria {

  private ArrayList<Personatge> personatges;
  private ArrayList<Arma> armes;
  private Scanner in;
  private int[] nivells = {100, 200, 500, 1000, 2000};

  public HeroesOfVannaria() {
    in = new Scanner(System.in);
    armes = new ArrayList<>();
    personatges = new ArrayList<>();
    crearArmes();
  }

  private void crearArmes() {
    /*
    0 - Daga
    1 - Espasa
    2 - Martell de combat
     */

    armes.add(new Arma(5, 15, "Daga"));
    armes.add(new Arma(10, 10, "Espasa"));
    armes.add(new Arma(15, 5, "Martell de combat"));
  }

  /**
   * Mètode auxiliar per inserir alguns personatges de prova mentre que no
   * tinguem completat els mètodes per inserir personatges per part de l'usuari
   * i per llegir personatges de l'arxiu CSV
   */
  private void inserirPersonatgesProva() {
    Personatge guerrer1 = new Personatge("Guerrer 1", "Guerrer", 12, 12, 12,
            12, 12, armes.get(2));
    Personatge caballer1 = new Personatge("Caballer 1", "Caballer", 10, 14, 10,
            12, 14, armes.get(1));
    Personatge valquiria1 = new Personatge("Valquiria 1", "Valquiria", 10, 10, 10,
            14, 16, armes.get(0));
    personatges.add(guerrer1);
    personatges.add(caballer1);
    personatges.add(valquiria1);
  }

  /**
   * Métode que llegeix d'un arxiu CSV les dades dels personatges del joc
   */
  public void llegirDades(String nomArxiu) {
    Path path = Paths.get(nomArxiu);
    boolean finalizado = false;
    int indiceArma = 0;
    try {
      BufferedReader entrada = Files.newBufferedReader(path, Charset.forName("UTF-8"));
      String linea = entrada.readLine();
      while (linea != null) {
        String[] dades = linea.split(";");

        for (int j = 0; j < armes.size(); j++) {
          if (armes.get(j).getNom().equals(dades[7]) && !finalizado) {
            finalizado = true;
            indiceArma = j;
          }
        }

        Personatge personaje = new Personatge(dades[0], dades[1], Integer.parseInt(dades[2]),
                Integer.parseInt(dades[3]), Integer.parseInt(dades[4]), Integer.parseInt(dades[5]),
                Integer.parseInt(dades[6]), armes.get(indiceArma), Integer.parseInt(dades[8]), Integer.parseInt(dades[9]));
        this.personatges.add(personaje);
        linea = entrada.readLine();
      }
      entrada.close();
    } catch (IOException ex) {
      System.out.println("No se puede abrir el archivo.");
    }
  }

  /**
   * Mostra un menú a l'usuari amb diferents opcions que es processen fins que
   * l'usuari tria la de sortir
   */
  public void jugar() {
    boolean sortir = false;
    while (!sortir) {
      switch (mostrarMenu()) {
        case "1":
          crearPersonatge();
          break;
        case "2":
          combat();
          break;
        case "X":
        case "x":
          sortir = true;
          break;
        default:
          System.out.println("ERROR: opció incorrecta. "
                  + "Premi ENTER per continuar");
          in.nextLine();
      }
    }
  }

  /**
   * Demana a l'usuari les dades d'un personatge i l'afegeix a la llista i el
   * salva a l'arxiu CSV
   */
  private void crearPersonatge() {
    String nom, classe;
    int fue, con, vel, inte, sor;
    int numeroArma;
    int total = 45;

    System.out.println("Introdueix el nom del personatge: ");
    nom = in.nextLine();
    System.out.println("Introdueix la classe del personatge: ");
    classe = in.nextLine();
    System.out.printf("Punts de força. Tens %d punts per distribuir\n", total);
    fue = 3 + Utils.llegeixEnterRang(in, 0, Utils.min(15, total));
    total = total - fue + 3;
    System.out.printf("Punts de constitució. Tens %d punts per distribuir\n", total);
    con = 3 + Utils.llegeixEnterRang(in, 0, Utils.min(15, total));
    total = total - con + 3;
    System.out.printf("Punts de velocitat. Tens %d punts per distribuir\n", total);
    vel = 3 + Utils.llegeixEnterRang(in, 0, Utils.min(15, total));
    total = total - vel + 3;
    System.out.printf("Punts de inteligencia. Tens %d punts per distribuir\n", total);
    inte = 3 + Utils.llegeixEnterRang(in, 0, Utils.min(15, total));
    total = total - inte + 3;
    System.out.printf("Punts de sort. Tens %d punts per distribuir\n", total);
    sor = 3 + Utils.llegeixEnterRang(in, 0, Utils.min(15, total));

    System.out.println("Tria l'arma per al teu personatge:");
    for (int j = 0; j < armes.size(); j++) {
      System.out.println(j + " " + armes.get(j).getNom());
    }
    numeroArma = Utils.llegeixEnterRang(in, 0, 2);

    Personatge personajeNuevo = new Personatge(nom, classe, fue, con, vel, inte, sor, armes.get(numeroArma));
    personatges.add(personajeNuevo);
  }

  private void inserirPersonatge(Personatge personatge) {
    personatges.add(personatge);
  }

  /**
   * Demana a l'usuari triar dos personatges de la llista, no repetits i simula
   * un combat entre ells
   */
  private void combat() {
    Dau dau1 = new Dau(25);
    Dau dau2 = new Dau(25);
    Dau dau3 = new Dau(25);
    System.out.println("Selecciona el primer combatent:");
    for (int i = 0; i < personatges.size(); i++) {
      System.out.printf("%d - %s%n", (i + 1), personatges.get(i));
    }
    int c1 = Utils.llegeixEnterRang(in, 1, personatges.size());
    // Per evitar duplicats recuperem i eliminem temporalment el personatge
    // de la llista
    Personatge atacant = personatges.remove(c1 - 1);

    System.out.println("Selecciona el segon combatent:");
    for (int i = 0; i < personatges.size(); i++) {
      System.out.printf("%d - %s%n", (i + 1), personatges.get(i));
    }
    int c2 = Utils.llegeixEnterRang(in, 1, personatges.size());
    Personatge defensor = personatges.get(c2 - 1);
    // Tornem a inserir el primer personatge a la seva posició original
    personatges.add(c1 - 1, atacant);
    // Fem que comenci el combat el personatge amb major velocitat
    Personatge tmp;
    if (atacant.getVel() < defensor.getVel()) {
      tmp = atacant;
      atacant = defensor;
      defensor = tmp;
    }

    System.out.println("====== INICI DEL COMBAT ======");
    System.out.println(atacant.getNom() + " contra " + defensor.getNom());

    boolean combatFinalitzat = false;
    int ronda = 1;
    while (!combatFinalitzat) {
      System.out.println("--------Ronda " + ronda + "---------");
      ronda++;
      if (atacant.ataca(dau1, dau2, dau3)) {
        System.out.println(atacant.getNom() + " ataca.");
        if (!defensor.esquiva(dau1, dau2, dau3)) {
          System.out.println(defensor.getNom() + " falla l'esquiva.");
          defensor.repDany(atacant);
          System.out.println(defensor.getNom() + " rep " + atacant.getPd()
                  + " punts de Dany i la seva PS baixa a " + defensor.getPs());
        } else {
          System.out.println(defensor.getNom() + " esquiva l'atac.");
        }
      } else {
        System.out.println(atacant.getNom() + " falla l'atac.");
      }
      if (defensor.getPs() <= 0) {
        combatFinalitzat = true;
        System.out.println(defensor.getNom() + " cau derrotat.");
        System.out.println(atacant.getNom() + " guanya el combat!!!");
      } else {
        tmp = atacant;
        atacant = defensor;
        defensor = tmp;
      }
    }
    /*Restauramos los puntos de salud de atacante y defensor al acabar el combate*/
    atacant.restauraPs();
    defensor.restauraPs();

    /*Añadimos los puntos de experiencia al ganador del combate (el último en atacar)*/
    atacant.afegirPex(defensor.getPs());

    if (atacant.getNiv() == 0 && atacant.getPex() >= 100) {
      atacant.pujarDeNivell();
    } else if (atacant.getNiv() == 1 && atacant.getPex() >= 200) {
      atacant.pujarDeNivell();
    } else if (atacant.getNiv() == 2 && atacant.getPex() >= 500) {
      atacant.pujarDeNivell();
    } else if (atacant.getNiv() == 3 && atacant.getPex() >= 1000) {
      atacant.pujarDeNivell();
    } else if (atacant.getNiv() == 4 && atacant.getPex() >= 2000) {
      atacant.pujarDeNivell();
    }

    if (atacant.getPex() >= nivells[atacant.getNiv()]) {
      atacant.setPex(atacant.getPex() - nivells[atacant.getNiv()]);
      atacant.pujarDeNivell();
    }
  }

  public void sobrescribirCSV(String nomArxiu) {
    Path path = Paths.get(nomArxiu);
    try {
      BufferedWriter salida = Files.newBufferedWriter(path, Charset.forName("UTF-8"), 
              StandardOpenOption.CREATE, StandardOpenOption.WRITE, 
              StandardOpenOption.TRUNCATE_EXISTING);
      for (Personatge personaje : personatges) {
        salida.write(personaje.toCSV());
        salida.newLine();
      }
      salida.close();
    } catch (IOException ex) {
      System.out.println("No se puede abrir el archivo.");
    }
  }

  private String mostrarMenu() {
    System.out.println("-----------HEROES OF VANNARIA---------");
    System.out.println("1 - Crear Personatge");
    System.out.println("2 - Iniciar Combat");
    System.out.println("X - Sortir");
    return in.nextLine();
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // TODO code application logic here
    if (args.length != 1) {
      System.out.println("Falta indicar el nom de l'arxiu de dades");
    } else {
      HeroesOfVannaria partida = new HeroesOfVannaria();
      partida.llegirDades(args[0]);
      //partida.inserirPersonatgesProva();
      partida.jugar();
      partida.sobrescribirCSV(args[0]);
    }
  }
}
