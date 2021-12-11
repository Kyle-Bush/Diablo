package main;
import java.util.*;

public class Enemy{
  int level;
  int health;
  int baseHealth = 50;
  int damage; 
  int baseDamage = 5;

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
}

class Grunt extends Enemy{
  Grunt(int level){
    this.level = level;
    health = 50*level;
    damage = 5*level;
  }
  public String toString(){
    return "Grunt has " + health + " HP; does " + damage + " damage and is level " + level  ;
  }
}

class MiniBoss extends Enemy{
  MiniBoss(int level){
    this.level = level;
    health = 10*level*5;
    damage = 10*level*5;
  }
  public String toString(){
    return "MiniBoss has " + health + "HP; does " + damage + " damage and is level " + level  ;
  }
}

class Boss extends Enemy{
  Boss(int level){
    this.level = level;
    health = 10*level*10;
    damage = 10*level*10;
  }
public String toString(){
  return "Boss has " + health + "HP; does " + damage + " damage and is level " + level  ;
 }
}
