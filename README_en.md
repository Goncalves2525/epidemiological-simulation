# Epidemiological Simulation with Numerical Methods

This project implements an epidemiological simulator that uses differential equations to model the spread of infectious diseases. The program offers two numerical integration methods - Euler's Method and 4th order Runge-Kutta Method (RK4) - to solve the differential equations that model population dynamics.

## Features

- Reading epidemiological parameters from CSV files
- Implementation of the SEIR model (Susceptible-Exposed-Infectious-Recovered)
- Two numerical integration methods:
    - Euler's Method
    - 4th order Runge-Kutta Method (RK4)
- Automatic graph generation using Gnuplot
- Interactive interface for parameter definition
- Support for command line execution with arguments

## System Requirements

- Java Runtime Environment (JRE) 8 or higher
- Gnuplot (for graph generation)

## Installation

1. Clone the repository:
   ```
   git clone https://github.com/your-username/repository-name.git
   ```

2. Install Gnuplot:
    - **macOS** (using Homebrew):
      ```
      brew install gnuplot
      ```
    - **Windows**: Download the installer from [Gnuplot Download](http://www.gnuplot.info/download.html)
    - **Linux**:
      ```
      sudo apt-get install gnuplot    # Ubuntu/Debian
      sudo dnf install gnuplot        # Fedora
      ```

## How to Use

### Interactive Mode

1. Run the program without arguments:
   ```
   java Main
   ```

2. Follow the on-screen instructions to:
    - Select the integration method (Euler or RK4)
    - Provide the name of the input CSV file
    - Define the integration step (value between 0 and 1)
    - Specify the population size
    - Set the number of days for analysis
    - Choose which parameter set(s) to analyze

### Command Line Mode

Run the program with arguments in the following order:
```
java Main input_file.csv -m [1|2] -p [0-1] -t [population] -d [days] output_file.csv
```

Where:
- `input_file.csv`: CSV file containing epidemiological parameters
- `-m`: Method (1 for Euler, 2 for RK4)
- `-p`: Integration step (value between 0 and 1)
- `-t`: Population size (positive integer)
- `-d`: Number of days for analysis (positive integer)
- `output_file.csv`: Output file name

## Input File Format

The input CSV file should have the following format:
```
Name,beta,gamma,ro,alpha
Person1,0.5,0.1,0.15,0.2
Person2,0.7,0.2,0.1,0.3
```

Where:
- `Name`: Parameter set identifier
- `beta`: Transmission rate
- `gamma`: Recovery rate
- `ro`: Exposure rate
- `alpha`: Incubation rate

## Outputs

The program generates two types of files:
1. **CSV files** with simulation results
2. **Gnuplot script files (.gp)** for graphical visualization

### Viewing the Graphs

To manually view the generated graphs:

1. Open the terminal and navigate to the directory with the .gp files
2. Run the command:
   ```
   gnuplot
   ```
3. At the Gnuplot prompt, load the script:
   ```
   load 'file_name.gp'
   ```

## Project Structure

- `Main.java`: Main class that manages the user interface and program flow
- `Calculos.java`: Implements the numerical integration methods (Euler and RK4)
- `OperacaoFicheiros.java`: Manages file reading and writing
- `Gnuplot.java`: Creates scripts for Gnuplot and generates graphs
- `Validacoes.java`: Checks the validity of user inputs

## Mathematical Model

The project implements the SEIR model with the following differential equations:

- dS/dt = -β × S × I / N
- dE/dt = β × S × I / N - α × E
- dI/dt = α × E - γ × I
- dR/dt = γ × I

Where:
- S: Susceptible population
- E: Exposed population (infected but not infectious)
- I: Infectious population
- R: Recovered population
- N: Total population
- β: Transmission rate
- α: Incubation rate (transition from exposed to infectious)
- γ: Recovery rate

## License

This project is licensed under [insert your license here, for example, MIT License].

## Authors

[Your name and contact/GitHub]

## Acknowledgments

- [Any acknowledgments or references you want to include]