package com.example.stephen.myfirstapp;

/**
 * Created by Steph on 10/25/2017.
 */

public class Weapon {
    /**Name of the weapon */
    private String name;
    /**damage level of the weapon */
    private int damage;
    /** description of the weapon */
    private String description;

    /**
     * @param name
     *            This weapon's name.
     * @param damage
     *            The damage level of this weapon.
     * @param description
     *            A description of this weapon.
     */
    public Weapon(String name, int damage, String description) {
        this.name=name;
        this.damage=damage;
        this.description=description;
    }

    /** @return name of the weapon */
    public String getName() {
        return name;
    }

    /** @return damage level of the weapon	 */
    public int getDamage() {
        return damage;
    }

    /** @return description of the weapon */
    public String getDescription() {
        return description;
    }
}
