package main;
import java.util.*;
import java.io.IOException;

public class Diablo{
  static int stage = 1;
  static int Num = 0;
  static int gold = 1000;
  static Player Kyle = new Player(1);
  static ArrayList<Items> Items = new ArrayList<Items>();
  static ArrayList<Enemy> Enemy = new ArrayList<Enemy>();
  static ArrayList<Integer> TotalDamageDone = new ArrayList<Integer>();
//https://stackoverflow.com/a/5762502
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";
  public static final String ANSI_BOLD = "\033[0;1m";


  public static void main(String[] args) {
    Grunt Clark = new Grunt(1);
    Enemy.add(Clark);
    MiniBoss Bob = new MiniBoss(1);
    Enemy.add(Bob);
    Boss John = new Boss(1);
    Enemy.add(John);
    Sheild Null = new Sheild(0);
    Items.add(Null);
    while(Kyle.getHealth() > 0){
      if(stage%2 == 0){
        Num = 1;
        Battle();
      }else if(stage%3 == 0){
        Num = 2;
        Battle();
      }else if(stage%5 == 0){
        ItemShop();
      }else{
        Num = 0;
        Battle();
      }
    }
  }
  public static int TotalDamageDone() {
    int sum = 0;
    for (int i: TotalDamageDone) {
        sum += i;
    }
    return sum;
 }
  public static int FindMaxItemDamage(){
    int max = Items.get(0).getDamage();
    int maxItemDamage = 0;
    for(int i = 0; i < Items.size(); i ++){
      if(Items.get(i).getDamage() > max){
        max = Items.get(i).getDamage();
        maxItemDamage = i;
      }
    }
    return maxItemDamage;
  }
  public static int FindMaxItemHealth(){
    int max = Items.get(0).getHealth();
    int maxItemHealth = 0;
    for(int i = 0; i < Items.size(); i++){
      if(Items.get(i).getHealth() > max){
        max = Items.get(i).getHealth();
        maxItemHealth = i;
      }
    }
    return maxItemHealth;
  }
  public static void SwordItemUpdate(){
    Kyle.setDamage(Kyle.getDamage(), Items.get(FindMaxItemDamage()).getDamage());
  }
  public static void SheildItemUpdate(){
    Kyle.setHealth(Kyle.getHealth(), Items.get(FindMaxItemHealth()).getHealth());
  }
  public static void UpdateStats(){
    Kyle.setLevel(Kyle.getLevel()+ 1);
    SwordItemUpdate();
    SheildItemUpdate();
  }
  //used to track the current stage
  public static void Stage(){ stage ++; }
  //Prints out the info when it is a Fight against a Grunt
  public static void Info(){
    System.out.println(ANSI_YELLOW + "The current stage is "+ stage + ANSI_RESET +"\n");

    Encounter(Num);

    System.out.println(Enemy.get(Num));

    PlayerPic();
    System.out.println(Kyle);
  }

  public static void PlayerKill(){
    System.out.println("you died");
    stage = 0;
  }
  //Tracks Grunt Kills and levels them up
  public static void Kill(){
    Enemy.get(Num).updateStats(Enemy.get(Num).getLevel()+1);
    gold += ((Num+2)*stage);
    System.out.println("You defeated the enemy!! You received " + gold + " gold");
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
  public static void Fight() {
    try{
    Scanner in = new Scanner(System.in);
    System.out.println("Choose an attack 1:2 Regular or Power(double damage but 50% chance to miss)");
    int input = in.nextInt();
      if(input == 1){
        Enemy.get(Num).setHealth(Regular());
        System.out.println(ANSI_CYAN + "You attacked using regular attack doing " + Kyle.getDamage() + "damage" + ANSI_RESET);
      } else if(input == 2){
        Power();
    }
  }
    catch (InputMismatchException ex) {
            System.out.println("input is not with in integer range "+ ex + "Ur Dumb so u still loose health");
         }
      if(Enemy.get(Num).getHealth() <= 0){

        }else{
    System.out.println(ANSI_RED + "Grunt attacks for " + Enemy.get(Num).getDamage() + ANSI_RESET);
    TotalDamageDone.add(Enemy.get(Num).getDamage());
    Kyle.setHealth(Reg(),Items.get(FindMaxItemHealth()).getHealth());
   }
  }
  //Entire fight for Grunt
  public static void Battle() {
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
  public static void RegenHealth(){
      if(gold >= 50){
    Kyle.setMaxHealth(Items.get(FindMaxItemHealth()).getHealth());
  }else{
    System.out.println("Sorry you are misisng " + (50 - gold) + "gold");
    pause(2000);
    clear();
    ItemShop();
  }
}
  public static void LegendaryItem(){
    double random_int = Math.random();
      if(gold >= 300){
        if(random_int >= 0.5){
          Sword Sword = new Sword(4);
          Items.add(Sword);
          System.out.println("You received a Legendary Sword");
          SwordItemUpdate();
        }else{
          Sheild Sheild = new Sheild(4);
          Items.add(Sheild);
          System.out.println("You received a Legendary Sheild");
          SheildItemUpdate();
        }
  }else{
    System.out.println("Sorry you are misisng " + (300 - gold) + "gold");
    pause(2000);
    clear();
    ItemShop();
  }

  }
  public static void ItemShop()  {
    System.out.println("Welcome to the Item shop, Here you can buy different items with the gold collected while killing enemies"+ "\n");
    System.out.println("1: Health regen: 50 Gold");
    System.out.println("2: Legendary Item: 300 Gold");
    System.out.println("99: Exit");
    Scanner in = new Scanner(System.in);
    int input = in.nextInt();
    switch (input){
      case 1: RegenHealth();
      break;
      case 2: LegendaryItem();
      break;
      case 99: Stage();
      break;
    }
  }
//https://manytools.org/hacker-tools/convert-images-to-ascii-art/go/ **used to convert all images into chatacters
  public static void PlayerPic(){
System.out.println(ANSI_BOLD+ "                &           @                            ");
System.out.println("             * &@.            ,@@                        ");
System.out.println("             (                   @                       ");
System.out.println("             *  @  @@    @@   @  %                       ");
System.out.println("             ,   @           @                           ");
System.out.println("         .    @   ( &       @   &                        ");
System.out.println("        @    @ %     @   @    &&@@                       ");
System.out.println("       &@       &  @@@@@@/ /                             ");
System.out.println("     .     @          @@@@@@@@@@@@@@&&&&%%%##((/*..*@    ");
System.out.println("      &*,     @  %   &                              %    ");
System.out.println("     ,@    #*& .     &     @  @         #         @ %    ");
System.out.println("       .  @,   @     &    .            @@      ,    #    ");
System.out.println("     #          @   %      &           .        (  @#    ");
System.out.println(" @ &                 %   @    @        @     @      #    ");
System.out.println("    /             *     #  *          .        (    (    ");
System.out.println("     @          /    &. #      (.   &     *, @ @    (    ");
System.out.println("       .         @@@.&.    . @       &    @.        (    ");
System.out.println("        @ @,,%*  @   &.         .     @     &@@@@@@%(    ");
System.out.println("       &@          * &.    /          @     %@    @ (    ");
System.out.println("     @ .&       *  * &.  ..   /  ,  (     @   @ (   #    ");
System.out.println("                /  ,&&.        @        #      @  @ #    ");
System.out.println("               %    /&.   %           @ #      ,   /#    ");
System.out.println("                     &.               @          @  #    ");
System.out.println("              @      &.       @       @,            %    ");
System.out.println("                @    &    , .          @         @ (%    ");
System.out.println("               (     &   ((                         &    ");
System.out.println("                     & @&@@@@@@@@@@@@@@@@@@@@@@@@@& &    ");
System.out.println("              #                  @                       ");
System.out.println("             @                                           ");
System.out.println("                                  @                      ");
System.out.println("            ,                                            ");
System.out.println("           @                       @                     ");
System.out.println("                                                         ");
System.out.println("        @%&                         @                    ");
System.out.println("                                      ,@&                " + ANSI_RESET);
  }
  public static void Gruntpic(){
System.out.println("                      /&@@&&@&(,        ");
System.out.println("              *&/             (&.       ");
System.out.println("        (  ,@,                  .@.     ");
System.out.println("      .% ,&.                      %/    ");
System.out.println("     .% %,               /#        &,   ");
System.out.println("     &.@                   @        @   ");
System.out.println("    ,%&                    #*       @   ");
System.out.println("    /@.                    /#/%@@,  @   ");
System.out.println("    /%            .*%&#/.  (*       @   ");
System.out.println("    #&            ,        @  .,   (/   ");
System.out.println("     *#          *.  *.   ,%    /% ,&   ");
System.out.println("      /%    *@(*&*        &.    ,#  %.  ");
System.out.println("       (@,.&     @       ./     %%@,&   ");
System.out.println("      (@. #@.   &.           /@* %/     ");
System.out.println("     (@.    /%%&@%(**/#%@&(,     #*     ");
System.out.println("    /#                            @     ");
System.out.println("   *#                             &     ");
System.out.println("   @                              ,#    ");
System.out.println("  #(,,,,,*/((#%%&%&%%#(**..       ,%    ");
System.out.println("    #(        #@           &.   ,(@@    ");
System.out.println("  .@          .&          .%         @, ");
System.out.println("  /&&@&&&&%%%%#            %&/.       /#");
System.out.println("                                    .,  ");
}
  public static void BossPic(){
System.out.println("                                         ```````                                      ");
System.out.println("                                ``...----:::::::::-----..```                          ");
System.out.println("                           ``.--:::::://///////////////////::-.`                      ");
System.out.println("                     ```.-:::::::/://///////////////////////+++/:-.`                  ");
System.out.println("                  `.-::////:///:::::////:::::::::::::::://////+++++/:.`               ");
System.out.println("               ``-:////////://::::://::--------:::::::::::::::////++++/-`             ");
System.out.println("             `.:+++////////::::--:/:--------------:::::::::::://////++++/-`           ");
System.out.println("           `-/++++/////::::::---::-----------------::::::::://////////++++/-`         ");
System.out.println("         `-/+//////:::::-------:-------------------::::::::://////////+++++/:`        ");
System.out.println("       `-//:::::::--------....-:-------------------::::::::://////////++++o++/`       ");
System.out.println("      .::-.........---...........------------------::::::::////////++++++ooo++/`      ");
System.out.println("     -:-...............----------------------------::::::::////////+++++oooooo+:`     ");
System.out.println("   `.:.........................--------------------::::::::///////+++++ooooooo+/-     ");
System.out.println("  `---..........................-------------------::::::::://////++++ooooooooo+/`    ");
System.out.println("  .:......`.....................-------------------:::::::://////+++++ooooooooo++`    ");
System.out.println(" `:......```....................-------------------:::::::://///+++++oooooooooo+o-    ");
System.out.println(" .-.....````....................-------------------:::::::://///+++++oooooooooooo:`   ");
System.out.println(" .-.....```.................--..-------------------::::::::////++++++ooooooooooo+/`   ");
System.out.println(" .-.....``......--:::::---..---.-------------------::::::::////+++++oooooooooooo+/`   ");
System.out.println(" .-.......`...://:------:::----.-------------------::::::::////+++++ooooooooooooo/`   ");
System.out.println(" `-.........-/:-.............--------..-----------:::::::://///+++++ooooooooooooo/`   ");
System.out.println(" `-........::-.............----------.------------:::::::://///+++++ooooooooooooo/`   ");
System.out.println("  `-......::.......--------------------++///:::::::::://///////++++++ooooooooooso:    ");
System.out.println("   --....-:`.......-------------..--------:://+++++++++++oooooo+++++++oooooooosoo.    ");
System.out.println("   `-............---------------...------:::::::::::::::://///++++++++ooooooooso+-//:.");
System.out.println("    `-...................---------.---:::::---::::::////////////+++++ooooooooossoososs");
System.out.println("    `.-.................----------..---:::-------::::::///+++++++++oooooooooosssssssoo");
System.out.println("     `-.................---------....--:::--------::::///++oosssssooooooooooosoosyyyyo");
System.out.println("      `-...............-----::-:--...--:::-------:++osyhdhhyyyyyhhyo+ooooooooooosyysso");
System.out.println("       .-.............-----::::::--...-::/:--:/+ymNNNNMMNys:::/+oo+++++ooooooooossssso");
System.out.println("       `.............----:::::///:-....-://:::o+mMMMMMMNdy+-:/++o+++++++oooooooossoooo");
System.out.println("       `.-.......--::/++syyhhdddyo:....-::/:-.-sdmNNmmdyso//++++++++++++oooooooooooo+/");
System.out.println("       ---....-:+oo++oh/dMMMMNNNyo-....--::::---/+////::::////////+++++oooooooo/://:.`");
System.out.println("       .-:-..-/+/.````+ydNNNNmdyo-...--::///:-------::::::://////++++++oooosoo/`      ");
System.out.println("       `-:-....---.`...-+syso+/-....---:://++::------::::://////++++++oooooooo.       ");
System.out.println("        `--.......--------------...--::://///::::----:::://////+++++++oooooo+.        ");
System.out.println("         .-.......----------------.--:::://::::/+/---:::///////+++++++ooooo:`         ");
System.out.println("         `.-....------:::::::-------..---::/++oo/:---::///////+++++++oooo/.`          ");
System.out.println("          `.-...------::::::::::::::-.-:++++o++//::::://///+++++++oooo+:.             ");
System.out.println("            .---------::::::::::::::---://///////::::///////++++ooo+/.`               ");
System.out.println("            `.------:::::::://///:::----::::::///////////++++oo++:.``                 ");
System.out.println("              `.---::::://///////:::::::::::::////////++++++/:-``                     ");
System.out.println("                `.-::://////////////://::////////+++++++//:``                         ");
System.out.println("                   ``.--::////////////////+++++++//::::+syhs:`                        ");
System.out.println("                        ```.-:+s//+oyyhhhhdddyo----:+ydNNNNmdo:`                      ");
System.out.println("                         `./ydm+::+dNNNNNmdhs+/::ohmNNNNNNNNmdh+/-.`                  ");
System.out.println("                      `:sydNmmy/:+hmmNmNh/:--:+hmNNNNNNNNNNNmmmdhyy+/.`               ");
System.out.println("                    `-ymmNmmmd/:ymmmmmdmy-.-+dmmmmmmmNNNmmmmmmmmmmddyyo/.`            ");
System.out.println("                   .sdmmmmmmms-dmmmmddmmy:odmmmmmmmmmmmmmmmmmmmmmmmmmddhyo/-`         ");
System.out.println("                `-oddmmdmmmmd+hmmddddmmmhdmdmmmmmmmmNNNNmmmmmmmmmmmmmmmmmmdhs/.`      ");
System.out.println("               -ymddmddmmmmddhmmdddddmdmmmmmmmmmmNNNNNNNmmmmmmmmmmmmmmmmmmmmmdhs:``   ");
System.out.println("            `-ydmmdddmmdmddmNmmmdddddmdmmmmmmmmNNNNNmmNNNmNmmmmmmmmmmmmmmmmmmmmmhs:`  ");
System.out.println("           `odmddddddmmddmddNmmddhddmmmmmmmmNNNmmmmmmmmNNNNNmmmmmmmmmmmmmmmmmmmmmdho. ");
System.out.println("          `ydddddmmmmmmmdmddNdddddddmmdmmmNNmmmmmmmmmmNNNNNNNmmmmmmmmmmmmmmmmmmmmmmds`");
System.out.println("          ommmdmmmmmmmddddddNdddhddddmmmmmmmmmmmmmmmmmmNNNNNNNNNmmmmmmmmmmmmmmmmmmmmd:");
System.out.println("         .dmdmmmmmmmddddddddNddhhddddmmmmmmmmmmmmmmmmmmmNNNNmNNNNNNmmmmmmmmmmmmmmmmmd+");
System.out.println("         /mddddmmmddddddddddNdddddddmmmmmmmmmmmmmmmmmmmmmmNNmmNNNymmddmmmmmmmmmmmmmmm/");
System.out.println("         omddddddmddddddddddNdddddmmmddmmmmmmmmmmmmmmmmmmmNNmNmhyodo//oymmmmmmmmmmmmd-");
System.out.println("         /mddds//mddddddddddNddddmmddddmmmmmmmmmmmmmmmmmmmmNmyo/::/:-..-/hmmmmmmmmmm+ ");
System.out.println("         .ydd/`.sdddddddddddNdddmddddddmmmmmmmmmmmmmmmmmmmmmd:--------...:ymmmmmmmd+` ");
System.out.println("          :ds`.:dddddddddddmmdmmdddddddmmmmmmmmmmmmmmmmmmmmmm/----------..-dmmmmds-   ");
System.out.println("           /s.:sdddddddddddmmmddddddddmmmmmmmmmmmmmmmmmmmNmmmh+---------.`-hddho-``   ");
System.out.println("          ` -oodmdmddddddddmmddddddddmmmmmmmmmmmmmmmmmmmmNNNmmho------..:+yhs/.       ");
System.out.println("             `ydddddddddddmmddddddddmmmmmmmmmmmmmmmmmmmmmNNNmmmNo:..`    ``           ");
System.out.println("             -mddddddddddmddmmddddddmmmmmmmmmmmmmmmmNmmmmNNNNmmmmd-`                  ");
System.out.println("            `sddddddddddmmddmmmdddddmmmmmmmmmmmmmmmmmmmmmNNNNNmmmmy`                  ");
System.out.println("            -dddddddddddddddmmmddddmmmmmmmmmmmmmmmmmmmmmmNNNNNNmmmd+`                 ");
System.out.println("           `oddddddddddddddddddddddmmmmmmmmmmmmmmmmmmmmmmNNNNNNmNmdh:                 ");
System.out.println("           .hdddddddddddddddmddddddmmmmmmmmmmmmmmmmmmmmmmmmNNNNNNmmhy`                ");
System.out.println("           /dddddddddddddddmmddddddmmmmmmmmmmmmmmmmmmmmmmmmmNNNNNmmds                 ");
System.out.println("           sddddddddddddddddmmdddddmmmmmmmmmmmmmmmmmmmmmmmmmNNNNNmd+`                 ");
System.out.println("           ddddddddddddddmmdmmmmddddmmmmmmmmmmmmmmmmmmmmmmmmNNNNNNs                   ");
System.out.println("           +hdddddddddddmmmdmmmmddddmmmmmmmmmmmmmmmmmmmmmmNNNNNNNmh`                  ");
System.out.println("           `-shddddddddmmmmmmddddmddmmmmmmmmmmmmmmmmmmmmNNNNNNNNmmd-                  ");
System.out.println("              -hddddddmmmmmmmmmmddmddmmmmmmmmmmmmmmmmmmmmNNNNNNNmmdo                  ");
System.out.println("              `odddddmmmmmmmmmNNNNmmdddddddddddmmmmmmmmmmmmNNNNNNmdh-                 ");
System.out.println("               .yddddmmmmmmmNNNNNNNNNmddhddddddddddmmmmmmmmmNNNNNmmds                 ");
System.out.println("                /hddddmmmmmmmNNNNNNNNNmhohdddddddddddmmmmmmmmmNNNNmdd-                ");
System.out.println("                 +hhddmmmmmmmNNNNNNNNmmd.`-ohhddddddddmmmmmmmmmNNNmmds                ");
System.out.println("                  /hhddmmmmmmNNNNNNNNNmh.   ./yhhddddddddmmmmmmmNNmmmm-               ");
System.out.println("                  `/yhddmmmmmmNNNNNNNNmd.     `/syhhhddddddmmmmmmNNmmds`              ");
System.out.println("                    /yhddmmmmmmNNNNNNmmy`       `:syyhhddddddmmmmNNmmmd-              ");
System.out.println("                     :yhdmmmmmmmmNNNNmmo          `:oyyhhhhddddmmmmmmmms              ");
System.out.println("                      :shdmmmmmmmmNNNmm/            `:osyyhhhddddmmmNNmm-             ");
System.out.println("                       .+hdmmmmmmmNNNmN-              `-+syyhhhddddmNNmmy-            ");
System.out.println("                        .mmmmmmmmmNNNmm/                `-osyyhhhdmmmNmmdy/`          ");
System.out.println("                        .smNNNNNNNNmmmh.                  `-+yhhddmmmNNNNdy+.`        ");
System.out.println("                      `:smNNNNNNNNNNmd:                     `+sysydNNNNNNdhho:..      ");
System.out.println("                      /mddNNNNNNNNmhy/`                       ``:+shmNNmdhdmNmdd.     ");
System.out.println("                      -hhhdhdddddyyyo`                          /hyyhhddhhdddddh.     ");
}
  public static void MiniBossPic(){
System.out.println("              &&&             ");
System.out.println("           @@@@@@@@@          ");
System.out.println("         @@@@@@@@@@@@@        ");
System.out.println("        @ @@@@@@@@@@@.@       ");
System.out.println("        @&@@@@@*@@@@@#@       ");
System.out.println("         @. .@@@@#. ,@        ");
System.out.println("         %@@@@( /@@@@(        ");
System.out.println("       #@@@@@@( #@@@@@@#      ");
System.out.println("    .@@@@@@@@@@&@@@@@@@@@@.   ");
System.out.println("   &@@@@@@@@@ (@@@@@@@@@@@%   ");
System.out.println("  (@@@@ @@@@/ @@#    @@ @@%   ");
System.out.println("  @@@@( @@.%#@@@%****@@ #@@@@ ");
System.out.println(" *@@@@  @*,@@@@@@@@@@@@  @@@@,");
System.out.println(" #@@@@  @@@@@@@@@@@@@@@  @@@@#");
System.out.println(" %@@@@  @@@@@@@@@@@@@@@  @@@@%");
System.out.println(" %@@@@ (@@@@@@@@@@@@@@@  @@@@#");
System.out.println(" ,,.,* &@@@@@@@@@@@@@@@  *. ,*");
System.out.println(" ,@@@& @@@@@@@@@@@@@@@@# @@@@.");
System.out.println("       @@@@@@@  .@@@@@@@      ");
System.out.println("      .@@@@@@@   @@@@@@@      ");
System.out.println("      /@@@@@@/   @@@@@@@      ");
System.out.println("      &@@@@@@    &@@@@@@.     ");
System.out.println("      @@@@@@@    /@@@@@@%     ");
System.out.println("      .,*(%@@     @&#/*,.     ");
System.out.println("     .@@@@@@@     @@@@@@&     ");
System.out.println("     /@@@@@@/     @@@@@@@     ");
System.out.println("     @@@@@@@.     @@@@@@@,    ");
System.out.println("     #@@@@@@       @@@@@@.    ");
  }
  public static void Encounter(int Num){
    if(Num == 0){
      Gruntpic();
    }else if(Num == 1){
      MiniBossPic();
    }else{
      clear();
      System.out.println("Uh Oh... You have encountered THE BOSS");
      BossPic();
    }
  }
    //https://stackoverflow.com/a/56546464
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
    System.out.println("\f");
    System.out.flush();
 }

}
//javac -d . *.java
//java main.Diablo
