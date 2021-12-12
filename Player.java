package main;
import java.util.*;

public class Player{
  int level;
  int health = 100;
  int baseHealth = 100;
  int damage = 100;
  int baseDamage = 100;

  Player(int level){
    this.level = level;
    health = health * level;
    damage = damage * level;
  }

  public int getHealth(){return health; }

  public int getDamage(){return damage; }

  public int getLevel(){return level; }

  public void setLevel(int level){this.level = level; }

  public void setDamage(int newDamage,int item){this.damage = (baseDamage * level)+item;}

  public void setHealth(int newHealth, int item){this.health = (baseHealth * level) + item; }

  public String toString(){
    return "Player is level " + getLevel() + "; and has " + getHealth() + "hp and does " + getDamage() + " damage";
  }
}
