# Monster Fighting Game - Assignment Instructions

## Part 1: Overview

### Scenario:
Let's create a unique and fun roleplaying-themed monster fighting game! All the details will be explained below, but let's first take a high-level look at the structure.

### Logistics:
For this assignment, you will be implementing the classes shown in the UML diagram.

In the UML diagram, each rectangle represents a class. There is a **Monster** superclass along with two abstract subclasses: **Humanoid** and **Ooze**. Multiple concrete subclasses extend from these abstract classes: **Bandit, Doppelganger, Jubilex, and Ochre**.

There are also two interfaces: **Comparable** and **Cloneable**. These interfaces are built-in Java interfaces and are **not explicit files** in the assignment. 

- A **solid line with a hollow triangle** represents inheritance (**extends**).
- A **dotted line with a hollow triangle** represents implementing an interface.

If the image appears blurry in the write-up, refer to **PA7_UML.pdf** for better clarity.

---

## Part 2: Coding Guidelines

- **NOTE 1:** You must **NOT** change any data field or method signature defined in the write-up. Do **not** add additional parameters to methods. However, you may add helper methods if desired.
- **NOTE 2:** Since no starter code is provided, ensure adherence to **CSE 8B and 11 style guidelines**. Declare necessary constants according to the guidelines.
- **NOTE 3:** Assume all inputs are valid unless otherwise specified.
- Compile your code frequently to catch errors early using:
  ```sh
  javac *.java
  ```
- Each member field must be **private**. Use **getter methods** (accessors) and **setter methods** (mutators) to modify these fields.
- Use the `this` keyword to access member variables when local variables have the same name.

---

## Part 3: Monster.java (9 points)

The **Monster** class serves as the superclass for most other classes in this assignment. It initializes the core attributes of a monster and defines default behaviors for methods, some of which are overridden by subclasses. Since this class implements the `Comparable` interface, modify the class declaration accordingly.

### Data Fields:
- `private int armor`
- `private int vitality`
- `private double speed`

### Constructors:
1. **`protected Monster()`**
   - No-argument constructor, initializes:
     - `armor = 0`
     - `vitality = 0`
     - `speed = 0.0`
2. **`protected Monster(int armor, int vitality, double speed)`**
   - Sets instance variables to the passed arguments using `this`.

### Getter & Setter Methods:
- `public int getArmor()`
- `public int getVitality()`
- `public double getSpeed()`
- `public void setArmor(int armor)`
- `public void setVitality(int vitality)`
- `public void setSpeed(double speed)`

### Methods to Implement:
1. **`public int compareTo(Monster monster)`**
   - Compare based on the average of `armor`, `vitality`, and `speed`.
   - Return `-1`, `0`, or `1` accordingly.
   - Ensure proper **double division** handling.

2. **`public boolean sameSpecies(Monster monster)`**
   - Returns `true` if both objects are of the same class.
   - Use `getClass().getName()` for comparison.

3. **`public String toString()`**
   - Returns a formatted string representation of a Monster.

4. **Abstract Methods:**
   - `public abstract void rest();`
   - `public abstract double calculatePower();`
   - `public abstract int attack(Monster monster);`

---

## Part 4: Humanoid.java and Ooze.java (6 points)

### Humanoid.java (3 points)
- Implements `intelligence` and `weapon` fields.
- Implements constructors and getters/setters.
- Includes abstract method `public abstract int strike(Monster monster);`

### Ooze.java (3 points)
- Implements `volume` and `acidity` fields.
- Implements constructors and getters/setters.
- Includes abstract method `public abstract boolean corrode();`

---

## Part 5: Concrete Monster Subclasses

### Bandit
- Implements combat mechanics based on weapon type.
- Uses a critical modifier in `calculatePower()`.
- Implements `strike()` and `attack()` methods.

### Doppelganger
- Implements the `Cloneable` interface.
- Has a `clones` list.
- Overrides `calculatePower()` to account for clone power.
- Implements `clone()`, `rest()`, `strike()`, and `attack()` methods.

### Jubilex
- Gains massive armor on rest.
- Has a high-power calculation with a critical multiplier.
- Implements `corrode()` with a 95% success rate.
- Implements `strike()` and `attack()` methods.

### Ochre
- Implements the `Cloneable` interface.
- Has a `clones` list.
- Implements `clone()` with volume constraints.
- Overrides `calculatePower()` to consider clone power.
- Implements `corrode()`, `strike()`, and `attack()` methods.

---

## Part 6: Dungeon.java (25 points)

### Method 1 - `calculateBettingOdds(Monster monster1, Monster monster2)`
- Determines win probability based on power ratios.
- Adjusts odds based on `compareTo()` results.

### Method 2 - `armory(Monster monster)`
- Humanoids can change weapons.
- Oozes double their volume.
- Cloneable monsters can generate additional clones.

### Method 3 - `showdown(Monster monster1, Monster monster2)`
- Simulates a battle between two monsters.
- Implements logic for corrosion, poison, clone absorption, and combat mechanics.
- Returns:
  - `0` → Tie game
  - `1` → `monster1` wins
  - `2` → `monster2` wins

---

## Part 7: Testing and Additional Methods

### Sample Battle Output:
```
Round 0:
(Bandit)
(Jubilex)
A: 1000000000
V: 1000000000
S: 100.00
A: 1
V: 1
S: 1.00
Left does 650000019 damage!
Right does 2147483647 damage!
GAME OVER - TIE: Both monsters died!
```

### Unit Testing Guidelines:
- Create at least **five test cases** covering:
  - `clone()`
  - `compareTo()`
  - `calculateBettingOdds()`
  - `armory()`
  - `showdown()`
- Run your unit tests using:
  ```sh
  javac *.java
  java Assignment7
  ```
- Use `toString()` to debug monster objects.

---

This document now includes **all** provided instructions and is formatted in Markdown (`.md`) for easier saving and compatibility.