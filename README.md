# Chinese Checkers
Chinese checkers is a board game played by two to six players. The objective of the game is to move all of your pieces from your starting triangle to the opposite triangle over a hexagonal grid before your opponents do the same. Each player takes turns moving one piece at a time, either by moving a single space or by jumping over other pieces. You can jump over your own pieces or your opponents' pieces, but you must land in an empty space on the other side. The goal of the game is to successfully move all of your pieces to the opposite triangle.

This is an implementation of the game in javafx. There is implemented functionality to highlight and/or enforce all the legal moves. The game operates by first clicking the desired piece you want to move, then clicking the desired location. It is also possible to save the current game state to a file and load it up again.

### Start screen:
![Screenshot 2023-04-16 at 17 25 35](https://user-images.githubusercontent.com/56915010/232323645-8b9bbda5-bdf7-4414-a12a-7de107bf7036.png)

### Example with hightlight- and enforce legal moves:
![Screenshot 2023-04-16 at 17 28 13](https://user-images.githubusercontent.com/56915010/232323658-30482d8b-3ff1-4dec-960a-44206a834004.png)

## Code explaination
### Class diagram of implementation:
![Diagram](https://user-images.githubusercontent.com/56915010/232324957-421dac7b-a51c-4057-b5b0-6633d5501dcf.png)

## Interface & Inheritance
In the project I am using inheritance for the Marble class which extends the built in javafx Circle class. The reason I do this is because the marbles are just drawn circles that need to update their location, but to do that I need to extend the class with the location of the marble where in this case I am storing the hole object as a variable in the marble. I also need to specify the color/army of each marble. In an earlier iteration of the app. I did not use a separate Marble class directly, and instead used the circle and their store data method for color. This was later changed to its own class which opened up for new and easier implementation of new features. It could also be possible to make the hole class an extension of Circle, but in this case I just stored the circle as a variable in each hole.

As for interface, I am using an interface for the FileManager class. In this case it is not strictly necessary, but for future development it could be good if a class needs its own seperate filehandling. I could also have implemented for shared functionality in the Hole and Marble class if both of them were setup as extensions of the Circle class. Then they may have had shared functionality.

## Implementation of delegation?
If I were to implement delegation in my project I probably would simplify the BoardController class and have it delegate some of the functionality to controller subclasses in away which makes the BoardController class less convulated.

## Model-View-Controller principle
The Model-View-Controller (MVC) principle is a software design pattern that separates an application into three interconnected components: the model, the view, and the controller. 

The model represents the data and business logic of the application. It is responsible for managing the data and providing methods to access and manipulate it. In this project you could think of the model as the Hole, Marble and FileManager classes. They implement most of the functionality in the app and the methods to access it. The BoardController does also contain some of the logic that could probably have been seperated into the Hole and Marble class or changed in its entirety

The view is responsible for presenting the data to the user. It is the user interface component of the application and displays the data in a way that is understandable and usable by the user. In this app the view is handled by Board.fxml which is the finished Board made in Scenebuilder with IDs to reference the coordinates of each hole object. The BoardController also draws the Marbles onto the board so some part of it can be considered as part of the view.

The controller acts as an intermediary between the model and the view. It receives input from the user and updates the model accordingly. It also updates the view based on changes in the model. In this case the controller is the BoardController class. It handles clicking and interacting with the board and setup of objects on the board. Some controller functionality is also handled by the Marble class. Which handles highlighting of holes and clicking on marbles.

## Testing
When creating tests for the app I struggled with the board not being initialised for some of the tests, therefore I had to initialise the board before doing most of the tests to create the board objects I could do the testing with. For the Hole class I chose to test the ability to find neighbouring holes which is integral to the highlight- and enforce legal moves functionality. I also chose to test the constructor which takes in circles from the Board and makes them into hole objects. I am also testing the occupied status of the hoels separately. As for the Marble class I am testing moving functionality. I wanted to also test moving when enforce legal moves is enabled so I added a separate test for that. I am also testing saving and loading functionality by clearing the board, then creating some marbles and trying to load and save them.
