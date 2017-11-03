package com.example.stephen.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private int bestWeaponDamage;
    private TextView textView;
    private Room currentRoom;
    private int score;
    private String display="";
    public final static String DISPLAY_KEY = "com.example.myfirstapp.DISPLAY";
    public final static String ROOM_KEY = "com.example.myfirstapp.ROOM";
    public final static String WEAPON_KEY ="com.example.myfirstapp.WEAPON";
    private Room bridge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textToDisplay);
        if(savedInstanceState!=null) {
            display=savedInstanceState.getString(DISPLAY_KEY);
            currentRoom = (Room) savedInstanceState.get(ROOM_KEY);
            bestWeaponDamage = savedInstanceState.getInt(WEAPON_KEY);
        }
        else {
            bridge = new Room("bridge",
                    "the bridge of a once impressive starship, that has since taken heavy damage");
            setUpStuff();
            currentRoom = bridge;
            bestWeaponDamage = 3;
            display = display.concat("You are in the bridge\n");
            display = display.concat("\n");
            textView.setText(display);
            listCommands();
        }
        textView.setText(display);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        textView.setText(savedInstanceState.getString(DISPLAY_KEY));
        currentRoom = (Room) savedInstanceState.get(ROOM_KEY);
        bestWeaponDamage = savedInstanceState.getInt(WEAPON_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(DISPLAY_KEY, display);
        outState.putParcelable(ROOM_KEY, currentRoom);
        outState.putInt(WEAPON_KEY, bestWeaponDamage);
        super.onSaveInstanceState(outState);
    }

    public void attack(String name) {
        Monster monster = currentRoom.getMonster();
        if (monster != null && monster.getName().equals(name)) {
            if (bestWeaponDamage > monster.getArmor()) {
                if(name.equals("claws"))
                    display=display.concat("That cat is declawed!\n");
                else
                    display=display.concat("You strike him dead!\n");
                currentRoom.setMonster(null);
            } else {
                if(name.equals("claws")) {
                    display=display.concat("Your blows are harmless against the might of CAT.\n");
                    display=display.concat("The " + monster.getName() + " scratch you into oblivion!\n");
                }
                else {
                    display=display.concat("Your blow bounces off harmlessly.\n");
                    display=display.concat("The " + monster.getName() + " eats your head!\n");
                }
                display=display.concat("\n");
                display=display.concat("GAME OVER");
            }
        } else {
            display=display.concat("There is no " + name + " here.\n");
        }
        textView.setText(display);
    }
    public void setUpStuff() {
        // Create rooms
//        Room bridge = new Room("bridge",
//                "the bridge of a once impressive starship, that has since taken heavy damage");
        Room messHall = new Room("mess hall", "a vast mess hall with a vaulted tritanium ceiling, that once housed many happy crewmen on their lunch breaks");
        Room armory = new Room("armory", "an armory once filled with potent weaponry, perhaps there is still something left..");
        Room brig = new Room("brig",
                "the brig, where misbehaving crewmen are locked up, as well as terrible ancient evils. Strange, one of the cell doors appears to be smashed open from the inside..");
        Room captainsQuarters = new Room("captain's quarters", "the captain's quarters, you always wondered what he was up to in here..");
        // Create connections
        bridge.addNeighbor("north", messHall);
        messHall.addNeighbor("south", bridge);
        messHall.addNeighbor("west", armory);
        armory.addNeighbor("east", messHall);
        messHall.addNeighbor("east", brig);
        brig.addNeighbor("west", messHall);
        bridge.addNeighbor("below", captainsQuarters);
        captainsQuarters.addNeighbor("above", bridge);
        // Create and install monsters
        messHall.setMonster(new Monster("lunatic", 2, "once a proud crewman, this man is now a lunatic, driven mad from the horrors he has witnessed aboard this ship"));
        brig.setMonster(new Monster("captain", 4, "the once respected captain that has been possessed by the space demon, and bears his teeth at you in anger"));
        captainsQuarters.setMonster(new Monster("claws",4,"a ferocious feline's fury lurking in the darkness, but without its claws, would be rendered helpless"));
        // Create and install treasures
        messHall.setTreasure(new Treasure("guest", 30,
                "a cowering guest aboard the ship that hasn't been possessed yet, and needs your help"));
        brig.setTreasure(new Treasure("friend", 70,
                "your loyal friend held captive by the space demon, and cannot get free on her own"));
        captainsQuarters.setTreasure(new Treasure("cat", 500,"the captain's famous cat, that appears to be immune to the space demon's influences (were it to be saved, you would be a true hero)"));
        // Create and install weapons
        armory.setWeapon(new Weapon("destructonator", 5, "the fantastic destructonator, illegal in seven systems, lying on the ground before you"));
        // The player starts in the bridge, armed with a sword
//        currentRoom = bridge;
//        bestWeaponDamage = 3;
    }
    /** Moves in the specified direction, if possible. */
    public void go(String direction) {
        Room destination = currentRoom.getNeighbor(direction);
        if (destination == null) {
            display=display.concat("You can't go that way from here.\n");
        } else {
            currentRoom = destination;
        }
        textView.setText(display);
    }
    public void handleCommand(String line) {
        String[] words = line.split(" ");
        if (currentRoom.getMonster() != null
                && !(words[0].equals("attack") || words[0].equals("look"))) {
            display=display.concat("You can't do that with unfriendlies about.\n");
            listCommands();
        } else if (words[0].equals("attack")) {
            attack(words[1]);
        } else if (words[0].equals("go")) {
            display=display.concat("You are in the "+currentRoom.getNeighbor(words[1]).getName()+"\n");
            go(words[1]);
        } else if (words[0].equals("look")) {
            look();
        } else if (words[0].equals("take")) {
            take(words[1]);
        }else if (words[0].equals("save")) {
            save(words[1]);
        } else {
            display=display.concat("I don't understand that.\n");
            listCommands();
        }
        textView.setText(display);
    }
    public void listCommands() {
        display=display.concat("Examples of commands:\n");
        display=display.concat("  attack lunatic\n");
        display=display.concat("  go north\n");
        display=display.concat("  look\n");
        display=display.concat("  take destructonator\n");
        display=display.concat("  save guest\n");
        textView.setText(display);
    }
    /** Prints a description of the current room and its contents. */
    public void look() {
        display=display.concat("You are in " + currentRoom.getDescription() + ".\n");
        if (currentRoom.getMonster() != null) {
            display=display.concat("There is "
                    + currentRoom.getMonster().getDescription() + " here.\n");
        }
        if (currentRoom.getWeapon() != null) {
            display=display.concat("There is "
                    + currentRoom.getWeapon().getDescription() + " here.\n");
        }
        if (currentRoom.getTreasure() != null) {
            display=display.concat("There is "
                    + currentRoom.getTreasure().getDescription() + " here.\n");
        }
        display=display.concat("Exits: " + currentRoom.listExits()+"\n");
        textView.setText(display);
    }
    public void take(String name) {
        Weapon weapon = currentRoom.getWeapon();
        if (weapon != null && weapon.getName().equals(name)) {
            currentRoom.setWeapon(null);
            if (weapon.getDamage() > bestWeaponDamage) {
                bestWeaponDamage = weapon.getDamage();
                display=display.concat("You'll be a more effective fighter with this!\n");
            }
        } else {
            display=display.concat("There is no " + name + " here.\n");
        }
        textView.setText(display);
    }
    public void save(String name) {
        Treasure treasure = currentRoom.getTreasure();
        if (treasure != null && treasure.getName().equals(name)) {
            currentRoom.setTreasure(null);
            score += treasure.getValue();
            display=display.concat("You have saved "+name+"!\n");
            display=display.concat("Your score is now " + score + " out of 100.\n");
            if (score == 100) {
                display=display.concat("\n");
                display=display.concat("You have won, but the space demon may return...\n");
            }
            if (score==600) {
                display=display.concat("\n");
                display=display.concat("You have won! The space demon's influence is banished forever by the might of the CAT!\n");
            }
        }
        else {
            display=display.concat("There is no " + name + " here.\n");
        }
        textView.setText(display);
    }



    public void sendMessage(View view)
    {
        display="";
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        handleCommand(editText.getText().toString());

    }
}
