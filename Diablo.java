package main;
import java.util.*;

public class Diablo{
  //Initilizes stage to be used as a global variable
  static int stage = 1;
  static int Num = 0;
  public static void main(String[] args) {
    ArrayList<Items> Items = new ArrayList<Items>();
    Player Kyle = new Player(1);
    ArrayList<Enemy> Enemy = new ArrayList<Enemy>();
    Grunt Clark = new Grunt(1);
    Enemy.add(Clark);
    MiniBoss Bob = new MiniBoss(1);
    Enemy.add(Bob);
    Boss John = new Boss(1);
    Enemy.add(John);
    while(Kyle.getHealth() > 0){
      if(stage%10 == 0){
        Num = 1;
        Battle(Kyle, Enemy);
      }else if(stage%25 == 0){
        Num = 2;
        Battle(Kyle, Enemy);
      }else{
        Num = 0;
        Battle(Kyle, Enemy);
      }
    }
  }
  //used to track the current stage
  public static void Stage(){ stage ++; }
  //Prints out the info when it is a Fight against a Grunt
  public static void Info(Player Kyle, ArrayList<Enemy> Enemy){
    System.out.println("The current stage is "+ stage);
    System.out.println("Place holder for Grunt");
    System.out.println(Enemy.get(Num));

    System.out.println("Hero Place holder");
    System.out.println(Kyle);
  }

  public static void PlayerKill(){
    System.out.println("you died");
    stage = 1;
  }
  //Tracks Grunt Kills and levels them up
  public static void Kill(){
    Enemy.get(Num).updateStats(Enemy.get(Num).getLevel()+1);
    if(Num > 0){
     Random(Num);
    }
    Stage();
    UpdateStats();
  }
  //Regular attack for Player against Grunt
  public static int Regular(){return (Enemy.get(Num).getHealth() - Kyle.getDamage());}
  //Power attack, has a 50/50 chance to do double damage
  public static void Power(){
    double random_int = (Math.random());
    if(random_int  >= 0.5){
       Enemy.get(Num).setHealth(Enemy.get(Num).getHealth() - (2*Kyle.getDamage()));
      System.out.println("You attacked using Power attack doing " + 2*(Kyle.getDamage()) + " damage");
    } else {
      Enemy.get(Num).setHealth(Enemy.get(Num).getHealth());
       System.out.println("You attacked using Power attack doing " + 0 + " damage");
    }
  }
  //Regular attack for Grunt against Player
  public static int Reg(){return (Kyle.getHealth() - Enemy.get(Num).getDamage());}
  //Handles the different ways to damage the Grunt
  public static void Fight(){
    Scanner in = new Scanner(System.in);
    System.out.println("Choose an attack 1:2 Regular or Power(double damage but 50% chance to miss)");
    int input = in.nextInt();
      if(input == 1){
        Enemy.get(Num).setHealth(Regular());
        System.out.println("You attacked using regular attack doing " + Kyle.getDamage() + "damage");
      } else if(input == 2){
        Power();
    }
    System.out.println("Grunt attacks for " + Enemy.get(Num).getDamage());
    Kyle.setHealth(Reg(),Items.get(FindMaxItemHealth()).getHealth());
  }
  //Entire fight for Grunt
  public static void Battle(){
    while(Enemy.get(Num).getHealth() > 0 && Kyle.getHealth() > 0){
      Info();
      Fight();
      clear();
    }
    if(Enemy.get(Num).getHealth() <= 0){
      Kill();
    } else {
      PlayerKill();
    }
  }
 //Puase and clear used for proper managing of terminal text

  public static void Random(int creator){
    int min = 1*creator;
    int max = 2*creator;
    int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
    double weapon = (Math.random());
    String rarityString = "";
    switch (random_int){
      case 1: rarityString = "Common";
        break;
      case 2: rarityString = "Rare";
        break;
      case 3: rarityString = "Epic";
        break;
      case 4: rarityString = "Legendary";
        break;
      default: break;
    }
    if(weapon >= 0.5){
      Sword Sword = new Sword(random_int);
      Items.add(Sword);
      System.out.println("You Found a Item!! it is a " + rarityString + " Sword");
      SwordItemUpdate();
    }else{
      Sheild Sheild = new Sheild(random_int);
      Items.add(Sheild);
      System.out.println("You Found a Item!! it is a " + rarityString + " Sheild");
      SheildItemUpdate();
    }
  }
  public static void pause(int ms) {
      try {
          Thread.sleep(ms);
      } catch (InterruptedException e) {
          System.err.format("IOException: %s%n", e);
      }
  }
  public static void clear() {
    pause(1000);
    System.out.print("\033[H\033[2J");
    System.out.flush();
 }
}

//javac -d . *.java
//java main.Diablo
