package main;
import java.util.*;

public class Items{
  int rarity;
  int damage = 10;
  int health = 50;
}

class Sword extends Items{
  Sword(int rarity){
    this.rarity = rarity; 
    damage = damage*rarity;

  }
}

class Sheild extends Items{
  Sheild(int rarity){
    this.rarity = rarity;
    health = health*rarity;
  }
}
