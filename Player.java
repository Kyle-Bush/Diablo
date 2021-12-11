package main;
import java.util.*;

public class Player{
  int level;
  int health = 100;
  int baseHealth = 100;
  int damage = 20;
  int baseDamage = 20;

  Player(int level){
    this.level = level;
    health = health*level;
    damage = damage*level; 
  }

  public int getHealth(){return health; }

  public void setHealth(int newHealth){this.health = newHealth; }

  public int getDamage(){ return damage; }

  public int getLevel(){return level; }

  public void setLevel(int level){this.level = level+1; }

  public void updateStats(int level){
    this.level = level;
    health = baseHealth*level;
    damage = baseDamage*level;
  }

  public String toString(){
    return "Player is level " + level + "; and has " + health + "hp and does " + damage + " damage";
  }
}
