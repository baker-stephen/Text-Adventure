package com.example.stephen.myfirstapp;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Stephen on 10/25/2017.
 */

public class Room implements Parcelable {
    /** description of the room */
    private String description;
    /** a list of the names of the exits */
    private ArrayList<String> exits;
    /** the monster in the room, null if there is no monster */
    private Monster monster;
    /** name of the room */
    private String name;
    /** a list of the rooms adjacent to this one, in the order at which their names appear in exits */
    private ArrayList<Room> neighbors;
    /** the treasure in the room, null if there is no treasure */
    private Treasure treasure;
    /** the weapon in the room, null if there is no weapon */
    private Weapon weapon;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        exits = new ArrayList<String>(4);
        neighbors = new ArrayList<Room>(4);
        monster = null;
        treasure = null;
        weapon = null;
    }


    /**@return the name of this room */
    public String getName() {
        return name;
    }

    /**@return the description of this room */
    public String getDescription() {
        return description;
    }

    /** adds the name of an adjacent room to exits at the same index as the room object that is added to neighbors */
    public void addNeighbor(String name, Room room) { // TODO make this better so that it is easier to set up rooms
        neighbors.add(room);
        exits.add(name);
    }

    /**@return the neighboring room of the given name, if no such room exists, null*/
    public Room getNeighbor(String name) {
        if (exits.contains(name))
            return neighbors.get(exits.indexOf(name));
        else
            return null;
    }

    /**@return the exits from this room */
    public String listExits() {
        return exits.toString();

    }

    /**@return the treasure in this room */
    public Treasure getTreasure() {
        return treasure;
    }

    /**@return the monster in this room */
    public Monster getMonster() {
        return monster;
    }

    /**@return the description of this room */
    public Weapon getWeapon() {
        return weapon;

    }

    /** sets the treasure of this room */
    public void setTreasure(Treasure treasure) {
        this.treasure=treasure;
    }

    /** sets the monster of this room */
    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    /** sets the weapon of this room */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.name, this.description});
        dest.writeStringList(this.exits);
        dest.writeTypedList(this.neighbors);
        dest.writeArray(new Object[] {this.monster, this.weapon, this.treasure});

    }
    // Parcel constructor
    public Room(Parcel in) {
        String[] nameAndDescriptionData = new String[2];
        ArrayList<String> exitData = new ArrayList<String>(1);
        ArrayList<Room> neighborData = new ArrayList<Room>(1);
        Object[] otherData = new Object[3];
        in.readStringArray(nameAndDescriptionData);
        in.readStringList(exitData);
        in.readTypedList(neighborData, Room.CREATOR);
        otherData=in.readArray(Object.class.getClassLoader());
        this.name=nameAndDescriptionData[0];
        this.description=nameAndDescriptionData[1];
        this.exits=exitData;
        this.neighbors=neighborData;
        this.monster=(Monster) otherData[0];
        this.weapon= (Weapon) otherData[1];
        this.treasure= (Treasure) otherData[2];
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        public Room[] newArray(int size) {
            return new Room[size];
        }
    };
}
