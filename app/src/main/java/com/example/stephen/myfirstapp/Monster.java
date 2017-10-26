package com.example.stephen.myfirstapp;

/**
 * Created by Stephen on 10/25/2017.
 *@author Stephen and Ming
 * A monster!
 */
public class Monster {
    /**Name of the monster	 */
    private String name;
    /**armor level of the monster	 */
    private int armor;
    /** description of the monster */
    private String description;

    /**
     * @param name
     *            This monster's name.
     * @param armor
     *            The armor level of this monster.
     * @param description
     *            A description of this monster.
     */
    public Monster(String name, int armor, String description) {
        this.name=name;
        this.armor=armor;
        this.description=description;
    }

    /** @return name of the monster */
    public String getName() {
        return name;
    }

    /** @return armor level of the monster	 */
    public int getArmor() {
        return armor;
    }

    /** @return description of the monster */
    public String getDescription() {
        return description;
    }
}
