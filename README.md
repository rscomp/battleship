BattleShip Game

This Project is developed as a maven project in Java language.

Fot Unit Test : JUnit, Mockito
For Project Management :  maven

For compiling and packaging :
mvn package
For running unit tests :
mvn test

For running the project with Input file
Execute jar and provide file as a input



Sample Input :
5 E
2
Q 1 1 A1 B2
P 2 1 D4 C3
A1 B2 B2 B3
A1 B2 B3 A1 D1 E1 D4 D4 D5 D5

Sample Output :
Player-1 fires a missile with target A1 which got miss
Player-2 fires a missile with target A1 which got hit
Player-2 fires a missile with target B2 which got miss
Player-1 fires a missile with target B2 which got hit
Player-1 fires a missile with target B2 which got hit
Player-1 fires a missile with target B3 which got miss
Player-2 fires a missile with target B3 which got miss
Player-1 has no more missiles left to launch
Player-2 fires a missile with target A1 which got hit
Player-2 fires a missile with target D1 which got miss
Player-1 has no more missiles left to launch
Player-2 fires a missile with target E1 which got miss
Player-1 has no more missiles left to launch
Player-2 fires a missile with target D4 which got hit
Player-2 fires a missile with target D4 which got miss
Player-1 has no more missiles left to launch
Player-2 fires a missile with target D5 which got hit
Player-2 won the battle
