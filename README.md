# Dining Philosophers
 - Justin Forseth
 - Charlie Said

## The Problem
5 philosophers are trapped in what one must assume is a reference to Plato's Cave; a table where they only eat and philosophize, equipped with 5 chopsticks as their only utinsils.

The philosophers cannot speak to each other due to cripplingly underdeveloped social skills.

Each chopstick is placed between 2 philosophers.  The philosophers need to be able to pick up 2 chopsticks before they can eat.  Functionally, this means that they cannot all eat at the same time.

Also, since the philosophers haven't discovered multithreading yet, they can't pick up both chopsticks at once.  They can only do so one at a time.

## Solution 1: Atomic Acquire

The easiest solution is to atomically acquire both chopsticks.  Unfortunately, while this works really well, it involves communication between philosophers (all other philosophers avoid entering their acquision protocol while any single philosopher is doing so).

## Solution 2: Try, if fail, put down
Each philosopher picks up the chopstick to his right, and then tries to pick up the chopstick to his left.  In the event that his left chopstick has already been taken, he puts down the right chopstick.

This solution would work as long as the philosophers don't all get hungry at the same time.  If they do, they will all pick up their right chopstick, leaving no chopsticks on the table.  None of the philosophers will be able to acquire their left chopstick, so they will all put down their right chopstick.  Of course, they will still be hungry, so they will try the process again, to the same effect.

This is called Livelock, since while activity is occurring, no meaningful progress is being made.

## Solution 3: Resource Hierarchy

Each chopstick is given an ID from 0 to 4.  When a philosopher gets hungry, they pick up the chopstick with the lowest value first.

This system is the same as picking up the left chopstick first for 4 philosophers (who will pick up chopstick 2, then 3 or 0, then 1).  But for the last philosopher who has chopsticks 4 and 0, he will pick up the chopsticks in the opposite order (0 first, then 4).  This effectively breaks the loop situation that every philosopher's first chopstick is some other philosopher's second chopstick.

Because there is no circular wait, there can be no deadlock.

## Challenges We Faced

We tried all 3 solutions in order, ultimately settling on Solution 3.  The idea came from the Resource Hierarchy discussion in class.  Solution 3 is very similar to Solution 2, just with a different mechanism for choosing which chopstick to reach for first.
