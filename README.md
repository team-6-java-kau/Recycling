# Recycling Factory Simulation

## Project Overview

This project simulates a recycling factory with two phases: manual sorting by employees and automated sorting by sensors. The goal is to compare the efficiency, error rates, and other metrics between the two phases.

## Phases

### Phase 1: Manual Sorting
- Employees manually sort recyclable items (plastic, glass, paper, metal).
- Metrics such as time taken, items sorted, and errors are recorded.

### Phase 2: Automated Sorting
- Sensors automatically sort the same list of recyclable items.
- Metrics are recorded and compared with Phase 1.

## Simulation Details

- The user inputs the number of materials entering the factory.
- A random list of recyclable items is generated.
- The same list of items is processed in both phases.

## Metrics Compared

- Time taken to sort items.
- Number of items sorted.
- Number of errors encountered.
- Weight of materials sorted.

## Technologies Used

- Java
- JavaFX for GUI

## How to Run

1. Clone the repository.
2. Open the project in your preferred Java IDE.
3. Run the `GUIMain` class to start the simulation.

## Classes

- `Sorter`: Represents an employee who sorts items.
- `Sensor`: Represents an automated sensor that sorts items.
- `Recyclableitem`: Represents a recyclable item.
- `Plastic`, `Metal`, `Glass`, `Paper`: Specific types of recyclable items.
- `Main`: Contains the main method for running the simulation.
- `GUIMain`: Contains the GUI implementation for the simulation.

## Future Improvements

- Add more detailed metrics and visualizations.
- Improve the GUI for better user experience.
- Add more types of recyclable items.

## License

This project is licensed under the idk License.
