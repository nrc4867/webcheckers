---
geometry: margin=1in
---
# PROJECT Design Documentation

## Team Information
* Team name: 2185-swen-261-04-d-damns
* Team members
  * Dylan Cuprewich
  * Abhaya Tamrakar
  * Mike Bianconi
  * Nicholas Chieppa
  * Suwamik Paul

## Executive Summary

### Purpose
WebCheckers allows users to connect with a server and play against each other at an online game of Checkers.

### Glossary and Acronyms

| Term | Definition |
|------|------------|
| VO | Value Object |
| MVP | Minimum Viable Product |
| Spark | Java-based Server API |
| FreeMarker | Java-generated HTML API |
| Ajax | Allows asynchronous server requests |

## Requirements

This section describes the features of the application.

The major features of the application include being able to signin with the name of your choosing, being able to select the player you want to challenge, move your own pieces, capture enemy pieces, beat your opponent, lose to your opponent, and resign to your opponent.

### Definition of MVP
The MVP includes every player being sign-in before playing a game and being sign-out when the game is finished, the players must play according to the American rules of Checkers and allowing players to resign at any point to end the game.

### MVP Features
Stories/Epics involved in completion of the MVP:
  * Lose
  * Win condition
  * Move
  * Perform turn
  * Jump
  * Resign
  * King
  * Sign-in
  * Start a Game
  
### Roadmap of Enhancements
* We have developed an AI that a user can play against replacing a human opponent. 
* We have also developed a "Spectator" mode where a third user could watch a match occurring 
between two other players without having the ability to play.
* Lastly, we have added a third enhancement where a user could watch the replay of the match,
meaning the user could be simulated the game that was previously played and can control what to watch- next move, previous move.


## Application Domain

This section describes the application domain.

![The WebCheckers Domain Model](pics/domain-model.png)

The main component of the domain model is the WebCheckers game. It is built up of smaller entities such as the players and the board. A player represents the user who is playing the game of WebCheckers and the board is the field on which the game is played on. The board is composed of 64 tiles on which the pieces are placed.  Each player can move one of their 24 pieces per turn, this move must follow the rules. This move can either be moving a piece forward or jumping and capturing an opponent’s piece. The piece can either be a standard piece or an upgraded king which follows a different moving ruleset.


## Architecture and Design

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the web app's architecture.

![The Tiers & Layers of the Architecture](pics/architecture-tiers-and-layers.png)

As a web application, the user interacts with the system using a
browser.  The client-side of the UI is composed of HTML pages with
some minimal CSS for styling the page.  There is also some JavaScript
that has been provided to the team by the architect.

The server-side tiers include the UI Tier that is composed of UI Controllers and Views.
Controllers are built using the Spark framework and View are built using the FreeMarker framework.  The Application and Model tiers are built using plain-old Java objects (POJOs).

Details of the components within these tiers are supplied below.


### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts
with the WebCheckers application.

![The WebCheckers Web Interface Statechart](pics/web-interface.png)

When you connect to the server, you first render to the home page. This Home page displays the players that are online in the lobby. You will have the option to sign in. When you click the sign in button, you render to the sign in page. 
In the sign in page, you are prompted to enter a username to join other players in the home page. If you enter a valid username, you are rendered back to the home page. If it an invalid name, you are prompted to enter the name again.
Once you are signed in and waiting in the home page, if you are challenged or if you challenge another player and they accept, you are sent to the game.
If you want to play against the AI, you can simply click on the AI and play against it.
If you want to spectate a game, simply clicking on the player under the "spectate" header will put you under spectate mode.
And finally, you can click on a game under the "replay" header to watch the replay.
You may sign out after playing a game and you render back to the home page.


### UI Tier
The UI tier contains classes that allow users to make web requests and therefore allows for user interaction. The UI tier is responsible for using model, application, and session data to generate dynamic webpages that respond to changes in the programs state. The UI tier, therefore, is also responsible for the handling of any AJAX calls that a users browser may send. 

<<<<<<< HEAD
![UI Tier Generation](pics/UITierGeneration.png)

The UI tier is built by the `Webserver` class when the application starts. When building the `Webserver` maps all the classes in this tier labeled 'Route' to different URLs. All Route classes implement the SparkJava interface `Route` which allows these classes to handle web requests and generate webpages for end-users. 


![POST /validateMove](pics/Validate&#32;Move.png)
=======
#### Generation
![UI Tier Generation](pics/UITierGeneration.png)

The UI tier is built by the `Webserver` class when the application starts. When building the `Webserver` maps all the classes in this tier labeled 'Route' to different URLs. All Route classes implement the SparkJava interface `Route` which allows these classes to handle web requests and generate webpages for end-users. 

#### Play Mode
![POST /validateMove](pics/Validate&#32;Move.png)

The UI tier handles events for gameplay. Above depicts the sequence that is executed when a player asks the server to submit a move for validation. When a player makes a move it must be validated on the server to ensure the move follows the rules of checkers.

![POST /submitTurn](pics/Checkers&#32;Turn.png)

Above depicts the average expected sequence for any particular checkers turn. When a player takes a turn they are expected to make a number of moves, then submit there turn for validation. The turn is either rejected or the moves are performed on the board.

#### Spectator Mode
The UI tier allows for people to spectate active games. A spectator will watch a chosen player, as they watch their web-browser will periodically ask the webserver (Through `PostSpectatorCheckRoute`) if any additional turns have been made. If an additional turn was made then the spectator's browser will automatically refresh grabbing the most recent copy of the board.  

#### Replay Mode
The UI tier allows for players to re-watch games that have already been played. When a player first request to see a game (or board) they are shown the board on the first turn of the game. When they press "Next Turn" a request to `PostNextTurnRoute` is made and the webpage is reloaded. A similar event occurs when "Previous Turn" is pressed. 

### Application Tier
The Application Tier holds the main interfaces between the Model Tier
and users. It contains Players and PlayerLobbies, which allows users
to sign in and play against other users. It also holds the
BoardController, which takes in moves from Players and updates the
Model's Board accordingly.

Additionally, the Application tier also holds Chinook, our implementation
of an AI. Chinook extends the Player class and simply sits in the lobby
waiting to be challenged. When playing, Chinook will recursively check
every jump available and assign each a score using a ScoredMoveList. It
then plays the highest-scored route.


### Model Tier
The Model Tier holds the most basic features of WebCheckers. It contains
the Board, Pieces, etc. It has few dependencies on classes outside of
its package. Board holds an array of Spaces, each of which may hold a
Piece. The Model Tier only holds the most basic access methods and
defers to the Application Tier's BoardController class to deal with
Player Moves. Outside of classes, the Model Tier also contains an
enum for holding GameState (RED WINS, WHITE WINS, or IN PROGRESS).

### Design Improvements
Moving forward, we should clean up BoardController and possibly split
it into several smaller classes. Right now, it has much more
functionality than is appropriate for one class. It may make more
sense to split it into BoardController and MoveValidator classes.
In that vein, we need to refactor several methods directly into
Move, Piece, etc.

## Testing
This section provides information about the testing performed
and the results of the testing

### Acceptance Testing
During sprint 3, we conducted across testing with other students. They tested the user stories of what have been implemented. Once we had completed the MVP and enhancements were completed, we conducted our own testing to determine if our project worked properly.
During the cross-testing, the other students discovered an error in regards to the player list not updating for all players who were signed in. We fix the problem after learning of its existence. During the course of our testing, we did not encounter any errors in our user stories.


### Unit Testing and Code Coverage
Our strategy for unit testing the code was very straight forward. We decided that the members who weren't much involved with implementing the MVP were to test the code. We divided the testing into different parts based on the tiers- Application, Model, UI and also including util, which is our utility class. 
The code coverage target for our initial MVP was at least 90%. After finishing most of the testing, our code coverage, generated using jacoco, turned out to be 94% which was a satisfactory result. 
After we finished all our enhancements, we had to re-test a few components of our code. Our target for the final project was 95% code coverage and we were able to accomplish it. We are very proud of the achievement.


### Code Metrics 
We ran multiple code metric measurements to test our code. There were no metric warnings for Chidamber-Kemerer metrics, JavaDoc coverage metrics, Lines of code metrics and Martin package metrics. We have several warnings when running the complexity metrics. On a package level, all packages did not provoke any warnings. There were several classes that were above the threshold. These were CreateModeOptions, BoardController, BoardTest, Board, and Move. On the method level, some of the methods that exceed the thresholds included canJumpTo, canMoveTop, getGameState, mustJumpThisTurn, shouldKing, in the BoardController class. Also included is bestRoute in Chinook, validName in PlayerLobby, hasPiece in Board, testGetSpaces in BoardTest, ConnectMoves in Move and CreateModeOptions and PostSubmitRoute in CheckersPlay.

The recommendation for most of the methods outside of the BoardController class would be to leave as is because they are necessary for the final output and that would be risked by attempting to make it more complex. For testGetSpaces, the code could be reimplemented to be simplified by reusing code. The piece and ConnectedMoves could be reimplemented as well to make better use of iteration. The recommendation for the BoardController class would be to redesign the class as a whole. This class' complexity stems from its effort to coordinate a large amount of information. It would be more effective the responsibility was spread throughout multiple classes and methods.


### Code Coverage Results

#### Overall Tiers
![Overall Tier Code Coverage](pics/overall_coverage.png)

#### Application Tier
![Application Tier Code Coverage](pics/appl_coverage.png)

#### Model Tier
![Model Tier Code Coverage](pics/model_coverage.png)

#### UI Tier
![UI Tier Code Coverage](pics/ui_coverage.png)

#### Checkersplay  Sub-Tier
![Checkersplay Sub-Tier Code Coverage](pics/check_coverage.png)

#### Util Tier
![Util Tier Code Coverage](pics/util_coverage.png)



