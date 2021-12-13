package main;
import java.util.*;

public class Items{
  int rarity;
  int damage = 10;
  int health = 50;
  public int getDamage(){return damage;}
  public int getHealth(){return health;}
}

class Sword extends Items{
  Sword(int rarity){
    this.rarity = rarity;
    damage = damage*rarity;
    health = 0;
  }
}
class Sheild extends Items{
  Sheild(int rarity){
    this.rarity = rarity;
    health = health*rarity;
    damage = 0;
   }
}
